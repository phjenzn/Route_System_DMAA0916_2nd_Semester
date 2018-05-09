package uiLayer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import controlLayer.DestinationCtrl;
import controlLayer.OrderCtrl;
import modelLayer.Destination;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Order extends JPanel {
	List<Destination> desList = new ArrayList<Destination>();

	java.awt.List orderList = new java.awt.List();

	JComboBox<Destination> comboBoxDestination;
	JComboBox<Destination> comboBoxDes;
	
	JSpinner spinner = new JSpinner();

	OrderCtrl oCtrl = OrderCtrl.getInstance();
	DestinationCtrl dCtrl = DestinationCtrl.getInstance();

	/**
	 * Create the panel.
	 */
	public Order() {

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE).addGap(11)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE).addContainerGap()));

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Se bestilling", null, panel_1, null);

		JLabel lblNewLabel_1 = new JLabel("V\u00E6lge sted:");

		comboBoxDes = new JComboBox<Destination>();
		comboBoxDes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){

				}
				
			}
		});
		
		comboBoxDestination = new JComboBox<Destination>();
		desBox();
		comboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					orderByDes();
				}
			}
		});
		add(comboBoxDestination);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
								.addGroup(gl_panel_1.createSequentialGroup().addComponent(lblNewLabel_1).addGap(26)
										.addComponent(comboBoxDestination, 0, 320, Short.MAX_VALUE)))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(25)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBoxDestination, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1))
						.addGap(18).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
						.addContainerGap()));

		scrollPane.setViewportView(orderList);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Ny bestilling", null, panel, null);
		
		JLabel lblDestination = new JLabel("Destination");
		
		JLabel lblMngdeFoder = new JLabel("M\u00E6ngde foder");
		
		
		JButton btnOpretBestilling = new JButton("Opret bestilling");
		btnOpretBestilling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				opretOrdre();
			}
		});
		
		spinner.setModel(new SpinnerNumberModel(30, 30, 5000, 1));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblDestination)
									.addGap(18)
									.addComponent(comboBoxDes, 0, 582, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblMngdeFoder)
									.addGap(18)
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(262)
							.addComponent(btnOpretBestilling)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDestination)
						.addComponent(comboBoxDes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(34)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMngdeFoder)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
					.addComponent(btnOpretBestilling)
					.addGap(115))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
		orderByDes();

	}
	
	public void opretOrdre(){
		Destination d = desList.get(comboBoxDes.getSelectedIndex());
		java.sql.Date datetime = null;
		
		try
		{
			spinner.commitEdit();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		int spin = (Integer) spinner.getValue();
		double amount = 1.0 * spin;
		oCtrl.insertOrder( d, amount, datetime);
	}
	
	public void desBox(){
		desList.addAll(dCtrl.getAllDestinations());

		for (Destination d : desList) {
			comboBoxDestination.addItem(d);
			comboBoxDes.addItem(d);
		}
	}

	public void orderByDes() {
		orderList.removeAll();
		
		Destination d = desList.get(comboBoxDestination.getSelectedIndex());

		for (modelLayer.Order o : oCtrl.getOrderByDestination(d))
		{
			if (o != null)
			{
				orderList.add(o.toString());
			}
		}
	}

	public JPanel getPanel() {
		return this;
	}
}