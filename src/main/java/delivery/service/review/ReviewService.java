package delivery.service.review;

import delivery.dto.review.ReviewRequestDto;
import delivery.dto.review.ReviewResponseDto;
import delivery.entity.order.Order;
import delivery.entity.review.Review;
import delivery.config.OrderStatus;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import delivery.repository.order.OrderRepository;
import delivery.repository.review.ReviewRepository;
import delivery.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewResponseDto addReview(Long userId, long storeId, long orderId, ReviewRequestDto requestDto) {
      Order order = orderRepository.findById(orderId).orElseThrow(()-> new NullPointerException("존재하지 않는 주문입니다."));

        // 로그인된 사용자가 주문을 했는지 확인
        if (!Objects.equals(userId,order.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_OWNER_ORDER);
        }


        if(requestDto.getComment() == null || requestDto.getComment().isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY);
        }

        if(requestDto.getRating() < 1 || requestDto.getRating() > 5) {
            throw new CustomException(ErrorCode.STAR_OVER);
        }

        if (order.getStatus() != OrderStatus.DELIVERY_COMPLETED){
            throw new CustomException(ErrorCode.NOT_DELIVERY_COMPLETED);
        }


        Review newReview = Review.builder()
                .comment(requestDto.getComment())
                .rating(requestDto.getRating())
                .order(order)
                .storeId(storeId)
                .build();

        reviewRepository.save(newReview);

        return new ReviewResponseDto(newReview);
    }
//일반정렬
    public List<ReviewResponseDto> getReviews(long storeId) {

        List<Review> reviewList = reviewRepository.findAllByStoreIdOrderByModifiedAtDesc( storeId);
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }

        return reviewResponseDtoList;
    }
// 별점으로 정렬
    public List<ReviewResponseDto> getReviewByStar(long storeId, int minRating, int maxRating ) {
        if (minRating < 1 || minRating > 5 || maxRating < 1 || maxRating > 5) {
            throw new CustomException(ErrorCode.STAR_OVER);
        }

        List<Review> reviewList = reviewRepository.findAllByStoreIdAndRatingBetweenOrderByModifiedAtDesc(storeId, minRating, maxRating);
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }

        return reviewResponseDtoList;
    }

}

