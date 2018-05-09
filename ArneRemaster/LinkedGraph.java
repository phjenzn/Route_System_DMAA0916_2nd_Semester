package ArneRemaster;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedGraph implements Graph{
	
	
	
	  private List<Vertex> vertices;
	   private int noVer;
	   private int noOfEdges;
	   private List<LinkedList<Vertex>> adjList;

	   public LinkedGraph(int noVer)
	   {
	        init(noVer);
	        noOfEdges = 0;
	   }

	   private void init(int noVer)
	   {
	        vertices = new ArrayList<Vertex>(noVer);
	        this.noVer = noVer; // - 1;
	        adjList = new ArrayList<LinkedList<Vertex>>(noVer);      
	   }
	
	   public void addVertex(Vertex vertex)
	   {
	        vertices.add(vertex);
	        adjList.add(new LinkedList<Vertex>());
	        noVer++;
	   }

	@Override
	public void addEdge(Vertex startVertex, Vertex endVertex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsVertex(Vertex vertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdjacent(Vertex startVertex, Vertex endVertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Vertex> getAdjacencies(Vertex vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNoOfVertices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNoOfEdges() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unMark() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Dfs(Vertex v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vertex> Dfs(Vertex v, List<Vertex> l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Bfs(Vertex v) {
		// TODO Auto-generated method stub
		
	}

}
