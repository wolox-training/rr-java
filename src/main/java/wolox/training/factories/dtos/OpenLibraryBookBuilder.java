package wolox.training.factories.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import wolox.training.dtos.OpenLibraryBook;

public class OpenLibraryBookBuilder {

    private List<String> authors;
    private List<String> publishers;
    private String title;
    private Optional<String> subtitle;
    private int numberOfPages;
    private String publishDate;

    public OpenLibraryBookBuilder() {
        authors = new ArrayList<>();
        publishers = new ArrayList<>();
    }

    public void title(String title) {
        this.title = title;
    }

    public void subtitle(Optional<String> subtitle) {
        this.subtitle = subtitle;
    }

    public void numberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void publishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void author(String name) {
        authors.add(name);
    }

    public void publisher(String name) {
        publishers.add(name);
    }

    public OpenLibraryBook build() {
        OpenLibraryBook book = new OpenLibraryBook();
        book.setTitle(title);
        book.setSubtitle(subtitle);
        book.setNumberOfPages(numberOfPages);
        book.setPublishDate(publishDate);
        book.setAuthors(authors);
        book.setPublishers(publishers);
        return book;
    }
}
