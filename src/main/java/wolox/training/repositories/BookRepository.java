package wolox.training.repositories;

import javax.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByAuthor(@NotNull String author);
}
