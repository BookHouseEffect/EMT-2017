package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.BookDetails;
import mk.ukim.finki.emt.model.jpa.BookPicture;

import java.sql.SQLException;

/**
 * @author CekovskiMrGjorche
 */
public interface BookDetailsServiceHelper {

    BookPicture addBookPicture(
            Long bookId,
            byte[] bytes,
            String contentType
    ) throws SQLException;

    BookPicture getBookPicture(
            Long bookId
    );

    BookPicture updateBookPicture(
            Long bookId,
            byte[] bytes,
            String contentType
    ) throws SQLException;

    BookDetails addBookDetails(
            Long bookId,
            String description,
            byte[] bytes,
            String contentType
    ) throws SQLException;

    BookDetails getBookDetails(
            Long bookId
    );

    BookDetails updateDescriptionDetails(
            Long bookId,
            String description
    );

    BookDetails updateDownloadFile(
            Long bookId,
            byte[] bytes,
            String contentType
    ) throws SQLException;
}
