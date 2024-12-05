package delivery.entity.order;

import delivery.config.OrderStatus;
import delivery.entity.Base;
import delivery.entity.menu.Menu;
import delivery.entity.store.Store;
import delivery.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "`order`")
public class Order extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = true)
    private String rejectReason;

    public Order() {}

    public Order(User user, Store store, Menu menu) {
        this.user = user;
        this.store = store;
        this.menu = menu;
    }

    public void orderReject(String reason){
        this.rejectReason = reason;
        this.status = OrderStatus.ORDER_REJECTED;
    }
}
