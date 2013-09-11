import java.util.Scanner;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class MedicalRecord implements Operable {
	static boolean isTest;
	static String tableName = "MedicalRecord";
	public void update(Database myDB) throws SQLException {
		Scanner buf = new Scanner (new InputStreamReader (System.in));
		if(isTest) {
			System.out.println("Update a medical record of a test.");
			System.out.println("Entering the index information for the test record:");
		}
		else {
			System.out.println("Update a medical record of a test.");
	        System.out.println("Entering the index information for the dest record:");
		}
        System.out.println("Paitnet ID");
        String pid = buf.nextLine();
        System.out.println("Start Date in dd-MMM-yyyy");
        String stadate = buf.nextLine();
        System.out.println("Doctor ID");
        String docid = buf.nextLine();
        System.out.println("now enter the new values:");
        String presc = "";
        if(!isTest) {
        	System.out.println("Prescription");
        	 presc = buf.nextLine();
        }
        System.out.println("Diagnosis");
        String diagn = buf.nextLine();
        String value;
        if(isTest)
        	value = "Diagnosis = '" + diagn + "'";
        else
        	value = "Prescription = '" + presc + "' ,Diagnosis = '" + diagn + "'";
        String condition = "PatientID = " + pid + " AND DoctorID = " + docid +" AND StartDate = '" + stadate +"'";
        myDB.update(tableName, value, condition);
        buf.close();
	}
	public void insert(Database myDB) throws SQLException {
		Scanner buf = new Scanner (new InputStreamReader (System.in));
        System.out.println("Entering a new medical record for this TREATMENT:");
        System.out.println("values in the format like below:");
        if(!isTest) {
        	System.out.println("PatientID, 'StartDate in dd-MMM-yyyy', DoctorID, 'Prescription', 'Diagnosis'");
        }
        else {
            System.out.println("PatientID, 'StartDate in dd-MMM-yyyy', DoctorID, 'Diagnosis'");
        }
        String values = buf.nextLine ();
        String tName = "MedicalRecord(PatientID, StartDate, DoctorID, Prescription, Diagnosis)";
        myDB.insert(tName, values);
        buf.close();
	}
	
	public void insertTest(Database myDB) throws SQLException {
		isTest = true;
		insert(myDB);
	}
	
	public void updateTest(Database myDB) throws SQLException {
		isTest = true;
		update(myDB);
	}
	
	public void insertTreatment(Database myDB) throws SQLException {
		isTest = false;
		insert(myDB);
	}
	
	public void updateTreatment(Database myDB) throws SQLException {
		isTest = false;
		update(myDB);
	}
}
