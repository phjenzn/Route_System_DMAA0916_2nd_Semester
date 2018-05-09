package dbLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controlLayer.DestinationCtrl;
import controlLayer.TruckCtrl;
import modelLayer.Destination;
import modelLayer.Route;

public class DbRoute
{
	private static final String	createRouteSQL	= "insert into route (truck_id) values (?)";
	private static final String	saveRouteSQL	= "update route set truck_id = ?, start_destination_id = ?, slut_destination_id = ? where id = ?";
	private PreparedStatement	createRoute, saveRoute;
	private Connection			con;
	private DBCon				DB;

	TruckCtrl					tCtrl;
	DestinationCtrl				dCtrl;

	public DbRoute()
	{
		DB = DBCon.getInstance();
		con = DB.getConnection();
		tCtrl = TruckCtrl.getInstance();
		dCtrl = DestinationCtrl.getInstance();

		try
		{
			createRoute = con.prepareStatement(createRouteSQL, Statement.RETURN_GENERATED_KEYS);
			saveRoute   = con.prepareStatement(saveRouteSQL);
		} catch (SQLException e)
		{
		}
	}

	public Route createRoute() throws SQLException
	{
		createRoute.setNull(1, java.sql.Types.INTEGER);
		int id = DB.executeInsertWithIdentity(createRoute);

		return (id > -1 ? new Route(id) : null);
	}

	public void saveRoute(Route route) throws SQLException
	{
		dCtrl.saveDestinations(route);

		saveRoute.setNull(1, java.sql.Types.INTEGER);
		saveRoute.setInt(4, route.getId());

		if (route.getStart() == null)
		{
			saveRoute.setNull(2, java.sql.Types.INTEGER);
		} else
		{
			saveRoute.setInt(2, route.getStart().getId());
		}

		if (route.getStart() == null)
		{
			saveRoute.setNull(3, java.sql.Types.INTEGER);
		} else
		{
			saveRoute.setInt(3, route.getSlut().getId());
		}

		saveRoute.executeUpdate();
	}

	public Route getRoute(int id)
	{
		Route r = null;

		String sql = "select * from Route where id = '" + id + "'";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			if (rs.next())
			{
				r = buildObject(rs);

				for (Destination d : dCtrl.getDestinationForRoute(r))
				{
					r.addDestination(d);
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex)
		{
		}

		return r;
	}

	public List<Route> getAllRoutes()
	{
		List<Route> all = new ArrayList<Route>();

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Route";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();

			while (rs.next())
			{
				all.add(buildObject(rs));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex)
		{
		}

		return all;
	}

	private Route buildObject(ResultSet rs) throws SQLException
	{
		return new Route(rs.getInt("id"), tCtrl.getTruck(rs.getInt("truck_id")),
				dCtrl.getDestination(rs.getInt("start_destination_id")),
				dCtrl.getDestination(rs.getInt("slut_destination_id")));
	}
}
