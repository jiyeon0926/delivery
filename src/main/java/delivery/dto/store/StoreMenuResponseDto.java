package delivery.dto.store;

import delivery.entity.store.Store;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StoreMenuResponseDto {

    private Long id;
    private String storeName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private BigDecimal minOrderPrice;
    private List<MenuDto> menus;

    public StoreMenuResponseDto(Long id, String storeName, LocalTime openTime, LocalTime closeTime, BigDecimal minOrderPrice, List<MenuDto> menus) {
        this.id = id;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
        this.menus = menus;
    }

    public static StoreMenuResponseDto toDto(Store store) {
        List<MenuDto> menus = store.getMenus().stream()
                .map(menu -> new MenuDto(menu.getName(), menu.getPrice(), menu.getDescription()))
                .collect(Collectors.toList());

        return new StoreMenuResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderPrice(),
                menus
        );
    }
}
