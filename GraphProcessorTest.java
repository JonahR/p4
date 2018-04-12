import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// adjust tests for the actual shortest paths
// fix file paths
// test anything added to the GraphProcessor class
// when would input stream throw exception
// add file headers

/**
 * Junit test class to test the GraphProcessor and WordProcessor classes
 *
 * @author Haley (hlrichards@wisc.edu)
 */
public class GraphProcessorTest 
{
	private GraphProcessor graphProcessor;
	private WordProcessor wordProcessor;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{

	}

	@Before
	public void setUp() throws Exception 
	{
		this.graphProcessor = new GraphProcessor();
		this.wordProcessor = new WordProcessor();
	}

	@After
	public void tearDown() throws Exception 
	{
		this.graphProcessor = null;
		this.wordProcessor = null;
	}

	/**
	 * This method tests whether the populatesGraph() method returns -1 
	 * if the file path is not valid
	 */
	@Test
	public void test1_populates_graph_throws_FileNotFound() 
	{
		int expected = -1;
		int actual = graphProcessor.populateGraph("RandomFilePath321");
		if(actual != expected)
			fail("The method failed to throw an exception for an invalid file path.");
	}
	
	/**
	 * This method tests that the correct number of words are added to the graph
	 * from the word_list.txt file
	 */
	@Test
	public void test2_populates_graph_correctly() 
	{
		//may need to fix the file path
		int wordCount = graphProcessor.populateGraph("word_list.txt");
		if(wordCount != 441)
			fail("populatesGraph() did not add the correct number of words to the graph");		
	}
	
	/**
	 * Tests that the getShortestPath() method returns an empty list if the
	 * graph hasn't been populated yet (and therefore is empty) and is given
	 * random words
	 */
	@Test
	public void test3_returns_empty() 
	{
		List<String> list = graphProcessor.getShortestPath("random", "word");
		if(list != null)
			fail("getShortestPath() fails to return an empty list if the graph has"
					+ " not yet been populated, if the parameters are not null");
	}
	
	/**
	 * Tests that the getShortestPath() method returns an empty list when
	 * word1 and word2 are the same
	 */
	@Test
	public void test4_returns_empty_with_same_params()
	{
		// graphProcessor.populateGraph(xxxxxxxxxx);
		// may need to be changed to incorporate words actually in the list
		List<String> list = graphProcessor.getShortestPath("random", "random");
		if(list != null)
			fail("getShortestPath() fails to return an empty list if the graph has "
					+ "been populated, but word1 and word2 are the same");
	}
	
	/**
	 * Tests that the getShortestPath() method returns the correct path between
	 * word1 and word2
	 */
	@Test
	public void test5_returns_correct_shortestPath() 
	{
		// graphProcessor.populateGraph(xxxxxx);
		// words may need to be changed
		List<String> actual = graphProcessor.getShortestPath("best", "piece");
		List<String> expected = null;
		if(actual != expected)
			fail("getShortestPath() fails to return the correct shortest path");
	}
	
	/**
	 * Tests that the getShortestPath() method returns an empty list if the path doesn't
	 * exist between word1 and word2
	 */
	@Test
	public void test6_returns_empty_if_no_path() 
	{
		// graphProcessor.populateGraph(xxxxxx);
		// change to two words without a path
		List<String> list = graphProcessor.getShortestPath("best", "piece");
		if(list != null)
			fail("getShortestPath() fails to return an empty list if there is not a path"
					+ "between the two words");
	}
		
	/**
	 * Tests that the getShortestDistance() method returns -1 if the graph hasn't been
	 * populated yet
	 */
	@Test
	public void test7_distance_on_empty_graph() 
	{
		int dist = graphProcessor.getShortestDistance("best", "piece");
		if(dist != -1)
			fail("getShortestPath() fails to return -1 if the graph is empty. Returned"
					+ dist + " instead");
	}
	
	/**
	 * Tests that the getShortestDistance() method returns -1 if there isn't a 
	 * path between the two words
	 */
	@Test
	public void test8_distance_when_no_path() 
	{
		//graphProcessor.populateGraph(xxxxxxxxxxxxx)
		// may need to be changed to find 2 words without a path
		int dist = graphProcessor.getShortestDistance("best", "tonnage");
		if(dist != -1)
			fail("getShortestPath() fails to return -1 if there is no path between"
					+ "the two words. Returned " + dist + " instead");
	}
	
	/**
	 * Tests that the getShortestDistance() method returns -1 if the words are the same
	 */
	@Test
	public void test9_distance_between_same_words() 
	{
		//graphProcessor.populateGraph(xxxxxxxxxxxxx)
		// may need to be changed to find 2 words without a path
		int dist = graphProcessor.getShortestDistance("tonnage", "tonnage");
		if(dist != -1)
			fail("getShortestPath() fails to return -1 if the words are the same. "
					+ "Returned " + dist + " instead");
	}
	
	/**
	 * Tests that the correct distance is returned by the getShortestDistance() method
	 */
	@Test
	public void test10_shortest_distance_correct() 
	{
		//graphProcessor.populateGraph(xxxxxxxxxxxxx)
		// may need to be changed to find 2 words without a path
		int actual = graphProcessor.getShortestDistance("shanny", "shinny");
		int expected = 1;
		if(actual != expected)
			fail("Actual distance: " + actual + ". Expected distance: " + expected);
	}
	
	/**
	 * Tests the the WordProcessor isAdjacent() method correctly recognizes that
	 * the same two words are not adjacent
	 */
	@Test
	public void test11_adjacency_between_same_words()
	{
		boolean expected = false;
		boolean actual = wordProcessor.isAdjacent("hi", "hi");
		if(expected != actual)
			fail("The isAdjacent() method failed to recognize that the same words are not"
					+ "adjacent");
	}
	
	/**
	 * Tests that the WordProcessor isAdjacent() method correctly recognizes that
	 * two null words are not adjacent 
	 */
	@Test
	public void test11_adjacency_between_null_words()
	{
		boolean expected = false;
		boolean actual = wordProcessor.isAdjacent(null, null);
		if(expected != actual)
			fail("The isAdjacent() method failed to recognize that the null words are not"
					+ "adjacent");
	}
	
	/**
	 * Tests that the isAdjacent() method correctly recognizes that a replacement 
	 * adjacency
	 */
	@Test
	public void test12_adjacency_by_replacement() 
	{
		boolean expected = true;
		boolean actual = wordProcessor.isAdjacent("take", "bake");
		if(expected != actual)
			fail("The isAdjacent() method failed to recognize that there is an adjacency"
					+ "by replacement");	
	}
	
	/**
	 * Tests that the isAdjacent() method does not recognizes adjacency 
	 * when it does not exist
	 */
	@Test
	public void test13_no_adjacency_between_words() 
	{
		boolean expected = false;
		boolean actual = wordProcessor.isAdjacent("talk", "shop");
		if(expected != actual)
			fail("The isAdjacent() method incorrectly found adjacency"
					+ "between two unadjacent words");	
	}
	
	/**
	 * Tests that the isAdjacent() method recognizes adjacency by insertion
	 */
	@Test
	public void test14_insertion_adjacency()
	{
		boolean expected = true;
		boolean actual = wordProcessor.isAdjacent("bran", "brain");
		if(expected != actual)
			fail("The isAdjacent() method failed to recognzie adjacency"
					+ "by insertion between two words");
	}
	
	/**
	 * Tests that the isAdjacent() method recognizes adjacency by deletion
	 */
	@Test
	public void test15_deletion_adjacency()
	{
		boolean expected = true;
		boolean actual = wordProcessor.isAdjacent("train", "rain");
		if(expected != actual)
			fail("The isAdjacent() method failed to recognzie adjacency"
					+ "by deletion between two words");
	}
	
}

