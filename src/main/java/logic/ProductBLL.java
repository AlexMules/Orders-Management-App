package logic;

import access.ProductDAO;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business-Logic Layer for {@link Product} entities.
 */
public class ProductBLL {

    private ProductDAO productDAO;

    public ProductBLL() {
        this.productDAO = new ProductDAO();
    }

    /**
     * Retrieves all products from the database.
     * @return a list of products
     */
    public List<Product> findAllProducts() {
        List<Product> products = productDAO.findAll();
        if (products == null) {
            throw new NoSuchElementException("No products were found!");
        }
        return products;
    }

    /**
     * Retrieves the product with the given ID.
     * @param id the product's id
     * @return the matching {@link Product}
     * @throws NoSuchElementException if not found
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return product;
    }

    /**
     * Inserts a new product into the database.
     *
     * @param name the product's name
     * @param price the product's price
     * @param quantity the product's quantity
     * @throws NoSuchElementException if insertion failed
     */
    public void insertProduct(String name, double price, int quantity) {
        Product product = new Product(name, price, quantity);
        Product productInserted = productDAO.insert(product);
        if (productInserted == null) {
            throw new NoSuchElementException("The product was not inserted!");
        }
    }

    /**
     * Deletes the given product from the database.
     * @param product the product to delete
     * @return the deleted {@link Product}
     * @throws NoSuchElementException if deletion failed
     */
    public Product deleteProduct(Product product) {
        Product productDeleted = productDAO.delete(product);
        if (productDeleted == null) {
            throw new NoSuchElementException("The product was not deleted!");
        }
        return productDeleted;
    }

    /**
     * Updates a single field on an existing product.
     *
     * @param product   the product to update
     * @param fieldName the name of the field/column to change
     * @param newValue  the new value for that field
     * @throws NoSuchElementException   if the update failed
     * @throws IllegalArgumentException if the field name is invalid
     */
    public void updateProductField(Product product, String fieldName, Object newValue) {
        Product productUpdated = productDAO.updateField(product, fieldName, newValue);
        if (productUpdated == null) {
            throw new NoSuchElementException("The product was not updated!");
        }
    }
}
