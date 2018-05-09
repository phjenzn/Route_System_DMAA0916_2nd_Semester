package controlLayer;

import dbLayer.DbUser;
import modelLayer.User;

public class UserCtrl
{
	private static UserCtrl uCtrl;
	private User LoggedInAsuser;

	// Singleton
	public static UserCtrl getInstance()
	{
		if (uCtrl == null)
		{
			uCtrl = new UserCtrl();
		}
		return uCtrl;
	}
	
	public boolean login(String username, String password)
	{
		LoggedInAsuser = getUser(username, password);
		if (LoggedInAsuser.isAllowed())
		{
			return true;
		}
		
		return false;
	}

	private User getUser(String username, String password)
	{
		DbUser DBct = new DbUser();
		User user = DBct.getUser(username, password);
		return user;
	}

	public User getLoggedInAsuser()
	{
		return LoggedInAsuser;
	}
	
	public boolean logout()
	{
		if(LoggedInAsuser.isAllowed())
		{
			LoggedInAsuser = new User(-1, "Not Found!", -1);
			return true;
		}
		
		return false;
	}
}
