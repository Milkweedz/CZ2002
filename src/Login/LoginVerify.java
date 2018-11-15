package Login;

import Course.*;
import Mark.MarksCtrl;
import Statistics.StatisticsCtrl;
import Student.*;
import Registration.*;

import java.util.List;
import java.util.Scanner;

public class LoginVerify {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "toor";

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("Enter admin password: ");
            if (scan.next().equalsIgnoreCase(PASSWORD)) break;
            else System.out.println("Wrong password. Try again.");
        }

        int choice;

        do {
            System.out.print("\nWhat area to access? ");
            System.out.print("\n1: Courses \n2: Students \n3: Registration \n4: Marks \n5: Print Transcripts\n6: Course Statistics\n0: Quit\nChoice:");
            choice = scan.nextInt();

            switch (choice) {
                case 1:
                    CourseCtrl courseCtrl = new CourseCtrl();
                    System.out.println("Initiating course controller...");
                    courseCtrl.init();
                    break;
                case 2:
                    StudentCtrl studentCtrl = new StudentCtrl();
                    System.out.println("Initiating student controller...");
                    studentCtrl.init();
                    break;
                case 3:
                    RegistrationCtrl regCtrl = new RegistrationCtrl();
                    System.out.println("Initiating registration controller...");
                    regCtrl.init();
                    break;
                case 4:
                    MarksCtrl mrkCtrl = new MarksCtrl();
                    System.out.println("Initiating Marks controller...");
                    mrkCtrl.init();
                    break;
                case 5:
                    printTranscripts(LoginUI.readStudentID(new Scanner(System.in)));
                    break;
                case 6:
                    StatisticsCtrl.init();
                    break;
            }

        } while (choice != 0);
    }

    public static void printTranscripts(int studentID) {
        if (Student.existsStudent(studentID)) {
            Student student = Student.readInFile(studentID);
            List<Integer> courses = RegistrationCtrl.studentCourses(studentID);
            //courses.forEach((course) -> System.out.println("DEBUG" + course));
            //System.out.println(courses.size());
            LoginUI.printStudentDetails(student);
            for (Integer course : courses) {
                LoginUI.printCourse(Course.readInFile(course));

                String tutGroup = Registration.getStudentGroup(studentID, course);
                if (tutGroup != null)
                    System.out.println("Tutorial Group: " + tutGroup);
                double totalPercentage = MarksCtrl.retTotalPercentage(studentID, course);
                if (totalPercentage == -1) {
                    LoginUI.displayMarksError(studentID, course);
                } else {


                    System.out.print(String.format("\n%12s|%20s~ %-19s|", " ", "Exam Component ", "Marks"));

                    LoginUI.printMarks(student, Course.readInFile(course));
                    System.out.print(String.format("\n%12s|%20s: %-19.2f|", " ", "Total Percentage ", totalPercentage));
                }

            }
            LoginUI.printParaEnd();
        } else
            StudentUI.studentIdNonexist();
    }

}
