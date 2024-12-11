package delivery.controller.user;

import delivery.dto.user.LoginRequestDto;
import delivery.dto.user.LoginResponseDto;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import delivery.service.user.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto,
                                                  HttpServletRequest request) {

        LoginResponseDto loginResponseDto = loginService.login(dto.getEmail(), dto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_USER", loginResponseDto.getId());

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 현재 세션 조회
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
