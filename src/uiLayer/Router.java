package uiLayer;

import javax.swing.JPanel;

import controlLayer.DestinationCtrl;
import controlLayer.MatrixCtrl;
import controlLayer.RouteCtrl;
import modelLayer.Destination;
import modelLayer.Route;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.List;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class Router extends JPanel
{
	java.util.List<Route>		comboBoxList	= new ArrayList<Route>();
	java.util.List<Destination>	rlist			= new ArrayList<Destination>();
	java.util.List<Destination>	mlist			= new ArrayList<Destination>();
	java.util.List<Destination>	olist			= new ArrayList<Destination>();
	java.util.List<Destination>	sslist			= new ArrayList<Destination>();

	JComboBox<Route>			comboBox;
	JComboBox<Destination>		comboBox_1;
	JComboBox<Destination>		comboBox_2;
	List						optimeringList;
	List						muligeList;
	List						routeList;

	JLabel						OptimizedRouteLength;
	JLabel						CurrentRouteLength;

	RouteCtrl					rCtrl			= RouteCtrl.getInstance();
	DestinationCtrl				dCtrl			= DestinationCtrl.getInstance();
	MatrixCtrl					mCtrl			= MatrixCtrl.getInstance();

	/**
	 * Create the panel.
	 */
	public Router()
	{
		setLayout(null);
		routeList = new List();
		routeList.setBounds(12, 44, 240, 548);
		add(routeList);

		muligeList = new List();
		muligeList.setBounds(325, 44, 224, 548);
		add(muligeList);

		optimeringList = new List();
		optimeringList.setBounds(555, 44, 240, 548);
		add(optimeringList);

		CurrentRouteLength = new JLabel("Nuv\u00E6rende route l\u00E6ngde:");
		CurrentRouteLength.setBounds(325, 674, 224, 16);
		add(CurrentRouteLength);

		OptimizedRouteLength = new JLabel("Optimeret route l\u00E6ngde:");
		OptimizedRouteLength.setBounds(325, 710, 224, 16);
		add(OptimizedRouteLength);

		comboBox = new JComboBox<Route>();
		comboBox_1 = new JComboBox<Destination>();
		comboBox_2 = new JComboBox<Destination>();

		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				// Start Dest
			}
		});
		comboBox_1.setBounds(74, 598, 178, 22);
		add(comboBox_1);

		comboBox_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				// Slut Dest
			}
		});
		comboBox_2.setBounds(74, 625, 178, 22);
		add(comboBox_2);

		popuplateRoutes(); // ComboBox
		populateSSLister(); // Start og Slut comboBoxene og ArrayList
		comboBox.setBounds(91, 13, 44, 22);
		comboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					populateMulige(); // Destinationer uden route
					populateRoute(); // Distinations for valgte route
					setStartSlutComboBoxe();
					updateRouteLengths();
				}
			}
		});
		add(comboBox);

		JLabel lblNewLabel = new JLabel("V\u00E6lge route:");
		lblNewLabel.setBounds(12, 16, 78, 16);
		add(lblNewLabel);

		JLabel lblNewLabel_4 = new JLabel("Start:");
		lblNewLabel_4.setBounds(12, 601, 56, 16);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Slut:");
		lblNewLabel_5.setBounds(12, 628, 44, 16);
		add(lblNewLabel_5);

		JLabel lblNewLabel_1 = new JLabel("Mangler route:");
		lblNewLabel_1.setBounds(325, 16, 224, 16);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Forslag Optimering:");
		lblNewLabel_2.setBounds(555, 16, 240, 16);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("eller");
		lblNewLabel_3.setBounds(141, 16, 33, 16);
		add(lblNewLabel_3);

		/**
		 * Buttons
		 */
		JButton btnNewButton = new JButton("Opret ny");
		btnNewButton.setBounds(170, 12, 81, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				Route r = null;
				r = rCtrl.createRoute();

				if (r != null)
				{
					comboBoxList.add(r);
					comboBox.addItem(r);

					comboBox.setSelectedIndex(comboBox.getItemCount() - 1);
					updateRouteLengths();
				}
			}
		});
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Gem Route");
		btnNewButton_1.setBounds(12, 741, 240, 25);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Route r = comboBoxList.get(comboBox.getSelectedIndex());
				r.setCurrentRoute(rlist);
				r.setStart(sslist.get(comboBox_1.getSelectedIndex()));
				r.setSlut(sslist.get(comboBox_2.getSelectedIndex()));
				saveRoute(r);
				// System.out.println("Den nye gemte route er: " +
				// rCtrl.getRouteDistance(r) + " meter lang!");

			}
		});
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Benyt Optimering");
		btnNewButton_2.setBounds(12, 706, 240, 25);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				/**
				 * Hvis olisten ikke er tom.
				 */
				if (!olist.isEmpty())
				{
					rlist.clear();
					rlist.addAll(olist);
					olist.clear();
					optimeringList.removeAll();
					routeList.removeAll();

					for (Destination d : rlist)
					{
						routeList.add(d.toString());
					}
					
					updateRouteLengths();

				}

			}
		});
		add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("OP");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_3.setBounds(258, 232, 61, 37);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (routeList.getItemCount() > 0 && routeList.getSelectedIndex() > 0)
				{
					int Id = routeList.getSelectedIndex();
					Destination a = rlist.get(Id);
					Destination b = rlist.get(Id - 1);

					rlist.set(Id, b);
					rlist.set(Id - 1, a);

					routeList.replaceItem(b.toString(), Id);
					routeList.replaceItem(a.toString(), Id - 1);

					updateRouteLengths();

				}
			}
		});
		add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("NED");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_4.setBounds(258, 282, 61, 37);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (routeList.getItemCount() > 0 && routeList.getSelectedIndex() >= 0
						&& routeList.getSelectedIndex() < routeList.getItemCount())
				{
					int Id = routeList.getSelectedIndex();
					Destination a = rlist.get(Id);
					Destination b = rlist.get(Id + 1);

					rlist.set(Id, b);
					rlist.set(Id + 1, a);

					routeList.replaceItem(b.toString(), Id);
					routeList.replaceItem(a.toString(), Id + 1);

					updateRouteLengths();

				}
			}
		});
		add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("---->");
		btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_5.setBounds(258, 332, 61, 37);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (routeList.getItemCount() > 0 && routeList.getSelectedIndex() >= 0)
				{
					olist.clear();
					optimeringList.removeAll();
					int Id = routeList.getSelectedIndex();
					Destination d = rlist.remove(Id);
					routeList.remove(Id);
					mlist.add(d);
					muligeList.add(d.toString());
					updateRouteLengths();
				}
			}
		});

		add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("<----");
		btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton_6.setBounds(258, 382, 61, 37);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{

				if (muligeList.getItemCount() > 0 && muligeList.getSelectedIndex() >= 0)
				{
					olist.clear();
					optimeringList.removeAll();
					int Id = muligeList.getSelectedIndex();
					Destination d = mlist.remove(Id);
					muligeList.remove(Id);
					rlist.add(d);
					routeList.add(d.toString());
					updateRouteLengths();

				}

			}
		});
		add(btnNewButton_6);

		JLabel lblNewLabel_6 = new JLabel("K\u00F8ret\u00F8j:");
		lblNewLabel_6.setBounds(12, 655, 56, 16);
		add(lblNewLabel_6);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				// Køre tøj
			}
		});
		comboBox_3.setBounds(74, 652, 178, 22);
		add(comboBox_3);

		JButton btnOptimize = new JButton("Optimize");
		btnOptimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				optimize();
			}
		});
		btnOptimize.setBounds(555, 597, 240, 25);
		add(btnOptimize);

		/**
		 * Henter router til ComboBoxen Så eventhandleren ikke går amok for hver
		 * item vi tilføjer
		 */

		/**
		 * Henter de sidste data elementer
		 */
		populateMulige(); // Destinationer uden route
		populateSSLister(); // Start Slut DropDown og ArrayList
		populateRoute(); // Distinations for valgte route
		setStartSlutComboBoxe();
		updateRouteLengths();
	}

	public void populateSSLister()
	{
		sslist.clear();
		comboBox_1.removeAllItems();
		comboBox_2.removeAllItems();

		sslist.addAll(dCtrl.getAllDestinations());

		for (Destination d : sslist)
		{
			comboBox_1.addItem(d);
			comboBox_2.addItem(d);
		}
	}

	/**
	 * Fetch and populate Routes ComboBox and backing ArrayList
	 */
	public void popuplateRoutes()
	{
		comboBoxList.addAll(rCtrl.getAllRoutes());

		for (Route r : comboBoxList)
		{
			comboBox.addItem(r);
		}
	}

	/**
	 * Fetch and populate Unused destinations in listbox and backing ArrayList
	 */
	public void populateMulige()
	{
		mlist.clear();
		muligeList.removeAll();

		for (Destination d : dCtrl.getDestinationForNoRoute())
		{
			mlist.add(d);

			muligeList.add(d.toString());
		}
	}

	/**
	 * Fetch and populate Selected routes current path, and backing ArrayList
	 */
	public void populateRoute()
	{
		olist.clear();
		optimeringList.removeAll();
		rlist.clear();
		routeList.removeAll();

		Route r = comboBoxList.get(comboBox.getSelectedIndex());

		for (Destination d : dCtrl.getDestinationForRoute(r))
		{
			rlist.add(d);
			routeList.add(d.toString()); // ListBox
		}
		
		//updateRouteLengths();
	}

	public void setStartSlutComboBoxe()
	{
		Route r = comboBoxList.get(comboBox.getSelectedIndex());
		if (r.getStart() != null)
		{
			//System.out.println("StartID: " + r.getStart().getId());
			for (Destination start : sslist)
			{
				if (start.getId() == r.getStart().getId())
				{
					comboBox_1.setSelectedIndex(sslist.indexOf(start));
					//System.out.println("ValgtID: " + start.getId());
					//System.out.println("SSIndex: " + sslist.indexOf(start));
				}
			}
		}
		else
		{
			comboBox_1.setSelectedIndex(0);
		}

		if (r.getSlut() != null)
		{
			for (Destination slut : sslist)
			{
				if (slut.getId() == r.getSlut().getId())
				{
					comboBox_2.setSelectedIndex(sslist.indexOf(slut));
				}
			}
		}
		else
		{
			comboBox_2.setSelectedIndex(0);
		}
	}

	public void saveRoute(Route r)
	{
		rCtrl.saveRoute(r);
	}

	/**
	 * Run our different optimizing code on the current route selected.
	 */
	public void optimize()
	{
		olist.clear();
		optimeringList.clear();
		optimeringList.removeAll();

		olist = rCtrl.Optimize1(comboBoxList.get(comboBox.getSelectedIndex()), rlist);

		for (Destination d : olist)
		{
			optimeringList.add(d.toString());
		}
		
		updateRouteLengths();
	}

	public void updateRouteLengths()
	{
		if (rlist.size() > 3)
		{
			double curLength = rCtrl.getOptimizedDistance(comboBoxList.get(comboBox.getSelectedIndex()), rlist);

			if (curLength > 0)
			{
				CurrentRouteLength.setText("Nuværende længde: " + curLength / 1000 + " km");
			}
		}
		else
		{
			CurrentRouteLength.setText("Nuværende route længde: Kunne ikke beregnes!");
		}

		if (olist.size() > 3)
		{
			double OptLength = rCtrl.getOptimizedDistance(comboBoxList.get(comboBox.getSelectedIndex()), olist);
			
			if (OptLength > 0)
			{
				OptimizedRouteLength.setText("Optimeret længde: " + OptLength/1000 + " km");
			}
		}
		else
		{
			OptimizedRouteLength.setText("Optimeret længde: Kunne ikke beregnes!");
		}
	}

	public JPanel getPanel()
	{
		return this;
	}
}
