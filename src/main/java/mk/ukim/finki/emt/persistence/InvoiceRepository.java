package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.Invoice;
import org.springframework.data.repository.CrudRepository;

/**
 * @author CekovskiMrGjorche
 */
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
