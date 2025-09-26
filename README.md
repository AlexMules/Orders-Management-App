# Orders Management App 📦
## Description
The application manages client orders in a warehouse and follows the layered architecture pattern (model, business logic, presentation, data access). A relational 
database is used to store products, clients, and orders.

The graphical user interface is implemented using the Swing API, through which the user can:
* Add, edit, delete, and view clients
* Add, edit, delete, and view products
* Place orders: the user can select an existing product, choose an existing client, and specify the desired quantity to create a valid order. In case there are not
enough products, an under-stock message is displayed. After the order is finalized, the product stock is decremented and a bill is generated.
* View bills
<br><br>

## 🛠️ Tools and Technologies
* MySQL Workbench – used to create the database and populate initial data
* IntelliJ IDEA – Java development environment
* MySQL Connector/J – a JDBC (Java Database Connectivity) driver provided by Oracle that allows Java applications to connect to and interact with MySQL databases. It facilitates executing SQL queries, manipulating data, and managing connections between the Java application and the MySQL server
* JavaDoc - used for generating project documentation
* Maven - used for dependency management
<br><br>

## Class diagram
<div align="center">
  <img width="850" src="https://github.com/user-attachments/assets/ce088ff2-27b2-4f01-8521-d68e50af0667" />
</div><br>

The **`Client`**, **`Order`**, **`Product`**, and **`Bill`** classes form the data model of the application. The **`Bill`** class is immutable, being defined using  `Java records`. A **`Bill`** object is generated for each order and stored in the **log** table in the database. Bills can only be inserted and read from the **log** table, with no updates allowed.

**`AbstractDAO`** is a generic **Data Access Object (DAO)** base class that provides common **`CRUD`** operations for any domain entity. It uses `reflection` and `introspection` to map fields to table columns, enabling data to be easily displayed in the graphical user interface. `Reflection` techniques are used to implement
the methods for accesing the database tables (except **log**): create, edit, delete and find object. In addition, the `SQL queries` for accessing the database are generated dynamically through `reflection`, based on the object–table mapping. The **`ClientDAO`**, **`ProductDAO`**, and **`OrderDAO`** classes extend **`AbstractDAO`**, using its methods to manage clients, products, and orders. The **`BillDAO`** class contains all operations related to bills.

The business logic layer consists of **`ClientBLL`**, **`ProductBLL`**, **`OrderBLL`**, and **`BillBLL`**. These classes coordinate operations on their respective entities through the corresponding **DAO** classes. **`ClientBLL`** and **`ProductBLL`** support full CRUD operations, while **`OrderBLL`** and **`BillBLL`** are append-only, allowing only creation and retrieval of records.






