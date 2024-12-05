package delivery.repository.review;

import delivery.entity.order.Order;
import delivery.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


   List<Review> findAllByStoreIdAndRatingBetweenOrderByModifiedAtDesc(Long storeId, int minRating, int maxRating);
    List<Review> findAllByStoreIdOrderByModifiedAtDesc(Long storeId);

//    // 로그인한 사용자의 리뷰 제외 + 평점 범위 조건 추가
//    @Query("SELECT r FROM Review r WHERE r.storeId = ?1 " +
//            "AND r.userId != ?2 " +
//            "AND r.rating BETWEEN ?3 AND ?4 " +
//            "ORDER BY r.modifiedAt DESC")
//    List<Review> findReviewsExcludingUserAndByRating(Long storeId, Long userId, int minRating, int maxRating);

//    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END FROM Review o WHERE o.id = :orderId")
//    boolean existsByReview_OrderId(@Param("orderId") Long orderId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Review r WHERE r.order.id = :orderId")
    boolean existsByOrderId(@Param("orderId") Long orderId);
}
