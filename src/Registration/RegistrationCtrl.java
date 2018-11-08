package Registration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RegistrationCtrl {
	public static void registerStudentForCourse() {
		Student student;
		Course course;
		int StudentID = RegistrationUI.readStudentID(new Scanner(System.in));
		if (!student.existsStudent(StudentID)) {
			StudentUI.studentIdNonexist();
		} else {
			Student student = Student.readInFile(studentID);
			String FName, LName;
			FName = student.getStudentFName();
			LName = student.getStudentLName();
			int CourseID = RegistrationUI.readCourseID(new Scanner(System.in));
			if (!Course.existsCourse(CourseID))
				CourseUI.courseIdNonexist();
			else {
				Course course = Course.readInFile(courseID);
				if (course.courseRegister() && isInFile(StudentID, CourseID)) {
					String courseName, coordinator;
					courseName = course.getCourseName();
					coordinator = course.coordinator();
					saveToFile(StudentID, FName, LName, CourseID, courseName, coordinator);
				}

			}
		}

	}

	public static void saveToFile(int StudentID, String FName, String LName, int CourseID, String courseName,
			String coordinator) {
		String regFile = "src\\Registration\\Registration.json";
		JSONObject file = readJSON(regFile);
		// JSONArray array = (JSONArray) file.get("data");

		JSONObject obj = new JSONObject();
		obj.put("firstname", FName);
		obj.put("lastname", LName);
		obj.put("courseid", Integer.toString(CourseID));
		obj.put("coursename", courseName);
		obj.put("coordinator", coordinator);

		file.put(Integer.toString(StudentID) + "." + Integer.toString(CourseID), obj);
		// file.replace("data", array);

		writeJSON(file, regFile);

		// add new course to courseid cache
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\Registration\\reglist.txt", true))) {
			bw.write(StudentID + "." + CourseID + "\n");
		} catch (IOException ex) {
			System.out.println("IOException! reglist.txt not found?");
			ex.printStackTrace();
		}
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

	public static void deleteInFile(int studentID, int courseID) {
		String courseFile = "src\\Registration\\Registration.json";
		JSONObject file = readJSON(courseFile);
		file.remove(Integer.toString(StudentID) + "." + Integer.toString(CourseID));

		writeJSON(file, courseFile);

		File readFile = null;
		File tempFile = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			// remove entry from course list file
			readFile = new File("src\\Registration\\reglist.txt");
			tempFile = File.createTempFile("file", ".txt", readFile.getParentFile());
			br = new BufferedReader(new FileReader(readFile));
			bw = new BufferedWriter(new FileWriter(tempFile));

			for (String line = ""; line != null; line = br.readLine()) {
				if (!line.equals(Integer.toString(studentID) + "." + Integer.toString(courseID))) {
					bw.write(line);
				}
			}
			// System.out.println("Delete error. CourseID not found.");
		} catch (IOException ex) {
			System.out.println("IOException! reglist.txt not found?");
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

	public static boolean isInFile(int studentID, int courseID) {
		String courseFile = "src\\Registration\\Registration.json";
		JSONObject file = readJSON(courseFile);
		JSONObject obj = (JSONObject) file.get(Integer.toString(studentID) + "." + Integer.toString(courseID));

		if (obj == null)
			return false;
		else
			return true;
	}

public List<Integer> studentCourses(int StudentID)
{  
	List<Integer> courses;
	File readFile = null;
	BufferedReader br = null;
	try {
		readFile = new File("src\\Registration\\reglist.txt");
		br = new BufferedReader(new FileReader(readFile));

		for (String line = ""; line != null; line = br.readLine()) {
			String array1[]= line.split(".");
			if(Integer.getInteger(array1[0])==StudentID)
				courses.add(Integer.getInteger(array1[1]));
		}
		return courses;
		// System.out.println("Delete error. CourseID not found.");
	} catch (IOException ex) {
		System.out.println("IOException! reglist.txt not found?");
		ex.printStackTrace();
	} finally {
		try {
			if (readFile != null && br != null ) {
				br.close();
				readFile.delete();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	return courses;;
}

}
