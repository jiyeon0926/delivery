package delivery.controller.menu;

import delivery.dto.menu.MenuRequestDto;
import delivery.dto.menu.MenuUpdateResponseDto;
import delivery.service.menu.MenuService;
import delivery.dto.menu.MenuResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuservice;

    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(@PathVariable Long storeId, @RequestBody MenuRequestDto dto, HttpServletRequest request) {

        // 세션에서 로그인된 사용자 정보 가져오기
        Long userId = getUserId(request);

        // menu 생성 service 실행
        MenuResponseDto menuResponseDto = menuservice.createMenu(userId, storeId, dto.getName(), dto.getPrice(), dto.getDescription());

        // dto 반환
        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(@PathVariable Long storeId, @PathVariable Long menuId, @RequestBody MenuRequestDto dto, HttpServletRequest request) {

        // 세션에서 로그인된 사용자 정보 가져오기
        Long userId = getUserId(request);

        // menu 수정 service 실행
        MenuUpdateResponseDto menuResponseDto = menuservice.updateMenu(userId, storeId, menuId, dto.getName(), dto.getPrice(), dto.getDescription());

        //dto 반환
        return new ResponseEntity<>(menuResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long storeId, @PathVariable Long menuId, HttpServletRequest request) {

        // 세션에서 로그인된 사용자 정보 가져오기
        Long userId = getUserId(request);

        menuservice.deleteMenu(userId, storeId, menuId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("LOGIN_USER");
        return userId;
    }
}


