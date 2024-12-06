package delivery.controller.store;

import delivery.dto.store.StoreMenuResponseDto;
import delivery.dto.store.StoreRequestDto;
import delivery.dto.store.StoreResponseDto;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import delivery.service.store.StoreService;
import delivery.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    // 가게 등록
    @PostMapping
    public ResponseEntity<StoreResponseDto> createStore(@Valid @RequestBody StoreRequestDto storeRequestDto,
                                                        HttpServletRequest servletRequest) {
        Long userId = getUserId(servletRequest);
        String role = userService.getRole(userId);
        if(role.equals("USER")) {
            throw new CustomException(ErrorCode.NOT_REGISTER_STORE);
        }

        return new ResponseEntity<>(storeService.createStore(userId, storeRequestDto.getStoreName(), storeRequestDto.getOpenTime(), storeRequestDto.getCloseTime(), storeRequestDto.getMinOrderPrice()), HttpStatus.CREATED);
    }

    // 가게 수정
    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable Long storeId,
                                                        @Valid @RequestBody StoreRequestDto storeRequestDto,
                                                        HttpServletRequest servletRequest) {
        Long userId = getUserId(servletRequest);

        return ResponseEntity.ok().body(storeService.updateStore(storeId, userId, storeRequestDto.getStoreName(), storeRequestDto.getOpenTime(), storeRequestDto.getCloseTime(), storeRequestDto.getMinOrderPrice()));
    }

    // 가게 폐업
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId,
                            HttpServletRequest servletRequest) {
        Long userId = getUserId(servletRequest);
        storeService.deleteStore(storeId, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 가게명으로 가게 전체 조회
    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> findAll(@RequestParam String storeName) {
        return ResponseEntity.ok().body(storeService.findAllByStoreName(storeName));
    }

    // 가게 메뉴 포함 단건 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreMenuResponseDto> findMenuByStoreId(@PathVariable Long storeId) {
        return ResponseEntity.ok().body(storeService.findMenuByStoreId(storeId));
    }

    private static Long getUserId(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        return userId;
    }
}
