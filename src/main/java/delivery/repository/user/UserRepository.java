package delivery.repository.user;

import delivery.entity.user.User;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    default User findUserByIdOrElseThrow(long id) {

        return findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.ID_NOT_FOUND));
    }

    default User findUserByEmailOrElseThrow(String email) {

        return findUserByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.ID_NOT_FOUND));
    }

}
