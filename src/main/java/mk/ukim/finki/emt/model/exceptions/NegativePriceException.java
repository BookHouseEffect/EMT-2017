package mk.ukim.finki.emt.model.exceptions;

/**
 * @author CekovskiMrGjorche
 */
public class NegativePriceException extends Exception {

    private double priceReceived;

    public NegativePriceException(double priceReceived){
        this.priceReceived = priceReceived;
    }

    @Override
    public String getMessage() {
        return "The received price is negative, but is expected to be positive. Price = " + priceReceived;
    }
}
