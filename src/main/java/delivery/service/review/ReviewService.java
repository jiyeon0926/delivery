package delivery.service.review;

import delivery.dto.review.ReviewRequestDto;
import delivery.dto.review.ReviewResponseDto;
import delivery.entity.order.Order;
import delivery.entity.review.Review;
import delivery.config.OrderStatus;
import delivery.entity.user.User;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import delivery.repository.order.OrderRepository;
import delivery.repository.review.ReviewRepository;
import delivery.repository.store.StoreRepository;
import delivery.repository.user.UserRepository;
import delivery.service.user.UserService;
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
    private final UserService userService;

    @Transactional
    public ReviewResponseDto addReview(Long userId, long storeId, long orderId, ReviewRequestDto requestDto) {
      Order order = orderRepository.findById(orderId).orElseThrow(()-> new NullPointerException("존재하지 않는 주문입니다."));
      User user = userService.findUserByIdOrElseThrow(userId);  //사용자 확인
        // 로그인된 사용자가 주문을 했는지 확인
        if (!Objects.equals(userId,order.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_OWNER_ORDER);
        }
        //빈칸
        if(requestDto.getComment() == null || requestDto.getComment().isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY);
        }
        //별점오류
        if(requestDto.getRating() < 1 || requestDto.getRating() > 5) {
            throw new CustomException(ErrorCode.STAR_OVER);
        }
        //배달완료에서만 작성
        if (order.getStatus() != OrderStatus.DELIVERY_COMPLETED){
            throw new CustomException(ErrorCode.NOT_DELIVERY_COMPLETED);
        }
        //리뷰 1개만 작성가능
        if(reviewRepository.existsByOrderId(orderId)){
            throw new CustomException(ErrorCode.REVIEW_COMPLETED);
        }

        Review newReview = Review.builder()
                .comment(requestDto.getComment())
                .rating(requestDto.getRating())
                .user(user)
                .order(order)
                .storeId(storeId)
                .build();

        reviewRepository.save(newReview);

        return new ReviewResponseDto(newReview);
    }
    //일반정렬
    public List<ReviewResponseDto> getReviews(long storeId, long userId) {

        List<Review> reviewList = reviewRepository.findAllByStoreIdAndUserIdNotOrderByModifiedAtDesc(storeId, userId);
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        reviewList(reviewList, reviewResponseDtoList);

        return reviewResponseDtoList;
    }

    // 별점으로 정렬
    public List<ReviewResponseDto> getReviewByStar(long storeId, int minRating, int maxRating, long userId ) {
        if (minRating < 1 || minRating > 5 || maxRating < 1 || maxRating > 5) {
            throw new CustomException(ErrorCode.STAR_OVER);
        }

        List<Review> reviewList = reviewRepository.findAllByStoreIdAndRatingBetweenAndUserIdNotOrderByModifiedAtDesc(storeId, minRating, maxRating, userId);
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        reviewList(reviewList, reviewResponseDtoList);

        return reviewResponseDtoList;
    }

    private static void reviewList(List<Review> reviewList, List<ReviewResponseDto> reviewResponseDtoList) {
        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }
    }
}

