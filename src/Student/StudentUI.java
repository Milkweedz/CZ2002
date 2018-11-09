package Student;

import java.util.Scanner;

public class StudentUI {
    public int studentCtrlChoice(){
        Scanner scan = new Scanner (System.in);
        System.out.println("\nWhat would you like to do?");
        System.out.println("0: View Student Details, 1: Create Student, 2: Edit Student, 3: Delete Student, 4: Quit");
        System.out.print("Enter Your Choice: ");
        return scan.nextInt();
    }

    public static String[] readStudentData() {
        String[] args;

        Scanner scan = new Scanner(System.in);
        args = new String[6];

        args[0] = readFName(scan);
        args[1] = readLName(scan);
        args[2] = readDepartment(scan);
        args[3] = Integer.toString(readGender(scan));
        args[4] = Integer.toString(readYear(scan));
        args[5] = readGraduate(scan);

        return args;
    }

    public void displayStudentData(String[] data){
        for(int i=0; i<data.length; i++){
            System.out.println(data[i]);
        }
        System.out.println("\n");
    }


    //error messages
    public static void studentIdTaken(){
        System.out.println("\nStudent ID already exists! Try again.");
    }

    public static void studentIdNonexist(){
        System.out.println("\nStudent ID doesn't exist! Try again.");
    }
    //!error messages





    static int readStudentID(Scanner scan) {
            System.out.println("Enter Student ID : ");
            return scan.nextInt();
    }

    private static String readFName(Scanner scan) {
        System.out.println("Enter Student's First Name : ");
        return scan.next();
    }

    private static String readLName(Scanner scan) {
        System.out.println("Enter Student's Last Name : ");
        return scan.next();
    }

    private static int readYear(Scanner scan){
        System.out.println("Enter Student's year of study : ");
        return scan.nextInt();
    }

    private static int readAU(Scanner scan){
        System.out.println("Enter number of AU's obtained so far : ");
        return scan.nextInt();
    }

    private static int readCGPASum(Scanner scan){
        System.out.println("Enter CGPA numerator obtained so far : ");
        return scan.nextInt();
    }

    private static int readGender(Scanner scan){
        System.out.println("Enter Student's Gender (Male: 1, Female: 2, Other: 3) : ");
        return scan.nextInt();
    }

    private static String readDepartment(Scanner scan){
        System.out.println("Enter Department of Study : ");
        return scan.nextLine();
    }

    private static String readGraduate(Scanner scan) {
        System.out.println("Enter Level of Study (UG/PG) : ");
        return scan.next();
    }
}
