package edu.gatech.seclass.gradescalculator;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class mytest {
   
	Students students = null;
    Grades grades = null;
    Course course = null;
    static final String STUDENTS_DB = "DB/GradesDatabase6300-students.xlsx";
    static final String GRADES_DB = "DB/GradesDatabase6300-grades.xlsx";
    
	@Before
	public void setUp() throws Exception {
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

	
	 @Test
	    public void testGetStudents1() {
	        HashSet<Student> studentsRoster = null;
	        studentsRoster = course.getStudents();
	        assertEquals(16, studentsRoster.size());
	    }
	 
	@Test
	public void testSetUp() {
		fail("Not yet implemented");
	}

	@Test
	public void testTearDown() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestGetNumStudents1() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestGetNumAssignments1() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestGetNumProjects1() {
		fail("Not yet implemented");
	}



	@Test
	public void testTestGetStudents2() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestGetStudentsByName1() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestGetStudentsByName2() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestGetStudentsByID1() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestGetTeam1() {
		fail("Not yet implemented");
	}

}
