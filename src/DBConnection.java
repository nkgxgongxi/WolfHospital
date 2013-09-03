import java.sql.*;

public class DBConnection {
	static Connection conn;
	static Statement stmt;
	
	public static void Create(String usr, String pwd){
		try{
			Class.forName(Configuration.driverName);
			conn = DriverManager.getConnection(Configuration.jdbcURL, usr, pwd);
			System.out.println("Successfully connected to Database.");
		}catch(Throwable oops){
			oops.printStackTrace();
		}
	}
	
	public static ResultSet Query(String sql){
		ResultSet rs = null;
		return rs;
	}
	
	public static void Update(String sql){
		
	}
	
	public static void Close(){
		try{
			if(stmt != null)
				stmt.close();
			}catch(SQLException se){
				se.printStackTrace();
		}
		try{
			if(conn != null)
				conn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
		System.out.println("Goodbye!");
	}
}
