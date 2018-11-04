package Student;

import java.util.Scanner;

public class StudentUI {

    public static String[] inputStudDetails() {
        String[] args;
        int studentID;
        String studentFName, studentLName, department;
        int gender, yearOfStudy;
        int graduate;

        Scanner scan = new Scanner(System.in);
        args = new String[7];

        args[0] = Integer.toString(readStudentID(scan));
        args[1] = readFName(scan);
        args[2] = readLName(scan);
        args[3] = readDepartment(scan);
        args[4] = Integer.toString(readGender(scan));
        args[5] = Integer.toString(readYear(scan));
        args[6] = Boolean.toString(readGraduate(scan));

        return args;
    }

    private static int readStudentID(Scanner scan) {
            System.out.println("Enter Student.Student ID : ");
            return scan.nextInt();
    }

    private static String readFName(Scanner scan) {
        System.out.println("Enter Student.Student's First Name : ");
        return scan.next();
    }

    private static String readLName(Scanner scan) {
        System.out.println("Enter Student.Student's First Name : ");
        return scan.next();
    }

    private static int readYear(Scanner scan){
        System.out.println("Enter Student.Student's year of study : ");
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
        System.out.println("Enter Student.Student's Gender (Male: 1 /Female: 2 /Other: 3) : ");
        return scan.nextInt();
    }

    private static String readDepartment(Scanner scan){
        System.out.println("Enter Department of Study : ");
        return scan.nextLine();
    }

    private static boolean readGraduate(Scanner scan) {
        System.out.println("Enter Level of Study (UG/PG) : ");
        return scan.nextBoolean();
    }
}
