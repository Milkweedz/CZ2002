package Registration;
/**
 * This class is responsible for all file handling methods and holds all details important to a student's registration to a course
 * @author Thomas Stephen Felix
 * @version 1.0
 * @date 2018-11-15
 *
 *
 * @param FirstName - first name of student
 * @param LastName - last name of student
 * @param CourseName - name of course taken
 * @param Coordinator - name of coordinator of course
 * @param TutorialGroup - tutorial group in which student is registered
 * @param StudentID
 * @param courseID
 * @param registrationFile - holds the text file in which all details with respect to registration is stored
 * @param listFile - holds the text file in which he index values of different registrations are stored
 *
 */

import FileManager.FileManager;

import java.io.*;
import java.util.HashMap;

public class Registration {
    private String FirstName;
    private String LastName;
    private String CourseName;
    private String Coordinator;
    private String TutorialGroup;
    private int StudentID;
    private int courseID;

    private static final String registrationFile = "src\\Registration\\registration.txt";
    private static final String listFile = "src\\Registration\\reglist.txt";

    /**
     * Constructor method that initialised value f all variables
     */
    public Registration() {
        FirstName = "";
        LastName = "";
        CourseName = "";
        Coordinator = "";
        TutorialGroup = null;
        StudentID = 0;
        courseID = 0;
    }

    /**
     * Saves the registration to the files after obtaining all required information
     * @param reg
     */
    public static void saveToFile(Registration reg) {
        HashMap<String,String> obj = new HashMap<String,String>();
        obj.put("firstname", reg.getFirstName());
        obj.put("lastname", reg.getLastName());
        obj.put("coursename", reg.getCourseName());
        obj.put("coordinator", reg.getCoordinator());
        obj.put("tutorialGroup",reg.gettutGroup());
        FileManager.saveToFile(reg.getStudentID(),reg.getCourseID(), obj, registrationFile, listFile);
    }

    /**
     * This method removes a student from the file after checking if the registration exists or not and returns true if de-registration is successful
     * @param studentID
     * @param courseID
     * @return boolean
     */
    public static boolean deleteInFile(int studentID, int courseID) {
        if(isInFile(studentID,courseID)){
        FileManager.deleteInFile(studentID,courseID, registrationFile, listFile);
        RegistrationUI.successRemove();
        return true;
        }
        else {
            RegistrationUI.studentCourseNotExist();
            return false;
        }
    }

    /**
     * This method reads to the listFile and returns true if registration ID exists else returns false
     * @param studentID
     * @param courseID
     * @return boolean
     */
    public static boolean isInFile(int studentID, int courseID) {
//        String courseList = "src\\Course\\courselist.txt";
//        JSONObject file = readJSON(courseList);
//        if(file.containsKey(Integer.toString(courseID))) return true;
//        else return false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(listFile));
            String nextID = br.readLine();

            while (nextID != null){
                String array1[] = nextID.split("\\.");
                if (Integer.valueOf(array1[0])==studentID&&Integer.valueOf(array1[1])==courseID){
                    return true;
                }
                nextID = br.readLine();
            }
            return false;
        } catch (IOException ex){
            System.out.println("IOException! reglist.txt not found?");
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
     * Returns tutorial group in which student is registered in if it exists
     * @param studentID
     * @param courseID
     * @return tutorial group ID
     */

    public static String getStudentGroup(int studentID, int courseID){
        String tutGroup;

        HashMap<String,String> obj = FileManager.accessFile(studentID,courseID,registrationFile);

        if (obj==null){
            return null;
        }

        tutGroup = obj.get("tutorialGroup");
        if (tutGroup == null){
            return null;
        } else {
            return tutGroup;
        }
    }


    /*
     * Accessors and Mutator
     */

    public void setFirstName(String fname) {
        FirstName = fname;
    }

    public void setLastName(String lname) {
        LastName = lname;
    }

    public void setCourseName(String cname) {
        CourseName = cname;
    }

    public void setCoordinator(String coordinatorName) {
        Coordinator = coordinatorName;
    }

    public void settutgrp(String tutgrp) {
        TutorialGroup = tutgrp;
    }

    public void setStudentID(int stdid) {
        StudentID = stdid;
    }

    public void setCourseID(int crsid) {
        courseID = crsid;
    }
    public String getLastName() {
        return LastName;
    }
    public String getFirstName() {
        return FirstName;
    }
    public String getCourseName() {
        return CourseName;
    }
    public String getCoordinator() {
        return Coordinator;
    }
    public String gettutGroup() {
        return TutorialGroup;
    }
    public int getStudentID() {
        return StudentID;
    }
    public int getCourseID() {
        return courseID;
    }



}
