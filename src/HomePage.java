//import java.awt.*;
//import java.awt.event.*;

import javax.swing.*;

public class HomePage extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	JFrame myFrame;
//	JMenuItem myStart;
	public HomePage(String name){
		myFrame = new JFrame("Welcome To " +  name + " System");
		myFrame.setSize(500, 500);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		myStart = new JMenuItem("Start Query");
//		myStart.addActionListener(new ActionListener(){
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0){
//				InputDialog query = new InputDialog(DatabaseUITest.this, "Username:");
//			}
//		});
//		myFrame.add(myStart);
	}
	public void Demo(){
		myFrame.setVisible(true);
	}
}
