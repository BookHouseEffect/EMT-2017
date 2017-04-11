package mk.ukim.finki.emt.service;

/**
 * @author CekovskiMrGjorche
 */
public interface DeliveryServiceHelper {

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
