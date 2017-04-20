package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.exceptions.CategoryInUseException;
import mk.ukim.finki.emt.model.exceptions.IsbnLengthException;
import mk.ukim.finki.emt.model.exceptions.NegativePriceException;
import mk.ukim.finki.emt.model.exceptions.NoBookInStockException;
import mk.ukim.finki.emt.model.jpa.*;
import mk.ukim.finki.emt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author Riste Stojanov
 */
@Service
public class StoreManagementServiceImpl implements StoreManagementService {

  private final CategoryServiceHelper categoryServiceHelper;
  private final BookServiceHelper bookServiceHelper;
  private final BookDetailsServiceHelper bookDetailsServiceHelper;
  private final StockServiceHelper stockServiceHelper;
  private final DeliveryServiceHelper deliveryServiceHelper;
  private final InvoiceServerHelper invoiceServerHelper;

  @Autowired
  public StoreManagementServiceImpl(
          CategoryServiceHelper categoryServiceHelper,
          BookServiceHelper bookServiceHelper,
          BookDetailsServiceHelper bookDetailsServiceHelper,
          StockServiceHelper stockServiceHelper,
          DeliveryServiceHelper deliveryServiceHelper,
          InvoiceServerHelper invoiceServerHelper
  ) {
    this.categoryServiceHelper = categoryServiceHelper;
    this.bookServiceHelper = bookServiceHelper;
    this.bookDetailsServiceHelper = bookDetailsServiceHelper;
    this.stockServiceHelper = stockServiceHelper;
    this.deliveryServiceHelper = deliveryServiceHelper;
    this.invoiceServerHelper = invoiceServerHelper;
  }

  @Override
  public Category createTopLevelCategory(String name) {
    return categoryServiceHelper.createTopLevelCategory(name);
  }

  @Override
  public Category createCategory(String name, Long parentId) {
    return categoryServiceHelper.createCategory(name, parentId);
  }

  @Override
  public Category updateCategoryName(Long id, String newName) {
    return categoryServiceHelper.updateCategoryName(id, newName);
  }

  @Override
  public Category changeCategoryParent(Long id, Long parentId) {
    return categoryServiceHelper.changeCategoryParent(id, parentId);
  }

  @Override
  public void removeCategory(Long id) throws CategoryInUseException {
    categoryServiceHelper.removeCategory(id);
  }

  @Override
  public Book createBook(String name, Long categoryId, String[] newAuthors, Long[] existingAuthors, String isbn10, String isbn13, Double price) throws NegativePriceException, IsbnLengthException {
    return bookServiceHelper.createBook(name, categoryId, newAuthors, existingAuthors, isbn10, isbn13, price);
  }

  @Override
  public Book updateBook(Long bookId, String name, String[] authors, String isbn10, String isbn13) throws IsbnLengthException {
    return bookServiceHelper.updateBook(bookId, name, authors, isbn10, isbn13);
  }

  @Override
  public Book updateBookPrice(Long bookId, Double price) throws NegativePriceException {
    return bookServiceHelper.updateBookPrice(bookId, price);
  }

  @Override
  public Book updateBookCategory(Long bookId, Long newCategoryId) {
    return bookServiceHelper.updateBookCategory(bookId, newCategoryId);
  }

  @Override
  public void addBooksInStock(Long bookId, int quantity) {
    stockServiceHelper.addBooksInStock(bookId, quantity);
  }

  @Override
  public void donateBooks(Long bookId, int quantity) throws NoBookInStockException {
    stockServiceHelper.donateBooks(bookId, quantity);
  }

  @Override
  public void clearCart(Long cartId) {

  }

  @Override
  public void markInvoiceAsExpired(Long invoiceId) {
    invoiceServerHelper.markInvoiceAsExpired(invoiceId);
  }

  @Override
  public DeliveryPackage markInvoiceAsPayed(Long invoiceId) {
    return invoiceServerHelper.markInvoiceAsPayed(invoiceId);
  }

  @Override
  public void preparedDelivery(Long deliverId) {
    deliveryServiceHelper.preparedDelivery(deliverId);
  }

  @Override
  public void shippedDelivery(Long deliveryId) {
    deliveryServiceHelper.shippedDelivery(deliveryId);
  }

  @Override
  public void closeDeliveryWithoutConfirmation(Long deliveryId) {
    deliveryServiceHelper.closeDeliveryWithoutConfirmation(deliveryId);
  }

  @Override
  public BookPicture addBookPicture(Long bookId, byte[] bytes, String contentType) throws SQLException {
    return bookDetailsServiceHelper.addBookPicture(bookId, bytes, contentType);
  }

  @Override
  public BookDetails addBookDetails(Long bookId, String description, byte[] bytes, String contentType) throws SQLException {
    return bookDetailsServiceHelper.addBookDetails(bookId, description, bytes, contentType);
  }
}
