package delivery.service.menu;

import delivery.dto.menu.MenuResponseDto;
import delivery.dto.menu.MenuUpdateResponseDto;
import delivery.entity.menu.Menu;
import delivery.entity.store.Store;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import delivery.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import delivery.repository.menu.MenuRepository;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto createMenu(Long userId, Long storeId, String name, BigDecimal price, String description) {

        // 가게 확인
        Store store = storeRepository.findStoreByIdAndUserIdOrElseThrow(storeId, userId);

        // 로그인된 사용자가 가게 주인인지 확인
        if (!Objects.equals(userId,store.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_OWNER_CREATE);
        }

        // 메뉴 객체 생성
        Menu menu = new Menu(store, name, price, description);

        // 메뉴 생성
        Menu savedMenu = menuRepository.save(menu);

        // dto 반환
        return new MenuResponseDto(store.getId(), savedMenu.getId(), savedMenu.getName(), savedMenu.getPrice(), savedMenu.getDescription());
    }

    public MenuUpdateResponseDto updateMenu(Long userId, Long storeId, Long menuId, String name, BigDecimal price, String description) {

        // 가게 확인
        Store store = storeRepository.findStoreByIdAndUserIdOrElseThrow(storeId, userId);

        // 로그인된 사용자가 가게 주인인지 확인
        if (!Objects.equals(userId,store.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_OWNER_UPDATE);
        }

        // 메뉴 확인
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);

        // 메뉴 수정
        menu.updateMenu(name, price, description);

        // dto 반환
        return new MenuUpdateResponseDto(menu.getName(), menu.getPrice(), menu.getDescription());
    }
}
