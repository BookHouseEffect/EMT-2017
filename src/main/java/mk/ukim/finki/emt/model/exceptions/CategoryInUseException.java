package mk.ukim.finki.emt.model.exceptions;


/**
 * @author Riste Stojanov
 */
public class CategoryInUseException extends Exception {

    private long categoryId;

    public CategoryInUseException(Long categoryId){
        this.categoryId = categoryId;
    }

    @Override
    public String getMessage() {
        return "Category with id " + categoryId + " is in use. Cannot be removed";
    }
}
