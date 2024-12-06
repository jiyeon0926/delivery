package delivery.config;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidation {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean isVaildEmail(String email) {
        if (email == null) {
            return false;
        }

        return pattern.matcher(email).matches();
    }
}
