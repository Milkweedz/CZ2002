package Student;

public class Student {
	private String studentFName, studentLName, department;
	private int studentID, yearOfStudy, gender, NumOfAU;
	private double CGPANum, CGPA;
	private int graduate;
	
	public Student() {
		studentID = -1;
		studentFName = "";
		studentLName = "";
		yearOfStudy = -1;
		NumOfAU = -1;
		CGPANum = -1.0;
		CGPA = -1.0;
		gender = -1;
		department = " ";
		graduate = -1;
	}

    public static int saveToFile(Student student){


	    return 0;
    }

    //getters and setters below
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentFName() {
        return studentFName;
    }

    public void setStudentFName(String studentFName) {
        this.studentFName = studentFName;
    }

    public String getStudentLName() {
        return studentLName;
    }

    public void setStudentLName(String studentLName) {
        this.studentLName = studentLName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getGraduate() {
        return graduate;
    }

    public void setGraduate(int graduate) {
        this.graduate = graduate;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getNumOfAU() {
        return NumOfAU;
    }

    public void setNumOfAU(int numOfAU) {
        NumOfAU = numOfAU;
    }

    public double getCGPANum() {
        return CGPANum;
    }

    public void setCGPANum(double CGPANum) {
        this.CGPANum = CGPANum;
    }

    public double getCGPA() {
        return CGPA;
    }

    public void setCGPA(double CGPA) {
        this.CGPA = CGPA;
    }

//	public void printStudDetails() {
//		System.out.print(String.format("|%-1d.%30s|", 1, " Student.Student ID "));
//		System.out.println(String.format(" %-30d|", Student_ID));
//		System.out.print(String.format("|%-1d.%30s|", 2, " Student.Student's Name "));
//		System.out.println(String.format(" %-30s|", StudentLName + "," + StudentFName));
//		System.out.print(String.format("|%-1d.%30s|", 3, " Year Of Study "));
//		System.out.println(String.format(" %-30d|", YearOfStudy));
//		System.out.print(String.format("|%-1d.%30s|", 4, " Number of AUs obtained "));
//		System.out.println(String.format(" %-30d|", NumOfAU));
//		System.out.print(String.format("|%-1d.%30s|", 5, " CGPA "));
//		System.out.println(String.format(" %-30.2f|", CGPA));
//		System.out.print(String.format("|%-1d.%30s|", 6, "Gender "));
//		System.out.println(String.format(" %-30s|", gender));
//		System.out.print(String.format("|%-1d.%30s|", 7, "Department of study "));
//		System.out.println(String.format(" %-30s|", department));
//		System.out.print(String.format("|%-1d.%30s|", 8, "Level Of Study "));
//		System.out.println(String.format(" %-30s|", level));
//	}

}
