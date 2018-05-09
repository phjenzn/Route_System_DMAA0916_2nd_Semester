package controlLayer;

import java.util.List;

import dbLayer.DbDriver;
import modelLayer.Driver;
import modelLayer.Truck;

public class DriverCtrl
{
	private static DriverCtrl dCtrl;

	// Singleton
	public static DriverCtrl getInstance()
	{
		if (dCtrl == null)
		{
			dCtrl = new DriverCtrl();
		}
		return dCtrl;
	}

	public Driver getDriver(int id)
	{
		DbDriver DBd = new DbDriver();
		Driver d = DBd.getDriver(id);
		return d;
	}
	
	public List<Driver> getDriverForTruck(Truck truck)
	{
		DbDriver DBd = new DbDriver();
		List<Driver> dlist = DBd.getDriversForTruck(truck);
		return dlist;
	}
}
