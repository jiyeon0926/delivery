package delivery.repository.user;

import delivery.entity.user.User;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email% AND u.division = true")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u.role FROM User u WHERE u.id = :userId")
    String findRoleByUserId(@Param("userId") Long userId);
}
