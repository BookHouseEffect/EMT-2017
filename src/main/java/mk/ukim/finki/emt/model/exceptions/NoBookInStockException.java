package mk.ukim.finki.emt.model.exceptions;

/**
 * @author CekovskiMrGjorche
 */
public class NoBookInStockException extends Exception {

    private Integer contains, askedFor;
    private Long bookId;

    public NoBookInStockException(Long bookId, Integer contains, Integer askedFor) {
        this.bookId = bookId;
        this.contains = contains;
        this.askedFor = askedFor;
    }

    @Override
    public String getMessage() {
        return "The store contains " + contains + " book with id " + bookId
                + ", but it was asked for " + askedFor + " book to get.";
    }
}
