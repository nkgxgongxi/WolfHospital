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
	public void create(String tableName, String attributes) {
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
	}
	public void delete(String tableName, String condition) {	
		try {
			stmt = conn.createStatement();
			String pre = "DELETE FROM";
			String content = pre + " " + tableName + " WHERE " + condition;
			Logger.log("The operation you are willing to do is:");
			Logger.log(content);
			stmt.executeUpdate(content);	
		}catch(Throwable oops) {
            oops.printStackTrace();
		}
	}
	
	public void update(String tableName, String modification, String condition) {
		try {
			String content = "UPDATE " + tableName + " SET " + modification + " WHERE " + condition;
			Logger.log("The operation you are willing to do is:");
			Logger.log(content);
			stmt = conn.createStatement();
			stmt.executeUpdate(content);
			
		}catch(Throwable oops) {
            oops.printStackTrace();
		}
	}
	
	public void insert(String tableName, String values) {
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
		}
	}
	
	public static void main(String[] args) {
		Database myDB = Database.getInstance();
		Logger.init();
		Tools.printDate();
		myDB.init("xgong", "gxgongxi");
		myDB.update("STAFF", "PHONE = '919-802-5887'", "NAME = 'John Terry'");
		myDB.close();
		Tools.printDate();
	}
}
