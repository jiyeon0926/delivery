package delivery.service.order;

import delivery.config.OrderStatus;
import delivery.config.UserStatus;
import delivery.dto.order.OrderResponseDto;
import delivery.entity.menu.Menu;
import delivery.entity.order.Order;
import delivery.entity.store.Store;
import delivery.entity.user.User;
import delivery.error.errorcode.ErrorCode;
import delivery.error.exception.CustomException;
import delivery.repository.menu.MenuRepository;
import delivery.repository.order.OrderRepository;
import delivery.repository.store.StoreRepository;
import delivery.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    //주문 생성
    public OrderResponseDto createOrder(Long userId, Long storeId, Long menuId) {
        User user = userRepository.findUserByIdOrElseThrow(userId);  //사용자 확인
        Store store = storeRepository.findStoreByIdOrElseThrow(storeId);  //가게 확인
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);  //메뉴 확인

        LocalTime now = LocalTime.now();
        //오픈 시간이 아닐 경우
        if(!isStoreOpen(store, now)){
            throw new CustomException(ErrorCode.NOT_OPEN_SOTRE);
        }

        Order order = new Order(user, store, menu);
        order.setStatus(OrderStatus.ORDER_COMPLETED);

        orderRepository.save(order);

        return new OrderResponseDto(
                order.getId(),
                order.getUser().getName(),
                order.getStore().getStoreName(),
                order.getMenu().getName(),
                order.getMenu().getPrice(),
                order.getStatus());
    }

    //주문 전체 조회
    public Page<OrderResponseDto> findAllOrder(Long userId, Long storeId, int page, int size) {

        User user = userRepository.findUserByIdOrElseThrow(userId);

        Pageable pageable = PageRequest.of(page, size);

        if(Objects.equals(user.getRole(), UserStatus.USER.toString())){  //일반 유저의 경우
            Page<Order> orderPage = orderRepository.findOrdersByUserId(userId, pageable);
            return orderPage.map(order -> new OrderResponseDto(
                    order.getId(),
                    order.getUser().getName(),
                    order.getStore().getStoreName(),
                    order.getMenu().getName(),
                    order.getMenu().getPrice(),
                    order.getStatus()));
        }else{  //사장님의 경우
            Page<Order> orderPage = orderRepository.findOrdersByStoreId(storeId, pageable);
            return orderPage.map(order -> new OrderResponseDto(
                    order.getId(),
                    order.getUser().getName(),
                    order.getStore().getStoreName(),
                    order.getMenu().getName(),
                    order.getMenu().getPrice(),
                    order.getStatus()));
        }
    }

    //주문 단건 조회
    public OrderResponseDto findOrder(Long userId, Long orderId) {
        Order order = orderRepository.findOrderByIdOrElseThrow(orderId);

        //주문한 가게의 사장님이거나 주문한 손님만 조회 가능
        if(!order.getUser().getId().equals(userId) && !order.getStore().getUser().getId().equals(userId)){
            throw new CustomException(ErrorCode.UNAUTHORIZED_OTHER_ORDER);
        }

        return new OrderResponseDto(
                order.getId(),
                order.getUser().getName(),
                order.getStore().getStoreName(),
                order.getMenu().getName(),
                order.getMenu().getPrice(),
                order.getStatus());
    }


    //주문 상태 수정
    @Transactional
    public OrderResponseDto updateStatus(Long userId, Long orderId){
        Order order = orderRepository.findOrderByIdOrElseThrow(orderId);

        //가게 주인이 아닌 경우 수정 불가
        if(!order.getStore().getUser().getId().equals(userId)){
            throw new CustomException(ErrorCode.NOT_OWNER_UPDATE);
        }

        //현재 주문상태를 다음 상태로 변경
        OrderStatus nextStatus = order.getStatus().transitionStatus(order.getStatus());
        order.setStatus(nextStatus);

        return new OrderResponseDto(
                order.getId(),
                order.getUser().getName(),
                order.getStore().getStoreName(),
                order.getMenu().getName(),
                order.getMenu().getPrice(),
                order.getStatus());
    }


    //현재 가게의 운영시간을 boolean으로 반환
    private boolean isStoreOpen(Store store, LocalTime now) {
        if(store.getCloseTime().isAfter(store.getOpenTime())){
            return !now.isBefore(store.getOpenTime()) && now.isBefore(store.getCloseTime());  //가게 운영 시간에 맞으면 true
        }else{
            return !now.isBefore(store.getOpenTime()) || now.isBefore(store.getCloseTime());  //가게 운영 시간에 맞으면 true
        }
    }
}
