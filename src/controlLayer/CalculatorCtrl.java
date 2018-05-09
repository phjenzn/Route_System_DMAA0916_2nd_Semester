package controlLayer;

public class CalculatorCtrl
{

	private static CalculatorCtrl cCtrl;

	// Singleton
	public static CalculatorCtrl getInstance()
	{
		if (cCtrl == null)
		{
			cCtrl = new CalculatorCtrl();
		}
		return cCtrl;
	}

}
