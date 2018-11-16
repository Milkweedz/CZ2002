package Registration;
/**
 * This class is responsible for controlling all operations with regards to registration which include input/output options and file reading and writing
 * @author Thomas Stephen Felix
 * @version 1.0
 * @Date 2018-11-15
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Student.*;
import Course.*;
public class RegistrationCtrl {

    /**
     * Does a registration functionality based on the input entered by the user
     */
    public void init() {
        RegistrationUI regUI = new RegistrationUI();
        int choice;

        do {
            choice = regUI.registrationCtrlChoice();
            switch (choice) {
                case 1:
                    registerStudentForCourse();
                    break;
                case 2:
                    deregisterStudentForCourse();
                    break;
                case 3:
                    int stdid;
                    if(Student.existsStudent(stdid=regUI.readStudentID()))
                    {List<Integer> courses = studentCourses(stdid);
                    RegistrationUI.printCourses(courses);}
                    else
                       RegistrationUI.studentNotExist();
                    break;
                case 4:
                    int crsid;
                    if(Course.existsCourse(crsid=regUI.readCourseID())){
                    List<Integer> students = studentsInCourses(crsid);
                    RegistrationUI.printStudents(students);}
                    else
                        RegistrationUI.courseNotExist();
                    break;
                case 5:
                    studentsInTut();
                    break;
            }
        } while (choice < 6);    //look at studentUI, 5 happens to be the option to quit
    }
    /**
     * Registers a student for the course by
     * 1. getting a valid student ID, course ID
     * 2. checking if record already exists
     * 3. saving the registration in the file
     */
    public static void registerStudentForCourse() {
        Student student = new Student();
        RegistrationUI regUI = new RegistrationUI();
        int StudentID = regUI.readStudentID();
        if (!student.existsStudent(StudentID)) {
            StudentUI.studentIdNonexist();
        } else {
            student = Student.readInFile(StudentID);
            String FName, LName;
            Registration reg = new Registration();
            reg.setFirstName(student.getStudentFName());
            reg.setLastName(student.getStudentLName());
            int CourseID = regUI.readCourseID();
            if (!Course.existsCourse(CourseID))
                System.out.printf("Course Id Does Not Exist");
            else {
                Course course = Course.readInFile(CourseID);
                if (course.courseRegister() && !Registration.isInFile(StudentID, CourseID)) {
                    reg.setCourseName(course.getCourseName());
                    reg.setCoordinator(course.getCoordinator());
                    reg.setCourseID(CourseID);
                    reg.setStudentID(StudentID);
                    if (Course.readInFile(CourseID).getType() != Course.COURSETYPE.Lec)
                        reg.settutgrp(RegistrationUI.readTutGroup(CourseID));
                    Registration.saveToFile(reg);
                    RegistrationUI.successAdd();
                }

            }
        }
    }

    /**
     * Deregister a student for a course by calling deleteInFile function in registration class and updates course details if de-registration is  successful
     *
     */
    public static void deregisterStudentForCourse(){
        RegistrationUI regUI = new RegistrationUI();
        boolean success;            //successful deregister

        int courseID = regUI.readCourseID();
        success = Registration.deleteInFile(regUI.readStudentID(), courseID);


        if(success) {
            Course course = Course.readInFile(courseID);
            course.courseDeregister();
        }
    }

    /**
     * This method returns course IDs of courses registered by a student
     * @param StudentID
     * @return List containing course IDs
     */
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

    /**
     * This method returns a list of student i.e their student IDs, registered for a particular course
     * @param courseID
     * @return List containing studen IDs
     */
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

    /**
     * This method is responsible for finding the students in a particular tutorial group and then printing their student IDs
     */
    public void studentsInTut() {
        RegistrationUI registrationUI = new RegistrationUI();
        int courseID = registrationUI.readCourseID();
        if (Course.existsCourse(courseID)) {
            Course course = Course.readInFile(courseID);
            if (course.getType() != Course.COURSETYPE.Lec) {
                String queryGroup = registrationUI.readTutGroup(courseID);
                String tutGroup;
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
                            if (Integer.parseInt(array1[1]) == courseID) {
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
            } else RegistrationUI.courseNoTutorials();
        } else
            RegistrationUI.courseNotExist();
    }


}
