package delivery.service.user;

import delivery.config.PasswordEncoder;
import delivery.dto.user.LoginResponseDto;
import delivery.entity.user.User;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(String email, String password) {

        User user = userService.findUserByEmailOrElseThrow(email);

        //비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        return new LoginResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
