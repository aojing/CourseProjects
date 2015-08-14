package edu.gatech.seclass.gradescalculator;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyCourseTest {
	Students students = null;
    Grades grades = null;
    Course course = null;
    static final String GRADES_DB = "DB" + File.separator + "GradesDatabase6300-grades.xlsx";
    static final String GRADES_DB_GOLDEN = "DB" + File.separator + "GradesDatabase6300-grades-goldenversion.xlsx";
    static final String STUDENTS_DB = "DB" + File.separator + "GradesDatabase6300-students.xlsx";
    static final String STUDENTS_DB_GOLDEN = "DB" + File.separator + "GradesDatabase6300-students-goldenversion.xlsx";
    
	@Before
	public void setUp() throws Exception {
		FileSystem fs = FileSystems.getDefault();
		Path gradesdbfilegolden = fs.getPath(GRADES_DB_GOLDEN);
		Path gradesdbfile = fs.getPath(GRADES_DB);
		Files.copy(gradesdbfilegolden, gradesdbfile, REPLACE_EXISTING);
		Path studentsdbfilegolden = fs.getPath(STUDENTS_DB_GOLDEN);
		Path studentsdbfile = fs.getPath(STUDENTS_DB);
		Files.copy(studentsdbfilegolden, studentsdbfile, REPLACE_EXISTING);
		students = new Students(STUDENTS_DB);
		grades = new Grades(GRADES_DB);
		course = new Course(students, grades);
	}

    @After
    public void tearDown() throws Exception {
        students = null;
        grades = null;
        course = null;
    }

    //TaskCards 3.4: provide a way to add students and update studentRoster
    //also test adding existing student
	@Test
	public void testAddStudent() {
		Student newS1 = new Student("Rachael Green", "901234517", course);
		course.addStudent(newS1);
		course.updateStudents(new Students(STUDENTS_DB));
		int numStudents = course.getNumStudents();
		assertEquals(17, numStudents);
		Student newS2 = new Student("Josepha Jube", "901234502", course);
		course.addStudent(newS2);
		course.updateStudents(new Students(STUDENTS_DB));
		numStudents = course.getNumStudents();
		assertEquals(17, numStudents);// existing student is not added again

		Student student = course.getStudentByID("901234517");
		assertEquals("Rachael Green", student.getName());

		Student student2 = course.getStudentByName("Josepha Jube");
		assertEquals(80, student2.getAttendance());
	}

	// TaskCards 3.5: provide a way to add projects
	@Test
	public void testAddProject() throws IOException {
		course.addProject("project Final");
		course.updateGrades(new Grades(GRADES_DB));
		assertEquals(4, course.getNumProjects());
		course.addProject("Project Extra");
		course.updateGrades(new Grades(GRADES_DB));
		assertEquals(5, course.getNumProjects());
	}
 
	// TaskCards 3.6: provide a way to add grades for projects
	@Test
	public void testAddGradesForProjects() {
		String projectName = "Project: gradescalculator";
		Student student1 = new Student("Freddie Catlay", "901234501", course);
		Student student2 = new Student("Shevon Wise", "901234504", course);
		Student student3 = new Student("Rastus Kight", "901234512", course);
		course.addProject(projectName);
		course.updateGrades(new Grades(GRADES_DB));
		// add grades for projects
		HashMap<Student, Integer> grades = new HashMap<Student, Integer>();
		grades.put(student1, 97);
		grades.put(student2, 94);
		grades.put(student3, 100);
		course.addGradesForProjects(projectName, grades);
		// add individual contribution
		HashMap<Student, Integer> contributions = new HashMap<Student, Integer>();
		contributions.put(student1, 98);
		contributions.put(student2, 95);
		contributions.put(student3, 90);
		course.addIndividualContributions(projectName, contributions);
		course.updateGrades(new Grades(GRADES_DB));
		// test project grade
		assertEquals(95, course.getProjectGrade(projectName, student1));
		assertEquals(89, course.getProjectGrade(projectName, student2));
		assertEquals(90, course.getProjectGrade(projectName, student3));
		// test average project grade
		assertEquals(85, course.getAverageProjectsGrade(student1));
		assertEquals(87, course.getAverageProjectsGrade(student2));
		assertEquals(87, course.getAverageProjectsGrade(student3));
	}

}
