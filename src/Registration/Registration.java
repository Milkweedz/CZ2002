package Registration;

import Course.Course;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Registration {
    private String FirstName;
    private String LastName;
    private String CourseName;
    private String Coordinator;
    private String TutorialGroup;
    private int StudentID;
    private int courseID;

    public Registration() {
        FirstName = "";
        LastName = "";
        CourseName = "";
        Coordinator = "";
        TutorialGroup = "";
        StudentID = 0;
        courseID = 0;
    }

    public static void saveToFile(Registration reg) {
        String regFile = "src\\Registration\\Registration.json";
        JSONObject file = readJSON(regFile);
        // JSONArray array = (JSONArray) file.get("data");
        JSONObject obj = new JSONObject();
        obj.put("firstname", reg.retFirstName());
        obj.put("lastname", reg.retLastName());
        obj.put("coursename", reg.retCourseName());
        obj.put("coordinator", reg.retCoordinator());
        obj.put("tutorialGroup",reg.rettutGroup());
        file.put(Integer.toString(reg.retStudentID()) + "." + Integer.toString(reg.retCourseID()), obj);
        // file.replace("data", array);

        writeJSON(file, regFile);

        // add new course to courseid cache
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\Registration\\reglist.txt", true))) {
            bw.write(reg.retStudentID() + "." + reg.retCourseID() + "\n");
        } catch (IOException ex) {
            System.out.println("IOException! reglist.txt not found?");
            ex.printStackTrace();
        }
    }

    // either returns JSON file object or null.
    private static JSONObject readJSON(String fileName) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(new FileReader(fileName));
        } catch (IOException ex) {
            System.out.println("IOException!");
            ex.printStackTrace();
        } catch (ParseException parsex) {
            System.out.println("ParseException!");
            parsex.printStackTrace();
        }
        return new JSONObject();
    }

    private static void writeJSON(JSONObject file, String fileName) {

        try {
            FileWriter writer = new FileWriter(fileName, false);
            file.writeJSONString(writer);
            writer.close();
        } catch (IOException ex) {
            System.out.println("IOException!");
            ex.printStackTrace();
        }
    }

    public static void deleteInFile(int studentID, int courseID) {
        String courseFile = "src\\Registration\\Registration.json";
        JSONObject file = readJSON(courseFile);
        file.remove(Integer.toString(studentID) + "." + Integer.toString(courseID));

        writeJSON(file, courseFile);

        File readFile = null;
        File tempFile = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            // remove entry from course list file
            readFile = new File("src\\Registration\\reglist.txt");
            tempFile = File.createTempFile("file", ".txt", readFile.getParentFile());
            br = new BufferedReader(new FileReader(readFile));
            bw = new BufferedWriter(new FileWriter(tempFile));

            for (String line = ""; line != null; line = br.readLine()) {
                if (!line.equals(Integer.toString(studentID) + "." + Integer.toString(courseID))) {
                    bw.write(line);
                }
            }
            RegistrationUI.successRemove();
            // System.out.println("Delete error. CourseID not found.");
        } catch (IOException ex) {
            System.out.println("IOException! reglist.txt not found?");
            ex.printStackTrace();
        } finally {
            try {
                if (readFile != null && tempFile != null && br != null && bw != null) {
                    br.close();
                    bw.close();
                    readFile.delete();
                    tempFile.renameTo(readFile);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean isInFile(int studentID, int courseID) {
        String courseFile = "src\\Registration\\Registration.json";
        JSONObject file = readJSON(courseFile);
        JSONObject obj = (JSONObject) file.get(Integer.toString(studentID) + "." + Integer.toString(courseID));

        if (obj == null)
            return false;
        else
            return true;
    }

    public void getFirstName(String fname) {
        FirstName = fname;
    }

    public void getLastName(String lname) {
        LastName = lname;
    }

    public void getCourseName(String cname) {
        CourseName = cname;
    }

    public void getCoordinator(String coordinatorName) {
        Coordinator = coordinatorName;
    }

    public void gettutgrp(String tutgrp) {
        TutorialGroup = tutgrp;
    }

    public void getStudentID(int stdid) {
        StudentID = stdid;
    }

    public void getCourseID(int crsid) {
        courseID = crsid;
    }
    public String retLastName() {
        return LastName;
    }
    public String retFirstName() {
        return FirstName;
    }
    public String retCourseName() {
        return CourseName;
    }
    public String retCoordinator() {
        return Coordinator;
    }
    public String rettutGroup() {
        return TutorialGroup;
    }
    public int retStudentID() {
        return StudentID;
    }
    public int retCourseID() {
        return courseID;
    }



}
