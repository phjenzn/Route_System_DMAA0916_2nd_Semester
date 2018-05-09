package testLayer;

import org.junit.After;
import org.junit.Test;

import dbLayer.DBCon;

public class DBtest {

	DBCon testConnection = DBCon.getInstance();

	/*
	 * Tests the getConnection method
	 */
	@Test
	public void test() {
		System.out.println(testConnection.getConnection());
	}

	@After
	public void cleanUp() {
		testConnection.disconnect();
	}
}