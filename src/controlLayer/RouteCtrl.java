package controlLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dbLayer.DbRoute;
import modelLayer.Destination;
import modelLayer.Route;

public class RouteCtrl
{
	private static RouteCtrl	rCtrl;
	private MatrixCtrl			mCtrl	= MatrixCtrl.getInstance();

	// Singleton
	public static RouteCtrl getInstance()
	{
		if (rCtrl == null)
		{
			rCtrl = new RouteCtrl();
		}
		return rCtrl;
	}

	public Route getRoute(int id)
	{
		DbRoute DBct = new DbRoute();
		Route route = DBct.getRoute(id);
		return route;
	}

	public List<Route> getAllRoutes()
	{
		DbRoute DBct = new DbRoute();
		List<Route> lroute = DBct.getAllRoutes();
		return lroute;
	}

	public Route createRoute()
	{
		Route route = null;

		DbRoute DBct = new DbRoute();
		try
		{
			route = DBct.createRoute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return route;
	}

	public boolean saveRoute(Route route)
	{
		boolean ok = false;

		DbRoute DBct = new DbRoute();

		try
		{
			DBct.saveRoute(route);
			ok = true;
		} catch (Exception e)
		{

		}

		return ok;
	}

	public long getRouteDistance(Route route)
	{
		long totalDistance = 0;
		List<Destination> dlist = new ArrayList<Destination>();
		dlist.add(route.getStart());
		dlist.addAll(route.getCurrentRoute());
		dlist.add(route.getSlut());
		int dlistSize = dlist.size();

		if (dlist.size() > 1)
		{
			for (int i = 0; i < dlistSize; i++)
			{
				if (i < (dlistSize - 1))
				{
					long[] aTOb = mCtrl.getDistanceInfo(dlist.get(i), dlist.get((i + 1)));

					if (aTOb[0] > 0)
					{
						totalDistance += aTOb[0];
					}
				}
			}
		}

		return totalDistance;
	}

	public long getOptimizedDistance(Route route, List<Destination> newCombination)
	{
		long totalDistance = 0;
		List<Destination> dlist = new ArrayList<Destination>();		
		dlist.add(route.getStart());
		dlist.addAll(newCombination);
		dlist.add(route.getSlut());
		
		if (dlist.size() > 1)
		{
			for (int i = 0; i < dlist.size(); i++)
			{
				if (i < (dlist.size() - 1))
				{
					long[] aTOb = mCtrl.getDistanceInfo(dlist.get(i), dlist.get((i + 1)));

					if (aTOb[0] > 0)
					{
						totalDistance += aTOb[0];
					}
				}
			}
		}

		return totalDistance;
	}

	/**
	 * Optimering af route data Methode 1: Fra Start til Slut, via nærmest
	 * destination fra forrige destination.
	 */
	public List<Destination> Optimize1(Route route, List<Destination> Destinations)
	{
		/**
		 * vi kan ikke rigtig optimere på 3 eller mindere elementer. Da der ikke
		 * optimeres på start og slut. Men kun de rasterende elementer.
		 */
		if (Destinations.size() < 4 || route.getStart() == null || route.getSlut() == null)
		{
			return new ArrayList<Destination>(Destinations);
		}
		

		List<Destination> optimizedRoute = new ArrayList<Destination>();
		List<Destination> workingSet = new ArrayList<Destination>(Destinations);

		/**
		 * Husk her at tilføje Start destination til optimizedRoute fra
		 * route.getStartDestination Ikke implementeret i nu!
		 */
		optimizedRoute.add(route.getStart());

		long counter = 0;
		
		while (workingSet.size() > 0)
		{
			Destination bestToGoNext = null;
			long bestToGoNextDistance = 0;

			for (Destination d : workingSet)
			{
				long[] dist = mCtrl.getDistanceInfo(optimizedRoute.get(optimizedRoute.size() - 1), d);

				if (dist != null)
				{
					if (dist[0] < bestToGoNextDistance || bestToGoNext == null)
					{
						bestToGoNext = d;
						bestToGoNextDistance = dist[0];
					}
				}
				counter++;
			}
			optimizedRoute.add(bestToGoNext);
			workingSet.remove(bestToGoNext);
		}
		
		System.out.println("Antal Loops: "+ counter);

		optimizedRoute.add(route.getSlut());
		/**
		 * Husk her at tilføje Slut destination til optimizedRoute fra
		 * route.getStartDestination Ikke implementeret i nu!
		 */
		String output = "";
		for (Destination d : optimizedRoute)
		{
			output += " --> " + d.getAddress() + "(" + d.getZipcode() + ")";
		}
		System.out.println(
				output + "\n\rTotal optimized distance er: " + getOptimizedDistance(route, optimizedRoute) + " meter.");

		optimizedRoute.remove(route.getStart());
		optimizedRoute.remove(route.getSlut());
		return optimizedRoute;
	}

	/**
	 * Optimering af route data Methode 2: Fra Start til Slut, via nærmest
	 * destination fra forrige destination skiftevis fra Start og slut
	 * destinationen.
	 */
	// public List<Destination> Optimize2(Route route, List<Destination>
	// Destinations)
	// {
	// /**
	// * vi kan ikke rigtig optimere på 3 eller mindere elementer.
	// * Da der ikke optimeres på start og slut.
	// * Men kun de rasterende elementer.
	// */
	// if(Destinations.size() < 4)
	// {
	// return new ArrayList<Destination>(Destinations);
	// }
	//
	//
	// Destination[] ds;
	// Destinations.toArray(ds);
	// List<Destination> optimizedRoute = new ArrayList<Destination>();
	// List<Destination> optimizedBegining = new ArrayList<Destination>();
	// List<Destination> optimizedend = new ArrayList<Destination>();
	// List<Destination> workingSet = new ArrayList<Destination>(Destinations);
	//
	// /**
	// * Husk her at tilføje Start destination til optimizedRoute fra
	// * route.getStartDestination Ikke implementeret i nu!
	// */
	// optimizedRoute.add(workingSet.get(0));
	// workingSet.remove(0);
	//
	// Destination slutSted = workingSet.get(workingSet.size()-1);
	// workingSet.remove(workingSet.size()-1);
	//
	// while (workingSet.size() > 0)
	// {
	// Destination bestToGoNext = null;
	// long bestToGoNextDistance = 0;
	//
	// for (Destination d : workingSet)
	// {
	// long[] dist =
	// mCtrl.getDistanceInfo(optimizedRoute.get(optimizedRoute.size()-1), d);
	//
	// if(dist != null)
	// {
	// if(dist[0] < bestToGoNextDistance || bestToGoNext == null)
	// {
	// bestToGoNext = d;
	// bestToGoNextDistance = dist[0];
	// }
	// }
	// }
	// optimizedRoute.add(bestToGoNext);
	// workingSet.remove(bestToGoNext);
	// }
	//
	// optimizedRoute.add(slutSted);
	// /**
	// * Husk her at tilføje Slut destination til optimizedRoute fra
	// * route.getStartDestination Ikke implementeret i nu!
	// */
	// String output = "";
	// for (Destination d : optimizedRoute)
	// {
	// output += " --> " + d.getAddress() + "(" + d.getZipcode() + ")";
	// }
	// System.out.println(
	// output + "\n\rTotal optimized distance er: " +
	// getOptimizedDistance(route, optimizedRoute) + " meter.");
	//
	// return optimizedRoute;
	// }

	/*
	 * Breadth first search
	 */
	public void BFs(Destination d)
	{
		Queue<Destination> q = new LinkedList<Destination>();
		q.add(d);
		d.setMark(true);
		while (!q.isEmpty())
		{
			Destination dd = q.poll();
			System.out.println("Visiting: " + dd);

			for (Destination u : d.getAdjacencies(dd))
			{
				if (!u.isMark())
				{
					u.setMark(true);
					q.add(u);
				}
			}
		}
	}

}
