package Mark;

import Registration.RegistrationCtrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class BellCurve {

public int[] retGradeMarks(int courseID)
    {
        List<Integer> students = RegistrationCtrl.studentsInCourses(courseID);
        int count[] = new int[100];
        Arrays.fill(count,0);
        for(int i=0;i< students.size();i++)
        {
            Marks marks=Marks.readInFile(students.get(i),courseID);
            count[(int)marks.retTotalPercentage()]++;
        }
        return count;
    }
    public double retMean(int[] marksCount)
    {
        double mean=0.0;
        for(int i=0;i<100;i++)
        {
            mean+=(i+0.5)*marksCount[i];
        }
        return mean/100;
    }
//    public double retStandardDeviation(int[] marksCount,double mean)
//    {
//        double standardDeviation = 0.0;
//        for(int i=0;i<100;i++)
//        {
//
//        }
//    }
}
