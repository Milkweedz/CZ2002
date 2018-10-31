package Entity_Class;

import java.util.Scanner;

import Extra.tutorialLabs;

public class Course {
	private String CourseID;
	private int NoOfSeatsTaken, NoOfSeats, NoOfComp, NoOfTutGroups, NoOfSubCom;
	private String CourseName, Professor;
	private boolean HaveTutorials, HaveLabs, isSubComp;
	private double examWeightage, courseWorkWeightage;
	private double[] subCompWeightage;
	private String[] subCompWeightageNames;
	private tutorialLabs[] Tutorials_Labs;
	private int[] studentId;

	public int enterCourseInformation(int check) {
		Scanner input = new Scanner(System.in);
		String dummychar;
		if (check == 1 || check == 0) {
			System.out.println("Enter Course ID : ");
			CourseID = input.next();
			dummychar = input.nextLine();
		}
		if (check == 2 || check == 0) {
			System.out.println("Enter Course Name : ");
			CourseName = input.nextLine();
			System.out.println("Enter Professor's Name : ");
			Professor = input.nextLine();
		}
		if (check == 3 || check == 0) {
			System.out.println("Does course have tutorials?(true/false) : ");
			HaveTutorials = input.nextBoolean();
		}
		if (check == 4 || check == 0) {
			System.out.println("Does course have labs?(true/false) : ");
			HaveLabs = input.nextBoolean();
		}
		if ((check == 5 || check == 0) && HaveTutorials) {
			System.out.println("Enter number of tutorial groups : ");
			NoOfTutGroups = input.nextInt();
			Tutorials_Labs = new tutorialLabs[NoOfTutGroups];
			System.out.println("Enter Maximum number of students per tutorial group :");
			int maxNoStud = input.nextInt();
			for (int i = 0; i < NoOfTutGroups; i++) {
				Tutorials_Labs[i] = new tutorialLabs(HaveLabs);
				Tutorials_Labs[i].inputTLData(0, maxNoStud);
			}
			NoOfSeats = maxNoStud * NoOfTutGroups;
			NoOfSeatsTaken = 0;
			studentId = new int[NoOfSeats];
		} else if ((check == 5 | check == 0)) {
			System.out.println("Enter Maximum number of students \:");
			NoOfSeats = input.nextInt();
			NoOfSeatsTaken = 0;;
			studentId = new int[NoOfSeats];
		}

		if (check == 6 || check == 0) {
			System.out.println("Enter exam weightage : ");
			examWeightage = input.nextDouble();
		}
		if (check == 7 || check == 0) {
			System.out.println("Enter Course Work wightage : ");
			courseWorkWeightage = input.nextDouble();
		}
		if (check == 8 || check == 0) {
			System.out.println("Does course have Sub-components for Course Work :");
			isSubComp = input.nextBoolean();
		}
		if ((check == 9 || check == 0) && isSubComp) {
			System.out.println("Enter Number of Sub-components :");
			NoOfSubCom = input.nextInt();
			subCompWeightage = new double[NoOfSubCom];
			subCompWeightageNames = new String[NoOfSubCom];
			for (int i = 0; i < NoOfSubCom; i++) {
				dummychar = input.next();
				System.out.println("Enter Name of sub component " + i + " : ");
				subCompWeightageNames[i] = input.nextLine();
				System.out.println("Enter weightage of sub component " + subCompWeightageNames[i] + " : ");
				subCompWeightage[i] = input.nextDouble();
			}
		}
		return 1;
	}

	public void updateCourseAssessmentWeightage() {
		Scanner input = new Scanner(System.in);
		String dummychar;
		System.out.println("Enter exam weightage : ");
		examWeightage = input.nextDouble();
		System.out.println("Enter Course Work wightage : ");
		courseWorkWeightage = input.nextDouble();
		System.out.println("Does course have Sub-components for Course Work :");
		isSubComp = input.nextBoolean();
		if (isSubComp) {
			System.out.println("Enter Number of Sub-components :");
			NoOfSubCom = input.nextInt();
			subCompWeightage = new double[NoOfSubCom];
			subCompWeightageNames = new String[NoOfSubCom];
			for (int i = 0; i < NoOfSubCom; i++) {
				dummychar = input.next();
				System.out.println("Enter Name of sub component " + i + " : ");
				subCompWeightageNames[i] = input.nextLine();
				System.out.println("Enter weightage of sub component " + subCompWeightageNames[i] + " : ");
				subCompWeightage[i] = input.nextDouble();
			}
		}
	}

	public boolean addStudent(long stdId) {
		if (NoOfSeatsTaken> NoOfSeats)
			if(HaveTutorials) {
			for (int i = 0; i < NoOfTutGroups; i++) {
				if (Tutorials_Labs[i].retTutGrp() == tutLab_Group)
					if (Tutorials_Labs[i].addStudent(stdId)) {
						studentId[NoOfSeatsTake++]=stdId;
						return true;
					} else {
						System.out.println("Error: No Vacancies in Groups !");
						return false;
					}
				System.out.println("Error: Tutorial Group Not Found !");
				return false;
			}
			else
			{NoOfVacancies--;
			studentId[NoOfSeatsTake++]=stdId;
			}
		else {
			System.out.println("Error: No Vacancies in course ! ");
			return false;
		}
		return false;

	}

	public boolean isCourse(String course_id) {
		if (course_id == CourseID)
			return true;
		return false;
	}

	public double[] retCourseWorkWeightage() {
		return subCompWeightage;
	}

	public String[] retCourseWorkWeightageNames() {
		return subCompWeightageNames;
	}

	public int retNumOfCourseWorkSubComp() {
		return NoOfComp;
	}

	public boolean retIsSubComp() {
		return isSubComp;
	}

	public double retExamPerc(double marks, double totalMark) {
		return (marks / totalMark) * examWeightage;
	}

	public double retCourseWorkPerc(double marks) {
		return marks * courseWorkWeightage;

	}

	public String retCourseID() {
		return CourseID;
	}

	public String retProf() {
		return Professor;
	}

	public String CourseName() {
		return CourseName;
	}

}
