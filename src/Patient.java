import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Patient implements Operable {
	public void insert(Database myDB, String table) throws SQLException {
		try {
			BufferedReader br1 = new BufferedReader (new InputStreamReader (System.in));
			System.out.println("INSERT INTO Patient.");
			System.out.println("Please Input the information you want to insert into patient");
			String Insert_val;
			Insert_val = br1.readLine();
			myDB.insert("patient", Insert_val);
		}catch (IOException e) {
			System.out.println("Error reading from user");
		}		
	}
	
	public void update(Database myDB, String table) throws SQLException
	{
		try
		{
			BufferedReader br1 = new BufferedReader (new InputStreamReader (System.in));
			System.out.println("UPDATE Patient");
			String Update_col;
			String Update_cond;
			System.out.println("Please Input the attribute you want to modify and the new value of it.");
			Update_col = br1.readLine();
			System.out.println("Please Input the WHERE condition.");
			Update_cond = br1.readLine();					
			UPDATEDATA(connection, "patient", Update_col, Update_cond);
		
		}
		catch (IOException e)
		{
			System.out.println("Error reading from user");
		}
	}	

	public void DELETEPATIENT(Database myDB, String table) throws SQLException
	{
		try
		{
			BufferedReader br1 = new BufferedReader (new InputStreamReader (System.in));
			System.out.println("DELETE FROM Patient.");
			System.out.println("Please Input the condition to allocate the record you want to delete.");
			String Delete_cond;
			Delete_cond = br1.readLine();
			DELETEDATA(connection, "patient", Delete_cond);
		}
		catch (IOException e)
		{
			System.out.println("Error reading from user");
		}
	}
	
	public void genReport(Connection connection)
	{
	try
	{
		BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Report the medical history for a given patient and for a certain time period (month/year):");
		System.out.println("Please enter the patient's name:");
		String patientname = buf.readLine();
	//patientname = "'" + patientname + "'";
	//need to test if this patient exists or not here
		System.out.println("Please enter the starting date in the format of 'dd-MMM-yyyy':");
		String stime = buf.readLine();
	//test format function here
		System.out.println("Please enter the ending date in the format of 'dd-MMM-yyyy':");
		String etime = buf.readLine();
		String sql = "SELECT p.Name AS pname,m.startdate AS sdate, m.doctorid AS did, m.Prescription AS pre, m.Diagnosis AS dia FROM MedicalRecord m,Patient p WHERE m.PatientID = p.ID AND p.Name = '" + patientname + "' AND m.StartDate >= '" + stime + "' AND m.StartDate <= '" + etime + "'";
		System.out.println(sql);
	            
	            Statement statement = connection. createStatement();
		ResultSet result = statement.executeQuery(sql);
	            System.out.println("PatientName     Startdate           DocID   Prescription    Diagnosis");
	            System.out.println("-----------     ---------           -----   ------------    ---------");
	            while(result.next())
	            {

	                String pname = result.getString("pname");
	                String sdate = result.getString("sdate");
	                String did = result.getString("did");
	                String pre = result.getString("pre");
	                String dig = result.getString("dia");
	                System.out.println(pname + "    " + sdate + "   " + did + " " + pre + " " + dig);
	            }
	            result.close();
	}
	    catch(Throwable oops)
	    {
	        oops.printStackTrace();
	    }
	}
	
	public void getVisitorsPerMonth(Connection connection)
	{
	    try
	    {
	        String sql = "SELECT Mon, count(Mon) as Num FROM (SELECT to_char(StartDate, 'MON-YY') AS Mon FROM CheckIN) GROUP BY Mon";
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        System.out.println("Month   Number of Patients");
	        System.out.println("-----   ------------------");
	        while(result.next())
	        {
	            String mon = result.getString("Mon");
	            int num = result.getInt("Num");
	            System.out.println(mon + "  " + num);
	        }
	        if(statement != null){statement.close();}
	    }
	    catch(Throwable oops)
	    {
	        oops.printStackTrace();
	    }
	}
}
