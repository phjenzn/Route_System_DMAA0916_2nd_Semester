package controlLayer;

import java.util.List;

import dbLayer.DbCustomer;
import modelLayer.Customer;


public class CustomerCtrl
{
	private static CustomerCtrl cCtrl;

	// Singleton
	public static CustomerCtrl getInstance()
	{
		if (cCtrl == null)
		{
			cCtrl = new CustomerCtrl();
		}
		return cCtrl;
	}

	public Customer getCustomer(int id)
	{
		DbCustomer DBc = new DbCustomer();
		Customer c = DBc.getCustomer(id);
		return c;
	}
	
	public List<Customer> getAllCustomer()
	{
		DbCustomer DBc = new DbCustomer();
		List<Customer> clist = DBc.getAllCustomer();
		return clist;
	}

}
