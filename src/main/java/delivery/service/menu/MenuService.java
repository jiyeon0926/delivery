package delivery.service.menu;

import delivery.dto.menu.MenuResponseDto;
import delivery.dto.menu.MenuUpdateResponseDto;
import delivery.entity.menu.Menu;
import delivery.entity.store.Store;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import delivery.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import delivery.repository.menu.MenuRepository;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreService storeService;

    public MenuResponseDto createMenu(Long userId, Long storeId, String name, BigDecimal price, String description) {

        // 가게 확인
        Store store = checkStoreAndOwner(storeId, userId);

        // 메뉴 객체 생성
        Menu menu = new Menu(store, name, price, description);

        // 메뉴 생성
        Menu savedMenu = menuRepository.save(menu);

        // dto 반환
        return new MenuResponseDto(store.getId(), savedMenu.getId(), savedMenu.getName(), savedMenu.getPrice(), savedMenu.getDescription());
    }

    public MenuUpdateResponseDto updateMenu(Long userId, Long storeId, Long menuId, String name, BigDecimal price, String description) {

        // 가게 확인
        checkStoreAndOwner(storeId, userId);

        // 메뉴 확인
        Menu menu = checkMenu(storeId, menuId);

        // 메뉴 수정
        menu.updateMenu(name, price, description);
        menuRepository.save(menu);


        // dto 반환
        return new MenuUpdateResponseDto(menu.getName(), menu.getPrice(), menu.getDescription());
    }

    public void deleteMenu(Long userId, Long storeId, Long menuId) {

        // 가게 확인
        checkStoreAndOwner(storeId, userId);

        // 메뉴 확인
        Menu menu = checkMenu(storeId, menuId);

        // 메뉴 상태를 삭제로 변경
        menu.updateDeleted(true);
        menuRepository.save(menu);

    }

    private Menu checkMenu(Long storeId, Long menuId) {

        // 메뉴 존재 여부 확인
        Menu menu = findMenuByIdOrElseThrow(menuId);

        // 메뉴가 해당 가게의 메뉴인지 확인
        if (!Objects.equals(menu.getStore().getId(), storeId)) {
            throw new CustomException(ErrorCode.MENU_NOT_FOUND);
        }

        // 메뉴 삭제 여부 확인
        if (menu.getIsDeleted().equals(true)){
            throw new CustomException(ErrorCode.ALREADY_DELETE_MENU);
        }
        return menu;
    }

    private Store checkStoreAndOwner(Long storeId, Long userId) {

        // 가게 확인
        Store store = storeService.findStoreById(storeId);

        // 로그인된 사용자가 가게 주인인지 확인
        if (!Objects.equals(userId, store.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_OWNER_CRUD);
        }

        return store;
    }

    //menuId로 메뉴를 찾음
    public Menu findMenuByIdOrElseThrow(Long menuId){
        return menuRepository.findMenuById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
    }
}

