

import java.util.Scanner;



public class Marks {
	private long StudentID;
	private String CourseID;
	private int NoOfComp;
	private double ExamMark;
	private double courseWorkMarks;
	private boolean courseWorkMarkSet, examMarkSet;

	public boolean isStudMarks(int StdID, String CrseID) {
		if (StudentID == StdID && CourseID == CrseID)
			return true;
		else
			return false;
	}

	public void inputMarkDet(int check, int noOfComp) {
		Scanner input = new Scanner(System.in);
		if (check == 1 || check == 0) {
			System.out.println("Enter Course.Course ID : ");
			CourseID = input.next();
		}
		if (check == 2 || check == 0) {
			System.out.println("Enter Student.Student ID : ");
			StudentID = input.nextLong();
		}

		NoOfComp = noOfComp;

	}

	public boolean retExamMarkSet() {
		return examMarkSet;
	}

	public boolean courseWorkMarkSet() {
		return courseWorkMarkSet;
	}

	public void enterExamMark(double Exam_Mark) {
		ExamMark = Exam_Mark;
	}

	public void enterCourseWorkMark(String[] subCompWeightageNames, double[] subCompWeightage) {
		Scanner input = new Scanner(System.in);
		double marks, Tmarks;
		for (int i = 0; i < NoOfComp; i++) {
			System.out.println("Enter Marks obtained for " + subCompWeightageNames[i] + ": ");
			marks = input.nextDouble();
			System.out.println("Enter Total Marks obtained for " + subCompWeightageNames[i] + ": ");
			Tmarks = input.nextDouble();
			courseWorkMarks += (marks / Tmarks) * subCompWeightage[i];
		}
		input.close();
	}

	public double retExamMark() {
		return ExamMark;
	}

	public double retSubCourseWorkMarkPer() {
		return courseWorkMarks;
	}

}
