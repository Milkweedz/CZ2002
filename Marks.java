package Entity_Class;

import java.util.Scanner;

public class Marks {
	private long StudentID, CourseID;
	private int NoOfComp;
	private double ExamMark;
	private double[] courseWorkMarks;
	private boolean courseWorkMarkSet, examMarkSet;

	public boolean isStudMarks(int StdID, int CrseID) {
		if (StudentID == StdID && CourseID == CrseID)
			return true;
		else
			return false;
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
		double marks;
		for (int i = 0; i < NoOfComp; i++) {
			System.out.println("Enter Marks obtained for " + subCompWeightageNames[i] + ": ");
			marks = input.nextDouble();
			courseWorkMarks[i] = marks * subCompWeightage[i];
		}
		input.close();
	}

	public double retExamMark() {
		return ExamMark;
	}

	public double[] retCourseWorkMark() {
		return courseWorkMarks;
	}

}
