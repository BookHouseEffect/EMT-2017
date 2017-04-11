package mk.ukim.finki.emt.listeners;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by CekovskiMrGjorche on 24.3.2017.
 */
@Component
public class BuildSearchIndex {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void init(){
        try{
            FullTextEntityManager fullTextEntityManager =
                    Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e){
            System.out.println(
                    "An error occured trying to build the search index:"
                    + e.toString()
            );
        }
    }
}
