/**
 * 
 */
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

import controlLayer.CustomerCtrl;
import controlLayer.DestinationCtrl;
import modelLayer.Destination;
import modelLayer.Order;

/**
 *
 */
public class DbOrder implements DbOrderIF {

	DestinationCtrl dCtrl = DestinationCtrl.getInstance();

	CustomerCtrl cCtrl = CustomerCtrl.getInstance();

	/*
	 * @see dbLayer.DbOrderIF#getOrder(int)
	 */
	@Override
	public Order getOrder(int id) {
		Order o = null;

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Ordre where id = " + id + "";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try {
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				o = buildObject(rs);
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
		}

		return o;
	}

	/*
	 * @see dbLayer.DbOrderIF#getAllOrders()
	 */
	@Override
	public List<Order> getAllOrders() {
		List<Order> all = new ArrayList<Order>();

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Ordre";
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

	public List<Order> getOrderWithDestinationId(Destination destination) {
		List<Order> od = new ArrayList<Order>();

		Connection con = DBCon.getInstance().getConnection();

		String sql = "select * from Ordre where destination_id = " + destination.getId() + "";
		System.out.printf("%s - %s\n", (new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date())), sql);
		try {
			Statement stmt = con.createStatement();
			stmt.execute(sql);

			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				od.add(buildObject(rs));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
		}

		return od;
	}

	/**
	 * Insert an Order to the DB
	 */
	public int insertOrder(Order o) throws SQLException {

		Connection con = DBCon.getInstance().getConnection();
		
		PreparedStatement pstmt = null;
		int controlInt = -1;
		String insert = "insert into Ordre(destination_id, ordreMethod_id, DeliveryDate, amount)"
				+ "values (?, null, default, ?)";
		System.out.println(insert);
		try {
			pstmt = con.prepareStatement(insert);
			pstmt.setInt(1, o.getDestination().getId());
			pstmt.setDouble(2, o.getAmount());
			controlInt = pstmt.executeUpdate();
		} catch (SQLException sqlE) {
			System.out.println("SQL Error, Order not inserted");
			System.out.println(sqlE.getMessage());
		} catch (Exception e) {
			e.getMessage();
		}

		return controlInt;
	}

	private Order buildObject(ResultSet rs) throws SQLException {
		return new Order(rs.getInt("id"), rs.getDouble("amount"), dCtrl.getDestination(rs.getInt("destination_id")),
				rs.getDate("DeliveryDate"));
	}
}
