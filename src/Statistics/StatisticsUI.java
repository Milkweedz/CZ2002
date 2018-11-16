package Statistics;

import java.util.Scanner;

/**
 * Represents user interface for the course statistics
 * Each course has its own individual statistics
 * @author Nikhita
 * @version 1.0
 * @since 2018-11-15
 */
public class StatisticsUI {

    /**
     * Accepts course ID from the user
     * @return The input course ID
     */
    public int readCourseID(){
        Scanner scan = new Scanner(System.in);

        System.out.print("\nEnter course ID: ");
        return scan.nextInt();
    }

    /**
     * Prints the statistics for a course (number of students and average score percentage)
     * @param stats String array containing the stats of the course
     * Should include the number of students and average score percentage
     */
    public void printStats(String[] stats){
        System.out.printf("Stats for course %s\n", stats[0]);
        System.out.println("Number of students: " + stats[1]);
        System.out.printf("Average score percentage: %.2f\n", Float.parseFloat(stats[2]));

    }
}
