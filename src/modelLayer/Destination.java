package modelLayer;

import java.util.LinkedList;
import java.util.List;

public class Destination
{
	private int			id;
	private String		address;
	private Customer	customer;
	private int			zipcode;
	private int			health;		// 0 = Ok, 1 = Sick, 2 = More Sick, 3 = Very
									// very Bad Sick
	private String		coordinates;
	private boolean mark;
	private List<Destination> destinations;
	private List<LinkedList<Destination>> adjList;

	public Destination(int id, Customer customer, String address, int zipcode, int health)
	{
		this.address = address;
		this.customer = customer;
		this.zipcode = zipcode;
		this.id = id;
		this.health = health;
		this.coordinates = "";
		mark = false;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public int getZipcode()
	{
		return zipcode;
	}

	public void setZipcode(int zipcode)
	{
		this.zipcode = zipcode;
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public String getCoordinates()
	{
		return coordinates;
	}

	public void setCoordinates(String coordinates)
	{
		this.coordinates = coordinates;
	}
	
	public String toString()
	{
		return new String("" + this.id + " - " + this.address + ", " + this.zipcode + "("+this.customer.getName()+")");
	}

	public void setMark(boolean mark) {
		this.mark = mark;
		
	}
	
	public boolean isMark()
	{
		return mark;
	}
	
	public List<Destination> getAdjacencies(Destination destination)
	{
		int index = destinations.indexOf(destination);
		
		return adjList.get(index);
	}

}
