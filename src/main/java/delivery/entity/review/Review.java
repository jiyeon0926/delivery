package delivery.entity.review;

import delivery.entity.Base;
import delivery.entity.order.Order;
import delivery.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.xml.sax.ext.LexicalHandler;


@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reivew_id")
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "rating", nullable = false)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name ="store_id")
    private Long storeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    }
