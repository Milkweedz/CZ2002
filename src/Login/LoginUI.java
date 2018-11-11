package Login;

import Course.Course;
import Mark.Marks;
import Mark.MarksUI;
import Registration.Registration;
import Student.Student;

import java.util.Scanner;

public class LoginUI {
    public static int readStudentID(Scanner scan) {
        System.out.println("Enter Student ID : ");
        return scan.nextInt();
    }

    public static int readCourseID(Scanner scan) {
        System.out.println("Enter Course ID : ");
        return scan.nextInt();
    }

    public static void printStudentDetails(Student student) {
        System.out.print(String.format("|%-1d.%30s|", 1, " Student ID "));
        System.out.println(String.format(" %-30d|", student.getStudentID()));
        System.out.print(String.format("|%-1d.%30s|", 2, " Student's Name "));
        System.out.println(String.format(" %-30s|", student.getStudentLName() + "," + student.getStudentFName()));
        System.out.print(String.format("|%-1d.%30s|", 3, " Year Of Study "));
        System.out.println(String.format(" %-30d|", student.getYearOfStudy()));
        System.out.print(String.format("|%-1d.%30s|", 4, " Number of AUs obtained "));
        System.out.println(String.format(" %-30d|", student.getNumOfAU()));
        System.out.print(String.format("|%-1d.%30s|", 5, " CGPA "));
        System.out.println(String.format(" %-30.2f|", student.getCGPA()));
        System.out.print(String.format("|%-1d.%30s|", 6, "Gender "));
        System.out.println(String.format(" %-30s|", student.getGender()));
        System.out.print(String.format("|%-1d.%30s|", 7, "Department of study "));
        System.out.println(String.format(" %-30s|", student.getDepartment()));
        System.out.print(String.format("|%-1d.%30s|", 8, "Level Of Study "));
        System.out.println(String.format(" %-30s|", student.getYearOfStudy()));
    }

    public static void printCourse(Course reg) {
        printParaBreak();
        System.out.print(String.format("\n|%-1d.%30s|", 1, " Course ID "));
        System.out.println(String.format(" %-30d|", reg.getCourseID()));
        System.out.print(String.format("|%-1d.%30s|", 2, " Course's Name "));
        System.out.println(String.format(" %-30s|", reg.getCourseName()));
        System.out.print(String.format("|%-1d.%30s|", 3, " Coordinator "));
        System.out.println(String.format(" %-30s|", reg.getCoordinator()));
        if (Course.readInFile(reg.getCourseID()).getType() == Course.COURSETYPE.Lec) {
            System.out.print(String.format("|%-1d.%30s|", 4, " Course Type "));
            System.out.println(String.format(" %-30s|", "Only Lecture "));
        } else if (Course.readInFile(reg.getCourseID()).getType() == Course.COURSETYPE.LecTut) {
            //System.out.print(String.format("|%-1d.%30s|", 4, " Tutorial Group "));
            //System.out.println(String.format(" %-30s|", reg.getTutorialGroups()));
            System.out.print(String.format("|%-1d.%30s|", 5, " Course Type "));
            System.out.println(String.format(" %-30s|", "Lecture + Tutorial"));
        } else {
            //System.out.print(String.format("|%-1d.%30s|", 4, " Tutorial Group "));
            //System.out.println(String.format(" %-30s|", reg.getTutorialGroups()));
            System.out.print(String.format("|%-1d.%30s|", 5, " Course Type "));
            System.out.println(String.format(" %-30s|", "Lecture + Tutorial + Lab "));
        }


    }

    public static void printMarks(Student student, Course course) {
        if (Marks.existsMarks(student.getStudentID(), course.getCourseID())) {
            MarksUI.displayTranscriptData(Marks.readInFile(student.getStudentID(), course.getCourseID()));
        } else
            displayMarksError();
    }

    public static void displayMarksError() {
        System.out.println(" No Marks entered as of yet.");
    }

    public static void printParaBreak() {
        for (int i = 0; i < 22; i++)
            System.out.print("-+-");
    }

    public static void printParaEnd() {
        System.out.println();
        for (int i = 0; i < 22; i++)
            System.out.print("***");
    }

    public static void studentIdNonexist() {
        System.out.println("\nStudent ID doesn't exist! Try again.");
    }
}
