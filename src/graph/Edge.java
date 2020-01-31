package graph;

/** Edge class
 * 
 * @author Hiro Watari
 *
 */
class Edge {

	/** start node for a directed edge */
	private Vertex start;
	/** end node for the directed edge */
	private Vertex end;
	
	/** ctor
	 * 
	 * @param from Start node
	 * @param to End node
	 */
	public Edge(Vertex from, Vertex to)
	{
		start = from;
		end = to;
	}
	
	public Vertex getStart()
	{
		/* Prevent exposure of private instance variable */
		return start.copy();
	}
	
	public Vertex getEnd()
	{
		/* Prevent exposure of private instance variable */
		return end.copy();
	}
}