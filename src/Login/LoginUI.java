package Login;
/**
 * This class is responsible for all input/output functionality of the the LoginVerify class
 * @author Mayank Nariani
 * @version 1.0
 * @date 2018-11-15
 */

import Course.Course;
import FileManager.InputMismatchHandler;
import Mark.Marks;
import Mark.MarksUI;
import Student.Student;

public class LoginUI {

    /**
     * Reads and returns student ID
     * @return
     */
    public static int readStudentID() {
        InputMismatchHandler inp = new InputMismatchHandler();
        System.out.println("Enter Student ID : ");
        return inp.checkInt();
    }

    /**
     * Prints all details of a student as stored in student file
     * @param student
     */
    public static void printStudentDetails(Student student) {
        System.out.print(String.format("|%-1d.%30s|", 1, " Student ID "));
        System.out.println(String.format(" %-30d|", student.getStudentID()));
        System.out.print(String.format("|%-1d.%30s|", 2, " Student's Name "));
        System.out.println(String.format(" %-30s|", student.getStudentLName() + "," + student.getStudentFName()));
        System.out.print(String.format("|%-1d.%30s|", 3, " Year Of Study "));
        System.out.println(String.format(" %-30d|", student.getYearOfStudy()));
        System.out.print(String.format("|%-1d.%30s|", 4, "Gender "));
        System.out.println(String.format(" %-30s|", student.getGender()));
        System.out.print(String.format("|%-1d.%30s|", 5, "Department of study "));
        System.out.println(String.format(" %-30s|", student.getDepartment()));
        System.out.print(String.format("|%-1d.%30s|", 6, "Level Of Study "));
        System.out.println(String.format(" %-30s|", student.getYearOfStudy()));
    }

    /**
     * Prints all the details of the course taken by the student
     * @param reg
     */
    public static void printCourse(Course reg) {
        System.out.println();
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

    /**
     * Prints all the details of the marks obtained by a student in a course if it exists
     * @param student
     * @param course
     */
    public static void printMarks(Student student, Course course) {
        if (Marks.existsMarks(student.getStudentID(), course.getCourseID())) {
            MarksUI.displayTranscriptData(Marks.readInFile(student.getStudentID(), course.getCourseID()));
        } else
            displayMarksError();
    }

    /**
     * Displays error if no marks have been entered in the marks file
     */

    public static void displayMarksError() {
        System.out.println(" No Marks entered as of yet.");
    }

    /**
     * Displays message if no marks have been entered ofr a student in a particular course
     * @param studentID
     * @param courseID
     */
    public static void displayMarksError(int studentID, int courseID) {
        System.out.printf("\n           No marks for student %d in course %d\n", studentID, courseID);
    }

    /**
     * Break between each para in print transcripts
     */
    public static void printParaBreak() {
        for (int i = 0; i < 22; i++)
            System.out.print("-+-");
    }

    /**
     * end of print transcripts
     */
    public static void printParaEnd() {
        System.out.println();
        for (int i = 0; i < 22; i++)
            System.out.print("***");
    }


}
