package delivery.repository.store;

import delivery.dto.store.StoreResponseDto;
import delivery.entity.store.Store;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findStoreByIdAndUserId(Long id, Long userId);

    default Store findStoreByIdAndUserIdOrElseThrow(Long id, Long userId) {
        return findStoreByIdAndUserId(id, userId).orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }

    default Store findStoreByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }

    @Query("SELECT s FROM Store s WHERE s.storeName LIKE %:storeName% AND s.division = true ORDER BY s.closeTime DESC")
    List<StoreResponseDto> findAllByStoreName(@Param("storeName") String storeName);

    @Query("SELECT COUNT(s.userId) FROM Store s WHERE s.userId = :userId AND s.division = true GROUP BY s.userId")
    Optional<Integer> countByUserId(@Param("userId") Long userId);

    @Query("SELECT u.role FROM User u WHERE u.id = :userId")
    String findRoleByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Store s INNER JOIN FETCH s.menus m WHERE s.id = :storeId AND s.division = true")
    Optional<Store> findMenuByStoreId(@Param("storeId") Long storeId);
}
