package graph;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

/**
 * A graph that represents a social network in Twitter
 * @author Hiro Watari
 * 
 */
public class TwitterGraph implements Graph {

	/** vertices in the Twitter network */
	private HashMap<Integer,Vertex> vertices;
	
	/** in-degrees mapped by vertex */
	private HashMap<Vertex,Integer> inDegrees;
	
	/** 
	 * Default ctor
	 */
	public TwitterGraph()
	{
		vertices = new HashMap<Integer,Vertex>();
		inDegrees = new HashMap<Vertex,Integer>();
	}
	
	/**
	 * Get number of vertices
	 * @return number of vertices
	 */
	public int getNumVertices()
	{
		return vertices.size();
	}
	
	/**
	 * Get a vertex associated with and ID number 
	 * @param num
	 * @return Vertex object
	 */
	public Vertex getVertex(Integer num)
	{
		if (num == null)
			throw new NullPointerException("param cannot be null");
		
		if (!vertices.containsKey(num))
			throw new IndexOutOfBoundsException("Invalid param used to access vertices in TwitterGraph");
		
		return vertices.get(num);
	}

	/**
	 * Get in-degree for Vertex v
	 * @param v Vertex object
	 * @return in-degree
	 */
	public int getInDegree(Vertex v)
	{
		return inDegrees.get(v);
	}
	
	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		if (!vertices.containsKey(num)) {
			Vertex v = new Vertex(num);
			vertices.put(num, v);
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		if (!vertices.containsKey(from) || !vertices.containsKey(to))
			throw new IllegalArgumentException("Both vertices must exist in the graph before adding an edge");
		
		Vertex vertexFrom = vertices.get(from);
		Vertex vertexTo = vertices.get(to);
		vertexTo.addEdge(vertexFrom);
		
		incrementInDegree(vertexTo);
	}
	
	/**
	 * Increment in-degree for particular vertex
	 * @param v Vertex object
	 */
	private void incrementInDegree(Vertex v)
	{
		Integer cnt = inDegrees.get(v);
		if (cnt == null)
			inDegrees.put(v, 1);
		else
			inDegrees.put(v, cnt+1);
	}
	
	/** Find a minimum set of vertices
	 * Use a "greedy" approach for efficiency
	 * @return minSet A minimum set of vertices
	 */
	public HashSet<Vertex> findMinimumSet()
	{
		int nVert = getNumVertices();
		HashSet<Vertex> visited = new HashSet<Vertex>();	// Keep track of visited vertices
		HashSet<Vertex> minSet = new HashSet<Vertex>(); 	// Keeps a minimum set of vertices
		
		// Sort vertices by in-degrees in descending order
		Map<Vertex,Integer> sorted = MapUtil.sortByValue(inDegrees, true);
		
		// list each vertex in sorted order while including neighbors until all vertices are accounted for
		for (Vertex v : sorted.keySet()) {
			//System.out.println("Visiting "+v.getID()+" ("+sorted.get(v)+")");
			if (visited.size() == nVert) return minSet;
			if (!minSet.contains(v))
				updateTracking(v, visited, minSet);
		}
		return minSet;
	}
	
	/** Update tracking variables
	 * a helper method for findMinimumSet()
	 * @param v Vertex object
	 * @param visited A set of vertices that have been visited
	 * @param minSet A minimum set of vertices
	 */
	private void updateTracking(Vertex v, HashSet<Vertex> visited, HashSet<Vertex> minSet)
	{
		visited.add(v);
		minSet.add(v);
		visitNeighbors(v.getConnectedBy(), visited);
	}
	
	/** Visit neighbors of a particular vertex
	 * a helper method for findMinimumSet()
	 * @param v Vertex object
	 * @param visited A set of vertices that have been visited
	 */
	private void visitNeighbors(HashSet<Vertex> neighbors, HashSet<Vertex> visited)
	{
		for (Vertex n : neighbors)
			visited.add(n);
	}
	
	/**
	 * Print in-degrees (ordered by vertex number)
	 */
	public void printInDegrees()
	{
		for (Vertex v : vertices.values())
			System.out.println(v.getID() + " " + inDegrees.get(v));
	}
	
	/**
	 * Print in-degrees sorted by value
	 * @param maxNum Number of in-degrees to print
	 * @param descOrder true for descending order
	 */
	public void printInDegreesSorted(int maxNum, boolean descOrder)
	{
		Map<Vertex,Integer> sorted = MapUtil.sortByValue(inDegrees, descOrder);
		int cnt = 0;
		for (Vertex v : sorted.keySet()) {
			if (cnt >= maxNum) break;
			System.out.println(v.getID() + " (" + sorted.get(v)+")");
			cnt++;
		}
	}
	
	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		HashMap<Integer,HashSet<Integer>> graph = new HashMap<Integer,HashSet<Integer>>();
		
		for (Vertex v : vertices.values()) {
			HashSet<Integer> hs = new HashSet<Integer>();
			
			for (Edge e : v.getEdges())
				hs.add(e.getEnd().getID());
			
			graph.put(v.getID(), hs);
		}
		return graph;
	}
}
