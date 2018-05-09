package dbLayer;

import java.util.List;

import modelLayer.Customer;

public interface DbCustomerIF
{
		// get
		public Customer getCustomer(int id) throws Exception;
		
		public List<Customer> getAllCustomer() throws Exception;
}