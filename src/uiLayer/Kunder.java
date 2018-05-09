package uiLayer;

import javax.swing.JPanel;

import java.awt.CardLayout;

import controlLayer.CustomerCtrl;
import modelLayer.Customer;
import javax.swing.JList;
import java.awt.List;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class Kunder extends JPanel
{

	CustomerCtrl		custCtrl	= CustomerCtrl.getInstance();
	//java.util.List<Customer>		customerList = new ArrayList<Customer>();

	JList<Customer>		kundeliste;
	private JTextField	textField_id;
	private JTextField	textField_navn;
	private JTextField	textField_email;

	/**
	 * Create the panel.
	 */
	public Kunder(JPanel ContentPanel, CardLayout layout)
	{
		setLayout(null);

		List list = new List();
		list.setBounds(10, 10, 203, 280);
		add(list);

		JLabel lblId = new JLabel("Id: ");
		lblId.setBounds(219, 10, 56, 16);
		add(lblId);

		JLabel lblNewLabel = new JLabel("Navn:");
		lblNewLabel.setBounds(219, 39, 56, 16);
		add(lblNewLabel);

		textField_id = new JTextField();
		textField_id.setBounds(271, 7, 167, 22);
		add(textField_id);
		textField_id.setColumns(10);

		textField_navn = new JTextField();
		textField_navn.setBounds(271, 36, 167, 22);
		add(textField_navn);
		textField_navn.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Email:");
		lblNewLabel_1.setBounds(219, 68, 56, 16);
		add(lblNewLabel_1);

		textField_email = new JTextField();
		textField_email.setBounds(271, 65, 167, 22);
		add(textField_email);
		textField_email.setColumns(10);

		for (Customer c : custCtrl.getAllCustomer())
		{
			list.add(c.toString(), c.getId());
		}
		
		list.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					System.out.println(list.getSelectedIndex());
					Customer c = null;
					c = custCtrl.getCustomer(list.getSelectedIndex());

					
						textField_id.setText(Integer.toString(c.getId()));
					
						textField_navn.setText(c.getName());
					
						textField_email.setText(c.getEmail());
					

				}
			}
		});

	}

	public JPanel getPanel()
	{
		return this;
	}
}
