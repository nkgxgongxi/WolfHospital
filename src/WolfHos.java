import java.io.*;
import java.sql.*;
import java.util.*;

public class WolfHos {
	static void create(Database myDB, ArrayList<String> tables) {
		try {
			for(String t : tables) {
				Scanner s = new Scanner(new FileReader("tables/" + t));
				String att = "";
				while(s.hasNextLine())
					att += s.nextLine();
				myDB.create(t, att);
				s.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Throwable oops) {
			oops.printStackTrace();
		}
	}
	static void init(Database myDB) throws SQLException {
        try {
        	
                myDB.insert("STAFF", "10001, 'John Terry', 48, 'M', 'Doctor', '919-100-2101', '106 Cloverdale Ct, Raleigh NC 27607'");
                myDB.insert("DOCTOR", "10001, 'Senior Surgeon', 'Oncological Surgery'");
                myDB.insert("STAFF", "20001, 'Rebecca Johnston', 36, 'F', 'Nurse', '919-853-2744', '1048 Avent Ferry, Raleigh NC 27606'");
                myDB.insert("NURSE", "20001, 'Senior Nurse'");
                myDB.insert("STAFF", "30001, 'Michael Smith', 41, 'M', NULL, '866-452-9100', '2734 Timber Dr, Maitland, FL'");
                myDB.insert("BILLINGSTAFF", "30001");
                myDB.insert("PATIENT", "1102140001, '677-22-1134', 'Jason Hunter', '23-MAY-1981', 'M', 30, '919-232-1122', '101 Dormant Dr. Cary, NC', 'In-ward'");
                myDB.insert("PATIENT", "1103110001, '677-56-4484', 'Michael Romeo', '04-FEB-1971', 'M', 40, '919-383-3388', '404 Reinwood Rd. Durham, NC', 'In-ward'");
                myDB.insert("WARD", "201, 4, 70");
                myDB.insert("INCHARGEOF ", "201, 20001");
                myDB.insert("BED", "1, 201, 'Y'");
                myDB.insert("BED", "2, 201, 'Y'");
                myDB.insert("BED", "3, 201, 'Y'");
                myDB.insert("BED", "4, 201, 'Y'");
                myDB.insert("CHECKIN", "1102140001, '14-FEB-2011', 1, 201, NULL");
                myDB.update("Bed", "Available = 'N'", "BedNum = 1 AND WardNum = 201");
                myDB.insert("CHECKIN ", "1103110001, '11-MAR-2011', 2, 201, NULL");
                myDB.update("Bed", "Available = 'N'", "BedNum = 2 AND WardNum = 201");
                myDB.insert("MEDICALRECORD", "1102140001, '14-FEB-2011', 10001, 'Pain Killer', 'Carcinoma'");
                myDB.insert("MEDICALRECORD", "1103110001, '11-MAR-2011', 10001, 'Pain Killer', 'Lymphoma'");
                myDB.insert("BILLINGACCOUNT", "1102140001, '14-FEB-2011', '677-22-1134', '101 Dormant Dr. Cary, NC', 'VISA, Credit, 1111-1022-2222-1023', 0, 50, 0");
                myDB.insert("BILLINGACCOUNT", "1103110001, '11-MAR-2011', '677-56-4484', '404 Reinwood Rd. Durham, NC', 'VISA, Credit, 2222-1111-0011-1133', 0, 50, 0");
        }catch(Throwable oops) {
        	oops.printStackTrace();
        }
	}
	
	public static ArrayList<String> getTableList(String configuration) {
		Properties prop = new Properties();
		ArrayList<String> tables = new ArrayList<String>();
		try {
			prop.load(new FileInputStream(configuration));
			Scanner s = new Scanner(new FileReader(prop.getProperty("tables")));
			while(s.hasNextLine()) 
				tables.add(s.nextLine());
			s.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return tables;
	}
	
//	private void report(Connection connection, String str) {
//		try {
//			Statement statement = connection. createStatement();
//			ResultSet result = statement.executeQuery(str);
//		            while(result.next())
//		            {
//		                result.getInt("");
//		            }
//		}catch(Throwable oops) {
//	        oops.printStackTrace();
//	    }
//	}
	
	public static void main (String[] args) {
	    try {
	    	Logger.init();
	    	Tools.printDate();
	        Scanner buf = new Scanner (new InputStreamReader (System.in));
	        //Put your oracle ID and password here
	        System.out.println("Please enter your username for sql.");
	        String user = buf.nextLine();
	        System.out.println("Please enter your password.");
	        String password = buf.nextLine();
	        buf.close();
	        Database myDB = Database.getInstance();
			myDB.init(user, password);
			ArrayList<String> myTables = getTableList("config.properties");
			create(myDB, myTables);
	        init(myDB);
	    }catch (Throwable oops) {
	        oops.printStackTrace();
	    }
	    System.out.println("Thanks for using our program!");
	}
}

/*

public static char[] task1(String table, String operation, char c1, char c2, char c3, char[] cc2) {

BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

String temp;
String com;

boolean flag = true;

try {
while (flag) {

	System.out.println("Please choose from the following options. Enter R to return to superior menu. Enter Q to quit the program.");
	System.out.println("1. Enter/Update/Delete basic information about staff.");
	System.out.println("2. Enter/Update/Delete basic information about patient.");
	System.out.println("3. Enter/Update/Delete basic information about wards/beds.");
	System.out.println("4. Check/Assign/Reserve/Release bed for a patient according to the request.");

	System.out.println("Enter your choice:");
	
	temp = br.readLine();
                if(temp.isEmpty())
                {
                    continue;
                }
	c2 = temp.charAt(0);

	if (temp.length() > 1) {
		System.out.println("Invalid input! Please enter a number from 1 to 4. Enter R to return to superior menu. Enter Q to quit the program.");
	}
	
	switch (c2) {
		case '1':
			table = "staff";
			com = "basic information about staff";
			c3 = chooseop(table, operation, c1, c2, c3, com);
			flag = false;
			break;
		case '2':
			table = "patient";
			com = "basic information about patient";
			c3 = chooseop(table, operation, c1, c2, c3, com);
			flag = false;
			break;
		case '3':
			table = "ward";
			com = "basic information about wards/bed";
			c3 = chooseop(table, operation, c1, c2, c3, com);
			flag = false;
			break;
		case '4':
			table = "ward";
			com = "bed for a patient according to the request";
			c3 = chooseop(table, operation, c1, c2, c3, com);
			flag = false;
			break;
		case 'R':
			cc2[0]= 'R';
			cc2[1] = 'R';
			return cc2;
		case 'Q':
			c1 = 'Q';
			cc2[0] = 'Q';
			cc2[1] = 'Q';
			return cc2;
		default:
			System.out.println("Invalid number! Please enter a number from 1 to 4. Please enter Q if you want to quit the program.");
			continue;
	}
if (c3 == 'R') flag = true;
if (c3 == 'Q') c2 = 'Q';
}
}
catch (IOException e){
      System.out.println("Error reading from user");
}
//cc2 = c2 + c3;
cc2[0] = c2;
cc2[1] = c3;
return cc2;
}				

public static char[] task2(String table, String operation, char c1, char c2, char c3, char[] cc2)
{

BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

String temp;
String com;

boolean flag = true;

try {
while (flag) {

	System.out.println("Please choose from the following options. Enter R to return to superior menu. Enter Q to quit the program.");
	System.out.println("1. Enter/Update a new medical record for treatment.");
	System.out.println("2. Enter/Update a new medical record for a test.");
	System.out.println("3. Enter/Update a new medical record for check-in.");

	System.out.println("Enter your choice:");
	
	temp = br.readLine();
                if(temp.isEmpty())
                {
                    continue;
                }
	c2 = temp.charAt(0);
	
	if (temp.length() > 1) {
		System.out.println("Invalid input! Please enter a number from 1 to 3. Enter R to return to superior menu. Enter Q to quit the program.");
	}
	
	table = "MedicalRecord";

	switch (c2) {
		case '1':
			com = "a new medical record for treatment.";
			c3 = chooseop(table, operation, c1, c2, c3, com);
			flag = false;
			break;
		case '2':
			com = "a new medical record for a test"; 
			c3 = chooseop(table, operation, c1, c2, c3, com);
			flag = false;
			break;
		case '3':
			com = "a new medical record for check-in";
			c3 = chooseop(table, operation, c1, c2, c3, com);
			flag = false;
			break;
		case 'R':
			cc2[0] = 'R';
			cc2[1] = 'R';
			return cc2;
		case 'Q':
			c1 = 'Q';
			cc2[0] = 'Q';
			cc2[1] = 'Q';
			return cc2;
		default:
			System.out.println("Invalid number! Please enter a number from 1 to 3. Enter R to return to superior menu, enter Q if you want to quit the program.");
			continue;
	}
if (c3 == 'R') flag = true;
if (c3 == 'Q') c2 = 'Q';
}
}
catch (IOException e){
      System.out.println("Error reading from user");
}
cc2[0] = c2;
cc2[1] = c3;
return cc2;

}				

public static char[] task3(String table, String operation, char c1, char c2, char c3, char[] cc2)
{

c2 = '0';

c3 = chooseop2(table, operation, c1, c2, c3);

if (c3 == 'Q') {
	cc2[0] = 'Q';
	cc2[1] = 'Q';
} else{

cc2[0] = c2;
cc2[1] = c3;
}
return cc2;

}

public static char[] task4(String table, String operation, char c1, char c2, char c3, char[] cc2)
{

BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

String temp;

boolean flag = true;

c2 = '0';
try {
while (flag) {

	System.out.println("Please choose from the following options. Enter R to return to superior menu. Enter Q to quit the program.");
	System.out.println("1. Generate reports: report the medical history for a given patient and for a certain time period (month/year).");
	System.out.println("2. Return the current usage status for all wards/beds.");
	System.out.println("3. Retrun the number of patients per month.");
	System.out.println("4. Return the ward usage percentage.");
	System.out.println("5. Return information on all the patients a doctor is currently responsible for.");
	System.out.println("6. Return information on hospital staff grouped by their role.");

	System.out.println("Enter your choice:");
	
	temp = br.readLine();
                if(temp.isEmpty())
                {
                    continue;
                }
	c3 = temp.charAt(0);
	
	if (temp.length() > 1) {
		System.out.println("Invalid input! Please enter a number from 1 to 6. Enter R to return to superior menu. Enter Q to quit the program.");
	}
	
	table = "MedicalRecord";

	switch (c3) {
		case '1':
			flag = false;
			break;
		case '2':
			flag = false;
			break;
		case '3':
			flag = false;
			break;
		case '4':
			flag = false;
			break;
		case '5':
			flag = false;
			break;
		case '6':
			flag = false;
			break;
		case 'R':
			cc2[0] = 'R';
			cc2[1] = 'R'; 
			return cc2;
		case 'Q':
			c1 = 'Q';
			cc2[0] = 'Q';
			cc2[1] = 'Q';
			return cc2;
		default:
			System.out.println("Invalid number! Please enter a number from 1 to 6. Enter R to return to superior menu, enter Q if you want to quit the program.");
			continue;
	}
if (c3 == 'R') flag = true;
if (c3 == 'Q') c2 = 'Q';
}

}
catch (IOException e){
      System.out.println("Error reading from user");
}
cc2[0] = c2;
cc2[1] = c3;
return cc2;
}

public static void exesql(String table, String operation, char c1, char c2, char c3) throws SQLException
{
int result;

result = (c1 - 48)*100 + (c2-48)*10 + (c3-48);


switch (result) {
	case 111:
//insert 'enter basic information for staff' function here
		{
			INSERTSTAFF(connection, table);
			
		}
		
		break;
	case 112:
//insert 'update basic information for staff' function here
		{			
			UPDATESTAFF(connection, table);
			
		}
		
		break;
	case 113:
//insert 'delete basic information for staff' function here
		{			
			deleteStaff(connection, table);
			
		}
		
		break;
	case 121:
//insert 'enter basic information for patient' function here
		{			
			insertPatient(connection, table);
			
		}
		
		break;
	case 122:
//insert 'update basic information for patient' function here
		{			
			UPDATEPATIENT(connection, table);
			
		}
		
		break;
	case 123:
//insert 'delete basic information for patient' function here
		{			
			DELETEPATIENT(connection, table);
			
		}

		break;
	case 131:
//insert 'enter basic information for ward' function here
		{			
			INSERTWARD(connection, table);
			
		}
		
		break;
	case 132:
//insert 'update basic information for ward' function here
		{			
			UPDATEWARD(connection, table);
			
		}
		
		break;
	case 133:
//insert 'delete basic information for ward' function here
		{			
			DELETEWARD(connection, table);
			
		}

		break;
	case 141:
//insert 'check ward' function here
            {
                check_available_bed(connection);
            }
		break;
	case 142:
//insert 'assign bed' function here
            {
                check_available_bed(connection);
            }
		break;
	case 143:
//insert 'reserve bed' function here
            {
                reserve_bed(connection);
            }
		break;
	case 144:
//insert 'release bed' function here
            {
                release_bed(connection);
            }
		break;
	case 211:
//insert 'enter a medical record for a treatment'
            {
                String mysql = new_treatment_medrecord();
                if(mysql != "oops!!")
                {
                    jdbc_update_medicalrecord(connection, mysql);
                }
            }
		break;
	case 212:
//insert 'update a medical record for a treatment'
            {
                String mysql = update_treatment_medrecord();
                if(mysql != "oops!!")
                {
                    jdbc_update_medicalrecord(connection, mysql);
                }
            }
		break;
	case 221:
//insert 'enter a medical record for a test'
            {
                String mysql = new_test_medrecord();
                if(mysql != "oops!!")
                {
                    jdbc_update_medicalrecord(connection, mysql);
                }
            }
		break;
	case 222:
//insert 'update a medical record for a test'
            {
                String mysql = update_test_medrecord();
                if(mysql != "oops!!")
                {
                    jdbc_update_medicalrecord(connection, mysql);
                }
            }
		break;
	case 231:
            {
                new_checkin_record(connection);
            }
//insert 'enter a record for checkin'
		break;
	case 232:
            {
                update_checkin_record(connection);
            }
//insert 'update a record for checkout'
		break;
	case 301:
//insert 'generate billing account'
			{
				INSERTBACCOUNT(connection, table);
			}
			
		break;
	case 302:
//insert 'maintain billing account'
			{
				UPDATEBACCOUNT(connection, table);
			}
			
		break;
	case 401:
            {
		generate_reports_patient(connection);
            }
                    break;
	case 402:
            {
                get_usage_beds(connection);
            }
		break;
	case 403:
            {
                number_patients_permonth(connection);
            }
		break;
	case 404:
            {
                ward_usage_percent(connection);
            }
		break;
	case 405:
            {
                generate_reports_doctor(connection);
            }
                    break;
	case 406:
            {
                    generate_staff_role(connection);
            }
		break;
	default:
		System.out.println("wrong!");
}
return;
}

public static char chooseop(String table, String operation, char c1, char c2, char c3, String com)
{

System.out.println("c2 in chooseop "+c2);
BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

String temp;

boolean flag = true;

try{
while (flag) {
	System.out.println("Please choose from the following options, Enter R to return to superior menu. Enter Q to quit the program.");
	if (c2 != '4') {
	System.out.println("1. Enter " + com);
	System.out.println("2. Update " + com);	

	if (c1 == '1') {
		System.out.println("3. Delete " + com);		
	}
	}

	if (c2 == '4') {
		System.out.println("1. Check " + com);
		System.out.println("2. Assign "  + com);
		System.out.println("3. Reserve " + com);
		System.out.println("4. Release " + com);
	}


	System.out.println("Enter your choice: ");

	temp = br.readLine();
                if(temp.isEmpty())
                {
                    continue;
                }
	c3 = temp.charAt(0);


	
	if (temp.length() > 1) {
		System.out.println("Invalid Input!");
		continue;
	}
	
	if (c1 == '1' && c2 != '4' && (c3 > '3' || c3 < '1') && c3 != 'R' && c3 != 'Q') {
		System.out.println("Invalid input!");
		continue;
	}

	if (c1 == '1' && c2 == '4' && (c3 > '4' || c3 < '1') && c3 != 'R' && c3 != 'Q') {
		System.out.println("Invalid input!");
		continue;
	}

	if (c1 == '2' && (c3 < '1' || c3 > '2') && c3 != 'R' && c3 != 'Q') {
		System.out.println("Invalid input!");
		continue;
	}

	switch (c3) {
		case '1':
			operation = "enter";
			flag = false;
			break;
		case '2':
			operation = "update";
			flag = false;
			break;
		case '3':	
			operation = "delete";
			flag = false;
			break;
		case '4': 
			operation = "check";
			flag = false;
			break;
		case '5':
			operation = "assign";
			flag = false;
			break;
		case '6':
			operation = "reserve";
			flag = false;
			break;
		case '7':
			operation = "release";
			flag = false;
			break;
		case 'R':
			flag = false;
			return c3;
		case 'Q':
			c1 = 'Q';
			flag = false;
			return c3;
		default:
			System.out.println("Invalid number! ");
			continue;
	}				

}

}
catch (IOException e){
      System.out.println("Error reading from user");
}
return c3;

}				

public static char chooseop2(String table, String operation, char c1, char c2, char c3) 
{

BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

String temp;

boolean flag = true;		

try{
while (flag) {
	System.out.println("Please choose from the following options, Enter R to return to superior menu. Enter Q to quit the program.");
	System.out.println("1. Generate billing account for every visit of every patient");
	System.out.println("2. Maitain billing account for every visit of every patient");	

	System.out.println("Enter your choice: ");

	temp = br.readLine();
                if(temp.isEmpty())
                {
                    continue;
                }
	c3 = temp.charAt(0);
	
	if (c3 != '1' && c3 != '2' && c3 != 'R' && c3 != 'Q') {
		System.out.println("Invalid input!");
		continue;
	}
	if (c3 == 'R') {
		flag = false;
		return c3;
	}
	if (c3 == 'Q') {
		c1 = 'Q';
		flag = false;
		return c3;
	}

flag = false;
}
}
catch (IOException e){
      System.out.println("Error reading from user");
}
return c3;

}
*/
	
/*
//*****************************************************************************
//*****************************************************************************
//TASK group 1
//Information processing
//*****************************************************************************
/**
 * Check available beds
 */

/**
/* Check available wards
/*SQL>SELECT DISTINCT WardNum FROM Bed WHERE Available <> 'N' AND Available <> 'R'
 */

//Reserve wards/beds

//*****************************************************************************
//*****************************************************************************
//TASK group 2
//Maintaining medical records
//*****************************************************************************

//Enter a new medical record for each check-in

//Update a new medical record for each check-in
//think it as check out


//*****************************************************************************
//TASK group 4
//Return: the current usage status for all wards/beds; the number of patients per month; the ward-usage percentage.
//*****************************************************************************
//Return: 	the current usage status for all wards/beds;

//Return:   the number of patients per month;

//Return:   the ward-usage percentage.
//********************
//task 4 by Hui
//********************
//	System.out.println("Please choose from the following options. Enter R to return to superior menu. Enter Q to quit the program.");
//	System.out.println("1. Generate reports: report the medical history for a given patient and for a certain time period (month/year).");
//	System.out.println("2. Return the current usage status for all wards/beds.");
//	System.out.println("3. Retrun the number of patients per month.");
//	System.out.println("4. Return the ward usage percentage.");
//	System.out.println("5. Return information on all the patients a doctor is currently responsible for.");
//	System.out.println("6. Return information on hospital staff grouped by their role.")
//Generate reports: report the medical history for a given patient and for a certain time period (month/year).
//SELECT P.* FROM (Patient P INNER JOIN MedicalRecord M ON P.ID = M.PatientID) INNER JOIN Staff S ON S.ID = M.DoctorID WHERE S.name = 'Lindsay Ball';

//select * from staff where ID in (select ID from Doctor);




