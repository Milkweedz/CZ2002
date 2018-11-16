package Course;

import FileManager.InputMismatchHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**
 * Represents the user interface for a course
 * Responsible for reading in and displaying all the data with respect to a course
 * @author Nikhita
 * @version 1.0
 * @since 2018-11-15
 */
public class CourseUI {
    InputMismatchHandler imh = new InputMismatchHandler();

    public int courseCtrlChoice(){
        Scanner scan = new Scanner (System.in);
        System.out.println("\nWhat would you like to do?");
        System.out.println("\n0: View Course Details \n1: Create Course \n2: Edit Course \n3: Delete Course \n4: View Mark Weights \n5: Change Mark Weights \n6: List Courses \n7: Check Vacancy \n8: Quit");

        return imh.checkInt();
    }

    /**
     * Reads or inputs course ID from user
     * @return
     */
    public int readCourseID(){
        Scanner scan = new Scanner(System.in);

        System.out.print("\nEnter course ID: ");
        return imh.checkInt();
    }
    /**
     * Reads course data (name, coordinator, type, capacity) from user
     * @return Course data as String array
     */
    public String[] readCourseData(){
        Scanner scan = new Scanner(System.in);
        String data[] = new String[4];

        System.out.print("\nEnter course name: ");
        String courseName = scan.nextLine();

        System.out.print("\nEnter course coordinator: ");
        String coordinator = scan.nextLine();

        System.out.print("\nEnter course type (lec/tut/lab): ");
        //private enum COURSETYPE {Lec, LecTut, LecTutLab, NULL};
        String[] options = {"LEC", "TUT", "LAB"};
        String tempType = imh.checkString(options);

        System.out.print("\nEnter course capacity: ");
        int maxVacancies = imh.checkInt();

        data[0] = courseName;
        data[1] = coordinator;
        data[2] = tempType;
        data[3] = Integer.toString(maxVacancies);

        return data;
    }
    /**
     * Reads in the list of tutorials for a course
     * @return List of all the tutorials (number and group name) for a course
     */
    public ArrayList<String> readTutorials() {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> tutorials = new ArrayList<>();
        System.out.print("\nEnter number of tutorials groups : ");
        //String dummychar = "";
        int numberOfTutorialGroups = imh.checkInt();;
        for (int i = 0; i < numberOfTutorialGroups; i++) {
            //scan.next(dummychar);
            System.out.print("\nEnter tutorial " + i + " group name: ");
            tutorials.add(scan.next());
        }
        return tutorials;
    }
    /**
     * Prints list of the courses
     * @param courseList List of all the courses as ArrayList (String)
     */
    public void listCourses(ArrayList<String> courseList){
        System.out.print("\nCourse IDs: ");
        courseList.forEach((course) -> System.out.print(course + ", "));
        System.out.print("\n");
    }

    /**
     * Prints course data
     * @param data Data of a course as String array
     */
    public void displayCourseData(String[] data){
        for(int i=0; i<data.length; i++){
            System.out.println(data[i]);
        }
        System.out.println("\n");
    }
    /**
     * Displays vacancies available in a course
     * @param vacancies Vacancies available in a course
     */
    public void displayVacancies(int vacancies){
        System.out.println("Vacancies: " + vacancies);
    }
    /**
     * Inputs and stores exam and coursework component weights for a course
     * @return Exam and coursework component weights for a course as HashMap
     */
    public HashMap<String, String> inputMarkWeights(){
        HashMap<String, String> markWeights = new HashMap<String, String>();
        int examWeight;
        int numComponents;
        String[] courseworks;
        int[] courseworkWeights;
        Scanner scan = new Scanner(System.in);

        //input exam and coursework weights
        while(true) {
            System.out.print("\nEnter exam weight in percentage: ");
            examWeight = imh.checkInt();; //change this to float possibly
            System.out.printf("\nExam weight: %d, Coursework weight: %d", examWeight, 100-examWeight) ;
            break;
        } //relic of old code: while loop allows a different implementation where coursework weight is not out of 100

        //input coursework component grades
        while(true) {
            System.out.print("\nHow many components in coursework: ");
            numComponents = imh.checkInt();;
            int totalWeight=0;

            //create temporary arrays to store coursework types and respective weights
            courseworks = new String[numComponents];
            courseworkWeights = new int[numComponents];
            for (int i = 0; i < numComponents; i++) {
                System.out.printf("\nEnter name of component %d: ", i+1);
                courseworks[i] = scan.nextLine();
                System.out.printf("\nEnter weight of %s: ", courseworks[i]);
                courseworkWeights[i] = imh.checkInt();;
                totalWeight+=courseworkWeights[i];
            }

            if (totalWeight!=100)
                System.out.println("Coursework component weights don't add up to total weight! Try again.");
            else break;
        }
        System.out.println("Inserted mark weight settings: ");
        markWeights.put("exam", Integer.toString(examWeight));
        //store to hashmap object
        for (int i=0; i<numComponents; i++){
            markWeights.put(courseworks[i], Integer.toString(courseworkWeights[i]));
        }

        return markWeights;
    }


    //error messages
    /**
     * Prints error message when course ID already exists
     */
    public void courseIdTaken(){
        System.out.println("\nCourse ID already exists! Try again.");
    }
    /**
     * Prints error message when course ID doesn't exist
     */
    public void courseIdNonexist(){
        System.out.println("\nCourse ID doesn't exist! Try again.");
    }
    /**
     * Prints error message when no mark weightages are found for the entered course
     */
    public static void hashmapEmpty(){
        System.out.println("\nNo mark weightages found for this course!");
    }
    //!error messages

}











//
//public class Course.CourseUI {
//    private String CourseID;
//    private int NoOfVacancies, NoOfSeats, NoOfComp, NoOfTutGroups, NoOfSubCom;
//    private String CourseName, Professor;
//    private boolean HaveTutorials, HaveLabs, isSubComp;
//    private double examWeightage, courseWorkWeightage;
//    private double[] subCompWeightage;
//    private String[] subCompWeightageNames;
//    private tutorialLabs[] Tutorials_Labs;
//
//    private int tutorID, LabAsstID, maxNoOfStud, noOfStud;
//    private String tutorialGp, tutorName, LabAsstName;
//    private int[] studentId;
//    private boolean haveLab;
//
//    public int enterCourseInformation(int check) {
//        Scanner input = new Scanner(System.in);
//        String dummychar;
//        if (check == 1 || check == 0) {
//            System.out.println("Enter Course.Course ID : ");
//            CourseID = input.next();
//            dummychar = input.nextLine();
//        }
//        if (check == 2 || check == 0) {
//            System.out.println("Enter Course.Course Name : ");
//            CourseName = input.nextLine();
//            System.out.println("Enter Professor's Name : ");
//            Professor = input.nextLine();
//        }
//        if (check == 3 || check == 0) {
//            System.out.println("Does course have tutorials?(true/false) : ");
//            HaveTutorials = input.nextBoolean();
//        }
//        if (check == 4 || check == 0) {
//            System.out.println("Does course have labs?(true/false) : ");
//            HaveLabs = input.nextBoolean();
//        }
//        if ((check == 5 || check == 0) && HaveTutorials) {
//            System.out.println("Enter number of tutorial groups : ");
//            NoOfTutGroups = input.nextInt();
//            Tutorials_Labs = new tutorialLabs[NoOfTutGroups];
//            System.out.println("Enter Maximum number of students per tutorial group :");
//            int maxNoStud = input.nextInt();
//            for (int i = 0; i < NoOfTutGroups; i++) {
//                Tutorials_Labs[i] = new tutorialLabs(HaveLabs);
//                inputTLData(0, maxNoStud);
//            }
//            NoOfSeats = maxNoStud * NoOfTutGroups;
//            NoOfVacancies = NoOfSeats;
//        } else if ((check == 5 | check == 0)) {
//            System.out.println("Enter Maximum number of students per tutorial group :");
//            NoOfSeats = input.nextInt();
//            NoOfVacancies = NoOfSeats;
//        }
//
//        if (check == 6 || check == 0) {
//            System.out.println("Enter exam weightage : ");
//            examWeightage = input.nextDouble();
//        }
//        if (check == 7 || check == 0) {
//            System.out.println("Enter Course.Course Work wightage : ");
//            courseWorkWeightage = input.nextDouble();
//        }
//        if (check == 8 || check == 0) {
//            System.out.println("Does course have Sub-components for Course.Course Work :");
//            isSubComp = input.nextBoolean();
//        }
//        if ((check == 9 || check == 0) && isSubComp) {
//            System.out.println("Enter Number of Sub-components :");
//            NoOfSubCom = input.nextInt();
//            subCompWeightage = new double[NoOfSubCom];
//            subCompWeightageNames = new String[NoOfSubCom];
//            for (int i = 0; i < NoOfSubCom; i++) {
//                dummychar = input.next();
//                System.out.println("Enter Name of sub component " + i + " : ");
//                subCompWeightageNames[i] = input.nextLine();
//                System.out.println("Enter weightage of sub component " + subCompWeightageNames[i] + " : ");
//                subCompWeightage[i] = input.nextDouble();
//            }
//        }
//        return 1;
//    }
//
//    public void updateCourseAssessmentWeightage() {
//        Scanner input = new Scanner(System.in);
//        String dummychar;
//        System.out.println("Enter exam weightage : ");
//        examWeightage = input.nextDouble();
//        System.out.println("Enter Course.Course Work wightage : ");
//        courseWorkWeightage = input.nextDouble();
//        System.out.println("Does course have Sub-components for Course.Course Work :");
//        isSubComp = input.nextBoolean();
//        if (isSubComp) {
//            System.out.println("Enter Number of Sub-components :");
//            NoOfSubCom = input.nextInt();
//            subCompWeightage = new double[NoOfSubCom];
//            subCompWeightageNames = new String[NoOfSubCom];
//            for (int i = 0; i < NoOfSubCom; i++) {
//                dummychar = input.next();
//                System.out.println("Enter Name of sub component " + i + " : ");
//                subCompWeightageNames[i] = input.nextLine();
//                System.out.println("Enter weightage of sub component " + subCompWeightageNames[i] + " : ");
//                subCompWeightage[i] = input.nextDouble();
//            }
//        }
//    }
//
//    public boolean addStudent(int stdId, String tutLab_Group) {
//        if (NoOfVacancies > 0)
//            for (int i = 0; i < NoOfTutGroups; i++) {
//                if (Tutorials_Labs[i].retTutGrp() == tutLab_Group)
//                    if (Tutorials_Labs[i].addStudent(stdId)) {
//                        NoOfVacancies--;
//                        return true;
//                    } else {
//                        System.out.println("Error: No Vacancies in Groups !");
//                        return false;
//                    }
//                System.out.println("Error: Tutorial Group Not Found !");
//                return false;
//            }
//        else {
//            System.out.println("Error: No Vacancies in course ! ");
//            return false;
//        }
//        return false;
//
//    }
//
//    public void inputTLData(int check,int maxStud) {
//        Scanner input = new Scanner(System.in);
//        String dummychar;
//        studentId = new int[maxStud];
//        if (check == 1 || check == 0) {
//            System.out.println("Enter tutorial group : ");
//            tutorialGp = input.nextLine();
//        }
//        if (check == 2 || check == 0) {
//            System.out.println("Enter Tutor Name : ");
//            tutorName = input.nextLine();
//            System.out.println("Enter tutorID : ");
//            tutorID = input.nextInt();
//            dummychar = input.next();
//        }
//        if ((check == 3 || check == 0) && haveLab == true) {
//            System.out.println("Enter Lab Assistant Name : ");
//            LabAsstName = input.nextLine();
//            System.out.println("Enter Lab Assistant ID : ");
//            LabAsstID = input.nextInt();
//            dummychar = input.next();
//            dummychar = input.next();
//        }
//
//
//    }
//}
