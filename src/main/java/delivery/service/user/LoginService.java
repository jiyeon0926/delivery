package delivery.service.user;

import delivery.config.PasswordEncoder;
import delivery.dto.user.LoginResponseDto;
import delivery.entity.user.User;
import delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(String email, String password) {

        User user = userRepository.findUserByEmailOrElseThrow(email);

        //비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀 번호가 일치하지 않습니다.");
        }

        return new LoginResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
