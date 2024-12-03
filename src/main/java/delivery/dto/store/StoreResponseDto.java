package delivery.dto.store;

import delivery.entity.store.Store;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreResponseDto {

    private Long id;
    private Long userId;
    private String storeName;
    private LocalTime openTime;
    private LocalTime closeTime;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.userId = store.getUserId();
        this.storeName = store.getStoreName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
    }

    public StoreResponseDto(Long id, Long userId, String storeName, LocalTime openTime, LocalTime closeTime) {
        this.id= id;
        this.userId = userId;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public static StoreResponseDto toDto(Store store) {
        return new StoreResponseDto(
                store.getId(),
                store.getUserId(),
                store.getStoreName(),
                store.getOpenTime(),
                store.getCloseTime()
        );
    }
}
