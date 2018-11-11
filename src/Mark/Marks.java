package Mark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Course.Course;


public class Marks {
	private int StudentID;
	private int CourseID;
	private int NoOfComp;
	private double ExamMark;
	private double courseWorkMark[];
	private boolean courseWorkMarkSet, examMarkSet;

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
		String MarkFile = "src\\Mark\\marks.json";
		JSONObject file = readJSON(MarkFile);
		// JSONArray array = (JSONArray) file.get("data");

		JSONObject obj = new JSONObject();
		// obj.put("studentid", Integer.toString(student.getStudentID()));
		obj.put("numofcomp", String.valueOf(marks.retNumOfComp()));
		HashMap<String, String> markWeights = Course.getMarkWeights(marks.retCourseID());
		int i=0;
		for (Map.Entry<String, String> entry : markWeights.entrySet())         //somewhat of a poor implementation here
			obj.put(entry.getKey(),String.valueOf(marks.retStudentCourseWorkMarks(i++)));

		file.put(Integer.toString(marks.retStudentID()) + "." + Integer.toString(marks.retCourseID()), obj);
		// file.replace("data", array);

		writeJSON(file, MarkFile);

		// add new student to studentid cache
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\Mark\\marklist.txt", true))) {
			bw.write(marks.retStudentID() + "." + marks.retCourseID() + "\n");
		} catch (IOException ex) {
			System.out.println("IOException! marklist.txt not found?");
			ex.printStackTrace();
		}

	}

	public static void deleteInFile(int studentID,int courseID) {
		String markFile = "src\\Mark\\marks.json";
		JSONObject file = readJSON(markFile);
		file.remove(Integer.toString(studentID)+"."+Integer.toString(courseID));

		writeJSON(file, markFile);

		File readFile = null;
		File tempFile = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			// remove entry from student list file
			readFile = new File("src\\Mark\\marklist.txt");
			tempFile = File.createTempFile("file", ".txt", readFile.getParentFile());
			br = new BufferedReader(new FileReader(readFile));
			bw = new BufferedWriter(new FileWriter(tempFile));

			for (String line = ""; line != null; line = br.readLine()) {
				if (!line.equals(Integer.toString(studentID)+"."+Integer.toString(courseID))) {
					bw.write(line);
				}
			}
			// System.out.println("Delete error. StudentID not found.");
		} catch (IOException ex) {
			System.out.println("IOException! marklist.txt not found?");
			ex.printStackTrace();
		} finally {
			try {
				if (readFile != null && tempFile != null && br != null && bw != null) {
					br.close();
					bw.close();
					readFile.delete();
					tempFile.renameTo(readFile);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static Marks readInFile(int studentID,int courseID) {
		String markFile = "src\\Mark\\marks.json";
		JSONObject file = readJSON(markFile);
		String getTarget = Integer.toString(studentID)+"."+Integer.toString(courseID);
        //System.out.println(getTarget);
		JSONObject obj = (JSONObject) file.get(getTarget);
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
		HashMap<String, String> markWeights = Course.getMarkWeights(marks.retCourseID());
		int i=0;
		for (Map.Entry<String, String> entry : markWeights.entrySet())
			courseMarks[i++]=Double.valueOf((String)obj.get(entry.getKey()));
		marks.setStudentCourseMarks(courseMarks);
		return marks;
	}

	// either returns JSON file object or null.
	private static JSONObject readJSON(String fileName) {
		try {
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(new FileReader(fileName));
		} catch (IOException ex) {
			System.out.println("IOException!");
			ex.printStackTrace();
		} catch (ParseException parsex) {
			System.out.println("ParseException!");
			// parsex.printStackTrace();
		}
		return new JSONObject();
	}

	private static void writeJSON(JSONObject file, String fileName) {

		try {
			FileWriter writer = new FileWriter(fileName, false);
			file.writeJSONString(writer);
			writer.close();
		} catch (IOException ex) {
			System.out.println("IOException!");
			ex.printStackTrace();
		}
	}

	public boolean isStudMarks(int StdID, int CrseID) {
		if (StudentID == StdID && CourseID == CrseID)
			return true;
		else
			return false;
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


	public int retStudentID()
	{
		return StudentID;
	}

	public int retNumOfComp() {
		return NoOfComp;
	}

	public int retCourseID() {
		return CourseID;
	}

	public double retStudentCourseWorkMarks(int n) {
		return courseWorkMark[n];
	}



}
