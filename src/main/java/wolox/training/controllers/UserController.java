package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Api
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    @ApiOperation(value = "Returns all users of the application")
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finds an user by Id")
    public User findOne(@PathVariable @ApiParam(value = "The user's id", required = true) Long id)
        throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a new user")
    public User create(@RequestBody User userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setBirthdate(userDto.getBirthdate());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Removes an user by Id")
    public void deleteUser(
        @PathVariable @ApiParam(value = "The user's id", required = true) Long id)
        throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates an user's information")
    public User updateUser(@RequestBody User dto, @PathVariable Long id)
        throws UserMismatchException, UserNotFoundException {
        if (dto.getId() != id) {
            throw new UserMismatchException();
        }
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        // Update all but password
        user.setBirthdate(dto.getBirthdate());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        return userRepository.save(user);
    }

    @PutMapping("/{id}/password")
    @ApiOperation(value = "Updates an user's password")
    public User updatePassword(@RequestBody User dto, @PathVariable Long id)
        throws UserNotFoundException, UserMismatchException {
        if (dto.getId() != id) {
            throw new UserMismatchException();
        }
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return user;
    }

    @GetMapping("/{userId}/books")
    @ApiOperation(value = "Returns all books from an user")
    public List<Book> getBooks(@PathVariable Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getBooks();
    }

    @ApiOperation(value = "Adds a book to an user")
    @PutMapping("/{userId}/books")
    public Book addBook(@PathVariable Long userId, @RequestBody Long bookId)
        throws UserNotFoundException, BookNotFoundException, BookAlreadyOwnedException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        user.addBook(book);
        userRepository.save(user);
        return book;
    }

    @DeleteMapping("/{userId}/books/{bookId}")
    @ApiOperation(value = "Removes a book from an user")
    public Book removeBook(@PathVariable Long userId, @PathVariable Long bookId)
        throws UserNotFoundException, BookNotFoundException, BookNotOwnedException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        user.removeBook(book);
        userRepository.save(user);
        return book;
    }
}
