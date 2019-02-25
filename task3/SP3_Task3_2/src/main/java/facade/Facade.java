/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Semester;
import entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mwn
 */
public class Facade {

    EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Student> getAllStudents() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Student.findAll").getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> findAllStudentsByFirstName(String firstName) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Student.findByFirstname").setParameter("firstname", firstName).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> findAllStudentsByLastName(String lastName) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Student.findByLastname").setParameter("lastname", lastName).getResultList();
        } finally {
            em.close();
        }
    }

    public Student addStudent(Student student) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return student;
        } finally {
            em.close();
        }
    }

    public Student addStudentWithSemester(Student student, String semesterName) {
        EntityManager em = emf.createEntityManager();
        
        try {
            Semester semester = (Semester) em.createNamedQuery("Semester.findByName").setParameter("name", semesterName).getSingleResult();
            student.setSemester(semester);
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return student;
        } finally {
            em.close();
        }
    }

    public long countStudentsForSemester(String semesterName) {
        EntityManager em = emf.createEntityManager();
        try {
            return (long) em.createQuery("SELECT count(s) FROM Student s WHERE s.semester.name = :name").setParameter("name", semesterName).getSingleResult();
        } finally {
            em.close();
        }
    }

    public long countStudentsForAllSemesters() {
        EntityManager em = emf.createEntityManager();
        try {
            return (long) em.createQuery("SELECT count(s) FROM Student s").getSingleResult();
        } finally {
            em.close();
        }
    }

}
