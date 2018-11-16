package Student;

import FileManager.InputMismatchHandler;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * The StudentUI class handles user inputs and displays for the user
 *
 * @author Ng Man Chun
 * @version 1.0
 * @since 2018-11-15
 */
public class StudentUI {

    /**
     * InputMismatchHandler is a class that sanitizes user inputs.
     */
    InputMismatchHandler imh = new InputMismatchHandler();

    /**
     * This method asks the user to input what he wants to do as an integer from a menu
     * @return user choice
     */
    public int studentCtrlChoice(){
        Scanner scan = new Scanner (System.in);
        System.out.println("\nWhat would you like to do?");
        System.out.println("0: View Student Details \n1: Create Student \n2: Edit Student \n3: Delete Student \n4: List Students \n5: Quit");
        System.out.print("Enter Your Choice: ");
        return imh.checkInt();
    }

    /**
     * This method asks the user to input student data
     * @return student data as string array
     */
    public String[] readStudentData() {
        String[] args;

        Scanner scan = new Scanner(System.in);
        args = new String[6];

        args[0] = readFName(scan);
        args[1] = readLName(scan);
        args[2] = Integer.toString(readGender(scan));
        args[3] = Integer.toString(readYear(scan));
        args[4] = readDepartment(scan);
        args[5] = readGraduate(scan);

        return args;
    }

    /**
     * This method displays a student's data to the user
     * @param data
     */
    public void displayStudentData(String[] data){
        for(int i=0; i<data.length; i++){
            System.out.println(data[i]);
        }
        System.out.println("\n");
    }

    /**
     * This method displays a list of student IDs
     * @param studentList
     */
    public void listStudents(ArrayList<String> studentList){
        System.out.print("\nStudent IDs: ");
        studentList.forEach((student) -> System.out.print(student + ", "));
        System.out.print("\n");
    }


    //error messages

    /**
     * This error message states that the student already exists.
     * It may be used if the user tries to create a student that already exists.
     */
    public static void studentIdTaken(){
        System.out.println("\nStudent ID already exists! Try again.");
    }

    /**
     * This error message states that the student does not exist.
     * It may be used if the user tries to read data on a student that does not exist
     */
    public static void studentIdNonexist(){
        System.out.println("\nStudent ID doesn't exist! Try again.");
    }
    //!error messages


    /**
     * This method prompts the user to input student ID
     * @param scan
     * @return student ID as int
     */
    int readStudentID(Scanner scan) {
            System.out.println("Enter Student ID : ");
            return imh.checkInt();
    }

    /**
     * This method prompts the user to input the student's first name
     * @param scan
     * @return student first name as string
     */
    private String readFName(Scanner scan) {
        System.out.println("Enter Student's First Name : ");
        return scan.nextLine();
    }

    /**
     * This method prompts the user to input the student's last name
     * @param scan
     * @return student last name as string
     */
    private String readLName(Scanner scan) {
        System.out.println("Enter Student's Last Name : ");
        return scan.nextLine();
    }

    /**
     * This method prompts the user to input the year of study of the student
     * @param scan
     * @return year of study as integer
     */
    private int readYear(Scanner scan){
        System.out.println("Enter Student's year of study : ");
        return imh.checkInt();
    }


//    private int readAU(Scanner scan){
//        System.out.println("Enter number of AU's obtained so far : ");
//        return imh.checkInt();
//    }
//
//    private int readCGPASum(Scanner scan){
//        System.out.println("Enter CGPA numerator obtained so far : ");
//        return imh.checkInt();
//    }

    /**
     * This method prompts the user to enter the gender of the student as an integer from a menu
     * @param scan
     * @return student gender as integer between 1 and 3 inclusive
     */
    private int readGender(Scanner scan){
        System.out.println("Enter Student's Gender (Male: 1, Female: 2, Other: 3) : ");
        return imh.checkInt(1,3);   //makes sure range in [1,3] inclusive
    }

    /**
     * This method prompts the user to enter the department that the student studies in
     * @param scan
     * @return department as string
     */
    private String readDepartment(Scanner scan){
        System.out.println("Enter Department of Study : ");
        return scan.nextLine();
    }

    /**
     * This method prompts the user to enter the graduate level of the student. It must be either UG or PG.
     * The case of input does not matter, as the application will later convert the string to upper case.
     * @param scan
     * @return graduate level as string
     */
    private String readGraduate(Scanner scan) {
        System.out.println("Enter Level of Study (UG/PG) : ");
        String[] options = {"UG", "PG"};

        return imh.checkString(options);
    }
}
