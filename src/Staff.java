import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Staff implements Operable {
	static String tableName = "Staff"; 
	public void insert(Database myDB) throws SQLException
	{
		try
		{
			//System.out.println("INSERT INTO Staff");
			BufferedReader br1 = new BufferedReader (new InputStreamReader (System.in));
			System.out.println("Doctor, Nurse, or BillingStaff?");
			String position = br1.readLine();
			System.out.println("You want to insert record for "+ position); 
			String val;
			String val2;
			System.out.println("Please Input the information you want to insert into staff");
			System.out.println("ID, Name, Age, Gender, Job Title, Phone, Address");
			val = br1.readLine();
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
			val2 = br1.readLine();
			myDB.insert(tableName, val);
			myDB.insert(position, val2);
		}
		catch (IOException e)
		{
			System.out.println("Error reading from user");
		}			
	}
	
	public void update(Database myDB) throws SQLException
	{
		try
		{
			BufferedReader br2 = new BufferedReader (new InputStreamReader (System.in));
			//System.out.println("UPDATE Staff");
			System.out.println("Staff, Doctor, Nurse, or BillingStaff?");
			String position = br2.readLine();
			String Update_col;
			String Update_cond;
			if(position.equals("Doctor")) {
				System.out.println("ID, Professional Title, Department");
				System.out.println("Please Input the information you want to modify into " + position + "(SET VALUE:)");
				Update_col = br2.readLine();
				System.out.println("Please Input condition.");
				Update_cond = br2.readLine();
			}
			else if(position.equals("Nurse")) {
				System.out.println("ID, Department");
				System.out.println("Please Input the information you want to modify into " + position + "(SET VALUE:)");
				Update_col = br2.readLine();
				System.out.println("Please Input condition.");
				Update_cond = br2.readLine();
			}
			else if(position.equals("Staff")) {
				System.out.println("ID");
				System.out.println("Please Input the information you want to modify into " + position + "(SET VALUE:)");
				Update_col = br2.readLine();
				System.out.println("Please Input condition.");
				Update_cond = br2.readLine();
			}
			else {
				System.out.println("ID, Name, Age, Gender, Job Title, Phone, Address");
				System.out.println("Please Input the information you want to modify into " + position + "(SET VALUE:)");
				Update_col = br2.readLine();
				System.out.println("Please Input condition.");
				Update_cond = br2.readLine();
			} 
			myDB.update(position, Update_col, Update_cond);	
		
		}
		catch (IOException e)
		{
			System.out.println("Error reading from user");
		}
	}	
	
	public void deleteStaff(Database myDB) throws SQLException
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

	public void genRole(Database myDB) {
		try {
	        System.out.println("Return information on all hospital staff grouped by their rolls");

	        String col = "ID as sid, NAME as sname";
	        String condition = "ID in (select ID from Doctor)";

			ResultSet result = myDB.select(tableName, col, condition);
		    System.out.println("Doctor:");
			System.out.println("ID	Name");
			System.out.println("--	----");
			while(result.next())
			{
					String sid = result.getString("sid");
					String sname = result.getString("sname");
					System.out.println(sid + "	" + sname);
			}
	
			col = "ID as sid, NAME as sname";
			condition = "ID in (select ID from Nurse)";
			result = myDB.select(tableName, col, condition);
		    System.out.println("Nurse:");
			System.out.println("ID	Name");
			System.out.println("--	----");
			while(result.next())
			{
				String sid = result.getString("sid");
				String sname = result.getString("sname");
				System.out.println(sid + "	" + sname);
			}
	
			col = "ID as sid, NAME as sname";
			condition = "ID in (select ID from BillingStaff)";
			result = myDB.select(tableName, col, condition);
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
		}catch(Throwable oops) {
	        oops.printStackTrace();
	    }
	}
}
