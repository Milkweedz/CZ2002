package Student;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        String studentFile = "src\\Student\\students.json";
        JSONObject file = readJSON(studentFile);
        //JSONArray array = (JSONArray) file.get("data");

        JSONObject obj = new JSONObject();
        //obj.put("studentid", Integer.toString(student.getStudentID()));
        obj.put("fname", student.getStudentFName());
        obj.put("lname", student.getStudentLName());
        obj.put("year", Integer.toString(student.getYearOfStudy()));
        obj.put("gender", Integer.toString(student.getGender()));
        obj.put("department", student.getDepartment());
        obj.put("graduate", student.getGraduate());

        file.put(Integer.toString(student.getStudentID()), obj);
        //file.replace("data", array);

        writeJSON(file, studentFile);

        //add new student to studentid cache
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\Student\\studentlist.txt", true))){
            bw.write(student.getStudentID()+"\n");
        } catch (IOException ex){
            System.out.println("IOException! studentlist.txt not found?");
            ex.printStackTrace();
        }

    }

    public static void deleteInFile(int studentID){
        String studentFile = "src\\Student\\students.json";
        JSONObject file = readJSON(studentFile);
        file.remove(Integer.toString(studentID));

        writeJSON(file, studentFile);


        File readFile = null;
        File tempFile = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            //remove entry from student list file
            readFile = new File("src\\Student\\studentlist.txt");
            tempFile = File.createTempFile("file",".txt", readFile.getParentFile());
            br = new BufferedReader(new FileReader(readFile));
            bw = new BufferedWriter(new FileWriter(tempFile));

            for (String line=""; line != null; line = br.readLine()){
                if (!line.equals(Integer.toString(studentID))){
                    bw.write(line);
                }
            }
            //System.out.println("Delete error. StudentID not found.");
        } catch (IOException ex){
            System.out.println("IOException! studentlist.txt not found?");
            ex.printStackTrace();
        } finally {
            try {
                if (readFile != null && tempFile != null && br != null && bw != null) {
                    br.close();
                    bw.close();
                    readFile.delete();
                    tempFile.renameTo(readFile);
                }
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static void editFile(Student student){
        String studentFile = "src\\Student\\students.json";
        JSONObject file = readJSON(studentFile);
        file.remove(Integer.toString(student.studentID));
        //JSONArray array = (JSONArray) file.get("data");

        JSONObject obj = new JSONObject();
        //obj.put("studentid", Integer.toString(student.getStudentID()));
        obj.put("fname", student.getStudentFName());
        obj.put("lname", student.getStudentLName());
        obj.put("year", Integer.toString(student.getYearOfStudy()));
        obj.put("gender", Integer.toString(student.getGender()));
        obj.put("department", student.getDepartment());
        obj.put("graduate", student.getGraduate());

        file.put(Integer.toString(student.getStudentID()), obj);
        //file.replace("data", array);

        writeJSON(file, studentFile);
    }

    public static Student readInFile(int studentID){
        String studentFile = "src\\Student\\students.json";
        JSONObject file = readJSON(studentFile);
        JSONObject obj = (JSONObject) file.get(Integer.toString(studentID));

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

    //either returns JSON file object or null.
    private static JSONObject readJSON(String fileName){
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(new FileReader(fileName));
        } catch (IOException ex) {
            System.out.println("IOException!");
            ex.printStackTrace();
        } catch (ParseException parsex) {
            System.out.println("ParseException!");
            //parsex.printStackTrace();
        }
        return new JSONObject();
    }

    private static void writeJSON(JSONObject file, String fileName){

        try {
            FileWriter writer = new FileWriter(fileName, false);
            file.writeJSONString(writer);
            writer.close();
        } catch (IOException ex) {
            System.out.println("IOException!");
            ex.printStackTrace();
        }
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
