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




