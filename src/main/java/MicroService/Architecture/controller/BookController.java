package MicroService.Architecture.controller;

import MicroService.Architecture.model.Book;
import MicroService.Architecture.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> findAllBooks(){
        return bookService.findAllBooks();
    }

    @GetMapping("/{bookId}")
    private Book findBook(@PathVariable int bookId){
        return bookService.findBookById(bookId);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return bookService.createBook(book);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable int bookId){
        bookService.deleteBook(bookId);
    }

    @PutMapping("/{bookId}")
    public Book updateBook(@RequestBody Book book, @PathVariable int bookId){
        return bookService.updateBook(book,bookId);
    }

    @PatchMapping("/{bookId}")
    public Book updateBook(@RequestBody Map<String,String> updates, @PathVariable int bookId){
        return bookService.updateBook(updates,bookId);
    }
}
