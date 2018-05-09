package dbLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelLayer.Destination;
import modelLayer.Route;
import controlLayer.CustomerCtrl;

public class DbDestination implements DbDestinationIF
{
	private static DbDestination	DbDist;
	private List<Destination>		destinations;
	private boolean					useCache;

	CustomerCtrl					cCtrl	= CustomerCtrl.getInstance();
	Connection						con		= DBCon.getInstance().getConnection();

	// Singleton
	public static DbDestination getInstance()
	{
		if (DbDist == null)
		{
			DbDist = new DbDestination();
		}
		return DbDist;
	}

	private DbDestination()
	{
		this.destinations = new ArrayList<Destination>();
		this.useCache = true;
	}

	private Destination getCache(int id)
	{
		Destination d = null;

		if (useCache)
		{
			for (Destination a : destinations)
			{
				if (a.getId() == id)
				{
					d = a;
				}
			}
		}

		return d;
	}

	private void putCache(Destination d)
	{
		if (useCache)
		{
			boolean isFound = false;

			for (Destination a : destinations)
			{
				if (d.getId() == a.getId())
				{
					isFound = true;
				}
			}

			if (!isFound)
			{
				destinations.add(d);
			}
		}
	}

	@Override
	public Destination getDestination(int id)
	{
		Destination d = null;

		d = getCache(id);

		if (d == null)
		{
			String sql = "select * from Destination where id = " + id;
			System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
			try
			{
				Statement stmt = con.createStatement();
				stmt.execute(sql);

				ResultSet rs = stmt.getResultSet();
				if (rs.next())
				{
					d = buildObject(rs);
				}
				rs.close();
				stmt.close();

				if (d != null)
				{
					putCache(d);
				}
			} catch (SQLException ex)
			{
			}
		}
		return d;
	}

	public List<Destination> getDestinationForRoute(Route route)
	{

		List<Destination> dlist = new ArrayList<Destination>();

		// Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Destination where route_id = " + route.getId() + " order by routePosition ASC";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			while (rs.next())
			{
				Destination tmp = getCache(rs.getInt("id"));
				if (tmp != null)
				{
					dlist.add(tmp);
				} else
				{
					tmp = buildObject(rs);
					putCache(tmp);
					dlist.add(tmp);
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex)
		{
		}
		return dlist;
	}

	public List<Destination> getDestinationForNoRoute()
	{

		List<Destination> dlist = new ArrayList<Destination>();

		// Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Destination where route_id is null";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			while (rs.next())
			{
				Destination tmp = getCache(rs.getInt("id"));
				if (tmp != null)
				{
					dlist.add(tmp);
				} else
				{
					tmp = buildObject(rs);
					putCache(tmp);
					dlist.add(tmp);
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex)
		{
		}
		return dlist;
	}

	public boolean saveDestination(Destination d, int route_id, int position)
	{
		boolean saved = false;

		// Connection con = DBCon.getInstance().getConnection();

		String sql = "update Destination set routePosition = " + (position == -1 ? null : position) + ", route_id = "
				+ (route_id == -1 ? null : route_id) + " where id = '" + d.getId() + "'";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			if (stmt.getUpdateCount() > 0)
			{
				saved = true;
			}
			stmt.close();
		} catch (SQLException ex)
		{
		}
		return saved;
	}

	public List<Destination> getAllDestinations()
	{
		List<Destination> all = new ArrayList<Destination>();

		// Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Destination";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();

			while (rs.next())
			{
				Destination tmp = getCache(rs.getInt("id"));
				if (tmp != null)
				{
					all.add(tmp);
				} else
				{
					tmp = buildObject(rs);
					putCache(tmp);
					all.add(tmp);
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex)
		{
		}

		return all;
	}

	private Destination buildObject(ResultSet rs) throws SQLException
	{
		return new Destination(rs.getInt("id"), cCtrl.getCustomer(rs.getInt("Customer_id")), rs.getString("address"),
				rs.getInt("zipcode"), rs.getInt("health"));
	}
}
