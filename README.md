# Bookworm Library - Readme

<img src="./images/Bookworm Library.png"  width="60%" height="350px"/>

## Overview
The Bookworm Library is a software application designed to efficiently manage the operations of a library. It provides a platform for librarians and students to interact with the library's resources, perform administrative tasks, and facilitate book rentals and feedback.

## Features
### Librarian Features
1. **Login and Signup**: Librarians can log in using their registered credentials or sign up by providing necessary information.
2. **Add New Book**: Librarians can add new books to the library system, specifying details like book title, author, genre, and availability.
3. **Update Book Information**: Librarians can update book details, including availability and other relevant information.
4. **Remove Book**: Librarians can remove books from the library system.
5. **View Student Rentals**: Librarians can view the status of student rentals, including rental details and return dates.
6. **View Feedback and Ratings**: Librarians can view feedback and ratings provided by students for books.

### Student Features
1. **Login and Signup**: Students can log in using their registered credentials or sign up by providing necessary information.
2. **View Available Books**: Students can view the list of available books in the library, including book details and availability status.
3. **Search and Browse Books**: Students can apply filters and sorting options to search and browse books based on genre, author, and availability.
4. **Rent a Book**: Students can rent a book by selecting the desired book and providing necessary information. Books are rented for 7 days.
5. **Return a Book**: Students can return the rented book within 7 days from the rental date. Overdue books are notified.
6. **Provide Feedback and Ratings**: Students can provide feedback and ratings for books they have rented.

## Database Schema
The application uses MySql database management system to store information related to librarians, students, books, rentals, feedback, and ratings. The database schema maintains relationships and constraints between tables, ensuring data integrity.

## Security
Sensitive information, such as passwords, is securely stored using appropriate encryption techniques to ensure data security.

## Technologies Used
- Java programming language
- Spring Tool Suite
- Jakarta Persistence API (JPA) for database interactions
- Maven Tool
- MySQL database management system

## How to Run the Application
1. Clone the repository to your local machine.
2. Set up the necessary database and configure the database connection in the `persistence.xml` file.
3. Compile and run the `App.java` file to start the application.
4. Follow the prompts to interact with the Library Management System.

## Contributors
- [Umesh Kumar Gupta](https://github.com/Umesh8878) - Role: Java Backend Developer.

## Feedback and Support
If you encounter any issues, have suggestions for improvements, or need support, please open an issue in the repository or contact us at [umeshgupta78361@gmail.com](mailto:umeshgupta78361@gmail.com).

Thank you for using the Bookworm Library System!