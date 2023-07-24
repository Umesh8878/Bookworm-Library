package com.masai;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.masai.dao.*;
import com.masai.dto.*;
import com.masai.exception.NoRecordFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sb101_library_management");
    private static final LibrarianDAO librarianDAO = new LibrarianDAO();
    private static final StudentDAO studentDAO = new StudentDAO();
    private static final BookDAO bookDAO = new BookDAO();

    
    public static EntityManager getConnection() {
        return emf.createEntityManager();
    }
    
    
    public static void main(String[] args) {
        System.out.println("Connection Successful");
        System.out.println("Welcome to the BookWorm Library!");
        System.out.println("-----------------------------------------");

        while (true) {
            System.out.println("Please select your choice:");
            System.out.println("1. Librarian");
            System.out.println("2. Student");
            System.out.println("0. Exit");

            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            switch (roleChoice) {
                case 1:
                    librarianMenu();
                    break;
                case 2:
                    studentMenu();
                    break;
                case 0:
                    System.out.println("Thank you for using the Bookworm Library Application.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void librarianMenu() {
        System.out.println("Librarian Menu");
        System.out.println("1. Already have an account. Login please!");
        System.out.println("2. New user, Please Signup first!");
        System.out.println("0. Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                performLibrarianLogin();
                break;
            case 2:
                performLibrarianSignup();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }
    }
    
    private static void performLibrarianLogin() {
        System.out.println("Enter your email:");
        String librarianEmail = scanner.nextLine();
        System.out.println("Enter your password:");
        String librarianPassword = scanner.nextLine();

        try {
            Librarian librarian = librarianDAO.getLibrarianByEmail(librarianEmail);
            if (librarian != null && librarian.verifyPassword(librarianPassword)) {
                System.out.println("Librarian Login Successful");
                while (true) {
                    System.out.println("Please select your choice:");
                    System.out.println("1. Add new book");
                    System.out.println("2. Update book information");
                    System.out.println("3. Remove book");
                    System.out.println("4. View student rentals");
                    System.out.println("5. View feedback and ratings");
                    System.out.println("6. Log out");
                    int librarianChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (librarianChoice) {
                        case 1:
                            addNewBook();
                            break;
                        case 2:
                             updateBook();
                            break;
                        case 3:
                            removeBook();
                            break;
                        case 4:
                            viewStudentRentals();
                            break;
                        case 5:
                            // Implement viewFeedbackAndRatings() method
                            break;
                        case 6:
                            System.out.println("Librarian logged out.");
                            return;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                    }
                }
            } else {
                throw new NoRecordFoundException("Invalid email or password. Please try again.");
            }
        } catch (NoRecordFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later.");
        }
     }
    
    private static void addNewBook() {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        System.out.println("Enter book genre:");
        String genre = scanner.nextLine();
        System.out.println("Is the book available? (true/false)");
        boolean availability = scanner.nextBoolean();
        scanner.nextLine(); // Consume the newline character

        Book newBook = new Book(title, author, genre, availability);
        try {
            if (bookDAO.saveBook(newBook)) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Error occurred while adding the book. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong while adding the book. Please try again later.");
        }
    }
    
    private static void updateBook() {
        System.out.println("Enter book ID:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }

        System.out.println("Update book title (current title: " + book.getTitle() + "):");
        String title = scanner.nextLine();
        System.out.println("Update book author (current author: " + book.getAuthor() + "):");
        String author = scanner.nextLine();
        System.out.println("Update book genre (current genre: " + book.getGenre() + "):");
        String genre = scanner.nextLine();
        System.out.println("Update book availability (current availability: " + book.isAvailability() + "):");
        boolean availability = scanner.nextBoolean();
        scanner.nextLine(); // Consume the newline character

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setAvailability(availability);

        try {
            if (bookDAO.updateBook(book)) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Error occurred while updating the book. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong while updating the book. Please try again later.");
        }
    }
    
    private static void removeBook() {
        System.out.println("Enter book ID:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }

        System.out.println("Are you sure you want to remove this book? (Y/N)");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            try {
                if (bookDAO.removeBook(bookId)) {
                    System.out.println("Book removed successfully.");
                } else {
                    System.out.println("Error occurred while removing the book. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong while removing the book. Please try again later.");
            }
        } else {
            System.out.println("Book removal canceled.");
        }
    }
    
    private static void viewStudentRentals() {
        System.out.println("Enter student email to view their rentals:");
        String studentEmail = scanner.nextLine();

        try {
            Student student = StudentDAO.getStudentByEmail(studentEmail);
            if (student == null) {
                System.out.println("Student not found with email: " + studentEmail);
                return;
            }

            List<Rental> studentRentals = RentalDAO.getRentalsByStudentId(student.getId());
            if (studentRentals.isEmpty()) {
                System.out.println("No rentals found for the student with email: " + studentEmail);
            } else {
                System.out.println("Rentals for Student '" + student.getName() + "' (" + student.getEmail() + "):");
                for (Rental rental : studentRentals) {
                    System.out.println("Book Title: " + rental.getBook().getTitle());
                    System.out.println("Rental Date: " + rental.getRentalDate());
                    System.out.println("Return Date: " + rental.getReturnDate());
                    System.out.println("--------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong while fetching student rentals. Please try again later.");
        }
    }

    private static void viewFeedbackAndRatings() {
        System.out.println("Enter the book ID to view feedback and ratings:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            Book book = BookDAO.getBookById(bookId);
            if (book == null) {
                System.out.println("Book not found with ID: " + bookId);
                return;
            }

            if (book.getFeedback() == null || ((String) book.getFeedback()).isEmpty()) {
                System.out.println("No feedback available for the book.");
            } else {
                System.out.println("Feedback for Book '" + book.getTitle() + "':");
                System.out.println(book.getFeedback());
            }

            System.out.println("Rating for Book '" + book.getTitle() + "': " + book.getRating() + " / 5");
        } catch (Exception e) {
            System.out.println("Something went wrong while fetching feedback and ratings for the book. Please try again later.");
        }
    }
    
    private static void performLibrarianSignup() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        try {
            Librarian newLibrarian = new Librarian(name, email, password);
            if (librarianDAO.saveLibrarian(newLibrarian)) {
                System.out.println("Librarian Signup Successful. You can now log in.");
                System.out.println("Do you want to log in now? (Y/N)");
                String loginChoice = scanner.nextLine();
                if (loginChoice.equalsIgnoreCase("Y")) {
                    performLibrarianLogin();
                }
            } else {
                System.out.println("Error occurred during signup. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later.");
        }
    }

    private static void studentMenu() {
        System.out.println("Student Menu");
        System.out.println("1. Already have an account. Login please!");
        System.out.println("2. New user, Please Signup first!");
        System.out.println("0. Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                performStudentLogin();
                break;
            case 2:
                performStudentSignup();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private static void performStudentLogin() {
        try {
            System.out.println("Enter your email:");
            String studentEmail = scanner.nextLine();
            System.out.println("Enter your password:");
            String studentPassword = scanner.nextLine();

            Student student = StudentDAO.getStudentByEmail(studentEmail);
            if (student != null && student.verifyPassword(studentPassword)) {
                System.out.println("Student Login Successful.");
                int choice;
                do {
                    System.out.println("1. View available books in the library");
                    System.out.println("2. Apply filters and sorting options to search and browse books");
                    System.out.println("3. Rent a book");
                    System.out.println("4. Return a rented book");
                    System.out.println("5. Provide feedback and ratings on rented books");
                    System.out.println("6. Log out");
                    
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            viewAvailableBooks();
                            break;
                        case 2:
                            searchAndBrowseBooks();
                            break;
                        case 3:
                            rentBook(student);
                            break;
                        case 4:
                            returnBook();
                            break;
                        case 5:
                            provideFeedbackAndRating();
                            break;
                        case 6:
                            System.out.println("You have successfully logged out from your student account.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                    }
                } while (choice != 6); // Keep repeating until user chooses to log out
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during student login: " + e.getMessage());
        }
    }

    private static void viewAvailableBooks() {
        try {
            List<Book> availableBooks = BookDAO.getAllBooks();
            if (availableBooks.isEmpty()) {
                System.out.println("No books available in the library.");
            } else {
                System.out.println("Available Books:");
                for (Book book : availableBooks) {
                    System.out.println(book);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong while fetching available books. Please try again later.");
            e.printStackTrace();
        }
    }
    
    private static void searchAndBrowseBooks() {
        System.out.println("Search and Browse Books");
        System.out.println("1. Filter by Genre");
        System.out.println("2. Filter by Author");
        System.out.println("3. Filter by Availability");
        System.out.println("4. Sort by Title");
        System.out.println("5. Sort by Author");
        System.out.println("6. Sort by Genre");
        System.out.println("7. Go back");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            switch (choice) {
                case 1:
                    System.out.println("Enter the genre:");
                    String genre = scanner.nextLine();
                    List<Book> booksByGenre = BookDAO.getBooksByGenre(genre);
                    if (booksByGenre.isEmpty()) {
                        System.out.println("No books found with the specified genre.");
                    } else {
                        System.out.println("Books with Genre '" + genre + "':");
                        for (Book book : booksByGenre) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter the author:");
                    String author = scanner.nextLine();
                    List<Book> booksByAuthor = BookDAO.getBooksByAuthor(author);
                    if (booksByAuthor.isEmpty()) {
                        System.out.println("No books found with the specified author.");
                    } else {
                        System.out.println("Books by Author '" + author + "':");
                        for (Book book : booksByAuthor) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter true or false to filter by availability:");
                    boolean availabilityFilter = scanner.nextBoolean();
                    List<Book> booksByAvailability = BookDAO.getBooksByAvailability(availabilityFilter);
                    if (booksByAvailability.isEmpty()) {
                        System.out.println("No books found with the specified availability.");
                    } else {
                        String availabilityStatus = availabilityFilter ? "Available" : "Not Available";
                        System.out.println("Books with Availability '" + availabilityStatus + "':");
                        for (Book book : booksByAvailability) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 4:
                    List<Book> booksSortedByTitle = BookDAO.getBooksSortedByTitle();
                    if (booksSortedByTitle.isEmpty()) {
                        System.out.println("No books found to sort by title.");
                    } else {
                        System.out.println("Books Sorted by Title:");
                        for (Book book : booksSortedByTitle) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 5:
                    List<Book> booksSortedByAuthor = BookDAO.getBooksSortedByAuthor();
                    if (booksSortedByAuthor.isEmpty()) {
                        System.out.println("No books found to sort by author.");
                    } else {
                        System.out.println("Books Sorted by Author:");
                        for (Book book : booksSortedByAuthor) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 6:
                    List<Book> booksSortedByGenre = BookDAO.getBooksSortedByGenre();
                    if (booksSortedByGenre.isEmpty()) {
                        System.out.println("No books found to sort by genre.");
                    } else {
                        System.out.println("Books Sorted by Genre:");
                        for (Book book : booksSortedByGenre) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 7:
                    // Go back to the student menu
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong while searching and browsing books. Please try again later.");
            e.printStackTrace();
        }
    }

    private static void rentBook(Student student) {
        System.out.println("Enter book ID:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            Book book = BookDAO.getBookById(bookId);
            if (book == null) {
                System.out.println("Book not found with ID: " + bookId);
                return;
            }

            if (book.isAvailability()) {
                book.setAvailability(false);
                book.setRentedBy(student);
                // Set return date to 7 days from the rental date
                book.setReturnDate(LocalDate.now().plusDays(7));
                if (BookDAO.updateBook(book)) {
                    System.out.println("Book rented successfully.");
                } else {
                    System.out.println("Error occurred while renting the book. Please try again.");
                }
            } else {
                System.out.println("Book is not available for rental.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later.");
        }
    }

    private static void returnBook() {
        System.out.println("Enter the book ID you want to return:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            Book book = BookDAO.getBookById(bookId);
            if (book == null) {
                System.out.println("Book not found with ID: " + bookId);
                return;
            }

            // Check if the book is currently rented
            if (book.isAvailability()) {
                System.out.println("The book is not currently rented. There is no need to return it.");
                return;
            }

            // Get the rental record for the book (assuming there's only one rental record per book)
            Rental rental = RentalDAO.getRentalByBookId(bookId);
            if (rental == null) {
                System.out.println("No rental record found for the book with ID: " + bookId);
                return;
            }

            // Get the current date to check for overdue
            LocalDate currentDate = LocalDate.now();

            // Check if the book is overdue
            if (currentDate.isAfter(rental.getReturnDate())) {
                System.out.println("The book is overdue. Please return it immediately.");
            } else {
                System.out.println("Thank you for returning the book on time.");
            }

            // Mark the book as available for further rentals
            book.setAvailability(true);
            BookDAO.updateBook(book);

            // Delete the rental record from the database
            RentalDAO.deleteRental(rental);

            System.out.println("Book returned successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while processing the return. Please try again later.");
        }
    }
    
    private static void provideFeedbackAndRating() {
        System.out.println("Enter the book ID for which you want to provide feedback and rating:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            Book book = BookDAO.getBookById(bookId);
            if (book == null) {
                System.out.println("Book not found with ID: " + bookId);
                return;
            }

            System.out.println("Enter your feedback for the book:");
            String feedback = scanner.nextLine();

            System.out.println("Enter your rating for the book (out of 5):");
            int rating = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Save the feedback and rating for the book
            book.setFeedback(feedback);
            book.setRating(rating);
            BookDAO.updateBook(book);

            System.out.println("Thank you for providing your feedback and rating!");
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later.");
        }
    }
    
    private static void performStudentSignup() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        try {
            Student newStudent = new Student(name, email, password);
            if (StudentDAO.saveStudent(newStudent)) {
                System.out.println("Student Signup Successful. You can now log in.");
                // Offer option for direct login
                System.out.println("Do you want to log in now? (Y/N)");
                String loginChoice = scanner.nextLine();
                if (loginChoice.equalsIgnoreCase("Y")) {
                    performStudentLogin();
                }
            } else {
                System.out.println("Error occurred during signup. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later.");
        }
    }
}
