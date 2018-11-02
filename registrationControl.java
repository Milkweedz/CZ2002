
public class registrationControl {
	
	public static void main(String[] args) {
		RegStudent();
		registerCourse();
	}

	public void RegStudent()
	{
		Student stud = new Student();
		stud.inputStudDetails;
			list = (ArrayList)SerializeDB.readSerializedObject("student.dat");
		
			list.add(stud);

			SerializeDB.writeSerializedObject("student.dat", list);}

	public void registerCourse() // returns status
	{
		Course crse = new Course();
		crse.enterCourseInformation(0);
		list = (ArrayList) SerializeDB.readSerializedObject("Course.dat");

		list.add(crse);

		SerializeDB.writeSerializedObject("Course.dat", list);
	}

}
//	Deregister student
//	deregisterCourse(int courseId, int studentId): int //returns  status

