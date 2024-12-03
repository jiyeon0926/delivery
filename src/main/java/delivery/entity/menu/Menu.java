package delivery.entity.menu;

import delivery.entity.Base;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "menu")
public class Menu extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    public Menu() {
    }

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 생성자 (메뉴 생성)
    public Menu(Store store, String name, BigDecimal price, String description) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}

