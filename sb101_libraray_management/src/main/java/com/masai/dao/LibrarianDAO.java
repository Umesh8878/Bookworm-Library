package com.masai.dao;

import com.masai.Librarian;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class LibrarianDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sb101_library_management");

    public boolean saveLibrarian(Librarian librarian) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = null;
        boolean success = false;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(librarian);
            transaction.commit();
            success = true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }

        return success;
    }

    public Librarian getLibrarianByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        Librarian librarian = null;

        try {
            librarian = em.createQuery("SELECT l FROM Librarian l WHERE l.email = :email", Librarian.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }

        return librarian;
    }
    
    public void closeEntityManagerFactory() {
        emf.close();
    }
}
