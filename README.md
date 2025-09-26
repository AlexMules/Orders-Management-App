# Orders Management App 📦
## 📓 Description
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

**`ConnectionFactory`** is a **singleton** class that manages the database connection. It provides a centralized way to obtain a **`Connection`** to the MySQL database and utility methods to safely close **`Connection`**, **`Statement`**, and **`ResultSet`** objects, ensuring proper resource management and logging any errors encountered.<br><br>

## GUI
<div align="center">
   <img width="600" height="400" alt="image" src="https://github.com/user-attachments/assets/fc758b31-c37b-43a7-8f1b-efc79c32879a" />
  <br><br>
   <img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/afe593c2-7f11-4127-bc93-4a97001b9598" />
  <br><br>
   <img width="677" height="377" alt="image" src="https://github.com/user-attachments/assets/78f9da87-adb9-4ee7-ac43-796a930c5900" />
  <br><br>
   <img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/f37992af-6a0d-4b5f-a2fb-97d834eae187" />
  <br><br>
   <img width="677" height="377" alt="image" src="https://github.com/user-attachments/assets/f7063205-f5e2-4a23-96e8-13e97901abe9" />
  <br><br>
   <img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/a1451608-0d78-47ec-9fd0-433078db624b" />
  <br><br>
   <img width="750" height="123" alt="image" src="https://github.com/user-attachments/assets/04aa1086-be4d-4d77-a8ca-5c5f84fcb0eb" />
  <br><br>
   <img width="506" height="140" alt="image" src="https://github.com/user-attachments/assets/76f87169-75dc-4378-9dce-1eef049cce6a" />
   <br><br>
   <img width="677" height="377" alt="image" src="https://github.com/user-attachments/assets/757a0b59-7411-45a7-97a0-e93e28f496f3" />
  <br><br>
   <img width="677" height="377" alt="image" src="https://github.com/user-attachments/assets/d3e3c229-fd12-43c5-89bb-d887e6be3882" />

</div>




