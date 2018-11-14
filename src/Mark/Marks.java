package Mark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import FileManager.FileManager;


import Course.Course;


public class Marks {
	private int StudentID;
	private int CourseID;
	private int NoOfComp;
	private double ExamMark;
	private double courseWorkMark[];
	private boolean courseWorkMarkSet, examMarkSet;

	private static final String marksFile = "src\\Mark\\marks.txt";
	private static final String listFile = "src\\Mark\\marklist.txt";

	public Marks(int n) {
		StudentID = 0;
		CourseID = 0;
		NoOfComp = n;
		ExamMark = 0.0;
		courseWorkMark = new double[n];
		courseWorkMarkSet = false;
		examMarkSet = false;
	}

	public static boolean existsMarks(int studentID, int courseID) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("src\\Mark\\marklist.txt"));
			String nextID = br.readLine();
			while (nextID != null) {
				String array1[]= nextID.split("\\.");
				if (Integer.parseInt(array1[0])==studentID && Integer.parseInt(array1[1])==courseID) {
					return true;
				}
				nextID = br.readLine();
			}
			return false;
		} catch (IOException ex) {
			System.out.println("IOException! marklist.txt not found?");
			ex.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return false;
	}

	public static void saveToFile(Marks marks) {
        HashMap<String,String> obj = new HashMap<String,String>();
		// obj.put("studentid", Integer.toString(student.getStudentID()));
		obj.put("numofcomp", String.valueOf(marks.getNumOfComp()));
		HashMap<String, String> markWeights = Course.getMarkWeights(marks.getCourseID());
		int i=0;
		for (Map.Entry<String, String> entry : markWeights.entrySet())         //somewhat of a poor implementation here
			obj.put(entry.getKey(),String.valueOf(marks.getStudentCourseWorkMarks(i++)));

        FileManager.saveToFile(marks.getStudentID(),marks.getCourseID(), obj, marksFile, listFile);

	}

    public static void deleteInFile(int studentID, int courseID) {
        FileManager.deleteInFile(studentID,courseID, marksFile, listFile);
    }
	public static Marks readInFile(int studentID,int courseID) {
        HashMap<String,String> obj = FileManager.accessFile(studentID,courseID, marksFile);
        if(obj == null){
            return null;
        }
		Object numComponents = obj.get("numofcomp");
		if (numComponents == null){return null;}
		int n = Integer.parseInt((String) numComponents);
		Marks marks = new Marks(n);
		double courseMarks[] = new double[n];
		marks.setStudentID(studentID);
		marks.setCourseID(courseID);
		HashMap<String, String> markWeights = Course.getMarkWeights(marks.getCourseID());
		int i=0;
		for (Map.Entry<String, String> entry : markWeights.entrySet())
			courseMarks[i++]=Double.valueOf((String)obj.get(entry.getKey()));
		marks.setStudentCourseMarks(courseMarks);
		return marks;
	}


	public void setStudentID(int studentID)
	{
		StudentID = studentID;
	}

	public void setCourseID(int courseID) {
		CourseID = courseID;
	}

	public void setStudentCourseMarks(double courseWorkMarks[]) {
		courseWorkMarkSet = true;
		for (int i = 0; i < NoOfComp; i++) {
			this.courseWorkMark[i] = courseWorkMarks[i];
		}
	}
	public void setNoOfComp(int n)
	{
		NoOfComp=n;
	}


	public boolean courseWorkMarkSet() {
		return courseWorkMarkSet;
	}


	public int getStudentID()
	{
		return StudentID;
	}

	public int getNumOfComp() {
		return NoOfComp;
	}

	public int getCourseID() {
		return CourseID;
	}

	public double getStudentCourseWorkMarks(int n) {
		return courseWorkMark[n];
	}



}
