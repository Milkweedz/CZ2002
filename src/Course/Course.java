package Course;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import FileManager.FileManager;

/**
 * Represents the course entity
 * Contains the details of each course
 * Responsible for any changes or operations with respect to all the courses in the system
 * @author Nikhita
 * @version 1.0
 * @since 2018-11-15
 */
public class Course {
	/**
	 * Unique ID of the course
	 */
    private int courseID;

    /**
     * Name of the course
     */
    private String courseName;

    /**
     * Name of coordinator of the course
     */
    private String coordinator;

    public enum COURSETYPE {Lec, LecTut, LecTutLab, NULL};
    
    private COURSETYPE type;

    /**
     * Capacity of the course (number of students it can take)
     */
    private int capacity;

    /**
     * Number of students
     */
    private int noOfStudents;

    /**
     * List of tutorials of the course as an ArrayList (String)
     */
    private ArrayList<String> tutorials;

    /**
     * File name of file that stores courses in the directory
     */
    private static final String courseFile = "src\\Course\\courses.txt";

    /**
     * File name of course list in the directory
     */
    private static final String listFile = "src\\Course\\courselist.txt";


    //private HashMap<String, String> markWeights;
    /**
     * Constructor to initialize class variables
     */
    public Course(){
        courseID = -1;
        courseName = "";
        coordinator = "";
        type = COURSETYPE.NULL;
        capacity = -1;
        noOfStudents = 0;
        tutorials = new ArrayList<>();
    }

    /**
     * Check if course exists based on given course ID
     * @param courseID Id of the course which is to be checked
     * @return Boolean value true if course exists and false if it does not
     */
    public static boolean existsCourse(int courseID){
//        String courseList = "src\\Course\\courselist.txt";
//        JSONObject file = readJSON(courseList);
//        if(file.containsKey(Integer.toString(courseID))) return true;
//        else return false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src\\Course\\courselist.txt"));
            String nextID = br.readLine();
            while (nextID != null){
                if (Integer.parseInt(nextID) == courseID){
                    return true;
                }
                nextID = br.readLine();
            }
            return false;
        } catch (IOException ex){
            System.out.println("IOException! courselist.txt not found?");
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
     * Lists Courses
     * @return The list of all the courses in the system
     */
    public static ArrayList<String> listCourses(){
        BufferedReader br = null;
        ArrayList<String> courseList = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader("src\\Course\\courselist.txt"));
            String nextID = br.readLine();
            while (nextID != null){
                courseList.add(nextID);
                nextID = br.readLine();
            }
            return courseList;
        } catch (IOException ex){
            System.out.println("IOException! courselist.txt not found?");
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
     * Saves the course and its details to the file 
     * @param course Object of the Course class
     */
    public static void saveToFile(Course course){

        //create map object for course
        HashMap<String,String> obj = new HashMap<String,String>();
        //obj.put("courseid", Integer.toString(course.getCourseID()));
        obj.put("coursename", course.getCourseName());
        obj.put("coordinator", course.getCoordinator());
        obj.put("type", translateType(course.getType()));
        obj.put("capacity", Integer.toString(course.getCapacity()));
        obj.put("numstudents", Integer.toString(course.getNoOfStudents()));
        obj.put("nooftut",Integer.toString(course.getTutorialGroups().size()));
        for(int i=0;i<course.getTutorialGroups().size();i++)
            obj.put("tut"+Integer.toString(i),course.getTutorialGroups().get(i));

        FileManager.saveToFile(course.courseID, obj, courseFile, listFile);
    }
    /**
     * Adds the list of tutorial groups
     * @param tutorialGroups List of tutorial groups
     */
    public void addTutorialGroups(ArrayList<String> tutorialGroups)
    {
        tutorials = tutorialGroups;
    }
    /**
     * Deletes a course from the file
     * @param courseID Id of the course to be deleted
     */
    public static void deleteInFile(int courseID){
        FileManager.deleteInFile(courseID, courseFile, listFile);
    }
    /**
     * Edits details of course in the file
     * @param course Object of the Course class
     */
    public static void editFile(Course course){
        HashMap<String,String> obj = new HashMap<String,String>();
        //obj.put("courseid", Integer.toString(course.getCourseID()));
        obj.put("coursename", course.getCourseName());
        obj.put("coordinator", course.getCoordinator());
        obj.put("type", translateType(course.getType()));
        obj.put("capacity", Integer.toString(course.getCapacity()));
        obj.put("numstudents", Integer.toString(course.getNoOfStudents()));
        obj.put("nooftut",Integer.toString(course.getTutorialGroups().size()));
        for(int i=0;i<course.getTutorialGroups().size();i++)
            obj.put("tut"+Integer.toString(i),course.getTutorialGroups().get(i));

        FileManager.editFile(course.courseID, obj, courseFile, listFile);
    }
    /**
     * Reads course details from the file
     * @param courseID Id of the course to be read in
     * @return Returns details of the course
     */
    public static Course readInFile(int courseID){
        HashMap<String,String> obj = FileManager.accessFile(courseID, courseFile);

        Course course = new Course();
        course.setCourseID(courseID);
        course.setCourseName((String) obj.get("coursename"));
        course.setCoordinator((String) obj.get("coordinator"));
        course.setType(translateType((String) obj.get("type")));
        course.setCapacity(Integer.parseInt((String) obj.get("capacity")));
        course.setNoOfStudents(Integer.parseInt((String) obj.get("numstudents")));


        Object numTut = obj.get("nooftut");
        if (numTut != null) {
            int noOfTut = Integer.parseInt((String) numTut);
            ArrayList<String> tutorialNames = new ArrayList<>();
            for (int i = 0; i < noOfTut; i++)
                tutorialNames.add((String) obj.get("tut" + Integer.toString(i)));
            course.addTutorialGroups(tutorialNames);
        }

        return course;
    }
    /**
     * Gets marks weightage of the course
     * @param courseID id of the course
     * @return Marks weights data as HashMap
     */
    public static HashMap<String, String> getMarkWeights(int courseID){
        HashMap<String,String> file = FileManager.readFile(courseFile);

        String objString = file.get(Integer.toString(courseID));
        HashMap<String,String> obj = FileManager.decompressMap(objString);

        if (obj.containsKey("markweights")){
            String markObjStr = obj.get("markweights");
            return FileManager.decompressMap(markObjStr, true);
        }
        else {
            return null;
        }
    }
    /**
     * Sets marks weightage of the course
     * @param courseID Id of the course 
     * @param markWeights Marks weights data as HashMap
     */
    public static void setMarkWeights(int courseID, HashMap<String, String> markWeights){
        HashMap<String,String> file = FileManager.readFile(courseFile);

        String objString = file.get(Integer.toString(courseID));
        HashMap<String,String> obj = FileManager.decompressMap(objString);

        HashMap<String,String> markObj;
        if (obj.containsKey("markweights")){
            obj.remove("markweights");
        }


        String markWeightStr = FileManager.compressMap(markWeights, true);
        obj.put("markweights", markWeightStr);
        System.out.println("Your input: " + markWeightStr);
        file.replace(Integer.toString(courseID), FileManager.compressMap(obj));

        FileManager.writeFile(file, courseFile);
    }


    /**
     * Translates the type of the course from String to COURSETYPE
     * @param tempType Type of the course as String
     * @return Type of the course as COURSETYPE
     */
    private static COURSETYPE translateType(String tempType){
        COURSETYPE type = COURSETYPE.NULL;
        //following converts string input of type to valid COURSETYPE
        if (tempType.equalsIgnoreCase("lec")) type = COURSETYPE.Lec;
        if (tempType.equalsIgnoreCase("tut")) type = COURSETYPE.LecTut;
        if (tempType.equalsIgnoreCase("lab")) type = COURSETYPE.LecTutLab;

        return type;
    }
    /**
     * Translates the type of the course from COURSETYPE to String
     * @param type Type of the course as COURSETYPE
     * @return Type of the course as String
     */
    private static String translateType(COURSETYPE type){
        String tempType = "";

        if (type == COURSETYPE.Lec) tempType = "lec";
        if (type == COURSETYPE.LecTut) tempType = "tut";
        if (type == COURSETYPE.LecTutLab) tempType = "lab";

        return tempType;
    }


    //getters and setters
    /**
     * Gets the unique id of the course
     * @return The course id
     */
    public int getCourseID() {
        return courseID;
    }
    /**
     * Sets the id of the course from parameter
     * @param courseID The unique id of the course to be set
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    /**
     * Gets the name of the course
     * @return The name of the course
     */
    public String getCourseName() {
        return courseName;
    }
    /**
     * Sets the name of the course from parameter
     * @param courseName The name of the course
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    /**
     * Gets the name of the course coordinator
     * @return The name of the course coordinator
     */
    public String getCoordinator() {
        return coordinator;
    }

    /**
     * Sets the name of the course coordinator from parameter
     */
    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    /**
     * Gets the type of the course (lecture, tutorial, lab)
     * @return The type of the course (lecture, tutorial, lab)
     */
    public COURSETYPE getType() {
        return type;
    }

    /**
     * Sets the type of the course (lecture, tutorial, lab) from parameter
     */
    public void setType(COURSETYPE type) {
        this.type = type;
    }

    /**
     * Gets the capacity of the course
     * @return The capacity of the course
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the list of tutorial groups of the course
     * @return The list of tutorial groups of the course
     */
    public ArrayList<String> getTutorialGroups() {
        return tutorials;
    }

    /**
     * Sets the capacity of the course from parameter
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the number of students in the course
     * @return The number of students in the course
     */
    public int getNoOfStudents() {
        return noOfStudents;
    }

    /**
     * Sets the number of students in the course from parameter
     */
    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    /**
     * Registers a student into the course
     * @return Boolean value true if registration is successful and false if it is not
     */
    public boolean courseRegister()
    {
        if(noOfStudents<capacity)
    	{
    	    noOfStudents++;
    	    editFile(this);
    	    return true;
    	}
        else
        {
            System.out.println("Full Capacity! ");
            return false;
        }
    }

    /**
     * Deregisters or removes a student from the course
     */
    public void courseDeregister()
    {
            noOfStudents--;
            editFile(this);
            //already check if student exists in Registration.java
    }


//    public HashMap<String, String> getMarkWeightage() {
//        return markWeightage;
//    }
//
//    public void setMarkWeightage(HashMap<String, String> markWeightage) {
//        this.markWeightage = markWeightage;
//    }
    //!getters and setters

}
