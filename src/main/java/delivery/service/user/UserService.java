package delivery.service.user;

import delivery.config.PasswordEncoder;
import delivery.config.PasswordValidation;
import delivery.dto.user.UserResponseDto;
import delivery.entity.user.User;
import delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordValidation passwordValidation;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //유저 생성
    public UserResponseDto signup(String name, String email, String password, String role) {

        //같은 아이디가 존재할 시
        if(userRepository.findUserByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "같은 아이디가 존재합니다.");
        }
        //비밀번호 형식이 다를 시
        if(!passwordValidation.isValidPassword(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 형식이 다릅니다.");
        }

        User user = new User(name, email, passwordEncoder.encode(password), role, true);
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
    }

    //유저 비밀번호 수정
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        User user = userRepository.findUserByIdOrElseThrow(id);

        // 기존 비밀번호 불일치 시 변경 불가
        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "기존 비밀번호가 일치하지 않습니다.");
        }
        // 기존 비밀번호와 변경하는 비밀번호가 동일할 시 변경 불가
        if(oldPassword.equals(newPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "같은 비밀번호로 변경할 수 없습니다.");
        }
        // 새로운 비밀번호의 형식이 조건에 맞지 않을 시 변경 불가
        if(!passwordValidation.isValidPassword(newPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 형식이 다릅니다.");
        }

        user.updatePassword(passwordEncoder.encode(newPassword));
    }

    //유저 삭제
    public void deleteUser(Long id, String password) {

        User user = userRepository.findUserByIdOrElseThrow(id);

        //패스워드 불일치 시
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }
}
