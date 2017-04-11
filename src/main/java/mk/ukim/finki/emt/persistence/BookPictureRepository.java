package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.BookPicture;
import org.springframework.data.repository.CrudRepository;

/**
 * @author CekovskiMrGjorche
 */
public interface BookPictureRepository extends CrudRepository<BookPicture, Long> {

    BookPicture findByBookId(Long id);
}
