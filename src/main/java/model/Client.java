package model;

/**
 * <p> Represents a client in the database, with a unique identifier, a name, an address, and an email. </p>
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;

    public Client() {
    }

    public Client(String name, String address, String email) {
        super();
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getId() + ". " + getName();
    }

}
