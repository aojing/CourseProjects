package edu.gatech.seclass.gradescalculator;

import java.util.HashMap;
import java.util.HashSet;

public class Course {
	private HashSet<Student> newStuRoster;
	private Grades grades;
   
	public Course(Students stus, Grades grades) {
		
           this.newStuRoster = stus.getStudent();
           this.grades = grades;
           HashMap<String, Integer> Attendance = grades.getAttendance();
           for(Student s: newStuRoster){

        	   for(String str: Attendance.keySet()) {
        		  
        		   if(str.equals(s.getGtid())) {
        			   
        			   s.setAttendance(Attendance.get(str));
        			   //System.out.println(s.getGtid()+" "+s.getAttendance());
        		   }
        	   }
           }
	}
	
	public int getNumStudents(){
		return this.newStuRoster.size();
	}
	
	public int getNumAssignments(){
		return grades.getAssingmentList().size();
	}

	public int getNumProjects(){
		return grades.getProjectList().size();
	}
	
	public HashSet<Student> getStudents(){
		
		return this.newStuRoster;
	}
	
	public Student getStudentByName(String stuName) {
		for (Student s : newStuRoster) {
			if (s.getName().equals(stuName)) {
				System.out.println(s.getName()+ " is matched by name");
				
				return s;
			}
			
		}
		System.out.println("Match not found");
		return null;
	}
	
	public Student getStudentByID(String Gtid) {
		for (Student s : newStuRoster) {
			if (s.getGtid().equals(Gtid)) {
				System.out.println(s.getGtid()+" is matched by Gtid");
				
				return s;
			}
          
		}
		System.out.println("Match not found");
			    return null;
			
		}
	}
	

