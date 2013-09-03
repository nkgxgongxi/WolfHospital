import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DatabaseUITest extends JFrame {
	JFrame myFrame;
	JMenuItem myStart;
	public DatabaseUITest(String Title){
		myFrame = new JFrame(Title);
		myFrame.setSize(500, 500);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myStart = new JMenuItem("Start Query");
		myStart.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0){
				InputDialog query = new InputDialog(DatabaseUITest.this, "Username:");
			}
		});
		myFrame.add(myStart);
	}
	public void Demo(){
		myFrame.setVisible(true);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseUITest myTest = new DatabaseUITest("WolfHospital Interface");
		myTest.Demo();

	}

}
