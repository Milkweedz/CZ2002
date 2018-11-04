package Student;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentCtrl {
    private static final int SUCCESS = 0;
    private static final int FAIL = 1;

    public int createStudent(String[] args){
        args = StudentUI.inputStudDetails();

        Student student = new Student();
        //int id, String fname, String lname, int gender, int studyYear, String department, boolean graduate

        //Convert string input to appropriate data types
        student.setStudentID(Integer.parseInt(args[0]));
        student.setStudentFName(args[1]);
        student.setStudentLName(args[2]);
        student.setGender(parseGender(args[3]));
        student.setYearOfStudy(Integer.parseInt(args[4]));
        student.setDepartment(args[5]);
        student.setGraduate(Integer.parseInt(args[6]));

        return Student.saveToFile(student);
    }

    private boolean patternMatching(String string, String patstring){
        Pattern pattern = Pattern.compile(patstring);
        Matcher matcher = pattern.matcher(string);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    private int parseGender(String string){
        String patstring = "\\d"; //pattern to match
        if (patternMatching(string, patstring)) return Integer.parseInt(string);
        else if (string.equalsIgnoreCase("male")) return 0;
        else if (string.equalsIgnoreCase("female")) return 1;
        else return 2;
    }

    private int parseGraduate(String string){
        if (patternMatching(string,"\\d"));
        else if (patternMatching(string,"\\w{2}")) {
            if (string.equalsIgnoreCase("ug")) return 0;
            else if (string.equalsIgnoreCase("pg")) return 1;
            else;
        }
        else;

        return -1;
    }
}


