import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// adjust tests for the actual shortest paths
// fix file paths
// test anything added to the GraphProcessor class
// when would input stream throw exception
// add headers to each file

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
	 * when the file path is not valid
	 */
	@Test
	public void test1_populates_graph_invalid_file() 
	{
		int expected = -1;
		int actual = graphProcessor.populateGraph("RandomFilePath321");
		if(actual != expected)
			fail("The method failed to throw an exception for an invalid file path.");
	}
	
	/**
	 * This method tests that the correct number of words are added to the graph
	 * from the word_list.txt file, a large number of words
	 */
	@Test
	public void test2_populates_large_graph_correctly() 
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
		graphProcessor.populateGraph("test_words.txt");
		graphProcessor.shortestPathPrecomputation();
		// may need to be changed to incorporate words actually in the list
		List<String> list = graphProcessor.getShortestPath("gate", "gate");
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
		graphProcessor.populateGraph("test_words.txt");
		graphProcessor.shortestPathPrecomputation();
		// words may need to be changed
		List<String> actual = graphProcessor.getShortestPath("gate", "bin");
		List<String> expected = null;
		expected.add("GATE");
		expected.add("BATE");
		expected.add("BAT");
		expected.add("BAN");
		expected.add("BIN");
	
		boolean same = false;
		for(int x = 0; x < expected.size(); x++) 
		{
			if(!(expected.get(x).equalsIgnoreCase(actual.get(x))))
			{
				if(same == true)
					same = false;
			}		
		}
		
		if(!same)
			fail("getShortestPath() fails to return the correct shortest path");
	}
	
	/**
	 * Tests that the getShortestPath() method returns an empty list if the path doesn't
	 * exist between word1 and word2
	 */
	@Test
	public void test6_returns_empty_if_no_path() 
	{
		graphProcessor.populateGraph("test_words.txt");
		graphProcessor.shortestPathPrecomputation();
		List<String> list = graphProcessor.getShortestPath("ran", "hungry");
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
		graphProcessor.populateGraph("test_words.txt");
		graphProcessor.shortestPathPrecomputation();
		int dist = graphProcessor.getShortestDistance("plat", "hunger");
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
		graphProcessor.populateGraph("test_words.txt");
		graphProcessor.shortestPathPrecomputation();
		int dist = graphProcessor.getShortestDistance("ran", "ran");
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
		graphProcessor.populateGraph("test_words.txt");
		graphProcessor.shortestPathPrecomputation();
		int actual = graphProcessor.getShortestDistance("plite", "rat");
		int expected = 4;
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
	
	/** 
	 * Tests that the populateGraph() method works correctly with a medium number
	 * of words
	 */
	@Test
	public void test16_populate_medium() 
	{
		int wordCount = graphProcessor.populateGraph("test_words.txt");
		if(wordCount != 15)
			fail("populatesGraph() did not add the correct number of words to the graph");	
	}
	
	/**
	 * Tests that the getWordStream() method correctly returns the stream
	 */
	@Test
	public void test17_word_stream_correct() 
	{
		try {
			Stream<String> test = WordProcessor.getWordStream("stream_test.txt");
			List<String> actual = test.collect(Collectors.toList());
			List<String> expected = new ArrayList<String>();
			expected.add("HAPPY");
			expected.add("GARAGE");
			expected.add("YELLOW");
			expected.add("BLUE");
			expected.add("GREEN");
			expected.add("EXTRA");
			expected.add("PEOPLE");
			expected.add("HOUSE");
			boolean same = false;
			for(int x = 0; x < expected.size(); x++) 
			{
				if(!(expected.get(x).equalsIgnoreCase(actual.get(x))))
				{
					same = false;
				}		
			}
			if(!same)
				fail("getWordStream() failed to return the correct stream of words");
		} catch (IOException e) {
			fail("getWordStream() failed to find the correct file");
		}
	}
	
	/**
	 * Tests that the getWordStream() method correctly throws an exception when 
	 * the file does not exist
	 */
	@Test
	public void test18_word_stream_throws() 
	{
		try {
			Stream<String> test = WordProcessor.getWordStream("RandomFileXYZ123");	
		} catch (IOException e) {
			fail("getWordStream() did not throw an IOException when the file did not exist");
		}
	}
}
