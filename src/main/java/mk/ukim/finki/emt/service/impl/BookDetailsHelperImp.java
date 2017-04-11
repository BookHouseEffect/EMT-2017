package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.BookDetails;
import mk.ukim.finki.emt.model.jpa.BookPicture;
import mk.ukim.finki.emt.model.jpa.FileEmbeddable;
import mk.ukim.finki.emt.persistence.BookDetailsRepository;
import mk.ukim.finki.emt.persistence.BookPictureRepository;
import mk.ukim.finki.emt.persistence.BookRepository;
import mk.ukim.finki.emt.service.BookDetailsServiceHelper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;

/**
 * @author CekovskiMrGjorche
 */
@Service
public class BookDetailsHelperImp extends BookAbstraction implements BookDetailsServiceHelper {

    private BookPictureRepository bookPictureRepository;
    private BookDetailsRepository bookDetailsRepository;

    @Autowired
    public BookDetailsHelperImp(
            BookPictureRepository bookPictureRepository,
            BookDetailsRepository bookDetailsRepository,
            BookRepository bookRepository) {
        super(bookRepository);
        this.bookPictureRepository = bookPictureRepository;
        this.bookDetailsRepository = bookDetailsRepository;
    }

    @Override
    public BookPicture addBookPicture(Long bookId, byte[] bytes, String contentType) throws SQLException {
        BookPicture bookPicture = new BookPicture();
        bookPicture.book = checkBook(bookId);
        bookPicture.picture = createFileEmbeddable(bytes, contentType, bookPicture.book.name);

        return bookPictureRepository.save(bookPicture);
    }

    @Override
    public BookPicture getBookPicture(Long bookId) {
        return bookPictureRepository.findByBookId(bookId);
    }

    @Override
    public BookPicture updateBookPicture(Long bookId, byte[] bytes, String contentType) throws SQLException {
        BookPicture bookPicture = getExistingBookPicture(bookId);
        bookPicture.picture = createFileEmbeddable(bytes, contentType, bookPicture.book.name);

        return bookPictureRepository.save(bookPicture);
    }

    @Override
    public BookDetails addBookDetails(Long bookId, String description, byte[] bytes, String contentType) throws SQLException {
        BookDetails bookDetails = new BookDetails();
        bookDetails.book = checkBook(bookId);
        bookDetails.description = description;
        bookDetails.downloadFile = createFileEmbeddable(bytes, contentType, bookDetails.book.name);

        return bookDetailsRepository.save(bookDetails);
    }

    @Override
    public BookDetails getBookDetails(Long bookId) {
        return bookDetailsRepository.findByBookId(bookId);
    }

    @Override
    public BookDetails updateDescriptionDetails(Long bookId, String description) {
        BookDetails bookDetails = getExistingBookDetails(bookId);
        bookDetails.description = description;

        return bookDetailsRepository.save(bookDetails);
    }

    @Override
    public BookDetails updateDownloadFile(Long bookId, byte[] bytes, String contentType) throws SQLException {
        BookDetails bookDetails = getExistingBookDetails(bookId);
        bookDetails.downloadFile = createFileEmbeddable(bytes, contentType, bookDetails.book.name);

        return bookDetailsRepository.save(bookDetails);
    }

    private BookPicture getExistingBookPicture(long bookId){
        BookPicture bookPicture = bookPictureRepository.findByBookId(bookId);
        if (bookPicture == null)
            throw new ObjectNotFoundException(bookId, "BookPicture");
        return bookPicture;
    }

    private BookDetails getExistingBookDetails(long bookId){
        BookDetails bookDetails = bookDetailsRepository.findByBookId(bookId);
        if (bookDetails == null)
            throw new ObjectNotFoundException(bookId, "BookDetails");
        return bookDetails;
    }

    private FileEmbeddable createFileEmbeddable(byte[] bytes, String contentType, String filename) throws SQLException {
        FileEmbeddable fileEmbeddable = new FileEmbeddable();
        fileEmbeddable.contentType = contentType;
        fileEmbeddable.data = new SerialBlob(bytes);
        fileEmbeddable.size = bytes.length;
        fileEmbeddable.fileName = filename;
        return fileEmbeddable;
    }
}
