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


  List<Review> findAllByStoreIdAndRatingBetweenAndUserIdNotOrderByModifiedAtDesc(Long storeId, int minRating, int maxRating, Long userId);

    List<Review> findAllByStoreIdAndUserIdNotOrderByModifiedAtDesc(Long storeId, Long userId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Review r WHERE r.order.id = :orderId")
    boolean existsByOrderId(@Param("orderId") Long orderId);

}
