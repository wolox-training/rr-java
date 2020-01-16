package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE "
        + "(?1 IS NULL OR publisher = ?1) AND "
        + "(?2 IS NULL OR year = ?2) AND "
        + "(?3 IS NULL OR genre = ?3)")
    List<Book> findByPublisherAndYearAndGenre(
        String publisher, String year, String genre
    );

    Optional<Book> findByAuthor(@NotNull String author);
}
