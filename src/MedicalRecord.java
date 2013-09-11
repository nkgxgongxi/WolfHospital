import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class MedicalRecord implements Operable {
	public void update(Database myDB, boolean isTest) throws SQLException {
			BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
			if(isTest) {
				System.out.println("Update a medical record of a test.");
				System.out.println("Entering the index information for the test record:");
			}
	        System.out.println("Paitnet ID");
	        String pid = buf.readLine();
	        System.out.println("Start Date in dd-MMM-yyyy");
	        String stadate = buf.readLine();
	        System.out.println("Doctor ID");
	        String docid = buf.readLine();
	        System.out.println("now enter the new values:");
	        if
	        System.out.println("Prescription");
	        String presc = buf.readLine();
	        System.out.println("Diagnosis");
	        String diagn = buf.readLine();
	        String sql = "UPDATE MedicalRecord SET Prescription = '" + presc + "' ,Diagnosis = '" + diagn + "' WHERE PatientID = " + pid + " AND DoctorID = " + docid +" AND StartDate = '" + stadate +"'";
	        System.out.println(sql);
	        return sql;
	        
	        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
	        System.out.println("Now update a medical record of a test.");
	        System.out.println("Entering the index information for the dest record:");
	        System.out.println("Paitnet ID");
	        String pid = buf.readLine();
	        System.out.println("Start Date in dd-MMM-yyyy");
	        String stadate = buf.readLine();
	        System.out.println("Doctor ID");
	        String docid = buf.readLine();
	        System.out.println("now enter the new values:");
	        System.out.println("Diagnosis");
	        String diagn = buf.readLine();
	        String sql = "UPDATE MedicalRecord SET Diagnosis = '" + diagn + "' WHERE PatientID = " + pid + " AND DoctorID = " + docid +" AND StartDate = '" + stadate +"'";
	        return sql;
    
	    }
	}
	// Enter a new medical record for each treatment
	// SQL> INSERT INTO MedicalRecord(PatientID, StartDate, DoctorID, Prescription, Diagnosis) values (1, '03-MAR-11', 3, 'Pills', 'Flu');
	public void  insert(Database myDB, boolean isTest) throws SQLException {
	    try
	    {
	        //buffer
	        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
	        System.out.println("Entering a new medical record for this TREATMENT:");
	        System.out.println("values in the format like below:");
	        System.out.println("PatientID, 'StartDate in dd-MMM-yyyy', DoctorID, 'Prescription', 'Diagnosis'");
	        String values = buf.readLine ();
	        String sql = "INSERT INTO MedicalRecord(PatientID, StartDate, DoctorID, Prescription, Diagnosis) values (" + values + ")";
	        return sql;
	    }
	    catch(Throwable oops)
	    {
	        oops.printStackTrace();
	        return "oops!!";
	    }

	}
	//Update a new medical record for each treatment
	//SQL> UPDATE MedicalRecord SET Prescription='Shot; Shot', Diagnosis='Flu; Flu' WHERE PatientID=1 AND StartDate='03-MAR-11' AND DoctorID=1;
	static String update_treatment_medrecord()
	{
	    try
	    {
	        //buffer
	        
	    }
	    catch(Throwable oops)
	    {
	        oops.printStackTrace();
	        return "oops!!";
	    }
	}
	// Enter a new medical record for each test
	// SQL> INSERT INTO MedicalRecord(PatientID, StartDate, DoctorID, Diagnosis) values (1, '03-MAR-11', 2, 'Normal');
	static String new_test_medrecord()
	{
	    try
	    {
	        //buffer
	        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
	        System.out.println("Entering a new medical record for this TEST:");
	        System.out.println("values in the format like below:");
	        System.out.println("PatientID, 'StartDate in dd-MMM-yyyy', DoctorID, 'Diagnosis'");
	        String values = buf.readLine ();
	        return "INSERT INTO MedicalRecord(PatientID, StartDate, DoctorID, Diagnosis) values (" + values + ")";
	    }
	    catch(Throwable oops)
	    {
	        oops.printStackTrace();
	        return "oops!!";
	    }
	}
	//Update a new medical record for each test
	//SQL> UPDATE MedicalRecord SET Diagnosis='Neutropenia' WHERE patientID=1 AND StartDate='03-MAR-11' AND DoctorID=2;
	static String update_test_medrecord()
	{
	    try
	    {
	        //buffer
	        
	    }
	    catch(Throwable oops)
	    {
	        oops.printStackTrace();
	        return "oops!!";
	    }
	}
}
