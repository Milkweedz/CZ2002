package Course;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import FileManager.FileManager;


public class Course {
    private int courseID;
    private String courseName;
    private String coordinator;
    public enum COURSETYPE {Lec, LecTut, LecTutLab, NULL};
    private COURSETYPE type;
    private int capacity;
    private int noOfStudents;
    private ArrayList<String> tutorials;

    private static final String courseFile = "src\\Course\\courses.txt";
    private static final String listFile = "src\\Course\\courselist.txt";

    //private HashMap<String, String> markWeights;

    public Course(){
        courseID = -1;
        courseName = "";
        coordinator = "";
        type = COURSETYPE.NULL;
        capacity = -1;
        noOfStudents = 0;
        tutorials = new ArrayList<>();
    }

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

    public static void saveToFile(Course course){

        //create map object for course
        HashMap<String,String> obj = new HashMap<String,String>();
        //obj.put("courseid", Integer.toString(course.getCourseID()));
        obj.put("coursename", course.getCourseName());
        obj.put("coordinator", course.getCoordinator());
        obj.put("type", translateType(course.getType()));
        obj.put("capacity", Integer.toString(course.getCapacity()));
        obj.put("nooftut",Integer.toString(course.getTutorialGroups().size()));
        for(int i=0;i<course.getTutorialGroups().size();i++)
            obj.put("tut"+Integer.toString(i),course.getTutorialGroups().get(i));

        FileManager.saveToFile(course.courseID, obj, courseFile, listFile);
    }

    public void addTutorialGroups(ArrayList<String> tutorialGroups)
    {
        tutorials = tutorialGroups;
    }

    public static void deleteInFile(int courseID){
        FileManager.deleteInFile(courseID, courseFile, listFile);
    }

    public static void editFile(Course course){
        HashMap<String,String> obj = new HashMap<String,String>();
        //obj.put("courseid", Integer.toString(course.getCourseID()));
        obj.put("coursename", course.getCourseName());
        obj.put("coordinator", course.getCoordinator());
        obj.put("type", translateType(course.getType()));
        obj.put("capacity", Integer.toString(course.getCapacity()));
        obj.put("nooftut",Integer.toString(course.getTutorialGroups().size()));
        for(int i=0;i<course.getTutorialGroups().size();i++)
            obj.put("tut"+Integer.toString(i),course.getTutorialGroups().get(i));

        FileManager.editFile(course.courseID, obj, courseFile, listFile);
    }

    public static Course readInFile(int courseID){
        HashMap<String,String> obj = FileManager.accessFile(courseID, courseFile);

        Course course = new Course();
        course.setCourseID(courseID);
        course.setCourseName((String) obj.get("coursename"));
        course.setCoordinator((String) obj.get("coordinator"));
        course.setType(translateType((String) obj.get("type")));
        course.setCapacity(Integer.parseInt((String) obj.get("capacity")));

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



    private static COURSETYPE translateType(String tempType){
        COURSETYPE type = COURSETYPE.NULL;
        //following converts string input of type to valid COURSETYPE
        if (tempType.equalsIgnoreCase("lec")) type = COURSETYPE.Lec;
        if (tempType.equalsIgnoreCase("tut")) type = COURSETYPE.LecTut;
        if (tempType.equalsIgnoreCase("lab")) type = COURSETYPE.LecTutLab;

        return type;
    }

    private static String translateType(COURSETYPE type){
        String tempType = "";

        if (type == COURSETYPE.Lec) tempType = "lec";
        if (type == COURSETYPE.LecTut) tempType = "tut";
        if (type == COURSETYPE.LecTutLab) tempType = "lab";

        return tempType;
    }


    //getters and setters
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public COURSETYPE getType() {
        return type;
    }

    public void setType(COURSETYPE type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<String> getTutorialGroups() {
        return tutorials;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public boolean courseRegister()
    {if(noOfStudents<capacity)
    	{noOfStudents++;
    	return true;}
    else
    {System.out.println("Full Capacity! ");
    	return false;}
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