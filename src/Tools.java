import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Tools {
	public static void printDate() {
		DateFormat myFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date current = new Date();
		Logger.log(myFormat.format(current));
	}
}
