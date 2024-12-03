package delivery.dto.user;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final Long id;

    private final String name;

    private final String email;

    private final String role;

    public LoginResponseDto(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
