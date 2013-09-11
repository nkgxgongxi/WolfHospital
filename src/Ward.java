import java.util.*;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Ward implements Operable {
	static String tableName = "Ward";
	public void insert(Database myDB) throws SQLException {
		Scanner br1 = new Scanner (new InputStreamReader (System.in));
		System.out.println("The basic information about a ward include WardNum, Capacity, Charge and Inchargeof");
		System.out.println("Please Input the WardNum of the ward:");
		String insertValue1 = br1.nextLine();
		System.out.println("Pleas Input the Capacity of the Ward:");
		String insertValue2 = br1.nextLine();
		int capacity = Integer.parseInt(insertValue2);
		ArrayList<String> bedNum = new ArrayList<String>();
		for(int i = 1; i <= capacity; i++)
			bedNum.add(String.valueOf(i));
		System.out.println("Charge?");
		String insertValue3 = br1.nextLine();
		System.out.println("Nurse ID Inchargeof Ward:");
		String insertValue4 = br1.nextLine();
		String Content1 = insertValue1 + ", " + insertValue2 + ", " + insertValue3;
		System.out.println(Content1);
		String Content2 = insertValue1 + ", " +insertValue4;
		myDB.insert(tableName, Content1);
		myDB.insert("InChargeOF", Content2);
		for(int n = 0; n < capacity; n++)
		{
			String content3 =  bedNum.get(n) + ", " + insertValue1 + ", 'Y'";
			myDB.insert("Bed", content3); 
		}
		br1.close();				
	}
	
	public void update(Database myDB) throws SQLException {
		Scanner br2 = new Scanner (new InputStreamReader (System.in));
		System.out.println("Please Input the SET Value");
		String updateValue = br2.nextLine();
		System.out.println("Please Input the Cond");
		String updateCond = br2.nextLine();
		myDB.update(tableName, updateValue, updateCond);
		br2.close();		
	}
	
	public void delete(Database myDB) throws SQLException {
		Scanner br3 = new Scanner (new InputStreamReader (System.in));
		System.out.println("Please input the condition \"wardnum ==\":");
		String condition = br3.nextLine();
		myDB.delete(tableName, condition);	
		br3.close();
	}
	
	public void getUsage(Database myDB) throws SQLException {
        String source = "(select MIN(AMount)/Max(Amount) as"
        		+ " percentage from ((select count(BedNum) as amount from"
        		+ " Bed where available <>'Y') UNION (select sum(capacity) from Ward)))";
        String col = "*";
        ResultSet result = myDB.select(source, col);
        System.out.println("Percentage");
        System.out.println("------------");
        while(result.next()) {
            float percent = result.getFloat("percentage");
            System.out.println(percent*100 + "%");
        }
	}
	
	public void check(Database myDB) throws SQLException {
        String col = "Distinct WardNum";
        String tableName = "Bed"; 
        String condition = "Available <> 'N' AND Available <> 'R'";
        ResultSet result = myDB.select(tableName, col, condition);
        System.out.println("wardnum");
        System.out.println("-------");
        while(result.next()) {
           int wardNum = result.getInt("WardNum");
           System.out.println(wardNum);
        }
	}
}
