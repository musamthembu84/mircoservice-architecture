package MicroService.Architecture.service;

import MicroService.Architecture.Exception.BookNotFoundException;
import MicroService.Architecture.Repository.BookRepository;
import MicroService.Architecture.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;


@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(int bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found" + bookId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book createBook(Book book){
        final Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(newBook.getAuthor());
        return bookRepository.save(newBook);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBook(int bookId){
        bookRepository.deleteById(bookId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book updateBook(Map<String, String> updates, int bookID){
        final Book book = findBookById(bookID);
        updates.keySet()
                .forEach(key -> {
                    switch (key){
                        case "author":
                            book.setAuthor(updates.get(key));
                            break;
                        case "title":
                            book.setTitle(updates.get(key));
                    }
                });
        return bookRepository.save(book);
    }

   @Transactional(propagation = Propagation.REQUIRED)
    public Book updateBook(Book book, int bookId){
        Preconditions.checkNotNull(book);
        Preconditions.checkState(book.getId() == bookId);
        Preconditions.checkNotNull(bookRepository.findById(bookId));
        return bookRepository.save(book);
   }
}
