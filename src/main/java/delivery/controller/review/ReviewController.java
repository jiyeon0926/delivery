package delivery.controller.review;

import delivery.dto.order.OrderResponseDto;
import delivery.dto.review.ReviewRequestDto;
import delivery.dto.review.ReviewResponseDto;
import delivery.entity.user.User;
import delivery.service.review.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/stores/{storeId}/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> addReview(@PathVariable long storeId,
                                                       @PathVariable long orderId,
                                                       @RequestBody ReviewRequestDto requestDto,
                                                       HttpServletRequest request
                                                       ) {
        // 세션에서 로그인된 사용자 정보 가져오기
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        ReviewResponseDto reviewResponseDto = reviewService.addReview(userId, storeId, orderId, requestDto);
        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
      //  return ResponseEntity.ok(reviewService.addReview(storeId,orderId,userId,requestDto));
    }

    // 가게 별 리뷰 전체 조회
    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@PathVariable Long storeId,
                                                              HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션 가져오기
        Long userId = (Long) session.getAttribute("LOGIN_USER"); // 로그인된 사용자 ID 가져오기

     return ResponseEntity.ok(reviewService.getReviews(storeId, userId));
    }

    //별점조회
    @GetMapping("/stores/{storeId}/rating")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviewsByStar(
            @PathVariable long storeId,
            @RequestParam int minRating,
            @RequestParam int maxRating,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        return ResponseEntity.ok(reviewService.getReviewByStar(storeId, minRating, maxRating, userId));
    }
}