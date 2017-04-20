package mk.ukim.finki.emt.persistence;

import java.util.List;

/**
 * @author CekovskiMrGjorche
 */
public interface SearchRepository {

    <T> List<T> searchKeyword(Class<T> entityClass, String text, String... fields);

    <T> List<T> searchPhrase(Class<T> entityClass, String text, String... fields);
}
