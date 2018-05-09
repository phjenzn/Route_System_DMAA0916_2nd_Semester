package modelLayer;

public class Customer
{
	private int		id;
	private String	name;
	private String	email;
	private int		phoneNo;

	public Customer(int id, String name, int phoneNo)
	{
		this.id = id;
		this.name = name;
		this.phoneNo = phoneNo;
		this.email = "";
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public int getPhoneNo()
	{
		return phoneNo;
	}

	public int getId()
	{
		return id;
	}

	public void setPhoneNo(int phoneNo)
	{
		this.phoneNo = phoneNo;
	}
	
	@Override
	public String toString()
	{
		return "" + this.id + " - " + this.name;
	}

}
