package Registration;

import FileManager.FileManager;

import java.io.*;
import java.util.ArrayList;
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
        TutorialGroup = "";
        StudentID = 0;
        courseID = 0;
    }


    public static void saveToFile(Registration reg) {
        HashMap<String,String> obj = new HashMap<String,String>();
        obj.put("firstname", reg.retFirstName());
        obj.put("lastname", reg.retLastName());
        obj.put("coursename", reg.retCourseName());
        obj.put("coordinator", reg.retCoordinator());
        obj.put("tutorialGroup",reg.rettutGroup());
        FileManager.saveToFile(reg.retStudentID(),reg.retCourseID(), obj, registrationFile, listFile);
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
                if (nextID == String.valueOf(studentID)+"."+String.valueOf(courseID)){
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
