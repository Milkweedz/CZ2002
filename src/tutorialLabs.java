public class tutorialLabs {
	private int tutorID, LabAsstID, maxNoOfStud, noOfStud;
	private String tutorialGp, tutorName, LabAsstName;
	private int[] StudentId;
	private boolean haveLab;

	public tutorialLabs(boolean Lab) {
		tutorialGp = "";
		tutorName = "";
		LabAsstName = "";
		tutorID = 0;
		LabAsstID = 0;
		StudentId = new int[0];
		noOfStud = 0;
		maxNoOfStud = 0;
		haveLab = Lab;
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

	public boolean addStudent(int studentId) {
		if (noOfStud < maxNoOfStud) {

			StudentId[noOfStud++] = studentId;
			return true;
		}
		return false;
	}

	public boolean retStudExist(int studentId) {
		for (int i = 0; i < noOfStud; i++)
			if (StudentId[i] == studentId)
				return true;
		return false;
	}

}
