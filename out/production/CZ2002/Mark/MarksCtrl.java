package Mark;

import Registration.RegistrationCtrl;

import java.util.*;

import Course.Course;
import Student.Student;
/**
 * Represents the control class for student's Marks
 * @author Nam
 * @version 1.0
 * @since 2018-11-15
 */
public class MarksCtrl {
	/**
	 * The main control that chooses which function to carry out
	 */
	public void init(){
		MarksUI marksUI = new MarksUI();

		int choice;

		do {
			choice = marksUI.marksCtrlChoice();
			switch(choice){
				case 1:
					addMarks();
					break;
				case 2:
					editMarks();
					break;
				case 3:
					deleteMarks();
					break;
				case 4:
					viewMarks();
					break;
			}
		} while (choice<5);    //look at studentUI, 5 happens to be the option to quit
	}

	/**
	 * Print out the marks of a student for a course
	 */
	public void viewMarks() {
		int studentID,courseID;
		MarksUI marksUI = new MarksUI();

		studentID = marksUI.readStudentID(new Scanner(System.in));
		courseID = marksUI.readCourseID(new Scanner(System.in));
		if (!Marks.existsMarks(studentID,courseID)) {
			MarksUI.studentCourseIdNonexist();
		} else {
			Marks marks = Marks.readInFile(studentID,courseID);


			MarksUI.displayMarksData(marks);
		}

	}

	/**
	 * Add a student's marks for a course to the storage file
	 */
	public void addMarks(){
        MarksUI marksUI = new MarksUI();
        int studentID=marksUI.readStudentID(new Scanner(System.in));
        if(Student.existsStudent(studentID)){
        int courseID=marksUI.readCourseID(new Scanner(System.in));
        List<Integer> courses = RegistrationCtrl.studentCourses(studentID);
        if(!courses.contains(courseID)){
            MarksUI.studentCourseIdNonexist();
        }
        else if(Marks.existsMarks(studentID,courseID)){
            MarksUI.studentCourseIdexist();
        }
        else {
            if (Course.getMarkWeights(courseID) == null){
                marksUI.noComponentsError();
            } else {
                double[] data = marksUI.readMarksData(new Scanner(System.in), Course.getMarkWeights(courseID));
                Marks marks = makeMarkObj(studentID, courseID, data);

                Marks.saveToFile(marks);
                marksUI.successAdd();
            }
        }}
        else
        	MarksUI.studentIdNotexist();
    }

	/**
	 * Change a student's marks for a course in the storage file
	 */
	public void editMarks() {
		MarksUI markUI = new MarksUI();
		int studentID = markUI.readStudentID(new Scanner(System.in));
		int courseID = markUI.readCourseID(new Scanner(System.in));
		if (Marks.existsMarks(studentID, courseID)) {
			double[] data = markUI.readMarksData(new Scanner(System.in), Course.getMarkWeights(courseID));
			Marks marks = makeMarkObj(studentID,courseID, data);
			Marks.deleteInFile(studentID, courseID);
			Marks.saveToFile(marks);
			markUI.successEdit();
		} else
			markUI.studentCourseIdNonexist(); // error message
	}

	/**
	 * Remove a student's marks for a course from the storage file
	 */
	public void deleteMarks() {
		MarksUI marksUI = new MarksUI();
		int studentID = marksUI.readStudentID(new Scanner(System.in));
		int courseID = marksUI.readCourseID(new Scanner(System.in));
		if (Marks.existsMarks(studentID,courseID)) {
			Marks.deleteInFile(studentID,courseID);
			marksUI.successRemove();
		} else
			marksUI.studentCourseIdNonexist();
	}
	
	/**
	 * Create a Marks object for a student and a course
	 */

	private Marks makeMarkObj(int studentID, int courseID, double[] args) {
		Marks marks = new Marks(Course.getMarkWeights(courseID).size());

		marks.setStudentID(studentID);
		marks.setCourseID(courseID);
		marks.setStudentCourseMarks(args);

		return marks;
	}

	/**
	 * Get the total marks of the student for the course
	 * @param studentID the ID of the student
	 * @param courseID the ID of the course
	 * @return -1 if the student does not take all components in the course,
	 * else the marks the student acquired for the course
	 */
    public static double retTotalPercentage(int studentID, int courseID) {
        //System.out.println(courseID + "DEBUG");
        Marks marks = Marks.readInFile(studentID, courseID);
        if (marks == null) {
            return -1;
        }
        double sumMarks = 0.0;
        double newMark;
        HashMap<String, String> markWeights = Course.getMarkWeights(marks.getCourseID());

        int i = 0;
        for (Map.Entry<String, String> entry : markWeights.entrySet()) {         //somewhat of a poor implementation here
            newMark = Integer.parseInt(entry.getValue()) * marks.getStudentCourseWorkMarks(i++) / 100.0;            //coursework component scores
            //System.out.println("DEBUG" + newMark);
            if (i==1){
                sumMarks += newMark;
                //System.out.println("DEBUG EXAM");
            } else {
                sumMarks += newMark * (100 - Integer.parseInt(markWeights.get("exam"))) / 100;
            }
        }
        return sumMarks;
    }

}
