package modelLayer;

import java.sql.Date;

public class Order
{
	private int			id;
	private double		amount;
	private OrderMethod	orderMethode;
	private Destination	destination;
	private Date		datetime;

	public Order(double amount, Destination destination, Date datetime)
	{
		this.amount = amount;
		this.destination = destination;
		this.datetime = datetime;
	}
	public Order(int id, double amount, Destination destination, Date datetime)
	{
		this.id = id;
		this.amount = amount;
//		this.orderMethode = orderMethode;
		this.destination = destination;
		this.datetime = datetime;
	}

	public Order() {
		
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public OrderMethod getOrderMethode()
	{
		return orderMethode;
	}

	public void setOrderMethode(OrderMethod orderMethode)
	{
		this.orderMethode = orderMethode;
	}

	public Destination getDestination()
	{
		return destination;
	}

	public void setDestination(Destination destination)
	{
		this.destination = destination;
	}

	public Date getDatetime()
	{
		return datetime;
	}

	public void setDatetime(Date datetime)
	{
		this.datetime = datetime;
	}
	
	@Override
	public String toString()
	{
		return new String("" + this.id + " - " + this.amount + " - " + this.destination + " - " + this.datetime);
	}

}
