package mk.ukim.finki.emt.service.impl;


import mk.ukim.finki.emt.model.enums.DeliveryStatus;
import mk.ukim.finki.emt.model.jpa.DeliveryPackage;
import mk.ukim.finki.emt.persistence.DeliveryPackageRepository;
import mk.ukim.finki.emt.service.DeliveryServiceHelper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CekovskiMrGjorche
 */
@Service
public class DeliveryServiceImpl implements DeliveryServiceHelper {

    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryPackageRepository deliveryPackageRepository) {
        this.deliveryPackageRepository = deliveryPackageRepository;
    }

    @Override
    public void preparedDelivery(Long deliveryId) {
        DeliveryPackage deliveryPackage = getDeliveryPackage(deliveryId);
        deliveryPackage.status = DeliveryStatus.PACKAGE_READY_FOR_SHIPMENT;
        deliveryPackageRepository.save(deliveryPackage);
    }

    @Override
    public void shippedDelivery(Long deliveryId) {
        DeliveryPackage deliveryPackage = getDeliveryPackage(deliveryId);
        deliveryPackage.status = DeliveryStatus.SHIPMENT_IN_PROGRESS;
        deliveryPackageRepository.save(deliveryPackage);
    }

    @Override
    public void closeDeliveryWithoutConfirmation(Long deliveryId) {
        DeliveryPackage deliveryPackage = getDeliveryPackage(deliveryId);
        deliveryPackage.status = DeliveryStatus.CLOSED_UNCONFIRMED_DELIVERY;
        deliveryPackageRepository.save(deliveryPackage);
    }

    private DeliveryPackage getDeliveryPackage(Long deliveryId){
        DeliveryPackage deliveryPackage = deliveryPackageRepository.findOne(deliveryId);
        if (deliveryPackage == null)
            throw new ObjectNotFoundException(deliveryId, "DeliveryPackage");
        return deliveryPackage;
    }
}
