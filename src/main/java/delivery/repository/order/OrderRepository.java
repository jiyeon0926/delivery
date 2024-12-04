package delivery.repository.order;

import delivery.entity.order.Order;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    default Order findOrderByIdOrElseThrow(Long id) {

        return findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    Page<Order> findOrdersByUserId(Long userId, Pageable pageable);
    Page<Order> findOrdersByStoreId(Long storeId, Pageable pageable);
}
