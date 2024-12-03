package delivery.config;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidation {

    private final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,}$";

    private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public boolean isValidPassword(String password) {
        if(password == null){
            return false;
        }

        return pattern.matcher(password).matches();
    }
}
