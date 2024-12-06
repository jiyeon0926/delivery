package delivery.entity.user;

import delivery.entity.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;

    private boolean division;

    public User() {}

    public User(String name, String email, String password, String role, boolean division) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.division = division;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateDivision() {
        this.division = false;
    }
}
