package fetchsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SetConnection {
	
	private Connection connection;
	private Statement statement;
	public boolean searchFound;
	
	public void openConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cvsu_checklist", "root", "october261974");
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getStudenProgramInfo(String studentNumber, ArrayList<String> name, ArrayList<String> studNum, ArrayList<String> program) {
		try {
			ResultSet result = statement.executeQuery("call getStudentProgram('"+studentNumber+"')");
			
			while(result.next()) {
				name.add(result.getString("student_name"));
				studNum.add(result.getString("student_number"));
				program.add(result.getString("program"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Student Program Info");
			e.printStackTrace();
		}
	}
	
	public void getCourseInfo(String studentNumber, ArrayList<Integer> info) {
		try {
			ResultSet result = statement.executeQuery("call getStudentCourseInfo('"+studentNumber+"')");
			
			while(result.next()) {
				info.add(result.getInt("course_taken"));
				info.add(result.getInt("course_left"));
				info.add(result.getInt("course_failed"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Course Info");
			e.printStackTrace();
		}
	}
	

	public void getCourseStash(String studentNumber, String search, ArrayList<String> courseCode, ArrayList<String> profName, ArrayList<String> grade, ArrayList<Integer> year, ArrayList<Integer> sem, String addon, String sort) {
		try {
			
			ResultSet result = statement.executeQuery("call searchingStash('"+studentNumber+"', '"+search+"','"+addon+" "+sort+"')");
			year.clear();
			sem.clear();
			courseCode.clear();
			profName.clear();
			grade.clear();
			
			while(result.next()) {
				year.add(result.getInt("year"));
				sem.add(result.getInt("semester"));
				courseCode.add(result.getString("course_code"));
				profName.add(result.getString("professor_name"));
				grade.add(result.getString("grade"));
			}
			searchFound = courseCode.size()!=0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Course Stash");
			searchFound = false;
		}
	}
	
	public void getStudentInfoAddress(String studentNum, ArrayList<String> name, ArrayList<String> studentNumber, ArrayList<String> program, ArrayList<String> address, ArrayList<String> contact) {
	    try {
	        ResultSet result = statement.executeQuery("SELECT * FROM student_info_address WHERE student_number = '" + studentNum + "';");
	        while (result.next()) {
	            name.add(result.getString("student_name"));
	            studentNumber.add(result.getString("student_number"));
	            program.add(result.getString("program"));
	            address.add(result.getString("full_address"));
	            contact.add(result.getString("contact_number"));
	        }
	    } catch (SQLException e) {
	        // TODO Auto-generated catch block
	    	System.out.println("Student Info Address");
	        e.printStackTrace();
	    }
	}
	
	public void getGradeAndProfessor(String studentNumber, ArrayList<String> courseCode, ArrayList<String> subjectName, ArrayList<String> profName, ArrayList<String> grade, ArrayList<String> equivalent, ArrayList<Integer> year, ArrayList<Integer> sem, ArrayList<Integer> cuLab, ArrayList<Integer> cuLec, ArrayList<Integer> chLab, ArrayList<Integer> chLec) {
		try {
			ResultSet result = statement.executeQuery("call getGradeAndProfessor('"+studentNumber+"')");
			while (result.next()) {
				courseCode.add(result.getString("course_code"));
				subjectName.add(result.getString("course_title"));
				profName.add(result.getString("professor_name"));
				grade.add(result.getString("grade"));
				equivalent.add(result.getString("equivalent"));
				
				year.add(result.getInt("year"));
				sem.add(result.getInt("semester"));
				cuLab.add(result.getInt("credit_unit_lab"));
				cuLec.add(result.getInt("credit_unit_lec"));
				chLab.add(result.getInt("contact_hr_lab"));
				chLec.add(result.getInt("contact_hr_lec"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Grade and Professor");
			e.printStackTrace();
		}
	}
	
	
	public void closeConnection() {
		try {
			connection.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
