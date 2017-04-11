package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.BookDetails;
import org.springframework.data.repository.CrudRepository;

/**
 * @author CekovskiMrGjorche
 */
public interface BookDetailsRepository extends CrudRepository<BookDetails, Long> {

    BookDetails findByBookId(Long id);
}
