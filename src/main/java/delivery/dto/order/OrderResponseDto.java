package delivery.dto.order;

import delivery.config.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderResponseDto {

    private final Long id;

    private final String userName;

    private final String storeName;

    private final String menuName;

    private final BigDecimal price;

    private final String status;

    public OrderResponseDto(Long id, String userName, String storeName, String menuName, BigDecimal price, OrderStatus status) {
        this.id = id;
        this.userName = userName;
        this.storeName = storeName;
        this.menuName = menuName;
        this.price = price;
        this.status = status.getDescription();
    }
}
