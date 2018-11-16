package Registration;
/**
 * This is the User interface class which handles all interactions made with user
 * @author Thomas Stephen Felix
 * @version 1.0
 * @since 2018-11-15
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Course.*;
import FileManager.InputMismatchHandler;

public class RegistrationUI {

    InputMismatchHandler imh = new InputMismatchHandler();
    /**
     * This method returns the choice input by the user after going through the options available for registration control
     * @return choice
     */
        public int registrationCtrlChoice () {

            System.out.println("\nWhat would you like to do?");
            System.out.println("1: Register a student for a course\n2: Remove a student from a course\n3: List of courses registered by a student\n4: List of students registered for a course\n5: List of students registered for a tutorial group\n6: Quit");
            System.out.print("Enter Your Choice: ");
            return imh.checkInt();
        }

    /**
     * This method returns the student ID as inputted bu user
     * @return student ID as inputted by user
     */
    public int readStudentID() {
        System.out.println("Enter Student ID : ");
        return imh.checkInt();
    }

    /**
     * This method return the a string displaying all the tutorial groups present in the course separated by a '|'
     * @param crsID
     * @return String with all tutorial groups
     */
    public static String readTutGroup(int crsID) {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> tutGroups = Course.readInFile(crsID).getTutorialGroups();
        String tutorial = "";
        System.out.print("Choose A tutorial Group ");
        for (int i = 0; i < tutGroups.size(); i++)
            System.out.print(tutGroups.get(i) + " | ");
        System.out.println();
        do {
            tutorial = scan.next();
        } while (!tutGroups.contains(tutorial));
        return tutorial;
    }

    /**
     * This method reads the course ID when inputted by user
     * @return course ID
     */
    public int readCourseID() {
        System.out.println("Enter Course ID : ");
        return imh.checkInt();
    }

    /**
     * Prints all the student IDs registered for a particular course
     * @param students
     */
    public static void printStudents(List<Integer> students) {
        if (students.isEmpty()) {
            System.out.println("No students registered for course!");
        } else {
            System.out.println("\nStudents registered for course:");
            for (int n = 0; n < students.size(); n++)
                System.out.print("Student ID "+(n+1)+" : "+students.get(n) + ",    ");
            System.out.print("\n");
        }
    }

    /**
     * Prints all the course IDs registered by a particual student
     * @param courses
     */

    public static void printCourses(List<Integer> courses) {
        if (courses.isEmpty()) {
            System.out.println("No courses registered by this student!");
        } else {
            System.out.println("\nCourses registered by student:");
            for (int n = 0; n < courses.size(); n++)
                System.out.println("Course ID "+(n+1)+" : "+courses.get(n));
            System.out.print("\n");
        }
    }

    /**
     * method prints a message when registration is successful
     */
    public static void successAdd() {
        System.out.println("Registration Successfull !");
    }

    /**
     *  method prints a message when registration is successfully removed
     */
    public static void successRemove() {
        System.out.println("Registration Successfully Removed !");
    }

    /**
     * method prints an error message when the course ID entered by the user does not exist
     */

    public static void courseNotExist() {
        System.out.println("Course does not exist!");
    }

    /**
     * method prints an error message when the student is not registered for a course
     */
    public static void studentCourseNotExist() {
        System.out.println("Student not registered for this course!");
    }

    /**
     * method prints an error message when the student ID entered by the user does not exist
     */
    public static void studentNotExist() {
        System.out.println("Student does not exist!");
    }
    

    public static void studentCoursesNotExist() {
        System.out.println("Student not registered for any course!");
    }

    public static void courseNoTutorials(){
        System.out.println("Course has no tutorials!");
    }


}
