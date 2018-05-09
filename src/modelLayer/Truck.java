package modelLayer;

import java.util.ArrayList;
import java.util.List;

public class Truck
{
	private int				id;
	private List<Driver>	drivers;
	private long			capacity;

	public Truck(int id, long capacity)
	{
		this.id = id;
		this.capacity = capacity;
		this.drivers = new ArrayList<Driver>();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public List<Driver> getDrivers()
	{
		return drivers;
	}

	public void addDriver(Driver d)
	{
		this.drivers.add(d);
	}

	public void removeDriver(Driver d)
	{
		this.drivers.remove(d);
	}

	public long getCapacity()
	{
		return capacity;
	}

	public void setCapacity(long capacity)
	{
		this.capacity = capacity;
	}
	
	public String toString()
	{
		return "Bil: " + this.id + "                Mængde (kg): " + this.capacity;
	}

}
