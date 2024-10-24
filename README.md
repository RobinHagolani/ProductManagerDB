
# Product Management Web Application

This is a simple Product Management web application built using **Spring Boot**, **Thymeleaf**, **Bulma CSS**, and **MySQL**. It allows users to add, view, edit, and delete products in a product store (e.g., a Toy Store). The project follows the standard MVC architecture using Spring Boot with Thymeleaf for server-side rendering.

## Features
1. **Add Products**: Users can add new products with fields like name, description, stock, and price.
2. **List Products**: Displays a list of all products in the store with essential details.
3. **Delete Products**: Users can delete specific products from the database via a delete link in the product list.
4. **Edit Products**: Users can edit existing products through a pre-filled form, allowing updates to the product's name, description, stock, and price.
5. **Responsive UI**: The application uses **Bulma CSS** to provide a clean, responsive user interface.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Setup](#project-setup)
- [Database Configuration](#database-configuration)
- [How to Use](#how-to-use)
- [Bonus Feature](#bonus-feature)

## Technologies Used
- **Spring Boot**: Backend framework for Java web applications.
- **Thymeleaf**: Templating engine used for rendering HTML pages.
- **Bulma CSS**: A CSS framework used to style the frontend of the application.
- **MySQL**: Database for storing product data.
- **Maven**: Dependency management and build tool.

## Project Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/product-management-app.git
cd product-management-app
```

### 2. Configure the MySQL Database
Create a new MySQL database for the project (for example: `product_db`), and configure the connection in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/product_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the Application
You can run the application using Maven:
```bash
mvn spring-boot:run
```

Alternatively, you can package the application and run the JAR file:
```bash
mvn package
java -jar target/product-management-app.jar
```

### 4. Access the Application
Once the application is running, you can access it in your browser at:
```
http://localhost:8080
```

## Database Configuration

The project uses a single table to store product data with the following schema:
```sql
CREATE TABLE products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  stock INT,
  price DECIMAL(10, 2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## How to Use

### 1. Add a New Product
Navigate to the **Add Product** page from the navbar. Fill out the form with product details (name, description, stock, and price) and submit the form. The product will be added to the database.

### 2. View Product List
The **Product List** page displays all products currently in the database. It shows important information like name, description, stock, price, created date, and modified date.

### 3. Delete a Product
In the **Product List** page, each product has a **Delete** link. Clicking this link will delete the product from the database.

### 4. Edit a Product (Bonus Feature)
In the **Product List** page, each product has an **Edit** link. Clicking this link takes you to a pre-filled form where you can modify the product's details (name, description, stock, price) and save the changes. The product ID and timestamps (created/modified date) cannot be edited.

## Bonus Feature
The **Edit Product** functionality allows users to update an existing product. The form is pre-filled with the current product details, and users can update fields like name, description, stock, and price.

## Example Screenshots
- Add Product Page![addNewProduct.png](screenshots%2FaddNewProduct.png)
- Product List with Edit and Delete Links![index.png](screenshots%2Findex.png)
- Edit Product Page![editProduct.png](screenshots%2FeditProduct.png)

