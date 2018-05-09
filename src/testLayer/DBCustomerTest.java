package testLayer;

import static org.junit.Assert.*;

import org.junit.Test;

import dbLayer.DbCustomer;
import modelLayer.Customer;

public class DBCustomerTest {

	/*
	 * Tests method getCustomer
	 */
	@Test
	public void testGetCustomer() {
		DbCustomer test = new DbCustomer();
		
		/*
		 * We test the method with '1' as parameter
		 * Should return the customer with id '1'.
		 */
		Customer output = test.getCustomer(1);
		
		/*
		 * If our code works, we should get the output "1 - Tom Jensen"
		 */
		assertEquals("1 - Tom Jensen", output.toString());
	}
}
