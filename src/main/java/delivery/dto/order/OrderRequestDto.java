package delivery.dto.order;

import lombok.Getter;

@Getter
public class OrderRequestDto {

    private final Long menuId;

    public OrderRequestDto(Long menuId) {
        this.menuId = menuId;
    }
}
