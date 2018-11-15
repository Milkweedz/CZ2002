package Student;

import FileManager.InputMismatchHandler;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentUI {
    InputMismatchHandler imh = new InputMismatchHandler();
    public int studentCtrlChoice(){
        Scanner scan = new Scanner (System.in);
        System.out.println("\nWhat would you like to do?");
        System.out.println("0: View Student Details \n1: Create Student \n2: Edit Student \n3: Delete Student \n4: List Students \n5: Quit");
        System.out.print("Enter Your Choice: ");
        return imh.checkInt();
    }

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

    public void displayStudentData(String[] data){
        for(int i=0; i<data.length; i++){
            System.out.println(data[i]);
        }
        System.out.println("\n");
    }

    public void listStudents(ArrayList<String> studentList){
        System.out.print("\nStudent IDs: ");
        studentList.forEach((student) -> System.out.print(student + ", "));
        System.out.print("\n");
    }


    //error messages
    public static void studentIdTaken(){
        System.out.println("\nStudent ID already exists! Try again.");
    }

    public static void studentIdNonexist(){
        System.out.println("\nStudent ID doesn't exist! Try again.");
    }
    //!error messages





    int readStudentID(Scanner scan) {
            System.out.println("Enter Student ID : ");
            return imh.checkInt();
    }

    private String readFName(Scanner scan) {
        System.out.println("Enter Student's First Name : ");
        return scan.nextLine();
    }

    private String readLName(Scanner scan) {
        System.out.println("Enter Student's Last Name : ");
        return scan.nextLine();
    }

    private int readYear(Scanner scan){
        System.out.println("Enter Student's year of study : ");
        return imh.checkInt();
    }

    private int readAU(Scanner scan){
        System.out.println("Enter number of AU's obtained so far : ");
        return imh.checkInt();
    }

    private int readCGPASum(Scanner scan){
        System.out.println("Enter CGPA numerator obtained so far : ");
        return imh.checkInt();
    }

    private int readGender(Scanner scan){
        System.out.println("Enter Student's Gender (Male: 1, Female: 2, Other: 3) : ");
        return imh.checkInt(1,3);   //makes sure range in [1,3] inclusive
    }

    private String readDepartment(Scanner scan){
        System.out.println("Enter Department of Study : ");
        return scan.nextLine();
    }

    private String readGraduate(Scanner scan) {
        System.out.println("Enter Level of Study (UG/PG) : ");
        String[] options = {"UG", "PG"};

        return imh.checkString(options);
    }
}
