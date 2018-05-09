package uiLayer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controlLayer.UserCtrl;
import dbLayer.DBCon;
import uiLayer.TruckUI;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Label;
import java.awt.Color;

public class FCL extends JFrame
{
	private static final long	serialVersionUID	= 1L;

	// Layout
	private JFrame				frame;
	private JPanel				MenuPanel;
	private JPanel				TitlePanel;
	private JPanel				StatusPanel;
	private JPanel				ContentPanel;
	private CardLayout			layout;

	// Henter Panel classes..
	private Home				home;
	private Kunder				kunder;
	private Router				router;
	private Login				login;
	private Logout				logout;
	private Order				order;
	private TruckUI				truck;

	// SQL init
	Connection					dbCon				= DBCon.getInstance().getConnection();
	UserCtrl					uCtrl				= UserCtrl.getInstance();

	// Menu
	private String				menuItems[]			= { "Home", "Router", "Kunder", "Order", "Truck", "Logout"};
	private List<JButton>		menu				= new ArrayList<JButton>();

	private JLabel				titleLogo;
	private Label				dbStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try
				{
					FCL window = new FCL();
					window.frame.setVisible(true);

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FCL()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		// Layout
		frame = new JFrame();
		TitlePanel = new JPanel();
		TitlePanel.setBackground(Color.WHITE);
		MenuPanel = new JPanel();
		MenuPanel.setBackground(SystemColor.textHighlight);
		StatusPanel = new JPanel();
		ContentPanel = new JPanel();
		layout = new CardLayout();

		
		frame.getContentPane().add(StatusPanel, BorderLayout.SOUTH);
		frame.getContentPane().add(MenuPanel, BorderLayout.WEST);
		frame.getContentPane().add(TitlePanel, BorderLayout.NORTH);

		// STATUS - DATABASE
		// Opretter ny Label, som viser status på database forbindelsen.
		String test = (DBCon.getInstance().testConnection() ? "Forbundet" : "Ikke forbundet!"); // Kører
																								// test
																								// af
																								// forbindelsen
		dbStatus = new Label("Database forbindelse: " + test);
		StatusPanel.add(dbStatus);

		// LOGO
		// Opretter ny JLabel og indsætter billede som logo.
		titleLogo = new JLabel("");
		titleLogo.setIcon(new ImageIcon(FCL.class.getResource("/img/fodercentralen-limfjorden.png")));
		TitlePanel.add(titleLogo);

		ContentPanel.setLayout(layout);

		// Tilføjer til card.
		home = new Home();
		kunder = new Kunder(ContentPanel, layout);
		logout = new Logout(this);
		router = new Router();
		order = new Order();
		truck = new TruckUI();
		login = new Login(this);
		
		ContentPanel.add(login.getPanel(), "Login");
		ContentPanel.add(home.getPanel(), "Home");
		ContentPanel.add(kunder.getPanel(), "Kunder");
		ContentPanel.add(router.getPanel(), "Router");
		ContentPanel.add(order.getPanel(), "Order");
		ContentPanel.add(truck.getPanel(), "Truck");
		ContentPanel.add(logout.getPanel(), "Logout");

		frame.getContentPane().add(ContentPanel, BorderLayout.CENTER);

		MenuPanel.setLayout(new BoxLayout(MenuPanel, BoxLayout.Y_AXIS));

		frame.setBounds(100, 100, 570, 456);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		frame.pack();

		WindowListener exitListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e)
			{
				quit();
			}
		};
		frame.addWindowListener(exitListener);

	}

	
	
	public void buildMenu()
	{
		if (uCtrl.getLoggedInAsuser().isAllowed())
		{
			for (String s : menuItems)
			{
				JButton btn = new JButton(s);
				menu.add(btn);
				menu.get(menu.size() - 1).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						changePanel(s);
						
					}
				});

				MenuPanel.add(menu.get(menu.size() - 1));
			}
		} else
		{
			for (JButton b : menu)
			{
				MenuPanel.remove(b);
			}

			menu.clear();
		}
		
	}

	public void changePanel(String pName)
	{
		layout.show(ContentPanel, pName);
	}
	

	public void quit()
	{
		int confirm = JOptionPane.showConfirmDialog(frame, "Exit!", "Do you really want to exit this program?",
				JOptionPane.YES_NO_OPTION);

		if (confirm == 0)
		{
			/**
			 * Lets cleanup some code: Close DB connections Kill threads etc.
			 * And then... exit the program.
			 */
			System.exit(0);
		}
	}
}