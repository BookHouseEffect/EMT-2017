package mk.ukim.finki.emt.service;


import mk.ukim.finki.emt.model.exceptions.CategoryInUseException;
import mk.ukim.finki.emt.model.exceptions.IsbnLengthException;
import mk.ukim.finki.emt.model.exceptions.NegativePriceException;
import mk.ukim.finki.emt.model.exceptions.NoBookInStockException;
import mk.ukim.finki.emt.model.jpa.*;

import java.sql.SQLException;

/**
 * @author Riste Stojanov
 */
public interface StoreManagementService {

  Category createTopLevelCategory(
          String name
  );

  Category createCategory(
    String name,
    Long parentId
  );

  Category updateCategoryName(
    Long id,
    String newName
  );

  Category changeCategoryParent(
    Long id,
    Long parentId
  );

  void removeCategory(Long id)
    throws CategoryInUseException;

  Book createBook(
    String name,
    Long categoryId,
    String[] newAuthors,
    Long[] existingAuthors,
    String isbn10,
    String isbn13,
    Double price
  ) throws NegativePriceException, IsbnLengthException;

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

  BookPicture addBookPicture(
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


  void addBooksInStock(
    Long bookId,
    int quantity
  );

  void donateBooks(
    Long bookId,
    int quantity
  ) throws NoBookInStockException;

  void clearCart(
    Long cartId
  );

  void markInvoiceAsExpired(
    Long invoiceId
  );

  DeliveryPackage markInvoiceAsPayed(
    Long invoiceId
  );

  void preparedDelivery(
    Long deliverId
  );

  void shippedDelivery(
    Long deliveryId
  );

  void closeDeliveryWithoutConfirmation(
    Long deliveryId
  );
}
