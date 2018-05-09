/**
 * 
 */
package uiLayer;

/**
 * @author Gruppe 7
 *
 */

import javax.swing.JPanel;
import controlLayer.TruckCtrl;
import modelLayer.Truck;
import java.awt.List;

@SuppressWarnings("serial")
public class TruckUI extends JPanel {
	
	TruckCtrl tCtrl = TruckCtrl.getInstance();
	
	public TruckUI() {
		setLayout(null);
		
		List list = new List();
		list.setBounds(51, 36, 340, 220);
		add(list);
		
		for (Truck t : tCtrl.getAllTruck())
		{
			list.add(t.toString(), t.getId());
		}
	}
	
	public JPanel getPanel()
	{
		return this;
	}
}
