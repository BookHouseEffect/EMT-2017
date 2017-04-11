package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Riste Stojanov
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findByParentIsNull();

    List<Category> findByParentId(Long categoryId);

}