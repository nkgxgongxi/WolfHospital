import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;

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
	static void insertBAccount(Database myDB) throws SQLException {
		/**
		 * This method interactively insert a new BillingAccount record.
		 */
		try {
			Scanner br1 = new Scanner (new InputStreamReader (System.in));
			System.out.println("PatientID");
			String PID = br1.nextLine();
			System.out.println("Visit Date"); 
			String VDATE = br1.nextLine();
			System.out.println("SSN"); 
			String SSN = br1.nextLine();
			System.out.println("PAYMENT"); 
			String PAYMENT = br1.nextLine();
			System.out.println("Billing Address"); 
			String BADDR = br1.nextLine();
			System.out.println("Fees"); 
			String Fees = "0, 50, 0";
			String Content = PID + ", '" + VDATE + "', '" + SSN + "', '" + PAYMENT + "', '" + BADDR + "', " + Fees;
			System.out.println("Input is: " + Content);
			myDB.insert("BillingAccount", Content);
			br1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	static void updateBAccount(Database myDB) throws SQLException
	{
		try {
			Scanner br2 = new Scanner (new InputStreamReader (System.in));
			System.out.println("Modify Basic Information or Update Bill?");
			String func = br2.nextLine();
			if(func.equals("Modify Basic Information"))
			{
				System.out.println("Please input the SET Value part:");
				String Update_val = br2.nextLine();
				System.out.println("Please input the Update Condition:");
				String Update_cond = br2.nextLine();
				myDB.update("BillingAccount", Update_val, Update_cond);
			}
			else
			{
				System.out.println("So You want to Count Bill.");
				System.out.println("Please input your ID:");
				String PaID = br2.nextLine();
				System.out.println("Please input your CheckIn date:");
				String SDate = br2.nextLine();
				myDB.stmt = myDB.conn.createStatement();
				String cont = "Select ENDDATE, STARTDATE, WardNum FROM CHECKIN WHERE PatientID =" + PaID + "AND StartDate =" +SDate;
				ResultSet result1 = myDB.stmt.executeQuery(cont);
				SimpleDateFormat myFormat = new SimpleDateFormat("dd-mmm-yy", Locale.ENGLISH);
				Date end = myFormat.parse("01-Jan-01");
				Date start = myFormat.parse("01-Jan-01");
	            int WNum = -1;
	            while(result1.next()) {
	                String sstart =result1.getString("STARTDATE");
					start = myFormat.parse(sstart);
					end = myFormat.parse(result1.getString("ENDDATE"));
					WNum = result1.getInt("WardNum");
					//System.out.println(mon + "  " + num);
				}
				ResultSet result2 = myDB.stmt.executeQuery("Select CHARGE from ward where WardNum =" + WNum);
				int rate = -1;
	            while(result2.next()) {
					rate = result2.getInt("CHARGE");
				}
				long mil = 86400000; 
				//use millisecond as unit to compute the time of one day.
				long day = (end.getTime() - start.getTime())/mil; 
				//through dividing by one day's time we get the number of days.
				long aFee = (long) rate*day;
				long tFee = 0;
				System.out.println("Medicine Fee?(Y/N)");
				String Answer = br2.nextLine();
	            int rFee = -1, mFee = -1;
				if(Answer.equals("Y"))
				{
					System.out.println("Update Medicine Fee SET.");
					String u_val = br2.nextLine();
					String u_cond = "PatientID = " + PaID + "AND VisitDate =" + SDate;
					myDB.update("BillingAccount", u_val, u_cond);
					ResultSet result3 = myDB.stmt.executeQuery("Select RegistrationFee, MedicationFee from BillingAccount where " + u_cond);
					while(result3.next())
					{
						rFee = result3.getInt("RegistrationFee");
						mFee = result3.getInt("MedicationFee");
					}
					tFee = mFee + aFee + rFee;				
				}
				else
				{
					tFee = aFee + rFee;
				}
				System.out.println("The total bill is " + tFee);
				
			}
			br2.close();
		}catch(ParseException pe) {
			pe.printStackTrace();
		}
	}
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
//Check available beds
//SQL>SELECT WardNum, BedNum FROM Bed WHERE Available <> 'N' AND Available <> 'R'
static int check_available_bed(Connection connection) throws SQLException
{
    Statement statement = null;
    try
    {
        String sql = "SELECT WardNum, BedNum FROM Bed WHERE Available <> 'N' AND Available <> 'R'";
        statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        System.out.println("wardnum  bednum");
        System.out.println("-------  ------");
        while(result.next())
        {
           int bednum = result.getInt("BedNum");
           int wardnum = result.getInt("WardNum");
           System.out.println(wardnum + "       " +bednum);
        }

    }
    catch(Throwable oops)
    {

        oops.printStackTrace();
        return -1;
    }
    finally
    {
       if(statement != null){statement.close();}
    }
    return 1;
}
//Check available wards
//SQL>SELECT DISTINCT WardNum FROM Bed WHERE Available <> 'N' AND Available <> 'R'
static int check_available_ward(Connection connection) throws SQLException
{
    Statement statement = null;
    try
    {
        String sql = "SELECT DISTINCT WardNum FROM Bed WHERE Available <> 'N' AND Available <> 'R'";
        statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        System.out.println("wardnum");
        System.out.println("-------");
        while(result.next())
        {
           int wardnum = result.getInt("WardNum");
           System.out.println(wardnum);
        }

    }
    catch(Throwable oops)
    {

        oops.printStackTrace();
        return -1;
    }
    finally
    {
       if(statement != null){statement.close();}
    }
    return 1;
}
//Reserve wards/beds
static int reserve_bed(Connection connection) throws SQLException
{
    Statement statement = null;
    try
    {
        System.out.println("Choose the ward and bed you want to reserve:");
        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
        System.out.println("Please input the ward number:");
        String wardNum = buf.readLine();
        System.out.println("Please input the bed number:");
        String bedNum = buf.readLine();
        String content="UPDATE Bed SET Available='R' WHERE WardNum="+ wardNum + " AND BedNum=" + bedNum;
        //System.out.println(content);
        statement = connection.createStatement();
        (content);
        System.out.println("Reserve succeed!");
    }
    catch(Throwable oops)
    {

        oops.printStackTrace();
        return -1;
    }
    finally
    {
       if(statement != null){statement.close();}
    }
    return 1;
}
//Release wards/beds
static int release_bed(Connection connection) throws SQLException
{
    Statement statement = null;
    try
    {
        System.out.println("Choose the ward and bed you want to release:");
        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
        System.out.println("Please input the ward number:");
        String wardNum = buf.readLine();
        System.out.println("Please input the bed number:");
        String bedNum = buf.readLine();
        String content="UPDATE Bed SET Available='Y' WHERE WardNum="+ wardNum + " AND BedNum=" + bedNum;
        //System.out.println(content);
        statement = connection.createStatement();
        statement.executeUpdate(content);
        System.out.println("Release succeed!");
    }
    catch(Throwable oops)
    {

        oops.printStackTrace();
        return -1;
    }
    finally
    {
       if(statement != null){statement.close();}
    }
    return 1;
}
//*****************************************************************************
//*****************************************************************************
//TASK group 2
//Maintaining medical records
//*****************************************************************************
static private int jdbc_update_medicalrecord(Connection connection, String str) throws SQLException
{
    Statement statement = null;
    try
    {
        statement = connection.createStatement();
        int result = statement.executeUpdate(str);
        if(result != 0)
        {
            if(result ==1)
            {
                System.out.println("1 row insert success!");
            }
            else
            {
                System.out.println(result+" rows insert success!");
            }
        }
        else
        {
            System.out.println("error no row insert!");
        }
    }
    catch(Throwable oops)
    {
        oops.printStackTrace();
        System.out.println("error no row insert!");
        return 0;
    }
    finally
    {
        statement.close();
    }
    return 1;
}
// Enter a new medical record for each treatment
// SQL> INSERT INTO MedicalRecord(PatientID, StartDate, DoctorID, Prescription, Diagnosis) values (1, '03-MAR-11', 3, 'Pills', 'Flu');
static String new_treatment_medrecord()
{
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
        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
        System.out.println("Update a medical record of a treatment.");
        System.out.println("Entering the index information for the dest record:");
        System.out.println("Paitnet ID");
        String pid = buf.readLine();
        System.out.println("Start Date in dd-MMM-yyyy");
        String stadate = buf.readLine();
        System.out.println("Doctor ID");
        String docid = buf.readLine();
        System.out.println("now enter the new values:");
        System.out.println("Prescription");
        String presc = buf.readLine();
        System.out.println("Diagnosis");
        String diagn = buf.readLine();
        String sql = "UPDATE MedicalRecord SET Prescription = '" + presc + "' ,Diagnosis = '" + diagn + "' WHERE PatientID = " + pid + " AND DoctorID = " + docid +" AND StartDate = '" + stadate +"'";
        System.out.println(sql);
        return sql;
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
    catch(Throwable oops)
    {
        oops.printStackTrace();
        return "oops!!";
    }
}
//Enter a new medical record for each check-in
static void new_checkin_record(Connection con) throws SQLException
{
    Statement checkstatement = null;
    Statement bedstatement = null;
    Statement insertstatement = null;
    try
    {

        //buffer
        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
        System.out.println("Entering a Check_In record:");
        System.out.println("input the Patient ID:");
        String pid = buf.readLine();
        System.out.println("input the StartDate in dd-MMM-yyyy:");
        String startdate = buf.readLine();
        System.out.println("input the WardNum to move in:");
        String wardnum = buf.readLine();
        System.out.println("input the BedNum to move in:");
        String bednum = buf.readLine();

        //must make sure that the bed is available at this time
        String checksql = "SELECT COUNT(*) AS IFAVAILABLE FROM BED WHERE BedNum = " + bednum + "AND WardNum = " + wardnum + "AND Available <> 'Y'";
        checkstatement = con.createStatement();
        ResultSet result = checkstatement.executeQuery(checksql);
        if(result.next())
        {
            int count = result.getInt("IFAVAILABLE");
            System.out.println(count);
            if (count != 0)
            {
                System.out.println("this bed is not available!!");
                result.close();
                checkstatement.close();
                return;
            }
        }
        else
        {
            return;
        }
        //transaction
        con.setAutoCommit(false);
        //do modification
        //on Bed
        String bedsql = "UPDATE Bed SET Available = 'N' WHERE BedNum = " + bednum + " AND WardNum = " + wardnum;
        bedstatement = con.createStatement();
        bedstatement.executeUpdate(bedsql);
        //on check in
        String insertsql = "INSERT INTO CheckIn(PatientID, StartDate, BedNum, WardNum) values (" + pid + ",'" + startdate + "'," + bednum + "," + wardnum + ")";
        insertstatement = con.createStatement();
        insertstatement.executeUpdate(insertsql);
        //commit here
        con.commit();
    }
    catch(Throwable oops)
    {
        oops.printStackTrace();
        if (con != null)
        {
            try
            {
                System.err.println("Transaction is being rolled back");
                //rollback here
                con.rollback();
            }
            catch(SQLException excep)
            {
                excep.printStackTrace();
            }
        }
    }
    finally
    {
        if(insertstatement != null) {insertstatement.close();}
        if(bedstatement != null) {bedstatement.close();}
        if(checkstatement != null) {checkstatement.close();}
        con.setAutoCommit(true);
    }
}
//Update a new medical record for each check-in
//think it as check out
static void update_checkin_record(Connection con) throws SQLException
{
    Statement checkoutstatement = null;
    Statement bedstatement = null;
    Statement querystatement = null;
    try
    {
        //buffer
        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
        System.out.println("Now update a medical record of a check-in.");
        System.out.println("Entering the index information for the dest record:");
        System.out.println("input the Paitnet ID");
        String pid = buf.readLine();
        System.out.println("input the Start Date in dd-MMM-yyyy");
        String startdate = buf.readLine();
        System.out.println("now enter the checkout/end date:");
        String enddate = buf.readLine();

        //check if such check in record exists in table
        //and get its wardnum and bednum
        String querysql = "SELECT WardNum, BedNum From CheckIn Where PatientID = " + pid + "AND StartDate = '" + startdate +"' AND EndDate is null";
        querystatement = con.createStatement();
        ResultSet qresult = querystatement.executeQuery(querysql);
        int wardnum = -1 , bednum = -1;
        if(qresult.next())
        {
            wardnum = qresult.getInt("WardNum");
            bednum = qresult.getInt("BedNum");
        }
        else
        {
            System.out.println("no such un-checked out check-in-record!!");
            qresult.close();
            return;
        }
        qresult.close();

        //transaction
        con.setAutoCommit(false);
        //modify the checkin table
        String checkoutsql = "UPDATE CheckIn SET EndDate = '" + enddate + "' WHERE PatientID = " + pid + "AND StartDate = '" + startdate +"' AND enddate is null";
        checkoutstatement = con.createStatement();
        checkoutstatement.executeUpdate(checkoutsql);
        //modify the bed table
        String bedsql = "UPDATE Bed SET available = 'Y' WHERE WardNum = " + wardnum + " AND BedNum = " + bednum + "";
        bedstatement = con.createStatement();
        bedstatement.executeUpdate(bedsql);


        //commit this transaction
        con.commit();
    }
    catch(Throwable oops)
    {
        oops.printStackTrace();
        if (con != null)
        {
            try
            {
                System.err.println("Transaction is being rolled back");
                //rollback here
                con.rollback();
            }
            catch(SQLException excep)
            {
                excep.printStackTrace();
            }
        }
    }
    finally
    {
        if(checkoutstatement != null) {checkoutstatement.close();}
        if(bedstatement != null) {bedstatement.close();}
        con.setAutoCommit(true);
    }
}

//*****************************************************************************
//TASK group 4
//Return: the current usage status for all wards/beds; the number of patients per month; the ward-usage percentage.
//*****************************************************************************
//Return: 	the current usage status for all wards/beds;
static int get_usage_beds(Connection connection)
{
    try
    {
        String sql = "SELECT * FROM Bed";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        System.out.println("bednum  wardnum available");
        System.out.println("------  ------- ---------");
        while(result.next())
        {
           int bednum = result.getInt("BedNum");
           int wardnum = result.getInt("WardNum");
           String available = result.getString("Available");
           System.out.println(bednum + "        " + wardnum + "       " +available);
        }
        if(statement != null){statement.close();}
    }
    catch(Throwable oops)
    {

        oops.printStackTrace();
        return 0;
    }
    return 1;
}
//Return:   the number of patients per month;
static int number_patients_permonth(Connection connection)
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
        return 0;
    }
    return 1;
}
//Return:   the ward-usage percentage.
static int ward_usage_percent(Connection connection)
{
    try
    {
        String sql = "select * from (select MIN(AMount)/Max(Amount) as percentage from ((select count(BedNum) as amount from Bed where available <>'Y') UNION (select sum(capacity) from Ward)))";
        Statement statement = connection.createStatement();
        //statement.executeUpdate("update Bed set available='N'  where BedNum=1");
        ResultSet result = statement.executeQuery(sql);
        System.out.println("Percentage");
        System.out.println("------------");
        while(result.next())
        {
            float percent = result.getFloat("percentage");
            System.out.println(percent*100+"%");
        }
        if(statement != null){statement.close();}
    }
    catch(Throwable oops)
    {
        oops.printStackTrace();
        return 0;
    }
    return 1;
}

//*****************************************************************************
//*****************************************************************************
//*****************************************************************************

//********************
//task 4 by Hui
//********************
static private int jdbc_reports(Connection connection, String str)
{
try
{
	Statement statement = connection. createStatement();
	ResultSet result = statement.executeQuery(str);
            while(result.next())
            {
                result.getInt("");
            }
}

    catch(Throwable oops)
    {
        oops.printStackTrace();
        return 0;
    }
    return 1;
}

//	System.out.println("Please choose from the following options. Enter R to return to superior menu. Enter Q to quit the program.");
//	System.out.println("1. Generate reports: report the medical history for a given patient and for a certain time period (month/year).");
//	System.out.println("2. Return the current usage status for all wards/beds.");
//	System.out.println("3. Retrun the number of patients per month.");
//	System.out.println("4. Return the ward usage percentage.");
//	System.out.println("5. Return information on all the patients a doctor is currently responsible for.");
//	System.out.println("6. Return information on hospital staff grouped by their role.");

//Generate reports: report the medical history for a given patient and for a certain time period (month/year).

static void generate_reports_patient(Connection connection)
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
//SELECT P.* FROM (Patient P INNER JOIN MedicalRecord M ON P.ID = M.PatientID) INNER JOIN Staff S ON S.ID = M.DoctorID WHERE S.name = 'Lindsay Ball';

static void generate_reports_doctor(Connection connection)
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

//select * from staff where ID in (select ID from Doctor);

static void generate_staff_role(Connection connection)
{
try
{
        System.out.println("Return information on all hospital staff grouped by their rolls");

        String sql = "select ID as sid, NAME as sname from staff where ID in (select ID from Doctor)";

	Statement statement = connection.createStatement();
	ResultSet result = statement.executeQuery(sql);
            System.out.println("Doctor:");
	System.out.println("ID	Name");
	System.out.println("--	----");
	while(result.next())
	{
			String sid = result.getString("sid");
			String sname = result.getString("sname");
			System.out.println(sid + "	" + sname);
	}

	sql = "select ID as sid, NAME as sname from staff where ID in (select ID from Nurse)";
	statement = connection.createStatement();
	result = statement.executeQuery(sql);
            System.out.println("Nurse:");
	System.out.println("ID	Name");
	System.out.println("--	----");
	while(result.next())
	{
			String sid = result.getString("sid");
			String sname = result.getString("sname");
			System.out.println(sid + "	" + sname);
	}

	sql = "select ID as sid, NAME as sname from staff where ID in (select ID from BillingStaff)";
	statement = connection.createStatement();
	result = statement.executeQuery(sql);
            System.out.println("Billingstaff:");
	System.out.println("ID	Name");
	System.out.println("--	----");
	while(result.next())
	{
			String sid = result.getString("sid");
			String sname = result.getString("sname");
			System.out.println(sid + "	" + sname);
	}
	result.close();
}
catch(Throwable oops)
    {
        oops.printStackTrace();
    }
}

static int INSERTSTAFF(Connection connection, String table)
{
	try
	{
		//System.out.println("INSERT INTO Staff");
		BufferedReader br1 = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Doctor, Nurse, or BillingStaff?");
		String position = br1.readLine();
		System.out.println("You want to insert record for "+ position); 
		String Insert_val;
		String Insert_val2;
		System.out.println("Please Input the information you want to insert into staff");
		System.out.println("ID, Name, Age, Gender, Job Title, Phone, Address");
		Insert_val = br1.readLine();
		System.out.println("Please Input the information you want to insert into " + position);
		if(position.equals("Doctor"))
		{
			System.out.println("ID, Professional Title, Department");
		}
		else if(position.equals("Nurse"))
		{
			System.out.println("ID, Department");
		}
		else
		{
			System.out.println("ID");
		}
		Insert_val2 = br1.readLine();
		INSERTDATA(connection, "staff", Insert_val);
		INSERTDATA(connection, position, Insert_val2);
	}
	catch (IOException e)
	{
		System.out.println("Error reading from user");
	}
	return 0;				
}

static int UPDATESTAFF(Connection connection, String table)
{
	try
	{
		BufferedReader br2 = new BufferedReader (new InputStreamReader (System.in));
		//System.out.println("UPDATE Staff");
		System.out.println("Staff, Doctor, Nurse, or BillingStaff?");
		String position = br2.readLine();
		String Update_col;
		String Update_cond;
		if(position.equals("Doctor"))
		{
			System.out.println("ID, Professional Title, Department");
			System.out.println("Please Input the information you want to modify into " + position + "(SET VALUE:)");
			Update_col = br2.readLine();
			System.out.println("Please Input condition.");
			Update_cond = br2.readLine();
			UPDATEDATA(connection, position, Update_col, Update_cond);	
		}
		else if(position.equals("Nurse"))
		{
			System.out.println("ID, Department");
			System.out.println("Please Input the information you want to modify into " + position + "(SET VALUE:)");
			Update_col = br2.readLine();
			System.out.println("Please Input condition.");
			Update_cond = br2.readLine();
			UPDATEDATA(connection, position, Update_col, Update_cond);
		}
		else if(position.equals("Staff"))
		{
			System.out.println("ID");
			System.out.println("Please Input the information you want to modify into " + position + "(SET VALUE:)");
			Update_col = br2.readLine();
			System.out.println("Please Input condition.");
			Update_cond = br2.readLine();
			UPDATEDATA(connection, position, Update_col, Update_cond);
		}
		else
		{
			System.out.println("ID, Name, Age, Gender, Job Title, Phone, Address");
			System.out.println("Please Input the information you want to modify into " + position + "(SET VALUE:)");
			Update_col = br2.readLine();
			System.out.println("Please Input condition.");
			Update_cond = br2.readLine();
			UPDATEDATA(connection, position, Update_col, Update_cond);
		} 
	
	}
	catch (IOException e)
	{
		System.out.println("Error reading from user");
	}
	return 0;
}	

static void deleteStaff(Database myDB, String table)
{
	try {
		BufferedReader br3 = new BufferedReader (new InputStreamReader (System.in));
		//System.out.println("DELETE FROM Staff");
		System.out.println("Please Input the condition to allocate the record you want to delete.");
		String Delete_cond;
		Delete_cond = br3.readLine();
		myDB.delete("staff", Delete_cond);
	}catch (IOException e) {
		System.out.println("Error reading from user");
	}
}	

static void insertPatient(Database myDB, String table)
{
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

static int UPDATEPATIENT(Database myDB, String table)
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
	return 0;
}	

static int DELETEPATIENT(Database myDB, String table)
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
	return 0;
}

static int INSERTWARD(Database myDB, String table)
{
	try
	{
		BufferedReader br1 = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("The basic information about a ward include WardNum, Capacity, Charge and Inchargeof");
		System.out.println("Please Input the WardNum of the ward:");
		String Insert_val1 = br1.readLine();
		System.out.println("Pleas Input the Capacity of the Ward:");
		String Insert_val2 = br1.readLine();
		int Cap;
		String[] Capa;
		Capa = new String[8];
		if(Insert_val2.equals("1"))
		{
			Cap = 1;
			Capa[0] = "1";
		}
		else if(Insert_val2.equals("2"))
		{
			Cap = 2;
			Capa[0] = "1";
			Capa[1] = "2";
		}
		else if(Insert_val2.equals("4"))
		{
			Cap = 4;
			Capa[0] = "1";
			Capa[1] = "2";
			Capa[2] = "3";
			Capa[3] = "4";
		}
		else if(Insert_val2.equals("8"))
		{
			Cap = 8;
			Capa[0] = "1";
			Capa[1] = "2";
			Capa[2] = "3";
			Capa[3] = "4";
			Capa[4] = "5";
			Capa[5] = "6";
			Capa[6] = "7";
			Capa[7] = "8";
		}
		else
		{
			System.out.println("Unable to add this kind of ward!");
			return 0;
		}
		System.out.println("Charge?");
		String Insert_val3 = br1.readLine();
		System.out.println("Nurse ID Inchargeof Ward:");
		String Insert_val4 = br1.readLine();
		String Content1 = Insert_val1 + ", " + Insert_val2 + ", " + Insert_val3;
		System.out.println(Content1);
		String Content2 = Insert_val1 + ", " +Insert_val4;
		INSERTDATA(connection, "ward", Content1);
		INSERTDATA(connection, "InChargeOF", Content2);
		for(int n = 0; n<Cap; n++)
		{
			String temp =  Capa[n] + ", " + Insert_val1 + ", 'Y'";
			System.out.println(temp);
			INSERTDATA(connection, "BED", temp); 
		}
	}
	catch (IOException e)
	{
		System.out.println("Error reading from user");
	}
	return 0;				
}

static int UPDATEWARD(Database myDB, String table)
{
	try
	{
		BufferedReader br2 = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Please Input the SET Value");
		String Update_val = br2.readLine();
		System.out.println("Please Input the Cond");
		String Update_cond = br2.readLine();
		UPDATEDATA(connection, "ward", Update_val, Update_cond);
	}
	catch (IOException e)
	{
		System.out.println("Error reading from user");
	}
	return 0;				
}

static int DELETEWARD(Database myDB, String table)
{
	try
	{
		BufferedReader br3 = new BufferedReader (new InputStreamReader (System.in));
		//String WorB = br3.readLine();
		System.out.println("Please input the condition \"wardnum ==\":");
		String Del_cond = br3.readLine();
		
		DELETEDATA(connection, "ward", Del_cond);
	}
	catch (IOException e)
	{
		System.out.println("Error reading from user");
	}
	return 0;				
}
*/



