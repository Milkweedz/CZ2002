package Course;
/**
 * This class controls all functionality of the course package including input/output and file writing/reading
 * @author Nikhita Menon
 * @version 1.0
 * @date 2018-11-15
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseCtrl {
    /**
     * This function the method controller
     * which outputs a list of options for the user to choose from with respect to the class and then
     * calls the respective methods to be executed
     */
    public void init(){
        CourseUI courseUI = new CourseUI();

        int choice;

        do {
            choice = courseUI.courseCtrlChoice();
            switch(choice){
                case 0:
                    viewCourse();
                    break;
                case 1:
                    createCourse();
                    break;
                case 2:
                    editCourse();
                    break;
                case 3:
                    deleteCourse();
                    break;
                case 4:
                    printCourseWeight();
                    break;
                case 5:
                    setCourseWeight();
                    break;
                case 6:
                    listCourses();
                    break;
                case 7:
                    checkVacancy();
                    break;

            }
        } while (choice!=8);    //look at courseUI, 8 happens to be the option to quit
    }

    /**
     * This method is responsible for displaying all the courses that have been implemented so far
     */
    public void listCourses(){
        ArrayList<String> courseList = Course.listCourses();
        CourseUI courseUI = new CourseUI();
        courseUI.listCourses(courseList);
    }

    /**
     * This method allows the user to view the particulars of any one course according to course ID entered by the user
     * after checking whether the course exists in the file
     */
    public void viewCourse(){
        int courseID;
        CourseUI courseUI = new CourseUI();

        courseID = courseUI.readCourseID();
        if (!Course.existsCourse(courseID)) {
            courseUI.courseIdNonexist();
        }
        else{
            Course course = Course.readInFile(courseID);

            String[] data = new String[5];
            data[0] = "Course ID : " + Integer.toString(course.getCourseID());
            data[1] = "Course Name : " + course.getCourseName();
            data[2] = "Course Coordinator : " + course.getCoordinator();
            data[3] = "Course Type : " + translateType(course.getType());
            data[4] = "Course Capacity : " + Integer.toString(course.getCapacity());

            courseUI.displayCourseData(data);
        }
    }

    /**
     * This method checks whether a particular course identified by its course ID has any vacancies by
     * reading the appropriate information from the course file
     */
    public void checkVacancy(){
        CourseUI courseUI = new CourseUI();
        int courseID = courseUI.readCourseID();
        if(Course.existsCourse(courseID)) {
            Course course = Course.readInFile(courseID);
            int capacity = course.getCapacity();
            int registered = course.getNoOfStudents();
            int vacancies = capacity - registered;
            courseUI.displayVacancies(vacancies);
        } else {
            courseUI.courseIdNonexist();
        }
    }

    /**
     * This method is responsible for creating a course with a unique ID
     * after checking if the course ID entered by the user does not already exist
     */
    public void createCourse(){
        int courseID;
        CourseUI courseUI = new CourseUI();

        courseID = courseUI.readCourseID();
        if (Course.existsCourse(courseID)) courseUI.courseIdTaken();  //print error if courseID already exists

        else {
            String[] data = courseUI.readCourseData();
            Course course = makeCourseObj(courseID, data);
            if(course.getType()!= Course.COURSETYPE.Lec){
                ArrayList<String> tutorialGroups = courseUI.readTutorials();
                course.addTutorialGroups(tutorialGroups);
            }
            Course.saveToFile(course);
        }

        listCourses();
    }

    /**
     * This method allows the user to edit the details of any course if the course exists
     */
    public void editCourse(){
        CourseUI courseUI = new CourseUI();
        int courseID = courseUI.readCourseID();

        if(Course.existsCourse(courseID)){
            String[] data = courseUI.readCourseData();
            Course course = makeCourseObj(courseID, data);
            if(course.getType()!= Course.COURSETYPE.Lec)
            {ArrayList<String> tutorialGroups = courseUI.readTutorials();
                course.addTutorialGroups(tutorialGroups);}
            Course.editFile(course);
        }
        else courseUI.courseIdNonexist(); //error message
    }

    /**
     * This method deletes a course from the file if the course exists
     */

    public void deleteCourse(){
        CourseUI courseUI = new CourseUI();
        int courseID = courseUI.readCourseID();
        if (Course.existsCourse(courseID)) {
            Course.deleteInFile(courseID);
        }
        else courseUI.courseIdNonexist();
    }

    /**
     * This method prints the course weightages for different components and sub-components of the overall mark
     * if the course exists
     */

    public void printCourseWeight(){
        CourseUI courseUI = new CourseUI();
        int courseID = courseUI.readCourseID();

        if (!Course.existsCourse(courseID)) {
            courseUI.courseIdNonexist();
            return;
        }

        HashMap<String, String> markWeights = Course.getMarkWeights(courseID);
        String[] data;
        if (markWeights == null) courseUI.hashmapEmpty();
        else {
            data = new String[markWeights.size()];

            int i = 0;
            for (Map.Entry<String, String> entry : markWeights.entrySet()) {        //somewhat of a poor implementation here
                String key = entry.getKey();
                String value = entry.getValue();
                data[i] = key + ": " + value + "%";
                i++;
            }

            //this does some formatting for the exam score output (bad code, but it's a duct tape approach)
            int examScore = Integer.parseInt(markWeights.get("exam"));
            data[0] = "exam: " + examScore + ", coursework: " + (100-examScore) + "\nCoursework components:";

            courseUI.displayCourseData(data);
        }
    }
    /**
     * This method return course marks weights of a particular course if the course ID inputed by the user is correct
     */
    public HashMap<String, String> getCourseWeight(int courseID){

        if (!Course.existsCourse(courseID)) {
            return null;
        }

        HashMap<String, String> markWeights = Course.getMarkWeights(courseID);
        String[] data;
        if (markWeights == null) return null;
        else {
            return markWeights;
        }
    }

    /**
     * This method sets the course weightages for different components and sub-components of the overall mark
     * if the course exists
     */
    public void setCourseWeight(){
        CourseUI courseUI = new CourseUI();
        int courseID = courseUI.readCourseID();

        if (!Course.existsCourse(courseID)) {
            courseUI.courseIdNonexist();
            return;
        }

        HashMap<String, String> markWeights = courseUI.inputMarkWeights();
        Course.setMarkWeights(courseID, markWeights);
    }

    /**
     * This method creates a Course object after reading all the relevant data from the file
     * @param courseID
     * @param data
     * @return Course with all parameters set to the information of required course
     */
    private Course makeCourseObj(int courseID, String[] data){
        Course course = new Course();

        course.setCourseID(courseID);
        course.setCourseName(data[0]);
        course.setCoordinator(data[1]);
        course.setType(translateType(data[2]));
        course.setCapacity(Integer.parseInt(data[3]));

        return course;
    }

    /**
     * This method translated he type of course as input from the user or read from the file
     * as an element of enumerator COURSETYPE [NULL,Lec,LecTut,LecTutLab]
     * @param tempType
     * @return COURSETYPE stating the type of course it is
     */

    private Course.COURSETYPE translateType(String tempType){
        Course.COURSETYPE type = Course.COURSETYPE.NULL;
        //following converts string input of type to valid COURSETYPE
        if (tempType.equalsIgnoreCase("lec")) type = Course.COURSETYPE.Lec;
        if (tempType.equalsIgnoreCase("tut")) type = Course.COURSETYPE.LecTut;
        if (tempType.equalsIgnoreCase("lab")) type = Course.COURSETYPE.LecTutLab;

        return type;
    }

    /**
     * This method return a string displaying whether the course in question is of which type
     * @param type
     * @return String containing course type
     */

    private String translateType(Course.COURSETYPE type){
        String tempType = "";

        if (type == Course.COURSETYPE.Lec) tempType = "lecture only";
        if (type == Course.COURSETYPE.LecTut) tempType = "lecture + tutorial";
        if (type == Course.COURSETYPE.LecTutLab) tempType = "lecture + tutorial + lab";

        return tempType;
    }
}
