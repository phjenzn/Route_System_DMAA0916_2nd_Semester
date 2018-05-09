package dbLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import modelLayer.Destination;


public class DbMatrix
{
	public long[] getFromMatrix(Destination a, Destination b)
	{
		long[] matrix = null;
		String sql = "";
		int lowestId, highestId;
		Connection con = DBCon.getInstance().getConnection();

		if(a.getId() > b.getId())
		{
			lowestId = b.getId();
			highestId = a.getId();
		}
		else
		{
			lowestId = a.getId();
			highestId = b.getId();
		}
		
			sql = "select distance, traveltime from Matrix where destinationA = " + lowestId + " AND destinationB = "+ highestId;	
		
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			if (rs.next())
			{
				matrix = buildObject(rs);
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex)
		{
		}

		return matrix;
	}
	
	public boolean saveInMatrix(Destination a, Destination b, long distance, long traveltime)
	{
		boolean saved = false;
		String sql = "";
		int lowestId, highestId;
		Connection con = DBCon.getInstance().getConnection();
		
		if(a.getId() > b.getId())
		{
			lowestId = b.getId();
			highestId = a.getId();
		}
		else
		{
			lowestId = a.getId();
			highestId = b.getId();
		}
		
		sql = 
				"IF NOT EXISTS(SELECT DestinationA FROM Matrix WHERE DestinationA = "+ lowestId +" AND DestinationB = "+ highestId +")"
				+"BEGIN" 
				+" INSERT INTO Matrix(DestinationA, DestinationB, distance, traveltime)" 
				+"  VALUES("+ lowestId +", " + highestId + ", "+ distance + ", "+ traveltime +") " 
				+"END";
		
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);
			saved = true;

			stmt.close();
		} catch (SQLException ex)
		{
		}

		return saved;
	}

	private long[] buildObject(ResultSet rs) throws SQLException
	{
		return new long[] {rs.getLong("distance"),rs.getLong("traveltime")};
	}
}
