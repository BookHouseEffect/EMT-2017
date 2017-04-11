package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.Book;
import mk.ukim.finki.emt.persistence.BookRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author CekovskiMrGjorche
 */
abstract class BookAbstraction {

    final BookRepository bookRepository;

    @Autowired
    public BookAbstraction(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    Book checkBook(long bookId){
        Book book = bookRepository.findOne(bookId);
        if (book == null)
            throw new ObjectNotFoundException(bookId, "Book");
        return book;
    }
}
