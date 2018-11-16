package Mark;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import Course.Course;
import Course.CourseUI;
import FileManager.InputMismatchHandler;
/**
 * Represents the user interface for marks, 
 * where users can input command and outputs are displayed
 * @author Nam
 * @version 1.0
 * @since 2018-11-15
 */
public class MarksUI {
	/**
	 * Error catching variable to detect user input exceptions
	 */
	InputMismatchHandler imh = new InputMismatchHandler();
	/**
	 * Acquire the users' choice on what to do with the marks package
	 * @return the user's choice
	 */
	public int marksCtrlChoice() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nWhat would you like to do?");
		System.out.println("1: Add marks of a student for a course\n2: Edit Marks of a student for a course\n3: Remove a student's marks for a course\n4: Return a student's marks for a course  \n5: Quit");
		System.out.print("Enter Your Choice: ");
		return imh.checkInt();
	}

	/**
	 * Scan the ID of the student that users want to interact with
	 * @param scan Scanner object
	 * @return the ID of the student
	 */
	public int readStudentID(Scanner scan) {
		System.out.println("Enter Student ID : ");
		return imh.checkInt();
	}

	/**
	 * Scan the ID of the course that users want to interact with
	 * @param scan Scanner object
	 * @return the ID of the course
	 */
	public int readCourseID(Scanner scan) {
		System.out.println("Enter Course ID : ");
		return imh.checkInt();
	}
	/**
	 * Scan the marks (both coursework and exam) of a student for a course
	 * @param scan Scanner object
	 * @param markWeights the weightage of each coursework components
	 * @return the inputted marks
	 */
	public double[] readMarksData(Scanner scan, HashMap<String, String> markWeights) {
		double[] data = new double[0];
		if (markWeights == null) CourseUI.hashmapEmpty();
		else {
			data = new double[markWeights.size()];

			int i = 0;
			for (Map.Entry<String, String> entry : markWeights.entrySet()) {        //somewhat of a poor implementation here
				String key = entry.getKey();
				int value = Integer.valueOf(entry.getValue());
				System.out.println("Enter Marks Of " + key + ": (" + value + "%)");
				data[i] = imh.checkFloat(0,100);
				i++;
			}
		}
		return data;
	}

	/**
	 * Error output when the student/course ID doesn't exist when users want to read marks
	 */
	public static void studentCourseIdNonexist() {
		System.out.println("\nStudent or Course ID doesn't exist! Try again.");
	}
	/**
	 * Error output when the student/course ID already exist when users want to input marks
	 */
	public static void studentCourseIdexist() {
		System.out.println("\nStudent or Course ID already exist! Try again.");
	}
	/**
	 * Output the a student's marks and its weightage
	 * @param marks the marks object of a student and a course
	 */
	public static void displayMarksData(Marks marks) {
		System.out.println("Student ID :" + marks.getStudentID());
		System.out.println("Course ID :" + marks.getCourseID());
		if (marks == null) {
			System.out.println("No marks for this student in this course!");
		} else {
			HashMap<String, String> markWeights = Course.getMarkWeights(marks.getCourseID());
			int i = 0;
			for (Map.Entry<String, String> entry : markWeights.entrySet())         //somewhat of a poor implementation here
				System.out.println(entry.getKey() + " : " + marks.getStudentCourseWorkMarks(i++));
		}

	}

	/**
	 * Output the a student's transcript (all courses he/she takes and its marks)
	 * @param marks the marks object of a student and a course
	 */
	public static void displayTranscriptData(Marks marks) {
		HashMap<String, String> markWeights = Course.getMarkWeights(marks.getCourseID());
		int i = 0;
		for (Map.Entry<String, String> entry : markWeights.entrySet())         //somewhat of a poor implementation here
			System.out.print(String.format("\n%12s|%19s : %-19s|", " ", entry.getKey(), marks.getStudentCourseWorkMarks(i++)));

	}

	/**
	 * Display message to inform the user that marks are added successfully
	 */
	public void successAdd() {
		System.out.println("Marks Successfully Added !");
	}

	
	/**
	 * Display message to inform the user that marks are edited successfully
	 */
	public void successEdit() {
		System.out.println("Marks Successfully Edited !");
	}

	/**
	 * Display message to inform the user that marks are removed successfully
	 */
	public void successRemove() {
		System.out.println("Marks Successfully Removed !");
	}

	/**
	 * Error message to inform the user that the coursework has no component
	 */
	public void noComponentsError() {
		System.out.println("No components assigned to course!");
	}

	/**
	 * Error message to inform the user that the ID of the student users want to adjust marks
	 * does not exist
	 */
	public static void studentIdNotexist() {
		System.out.println("Student Does Not Exist!");
	}
}
