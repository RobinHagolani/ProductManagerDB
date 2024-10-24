package dci_j2401.ProductStore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {

    private final DBConnectionManager dbConnectionManager = new MySqlConnectionManager();
    private final ProductDAO productDAO = new ProductDAOImpl(dbConnectionManager);


    @GetMapping("/")
    public String home(){
        return "redirect:/product/list";
    }

    @GetMapping("/product/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());  // Adding an empty Product object
        return "addProductForm";  // Name of the Thymeleaf template (without .html extension)
    }

    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute("product") Product product, Model model) {
        try {
            productDAO.addProduct(product);
            // Add a success message to the model
            model.addAttribute("successMessage", "Product '" + product.getName() + "' with price " + product.getPrice() + " has been added successfully.");
            model.addAttribute("product", new Product());  // Reset the form fields for a new product
            return "addProductForm";  // Stay on the form page
        } catch (DuplicateProductException e) {
            model.addAttribute("errorMessage", e.getMessage());  // Add error message to the model
            return "addProductForm";  // Stay on the form page
        }
    }

    @GetMapping("/product/list")
    public String listProducts(Model model){
        List<Product> products = productDAO.getAllProducts();
        model.addAttribute("products", products);
        return "productList";

    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productDAO.deleteProduct(id);
        return "redirect:/product/list";  // Redirect back to the product list after deletion
    }

    @GetMapping("/product/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Product product = productDAO.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "editProduct";
        } else {
            model.addAttribute("errorMessage", "Product not found");
            return "redirect:/product/list";
        }
    }

    @PostMapping("/product/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        product.setId(id);
        productDAO.updateProduct(product);
        return "redirect:/product/list"; // Redirect to product list after updating
    }


}
