package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.Book;
import mk.ukim.finki.emt.model.jpa.Category;
import mk.ukim.finki.emt.persistence.BookRepository;
import mk.ukim.finki.emt.persistence.CategoryRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author CekovskiMrGjorche
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BookHelperTest {

    @Autowired
    private CategoryServiceHelper categoryServiceHelper;

    @Autowired
    private BookServiceHelper bookServiceHelper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category topLevelCategory, subCategory;
    private Book book, secondBook;

    @Before
    public void setUp() throws Exception {
        topLevelCategory = categoryServiceHelper.createTopLevelCategory("Java");
        subCategory = categoryServiceHelper.createCategory("Java Spring", topLevelCategory.id);
    }

    @After
    public void tearDown() throws Exception {
        if (book != null) bookRepository.delete(book.id);
        if (secondBook != null) bookRepository.delete(secondBook.id);
        if (subCategory != null) categoryRepository.delete(subCategory.id);
        if (topLevelCategory != null) categoryRepository.delete(topLevelCategory.id);
    }

    @Test
    public void createBook() throws Exception {
        book = bookServiceHelper.createBook("Pro Spring Boot", subCategory.id,
                new String[]{"Felipe Gutierrez"}, "1484214323", "9781484214329", 100.0);

        Assert.assertNotNull(bookRepository.findOne(book.id));

        secondBook = bookServiceHelper.createBook("Tester book", subCategory.id,
                new String[]{"Gjorche Cekovski"}, new Long[] {book.authors.get(0).id},
                "1234567890", "1234567890123", 49.99);

        Assert.assertNotNull(bookRepository.findOne(secondBook.id));
    }

    @Test
    public void updateBook() throws Exception {
        book = bookServiceHelper.createBook("Pro Spring", subCategory.id,
                new String[]{"Gjorche", "Cekovski"}, "", "", 100.0);

        String[] authors = new String[]{"Felipe Gutierrez"};
        bookServiceHelper.updateBook(book.id, "Pro Spring Boot",
                authors, "1484214323", "9781484214329");

        Book found = bookRepository.findOne(book.id);
        Assert.assertEquals("Pro Spring Boot", found.name);
        Assert.assertEquals("1484214323", found.isbn10);
        Assert.assertEquals("9781484214329", found.isbn13);
        Assert.assertEquals(authors.length, found.authors.size());
        for (int i=0; i<authors.length; i++){
            Assert.assertEquals(authors[i], found.authors.get(i).nameAndLastName);
        }
    }

    @Test
    public void updateBookPrice() throws Exception {
        book = bookServiceHelper.createBook("Pro Spring Boot", subCategory.id,
                new String[]{"Felipe Gutierrez"}, "1484214323", "9781484214329", 1000.0);

        bookServiceHelper.updateBookPrice(book.id, 100.0);

        Book found = bookRepository.findOne(book.id);
        Assert.assertEquals( new Double(100.0), found.price);
    }

    @Test
    public void updateBookCategory() throws Exception {
        book = bookServiceHelper.createBook("Pro Spring Boot", topLevelCategory.id,
                new String[]{"Felipe Gutierrez"}, "1484214323", "9781484214329", 100.0);

        bookServiceHelper.updateBookCategory(book.id, subCategory.id);

        Book found = bookRepository.findOne(book.id);
        Assert.assertEquals( subCategory, found.category);
    }

}