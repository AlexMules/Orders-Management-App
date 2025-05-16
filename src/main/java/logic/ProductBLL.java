package logic;

import access.ProductDAO;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    private ProductDAO productDAO;

    public ProductBLL() {
        this.productDAO = new ProductDAO();
    }

    public List<Product> findAllProducts() {
        List<Product> products = productDAO.findAll();
        if (products == null) {
            throw new NoSuchElementException("No products were found!");
        }
        return products;
    }

    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return product;
    }

    public Product insertProduct(Product product) {
        Product productInserted = productDAO.insert(product);
        if (productInserted == null) {
            throw new NoSuchElementException("The product was not inserted!");
        }
        return productInserted;
    }

    public Product deleteProduct(Product product) {
        Product productDeleted = productDAO.delete(product);
        if (productDeleted == null) {
            throw new NoSuchElementException("The product was not deleted!");
        }
        return productDeleted;
    }

    public Product updateProductField(Product product, String fieldName, Object newValue) {
        Product productUpdated = productDAO.updateField(product, fieldName, newValue);
        if (productUpdated == null) {
            throw new NoSuchElementException("The product was not updated!");
        }
        return productUpdated;
    }
}
