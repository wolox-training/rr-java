package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.factories.UserFactory;
import wolox.training.models.User;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTests {

    private final UserFactory userFactory = new UserFactory();
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindingUserByNameAndUserExists_thenReturnsAnEmployee() {
        User user = userFactory.build();
        userRepository.save(user);

        assertThat(userRepository.findByUsername(user.getUsername()))
            .isNotNull()
            .isInstanceOf(User.class);
    }

    @Test
    public void whenFindingUserByUsernameAndUserDoesNotExist_thenThrowsException() {
        User user = userFactory.build();

        assertThat(userRepository.findByUsername(user.getUsername()))
            .isNull();
    }
}
