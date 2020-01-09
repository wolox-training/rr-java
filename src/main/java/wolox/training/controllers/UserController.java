package wolox.training.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.BookNotOwnedException;
import wolox.training.exceptions.UserMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id)
        throws UserMismatchException, UserNotFoundException {
        if (user.getId() != id) {
            throw new UserMismatchException();
        }
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    @GetMapping("/{userId}/books")
    public List<Book> getBooks(@PathVariable Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getBooks();
    }

    @PostMapping("/{userId}/books")
    public Book addBook(@PathVariable Long userId, @RequestBody Long bookId)
        throws UserNotFoundException, BookNotFoundException, BookAlreadyOwnedException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        user.addBook(book);
        userRepository.save(user);
        return book;
    }

    @DeleteMapping("/{userId}/books/{bookId}")
    public Book removeBook(@PathVariable Long userId, @PathVariable Long bookId)
        throws UserNotFoundException, BookNotFoundException, BookNotOwnedException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        user.removeBook(book);
        userRepository.save(user);
        return book;
    }
}
