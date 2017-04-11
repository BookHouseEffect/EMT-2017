package mk.ukim.finki.emt.model.jpa;

import javax.persistence.*;

/**
 * @author CekovskiMrGjorche
 */
@Entity
@Table(name="book_pictures")
public class BookPicture extends BaseEntity {

    @Embedded
    public FileEmbeddable picture;

    @ManyToOne
    public Book book;
}
