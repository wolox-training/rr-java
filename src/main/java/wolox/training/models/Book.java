package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
import wolox.training.validators.NumberValidator;
import wolox.training.validators.StringValidator;
import wolox.training.validators.YearValidator;

@Entity
@ApiModel("Contains information about each book on the platform")
public class Book {

    private static final String ISBN_FORMAT = "^(97(8|9))?\\d{9}(\\d|X)$";

    @ManyToMany(mappedBy = "books")
    @ApiModelProperty("List of users than has this book")
    List<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty("Unique number identifying the user")
    private long id;
    @Column
    @ApiModelProperty("The genre this book belongs to")
    private String genre;
    @Column(nullable = false)
    @ApiModelProperty("The author of this book")
    private String author;
    @Column(nullable = false)
    @ApiModelProperty("An URL for an image of this book")
    private String image;
    @Column(nullable = false)
    @ApiModelProperty("The title for this book")
    private String title;
    @Column(nullable = false)
    @ApiModelProperty("The subtitle for this book")
    private String subtitle;
    @Column(nullable = false)
    @ApiModelProperty("The publisher of this book")
    private String publisher;
    @Column(nullable = false)
    @ApiModelProperty("The year this book was published")
    private String year;
    @Column(nullable = false)
    @ApiModelProperty("The number of pages of this book")
    private int pages;
    @Column(nullable = false)
    @ApiModelProperty("The ISBN code for this book.")
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
        Preconditions.checkNotNull(author, "author is required");
        this.author = author;
    }

    @NotNull
    public String getImage() {
        return image;
    }

    public void setImage(@NotNull String image) {
        Preconditions.checkNotNull(image, "image is required");
        this.image = image;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        StringValidator.validate(title, "title", 80);
        this.title = title;
    }

    @NotNull
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@NotNull String subtitle) {
        StringValidator.validate(title, "subtitle", 80);
        this.subtitle = subtitle;
    }

    @NotNull
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(@NotNull String publisher) {
        StringValidator.validate(title, "subtitle", 50);
        this.publisher = publisher;
    }

    @NotNull
    public String getYear() {
        return year;
    }

    public void setYear(@NotNull String year) {
        YearValidator.validate(year, "year");
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        NumberValidator.validate(pages, "number of pages", 0, 1000);
        this.pages = pages;
    }

    @NotNull
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NotNull String isbn) {
        Preconditions.checkNotNull(isbn, "ISBN is required");
        Preconditions.checkArgument(isbn.matches(ISBN_FORMAT), "ISBN is not a valid ISBN code.");
        this.isbn = isbn;
    }

    @NotNull
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void addUser(@NotNull User user) throws BookAlreadyOwnedException {
        Preconditions.checkNotNull(user, "user is required.");
        if (users.contains(user)) {
            throw new BookAlreadyOwnedException();
        }
        users.add(user);
    }

    public void removeUser(@NotNull User user) throws BookNotOwnedException {
        Preconditions.checkNotNull(user, "user is required");
        if (!users.contains(user)) {
            throw new BookNotOwnedException();
        }
        users.remove(user);
    }
}
