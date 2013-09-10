import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Ward implements Operable {
	public void insert(Database myDB) throws SQLException {
		try {
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
			else {
				System.out.println("Unable to add this kind of ward!");
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
		}catch (IOException e) {
			System.out.println("Error reading from user");
		}				
	}
	
	public void update(Database myDB) throws SQLException {
		try {
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
	}
	
	public void delete(Database myDB)
	{
		try
		{
			BufferedReader br3 = new BufferedReader (new InputStreamReader (System.in));
			//String WorB = br3.readLine();
			System.out.println("Please input the condition \"wardnum ==\":");
			String Del_cond = br3.readLine();
			
			DELETEDATA(connection, "ward", Del_cond);
		}catch (IOException e){
			System.out.println("Error reading from user");
		}			
	}
	
	public void getUsage(Connection connection)
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
	    }
	}
	
	public void check(Database myDB) throws SQLException {
	    try {
	        String col = "Distinct WardNum";
	        String tableName = "Bed"; 
	        String condition = "Available <> 'N' AND Available <> 'R'";
	        ResultSet result = myDB.select(tableName, col, condition);
	        System.out.println("wardnum");
	        System.out.println("-------");
	        while(result.next()) {
	           int wardnum = result.getInt("WardNum");
	           System.out.println(wardnum);
	        }
	    }catch(Throwable oops) {
	        oops.printStackTrace();
	    }
	}
}
