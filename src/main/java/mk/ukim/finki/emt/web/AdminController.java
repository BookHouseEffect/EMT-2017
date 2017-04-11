package mk.ukim.finki.emt.web;


import mk.ukim.finki.emt.model.exceptions.IsbnLengthException;
import mk.ukim.finki.emt.model.exceptions.NegativePriceException;
import mk.ukim.finki.emt.model.jpa.Book;
import mk.ukim.finki.emt.model.jpa.Category;
import mk.ukim.finki.emt.service.StoreManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author CekovskiMrGjorche
 */
@Controller
public class AdminController {

    private StoreManagementService storeManagementService;

    @Autowired
    public AdminController(StoreManagementService storeManagementService) {
        this.storeManagementService = storeManagementService;
    }

    @RequestMapping(value = {"/admin/category"}, method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("pageFragment", "addCategory");
        return "index";
    }

    @RequestMapping(value = {"/admin/book"}, method = RequestMethod.GET)
    public String addBook(Model model) {
        model.addAttribute("pageFragment", "addBook");
        return "index";
    }


    @RequestMapping(value = {"/admin/category"}, method = RequestMethod.POST)
    public String createCategory (@RequestParam String categoryName) {
        storeManagementService.createTopLevelCategory(categoryName);
        return "redirect:/admin/category";
    }


    @RequestMapping(value = {"/admin/book"}, method = RequestMethod.POST)
    public String createBook (Model model,
                              @RequestParam String name,
                              @RequestParam Long categoryId,
                              @RequestParam String authors,
                              @RequestParam String isbn10,
                              @RequestParam String isbn13,
                              @RequestParam Double price,
                              @RequestParam String description,
                              MultipartFile picture)
            throws IOException, SQLException, IsbnLengthException, NegativePriceException {

        Book product = storeManagementService.createBook(
                name,
                categoryId,
                authors.split(";"),
                isbn10,
                isbn13,
                price
        );
        storeManagementService.addBookPicture(product.id, picture.getBytes(), picture.getContentType());

        /*
        * TODO: add description and file
        * */

        model.addAttribute("product", product);
        return "index";
    }

}
