package delivery.service.store;

import delivery.dto.store.MenuDto;
import delivery.dto.store.StoreMenuResponseDto;
import delivery.dto.store.StoreResponseDto;
import delivery.entity.store.Store;
import delivery.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreResponseDto createStore(Long userId, String storeName, LocalTime openTime, LocalTime closeTime, BigDecimal minOrderPrice) {
        Store store = new Store(userId, storeName, openTime, closeTime, minOrderPrice);
        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

    @Transactional
    public StoreResponseDto updateStore(Long storeId, Long userId, String storeName, LocalTime openTime, LocalTime closeTime, BigDecimal minOrderPrice) {
        Store store = storeRepository.findStoreByIdAndUserIdOrElseThrow(storeId, userId);
        store.updateStore(storeName, openTime, closeTime, minOrderPrice);

        return new StoreResponseDto(
                store.getId(),
                store.getUserId(),
                store.getStoreName(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderPrice());
    }

    @Transactional
    public void deleteStore(Long storeId, Long userId) {
        Store store = storeRepository.findStoreByIdAndUserIdOrElseThrow(storeId, userId);
        store.updateDivision();
        storeRepository.save(store);
    }

    public List<StoreResponseDto> findAllByStoreName(String storeName) {
       return storeRepository.findAllByStoreName(storeName);
    }

    public StoreMenuResponseDto findMenuByStoreId(Long storeId) {
        Store store = storeRepository.findMenuByStoreIdOrElseThrow(storeId);

        List<MenuDto> menuDtos = store.getMenus().stream()
                .map(menu -> new MenuDto(menu.getName(), menu.getPrice(), menu.getDescription()))
                .collect(Collectors.toList());

        return new StoreMenuResponseDto(
                store.getId(),
                store.getStoreName(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderPrice(),
                menuDtos);
    }

    public Optional<Integer> countStore(Long userId) {
        return storeRepository.countByUserId(userId);
    }

    public String findRoleByUserId(Long userId) {
        return storeRepository.findRoleByUserId(userId);
    }

    public Store findById(Long storeId) {
        return storeRepository.findStoreByIdOrElseThrow(storeId);
    }
}
