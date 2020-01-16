package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE "
        // If any date is null or it's between dates
        + "(?1 IS NULL OR ?2 IS NULL OR u.birthdate BETWEEN ?1 AND ?2) AND "
        // If the name is not present or like the searched value
        + "(?3 IS NULL OR LOWER(u.name) LIKE  CONCAT('%', LOWER(?3), '%'))")
    List<User> findByBirthdateBetweenAndNameIgnoreCaseContaining(
        LocalDate startDate, LocalDate endDate, @NotNull CharSequence substring
    );

    Optional<User> findByUsername(@NotNull String username);
}
