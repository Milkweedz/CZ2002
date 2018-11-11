package Login;

import Course.*;
import Mark.MarksCtrl;
import Statistics.StatisticsCtrl;
import Student.*;
import Registration.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LoginVerify {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "toor";

    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);

        while(true) {
            System.out.print("Enter admin password: ");
            if (scan.next().equalsIgnoreCase(PASSWORD)) break;
            else System.out.println("Wrong password. Try again.");
        }

        int choice;

        do {
            System.out.print("\nWhat area to access? ");
            System.out.print("\n1: Courses, \n2: Students, \n3: Registration, \n4: Marks \n5: Print Transcripts\n6: Course Statistics\n0: Quit\nChoice:");
            choice = scan.nextInt();

            switch(choice){
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

        } while(choice != 0);
    }

    public static void printTranscripts(int studentID) {
        if (Student.existsStudent(studentID)) {
            Student student = Student.readInFile(studentID);
            List<Integer> courses = RegistrationCtrl.studentCourses(studentID);
            //courses.forEach((course) -> System.out.println("DEBUG" + course));
            //System.out.println(courses.size());
            LoginUI.printStudentDetails(student);
            for (Integer course : courses) {
                double totalPercentage = MarksCtrl.retTotalPercentage(studentID, course);
                if (totalPercentage == -1){
                    System.out.printf("\n\nNo marks for student %d in course %d\n\n", studentID, course);
                }
                else {
                    LoginUI.printCourse(Course.readInFile(course));

                    String tutGroup = Registration.getStudentGroup(studentID, course);
                    if (tutGroup != null)
                        System.out.println("Tutorial Group: " + tutGroup);

                    System.out.print(String.format("\n%12s|%20s~ %-19s|", " ", "Exam Component ", "Marks"));

                    LoginUI.printMarks(student, Course.readInFile(course));
                    System.out.print(String.format("\n%12s|%20s: %-19.2f|", " ", "Total Percentage ", totalPercentage));
                }

            }
            LoginUI.printParaEnd();
        } else
            StudentUI.studentIdNonexist();
    }







    // either returns JSON file object or null.
    private static JSONObject readJSON(String fileName) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(new FileReader(fileName));
        } catch (IOException ex) {
            System.out.println("IOException!");
            ex.printStackTrace();
        } catch (ParseException parsex) {
            System.out.println("ParseException!");
            parsex.printStackTrace();
        }
        return new JSONObject();
    }

    private static void writeJSON(JSONObject file, String fileName) {

        try {
            FileWriter writer = new FileWriter(fileName, false);
            file.writeJSONString(writer);
            writer.close();
        } catch (IOException ex) {
            System.out.println("IOException!");
            ex.printStackTrace();
        }
    }
}
