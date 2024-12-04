package delivery.entity.menu;

import delivery.entity.Base;
import delivery.entity.store.Store;
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

    @Column(nullable = false)
    private Boolean isDeleted = false;

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

    // 생성자 (메뉴 수정)
    public void updateMenu(String name, BigDecimal price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void updateDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
    }
}

