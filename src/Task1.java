import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task1 {
	public static void connector() {
		Scanner br = new Scanner (new InputStreamReader (System.in));
		String temp;
		String com;
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("1", "Staff");
		options.put("2", "Patient");
		options.put("3", "Ward/Bed");
		options.put("4", "Reservation");
		options.put("q", "Quit");
		options.put("r", "Upper Level");
		boolean flag = true;
		try {
			while (flag) {
				System.out.println("Please choose from the following options. Enter R to return to superior menu. Enter Q to quit the program.");
				System.out.println("1. Enter/Update/Delete basic information about staff.");
				System.out.println("2. Enter/Update/Delete basic information about patient.");
				System.out.println("3. Enter/Update/Delete basic information about wards/beds.");
				System.out.println("4. Check/Assign/Reserve/Release bed for a patient according to the request.");
				System.out.println("Enter your choice:");
				temp = br.nextLine();
		        while(temp.isEmpty())
		        {
		            continue;
		        }
				char c = temp.charAt(0);
		
				if(temp.length() > 1) {
					System.out.println("Invalid input! Please enter a number from 1 to 4. Enter R to return to superior menu. Enter Q to quit the program.");
				}
				
				switch (c) {
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
}	
	}
}
