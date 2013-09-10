import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Bed {
	public void check(Database myDB) throws SQLException {
	    try {
	        String col = "WardNum, BedNum";
	        String tableName = "Bed";
	        String condition =  "Available <> 'N' AND Available <> 'R'";
	        ResultSet result = myDB.select(tableName, col, condition);
	        System.out.println("wardnum  bednum");
	        System.out.println("-------  ------");
	        while(result.next()) {
	           int bednum = result.getInt("BedNum");
	           int wardnum = result.getInt("WardNum");
	           System.out.println(wardnum + "       " +bednum);
	        }
	    }catch(Throwable oops) {
	        oops.printStackTrace();
	    }
	}
	public void reserve(Database myDB) throws SQLException {
	    try {
	        System.out.println("Choose the ward and bed you want to reserve:");
	        Scanner buf = new Scanner (new InputStreamReader (System.in));
	        System.out.println("Please input the ward number:");
	        String wardNum = buf.nextLine();
	        System.out.println("Please input the bed number:");
	        String bedNum = buf.nextLine();
	        String tableName = "Bed";
	        String modification = "Available='R'";
	        String condition = "WardNum = "+ wardNum + " AND BedNum = " + bedNum;
	        myDB.update(tableName, modification, condition);
	        System.out.println("Reserve succeed!");
	        buf.close();
	    }catch(Throwable oops) {
	        oops.printStackTrace();
	    }
	}
	//Release wards/beds
	static void releaseBed(Database myDB) throws SQLException {
	    try {
	        System.out.println("Choose the ward and bed you want to release:");
	        BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
	        System.out.println("Please input the ward number:");
	        String wardNum = buf.readLine();
	        System.out.println("Please input the bed number:");
	        String bedNum = buf.readLine();
	        String condition =" WardNum = "+ wardNum + " AND BedNum = " + bedNum;
	        myDB.update("Bed", "Available='Y'", condition);
	        System.out.println("Release succeed!");
	    }catch(Throwable oops) {
	        oops.printStackTrace();
	    }
	}
}
