package dbLayer;

import java.util.List;

import modelLayer.Order;

public interface DbOrderIF
{
	
	/*
	 * get
	 */
	public Order getOrder (int id) throws Exception;
	
	public List<Order> getAllOrders() throws Exception;

}