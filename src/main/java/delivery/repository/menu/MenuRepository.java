package delivery.repository.menu;

import delivery.entity.menu.Menu;
import delivery.entity.store.Store;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findMenuById(Long menuId);

    default Menu findMenuByIdOrElseThrow(Long menuId) {
        return findMenuById(menuId).orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }
}
