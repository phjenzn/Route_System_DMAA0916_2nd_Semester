package testLayer;

import static org.junit.Assert.*;

import org.junit.Test;

import controlLayer.UserCtrl;

public class UserCtrlTest {

	private static UserCtrl uc = UserCtrl.getInstance();
	
	/*
	 * Test login method
	 */
	@Test
	public void loginTest() {
		
		/*
		 * We test the method with "test" as username and "1234" as password
		 */
		boolean lgin = uc.login("test", "1234");
		
		/*
		 * boolean should return true if 'lgin' is correct
		 */
		assertTrue(lgin);
	}
	
	@Test
	public void noAccessTest()
	{
		/*
		 * We test the method with "user" as username and "pw" as password
		 */
		boolean wrlg = uc.login("user", "pw");
		
		/*
		 * boolean should return false if 'wrlg' is incorrect
		 */
		assertFalse(wrlg);
	}

}
