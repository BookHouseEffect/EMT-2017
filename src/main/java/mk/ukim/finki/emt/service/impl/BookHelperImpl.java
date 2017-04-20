package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.enums.IsbnLength;
import mk.ukim.finki.emt.model.exceptions.IsbnLengthException;
import mk.ukim.finki.emt.model.exceptions.NegativePriceException;
import mk.ukim.finki.emt.model.jpa.Author;
import mk.ukim.finki.emt.model.jpa.Book;
import mk.ukim.finki.emt.model.jpa.Category;
import mk.ukim.finki.emt.persistence.AuthorsRepository;
import mk.ukim.finki.emt.persistence.BookRepository;
import mk.ukim.finki.emt.persistence.CategoryRepository;
import mk.ukim.finki.emt.service.BookServiceHelper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Riste Stojanov
 */
@Service
public class BookHelperImpl extends BookAbstraction implements BookServiceHelper {

    private CategoryRepository categoryRepository;
    private AuthorsRepository authorsRepository;

    @Autowired
    public BookHelperImpl(
            CategoryRepository categoryRepository,
            BookRepository bookRepository,
            AuthorsRepository authorsRepository) {
        super(bookRepository);
        this.categoryRepository = categoryRepository;
        this.authorsRepository = authorsRepository;
    }

    @Override
    public Book createBook(String name, Long categoryId, String[] authors, String isbn10, String isbn13, Double price) throws IsbnLengthException, NegativePriceException {
        Book book = createBookWithNewAuthors(name, categoryId, authors, isbn10, isbn13, price);
        return bookRepository.save(book);
    }

    @Override
    public Book createBook(String name, Long categoryId, String[] authors, Long[] existingAuthors, String isbn10, String isbn13, Double price) throws NegativePriceException, IsbnLengthException {
        Book book = createBookWithNewAuthors(name, categoryId, authors, isbn10, isbn13, price);
        for (Long authorId : existingAuthors) {
            book.authors.add(checkAuthor(authorId));
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, String name, String[] authors, String isbn10, String isbn13) throws IsbnLengthException {
        Book book = checkBook(bookId);

        book.name = name;
        book.isbn10 = checkIsbn(isbn10, IsbnLength.TEN);
        book.isbn13 = checkIsbn(isbn13, IsbnLength.THIRTEEN);

        List<Author> authorsList = new ArrayList<>();
        for (String authorName: authors){
            Author author = createAuthor(authorName);
            authorsList.add(author);
        }
        book.authors = authorsList;

        return bookRepository.save(book);
    }

    @Override
    public Book updateBookPrice(Long bookId, Double price) throws NegativePriceException {
        Book book = checkBook(bookId);
        book.price = checkPrice(price);

        return bookRepository.save(book);
    }

    @Override
    public Book updateBookCategory(Long bookId, Long newCategoryId) {
        Book book = checkBook(bookId);
        book.category = checkCategory(newCategoryId);

        return bookRepository.save(book);
    }

    private Author checkAuthor(Long authorId){
        Author author = authorsRepository.findOne(authorId);
        if (author == null)
            throw new ObjectNotFoundException(authorId, "Author");
        return author;
    }

    private Author createAuthor(String authorName) {
        Author author = new Author();
        author.nameAndLastName = authorName;
        author = authorsRepository.save(author);
        return author;
    }

    private String checkIsbn(String isbn, IsbnLength type) throws IsbnLengthException {
        switch (type){
            case TEN:
                if (isbn.length() != 10 && isbn.length() != 0) throw new IsbnLengthException(type, isbn.length());
                break;
            case THIRTEEN:
                if (isbn.length() != 13 && isbn.length() != 0) throw new IsbnLengthException(type, isbn.length());
        }

        return isbn;
    }

    private double checkPrice(double price) throws NegativePriceException {
        if (price < 0)
            throw new NegativePriceException(price);
        return price;
    }

    private Category checkCategory(long categoryId){
        Category category = categoryRepository.findOne(categoryId);
        if (category == null)
            throw new ObjectNotFoundException(categoryId, "Category");
        return category;
    }

    private Book createBookWithNewAuthors(String name, Long categoryId, String[] authors, String isbn10, String isbn13, Double price) throws IsbnLengthException, NegativePriceException {
        Book book = new Book();

        book.name = name;
        book.isbn10 = checkIsbn(isbn10, IsbnLength.TEN);
        book.isbn13 = checkIsbn(isbn13, IsbnLength.THIRTEEN);
        book.price = checkPrice(price);
        book.category = checkCategory(categoryId);

        for (String authorName : authors){
            Author author = createAuthor(authorName);
            book.authors.add(author);
        }

        return book;
    }
}