package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.exceptions.NoBookInStockException;
import mk.ukim.finki.emt.model.jpa.Book;
import mk.ukim.finki.emt.persistence.BookRepository;
import mk.ukim.finki.emt.service.StockServiceHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CekovskiMrGjorche
 */
@Service
public class StockHelperImpl extends BookAbstraction implements StockServiceHelper {

    public StockHelperImpl(BookRepository bookRepository) {
        super(bookRepository);
    }

    @Override
    @Transactional
    public void addBooksInStock(Long bookId, int quantity) {
        Book book = checkBook(bookId);

        if (book.quantityInStock == null)
            book.quantityInStock = quantity;
        else
            book.quantityInStock += quantity;

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void donateBooks(Long bookId, int quantity) throws NoBookInStockException {
        Book book = checkBook(bookId);

        if (book.quantityInStock == null)
            throw new NoBookInStockException(bookId, 0, quantity);

        if (book.quantityInStock < quantity)
            throw new NoBookInStockException(bookId, book.quantityInStock, quantity);

        book.quantityInStock -= quantity;
        bookRepository.save(book);
    }
}
