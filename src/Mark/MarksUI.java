package Mark;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Course.Course;
import Course.CourseUI;

public class MarksUI {
	public static int marksCtrlChoice(){
		Scanner scan = new Scanner (System.in);
		System.out.println("\nWhat would you like to do?");
		System.out.println("1: Add marks of a student for a course\n2: Edit Marks of a student for a course\n3: Remove a student's marks for a course\n4: Return a student's marks for a course  \n5: Quit");
		System.out.print("Enter Your Choice: ");
		return scan.nextInt();
	}

	public static int readStudentID(Scanner scan) {
		System.out.println("Enter Student ID : ");
		return scan.nextInt();
	}

	public static int readCourseID(Scanner scan) {
		System.out.println("Enter Course ID : ");
		return scan.nextInt();
	}

	public static double[] readMarksData(Scanner scan,HashMap<String, String> markWeights) {
	        double[] data = new double[0];
	        if (markWeights == null) CourseUI.hashmapEmpty();
	        else {
	            data = new double[markWeights.size()];

	            int i = 0;
	            for (Map.Entry<String, String> entry : markWeights.entrySet()) {        //somewhat of a poor implementation here
	                String key = entry.getKey();
	                int value = Integer.valueOf(entry.getValue());
	                System.out.println( "Enter Marks Of " + key + ": (" + value + "%)");
	                data[i]=scan.nextFloat();
	                i++;
	            }
	        }
	          return data;
	}

	public static void studentCourseIdNonexist() {
		System.out.println("\nStudent or Course ID doesn't exist! Try again.");
	}
	public static void studentCourseIdexist() {
		System.out.println("\nStudent or Course ID already exist! Try again.");
	}
	public static void displayMarksData(Marks marks) {
		System.out.println("Student ID :" + marks.retStudentID());
		System.out.println("Course ID :" + marks.retCourseID());
		if (marks == null){
			System.out.println("No marks for this student in this course!");
		} else {
			HashMap<String, String> markWeights = Course.getMarkWeights(marks.retCourseID());
			int i = 0;
			for (Map.Entry<String, String> entry : markWeights.entrySet())         //somewhat of a poor implementation here
				System.out.println(entry.getKey() + " : " + marks.retStudentCourseWorkMarks(i++));
		}

	}
	public static void displayTranscriptMarksData(Marks marks) {
		HashMap<String, String> markWeights = Course.getMarkWeights(marks.retCourseID());
		int i=0;
		for (Map.Entry<String, String> entry : markWeights.entrySet())         //somewhat of a poor implementation here
			System.out.print(String.format("\n%12s|%19s : %-19s|"," ",entry.getKey(),marks.retStudentCourseWorkMarks(i++)));

	}
	public static void successAdd()
	{
		System.out.println("Marks Successfully Added !");
	}
    public static void successEdit()
    {
        System.out.println("Marks Successfully Edited !");
    }
    public static void successRemove()
    {
        System.out.println("Marks Successfully Removed !");
    }
}
