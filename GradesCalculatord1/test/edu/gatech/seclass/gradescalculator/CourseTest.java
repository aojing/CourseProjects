package edu.gatech.seclass.gradescalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourseTest {

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
    public void testGetNumStudents1() {
        int numStudents = course.getNumStudents();
        assertEquals(16, numStudents);
    }

    @Test
    public void testGetNumAssignments1() {
        int numAssignments = course.getNumAssignments();
        assertEquals(3, numAssignments);
    }

    @Test
    public void testGetNumProjects1() {
        int numProjects;
        numProjects = course.getNumProjects();
        assertEquals(3, numProjects);
    }

    @Test
    public void testGetStudents1() {
        HashSet<Student> studentsRoster = null;
        studentsRoster = course.getStudents();
        assertEquals(16, studentsRoster.size());
    }

    @Test
    public void testGetStudents2() {
        HashSet<Student> studentsRoster = null;
        studentsRoster = course.getStudents();
        Student student = null;
        for (Student s : studentsRoster) {
            if (s.getName().compareTo("Tendai Charpentier") == 0) {
                student = s;
                break;
            }
        }
        assertNotNull(student);
    }

    @Test
    public void testGetStudentsByName1() {
        Student student = null;
        student = course.getStudentByName("Rastus Kight");
        assertEquals("901234512", student.getGtid());
    }

    @Test
    public void testGetStudentsByName2() {
        Student student = null;
        student = course.getStudentByName("Coriander Alfsson");
        assertEquals(98, student.getAttendance());
    }

    @Test
    public void testGetStudentsByID1() {
        Student student = course.getStudentByID("901234504");
        assertEquals("Shevon Wise", student.getName());
    }

    @Test
    public void testGetTeam1() {
        Student student = course.getStudentByName("Genista Parrish");
        assertEquals("Team 3", student.getTeam());
    }
}
