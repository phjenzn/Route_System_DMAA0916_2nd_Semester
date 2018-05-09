package testLayer;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import dbLayer.DBCon;
import dbLayer.DbDestination;
import modelLayer.Destination;

public class DBDestinationTest {
	
	/*
	 * Tests method getDestination
	 */
	
	@Before
	public void setUp() throws SQLException {
		DBCon con = DBCon.getInstance();
		con.forbind();
	}
	@Test
	public void testGetDestination() throws SQLException {
		DbDestination test = DbDestination.getInstance();
		
		/*
		 * We test the method with '0' as parameter
		 * Should return the destination with id '0'.
		 */
		Destination output = test.getDestination(0);
		
		/*
		 * If our code works, we should get the output "0 - Lærkevej 21..."
		 */
		assertEquals("0 - Lærkevej 21, 9530(Fordercentralen Limfjorden)", output.toString());
	}
}