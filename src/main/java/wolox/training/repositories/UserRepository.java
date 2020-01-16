package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByBirthdateBetweenAndNameIgnoreCaseContaining(
        @NotNull LocalDate startDate, @NotNull LocalDate endDate, @NotNull CharSequence substring
    );

    Optional<User> findByUsername(@NotNull String username);
}
