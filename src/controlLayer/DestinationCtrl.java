package controlLayer;

import java.util.ArrayList;
import java.util.List;

import dbLayer.DbDestination;
import modelLayer.Destination;
import modelLayer.Route;

public class DestinationCtrl
{
	private static DestinationCtrl	dCtrl;
	private static DbDestination DBd;

	// Singleton
	public static DestinationCtrl getInstance()
	{
		if (dCtrl == null)
		{
			dCtrl = new DestinationCtrl();
		}
		return dCtrl;
	}

	private DestinationCtrl()
	{
		DBd = DbDestination.getInstance();
	}

	public Destination getDestination(int id)
	{
		Destination d = null;

		//DbDestination DBd = new DbDestination();
		d = DBd.getDestination(id);

		return d;
	}

	public List<Destination> getAllDestinations()
	{
		//DbDestination DBd = new DbDestination();
		List<Destination> dlist = DBd.getAllDestinations();
		return dlist;
	}

	public List<Destination> getDestinationForRoute(Route route)
	{
		//DbDestination DBd = new DbDestination();
		List<Destination> dlist = DBd.getDestinationForRoute(route);
		return dlist;
	}

	public List<Destination> getDestinationForNoRoute()
	{
		//DbDestination DBd = new DbDestination();
		List<Destination> dlist = DBd.getDestinationForNoRoute();
		return dlist;
	}

	public boolean saveDestination(Destination d, int route_id, int position)
	{
		//DbDestination DBd = new DbDestination();
		return DBd.saveDestination(d, route_id, position);
	}

	public boolean saveDestinations(Route route)
	{
		boolean ok = true;
		List<Destination> old = new ArrayList<Destination>();

		// Get all old Destinations for this route_id
		old.addAll(getDestinationForRoute(route));

		// Remove Destinations still here, or newly added, that needs updating.
		// old.removeAll(route.getCurrentRoute());

		// Set current Destinations new route_id and position.
		for (Destination d : route.getCurrentRoute())
		{
			int toremove = -1;

			for (int i = 0; i < old.size(); i++)
			{
				if (old.get(i).getId() == d.getId())
				{
					toremove = i;
				}
			}

			if (toremove > -1)
			{
				old.remove(toremove);
			}
		}

		// Remove old destination for the route, sets route_id and routePosition
		// to -1.
		for (Destination d : old)
		{
			if (!saveDestination(d, -1, -1))
			{
				ok = false;
			}
		}

		// Save the new once
		for (Destination d : route.getCurrentRoute())
		{
			if (!saveDestination(d, route.getId(), route.getCurrentRoute().indexOf(d)))
			{
				ok = false;
			}
		}

		return ok;
	}

	public double getDistance(Destination a, Destination b)
	{
		double km = -1; // Default return value in case of error!

		/**
		 * a and b can't be the same.
		 */
		if (!a.equals(b))
		{
			return km;
		}

		/**
		 * id of a is larger than id of b else its reverse.
		 */
		if (a.getId() > b.getId())
		{
			// Some code here to fetch data from
			// The matrix table
		} else
		{

		}

		return km;
	}

}
