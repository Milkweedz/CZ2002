package Course;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Course {
    private int courseID;
    private String courseName;
    private String coordinator;
    public enum COURSETYPE {Lec, LecTut, LecTutLab, NULL};
    private COURSETYPE type;
    private int capacity;
    private int noOfStudents;
    private ArrayList<String> tutorials;

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
        String courseFile = "src\\Course\\courses.json";
        JSONObject file = readJSON(courseFile);
        //JSONArray array = (JSONArray) file.get("data");

        JSONObject obj = new JSONObject();
        //obj.put("courseid", Integer.toString(course.getCourseID()));
        obj.put("coursename", course.getCourseName());
        obj.put("coordinator", course.getCoordinator());
        obj.put("type", translateType(course.getType()));
        obj.put("capacity", Integer.toString(course.getCapacity()));
        obj.put("nooftut",Integer.toString(course.getTutorialGroups().size()));
        for(int i=0;i<course.getTutorialGroups().size();i++)
            obj.put("tut"+Integer.toString(i),course.getTutorialGroups().get(i));
        file.put(Integer.toString(course.getCourseID()), obj);
        //file.replace("data", array);

        writeJSON(file, courseFile);

        //add new course to courseid cache
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\Course\\courselist.txt", true))){
            bw.write(course.getCourseID()+"\n");
        } catch (IOException ex){
            System.out.println("IOException! courselist.txt not found?");
            ex.printStackTrace();
        }
    }

    public void addTutorialGroups(ArrayList<String> tutorialGroups)
    {
        tutorials = tutorialGroups;
    }
    public static void deleteInFile(int courseID){
        String courseFile = "src\\Course\\courses.json";
        JSONObject file = readJSON(courseFile);
        file.remove(Integer.toString(courseID));

        writeJSON(file, courseFile);


        File readFile = null;
        File tempFile = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            //remove entry from course list file
            readFile = new File("src\\Course\\courselist.txt");
            tempFile = File.createTempFile("file",".txt", readFile.getParentFile());
            br = new BufferedReader(new FileReader(readFile));
            bw = new BufferedWriter(new FileWriter(tempFile));

            for (String line=""; line != null; line = br.readLine()){
                if (!line.equals(Integer.toString(courseID))){
                    bw.write(line);
                }
            }
            //System.out.println("Delete error. CourseID not found.");
        } catch (IOException ex){
            System.out.println("IOException! courselist.txt not found?");
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

    public static void editFile(Course course){
        String courseFile = "src\\Course\\courses.json";
        JSONObject file = readJSON(courseFile);
        file.remove(Integer.toString(course.courseID));
        //JSONArray array = (JSONArray) file.get("data");

        JSONObject obj = new JSONObject();
        //obj.put("courseid", Integer.toString(course.getCourseID()));
        obj.put("coursename", course.getCourseName());
        obj.put("coordinator", course.getCoordinator());
        obj.put("type", translateType(course.getType()));
        obj.put("capacity", Integer.toString(course.getCapacity()));
        obj.put("nooftut",Integer.toString(course.getTutorialGroups().size()));
        for(int i=0;i<course.getTutorialGroups().size();i++)
            obj.put("tut"+Integer.toString(i),course.getTutorialGroups().get(i));

        file.put(Integer.toString(course.getCourseID()), obj);
        //file.replace("data", array);

        writeJSON(file, courseFile);
    }

    public static Course readInFile(int courseID){
        String courseFile = "src\\Course\\courses.json";
        JSONObject file = readJSON(courseFile);
        JSONObject obj = (JSONObject) file.get(Integer.toString(courseID));

        Course course = new Course();
        course.setCourseID(courseID);
        course.setCourseName((String) obj.get("coursename"));
        course.setCoordinator((String) obj.get("coordinator"));
        course.setType(translateType((String) obj.get("type")));
        course.setCapacity(Integer.parseInt((String) obj.get("capacity")));
        int noOfTut=Integer.parseInt((String)obj.get("nooftut"));
        ArrayList<String> tutorialNames = new ArrayList<>();
        for(int i=0;i<noOfTut;i++)
            tutorialNames.add((String)obj.get("tut"+Integer.toString(i)));
        course.addTutorialGroups(tutorialNames);
        return course;
    }

    public static HashMap<String, String> getMarkWeights(int courseID){
        String courseFile = "src\\Course\\courses.json";
        JSONObject file = readJSON(courseFile);

        JSONObject obj = (JSONObject) file.get(Integer.toString(courseID));

        if (obj.containsKey("markweights")){
            JSONObject markObj = (JSONObject) obj.get("markweights");
            HashMap<String, String> markWeights = new HashMap<>();

            markObj.forEach((key, value) -> markWeights.put(key.toString(), value.toString()));

            return markWeights;
        } else return null;
    }

    public static void setMarkWeights(int courseID, HashMap<String, String> markWeights){
        String courseFile = "src\\Course\\courses.json";
        JSONObject file = readJSON(courseFile);

        JSONObject obj = (JSONObject) file.get(Integer.toString(courseID));
        JSONObject markObj;

        if (obj.containsKey("markweights")){
            markObj = (JSONObject) obj.get("markweights");
            obj.remove("markweights");
        } else {
            markObj = new JSONObject();
        }

        //markWeights.forEach((key, value) -> markObj.put(key, value));
        markObj.putAll(markWeights);
        System.out.println(markObj.toJSONString());
        obj.put("markweights", markObj);
        System.out.println(obj.toJSONString());
        file.replace(Integer.toString(courseID), obj);

        writeJSON(file, courseFile);
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