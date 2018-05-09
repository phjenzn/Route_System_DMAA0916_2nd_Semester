package ArneRemaster;

import java.util.LinkedList;

public class Main {
	
	public static void main(String[] args)
	{
	
	Graph ruter = new LinkedGraph(10);
    LinkedList<Vertex> l;

    System.out.println("IsEmpty() (should be true): " + ruter.isEmpty());
    
    
    // Add dest. 
    
    Vertex logstor = new Vertex("Logstor"); 
    Vertex aalborg = new Vertex("Aalborg"); 
    Vertex gistrup = new Vertex("Gistrup"); 
    Vertex vandland= new Vertex("Vandland"); 
    
   ruter.addVertex(logstor);
   ruter.addVertex(aalborg);
   ruter.addVertex(gistrup);
   ruter.addVertex(vandland);
   
    
    
	}
}
