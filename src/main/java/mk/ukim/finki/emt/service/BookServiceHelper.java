package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.exceptions.IsbnLengthException;
import mk.ukim.finki.emt.model.exceptions.NegativePriceException;
import mk.ukim.finki.emt.model.jpa.Book;
import mk.ukim.finki.emt.model.jpa.BookDetails;
import mk.ukim.finki.emt.model.jpa.BookPicture;
import mk.ukim.finki.emt.model.jpa.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Riste Stojanov
 */
public interface BookServiceHelper {

    Book createBook(
            String name,
            Long categoryId,
            String[] authors,
            String isbn10,
            String isbn13,
            Double price
    ) throws IsbnLengthException, NegativePriceException;

    Book updateBook(
            Long bookId,
            String name,
            String[] authors,
            String isbn10,
            String isbn13
    ) throws IsbnLengthException;

    Book updateBookPrice(
            Long bookId,
            Double price
    ) throws NegativePriceException;

    Book updateBookCategory(
            Long bookId,
            Long newCategoryId
    );
}