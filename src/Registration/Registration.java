package Registration;

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

    public Registration() {
        FirstName = "";
        LastName = "";
        CourseName = "";
        Coordinator = "";
        TutorialGroup = null;
        StudentID = 0;
        courseID = 0;
    }


    public static void saveToFile(Registration reg) {
        HashMap<String,String> obj = new HashMap<String,String>();
        obj.put("firstname", reg.getFirstName());
        obj.put("lastname", reg.getLastName());
        obj.put("coursename", reg.getCourseName());
        obj.put("coordinator", reg.getCoordinator());
        obj.put("tutorialGroup",reg.gettutGroup());
        FileManager.saveToFile(reg.getStudentID(),reg.getCourseID(), obj, registrationFile, listFile);
    }


    public static void deleteInFile(int studentID, int courseID) {
        if(isInFile(studentID,courseID)){
        FileManager.deleteInFile(studentID,courseID, registrationFile, listFile);
        RegistrationUI.successRemove();}
        else
            RegistrationUI.studentCourseNotExist();
    }

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
