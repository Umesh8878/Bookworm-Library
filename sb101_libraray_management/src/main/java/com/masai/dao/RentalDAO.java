package com.masai.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

import com.masai.App;
import com.masai.dto.Rental;

public class RentalDAO {

    public boolean saveRental(Rental rental) {
        EntityManager entityManager = App.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(rental);
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

    public static boolean deleteRental(Rental rental) {
        EntityManager entityManager = App.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Rental rentalToDelete = entityManager.find(Rental.class, rental.getId());
            if (rentalToDelete != null) {
                entityManager.remove(rentalToDelete);
                transaction.commit();
                return true;
            } else {
                System.out.println("Rental record not found with ID: " + rental.getId());
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

    public static Rental getRentalByBookId(int bookId) {
        EntityManager entityManager = App.getConnection();
        try {
            TypedQuery<Rental> query = entityManager.createQuery(
                "SELECT r FROM Rental r WHERE r.book.id = :bookId", Rental.class
            );
            query.setParameter("bookId", bookId);
            return query.getSingleResult();
        } catch (Exception e) {
            // No rental record found for the given bookId
        } finally {
            entityManager.close();
        }

        return null;
    }

    public static List<Rental> getRentalsByStudentId(Object id) {
        EntityManager entityManager = App.getConnection();
        try {
            TypedQuery<Rental> query = entityManager.createQuery(
                "SELECT r FROM Rental r WHERE r.student.id = :studentId", Rental.class
            );
            query.setParameter("studentId", id);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return null;
    }

}
