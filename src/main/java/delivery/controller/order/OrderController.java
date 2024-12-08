package delivery.controller.order;

import delivery.dto.order.OrderRejectRequestDto;
import delivery.dto.order.OrderRequestDto;
import delivery.dto.order.OrderResponseDto;
import delivery.service.order.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/orders")
public class OrderController {

    private final OrderService orderService;

    //주문 생성
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable Long storeId,
                                                        @RequestBody OrderRequestDto dto,
                                                        HttpServletRequest request) {

        Long userId = getUserId(request);

        OrderResponseDto orderResponseDto = orderService.createOrder(userId, storeId, dto.getMenuId());

        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    //주문 전체 조회(사장님, 손님용)
    //사장님은 해당 가게의 주문목록을 전체 조회
    //손님은 자신이 주문한 목록을 전체 조회
    //예외처리사항 -> 사장님이 자신의 가게 주문 목록이 아닌 손님으로의 주문목록을 조회하지 못함
    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> findOrders(@PathVariable Long storeId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             HttpServletRequest request) {

        Long userId = getUserId(request);

        Page<OrderResponseDto> orderResponseDtoPage = orderService.findAllOrder(userId, storeId, page, size);

        return new ResponseEntity<>(orderResponseDtoPage, HttpStatus.OK);
    }

    //주문 단건 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findOrder(@PathVariable Long orderId,
                                                      HttpServletRequest request) {

        Long userId = getUserId(request);

        OrderResponseDto orderResponseDto = orderService.findOrder(userId, orderId);

        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    //주문 상태 변경
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long orderId,
                                                        HttpServletRequest request) {

        Long userId = getUserId(request);

        OrderResponseDto orderResponseDto = orderService.updateStatus(userId, orderId);

        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    //주문 거절
    @PatchMapping("/{orderId}/reject")
    public ResponseEntity<OrderResponseDto> rejectOrder(@PathVariable Long orderId,
                                                        @RequestBody OrderRejectRequestDto dto,
                                                        HttpServletRequest request){

        Long userId = getUserId(request);

        OrderResponseDto OrderResponseDto = orderService.rejectOrder(userId, orderId, dto.getReason());

        return new ResponseEntity<>(OrderResponseDto, HttpStatus.OK);
    }

    private static Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long loginUser = (Long) session.getAttribute("LOGIN_USER");
        return loginUser;
    }
}
