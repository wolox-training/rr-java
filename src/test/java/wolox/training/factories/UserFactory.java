package wolox.training.factories;

import java.time.LocalDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import wolox.training.models.User;

public class UserFactory extends AbstractFactory<User> {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User build() {
        User user = new User();
        user.setUsername(faker().name().username());
        user.setName(faker().name().name());
        user.setBirthdate(LocalDate.now().minusYears(18));
        user.setPassword(passwordEncoder.encode(faker().internet().password()));
        return user;
    }
}
