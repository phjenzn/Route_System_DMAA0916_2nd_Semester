package dbLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelLayer.Driver;
import modelLayer.Truck;

public class DbDriver
{
	public Driver getDriver(int id)
	{
		Driver d = null;

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Driver where id = '" + id + "'";
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
		}
		catch (SQLException ex)
		{
		}

		return d;
	}
	
	public List<Driver> getDriversForTruck(Truck truck)
	{
		List<Driver> all = new ArrayList<Driver>();
		
		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Driver where truck_id = " + truck.getId();
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			
			while(rs.next())
			{
				all.add(buildObject(rs));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
		}
		
		return all;
	}
	
	private Driver buildObject(ResultSet rs) throws SQLException 
	{
		return new Driver(rs.getInt("id"), rs.getString("name"));
	}
}
