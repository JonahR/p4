import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and
 * edges.
 * 
 * @see #populateGraph(String) - loads a dictionary of words as vertices in the graph. - finds
 *      possible edges between all pairs of vertices and adds these edges in the graph. - returns
 *      number of vertices added as Integer. - every call to this method will add to the existing
 *      graph. - this method needs to be invoked first for other methods on shortest path
 *      computation to work.
 * @see #shortestPathPrecomputation() - applies a shortest path algorithm to precompute data
 *      structures (that store shortest path data) - the shortest path data structures are used
 *      later to to quickly find the shortest path and distance between two vertices. - this method
 *      is called after any call to populateGraph. - It is not called again unless new graph
 *      information is added via populateGraph().
 * @see #getShortestPath(String, String) - returns a list of vertices that constitute the shortest
 *      path between two given vertices, computed using the precomputed data structures computed as
 *      part of {@link #shortestPathPrecomputation()}. - {@link #shortestPathPrecomputation()} must
 *      have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String) - returns distance (number of edges) as an Integer for
 *      the shortest path between two given vertices - this is computed using the precomputed data
 *      structures computed as part of {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before invoking this
 *      method.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private GraphADT<String> graph;

    /**
     * A HashMap used to store the paths using different vertices as sources
     */
    private HashMap<String, PathMap> pathMaps;

    /**
     * This class is used to store the data when implementing the djikstra's algorithm using each
     * vertex as the source
     */
    private class PathMap {
        private String vertex;
        // Use HashMap to store the distance and predecessor
        private HashMap<String, Integer> distance;
        private HashMap<String, String> predecessors;

        private PathMap(String vertex) {
            this.vertex = vertex;
            this.distance = new HashMap<String, Integer>();
            this.predecessors = new HashMap<String, String>();
        }

        /**
         * A private helper method to get the shortest distance from source to vertex. If not
         * computed yet or unreachable, return "infinity".
         * 
         * @param vertex
         * @return the shortest distance from source to the vertex
         */
        private int getDistance(String vertex) {
            Integer d = distance.get(vertex);
            if (d == null)
                return Integer.MAX_VALUE;
            else
                return d;
        }

        /**
         * To choose the highest priority successor from a list of successors
         * @param vertexes
         * @return the successor with the shortest distance
         */
        private String getHighestPriority(Set<String> vertexes) {
            String temp = null;
            for (String vertex : vertexes) {
                if (temp == null) {
                    temp = vertex;
                } else {
                    if (getDistance(vertex) < getDistance(temp)) {
                        temp = vertex;
                    }
                }
            }
            return temp;
        }
    }

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the
     * object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
        this.pathMaps = new HashMap<String, PathMap>();
    }

    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the
     * dictionary as vertices and finding and adding the corresponding connections (edges) between
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph. Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent
     * {@link WordProcessor#isAdjacent(String, String)} If a pair is adjacent, adds an undirected
     * and unweighted edge between the pair of vertices in the graph.
     *
     * Log any issues encountered (print the issue details)
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added; return -1 if file not found or if
     *         encountering other exceptions
     */
    public Integer populateGraph(String filepath) {
        Stream<String> s = null;

		try{
			s = WordProcessor.getWordStream(filepath);
		} catch( IOException e ) {
			System.err.println("file could not be read");
			return -1;
		}

		ArrayList<String> list = new ArrayList<String>();
		list = (ArrayList) s.collect(Collectors.toList());
		for ( String node : list ) {
			graph.addVertex(node);
		}

		for ( int i = 0; i < list.size(); i++) 
			for(int j = i; j < list.size(); j++) 
				if(WordProcessor.isAdjacent(list.get(i), list.get(j))) 
					graph.addEdge(list.get(i), list.get(j));

		return list.size();

    }


    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit shortest path between cat and wheat
     * is the following list of words: [cat, hat, heat, wheat]
     *
     * If word1 = word2, List will be empty. Both the arguments will always be present in the graph.
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
        // if word1 == word2, return null list
        if (word1.equals(word2))
            return null;
        else {
            // Get the djikstra's map for word1
            PathMap map = pathMaps.get(word1);
            LinkedList<String> path = new LinkedList<String>();
            // If word2 is unreachable return null list
            if (map.predecessors.get(word2) == null)
                return null;
            // else go through the predecessors of word2 until it gets to word1
            String pred = word2;
            path.add(pred);
            while (map.predecessors.get(pred) != null) {
                pred = map.predecessors.get(pred);
                path.add(pred);
            }

            // return the reverse of the path list to show the correct order
            Collections.reverse(path);
            return path;
        }

    }

    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit distance of the shortest path between
     * cat and wheat, [cat, hat, heat, wheat] = 3 (the number of edges in the shortest path)
     *
     * Distance = -1 if no path found between words (true also for word1=word2) Both the arguments
     * will always be present in the graph.
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
        // If there's no path between word1 and word2, return -1
        if (getShortestPath(word1, word2) == null)
            return -1;

        // else return the length of the path
        return getShortestPath(word1, word2).size();
    }

    /**
     * Computes shortest paths and distances between all possible pairs of vertices. This method is
     * called after every set of updates in the graph to recompute the path information. Any
     * shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        for (String vertex : graph.getAllVertices()) {
            // Set Up before starting to compute
            PathMap map = new PathMap(vertex);
            // Create 2 sets to store the visited nodes and the unvisitednode set is basically a priority queue
            Set<String> visitedNodes = new HashSet<>();
            Set<String> unVisitedNodes = new HashSet<>();
            
            // Put the source vertex into the map
            map.distance.put(map.vertex, 0);
            unVisitedNodes.add(map.vertex);
            
            // While the priority queue is not empty
            while (unVisitedNodes.size() > 0) {
                // Get the minimal distance successor
                String node = map.getHighestPriority(unVisitedNodes);
                // Remove the successor and mark it as visited
                unVisitedNodes.remove(node);
                visitedNodes.add(node);
                for (String successor : graph.getNeighbors(node)) { // for each unvisited successor of the vertex
                    if (map.getDistance(node) + 1 < map.getDistance(successor)) {
                        // if the distance can be reduced, update the distance
                        map.distance.put(successor, map.getDistance(node) + 1);
                        map.predecessors.put(successor, node);
                        // put that successor into the priority queue
                        unVisitedNodes.add(successor);
                    }
                }
            }
            // Put the djikstra's table of that source into the list of maps
            pathMaps.put(vertex, map);
        }
    }
}
