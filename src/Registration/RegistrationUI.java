package Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Course.*;

public class RegistrationUI {
    public static int registrationCtrlChoice(){
        Scanner scan = new Scanner (System.in);
        System.out.println("\nWhat would you like to do?");
        System.out.println("1: Register a student for a course\n2: Remove a student from a course\n3: List of courses registered by a student\n4: List of students registered for a course\n5: List of students registered for a tutorial group\n6: Quit");
        System.out.print("Enter Your Choice: ");
        return scan.nextInt();
    }

    public static int readStudentID(Scanner scan) {
        System.out.println("Enter Student ID : ");
        return scan.nextInt();
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
    public static int readCourseID(Scanner scan) {
        System.out.println("Enter Course ID : ");
        return scan.nextInt();
}

    public static void printStudents(List<Integer> students) {
        if (students.isEmpty()){
            System.out.println("No students registered for course!");
        } else {
            System.out.println("\nStudents registered for course:");
            for (int n = 0; n < students.size(); n++)
                System.out.print(students.get(n) + ", ");
            System.out.print("\n");
        }
    }
    public static void printCourses(List<Integer> courses) {
        if (courses.isEmpty()){
            System.out.println("No courses registered by this student!");
        } else {
            System.out.println("\nCourses registered by student:");
            for (int n = 0; n < courses.size(); n++)
                System.out.print(courses.get(n) + ", ");
            System.out.print("\n");
        }
    }
    public static void successAdd()
    {
        System.out.println("Registration Successfull !");
    }
    public static void successEdit()
    {
        System.out.println("Marks Successfully Edited !");
    }
    public static void successRemove()
    {
        System.out.println("Registration Successfully Removed !");
    }

}
