#Overview
This GitHub repository houses a bookstore application. The API can be tested using Postman.


Running the Application
To utilize this application, follow these instructions:
Execute the ApplicationContainer.java file located in the server package.


Database Setup
You can create the necessary database by executing the following SQL query:
      CREATE DATABASE bookstore;
      USE bookstore;
      
      CREATE TABLE books (
        id INT AUTO_INCREMENT PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        isbn BIGINT NOT NULL,
        author VARCHAR(255) NOT NULL
      );


Authentication
For authentication, use the following credentials:
Username: guest@cookbook.de
Password: guest



Sample API Endpoints
Retrieve a List of All Books:
    Endpoint: http://localhost:8001/services/books/
    Description: Retrieve a list of all books available in the bookstore.

Retrieve Information about a Specific Book by ID:
    Endpoint: http://localhost:8001/services/books/1
    Description: Retrieve detailed information about a specific book identified by ID 1.
