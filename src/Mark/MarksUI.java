package Mark;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MarksUI {

	public static int readStudentID(Scanner scan) {
		System.out.println("Enter Student ID : ");
		return scan.nextInt();
	}

	public static int readCourseID(Scanner scan) {
		System.out.println("Enter Course ID : ");
		return scan.nextInt();
	}

	public static float[] readMarksData(Scanner scan,HashMap<String, String> markWeights) {
	        float[] data;
	        if (markWeights == null) courseUI.hashmapEmpty();
	        else {
	            data = new float[markWeights.size()];

	            int i = 0;
	            for (Map.Entry<String, String> entry : markWeights.entrySet()) {        //somewhat of a poor implementation here
	                String key = entry.getKey();
	                int value = Integer.valueOf(entry.getValue());
	                System.out.println( "Enter Marks Of " + key + ": (" + value + "%)";
	                data[i]=scan.nextFloat();
	                i++;
	            }
	          return data;
	        }
	}

	public static void studentCourseIdNonexist() {
		System.out.println("\nStudent or Course ID doesn't exist! Try again.");
	}

	public static void displayMarksData(Marks marks) {
		System.out.println("Student ID :" + marks.retStudentID);
		System.out.println("Course ID :" + marks.retCourseID);
		System.out.println("Exam Marks :" + marks.retStudentExamMarks);
		for (int i = 0; i < marks.retNumOfComp; i++) {
			System.out.println("Course mark " + (i + 1) + " : " + marks.retStudentCourseWorkMarks[i]);
		}
	}
}
