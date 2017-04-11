package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.DeliveryPackage;
import org.springframework.data.repository.CrudRepository;

/**
 * @author CekovskiMrGjorche
 */
public interface DeliveryPackageRepository extends CrudRepository<DeliveryPackage, Long> {
}
