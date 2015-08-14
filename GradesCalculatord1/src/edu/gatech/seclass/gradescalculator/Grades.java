package edu.gatech.seclass.gradescalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Grades {
	HashMap<String, Integer> attendance;
    ArrayList<String> assignmentList;
    ArrayList<String> projectList;
    HashMap<String, HashMap<String, Integer>> Assignments;
    HashMap<String, HashMap<String, Integer>> Projects;
    HashMap<String, HashMap<String, Integer>> TeamProjects;
    
	public Grades() {
		
	}
	
	public Grades(String GradesDB){
		try{
			Students stu = new Students();
			attendance = new HashMap<String, Integer>();
			Assignments = new HashMap<String, HashMap<String, Integer>>();
			Projects = new HashMap<String, HashMap<String, Integer>>();
			
			
			FileInputStream file = new FileInputStream(new File(GradesDB));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			//get first sheet from the workbook
			XSSFSheet Attendance = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = Attendance.iterator();
			rowIterator.next();
			
			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cell = cellIterator.next();
				if (cellIterator.hasNext()) {
					String nameID = Integer.toString((int) (cell
							.getNumericCellValue()));

					int Attend = (int) cellIterator.next()
							.getNumericCellValue();
					attendance.put(nameID, Attend);
					// System.out.println(attendance.get(nameID));

				}

			}
	
		    //get second sheet
			XSSFSheet IndividualGrades = workbook.getSheetAt(1);
			Iterator<Row> rowIterator2 = IndividualGrades.iterator();

			Row row2 = rowIterator2.next();
			Iterator<Cell> cellIterator2 = row2.cellIterator();
			cellIterator2.next();
			assignmentList = new ArrayList<String>();
			while (cellIterator2.hasNext()) {

				String assignList = cellIterator2.next().getStringCellValue();
				// System.out.println(assignList);
				assignmentList.add(assignList);

			}
			HashMap<String, Integer> assignScore = new HashMap();
			Iterator<Row> rowIter = IndividualGrades.iterator();
			rowIter.next();
			while (rowIter.hasNext()) {
				Row newrow = rowIter.next();
				Iterator<Cell> cellIter = newrow.cellIterator();
				if (cellIter.hasNext()) {
					String nameID = Integer.toString((int) (cellIter.next()
							.getNumericCellValue()));

					while (cellIter.hasNext()) {
						int i = 0;
						int score = (int) cellIter.next().getNumericCellValue();

						assignScore.put(assignmentList.get(i), score);
						// System.out.println(nameID+" "+score);

					}
					Assignments.put(nameID, assignScore);
				}
			}

			// get second sheet
			XSSFSheet IndividualContribs = workbook.getSheetAt(2);
			Iterator<Row> rowIterator3 = IndividualContribs.iterator();

			Row row3 = rowIterator3.next();
			Iterator<Cell> cellIterator3 = row3.cellIterator();
			cellIterator3.next();
			projectList = new ArrayList<String>();
			while (cellIterator3.hasNext()) {

				String projList = cellIterator3.next().getStringCellValue();
				// System.out.println(projList);
				projectList.add(projList);

			}
			HashMap<String, Integer> projContribs = new HashMap();
			Iterator<Row> rowIter3 = IndividualContribs.iterator();
			rowIter3.next();

			while (rowIter3.hasNext()) {

				Row newrow = rowIter3.next();

				Iterator<Cell> cellIter = newrow.cellIterator();

				if (cellIter.hasNext()) {
					String nameID = Integer.toString((int) (cellIter.next()
							.getNumericCellValue()));
					int i = 0;
					while (cellIter.hasNext()) {

						int score = (int) cellIter.next().getNumericCellValue();

						projContribs.put(projectList.get(i), score);
						// System.out.println(nameID+" "+projectList.get(i)+" "+score);
						i++;
					}
					Projects.put(nameID, projContribs);
				}
			}

			// get fouth sheet
			XSSFSheet TeamGrades = workbook.getSheetAt(3);
			Iterator<Row> rowIterator4 = TeamGrades.iterator();
			Row row4 = rowIterator4.next();
			Iterator<Cell> cellIterator4 = row4.cellIterator();
			cellIterator4.next();

			HashMap<String, Integer> projScore = new HashMap();
			Iterator<Row> rowIter4 = TeamGrades.iterator();
			rowIter4.next();
			while (rowIter4.hasNext()) {

				Row newrow = rowIter4.next();

				Iterator<Cell> cellIter = newrow.cellIterator();

				if (cellIter.hasNext()) {
					String nameID = cellIter.next().getStringCellValue();
					int i = 0;
					while (cellIter.hasNext()) {

						int score = (int) cellIter.next().getNumericCellValue();

						projScore.put(projectList.get(i), score);
						// System.out.println(nameID+" "+projectList.get(i)+" "+score);
						i++;
					}
					Projects.put(nameID, projScore);
				}
			}

	    file.close();
	    FileOutputStream out = 
	            new FileOutputStream(new File(GradesDB));
	        workbook.write(out);
	        out.close();
	         
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		}

	public ArrayList<String> getAssingmentList() {

		return assignmentList;
	}

	public HashMap<String, Integer> getAttendance() {
		return attendance;
	}

	public ArrayList<String> getAssignmentList() {
		return assignmentList;
	}

	public ArrayList<String> getProjectList() {
		return projectList;
	}

	public HashMap<String, HashMap<String, Integer>> getAssignments() {
		return Assignments;
	}

	public HashMap<String, HashMap<String, Integer>> getProjects() {
		return Projects;
	}

	public HashMap<String, HashMap<String, Integer>> getTeamProjects() {
		return TeamProjects;
	}

}
