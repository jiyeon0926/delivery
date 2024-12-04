package delivery.dto.store;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuDto {

    private String name;
    private BigDecimal price;
    private String description;

    public MenuDto(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
