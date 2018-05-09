package dbLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelLayer.User;

public class DbUser implements DbUserIF
{

	@Override
	public User getUser(String username, String password)
	{
		User user = new User(-1, "Not Found!", -1); // Default returned user,
													// with no access

		Connection con = DBCon.getInstance().getConnection();

		String sql = String.format(
				"select top 1 * from users where name = '%s' and password = CONVERT(VARCHAR(32), HashBytes('MD5', '%s'), 2)",
				username, password);
		try
		{
			Statement stmt = con.createStatement();
			stmt.execute(sql);
			ResultSet rs = stmt.getResultSet();
			if (rs.next())
			{
				user = buildObject(rs);
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex)
		{
		}

		return user;
	}

	private User buildObject(ResultSet rs) throws SQLException
	{
		return new User(rs.getInt("id"), rs.getString("name"), rs.getInt("accesslevel"));
	}
}
