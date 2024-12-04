package delivery.dto.menu;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuUpdateResponseDto {

    private String name;

    private BigDecimal price;

    private String description;

    public MenuUpdateResponseDto(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
