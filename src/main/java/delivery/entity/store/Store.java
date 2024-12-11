package delivery.entity.store;

import delivery.entity.Base;
import delivery.entity.menu.Menu;
import delivery.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Store extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String storeName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean division = true;

    @Column(precision = 10, scale = 4, nullable = false)
    private BigDecimal minOrderPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Menu> menus = new ArrayList<>();

    public Store(Long userId, String storeName, LocalTime openTime, LocalTime closeTime, BigDecimal minOrderPrice) {
        this.userId = userId;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

    public void updateStore(String storeName, LocalTime openTime, LocalTime closeTime, BigDecimal minOrderPrice) {
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

    public void updateDivision() {
        this.division = false;
    }
}
