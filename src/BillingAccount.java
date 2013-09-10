import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


public class BillingAccount implements Operable {
	public void insert(Database myDB) throws SQLException {
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
	
	public void update(Database myDB) throws SQLException
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
}
