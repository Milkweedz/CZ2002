package Mark;

import Registration.RegistrationCtrl;

import java.util.*;

import Course.Course;
import Registration.RegistrationUI;

public class MarksCtrl {
	public void init(){

		int choice;

		do {
			choice = MarksUI.marksCtrlChoice();
			switch(choice){
				case 1:
					addMarks();
					break;
				case 2:
					editMarks();
					break;
				case 3:
					deleteMarks();
					break;
				case 4:
					viewMarks();
					break;
			}
		} while (choice<5);    //look at studentUI, 5 happens to be the option to quit
	}

	public void viewMarks() {
		int studentID,courseID;
		MarksUI marksUI = new MarksUI();

		studentID = marksUI.readStudentID(new Scanner(System.in));
		courseID = marksUI.readCourseID(new Scanner(System.in));
		if (!Marks.existsMarks(studentID,courseID)) {
			MarksUI.studentCourseIdNonexist();
		} else {
			Marks marks = Marks.readInFile(studentID,courseID);


			MarksUI.displayMarksData(marks);
		}

	}


	public void addMarks(){
        MarksUI marksUI = new MarksUI();
        int studentID=MarksUI.readStudentID(new Scanner(System.in));
        int courseID=MarksUI.readCourseID(new Scanner(System.in));
        List<Integer> courses = RegistrationCtrl.studentCourses(studentID);
        if(!courses.contains(courseID)){
            MarksUI.studentCourseIdNonexist();
        }
        else if(Marks.existsMarks(studentID,courseID)){
            MarksUI.studentCourseIdexist();
        }
        else {
            double[] data = MarksUI.readMarksData(new Scanner(System.in),Course.getMarkWeights(courseID));
            Marks marks = makeMarkObj(studentID,courseID,data);

            Marks.saveToFile(marks);
            marksUI.successAdd();
        }
    }

	public void editMarks() {
		MarksUI markUI = new MarksUI();
		int studentID = markUI.readStudentID(new Scanner(System.in));
		int courseID = markUI.readCourseID(new Scanner(System.in));
		if (Marks.existsMarks(studentID, courseID)) {
			double[] data = markUI.readMarksData(new Scanner(System.in), Course.getMarkWeights(courseID));
			Marks marks = makeMarkObj(studentID,courseID, data);
			Marks.deleteInFile(studentID, courseID);
			Marks.saveToFile(marks);
			markUI.successEdit();
		} else
			markUI.studentCourseIdNonexist(); // error message
	}

	public void deleteMarks() {
		MarksUI marksUI = new MarksUI();
		int studentID = MarksUI.readStudentID(new Scanner(System.in));
		int courseID = MarksUI.readCourseID(new Scanner(System.in));
		if (Marks.existsMarks(studentID,courseID)) {
			Marks.deleteInFile(studentID,courseID);
			marksUI.successRemove();
		} else
			marksUI.studentCourseIdNonexist();
	}

	private Marks makeMarkObj(int studentID, int courseID, double[] args) {
		Marks marks = new Marks(Course.getMarkWeights(courseID).size());

		marks.setStudentID(studentID);
		marks.setCourseID(courseID);
		marks.setStudentCourseMarks(args);

		return marks;
	}

    public static double retTotalPercentage(int studentID, int courseID) {
        //System.out.println(courseID + "DEBUG");
        Marks marks = Marks.readInFile(studentID, courseID);
        if (marks == null) {
            return -1;
        }
        double sumMarks = 0.0;
        double newMark;
        HashMap<String, String> markWeights = Course.getMarkWeights(marks.retCourseID());

        int i = 0;
        for (Map.Entry<String, String> entry : markWeights.entrySet()) {         //somewhat of a poor implementation here
            newMark = Integer.parseInt(entry.getValue()) * marks.retStudentCourseWorkMarks(i++) / 100.0;            //coursework component scores
            //System.out.println("DEBUG" + newMark);
            if (i==1){
                sumMarks += newMark;
                //System.out.println("DEBUG EXAM");
            } else {
                sumMarks += newMark * (100 - Integer.parseInt(markWeights.get("exam"))) / 100;
            }
        }
        return sumMarks;
    }

}
