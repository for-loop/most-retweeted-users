package graph;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

/** Vertex class
 * 
 * @author Hiro Watari
 *
 */
class Vertex {

	/** user ID */
	private int id;
	
	/** Edges that comes into this node */
	private List<Edge> edges;
	
	/** Set of vertices that are connected to this vertex; multi-edges are ignored */
	private HashSet<Vertex> connectedBy;
	
	/** A cache of incoming edges mapped by vertices */
	private HashMap<Vertex,Integer> numEdges;
	
	/**
	 * ctor
	 * @param num An ID number
	 */
	public Vertex(int num)
	{
		id = num;
		edges = new LinkedList<Edge>();
		numEdges = new HashMap<Vertex,Integer>();
		connectedBy = new HashSet<Vertex>();
	}
	
	/** Copy ctor
	 * Return a copy of this node
	 * @return copyThis
	 */
	public Vertex copy()
	{
		Vertex copyThis = new Vertex(this.id);
		copyThis.edges = this.getEdges();
		copyThis.numEdges = this.getNumEdges();
		copyThis.connectedBy = this.getConnectedBy();
		return copyThis;
	}
	
	/** Add a directed edge from other vertex to this vertex
	 * 
	 * @param other Vertex object
	 */
	public void addEdge(Vertex other)
	{
		Edge e = new Edge(other, this);
		edges.add(e);
		
		connectedBy.add(other);
		
		// Update number of edges
		incrementNumEdgesFrom(other);
	}
	
	/**
	 * Update number of edges from particular vertex
	 * @param v Vertex object
	 */
	private void incrementNumEdgesFrom(Vertex v)
	{
		Integer cnt = numEdges.get(v);
		if (cnt == null)
			numEdges.put(v, 1);
		else
			numEdges.put(v, cnt+1);
	}
	
	/** Get ID
	 * 
	 * @return id
	 */
	public int getID()
	{
		return id;
	}
	
	/** Get a number of incoming edges by a particular vertex
	 * 
	 * @param other A vertex from which the edge comes in
	 * @return number of incoming edges from the other vertex
	 */
	public int getNumEdgesFrom(Vertex other)
	{
		return numEdges.get(other);
	}
	
	/** Return the copy of edge list
	 * Do not return the private instance variable
	 * @return copyEdges
	 */
	public List<Edge> getEdges()
	{
		/* Prevent exposure of private instance variable */
		List<Edge> copyEdges = new LinkedList(edges);
		return copyEdges;
	}

	/** Return the copy of connected set of vertices
	 * Do not return the private instance variable
	 * @return copyConnectedBy
	 */
	public HashSet<Vertex> getConnectedBy()
	{
		/* Prevent exposure of private instance variable */
		HashSet<Vertex> copyConnectedBy = new HashSet<Vertex>(connectedBy);
		return copyConnectedBy;
	}
	
	/** Return the copy of incoming edges
	 * Do not return the private instance variable
	 * @return copyInEdges
	 */
	public HashMap<Vertex,Integer> getNumEdges()
	{
		/* Prevent exposure of private instance variable */
		HashMap<Vertex,Integer> copyInEdges = new HashMap<Vertex,Integer>(numEdges);
		return copyInEdges;
	}
}
