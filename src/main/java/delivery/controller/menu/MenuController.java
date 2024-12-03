package delivery.controller.menu;

import delivery.dto.menu.MenuRequestDto;
import delivery.entity.user.User;
import delivery.service.menu.MenuService;
import delivery.dto.menu.MenuResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute("sessionKey");

        // menu 생성 service 실행
        MenuResponseDto menuResponseDto = menuservice.createMenu(loginUser.getId(), storeId, dto.getName(), dto.getPrice(), dto.getDescription());

        // dto 반환
        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }
}


