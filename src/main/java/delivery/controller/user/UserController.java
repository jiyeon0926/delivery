package delivery.controller.user;

import delivery.dto.user.DeleteUserRequestDto;
import delivery.dto.user.UpdatePasswordRequestDto;
import delivery.dto.user.UserRequestDto;
import delivery.dto.user.UserResponseDto;
import delivery.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto dto) {
        UserResponseDto userResponseDto = userService.signup(
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getRole()
        );

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody UpdatePasswordRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 세션 가져옴
        Long loginUser = (Long) session.getAttribute("LOGIN_USER"); // 로그인된 사용자 정보 조회

        userService.updatePassword(loginUser, dto.getOldPassword(), dto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteUserRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Long loginUser = (Long) session.getAttribute("LOGIN_USER");

        userService.deleteUser(loginUser, dto.getPassword());
        if(session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
