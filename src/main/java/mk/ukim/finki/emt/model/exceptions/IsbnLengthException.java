package mk.ukim.finki.emt.model.exceptions;

import mk.ukim.finki.emt.model.enums.IsbnLength;

/**
 * Created by CekovskiMrGjorche on 10.4.2017.
 */
public class IsbnLengthException extends Exception {

    private int ExpectedValue;
    private int ReceivedValue;

    public IsbnLengthException(IsbnLength expectedValue, int receivedValue){
        switch (expectedValue){
            case TEN: this.ExpectedValue = 10; break;
            case THIRTEEN: this.ExpectedValue = 13; break;
        }

        this.ReceivedValue = receivedValue;
    }

    @Override
    public String getMessage() {
        return "Expected isbn number length is "
                + ExpectedValue + " but received length is " + ReceivedValue + ".";
    }
}

