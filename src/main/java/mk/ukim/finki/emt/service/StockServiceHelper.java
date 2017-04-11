package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.exceptions.NoBookInStockException;

/**
 * @author CekovskiMrGjorche
 */
public interface StockServiceHelper {
    void addBooksInStock(
            Long bookId,
            int quantity
    );

    void donateBooks(
            Long bookId,
            int quantity
    ) throws NoBookInStockException;
}
