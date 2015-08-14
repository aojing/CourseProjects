package edu.gatech.seclass.gradescalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Course {
	private Students students;
	private Grades grades;

	public Course(Students students, Grades grades) {
		this.students = students;
		this.grades = grades;
		setAttendance();
	}

	public void setAttendance() {

		// set Attendance
		HashMap<String, Integer> attendance = grades.getAttendance();
		for (Student s : students.getStudent()) {

			for (String Gtid : attendance.keySet()) {

				if (Gtid.equals(s.getGtid())) {

					s.setAttendance(attendance.get(Gtid));
					// System.out.println(s.getGtid()+" "+s.getAttendance());
				}
			}
		}

	}

	public int getNumStudents() {
		return students.getStudent().size();
	}

	public int getNumAssignments() {
		return grades.getAssignments().size();
	}

	public int getNumProjects() {
		return grades.getProjects().size();
	}

	public HashSet<Student> getStudents() {
		return students.getStudent();
	}

	public Student getStudentByName(String stuName) {
		for (Student stu : students.getStudent()) {
			if (stu.getName().equals(stuName)) {// System.out.println(stuName);
				return stu;
			}
		}
		return null;
	}

	public Student getStudentByID(String Gtid) {
		for (Student stu : students.getStudent()) {
			if (stu.getGtid().equals(Gtid))
				return stu;
		}
		return null;
	}

	public void updateGrades(Grades newgrades) {
		grades = newgrades;
	}

	public void addAssignment(String assignmentName) {
		HashMap<String, Integer> assignScore = new HashMap<>();
		grades.addAssignment(assignmentName, assignScore);
	}

	public void addGradesForAssignment(String assignmentName,
			HashMap<Student, Integer> newgrades) {
		HashMap<String, Integer> newScore = new HashMap<>();
		for (Student stu : newgrades.keySet()) {
			newScore.put(stu.getGtid(), newgrades.get(stu));
		}
		grades.addAssignment(assignmentName, newScore);
	}

	public int getAverageAssignmentsGrade(Student student) {
		String Gtid = student.getGtid();
		HashMap<String, HashMap<String, Integer>> assignments = grades
				.getAssignments();
		double total = 0;
		for (String assign : assignments.keySet()) {
				if (assignments.get(assign).containsKey(Gtid)) {
					total += assignments.get(assign).get(Gtid);
					// System.out.println(student.getGtid() + " " + assign + " "
					// + assignments.get(assign).get(id));
				}
		}
		return (int) Math.rint(total / assignments.size());
	}

	public void addIndividualContributions(String projectName,
			HashMap<Student, Integer> contribution) {

		HashMap<String, Integer> contrib = new HashMap<>();
		for (Student s : contribution.keySet()) {
			String Gtid = s.getGtid();
			int score = contribution.get(s);
			contrib.put(Gtid, score);
		}
		grades.addProject(projectName, contrib);

	}

	public int getAverageProjectsGrade(Student student) {

		double total = 0;
		HashMap<String, HashMap<String, Integer>> Contribs = grades
				.getProjects();
		for (String proj : Contribs.keySet()) {

			for (Student s : students.getStudent()) {
				if (s.getGtid().equals(student.getGtid())) {
					String id = s.getGtid();
					int indiviContrib = 0;
					HashMap<String, Integer> contribs = Contribs.get(proj);
					if (contribs.containsKey(id)) {
						indiviContrib = contribs.get(id);
					}
					int projGrade = 0;
					HashMap<String, HashMap<String, Integer>> teamgrades = grades
							.getTeamGrades();
					if (teamgrades.containsKey(proj)) {
						String Team = s.getTeam();
						HashMap<String, Integer> team = teamgrades.get(proj);
						if (team.containsKey(Team)) {
							projGrade = team.get(Team);
						}
					}
					// System.out.println(proj + " " + indiviContrib + " "
					// + projGrade);
					total += indiviContrib * projGrade / 100.0;
				}
			}
		}

		return (int) Math.rint(total / grades.getProjects().size());

	}

	// for taskcards 3.4, 3.5, 3.6 not implemented yet
	public void addStudent(Student newS) {
		// TODO Auto-generated method stub

	}

	public void addProject(String string) {
		// TODO Auto-generated method stub

	}

	public void updateStudents(Students students) {
		// TODO Auto-generated method stub

	}

	public void addGradesForProjects(String projectName,
			HashMap<Student, Integer> grades2) {
		// TODO Auto-generated method stub

	}

	public int getProjectGrade(String projectName, Student student) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTeam(Student student) {
		// TODO Auto-generated method stub
		for (Student s : students.getStudent()) {
			if (s.getName() == student.getName())
				return s.getTeam();
		}

		return null;
	}

	public int getAttendance(Student student) {
		// TODO Auto-generated method stub
		for (Student s : students.getStudent()) {
			if (s.getName() == student.getName())
				return s.getAttendance();
		}
		return 0;
	}

	public int getOverallGrade(Student student) {
		// TODO Auto-generated method stub
		double assignmentGrade = getAverageAssignmentsGrade(student) * 1.0;
		double projectGrade = getAverageProjectsGrade(student) * 1.0;
		double attend = getAttendance(student) * 1.0;
		double overall = 0;

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		String formula = grades.getFormula();
		String f = formula.replace("AT", Double.toString(attend));
		System.out.println(f);
		f = f.replace("AA", Double.toString(assignmentGrade));
		System.out.println(f);
		f = f.replace("AP", Double.toString(projectGrade));
		System.out.println(f);

		try {
			overall = (double) engine.eval(f);
			System.out.println(overall);
		} catch (ScriptException e) {
			throw new GradeFormulaException("Invalid Formula");
		}
		return (int) Math.rint(overall);

	}

	public String getFormula() {
		// TODO Auto-generated method stub
		return grades.getFormula();
	}

	public void setFormula(String text) {
		// TODO Auto-generated method stub
		grades.setFormula(text);
	}

	public String getEmail(Student student) {
		// TODO Auto-generated method stub
		String email = null;
		for (Student s : students.getStudent()) {
			if (s.getName().equals(student.getName())) {
				email = student.getEmail();
			}
		}
		return email;
	}

}
