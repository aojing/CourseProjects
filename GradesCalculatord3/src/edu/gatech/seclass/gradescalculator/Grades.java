package edu.gatech.seclass.gradescalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Grades 
{

	private HashMap<String, Integer> Attendance;
	private ArrayList<String> assignmentList;
	private ArrayList<String> projectList;
	private HashMap<String, HashMap<String, Integer>> Assignments;
	private HashMap<String, HashMap<String, Integer>> Projects;
	private HashMap<String, HashMap<String, Integer>> TeamGrades;
    private String path;
    public XSSFWorkbook workbook;
    private String Formula = "AT * 0.2 + AA * 0.4 + AP * 0.4";

	
	public Grades(String gradesDB) {
		path = gradesDB;

		try {
			FileInputStream file = new FileInputStream(new File(path));
			try {
				XSSFWorkbook workbook = new XSSFWorkbook(file);

				// get first sheet, add attendance
				Attendance = new HashMap<>();

				XSSFSheet sheet1 = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet1.iterator();
				rowIterator.next();

				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					Cell cell = cellIterator.next();
					if (cell != null) {
						String Gtid = null;
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_NUMERIC:
							Gtid = Integer.toString((int) cell
									.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							Gtid = cell.getStringCellValue();
							break;
						}

						// System.out.println(Gtid);
						int attend = (int) cellIterator.next()
								.getNumericCellValue();
						Attendance.put(Gtid, attend);
					}

				}

				// get second sheet, add individual grades for assignments
				assignmentList = new ArrayList<>();
				Assignments = new HashMap<>();

				XSSFSheet sheet2 = workbook.getSheetAt(1);
				Iterator<Row> rowIterator2 = sheet2.iterator();
				Row row2 = rowIterator2.next();

				Iterator<Cell> cellIterator2 = row2.cellIterator();
				cellIterator2.next();

				Cell cell;
				while (cellIterator2.hasNext()
						&& (cell = cellIterator2.next()) != null)
					assignmentList.add(cell.getStringCellValue());

				for (int i = 0; i < assignmentList.size(); i++) {
					HashMap<String, Integer> assignScore = new HashMap<>();
					Iterator<Row> rowIter = sheet2.iterator();
					rowIter.next();
					while (rowIter.hasNext()) {
						Row newrow = rowIter.next();
						Iterator<Cell> cellIter = newrow.cellIterator();
						if (cellIter.hasNext()) {
							Cell c = cellIter.next();
							String Gtid = null;
							switch (c.getCellType()) {

							case Cell.CELL_TYPE_NUMERIC:
								Gtid = Integer.toString((int) c
										.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								Gtid = c.getStringCellValue();
								break;
							}

							int colNum = newrow.getLastCellNum();
							for (int j = i; (j != 0) && j < colNum - 1; j--) {
								cellIter.next();
							}

							int score = (int) cellIter.next()
									.getNumericCellValue();
							// int score = 0;

							assignScore.put(Gtid, score);

						}

						Assignments.put(assignmentList.get(i), assignScore);

					}
				}

				// get third sheet, add individual contribs for projects
				projectList = new ArrayList<>();
				Projects = new HashMap<>();

				XSSFSheet sheet3 = workbook.getSheetAt(2);
				Iterator<Row> rowIterator3 = sheet3.iterator();
				Row row3 = rowIterator3.next();

				Iterator<Cell> cellIterator3 = row3.cellIterator();
				cellIterator3.next();

				Cell cell3;
				while (cellIterator3.hasNext()
						&& (cell3 = cellIterator3.next()) != null)
					projectList.add(cell3.getStringCellValue());

				for (int i = 0; i < projectList.size(); i++) {
					HashMap<String, Integer> projScore = new HashMap<>();
					Iterator<Row> rowIter = sheet3.iterator();
					rowIter.next();
					while (rowIter.hasNext()) {
						Row row = rowIter.next();
						Iterator<Cell> cellIter = row.cellIterator();
						cell = cellIter.next();
						String Gtid = null;
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_NUMERIC:
							Gtid = Integer.toString((int) cell
									.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							Gtid = cell.getStringCellValue();
							break;
						}
						int colNum = row.getLastCellNum();
						for (int j = i; j != 0&&  j<colNum-1;j--) {
							cellIter.next();
						}
						int score = (int) cellIter.next().getNumericCellValue();

						projScore.put(Gtid, score);
					}

					Projects.put(projectList.get(i), projScore);

				}

				// get forth sheet, add team grades for projects
				TeamGrades = new HashMap<>();

				XSSFSheet sheet4 = workbook.getSheetAt(3);
				Iterator<Row> rowIterator4 = sheet4.iterator();
				rowIterator4.next();

				for (int i = 0; i < projectList.size(); i++) {
					HashMap<String, Integer> teamScore = new HashMap<>();
					Iterator<Row> rowIter = sheet4.iterator();
					rowIter.next();
					while (rowIter.hasNext()) {
						Row row = rowIter.next();
						Iterator<Cell> cellIter = row.cellIterator();
						String name = cellIter.next().getStringCellValue();

						for (int j = i; j != 0; j--) {
							cellIter.next();
						}
						int score = (int) cellIter.next().getNumericCellValue();

						teamScore.put(name, score);

					}

					TeamGrades.put(projectList.get(i), teamScore);
				}

				file.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public HashMap<String, Integer> getAttendance() {
		return this.Attendance;
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

	public HashMap<String, HashMap<String, Integer>> getTeamGrades() {
		return TeamGrades;
	}
	


	public void addAssignment(String assignmentName,
			HashMap<String, Integer> assignGrades) {
	

		// write assignments to excel
		try {
			FileInputStream file = new FileInputStream(new File(path));
			try {
				workbook = new XSSFWorkbook(file);
				XSSFSheet sheet2 = workbook.getSheetAt(1);
				Cell cell = null;

				// add new assignment
				int currAssignNum = assignmentList.size();
				if(!assignmentList.contains(assignmentName)){
				//create the cell after current last assignment
				cell = sheet2.getRow(0).createCell(currAssignNum+1);
				cell.setCellValue(assignmentName);
				assignmentList.add(assignmentName);
				}
				
				for(int i=0; i<assignmentList.size();i++){
					if(assignmentList.get(i).equals(assignmentName)){
						currAssignNum = i+1;
						//System.out.println(currAssignNum+" "+assignmentName);
					}
				}
				

				for (String Gtid : assignGrades.keySet()) {
					int rowNum = 0;
					for (int i = 1; i < sheet2.getLastRowNum() + 1; i++) {
						String id = Integer.toString((int) sheet2.getRow(i)
								.getCell(0).getNumericCellValue());

						if (id.equals(Gtid)) {
							rowNum = i;
							Cell c = sheet2.getRow(i).createCell(
									currAssignNum);
							c.setCellValue(assignGrades.get(Gtid));
							System.out.println("grades of " + assignmentName
									+ " for " + Gtid + " is: "
									+ assignGrades.get(Gtid));
						}
					}
				}

				FileOutputStream out;
				out = new FileOutputStream(new File(path));
				workbook.write(out);
				out.close();
				System.out.println("new file created at: " + path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void addProject(String projectName, HashMap<String, Integer> contribs) {
		
		//write contribs to excel
	   try {
			FileInputStream file = new FileInputStream(new File(path));
				try {
						workbook = new XSSFWorkbook(file);
						XSSFSheet sheet3 = workbook.getSheetAt(2);
						Cell cell = null;

				        //add new assignment name
						int currProjNum = projectList.size();
						if(!projectList.contains(projectName)){
						
						cell = sheet3.getRow(0).createCell(currProjNum+1);
						cell.setCellValue(projectName);
						projectList.add(projectName);
						}
						
						for(int i=0; i<projectList.size();i++){
							if(projectList.get(i).equals(projectName)){
								currProjNum = i+1;
								//System.out.println(currProjNum+" "+projectName);
							}
						}
						
		                //set individual contribs for each project
			            
						for (String Gtid : contribs.keySet()) {
							   int rowNum = 0;
							   for(int i=1; i<sheet3.getLastRowNum()+1;i++){
								   String id = Integer.toString((int)sheet3.getRow(i).getCell(0).getNumericCellValue());
								   if(id.equals(Gtid)){
									   rowNum = i;
									   Cell c = sheet3.getRow(i).createCell(currProjNum);
									   c.setCellValue(contribs.get(Gtid));
									   System.out.println("grades of "+projectName+" for "+Gtid+" is: "+contribs.get(Gtid));
								   }
							   }
						}
								

				FileOutputStream out;
				out = new FileOutputStream(new File(path));
				workbook.write(out);
				out.close();
				System.out.println("new file created at: " + path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void setFormula(String formula) {
		// TODO Auto-generated method stub
		this.Formula = formula;
	}

	public String getFormula() {
		return Formula;
	}


	
}
