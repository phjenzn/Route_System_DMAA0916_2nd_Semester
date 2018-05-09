package controlLayer;

import java.util.List;

import dbLayer.DbTruck;
import modelLayer.Truck;

public class TruckCtrl
{
	private static TruckCtrl tCtrl;

	// Singleton
	public static TruckCtrl getInstance()
	{
		if (tCtrl == null)
		{
			tCtrl = new TruckCtrl();
		}
		return tCtrl;
	}
	
	public Truck getTruck(int id)
	{
		DbTruck DBt = new DbTruck();
		Truck t = DBt.getTruck(id);
		return t;
	}
	
	public List<Truck> getAllTruck()
	{
		DbTruck DBt = new DbTruck();
		List<Truck> tlist = DBt.getAllTrucks();
		return tlist;
	}
}
