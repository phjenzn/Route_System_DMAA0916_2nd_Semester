package dbLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelLayer.Customer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbCustomer implements DbCustomerIF {
	@Override
	public Customer getCustomer(int id) {
		Customer c = null;

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Customer where id = '" + id + "'";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try {
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				c = buildObject(rs);
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
		}

		return c;
	}

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> all = new ArrayList<Customer>();

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Customer";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try {
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				all.add(buildObject(rs));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
		}

		return all;
	}

	private Customer buildObject(ResultSet rs) throws SQLException {
		return new Customer(rs.getInt("id"), rs.getString("name"), rs.getInt("phoneNo"));
	}
}
