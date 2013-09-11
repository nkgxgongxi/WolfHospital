import java.util.Scanner;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Patient implements Operable {
	static String tableName = "Patient";
	public void insert(Database myDB) throws SQLException {
		Scanner br1 = new Scanner (new InputStreamReader (System.in));
		System.out.println("INSERT INTO Patient.");
		System.out.println("Please Input the information you want to insert into patient");
		String Insert_val;
		Insert_val = br1.nextLine();
		myDB.insert(tableName, Insert_val);
		br1.close();	
	}
	
	public void update(Database myDB) throws SQLException
	{
		Scanner br1 = new Scanner (new InputStreamReader (System.in));
		System.out.println("UPDATE Patient");
		String Update_col;
		String Update_cond;
		System.out.println("Please Input the attribute you want to modify and the new value of it.");
		Update_col = br1.nextLine();
		System.out.println("Please Input the WHERE condition.");
		Update_cond = br1.nextLine();					
		myDB.update(tableName, Update_col, Update_cond);
		br1.close();
	}	

	public void delete(Database myDB) throws SQLException
	{
		Scanner br1 = new Scanner (new InputStreamReader (System.in));
		System.out.println("DELETE FROM Patient.");
		System.out.println("Please Input the condition to allocate the record you want to delete.");
		String Delete_cond;
		Delete_cond = br1.nextLine();
		myDB.delete(tableName, Delete_cond);
		br1.close();
	}
	
	public void genReport(Database myDB)
	{
		try {
			Scanner buf = new Scanner (new InputStreamReader (System.in));
			System.out.println("Report the medical history for a given patient and for a certain time period (month/year):");
			System.out.println("Please enter the patient's name:");
			String patientname = buf.nextLine();
			System.out.println("Please enter the starting date in the format of 'dd-MMM-yyyy':");
			String stime = buf.nextLine();
			System.out.println("Please enter the ending date in the format of 'dd-MMM-yyyy':");
			String etime = buf.nextLine();
			String col = "p.Name AS pname,m.startdate AS sdate, m.doctorid AS did, m.Prescription AS pre, m.Diagnosis AS dia";
			String tName = "MedicalRecord m,Patient p";
			String condition = "m.PatientID = p.ID AND p.Name = '" + patientname + "' AND m.StartDate >= '" + stime + "' AND m.StartDate <= '" + etime + "'";
			ResultSet result = myDB.select(tName, col, condition);
			buf.close();
            System.out.println("PatientName     Startdate           DocID   Prescription    Diagnosis");
            System.out.println("-----------     ---------           -----   ------------    ---------");
            while(result.next()) {
                String pname = result.getString("pname");
                String sdate = result.getString("sdate");
                String did = result.getString("did");
                String pre = result.getString("pre");
                String dig = result.getString("dia");
                System.out.println(pname + "    " + sdate + "   " + did + " " + pre + " " + dig);
            }
            result.close();
		}catch(Throwable oops) {
	        oops.printStackTrace();
	    }
	}
	
	public void getVisitorsPerMonth(Database myDB)
	{
	    try {
	        String col = "Mon, count(Mon) as Num";
	        String source = "(SELECT to_char(StartDate, 'MON-YY') AS Mon FROM CheckIN) GROUP BY Mon";
	        ResultSet result = myDB.select(source, col);
	        System.out.println("Month   Number of Patients");
	        System.out.println("-----   ------------------");
	        while(result.next()) {
	            String mon = result.getString("Mon");
	            int num = result.getInt("Num");
	            System.out.println(mon + "  " + num);
	        }
	    }catch(Throwable oops) {
	        oops.printStackTrace();
	    }
	}
}
