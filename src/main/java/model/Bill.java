package model;

/**
 * <p> Represents a Bill in the `log` table of the database, with a unique identifier, identifier of the
 * corresponding order, the name of the client, the name of the product, the ordered quantity and the total price of
 * the order. </p>
 */
public record Bill(int id, int idOrder, String clientName, String productName, int quantity, double price){}
