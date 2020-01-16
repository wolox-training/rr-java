package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
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
            .isNotEmpty()
            .isInstanceOf(Optional.class);
    }

    @Test
    public void whenFindingUserByUsernameAndUserDoesNotExist_thenThrowsException() {
        User user = userFactory.build();

        assertThat(userRepository.findByUsername(user.getUsername()))
            .isEmpty();
    }

    @Test
    public void whenFindingAnUserByConditions_thenReturnsTheUser() {
        User user = userFactory.build();
        user.setName("Pedro");
        userRepository.save(user);

        String partOfName = "PE";
        LocalDate startDate = user.getBirthdate().minusDays(1);
        LocalDate endDate = user.getBirthdate().plusDays(1);

        assertThat(
            userRepository
                .findByBirthdateBetweenAndNameIgnoreCaseContaining(startDate, endDate, partOfName)
        )
            .hasAtLeastOneElementOfType(User.class)
        ;
    }

    @Test
    public void whenFindingAnUserBySomeConditions_thenReturnsTheUser() {
        User user = userFactory.build();
        user.setName("Pedro");
        userRepository.save(user);

        String partOfName = "PE";
        LocalDate startDate = user.getBirthdate().minusDays(1);
        LocalDate endDate = user.getBirthdate().plusDays(1);

        assertThat(
            userRepository
                .findByBirthdateBetweenAndNameIgnoreCaseContaining(null, null, partOfName)
        )
            .hasAtLeastOneElementOfType(User.class)
        ;
        assertThat(
            userRepository
                .findByBirthdateBetweenAndNameIgnoreCaseContaining(startDate, endDate, null)
        )
            .hasAtLeastOneElementOfType(User.class)
        ;
        assertThat(
            userRepository
                .findByBirthdateBetweenAndNameIgnoreCaseContaining(startDate, null, partOfName)
        )
            .hasAtLeastOneElementOfType(User.class)
        ;
    }
}
