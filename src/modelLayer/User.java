package modelLayer;

public class User
{
	private int		id;
	private String	name;
	private int		accesslevel;

	public User(int id, String name, int accesslevel)
	{
		this.id = id;
		this.name = name;
		this.accesslevel = accesslevel;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getAccesslevel()
	{
		return accesslevel;
	}
	
	public boolean isAllowed()
	{
		return (this.accesslevel > -1 ? true : false); // ? = if/else
	}
	
	

}
