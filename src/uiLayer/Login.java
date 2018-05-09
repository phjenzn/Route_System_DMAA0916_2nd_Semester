package uiLayer;

import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import controlLayer.UserCtrl;

@SuppressWarnings("serial")
public class Login extends JPanel
{
	private JTextField		textField;
	private JPasswordField	passwordField;
	private UserCtrl		uCtrl	= UserCtrl.getInstance();
	private FCL				myparent;
	private JLabel			lbStatus;

	/**
	 * Create the panel.
	 */
	public Login(FCL myparent)
	{
		this.myparent = myparent;
		
		textField = new JTextField();
		textField.setBounds(87, 13, 144, 22);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(87, 88, 144, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				checkLogin(textField.getText(), passwordField.getPassword());
			}
		});

		passwordField = new JPasswordField();
		passwordField.setBounds(87, 53, 144, 22);

		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(12, 16, 63, 16);

		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(12, 56, 60, 16);
		setLayout(null);
		add(textField);
		add(btnNewButton);
		add(passwordField);
		add(lblNewLabel);
		add(lblNewLabel_1);

		lbStatus = new JLabel("");
		lbStatus.setBounds(12, 126, 428, 16);
		add(lbStatus);

	}

	private void checkLogin(String username, char[] password)
	{
		if (username.length() <= 2)
		{
			lbStatus.setText("Username needs to be longer than, or equal to 2 characters.");
			return;
		}

		if (String.valueOf(password).length() < 4)
		{
			lbStatus.setText("Passwords needs to be longer than, or equal to 4 characters.");
			return;
		}

		if (uCtrl.login(username, String.valueOf(password)))
		{
			myparent.buildMenu();
			myparent.changePanel("Router");
			/**
			 * Cleans up the login page, in case a user logout. 
			 */
			lbStatus.setText("");
			textField.setText("");
			passwordField.setText("");
		} else
		{
			lbStatus.setText("The combination of username and password did not match.");
			return;
		}
	}

	public JPanel getPanel()
	{
		return this;
	}
}
