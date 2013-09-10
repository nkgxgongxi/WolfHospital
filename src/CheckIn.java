import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CheckIn implements Operable {
	static String tableName = "CheckIn";
	public void insert(Database myDB) throws SQLException {
	    try {

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
	        String col = "COUNT(*) AS IFAVAILABLE";
	        String tName = "BED";
	        String condition = "BedNum = " + bednum + "AND WardNum = " + wardnum + "AND Available <> 'Y'";
	        ResultSet result = myDB.select(tName, col, condition);
	        if(result.next()) {
	            int count = result.getInt("IFAVAILABLE");
	            System.out.println(count);
	            if (count != 0) {
	                System.out.println("this bed is not available!!");
	                result.close();
	            }
	        }
	        else
	        {
	            return;
	        }
	        //transaction
	        myDB.conn.setAutoCommit(false);
	        //do modification
	        //on Bed
	        String tName2 = "Bed";
	        String modification = "Available = 'N'";
	        String condition2 = "BedNum = " + bednum + " AND WardNum = " + wardnum;
	        myDB.update(tName2, modification, condition2);
	        //on check in
	        String tName3 = "CheckIn(PatientID, StartDate, BedNum, WardNum)"; 
	        String values = pid + ",'" + startdate + "'," + bednum + "," + wardnum;
	        myDB.insert(tName3, values);
	        //commit here
	        myDB.conn.commit();
	    }
	    catch(Throwable oops)
	    {
	        oops.printStackTrace();
	        if (myDB.conn != null)
	        {
	            try
	            {
	                System.err.println("Transaction is being rolled back");
	                //rollback here
	                myDB.conn.rollback();
	            }
	            catch(SQLException excep)
	            {
	                excep.printStackTrace();
	            }
	        }
	    }finally {
	    	myDB.conn.setAutoCommit(true);
	    }
	}
	public void update(Database myDB) throws SQLException {
	    try {
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
	        String col = "WardNum, BedNum";
	        String condition = "PatientID = " + pid + "AND StartDate = '" + startdate +"' AND EndDate is null";
	        ResultSet qresult = myDB.select(tableName, col, condition);
	        int wardnum = -1 , bednum = -1;
	        if(qresult.next()) {
	            wardnum = qresult.getInt("WardNum");
	            bednum = qresult.getInt("BedNum");
	        }
	        else {
	            System.out.println("no such un-checked out check-in-record!!");
	            qresult.close();
	            return;
	        }
	        qresult.close();

	        //transaction
	        myDB.conn.setAutoCommit(false);
	        //modify the checkin table

	        String col2 = "EndDate = '" + enddate + "'";
	        String condition2 = "PatientID = " + pid + "AND StartDate = '" + startdate +"' AND enddate is null";
	        myDB.update(tableName, col2, condition2);
	        
	        //modify the bed table
	        String tName3 = "Bed"; 
	        String col3 = "available = 'Y'";
	        String condition3 = "WardNum = " + wardnum + " AND BedNum = " + bednum;
	        myDB.update(tName3, col3, condition3);
	        //commit this transaction
	        myDB.conn.commit();
	    }
	    catch(Throwable oops) {
	        oops.printStackTrace();
	        if (myDB.conn != null) {
	            try {
	                System.err.println("Transaction is being rolled back");
	                //rollback here
	                myDB.conn.rollback();
	            }catch(SQLException excep) {
	                excep.printStackTrace();
	            }
	        }
	    }
	    finally {
	        myDB.conn.setAutoCommit(true);
	    }
	}
}
