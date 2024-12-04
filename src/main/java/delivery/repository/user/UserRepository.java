package delivery.repository.user;

import delivery.entity.user.User;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.division = true")
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
