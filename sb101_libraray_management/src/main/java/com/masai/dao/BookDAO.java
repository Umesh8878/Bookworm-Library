package com.masai.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.Collections;
import java.util.List;

import com.masai.App;
import com.masai.dto.Book;

public class BookDAO {

    public boolean saveBook(Book book) {
        EntityManager entityManager = App.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return false;
    }

    public static boolean updateBook(Book book) {
        EntityManager entityManager = App.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(book);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return false;
    }

    public boolean removeBook(int bookId) {
        EntityManager entityManager = App.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Book book = entityManager.find(Book.class, bookId);
            if (book != null) {
                entityManager.remove(book);
                transaction.commit();
                return true;
            } else {
                System.out.println("Book not found with ID: " + bookId);
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return false;
    }

    public static List<Book> getAllBooks() {
        EntityManager entityManager = App.getConnection();
        try {
            Query query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Collections.emptyList();
    }

    public static List<Book> getBooksByGenre(String genre) {
        EntityManager entityManager = App.getConnection();
        try {
            return entityManager.createQuery("SELECT b FROM Book b WHERE b.genre = :genre", Book.class)
                    .setParameter("genre", genre)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Collections.emptyList();
    }

    public static List<Book> getBooksByAuthor(String author) {
        EntityManager entityManager = App.getConnection();
        try {
            return entityManager.createQuery("SELECT b FROM Book b WHERE b.author = :author", Book.class)
                    .setParameter("author", author)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Collections.emptyList();
    }

    public static List<Book> getBooksByAvailability(boolean availability) {
        EntityManager entityManager = App.getConnection();
        try {
            return entityManager.createQuery("SELECT b FROM Book b WHERE b.availability = :availability", Book.class)
                    .setParameter("availability", availability)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Collections.emptyList();
    }

    public static List<Book> getBooksSortedByTitle() {
        EntityManager entityManager = App.getConnection();
        try {
            return entityManager.createQuery("SELECT b FROM Book b ORDER BY b.title", Book.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Collections.emptyList();
    }

    public static List<Book> getBooksSortedByAuthor() {
        EntityManager entityManager = App.getConnection();
        try {
            return entityManager.createQuery("SELECT b FROM Book b ORDER BY b.author", Book.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Collections.emptyList();
    }

    public static List<Book> getBooksSortedByGenre() {
        EntityManager entityManager = App.getConnection();
        try {
            return entityManager.createQuery("SELECT b FROM Book b ORDER BY b.genre", Book.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Collections.emptyList();
    }

    public static Book getBookById(int bookId) {
        EntityManager entityManager = App.getConnection();
        try {
            return entityManager.find(Book.class, bookId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    // Add other methods as per requirements

}
