package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.factories.BookFactory;
import wolox.training.models.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTests {

    private final BookFactory bookFactory = new BookFactory();
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenFindingBookByAuthorAndExists_thenReturnBook() {
        Book book = bookFactory.build();
        bookRepository.save(book);

        assertThat(bookRepository.findByAuthor(book.getAuthor()))
            .isNotEmpty()
            .isInstanceOf(Optional.class)
        ;
    }

    @Test
    public void whenFindingBookByAuthorAndNotExists_thenIsNull() {
        Book book = bookFactory.build();

        assertThat(bookRepository.findByAuthor(book.getAuthor())).isEmpty();
    }
}
