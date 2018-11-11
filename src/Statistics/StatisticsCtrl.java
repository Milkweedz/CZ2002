package Statistics;

import Mark.MarksCtrl;
import Registration.RegistrationCtrl;

public class StatisticsCtrl {
    public static void init(){
        StatisticsUI statisticsUI = new StatisticsUI();
        int courseID = statisticsUI.readCourseID();
        int numStudents = RegistrationCtrl.studentsInCourses(courseID).size();
        double avgScore;
        double totalScore = 0.0;

        for(int student : RegistrationCtrl.studentsInCourses(courseID)){
            totalScore += MarksCtrl.retTotalPercentage(student, courseID);
        }
        avgScore = totalScore / numStudents;

        String[] stats = new String[3];
        stats[0] = Integer.toString(courseID);
        stats[1] = Integer.toString(numStudents);
        stats[2] = Double.toString(avgScore);

        statisticsUI.printStats(stats);
    }
}
