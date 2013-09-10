import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Doctor {
	static void genReports(Connection connection)
	{
	try
	{
		BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Return information on all patients a given doctor is currently responsible for.");
		System.out.println("Please enter the doctor's name in the format of 'Matt Jones':");
		String doctorname = buf.readLine();

		String sql = "SELECT p.ID AS pid,P.Name AS pname FROM (Patient P INNER JOIN MedicalRecord M ON P.ID = M.PatientID) INNER JOIN Staff S ON S.ID = M.DoctorID WHERE S.name = '" + doctorname + "'";
		System.out.println(sql);
	            Statement statement = connection. createStatement();
		ResultSet result = statement.executeQuery(sql);
	            System.out.println("PatientID   PatientName");
	            System.out.println("---------   ------------");
	            while(result.next())
	            {
	                String pid = result.getString("pid");
	                String pname = result.getString("pname");
	                System.out.println( pid + "    " + pname);
	            }
	            result.close();
	}
	catch(Throwable oops)
	    {
	        oops.printStackTrace();
	    }
	}
}
