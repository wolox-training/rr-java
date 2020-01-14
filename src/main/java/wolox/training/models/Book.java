package wolox.training.models;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotOwnedException;

/**
 * This class represents a Book on the system.
 * <p>
 * A book has infinite stock as it's a digital product, so quantity doesn't exist. Later, an user
 * will be able to add or remove books from their collection.
 */
@Entity
public class Book {

    /**
     * A list of users containing the book.
     */
    @ManyToMany(mappedBy = "books")
    List<User> users;

    /**
     * The ID of a Book, an unique identifier generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The genre of the book, this value is Optional.
     */
    @Column
    private String genre;

    /**
     * The author of the book.
     */
    @Column(nullable = false)
    private String author;

    /**
     * An URL for the book's cover photo.
     */
    @Column(nullable = false)
    private String image;

    /**
     * The book's title.
     */
    @Column(nullable = false)
    private String title;

    /**
     * The books's subtitle.
     */
    @Column(nullable = false)
    private String subtitle;

    /**
     * The book's publisher
     */
    @Column(nullable = false)
    private String publisher;

    /**
     * The year when this book was published.
     */
    @Column(nullable = false)
    private String year;

    /**
     * The number of pages on the book.
     */
    @Column(nullable = false)
    private int pages;

    /**
     * The ISBN code identifying this book.
     */
    @Column(nullable = false)
    private String isbn;

    public Book() {
    }


    public long getId() {
        return id;
    }

    public Optional<String> getGenre() {
        return Optional.ofNullable(genre);
    }

    public void setGenre(Optional<String> genre) {
        this.genre = genre.orElse(null);
    }

    @NotNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NotNull String author) {
        this.author = author;
    }

    @NotNull
    public String getImage() {
        return image;
    }

    public void setImage(@NotNull String image) {
        this.image = image;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    @NotNull
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@NotNull String subtitle) {
        this.subtitle = subtitle;
    }

    @NotNull
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(@NotNull String publisher) {
        this.publisher = publisher;
    }

    @NotNull
    public String getYear() {
        return year;
    }

    public void setYear(@NotNull String year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @NotNull
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NotNull String isbn) {
        this.isbn = isbn;
    }

    @NotNull
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void addUser(@NotNull User user) throws BookAlreadyOwnedException {
        if (users.contains(user)) {
            throw new BookAlreadyOwnedException();
        }
        users.add(user);
    }

    public void removeUser(@NotNull User user) throws BookNotOwnedException {
        if (!users.contains(user)) {
            throw new BookNotOwnedException();
        }
        users.remove(user);
    }
}
