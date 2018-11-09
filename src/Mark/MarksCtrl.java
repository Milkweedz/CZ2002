package Mark;

import Registration.RegistrationCtrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import Course.Course;
import Registration.RegistrationUI;

public class MarksCtrl {
	public void init(){

		int choice;

		do {
			choice = MarksUI.marksCtrlChoice();
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
	
	public Marks retMarks(int studentID,int courseID) {
		Marks marks = new Marks(0);
		if (!Marks.existsMarks(studentID,courseID)) {
			MarksUI.studentCourseIdNonexist();
		} else {
			marks = Marks.readInFile(studentID,courseID);
			return marks;}
			return marks;
	}

	public void addMarks(){
	        int studentID=MarksUI.readStudentID(new Scanner(System.in));
	        int courseID=MarksUI.readCourseID(new Scanner(System.in));
	        List<Integer> courses = RegistrationCtrl.studentCourses(studentID);
	        if(!courses.contains(courseID)){
				MarksUI.studentCourseIdNonexist();
			}
	        else if(Marks.existsMarks(studentID,courseID)){
	        	MarksUI.studentCourseIdexist();
			}
			else {
	            double[] data = MarksUI.readMarksData(new Scanner(System.in),Course.getMarkWeights(courseID));
	            Marks marks = makeMarkObj(studentID,courseID,data);

	            Marks.saveToFile(marks);
	            MarksUI.successAdd();
	        }
	    }

	public void editMarks() {
		MarksUI markUI = new MarksUI();
		int studentID = MarksUI.readStudentID(new Scanner(System.in));
		int courseID = MarksUI.readCourseID(new Scanner(System.in));
		if (Marks.existsMarks(studentID, courseID)) {
			double[] data = MarksUI.readMarksData(new Scanner(System.in), Course.getMarkWeights(courseID));
			Marks marks = makeMarkObj(studentID,courseID, data);
			Marks.deleteInFile(studentID, courseID);
			Marks.saveToFile(marks);
			MarksUI.successEdit();
		} else
			MarksUI.studentCourseIdNonexist(); // error message
	}

	public void deleteMarks() {
		MarksUI marksUI = new MarksUI();
		int studentID = MarksUI.readStudentID(new Scanner(System.in));
		int courseID = MarksUI.readCourseID(new Scanner(System.in));
		if (Marks.existsMarks(studentID,courseID)) {
			Marks.deleteInFile(studentID,courseID);
			MarksUI.successRemove();
		} else
			marksUI.studentCourseIdNonexist();
	}

	private Marks makeMarkObj(int studentID, int courseID, double[] args) {
		Marks marks = new Marks(Course.getMarkWeights(courseID).size());

		marks.setStudentID(studentID);
		marks.setCourseID(courseID);
		marks.setStudentCourseMarks(args);

		return marks;
	}

}
