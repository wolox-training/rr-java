package wolox.training.repositories;

import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Query(value = "SELECT b FROM Book b WHERE b.author = ?1")
    Book findByAuthor(@NotNull String author);
}
