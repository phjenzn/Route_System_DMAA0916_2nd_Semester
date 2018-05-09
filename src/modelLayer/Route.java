package modelLayer;

import java.util.ArrayList;
import java.util.List;

public class Route
{
	private int					id;
	private Truck				truck			= null;
	private List<Destination>	currentRoute	= new ArrayList<Destination>();	// Current
																				// used
																				// route.
	private List<Destination>	OptimizedRoute	= new ArrayList<Destination>();	// Best
																				// route
																				// found.
	private Destination			start;
	private Destination			slut;

	// private double currentRouteLength;
	// private double OptimizedRouteLength;
	public Route()
	{
	}

	public Route(int id)
	{
		this.id = id;
	}

	public Route(int id, Truck truck, Destination start, Destination slut)
	{
		this.id = id;
		this.truck = truck;
		this.start = start;
		this.slut = slut;
	}

	public Route(int id, Truck truck, List<Destination> currentRoute, List<Destination> OptimizedRoute, Destination start, Destination slut)
	{
		this.id = id;
		this.truck = truck;
		this.start = start;
		this.slut = slut;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Truck getTruck()
	{
		return truck;
	}

	public void setTruck(Truck truck)
	{
		this.truck = truck;
	}

	public List<Destination> getCurrentRoute()
	{
		return currentRoute;
	}

	public void setCurrentRoute(List<Destination> currentRoute)
	{
		this.currentRoute = currentRoute;
	}

	public List<Destination> getOptimizedRoute()
	{
		return OptimizedRoute;
	}

	public void setOptimizedRoute(List<Destination> optimizedRoute)
	{
		OptimizedRoute = optimizedRoute;
	}

	public void addDestination(Destination d)
	{
		currentRoute.add(d);
	}

	public void removeDestination(Destination d)
	{
		currentRoute.remove(d);
	}

	public Destination getStart()
	{
		return start;
	}

	public void setStart(Destination start)
	{
		this.start = start;
	}

	public Destination getSlut()
	{
		return slut;
	}

	public void setSlut(Destination slut)
	{
		this.slut = slut;
	}

	@Override
	public String toString()
	{
		return new String("" + this.id);
	}

	// public double getCurrentRouteLength()
	// {
	// return currentRouteLength;
	// }
	//
	// public double getOptimizedRouteLength()
	// {
	// return OptimizedRouteLength;
	// }
}
