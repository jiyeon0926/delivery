package delivery.entity.store;

import delivery.entity.Base;
import delivery.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Store(Long userId, String storeName, LocalTime openTime, LocalTime closeTime) {
        this.userId = userId;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void updateStore(String storeName, LocalTime openTime, LocalTime closeTime) {
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void updateDivision() {
        this.division = false;
    }
}
