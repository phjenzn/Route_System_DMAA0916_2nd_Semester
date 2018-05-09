package dbLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DBCon
{
	private static final String	DBNAME	= "dmaa0916_128844";
	private static final String	DBHOST	= "kraka.ucn.dk";
	private static final int	DBPORT	= 1433;
	private static final String	DBUSER	= "dmaa0916_128844";
	private static final String	DBPASS	= "Password1!";

	private static Connection	con		= null;
	private static DBCon		dbCon;
	private static final String	DBDRIV	= "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String	CONSTR	= String.format("jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s",
			DBHOST, DBPORT, DBNAME, DBUSER, DBPASS);

	private DBCon()
	{
		forbind();
	}

	// Singleton
	public static DBCon getInstance()
	{
		if (dbCon == null)
		{
			dbCon = new DBCon();
		}
		return dbCon;
	}

	public int executeInsertWithIdentity(String sql) throws SQLException
	{
		System.out.println("DBConnection: " + sql);
		int res = -1;
		try (Statement s = con.createStatement())
		{
			res = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (res > 0)
			{
				ResultSet rs = s.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	public int executeInsertWithIdentity(PreparedStatement ps) throws SQLException  {
		int res = -1;
		try {
			res = ps.executeUpdate();
			if(res > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}

	public int executeUpdate(String sql) throws SQLException
	{
		System.out.println("DBCon, Updating: " + sql);
		int res = -1;
		try (Statement s = con.createStatement())
		{
			res = s.executeUpdate(sql);
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	public int executeUpdate(PreparedStatement ps) throws SQLException  {
		System.out.println("HMM");
		int res = -1;
		try {
			res = ps.executeUpdate();
			if(res > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}

	public Connection getConnection()
	{
		return con;
	}

	
	//Tester vores forbindelse
	public boolean testConnection()
	{
		boolean test = false;

		Connection con = DBCon.getInstance().getConnection();			//
																		//
		String sql = String.format("select top 1 * from users");		//<-	
		try																//  |
		{																//  --------------------
			Statement stmt = con.createStatement();						//opretter statement   |
			stmt.execute(sql);											//executer statement <-|
			ResultSet rs = stmt.getResultSet();
			if (rs.next())
			{
				test = true;
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			
		}

		return test;
	}
	
	public void forbind()
	{
		try
		{
			System.out.println("Forbinder til databasen.");
			Class.forName(DBDRIV);
			con = DriverManager.getConnection(CONSTR);
			getClass();
			System.out.println("Forbundet...");
		} catch (ClassNotFoundException e)
		{
			System.out.println("Kunne ikke starte JDBC driver.");
			e.printStackTrace();
		} catch (SQLException e)
		{
			System.out.println("Kunne ikke forbinde til databasen " + DBNAME + "@" + DBHOST + ":" + DBPORT + " as user "
					+ DBUSER + " med password ******");
			System.out.println("Forbindelses stringen CONSTR var: "
					+ CONSTR.substring(0, CONSTR.length() - DBPASS.length()) + "....");
			e.printStackTrace();
		}
	}

	public void disconnect()
	{
		try
		{
			con.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
