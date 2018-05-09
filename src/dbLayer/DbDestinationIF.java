package dbLayer;

import modelLayer.Destination;

public interface DbDestinationIF {
			// get
			public Destination getDestination(int id) throws Exception;
}
