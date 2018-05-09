package ArneRemaster;

import java.util.List;

public interface Graph {

	void addVertex(Vertex vertex);

	void addEdge(Vertex startVertex, Vertex endVertex);

	boolean containsVertex(Vertex vertex);

	boolean isAdjacent(Vertex startVertex, Vertex endVertex);

	List<Vertex> getAdjacencies(Vertex vertex);

	boolean isEmpty();

	int getNoOfVertices();

	int getNoOfEdges();

	void Clear();

	void unMark();

	void Dfs(Vertex v);

	List<Vertex> Dfs(Vertex v, List<Vertex> l);

	void Bfs(Vertex v);


}
