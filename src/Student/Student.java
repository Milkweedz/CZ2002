package Student;

import FileManager.FileManager;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Student {
    private int studentID;
    private String studentFName;
    private String studentLName;
	private int yearOfStudy;
    private int gender;
    private String department;
    private String graduate;
    private int NumOfAU;
    private double totalGPA, CGPA;

	public Student() {
		studentID = -1;
		studentFName = "";
		studentLName = "";
		yearOfStudy = -1;
		gender = -1;
        department = "";
        graduate = "";
        NumOfAU = -1;
        totalGPA = -1.0;
        CGPA = -1.0;
    }

    private static final String studentFile = "src\\Student\\students.txt";
    private static final String listFile = "src\\Student\\studentlist.txt";
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

    public static void deleteInFile(int studentID){
        FileManager.deleteInFile(studentID, studentFile, listFile);
    }


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

    public int getNumOfAU() {
        return NumOfAU;
    }

    public void setNumOfAU(int numOfAU) {
        NumOfAU = numOfAU;
    }

    public double getTotalGPA() {
        return totalGPA;
    }

    public void setTotalGPA(double totalGPA) {
        this.totalGPA = totalGPA;
    }

    public double getCGPA() {
        return CGPA;
    }

    public void setCGPA(double CGPA) {
        this.CGPA = CGPA;
    }

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
