package delivery.service.menu;

import delivery.dto.menu.MenuResponseDto;
import delivery.entity.menu.Menu;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import delivery.repository.menu.MenuRepository;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuResponseDto createMenu(Long userId, Long storeId, String name, BigDecimal price, String description) {
        // 가게 확인
        Store store = storepRepository.findByIdOrElseThrow(storeId);

        // 로그인된 사용자가 가게 주인인지 확인
        if (!Objects.equals(userId,store.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_OWNER_CREATE);
        }

        // 메뉴 저장
        Menu menu = new Menu(store, name, price, description);

        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(store.getId(), savedMenu.getId(), savedMenu.getName(), savedMenu.getPrice());
    }
}
