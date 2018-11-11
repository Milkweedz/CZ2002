package Registration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Student.*;
import Course.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class RegistrationCtrl {


    public void init() {

        int choice;

        do {
            choice = RegistrationUI.registrationCtrlChoice();
            switch (choice) {
                case 1:
                    registerStudentForCourse();
                    break;
                case 2:
                    Registration.deleteInFile(RegistrationUI.readStudentID(new Scanner(System.in)), RegistrationUI.readCourseID(new Scanner(System.in)));
                    break;
                case 3:
                    List<Integer> courses = studentCourses(RegistrationUI.readStudentID(new Scanner(System.in)));
                    RegistrationUI.printCourses(courses);
                    break;
                case 4:
                    List<Integer> students = studentsInCourses(RegistrationUI.readCourseID(new Scanner(System.in)));
                    RegistrationUI.printStudents(students);
                    break;
                case 5:
                    studentsInTut();
                    break;
            }
        } while (choice < 6);    //look at studentUI, 5 happens to be the option to quit
    }

    public static void registerStudentForCourse() {
        Student student = new Student();
        int StudentID = RegistrationUI.readStudentID(new Scanner(System.in));
        if (!student.existsStudent(StudentID)) {
            StudentUI.studentIdNonexist();
        } else {
            student = Student.readInFile(StudentID);
            String FName, LName;
            Registration reg = new Registration();
            reg.getFirstName(student.getStudentFName());
            reg.getLastName(student.getStudentLName());
            int CourseID = RegistrationUI.readCourseID(new Scanner(System.in));
            if (!Course.existsCourse(CourseID))
                System.out.printf("Course Id Does Not Exist");
            else {
                Course course = Course.readInFile(CourseID);
                if (course.courseRegister() && !Registration.isInFile(StudentID, CourseID)) {
                    String courseName, coordinator;
                    reg.getCourseName(course.getCourseName());
                    reg.getCoordinator(course.getCoordinator());
                    reg.getCourseID(CourseID);
                    reg.getStudentID(StudentID);
                    if (Course.readInFile(CourseID).getType() != Course.COURSETYPE.Lec)
                        reg.gettutgrp(RegistrationUI.readTutGroup(CourseID));
                    Registration.saveToFile(reg);
                    RegistrationUI.successAdd();
                }

            }
        }
    }


    public static List<Integer> studentCourses(int StudentID) {
        List<Integer> courses = new ArrayList<Integer>();
        File readFile = null;
        BufferedReader br = null;
        try {
            readFile = new File("src\\Registration\\reglist.txt");
            br = new BufferedReader(new FileReader(readFile));
            String line = br.readLine();
            if (line == null || line.equals("")) {
                return courses;  //return empty list if reglist is empty
            }
            for (; line != null; line = br.readLine()) {
                String array1[] = line.split("\\.");
                if (Integer.parseInt(array1[0]) == StudentID)
                    courses.add(Integer.parseInt(array1[1]));
            }
            return courses;
            // System.out.println("Delete error. CourseID not found.");
        } catch (IOException ex) {
            System.out.println("IOException! reglist.txt not found?");
            ex.printStackTrace();
        } finally {
            try {
                if (readFile != null && br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return courses;
    }

    public static List<Integer> studentsInCourses(int courseID) {
        List<Integer> students = new ArrayList<Integer>();
        File readFile = null;
        BufferedReader br = null;
        try {
            readFile = new File("src\\Registration\\reglist.txt");
            br = new BufferedReader(new FileReader(readFile));
            String line = br.readLine();
            if (line == null || line.equals("")) {
                return students;  //return empty list if reglist is empty
            }
            for (; line != null; line = br.readLine()) {
                String array1[] = line.split("\\.");
                if (Integer.parseInt(array1[1]) == courseID)
                    students.add(Integer.parseInt(array1[0]));
            }
            return students;
            // System.out.println("Delete error. CourseID not found.");
        } catch (IOException ex) {
            System.out.println("IOException! reglist.txt not found?");
            ex.printStackTrace();
        } finally {
            try {
                if (readFile != null && br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return students;
    }

    public void studentsInTut(){
        RegistrationUI registrationUI = new RegistrationUI();
        int courseID = registrationUI.readCourseID(new Scanner(System.in));
        String queryGroup = registrationUI.readTutGroup(courseID);
        String tutGroup;
        int studentID;

        List<Integer> students = new ArrayList<Integer>();
        File readFile = null;
        BufferedReader br = null;
        try {
            readFile = new File("src\\Registration\\reglist.txt");
            br = new BufferedReader(new FileReader(readFile));
            String line = br.readLine();
            if (line != null && !line.equals("")) {
                for (; line != null; line = br.readLine()) {
                    String array1[] = line.split("\\.");
                    if (Integer.parseInt(array1[1]) == courseID){
                        tutGroup = Registration.getStudentGroup(Integer.parseInt(array1[0]), courseID);
                        //System.out.println("DEBUG" + tutGroup);
                        if (queryGroup.equals(tutGroup)) {
                            students.add(Integer.parseInt(array1[0]));
                        }
                    }
                }
            }
            registrationUI.printStudents(students);
            // System.out.println("Delete error. CourseID not found.");
        } catch (IOException ex) {
            System.out.println("IOException! reglist.txt not found?");
            ex.printStackTrace();
        } finally {
            try {
                if (readFile != null && br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



}
