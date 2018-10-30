package Entity_Class;

import java.util.Scanner;

public class Student {
	private long Student_ID;
	private String StudentFName, StudentLName, gender, department, level;
	private int YearOfStudy, NumOfAU;
	private double CGPANum, CGPA;
	
	public Student() {
		Student_ID = 0;
		StudentFName = "";
		StudentLName = "";
		YearOfStudy = 0;
		NumOfAU = 0;
		CGPANum = 0.0;
		CGPA = 0.0;
		gender = "";
		department = " ";
		level = " ";
	}

	public void inputStudDetails(int check) {
		Scanner input = new Scanner(System.in);
		String dummychar;
		if (check == 1 || check == 0) {
			System.out.println("Enter Student ID : ");
			Student_ID = input.nextLong();
			dummychar = input.nextLine();
		}
		if (check == 2 || check == 0) {
			System.out.println("Enter Student's First Name : ");
			StudentFName = input.nextLine();
			System.out.println("Enter Student's Last Name : ");
			StudentLName = input.nextLine();
		}
		if (check == 3 || check == 0) {
			System.out.println("Enter Student's year of study : ");
			YearOfStudy = input.nextInt();
		}
		if (check == 4 || check == 0) {
			System.out.println("Enter number of AU's obtained so far : ");
			NumOfAU = input.nextInt();
		}
		if (check == 5 || check == 0) {
			System.out.println("Enter CGPA numerator obtained so far : ");
			CGPANum = input.nextDouble();
			CGPA = CGPANum / NumOfAU;
			System.out.println(String.format("CGPA: %.2f", CGPA));
			dummychar = input.nextLine();
		}

		if (check == 6 || check == 0) {
			System.out.println("Enter Student's Gender (Male/Female/Other) : ");
			gender = input.nextLine();
		}
		if (check == 7 || check == 0) {
			System.out.println("Enter Department of Study : ");
			department = input.nextLine();
		}
		if (check == 8 || check == 0) {
			System.out.println("Enter Level of Study (UG/PG) : ");
			level = input.next();

		}
	}

	public boolean retStudentId(long ID) {
		return ID == Student_ID;
	}

	public String retStudentName() {
		return StudentLName + "," + StudentFName;
	}

	public int retNumOfAU() {
		return NumOfAU;
	}

	public double retCGPA() {
		return CGPA;
	}

	public void calcCGPA(double GPXAU, int AU) {
		NumOfAU += AU;
		CGPANum += GPXAU;
		CGPA = CGPANum / NumOfAU;
	}

	public void printStudDetails() {
		System.out.print(String.format("|%-1d.%30s|", 1, " Student ID "));
		System.out.println(String.format(" %-30d|", Student_ID));
		System.out.print(String.format("|%-1d.%30s|", 2, " Student's Name "));
		System.out.println(String.format(" %-30s|", StudentLName + "," + StudentFName));
		System.out.print(String.format("|%-1d.%30s|", 3, " Year Of Study "));
		System.out.println(String.format(" %-30d|", YearOfStudy));
		System.out.print(String.format("|%-1d.%30s|", 4, " Number of AUs obtained "));
		System.out.println(String.format(" %-30d|", NumOfAU));
		System.out.print(String.format("|%-1d.%30s|", 5, " CGPA "));
		System.out.println(String.format(" %-30.2f|", CGPA));
		System.out.print(String.format("|%-1d.%30s|", 6, "Gender "));
		System.out.println(String.format(" %-30s|", gender));
		System.out.print(String.format("|%-1d.%30s|", 7, "Department of study "));
		System.out.println(String.format(" %-30s|", department));
		System.out.print(String.format("|%-1d.%30s|", 8, "Level Of Study "));
		System.out.println(String.format(" %-30s|", level));
	}

}
