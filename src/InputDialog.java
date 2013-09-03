import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InputDialog extends JDialog{
	private DatabaseUITest myFrame;
	private JLabel title;
	private JTextField input;
	public InputDialog(DatabaseUITest myInterface, String title){
		super(myInterface);
		setSize(150,150);
		this.myFrame = myInterface;
		this.title = new JLabel(title);
		this.input = new JTextField(100);
		
		setLocationRelativeTo(null);
		init();
		addListener();
		setVisible(true);
	}
	
	public void init(){
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		container.add(title);
		container.add(input);
	}
	
	public void addListener(){
		QueryListener ql = new QueryListener();
	}
	public class QueryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent evnet) {
			String queryStr = input.getText();
			if (queryStr!=null && !queryStr.equals("")){
				JOptionPane.showMessageDialog(myFrame, queryStr);
			}
			else{
				JOptionPane.showMessageDialog(myFrame, "Invalid Input.");
			}
		}
	}
}
