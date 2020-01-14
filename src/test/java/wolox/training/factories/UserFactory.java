package wolox.training.factories;

import java.time.LocalDate;
import wolox.training.models.User;

public class UserFactory extends AbstractFactory<User> {

    @Override
    public User build() {
        User user = new User();
        user.setUsername(faker().name().username());
        user.setName(faker().name().name());
        user.setBirthdate(LocalDate.now().minusYears(18));
        return user;
    }
}
