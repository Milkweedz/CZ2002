import Course.CourseCtrl;

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
            System.out.println("1: Courses, 2: Students, 3: Registration, 0: Quit");
            choice = scan.nextInt();

            switch(choice){
                case 1:
                    CourseCtrl courseCtrl = new CourseCtrl();
                    System.out.println("Initiating course controller...");
                    courseCtrl.init();
                    break;
            }

        } while(choice != 0);
    }
}
