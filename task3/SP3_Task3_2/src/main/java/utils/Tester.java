/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.Student;
import facade.Facade;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author mwn
 */
public class Tester {

    public static void main(String[] args) {
        String semester = "CLcos-v14e";
        
        Facade facade = new Facade(Persistence.createEntityManagerFactory("pu"));

        System.out.println("Find all students in the system");

        facade.getAllStudents().forEach((s) -> {
            System.out.println(s.getFirstname() + " " + s.getLastname());
        });

        System.out.println("_________________________________");

        System.out.println("Students in the System with the first name Anders");

        facade.findAllStudentsByFirstName("Anders").forEach((s) -> {
            System.out.println(s.getFirstname() + " " + s.getLastname());
        });

        System.out.println("_________________________________");

        System.out.println("Insert a new Student into the system");

//        Student s = facade.addStudent(new Student("SomeFirstName", "SomeLastName"));
//        
//        System.out.println("Got an ID: " + s.getId());
        System.out.println("_________________________________");

        System.out.println("Assign a new student to a semester");

        Student s1 = facade.addStudentWithSemester(new Student("SomeFirstNameWithSem", "SomeLastNameWithSem"), semester);
        
        System.out.println("Got an ID: " + s1.getId());
        System.out.println("Got a Semester: " + s1.getSemester().getName());
        System.out.println("_________________________________");

        System.out.println("Find (using JPQL) all Students in the system with the last name And");

        facade.findAllStudentsByLastName("And").forEach((s) -> {
            System.out.println(s.getFirstname() + " " + s.getLastname());
        });
        System.out.println("_________________________________");

        System.out.println("The total number of students, for a semester ");

        

        System.out.println("Total number for " + semester + ": " + facade.countStudentsForSemester(semester));

        System.out.println("_________________________________");

        System.out.println("Total number of students in all semesters.");

        System.out.println("Total number of students: " + facade.countStudentsForAllSemesters());

        System.out.println("_________________________________");

    }
}
