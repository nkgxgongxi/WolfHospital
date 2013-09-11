import java.util.Scanner;
import java.io.InputStreamReader;
import java.sql.*;


public class Doctor {
	static void genReports(Database myDB) throws SQLException {
		Scanner buf = new Scanner (new InputStreamReader (System.in));
		System.out.println("Return information on all patients a given doctor is currently responsible for.");
		System.out.println("Please enter the doctor's name in the format of 'Matt Jones':");
		String doctorname = buf.nextLine();
		String col = "P.ID AS pid,P.Name AS pname";
		String source = "(Patient P INNER JOIN MedicalRecord M ON P.ID = M.PatientID)"
				+ " INNER JOIN Staff S ON S.ID = M.DoctorID";
		String condition = "WHERE S.name = '" + doctorname + "'";
		ResultSet result = myDB.select(source, col, condition);
		buf.close();
        System.out.println("PatientID   PatientName");
        System.out.println("---------   ------------");
        while(result.next()) {
            String pid = result.getString("pid");
            String pname = result.getString("pname");
            System.out.println( pid + "    " + pname);
        }
        result.close();
	}
}
