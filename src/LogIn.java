import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LogIn extends JFrame implements ActionListener{
	JButton submit;
	JPanel myPanel;
	JLabel label1, label2;
	final JTextField text1, text2;
	static int counter = 0;
	LogIn(){
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);
		
		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JTextField(15);
		
		submit = new JButton("Submit");
		
		myPanel = new JPanel(new GridLayout(3,1,5,0));
		myPanel.add(label1);
		myPanel.add(text1);
		myPanel.add(label2);
		myPanel.add(text2);
		myPanel.add(submit);
		add(myPanel, BorderLayout.CENTER);
		submit.addActionListener(this);
		setTitle("LOGIN");
		counter++;
	}

	public void actionPerformed(ActionEvent arg0){
		String username = text1.getText();
		String password = text2.getText();
		if(username.equals("xgong") && password.equals("gxgongxi")){
			HomePage myHomePage = new HomePage("WolfHospital");
			DBConnection.Create(username, password);
			myHomePage.Demo();
			this.setVisible(false);
			System.out.println(myHomePage.isActive());
//			if(!myHomePage.isActive())
//				DBConnection.Close();
			
		}
		else{
			JOptionPane.showMessageDialog(this, "Incorrect login or password", 
					"Error", JOptionPane.ERROR_MESSAGE);
			if(counter < 3)
				new LogIn();
		}
	}
}
