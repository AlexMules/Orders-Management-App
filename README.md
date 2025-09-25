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






