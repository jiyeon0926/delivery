package delivery.service.store;

import delivery.dto.store.StoreMenuResponseDto;
import delivery.dto.store.StoreResponseDto;
import delivery.entity.store.Store;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import delivery.repository.store.StoreRepository;
import delivery.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Transactional
    public StoreResponseDto createStore(Long userId, String storeName, LocalTime openTime, LocalTime closeTime, BigDecimal minOrderPrice) {
        Store store = new Store(userId, storeName, openTime, closeTime, minOrderPrice);
        Optional<Integer> count = storeRepository.countByUserId(userId);
        String role = userService.findRoleByUserId(userId);

        if (count.isPresent() && count.get().intValue() == 3) {
            throw new CustomException(ErrorCode.STORE_COUNT_OVER);
        }

        if (role.equals("USER")) {
            throw new CustomException(ErrorCode.NOT_REGISTER_STORE);
        }

        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

    @Transactional
    public StoreResponseDto updateStore(Long storeId, Long userId, String storeName, LocalTime openTime, LocalTime closeTime, BigDecimal minOrderPrice) {
        Optional<Store> store = storeRepository.findStoreByIdAndUserId(storeId, userId);
        notFoundStore(store);

        store.get().updateStore(storeName, openTime, closeTime, minOrderPrice);

        return StoreResponseDto.toDto(store.get());
    }

    @Transactional
    public void deleteStore(Long storeId, Long userId) {
        Optional<Store> store = storeRepository.findStoreByIdAndUserId(storeId, userId);
        store.orElseThrow(() -> new CustomException(ErrorCode.NOT_OWNER_DELETE));

        store.get().updateDivision();
        storeRepository.save(store.get());
    }

    public List<StoreResponseDto> findAllByStoreName(String storeName) {
       return storeRepository.findAllByStoreName(storeName);
    }

    public StoreMenuResponseDto findMenuByStoreId(Long storeId) {
        Store menu = storeRepository.findMenuByStoreId(storeId);
        Optional<Store> store = storeRepository.findById(storeId);
        notFoundStore(store);

        if (menu == null && !store.isEmpty()) {
            throw new CustomException(ErrorCode.MENU_NOT_FOUND);
        }

        return StoreMenuResponseDto.toDto(menu);
    }

    public Store findStoreById(Long storeId) {
        return storeRepository.findStoreById(storeId);
    }

    private static void notFoundStore(Optional<Store> store) {
        if (store == null || store.isEmpty() || !store.get().isDivision()) {
            throw new CustomException(ErrorCode.STORE_NOT_FOUND);
        }
    }

    //userId로 해당 유저의 가게 개수를 확인
    public Integer findCountByUserId(Long userId) {
        return storeRepository.countByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }
}
