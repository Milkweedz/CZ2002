package Registration;

import java.util.Scanner;

public class RegistrationUI {
	
    public static int readStudentID(Scanner scan) {
        System.out.println("Enter Student ID : ");
        return scan.nextInt();
}
    
    public static int readCourseID(Scanner scan) {
        System.out.println("Enter Course ID : ");
        return scan.nextInt();
}

}
