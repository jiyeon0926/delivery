package delivery.repository.review;

import delivery.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {


   List<Review> findAllByStoreIdAndRatingBetweenOrderByModifiedAtDesc(Long storeId, int minRating, int maxRating);
    List<Review> findAllByStoreIdOrderByModifiedAtDesc(Long storeId);

//    // 로그인한 사용자의 리뷰 제외 + 평점 범위 조건 추가
//    @Query("SELECT r FROM Review r WHERE r.storeId = ?1 " +
//            "AND r.userId != ?2 " +
//            "AND r.rating BETWEEN ?3 AND ?4 " +
//            "ORDER BY r.modifiedAt DESC")
//    List<Review> findReviewsExcludingUserAndByRating(Long storeId, Long userId, int minRating, int maxRating);
}
