package delivery.dto.order;

import delivery.config.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderResponseDto {

    private final Long id;

    private final Long userId;

    private final Long storeId;

    private final Long menuId;

    private final BigDecimal price;

    private final String status;

    private final String rejectReason;

    public OrderResponseDto(Long id, Long userId, Long storeId, Long menuId, BigDecimal price, OrderStatus status, String rejectReason) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.menuId = menuId;
        this.price = price;
        this.status = status.getDescription();
        this.rejectReason = rejectReason;
    }
}
