package testLayer;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import controlLayer.DestinationCtrl;
import dbLayer.DBCon;
import dbLayer.DbOrder;
import modelLayer.Destination;
import modelLayer.Order;

public class DBOrderTest
{
	@Before
	public void setUp() throws SQLException
	{
		DBCon con = DBCon.getInstance();
		con.forbind();
	}

	@Test
	public void wasInsertOrder()
	{
		DBCon.getInstance().getConnection();
		
		DestinationCtrl dCtrl = DestinationCtrl.getInstance();
		
		Destination destination = dCtrl.getDestination(12);
		
		double kgBestilt = 100.0;
		
		Order testOrder = new Order(kgBestilt, destination, null);

		DbOrder DBo= new DbOrder();
		
		try
		{
			DBo.insertOrder(testOrder);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Order output = DBo.getOrder(id);
		
		/*
		 * If our code works, we should get the output "0 - Lærkevej 21..."
		 */
		//assertEquals("0 - Lærkevej 21, 9530(Fordercentralen Limfjorden)", output.toString());
	
		

	}

}
