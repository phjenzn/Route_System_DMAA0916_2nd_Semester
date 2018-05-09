package dbLayer;

import modelLayer.User;

public interface DbUserIF
{ 
	// get
	public User getUser(String username, String password) throws Exception;
}
