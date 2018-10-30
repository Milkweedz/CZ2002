package Extra;

import java.util.Scanner;

public class tutorialLabs {
	private int tutorID, LabAsstID, maxNoOfStud, noOfStud;
	private String tutorialGp, tutorName, LabAsstName;
	private long[] studentid;
	private boolean haveLab;

	public tutorialLabs(boolean Lab) {
		tutorialGp = "";
		tutorName = "";
		LabAsstName = "";
		tutorID = 0;
		LabAsstID = 0;
		studentid = new long[0];
		noOfStud = 0;
		maxNoOfStud = 0;
		haveLab = Lab;
	}

	public void inputTLData(int check,int maxStud) {
		Scanner input = new Scanner(System.in);
		String dummychar;
		studentid = new long[maxStud];
		if (check == 1 || check == 0) {
			System.out.println("Enter tutorial group : ");
			tutorialGp = input.nextLine();
		}
		if (check == 2 || check == 0) {
			System.out.println("Enter Tutor Name : ");
			tutorName = input.nextLine();
			System.out.println("Enter tutorID : ");
			tutorID = input.nextInt();
			dummychar = input.next();
		}
		if ((check == 3 || check == 0) && haveLab == true) {
			System.out.println("Enter Lab Assistant Name : ");
			LabAsstName = input.nextLine();
			System.out.println("Enter Lab Assistant ID : ");
			LabAsstID = input.nextInt();
			dummychar = input.next();
			dummychar = input.next();
		}
			

	}

	public String retTutGrp() {
		return tutorialGp;
	}

	public String retTutorName() {
		return tutorName;
	}

	public String retLabAssName() {
		return LabAsstName;
	}

	public int retTutorID() {
		return tutorID;
	}

	public int retLabAssID() {
		return LabAsstID;
	}

	public boolean addStudent(long studentId) {
		if (noOfStud < maxNoOfStud) {
			studentid[noOfStud++] = studentId;
			return true;
		}
		return false;
	}

	public boolean retStudExist(long studentId) {
		for (int i = 0; i < noOfStud; i++)
			if (studentid[i] == studentId)
				return true;
		return false;
	}

}
