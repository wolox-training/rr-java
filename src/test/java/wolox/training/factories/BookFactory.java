package wolox.training.factories;

import java.util.Optional;
import wolox.training.models.Book;

public class BookFactory extends AbstractFactory<Book> {

    @Override
    public Book build() {
        Book book = new Book();
        book.setTitle(faker().book().title());
        book.setSubtitle(faker().book().title());
        book.setAuthor(faker().book().author());
        book.setGenre(Optional.of(faker().book().genre()));
        book.setImage(faker().internet().image());
        book.setIsbn("9780672317248");
        book.setPages(faker().number().numberBetween(10, 100));
        book.setPublisher(faker().book().publisher());
        book.setYear("2020");
        return book;
    }

}
