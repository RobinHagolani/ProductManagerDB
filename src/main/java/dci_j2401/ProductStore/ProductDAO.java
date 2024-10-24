package dci_j2401.ProductStore;

import java.util.List;

public interface ProductDAO {

    void addProduct(Product product) throws DuplicateProductException;;
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProduct(Long id);
    void updateProduct(Product product);

}
