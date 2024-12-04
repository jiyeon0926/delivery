package delivery.dto.store;

import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class StoreMenuResponseDto {

    private Long id;
    private String storeName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private List<MenuDto> menus;

    public StoreMenuResponseDto(Long id, String storeName, LocalTime openTime, LocalTime closeTime, List<MenuDto> menus) {
        this.id = id;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.menus = menus;
    }
}
