package Student;

import FileManager.FileManager;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * The Student class handles the data access related to student objects.
 *
 * @author Ng Man Chun
 * @version 1.0
 * @since 2018-11-15
 */
public class Student {

    /**
     * This is the unique ID for each student
     */
    private int studentID;

    /**
     * This is the student's first name
     */
    private String studentFName;

    /**
     * This is the student's last name
     */
    private String studentLName;

    /**
     * This is the student's year of study. It is bounded between 1 and 9 inclusive, because 1 is the minimum possible
     * and 9 is the maximum allowed by NTU
     */
    private int yearOfStudy;

    /**
     * This is the student's gender. It is set as an integer that represents one of three options: Male, Female, and Other
     * It is bounded between 1 and 3 inclusive.
     */
    private int gender;

    /**
     * This is the department of study for the student.
     */
    private String department;

    /**
     * This behaves like a boolean in that the student can only be UG (undergraduate) or PG (postgraduate).
     * The string can only be either UG or PG. It is always stored in upper case.
     */
    private String graduate;


    //private int NumOfAU;
    //private double totalGPA, CGPA;

    /**
     * This attribute stores the directory of the file which stores student details
     */
    private static final String studentFile = "src\\Student\\students.txt";

    /**
     * This attribute stores the directory of the file which stores the list of students.
     */
    private static final String listFile = "src\\Student\\studentlist.txt";

    /**
     * This constructor creates a student and initializes the attributes. The student object created can then be
     * passed between classes.
     */
	public Student() {
		studentID = -1;
		studentFName = "";
		studentLName = "";
		yearOfStudy = -1;
		gender = -1;
        department = "";
        graduate = "";
        //NumOfAU = -1;
        //totalGPA = -1.0;
        //CGPA = -1.0;
    }

    /**
     * This method checks if a student exists by scanning the student list file
     * @param studentID
     * @return boolean, true if student exists
     */
    public static boolean existsStudent(int studentID){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src\\Student\\studentlist.txt"));
            String nextID = br.readLine();
            while (nextID != null){
                if (Integer.parseInt(nextID) == studentID){
                    return true;
                }
                nextID = br.readLine();
            }
            return false;
        } catch (IOException ex){
            System.out.println("IOException! studentlist.txt not found?");
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try { br.close(); }
                catch (IOException ex) {ex.printStackTrace();}
            }
        }
        return false;
    }

    /**
     * This method returns an array of all student IDs that are added in the application
     * @return list of student IDs
     */
    public static ArrayList<String> listStudents(){
        BufferedReader br = null;
        ArrayList<String> studentList = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader("src\\Student\\studentlist.txt"));
            String nextID = br.readLine();
            while (nextID != null){
                studentList.add(nextID);
                nextID = br.readLine();
            }
            return studentList;
        } catch (IOException ex){
            System.out.println("IOException! studentlist.txt not found?");
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try { br.close(); }
                catch (IOException ex) {ex.printStackTrace();}
            }
        }
        return null;
    }

    /**
     * This method takes a student object and converts it to a hashmap, which is then written to file by FileManager.FileManager
     * @param student
     */
    public static void saveToFile(Student student){
        HashMap<String,String> obj = new HashMap<String,String>();
        obj.put("fname", student.getStudentFName());
        obj.put("lname", student.getStudentLName());
        obj.put("year", Integer.toString(student.getYearOfStudy()));
        obj.put("gender", Integer.toString(student.getGender()));
        obj.put("department", student.getDepartment());
        obj.put("graduate", student.getGraduate());

        FileManager.saveToFile(student.studentID, obj, studentFile, listFile);
    }

    /**
     * This method calls FileManager.FileManager to delete a student
     * @param studentID
     */
    public static void deleteInFile(int studentID){
        FileManager.deleteInFile(studentID, studentFile, listFile);
    }

    /**
     * This method takes a student object, converts it to a hashmap, and then calls FileManager.FileManager to overwrite
     * the existing object.
     * @param student
     */
    public static void editFile(Student student){
        HashMap<String,String> obj = new HashMap<String,String>();
        //obj.put("studentid", Integer.toString(student.getStudentID()));
        obj.put("fname", student.getStudentFName());
        obj.put("lname", student.getStudentLName());
        obj.put("year", Integer.toString(student.getYearOfStudy()));
        obj.put("gender", Integer.toString(student.getGender()));
        obj.put("department", student.getDepartment());
        obj.put("graduate", student.getGraduate());

        FileManager.editFile(student.studentID, obj, studentFile, listFile);
    }

    /**
     * This method takes a student ID and asks the FileManager.FileManager to retrieve the hashmap object of the student
     * from file. It then converts the hashmap to a student object.
     * @param studentID
     * @return student object
     */
    public static Student readInFile(int studentID){
        HashMap<String,String> obj = FileManager.accessFile(studentID, studentFile);

        Student student = new Student();
        student.setStudentID(studentID);
        student.setStudentFName((String) obj.get("fname"));
        student.setStudentLName((String) obj.get("lname"));
        student.setYearOfStudy(Integer.parseInt((String) obj.get("year")));
        student.setGender(Integer.parseInt((String) obj.get("gender")));
        student.setDepartment((String) obj.get("department"));
        student.setGraduate((String) obj.get("graduate"));

        return student;
    }


    //getters and setters below
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentFName() {
        return studentFName;
    }

    public void setStudentFName(String studentFName) {
        this.studentFName = studentFName;
    }

    public String getStudentLName() {
        return studentLName;
    }

    public void setStudentLName(String studentLName) {
        this.studentLName = studentLName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

//    public int getNumOfAU() {
//        return NumOfAU;
//    }
//
//    public void setNumOfAU(int numOfAU) {
//        NumOfAU = numOfAU;
//    }
//
//    public double getTotalGPA() {
//        return totalGPA;
//    }
//
//    public void setTotalGPA(double totalGPA) {
//        this.totalGPA = totalGPA;
//    }
//
//    public double getCGPA() {
//        return CGPA;
//    }
//
//    public void setCGPA(double CGPA) {
//        this.CGPA = CGPA;
//    }

//	public void printStudDetails() {
//		System.out.print(String.format("|%-1d.%30s|", 1, " Student.Student ID "));
//		System.out.println(String.format(" %-30d|", Student_ID));
//		System.out.print(String.format("|%-1d.%30s|", 2, " Student.Student's Name "));
//		System.out.println(String.format(" %-30s|", StudentLName + "," + StudentFName));
//		System.out.print(String.format("|%-1d.%30s|", 3, " Year Of Study "));
//		System.out.println(String.format(" %-30d|", YearOfStudy));
//		System.out.print(String.format("|%-1d.%30s|", 4, " Number of AUs obtained "));
//		System.out.println(String.format(" %-30d|", NumOfAU));
//		System.out.print(String.format("|%-1d.%30s|", 5, " CGPA "));
//		System.out.println(String.format(" %-30.2f|", CGPA));
//		System.out.print(String.format("|%-1d.%30s|", 6, "Gender "));
//		System.out.println(String.format(" %-30s|", gender));
//		System.out.print(String.format("|%-1d.%30s|", 7, "Department of study "));
//		System.out.println(String.format(" %-30s|", department));
//		System.out.print(String.format("|%-1d.%30s|", 8, "Level Of Study "));
//		System.out.println(String.format(" %-30s|", level));
//	}

}
