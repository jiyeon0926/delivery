package delivery.dto.order;

import lombok.Getter;

@Getter
public class OrderRejectRequestDto {
    private String reason;

    public OrderRejectRequestDto(String reason) {
        this.reason = reason;
    }
}
