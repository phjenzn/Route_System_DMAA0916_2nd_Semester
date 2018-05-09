package uiLayer;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import controlLayer.UserCtrl;


@SuppressWarnings("serial")
public class Logout extends JPanel
{

	private UserCtrl	uCtrl	= UserCtrl.getInstance();

	/**
	 * Create the panel.
	 */
	public Logout(FCL myparent)
	{
		setBackground(Color.WHITE);
		setLayout(null);

		JButton ExitButt = new JButton("Exit Program");
		ExitButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				myparent.quit();
			}
		});
		ExitButt.setBounds(162, 236, 114, 25);
		add(ExitButt);

		JButton LogOutButt = new JButton("Logout");
		LogOutButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				uCtrl.logout();
				myparent.changePanel("Login");
				myparent.buildMenu();
			}
		});
		LogOutButt.setBounds(162, 198, 114, 25);
		add(LogOutButt);

	}

	public JPanel getPanel()
	{
		return this;
	}
}
