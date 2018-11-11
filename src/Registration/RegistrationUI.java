package Registration;

import java.util.List;
import java.util.Scanner;

public class RegistrationUI {
    public static int registrationCtrlChoice(){
        Scanner scan = new Scanner (System.in);
        System.out.println("\nWhat would you like to do?");
        System.out.println("1: Register a student for a course\n2: Remove a student from a course\n3: List of courses registered by a student\n4: List of students registered for a course \n5: Quit");
        System.out.print("Enter Your Choice: ");
        return scan.nextInt();
    }

    public static int readStudentID(Scanner scan) {
        System.out.println("Enter Student ID : ");
        return scan.nextInt();
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
        System.out.println("Marks Successfully Added !");
    }
    public static void successEdit()
    {
        System.out.println("Marks Successfully Edited !");
    }
    public static void successRemove()
    {
        System.out.println("Marks Successfully Removed !");
    }

}
