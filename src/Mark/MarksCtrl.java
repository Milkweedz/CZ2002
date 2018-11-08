package Mark;

import java.util.ArrayList;
import java.util.Scanner;

public class MarksCtrl {

	public void viewMarks() {
		int studentID,courseID;
		MarksUI marksUI = new MarksUI();

		studentID = marksUI.readStudentID(new Scanner(System.in));
		courseID = marksUI.readCourseID(new Scanner(System.in));
		if (!Marks.existsStudent(studentID,courseID)) {
			MarksUI.studentMarksIdNonexist();
		} else {
			Marks marks = Marks.readInFile(studentID,courseID);

			MarksUI.displayMarksData(marks);
		}

	}
	
	public Marks retMarks(int studentID,int courseID) {

		if (!Marks.existsStudent(studentID,courseID)) {
			MarksUI.studentMarksIdNonexist();
		} else {
			Marks marks = Marks.readInFile(studentID,courseID);
			return marks;}


	}

	public void addMarks(){
	        int studentID;
	        int courseID;
	        ArrayList<Integer> courses = RegistrationCtrl(MarksUI.readStudentID(new Scanner(System.in)));
	        if(courses.contains(MarksUI.readCourseID(new Scanner(System.in))))

	        else {
	            float[] data = MarksUI.readMarksData(new Scanner(System.in),Course.getMarkWeights(courseID));
	            Marks marks = makeStudentObj(studentID, data);

	            Marks.saveToFile(marks);
	        }
	    }

	public void editMarks() {
		MarksUI markUI = new MarksUI();
		int studentID = markUI.readStudentID(new Scanner(System.in));
		int courseID = markUI.readcourseID(new Scanner(System.in));
		if (Marks.existsStudent(studentID, courseID)) {
			float[] data = MarksUI.readMarksData(new Scanner(System.in), Course.getMarkWeights(courseID));
			Marks marks = makeStudentObj(studentID, data);
			Marks.deleteInFile(studentID, courseID);
			Marks.saveToFile(marks);
		} else
			marksUI.studentCourseIdNonexist(); // error message
	}

	public void deleteMarks() {
		MarksUI marksUI = new MarksUI();
		int studentID = MarksUI.readStudentID(new Scanner(System.in));
		int courseID = MarksUI.readCourseID(new Scanner(System.in));
		if (Student.existsStudent(studentID,courseID)) {
			Student.deleteInFile(studentID,courseID);
		} else
			marksUI.studentCourseIdNonexist();
	}

	private Mark makeMarkObj(int studentID,int courseID, float[] args) {
		Marks marks = new Marks(Course.getMarkWeights(courseID).size());

		marks.setStudentID(studentID);
		marks.setCourseID(courseID)
		marks.setStudentExamMarks(args[0]);
		marks.setStudentCourseWorkMarks(&args[1]);

		return marks;
	}

}
