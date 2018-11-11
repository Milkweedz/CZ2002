package Statistics;

import java.util.Scanner;

public class StatisticsUI {

    public int readCourseID(){
        Scanner scan = new Scanner(System.in);

        System.out.print("\nEnter course ID: ");
        return scan.nextInt();
    }

    public void printStats(String[] stats){
        System.out.printf("Stats for course %s\n", stats[0]);
        System.out.println("Number of students: " + stats[1]);
        System.out.println("Average score percentage: " + stats[2]);

    }
}
