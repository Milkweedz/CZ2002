package Mark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import FileManager.FileManager;


import Course.Course;
/**
 * Represents the marks entity of a student for a course
 * A course can have multiple marks from multiple students
 * @author Nam
 * @version 1.0
 * @since 2018-11-15
 */

public class Marks {
	/**
	 * The identification number of the student
	 */
	private int StudentID;
	/**
	 * The identification number of the course
	 */
	private int CourseID;
	/**
	 * The number of components in coursework
	 */
	private int NoOfComp;
	/**
	 * The exam mark
	 */
	private double ExamMark;
	/**
	 * The coursework mark(s)
	 */
	private double courseWorkMark[];
	/**
	 * The boolean values to check whether the student has coursework/exam marks
	 */
	private boolean courseWorkMarkSet, examMarkSet;

	/**
	 * File names in the directories
	 */
	private static final String marksFile = "src\\Mark\\marks.txt";
	private static final String listFile = "src\\Mark\\marklist.txt";
	
	/**
	 * Create a new Marks object with the current studentID and courseID
	 * @param n the number of composition in coursework
	 */
	public Marks(int n) {
		StudentID = 0;
		CourseID = 0;
		NoOfComp = n;
		ExamMark = 0.0;
		courseWorkMark = new double[n];
		courseWorkMarkSet = false;
		examMarkSet = false;
	}
	/**
	 * Check if a student has marks for a course
	 * @param studentID the ID of the student
	 * @param courseID the ID of the course
	 * @return the boolean value whether he has or not
	 */
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
	/**
	 * Saving a marks object to a file
	 * @param marks the mark of a student taking a course
	 */
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
	/**
	 * Delete a marks object from a file
	 * @param studentID the ID of the student
	 * @param courseID the ID of the course
	 */
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

	/**
	 * Set the student ID
	 * @param studentID the ID of the student
	 */
	public void setStudentID(int studentID)
	{
		StudentID = studentID;
	}

	/**
	 * Set the course ID
	 * @param courseID the ID of the course
	 */
	public void setCourseID(int courseID) {
		CourseID = courseID;
	}

	/**
	 * Set the marks of the student for this course's coursework
	 * @param courseWorkMarks 
	 */
	public void setStudentCourseMarks(double courseWorkMarks[]) {
		courseWorkMarkSet = true;
		for (int i = 0; i < NoOfComp; i++) {
			this.courseWorkMark[i] = courseWorkMarks[i];
		}
	}
	
	/**
	 * Set the number of composition in the coursework
	 * @param n number of composition
	 */
	public void setNoOfComp(int n)
	{
		NoOfComp=n;
	}

	/**
	 * Check whether the student's coursework marks are inputted
	 * @return
	 */
	public boolean courseWorkMarkSet() {
		return courseWorkMarkSet;
	}

	/**
	 * Get the ID of the student
	 * @return this student's ID
	 */
	public int getStudentID()
	{
		return StudentID;
	}

	/**
	 * Get the number of composition of the course's coursework
	 * @return this course's number of coursework composition
	 */
	public int getNumOfComp() {
		return NoOfComp;
	}

	/**
	 * Get the ID of the course
	 * @return this course's ID
	 */
	public int getCourseID() {
		return CourseID;
	}

	/**
	 * Get the coursework marks of the course for a student
	 * @param n number of coursework component
	 * @return the coursework marks of this student in this course
	 */
	public double getStudentCourseWorkMarks(int n) {
		return courseWorkMark[n];
	}



}
