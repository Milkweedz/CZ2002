package Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Course.*;
import FileManager.InputMismatchHandler;

public class RegistrationUI {
    InputMismatchHandler imh = new InputMismatchHandler();
    public int registrationCtrlChoice() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nWhat would you like to do?");
        System.out.println("1: Register a student for a course\n2: Remove a student from a course\n3: List of courses registered by a student\n4: List of students registered for a course\n5: List of students registered for a tutorial group\n6: Quit");
        System.out.print("Enter Your Choice: ");
        return imh.checkInt();
    }

    public int readStudentID(Scanner scan) {
        System.out.println("Enter Student ID : ");
        return imh.checkInt();
    }

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

    public int readCourseID(Scanner scan) {
        System.out.println("Enter Course ID : ");
        return imh.checkInt();
    }

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

    public static void successAdd() {
        System.out.println("Registration Successfull !");
    }

    public static void successEdit() {
        System.out.println("Marks Successfully Edited !");
    }

    public static void successRemove() {
        System.out.println("Registration Successfully Removed !");
    }

    public static void courseNotExist() {
        System.out.println("Course does not exist!");
    }

    public static void studentCourseNotExist() {
        System.out.println("Student not registered for this course!");
    }
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
