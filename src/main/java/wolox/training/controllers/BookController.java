package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/greeting")
    @ApiOperation(value = "Sends some greeting for a specific name")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "When a greeting is requested.")
    })
    public String greeting(
        @RequestParam(name = "name", required = false, defaultValue = "World") String name,
        Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping
    @ApiOperation(value = "Returns all available books")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "When thee application lists all available books.")
    })
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a book by an Id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "When an existing book is asked"),
        @ApiResponse(code = 404, message = "When a book could not be found")
    })
    public Book findOne(@PathVariable Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a new Book")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "When a valid book is created"),
        @ApiResponse(code = 422, message = "When the book information is invalid")
    })
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Destroys a book")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "When an existing book is deleted"),
        @ApiResponse(code = 404, message = "When a book could not be found")
    })
    public void deleteBook(@PathVariable Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a book's information")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "When an existing book is updated with valid information"),
        @ApiResponse(code = 404, message = "When a book could not be found"),
        @ApiResponse(code = 422, message = "When the book information is invalid")
    })
    public Book updateBook(@RequestBody Book book, @PathVariable Long id)
        throws BookNotFoundException, BookMismatchException {
        if (book.getId() != id) {
            throw new BookMismatchException();
        }
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}
