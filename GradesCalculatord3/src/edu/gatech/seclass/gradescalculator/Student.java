package edu.gatech.seclass.gradescalculator;

import java.util.HashMap;

public class Student {
    
	private String name;
	private String Gtid;
	private String email;
	private int attendance;
	private String team;
	
	private int C;
	private int Cpp;
	private int Java;
	private String CSJobEx;
	
	private Course course;

	/**
	HashMap<String, Integer> assignments;
	HashMap<String, Integer> projects;
	HashMap<String, Integer> courses;
	Grades grades;**/
	
	public Student() {
		
	}


	public Student(String name, String Gtid, Course course) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.Gtid = Gtid;
		this.course = course;
		course.setAttendance();

	}



	public Student(String name, String Gtid) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.Gtid = Gtid;
		
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGtid() {
		return Gtid;
	}

	public void setGtid(String Gtid) {
		this.Gtid = Gtid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAttendance() {
		return attendance;
	}

	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	public int getC() {
		return C;
	}

	public void setC(int c) {
		C = c;
	}

	public int getCpp() {
		return Cpp;
	}

	public void setCpp(int cpp) {
		Cpp = cpp;
	}

	public int getJava() {
		return Java;
	}

	public void setJava(int java) {
		Java = java;
	}

	public String getCSJobEx() {
		return CSJobEx;
	}

	public void setCSJobEx(String cSJobEx) {
		CSJobEx = cSJobEx;
	}


}
