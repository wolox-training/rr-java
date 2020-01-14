package wolox.training.repositories;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByAuthor(@NotNull String author);
}
