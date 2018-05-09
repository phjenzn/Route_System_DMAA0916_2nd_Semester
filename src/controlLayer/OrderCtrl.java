package controlLayer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dbLayer.DbOrder;
import modelLayer.Destination;
import modelLayer.Order;

public class OrderCtrl
{
	private static OrderCtrl oCtrl;

	// Singleton
	public static OrderCtrl getInstance()
	{
		if (oCtrl == null)
		{
			oCtrl = new OrderCtrl();
		}
		return oCtrl;
	}
	
	public Order getOrder(int id)
	{
		DbOrder DBo = new DbOrder();
		Order o = DBo.getOrder(id);
		return o;
	}
	
	public List<Order> getAllOrders()
	{
		DbOrder DBo = new DbOrder();
		List<Order> olist = DBo.getAllOrders();
		return olist;
	}
	
	public List<Order> getOrderByDestination(Destination destination)
	{
		DbOrder DBo = new DbOrder();
		List<Order> oD = DBo.getOrderWithDestinationId(destination);
		return oD;
	}
	
	/**
	 * Insert Order to DB
	 * @return 1 if succeeded, -1 if failed.
	 */
	public int insertOrder(Destination destination_id, double amount, Date datetime) 
	{
		DbOrder DBo = new DbOrder();
		int returnValue = 0;
		
		//create order object
		Order o = new Order(amount, destination_id, datetime);
		
		//insert to db
		try {
			returnValue = DBo.insertOrder(o);
		} catch (SQLException e) {
			e.printStackTrace();
			returnValue = -1;
		}
		return returnValue;
	}
}
