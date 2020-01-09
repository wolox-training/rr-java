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

@Entity
public class Book {

    @ManyToMany(mappedBy = "books")
    List<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String genre;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String subtitle;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false)
    private String year;
    @Column(nullable = false)
    private int pages;
    @Column(nullable = false)
    private String isbn;

    public Book() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
