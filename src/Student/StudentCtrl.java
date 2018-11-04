package Student;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentCtrl {
//    private static final int SUCCESS = 0;
//    private static final int FAIL = 1;

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
            }
        } while (choice!=4);    //look at studentUI, 5 happens to be the option to quit
    }

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
    }

    public void editStudent(){
        StudentUI studentUI = new StudentUI();
        int studentID = studentUI.readStudentID(new Scanner(System.in));

        if(Student.existsStudent(studentID)){
            String[] data = studentUI.readStudentData();
            Student student = makeStudentObj(studentID, data);
            Student.deleteInFile(studentID);
            Student.saveToFile(student);
        }
        else studentUI.studentIdNonexist(); //error message
    }

    public void deleteStudent(){
        StudentUI studentUI = new StudentUI();
        int studentID = studentUI.readStudentID(new Scanner(System.in));
        if (Student.existsStudent(studentID)) {
            Student.deleteInFile(studentID);
        }
        else studentUI.studentIdNonexist();
    }


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

    private boolean patternMatching(String string, String patstring){
        Pattern pattern = Pattern.compile(patstring);
        Matcher matcher = pattern.matcher(string);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    private int parseGender(String string){
        String patstring = "\\d"; //pattern to match
        if (patternMatching(string, patstring)) return Integer.parseInt(string);
        else if (string.equalsIgnoreCase("male")) return 0;
        else if (string.equalsIgnoreCase("female")) return 1;
        else return 2;
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


