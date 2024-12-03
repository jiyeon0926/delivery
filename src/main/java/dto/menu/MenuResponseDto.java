package dto.menu;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuResponseDto {

    private Long storeId;

    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    public MenuResponseDto(Long storeId, Long id, String name, BigDecimal price, String description) {
        this.storeId = storeId;
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
