import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestBase {
	public static void main(String[] args){
		try{
			LogIn myLogIn = new LogIn();
			myLogIn.setSize(300, 100);
			myLogIn.setVisible(true);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
