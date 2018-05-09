package controlLayer;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.BasicConfigurator;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;

import dbLayer.DbMatrix;
import modelLayer.Destination;

public class MatrixCtrl
{
	private static MatrixCtrl									mCtrl;
	private static final String									ApiKey		= "AIzaSyD45RxUgPtE2o-NZD5zhi-bvsV6-7P2lMw";
	private static final int									ApiQPS		= 10;
	private static final GeoApiContext							Apicontext	= new GeoApiContext().setApiKey(ApiKey)
			.setQueryRateLimit(ApiQPS);

	private static final HashMap<HashSet<Destination>, long[]>	leMap		= new HashMap<HashSet<Destination>, long[]>();

	DbMatrix													DBmatrix;

	// Singleton
	public static MatrixCtrl getInstance()
	{
		if (mCtrl == null)
		{
			mCtrl = new MatrixCtrl();
		}
		return mCtrl;
	}

	private MatrixCtrl()
	{
		// Til apache Log4j - google maps api.
		BasicConfigurator.configure();
		DBmatrix = new DbMatrix();
	}

	private String[] getDistanceData(Destination a, Destination b)
	{
		DistanceMatrix data = null;
		String[] returnValue = { "FAILED", "", "" };

		try
		{
			DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(Apicontext);
			data = req.origins(a.getAddress() + " " + a.getZipcode())
					.destinations(b.getAddress() + " " + b.getZipcode()).mode(TravelMode.DRIVING)
					.avoid(RouteRestriction.FERRIES).language("da-DK").await();
		} catch (ApiException e)
		{
			// output += this.printError(e);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		if (data != null)
		{
			for (DistanceMatrixRow dm : data.rows)
			{
				for (DistanceMatrixElement dl : dm.elements)
				{
					// Fixes og test
					returnValue[0] = "OK";
					returnValue[1] = "" + dl.distance.inMeters;
					returnValue[2] = "" + dl.duration.inSeconds;
				}
			}
		}

		return returnValue;
	}

	public long[] getDistanceInfo(Destination a, Destination b)
	{
		long[] dData = new long[2]; // return data

		// Opret vores HashSet, så vi kan få os en hashkey.
		HashSet<Destination> theKey = new HashSet<Destination>();
		theKey.add(a);
		theKey.add(b);

		// Kig i HashMap
		long[] mapData = leMap.get(theKey);

		if (mapData != null)
		{
			//System.out.println("Fundet i HashMap");
			dData = mapData;
		} 
		else // Ikke fundet i hashMap
		{
			//System.out.println("Ikke fundet i HashMap");
			// Kig i sqlDB
			long[] dbData = DBmatrix.getFromMatrix(a, b);

			if (dbData != null)
			{
				/**
				 * Gem i HashMap
				 */
				dData[0] = dbData[0];
				dData[1] = dbData[1];

				//System.out.println("Fundet i DB.");

				leMap.put(theKey, dbData);
				//System.out.println("Puttet i HashMap");
			} else
			{
				// Hent fra google
				String[] gdata = getDistanceData(a, b);

				// hvis ok, ellers...
				if (gdata[0] == "OK")
				{
					//System.out.println("Hentet fra googlemaps");
					/**
					 * Gem Info i DB
					 */
					
					dData[0] = Long.parseLong(gdata[1]);
					dData[1] = Long.parseLong(gdata[2]);
					
					if (DBmatrix.saveInMatrix(a, b, dData[0], dData[1]))
					{
						//System.out.println("Gemt I DB");
						/**
						 * Gem i HashMap
						 */
					
						System.out.println("Puttet i HashMap");

						leMap.put(theKey, dData);
					}

				}
				/**
				 * Hvis fejl i get fra map, load fra DB eller get fra google.
				 * Bare retuner tomt array
				 */
			}
		}

		return dData;
	}

}