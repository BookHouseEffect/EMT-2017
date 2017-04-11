package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.Book;
import mk.ukim.finki.emt.model.jpa.BookDetails;
import mk.ukim.finki.emt.model.jpa.BookPicture;
import mk.ukim.finki.emt.model.jpa.Category;
import mk.ukim.finki.emt.persistence.BookDetailsRepository;
import mk.ukim.finki.emt.persistence.BookPictureRepository;
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
@ActiveProfiles("dev")
public class BookDetailsHelperTest {

    @Autowired private CategoryServiceHelper categoryServiceHelper;
    @Autowired private BookServiceHelper bookServiceHelper;
    @Autowired private BookDetailsServiceHelper bookDetailsServiceHelper;

    @Autowired private BookRepository bookRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private BookDetailsRepository bookDetailsRepository;
    @Autowired private BookPictureRepository bookPictureRepository;

    private Category category;
    private Book book;
    private BookDetails bookDetails;
    private BookPicture bookPicture;

    private final byte[] file = new byte[]{(byte)0xff, (byte)0x80, (byte)0x00};

    @Before
    public void setUp() throws Exception {
        category = categoryServiceHelper.createTopLevelCategory("Java");
        book = bookServiceHelper.createBook("Pro Spring Boot", category.id,
                new String[]{"Felipe Gutierrez"}, "1484214323", "9781484214329", 100.0);
    }

    @After
    public void tearDown() throws Exception {
        if (bookDetails != null) bookDetailsRepository.delete(bookDetails.id);
        if (bookPicture != null) bookPictureRepository.delete(bookPicture.id);
        if (book != null) bookRepository.delete(book.id);
        if (category != null) categoryRepository.delete(category.id);
    }

    @Test
    public void addBookDetails() throws Exception {
        bookDetails = bookDetailsServiceHelper.addBookDetails(
                book.id, "The is book for Java Spring Boot", file, "pdf");

        Assert.assertNotNull(bookDetails);
    }

    @Test
    public void updateDescriptionDetails() throws Exception {
        bookDetails = bookDetailsServiceHelper.addBookDetails(
                book.id, "No description available.", file, "pdf");

        String description = "The is book for Java Spring Boot";
        bookDetailsServiceHelper.updateDescriptionDetails(book.id, description);

        BookDetails found = bookDetailsRepository.findByBookId(book.id);
        Assert.assertEquals(description, found.description);
    }

    @Test
    public void updateDownloadFile() throws Exception {
        bookDetails = bookDetailsServiceHelper.addBookDetails(
                book.id, "The is book for Java Spring Boot", file, "pdf");

        byte[] newByte = new byte[]{(byte)50,(byte)100, (byte)150, (byte)200 };
        bookDetailsServiceHelper.updateDownloadFile(book.id, newByte, "xps");

        BookDetails found = bookDetailsRepository.findByBookId(book.id);
        Assert.assertEquals("xps", found.downloadFile.contentType);
        Assert.assertEquals(newByte.length, found.downloadFile.size);
    }

    @Test
    public void addBookPicture() throws Exception {
        bookPicture = bookDetailsServiceHelper.addBookPicture(book.id, file, ".jpg");

        Assert.assertNotNull(bookPicture);
    }

    @Test
    public void updateBookPicture() throws Exception {
        bookPicture = bookDetailsServiceHelper.addBookPicture(book.id, file, ".jpg");

        byte[] newByte = new byte[]{(byte)50,(byte)100, (byte)150, (byte)200 };
        bookDetailsServiceHelper.updateBookPicture(book.id, newByte, "png");

        BookPicture found = bookPictureRepository.findByBookId(book.id);
        Assert.assertEquals("png", found.picture.contentType);
        Assert.assertEquals(newByte.length, found.picture.size);

    }



}