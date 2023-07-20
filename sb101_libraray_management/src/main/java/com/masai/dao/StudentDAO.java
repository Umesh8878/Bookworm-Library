package com.masai.dao;

import com.masai.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class StudentDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sb101_library_management");

    public static boolean saveStudent(Student student) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = null;
        boolean success = false;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(student);
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

    public static Student getStudentByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        Student student = null;

        try {
        	student = em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }

        return student;
    }
    
   
}
