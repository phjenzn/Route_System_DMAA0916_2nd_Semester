package dbLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controlLayer.DriverCtrl;
import modelLayer.Driver;
import modelLayer.Truck;

public class DbTruck
{
	DriverCtrl dCtrl = new DriverCtrl();
	
	public Truck getTruck(int id)
	{
		Truck t = null;

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Truck where id = '" + id + "'";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			if (rs.next())
			{
				t = buildObject(rs);
				for (Driver d : dCtrl.getDriverForTruck(t))
				{
					t.addDriver(d);
				}
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
		}

		return t;
	}
	
	public List<Truck> getAllTrucks()
	{
//		Truck t = null;
		List<Truck> tlist = new ArrayList<Truck>();

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Truck";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			while (rs.next())
			{
				tlist.add(buildObject(rs));
//				for (Driver d : dCtrl.getDriverForTruck(t))
//				{
//					t.addDriver(d);
//				}
//				
//				tlist.add(t);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
		}

		return tlist;
	}
	
	private Truck buildObject(ResultSet rs) throws SQLException 
	{
		return new Truck(rs.getInt("id"), rs.getLong("capacity"));
	}
}
