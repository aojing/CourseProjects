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

public class Students {

	HashSet<Student> StudentRoster;
	HashMap<String, ArrayList<String>> TeamMap;
	
	public Students() {
	}
	
	public Students(String StudentDB){
	
	//read database
	try{
			FileInputStream file = new FileInputStream(new File(StudentDB));
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// get first sheet from the workbook
			XSSFSheet StudentsInfo = workbook.getSheetAt(0);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = StudentsInfo.iterator();

			StudentRoster = new HashSet<>();
			rowIterator.next();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Student student = new Student();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				String name = cellIterator.next().getStringCellValue();
				student.setName(name);
				String Gtid = Integer.toString((int) (cellIterator.next()
						.getNumericCellValue()));
				student.setGtid(Gtid);
				student.setEmail(cellIterator.next().getStringCellValue());
				student.setC((int) (cellIterator.next().getNumericCellValue()));
				student.setCpp((int) (cellIterator.next().getNumericCellValue()));
				student.setJava((int) (cellIterator.next()
						.getNumericCellValue()));
				student.setCSJobEx(cellIterator.next().getStringCellValue());

				StudentRoster.add(student);

			}
	    /**
	    for(Student s: StudentRoster){
	    	System.out.println(s.getName()+s.getGtid()+s.getEmail()+s.getC());
	    }
	    **/
		
	    //get second sheet
			XSSFSheet teams = workbook.getSheetAt(1);
			Iterator<Row> rowIterator2 = teams.iterator();
			TeamMap = new HashMap<>();

			rowIterator2.next();

			while (rowIterator2.hasNext()) {
				Row row2 = rowIterator2.next();
				Iterator<Cell> cellIterator2 = row2.cellIterator();
				if (cellIterator2.hasNext()) {
					String team = cellIterator2.next().getStringCellValue();
                    ArrayList<String> teamMember = new ArrayList<String>();
					while (cellIterator2.hasNext()) {
						String studentName = cellIterator2.next()
								.getStringCellValue();
						teamMember.add(studentName);
						for (Student stu : StudentRoster) {
							if (stu.getName().equals(studentName)) {
								stu.setTeam(team);

							}
						}

					}
					TeamMap.put(team, teamMember);
					
				}
				
			}
			/** test teamMap
			for(String t : TeamMap.keySet()){
				ArrayList<String> teamMember = TeamMap.get(t);
			
				for(int i=0;i<teamMember.size();i++){
					System.out.println(t+": "+teamMember.get(i));
				}
			}
	   **/

    file.close();
    FileOutputStream out = 
            new FileOutputStream(new File(StudentDB));
        workbook.write(out);
        out.close();
         
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
	} 
	

public HashSet<Student> getStudent() {
	
	return StudentRoster;
}

public HashMap<String, ArrayList<String>> getTeamMap(){
	return TeamMap;
}
}