package wolox.training.models;

import static java.time.temporal.ChronoUnit.YEARS;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotOwnedException;
import wolox.training.validators.StringValidator;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_books",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<Book> books;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        System.out.println(username);
        StringValidator.validate(username, "username", 50);
        this.username = username;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        StringValidator.validate(name, "name", 120);
        this.name = name;
    }

    @NotNull
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(@NotNull LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate, "birthdate is required.");
        Preconditions.checkArgument(YEARS.between(birthdate, LocalDate.now()) < 18,
            "users can't be less than 18 years old");
        this.birthdate = birthdate;
    }

    @NotNull
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void addBook(@NotNull Book book) throws BookAlreadyOwnedException {
        Preconditions.checkNotNull(book, "you cannot add a non existent book.");
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException();
        }
        books.add(book);
    }

    public void removeBook(@NotNull Book book) throws BookNotOwnedException {
        Preconditions.checkNotNull(book, "you can't remove a null book.");
        if (!books.contains(book)) {
            throw new BookNotOwnedException();
        }
        books.remove(book);
    }

}
