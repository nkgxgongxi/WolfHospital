/**
 * First Attempt of using JDBC to manipulate database
 */

import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * @author xgong
 *
 */
public class FirstExample {

	/**
	 * @param args
	 */
	
	//JDBC driver name and database URL
//	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	//Database credentials
	static String USER = null;
	static String PSWD = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Please input the username:");
			Scanner s = new Scanner(new InputStreamReader(System.in));
//			USER = args[0];
			USER = s.nextLine();
			System.out.println("Please input the password:");
//			PSWD = args[1];
			PSWD = s.nextLine();
//			s.close();
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PSWD);
//			conn.setAutoCommit(false);
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			System.out.println("Please provide the query you want to conduct:");
//			Scanner s2 = new Scanner(new InputStreamReader(System.in));
//			sql = "SELECT * FROM NBAPLAYER";
			sql = s.nextLine();
			s.close();
			ResultSet rs = stmt.executeQuery(sql);
//			conn.commit();
			String name, team, position;
			int age;
			name = null;
			team = null;
			position = null;
			age = 0;
			while(rs.next()){
				name = rs.getString("PLAYERNAME");
				team = rs.getString("TEAM");
				age = rs.getInt("AGE");
				position = rs.getString("POSITION");
			}
			System.out.printf("%-20s %-20s %-3s %-10s \n", "Name", "TEAM", "AGE", "POSITION");
			System.out.printf("%-20s %-20s %-3d %-10s \n", name, team, age, position);
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt != null)
					stmt.close();
				}catch(SQLException se2){
					se2.printStackTrace();
			}
			try{
				if(conn != null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	System.out.println("Goodbye!");
	}//end main
}//end FirstExample
