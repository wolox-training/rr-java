package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByPublisherAndYearAndGenre(
        @NotNull String publisher, @NotNull String year, @NotNull String genre
    );

    Optional<Book> findByAuthor(@NotNull String author);
}
