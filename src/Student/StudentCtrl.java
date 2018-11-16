package Student;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The StudentCtrl class handles all of the application logic pertaining to the student objects.
 *
 * @author Ng Man Chun
 * @version 1.0
 * @since 2018-11-15
 */
public class StudentCtrl {

    /**
     * This method is the initial function of student control. It is needed because all student related operations are
     * initiated by the student control class. The initial function acts as a switch between all possible user actions
     * pertaining to student objects.
     */
    public void init(){
        StudentUI studentUI = new StudentUI();

        int choice;

        do {
            choice = studentUI.studentCtrlChoice();
            switch(choice){
                case 0:
                    viewStudent();
                    break;
                case 1:
                    createStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    listStudents();
            }
        } while (choice!=5);    //look at studentUI, 5 happens to be the option to quit
    }

    /**
     * This method handles the action sequence when the user requests a list of students
     */
    public void listStudents(){
        ArrayList<String> studentList = Student.listStudents();
        StudentUI studentUI = new StudentUI();
        studentUI.listStudents(studentList);
    }

    /**
     * This method calls the Student class to return a student and then calls the StudentUI class to display it
     * It also performs error catching.
     */
    public void viewStudent(){
        int studentID;
        StudentUI studentUI = new StudentUI();

        studentID = studentUI.readStudentID(new Scanner(System.in));
        if (!Student.existsStudent(studentID)) {
            StudentUI.studentIdNonexist();
        }
        else{
            Student student = Student.readInFile(studentID);

            String[] data = new String[7];
            data[0] = "Student ID : " + Integer.toString(student.getStudentID());
            data[1] = "First Name : " + student.getStudentFName();
            data[2] = "Last Name : " + student.getStudentLName();
            data[3] = "Year of Study : " + student.getYearOfStudy();
            data[4] = "Gender : " + student.getGender();
            data[5] = "Department : " + student.getDepartment();
            data[6] = "Graduate: " + student.getGraduate().toUpperCase();

            studentUI.displayStudentData(data);
        }
    }

    /**
     * This method handles the logic of creating a student
     */
    public void createStudent(){
        int studentID;
        StudentUI studentUI = new StudentUI();

        studentID = studentUI.readStudentID(new Scanner(System.in));
        if (Student.existsStudent(studentID)) StudentUI.studentIdTaken();  //print error if studentID already exists

        else {
            String[] data = studentUI.readStudentData();
            Student student = makeStudentObj(studentID, data);

            Student.saveToFile(student);
        }

        listStudents();
    }

    /**
     * This method handles the logic of editing student data
     */
    public void editStudent(){
        StudentUI studentUI = new StudentUI();
        int studentID = studentUI.readStudentID(new Scanner(System.in));

        if(Student.existsStudent(studentID)){
            String[] data = studentUI.readStudentData();
            Student student = makeStudentObj(studentID, data);
            Student.editFile(student);
        }
        else studentUI.studentIdNonexist(); //error message
    }

    /**
     * This method handles the logic of deleting a student
     */
    public void deleteStudent(){
        StudentUI studentUI = new StudentUI();
        int studentID = studentUI.readStudentID(new Scanner(System.in));
        if (Student.existsStudent(studentID)) {
            Student.deleteInFile(studentID);
        }
        else studentUI.studentIdNonexist();
    }

    /**
     * This method takes a string array containing student details from the StudentUI, and puts it into a student object.
     * This is the only method that needs to be changed if the boundary classes change (e.g. switch to a GUI instead of
     * console). This allows for more extensibility without having to change a lot of code.
     * @param studentID
     * @param args
     * @return student object
     */
    private Student makeStudentObj(int studentID, String[] args){
        Student student = new Student();

        student.setStudentID(studentID);
        student.setStudentFName(args[0]);
        student.setStudentLName(args[1]);
        student.setGender(parseGender(args[2]));
        int tempYear = Integer.parseInt(args[3]);
        if (tempYear>9) tempYear = 9;
        if (tempYear<1) tempYear = 1;
        student.setYearOfStudy(tempYear);               //I know this is bad coding because we don't give user feedback, but, I'm tired
        student.setDepartment(args[4].toLowerCase());   //for consistency
        student.setGraduate(args[5].toLowerCase());     //for consistency

        return student;
    }

    /**
     * This is a supporting method that reduces code duplication. It is called whenever another method wants to check if
     * a string matches a particular regular expression
     * @param string
     * @param patstring
     * @return
     */
    private boolean patternMatching(String string, String patstring){
        Pattern pattern = Pattern.compile(patstring);
        Matcher matcher = pattern.matcher(string);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    /**
     * This method handles unexpected user inputs for gender. Since gender is stored as an integer from 1 to 3 inclusive
     * the user may mistakenly input male or female instead. This method will then convert those strings to the correct
     * format to be stored.
     * @param string
     * @return
     */
    private int parseGender(String string){
        String patstring = "\\d"; //pattern to match
        if (patternMatching(string, patstring)) return Integer.parseInt(string);
        else if (string.equalsIgnoreCase("male")) return 1;
        else if (string.equalsIgnoreCase("female")) return 2;
        else return 3;
    }

//    private int parseGraduate(String string){
//        if (patternMatching(string,"\\d"));
//        else if (patternMatching(string,"\\w{2}")) {
//            if (string.equalsIgnoreCase("ug")) return 0;
//            else if (string.equalsIgnoreCase("pg")) return 1;
//            else;
//        }
//        else;
//
//        return -1;
//    }
}


