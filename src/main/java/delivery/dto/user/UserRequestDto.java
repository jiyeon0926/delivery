package delivery.dto.user;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String name;

    private final String email;

    private final String password;

    private final String role;

    public UserRequestDto(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
