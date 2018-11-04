package Course;

public class CourseCtrl {
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
                    courseWeight();
                    break;
            }
        } while (choice!=5);    //look at courseUI, 5 happens to be the option to quit
    }

    public void viewCourse(){
        int courseID;
        CourseUI courseUI = new CourseUI();

        courseID = courseUI.readCourseID();
        if (!Course.existsCourse(courseID)) {
            CourseUI.courseIdNonexist();
        }
        else{
            Course course = Course.readInFile(courseID);

            String[] data = new String[5];
            data[0] = "Course.Course ID : " + Integer.toString(course.getCourseID());
            data[1] = "Course.Course Name : " + course.getCourseName();
            data[2] = "Course.Course Coordinator : " + course.getCoordinator();
            data[3] = "Course.Course Type : " + translateType(course.getType());
            data[4] = "Course.Course Capacity : " + Integer.toString(course.getCapacity());

            courseUI.displayCourseData(data);
        }


    }

    public void createCourse(){
        int courseID;
        CourseUI courseUI = new CourseUI();

        courseID = courseUI.readCourseID();
        if (Course.existsCourse(courseID)) CourseUI.courseIdTaken();  //print error if courseID already exists

        else {
            String[] data = courseUI.readCourseData();
            Course course = makeCourseObj(courseID, data);

            Course.saveToFile(course);
        }
    }

    public void editCourse(){
        CourseUI courseUI = new CourseUI();
        int courseID = courseUI.readCourseID();

        if(Course.existsCourse(courseID)){
            String[] data = courseUI.readCourseData();
            Course course = makeCourseObj(courseID, data);
            Course.deleteInFile(courseID);
            Course.saveToFile(course);
        }
        else CourseUI.courseIdNonexist(); //error message
    }

    public void deleteCourse(){
        CourseUI courseUI = new CourseUI();
        int courseID = courseUI.readCourseID();
        if (Course.existsCourse(courseID)) {
            Course.deleteInFile(courseID);
        }
        else CourseUI.courseIdNonexist();
    }

    public void courseWeight(){
        CourseUI courseUI = new CourseUI();
        int courseID = courseUI.readCourseID();

        if (!Course.existsCourse(courseID)) {
            CourseUI.courseIdNonexist();
            return;
        }


    }


    private Course makeCourseObj(int courseID, String[] data){
        Course course = new Course();

        course.setCourseID(courseID);
        course.setCourseName(data[0]);
        course.setCoordinator(data[1]);
        course.setType(translateType(data[2]));
        course.setCapacity(Integer.parseInt(data[3]));

        return course;
    }

    private Course.COURSETYPE translateType(String tempType){
        Course.COURSETYPE type = Course.COURSETYPE.NULL;
        //following converts string input of type to valid COURSETYPE
        if (tempType.equalsIgnoreCase("lec")) type = Course.COURSETYPE.Lec;
        if (tempType.equalsIgnoreCase("tut")) type = Course.COURSETYPE.LecTut;
        if (tempType.equalsIgnoreCase("lab")) type = Course.COURSETYPE.LecTutLab;

        return type;
    }

    private String translateType(Course.COURSETYPE type){
        String tempType = "";

        if (type == Course.COURSETYPE.Lec) tempType = "lecture only";
        if (type == Course.COURSETYPE.LecTut) tempType = "lecture + tutorial";
        if (type == Course.COURSETYPE.LecTutLab) tempType = "lecture + tutorial + lab";

        return tempType;
    }
}
