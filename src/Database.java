import java.sql.*;
import java.util.*;

public class Database {
	private static Database instance = null;
	Connection conn;
	Statement stmt;
	protected Database() {
		conn = null;
		stmt = null;
	}
	
	public static Database getInstance() {
		if(instance == null) 
			instance = new Database();
		return instance;
	}
	
	public void init(String usr, String pwd){
		try{
			Class.forName(Configuration.driverName);
			conn = DriverManager.getConnection(Configuration.jdbcURL, usr, pwd);
			Logger.log("Successfully connected to Database.");
		}catch(Throwable oops){
			oops.printStackTrace();
		}
	}
	
	public void close(){
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
		Logger.log("Successfully disconnected from Database.");
		Logger.log("Goodbye!");
	}
	public void create(String tableName, String attributes) throws SQLException {
		try {
			stmt = conn.createStatement();
			String pre = "CREATE TABLE";
			String content = pre + " " + tableName + "( " + attributes + ")";
			Logger.log("The operation you are willing to do is:");
			Logger.log(content);
			stmt.executeUpdate(content);	
		}catch(Throwable oops) {
            oops.printStackTrace();
		}
		finally {
			if(stmt != null)
				stmt.close();
		}
	}
	public void delete(String tableName, String condition) throws SQLException {	
		try {
			stmt = conn.createStatement();
			String pre = "DELETE FROM";
			String content = pre + " " + tableName + " WHERE " + condition;
			Logger.log("The operation you are willing to do is:");
			Logger.log(content);
			stmt.executeUpdate(content);	
		}catch(Throwable oops) {
            oops.printStackTrace();
		}finally {
			if(stmt != null)
				stmt.close();
		}
	}
	
	public void update(String tableName, String modification, String condition) throws SQLException {
		try {
			String content = "UPDATE " + tableName + " SET " + modification + " WHERE " + condition;
			Logger.log("The operation you are willing to do is:");
			Logger.log(content);
			stmt = conn.createStatement();
			stmt.executeUpdate(content);
			
		}catch(Throwable oops) {
            oops.printStackTrace();
		}finally {
			if(stmt != null)
				stmt.close();
		}
	}
	
	public void insert(String tableName, String values) throws SQLException {
		try {
			stmt = conn.createStatement();
			String prefix = "INSERT INTO";
			String mid = "VALUES(";
			String suffix = ")";
			String content = prefix + " " + tableName + " " + mid + values + suffix;
			Logger.log("The operation you are willing to do is:");
			Logger.log(content);
			stmt.executeUpdate(content);
			
		}catch(Throwable oops) {
            oops.printStackTrace();
		}finally {
			if(stmt != null)
				stmt.close();
		}
	}
	
	public ResultSet select(String tableName, String col, String condition) throws SQLException {
		ResultSet res = null;
		try {
			stmt = conn.createStatement();
			String select = "SELECT";
			String from = " FROM ";
			String where = " WHERE ";
			String content = select + " " + col + " " + from + tableName + where + condition;
			Logger.log("The operation you are willing to do is:");
			Logger.log(content);
			res = stmt.executeQuery(content);
		}catch(Throwable oops) {
            oops.printStackTrace();
		}
		finally {
			if(stmt != null)
				stmt.close();
		}
		return res; 
	}
	
	public ResultSet select(String tableName, String col) throws SQLException {
		ResultSet res = null;
		try {
			stmt = conn.createStatement();
			String select = "SELECT";
			String from = " FROM ";
			String content = select + " " + col + " " + from + tableName;
			Logger.log("The operation you are willing to do is:");
			Logger.log(content);
			res = stmt.executeQuery(content);
		}catch(Throwable oops) {
            oops.printStackTrace();
		}
		finally {
			if(stmt != null)
				stmt.close();
		}
		return res; 
	}
	
	public static void main(String[] args) {
		Database myDB = Database.getInstance();
		Logger.init();
		Tools.printDate();
		try {
			myDB.init("xgong", "gxgongxi");
			myDB.update("STAFF", "PHONE = '919-802-5887'", "NAME = 'John Terry'");
			myDB.close();
		}catch(Throwable oops) {
			oops.printStackTrace();
		}
		Tools.printDate();
	}
}
