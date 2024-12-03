package delivery.service.store;

import delivery.dto.store.StoreResponseDto;
import delivery.entity.store.Store;
import delivery.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreResponseDto createStore(Long userId, String storeName, LocalTime openTime, LocalTime closeTime) {
        Store store = new Store(userId, storeName, openTime, closeTime);
        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

    @Transactional
    public StoreResponseDto updateStore(Long storeId, Long userId, String storeName, LocalTime openTime, LocalTime closeTime) {
        Store store = storeRepository.findStoreByIdAndUserIdOrElseThrow(storeId, userId);
        store.updateStore(storeName, openTime, closeTime);

        return new StoreResponseDto(
                store.getId(),
                store.getUserId(),
                store.getStoreName(),
                store.getOpenTime(),
                store.getCloseTime());
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

    public Optional<Integer> countStore(Long userId) {
        return storeRepository.countByUserId(userId);
    }

    public String findRoleByUserId(Long userId) {
        return storeRepository.findRoleByUserId(userId);
    }
}
