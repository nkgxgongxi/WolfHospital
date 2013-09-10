import java.io.*;
import java.util.Properties;


public class Logger {
	static String fileName = null;
	public static void log(String message) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("config.properties"));
			PrintWriter out = new PrintWriter(new FileWriter(fileName, true));
			out.println(message);
			out.println();
			out.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void init() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("config.properties"));
			fileName = prop.getProperty("logger");
			File f = new File(fileName);
			if(f.exists())
				f.delete();
			f.createNewFile();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
