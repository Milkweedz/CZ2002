package Statistics;

import Mark.MarksCtrl;
import Registration.RegistrationCtrl;

/**
 * Represents the control class for statistics of a course
 * Each course has its own statistics to be computed
 * @author Nikhita
 * @version 1.0
 * @since 2018-11-15
 */

public class StatisticsCtrl {

    /**
     * calls and runs methods from the other classes
     * initializes variables
     * computes average score
     * prints course statistics
     */
    public static void init(){
        StatisticsUI statisticsUI = new StatisticsUI();
        int courseID = statisticsUI.readCourseID();
        int numStudents = RegistrationCtrl.studentsInCourses(courseID).size();
        int numStudentsScored = 0;
        double avgScore;
        double totalScore = 0.0;
        double studentScore;

        for(int student : RegistrationCtrl.studentsInCourses(courseID)){
            studentScore = MarksCtrl.retTotalPercentage(student, courseID);
            if (studentScore != -1){
                totalScore += studentScore;
                numStudentsScored++;
            }
        }
        avgScore = totalScore / numStudentsScored;

        String[] stats = new String[3];
        stats[0] = Integer.toString(courseID);
        stats[1] = Integer.toString(numStudents);
        stats[2] = Double.toString(avgScore);

        statisticsUI.printStats(stats);
    }
}
