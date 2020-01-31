package graph;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import util.GraphLoader;
import java.util.HashSet;

/** Test cases for TwitterGraph
 * 
 * @author Hiro Watari
 *
 */
public class TwitterGraphTester {
	private String smallFile = "data/small_test_graph.txt";
	private String largeFile = "data/twitter_higgs.txt";
	
	TwitterGraph emptySocialNet;
	TwitterGraph simpleSocialNet;
	TwitterGraph smallSocialNet;
	TwitterGraph largeSocialNet;
	TwitterGraph customSocialNet;
	TwitterGraph stronglyConnectedSocialNet;
	
	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception
	{
		emptySocialNet = new TwitterGraph();
		simpleSocialNet = new TwitterGraph();
		smallSocialNet = new TwitterGraph();
		largeSocialNet = new TwitterGraph();
		customSocialNet = new TwitterGraph();
		stronglyConnectedSocialNet = new TwitterGraph();
		
		for (int i=0; i<5; i++)
			simpleSocialNet.addVertex(i);
		simpleSocialNet.addEdge(0, 1);
		simpleSocialNet.addEdge(1, 0);
		simpleSocialNet.addEdge(2, 1);
		simpleSocialNet.addEdge(2, 1); // duplicate
		simpleSocialNet.addEdge(2, 3);
		simpleSocialNet.addEdge(4, 3);
		
		GraphLoader.loadGraph(smallSocialNet, smallFile);
		GraphLoader.loadGraph(largeSocialNet, largeFile);
		
		for (int i=0; i<7; i++)
			customSocialNet.addVertex(i);
		customSocialNet.addEdge(1, 0);
		customSocialNet.addEdge(2, 0);
		customSocialNet.addEdge(3, 0);
		customSocialNet.addEdge(4, 0);
		customSocialNet.addEdge(5, 4);
		customSocialNet.addEdge(6, 4);
		
		// edge case
		for (int i=0; i<4; i++)
			stronglyConnectedSocialNet.addVertex(i);
		stronglyConnectedSocialNet.addEdge(0, 1);
		stronglyConnectedSocialNet.addEdge(1, 2);
		stronglyConnectedSocialNet.addEdge(2, 3); // duplicate
		stronglyConnectedSocialNet.addEdge(2, 3);
		stronglyConnectedSocialNet.addEdge(3, 0); // triplicate
		stronglyConnectedSocialNet.addEdge(3, 0);
		stronglyConnectedSocialNet.addEdge(3, 0);
	}
	
	/** Test if the correct ID is in the node */
	@Test
	public void testID()
	{
		try {
			simpleSocialNet.getVertex(null).getID();
			fail("Check null");
		} catch (NullPointerException e) {
			
		}
		try {
			simpleSocialNet.getVertex(5).getID();
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			simpleSocialNet.getVertex(-1).getID();
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		assertEquals("Testing ID 1 on simple social network",1,simpleSocialNet.getVertex(1).getID());
		assertEquals("Testing ID 11 on small social network",11,smallSocialNet.getVertex(11).getID());
		assertEquals("Testing ID 298960 on large social network",298960,largeSocialNet.getVertex(298960).getID());
	}
	
	/** Test if the correct IDs of the edges are associated with a vertex */
	@Test
	public void testGetEdges()
	{
		assertEquals("Testing number of edges connected to node 1 on simple social network",3,simpleSocialNet.getVertex(1).getEdges().size());
		assertEquals("Testing number of edges connected to node 8 on small social network",3,smallSocialNet.getVertex(8).getEdges().size());
		
		/*
		List<Edge> lst = largeSocialNet.getVertex(18).getEdges();
		for (Edge e : lst)
			System.out.println(e.getEnd().getID());
		*/
		assertEquals("Testing start vertex connected to vertex 0 on simple social network",1,simpleSocialNet.getVertex(0).getEdges().get(0).getStart().getID());
		assertEquals("Testing end vertex connected to vertex 0 on simple social network",0,simpleSocialNet.getVertex(0).getEdges().get(0).getEnd().getID());
	}
	
	/** Test number of incoming edges from specific Vertex objects */
	@Test
	public void testGetNumEdgesFrom()
	{
		assertEquals("Testing number of incoming edges for vertex 1 from vertex 2 on simple social network",2,simpleSocialNet.getVertex(1).getNumEdgesFrom(simpleSocialNet.getVertex(2)));
		assertEquals("Testing number of incoming edges for vertex 8 from vertex 7 on small social network",1,smallSocialNet.getVertex(8).getNumEdgesFrom(smallSocialNet.getVertex(7)));
	}
	
	/** Test getInDegrees method */
	@Test
	public void testGetInDegrees()
	{
		assertEquals("Testing in-degrees for vertex 0 in simple social network",1,simpleSocialNet.getInDegree(simpleSocialNet.getVertex(0)));
		assertEquals("Testing in-degrees for vertex 8 in small social network",3,smallSocialNet.getInDegree(smallSocialNet.getVertex(8)));
	}
	
	/** Test print the top 10 vertices with the highest in-degrees */
	@Test
	public void testPrintInDegrees()
	{
		System.out.println("---\nTop 10 vertices sorted by the highest in-degrees:");
		largeSocialNet.printInDegreesSorted(10, true);
	}
	
	/** Test the size */
	@Test
	public void testSize()
	{
		assertEquals("Testing size of simple social network after export",5,simpleSocialNet.exportGraph().size());
		assertEquals("Testing size of small social network after export",smallSocialNet.getNumVertices(),smallSocialNet.exportGraph().size());
	}
	
	/** Test print the minimum set */
	@Test
	public void testPrintMinimumSet()
	{
		TwitterGraph g = largeSocialNet;
		
		HashSet<Vertex> minSet = g.findMinimumSet();
		
		System.out.println("---\nVertices in a min set:");
		
		for (Vertex v : minSet)
			System.out.println(v.getID());
		
		System.out.println("Minimum number of vertices: " + minSet.size() + " of " + g.getNumVertices());
	}
	
	/** Test the minimum set algorithm */
	@Test
	public void testMinimumSet()
	{
		HashSet<Vertex> minSet = simpleSocialNet.findMinimumSet();
		assertEquals("Testing Vertex 3 in simple social network",true,minSet.contains(simpleSocialNet.getVertex(3)));
		assertEquals("Testing Vertex 4 in simple social network",false,minSet.contains(simpleSocialNet.getVertex(4)));
		
		minSet = customSocialNet.findMinimumSet();
		assertEquals("Testing Vertex 4 in custom social network",true,minSet.contains(customSocialNet.getVertex(4)));
		assertEquals("Testing Vertec 6 in custom social network",false,minSet.contains(customSocialNet.getVertex(6)));
		
		minSet = stronglyConnectedSocialNet.findMinimumSet();
		assertEquals("Testing number of minimum set expected by this algorithm (actual min is 2)",3,minSet.size());
	}
}
