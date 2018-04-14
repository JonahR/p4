///////////////////////////////////////////////////////////////////////////////
//                   
// Title:            p4
// Files:            Graph.java, GraphADT.java, GraphProcessor.java,
//                   GraphProcessorTest.java, GraphTest.java, TestWordProcessorTest.java
//                   WordProcessor.java
//
// Semester:         Spring 2018
//
// Author:           Jonah Rueb, jrueb@wisc.edu
// Lecturer's Name:  Debra Deppeler CS400
//
///////////////////////////////////////////////////////////////////////////////



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
  
    // list of graph node verticies 
    public Set<GraphNode> vertices;

    
    /**
     * Create new GraphNode
     */
    private class GraphNode{        

        public E value;
        public Set<GraphNode> edges;
        
        /**
         * Node contains a value and list of edges
         * 
         * @param value
         */
        public GraphNode(E value) {
            this.value = value;
            this.edges = new HashSet<>();
        }     
    }
    
    /**
     * Create new Graph
     * 
     * Size and degree are set to 0
     * Creates an empty list of verticies for the Graph
     */
    public Graph(){
        vertices = new HashSet<>();                       
    }
    
    

    /**
     * Adds vertex to Graph
     */
    @Override
    public E addVertex(E vertex) {
        
        // vertex cannot be null
        if(vertex == null) return null;
        
        // vertex cannot be a duplicate
        for(GraphNode n : vertices) {
            if(n.value.equals(vertex)) return null;
        }
        
        // vertex must be a GraphNode 
        GraphNode node = new GraphNode(vertex);
      
        // GraphNode must be added to Graph
        vertices.add(node);
        
        return vertex;
    }

    /**
     * Removes vertex from Graph
     */
    @Override
    public E removeVertex(E vertex) { 
       
        // vertex cannot be null
        if(vertex == null) return null;      
        
        // must remove GraphNode from Graph 
        for(GraphNode n : vertices) {
            if(n.value.equals(vertex)) {              
                // Get list of Edges and remove refrences
                for(GraphNode e : n.edges) {
                    e.edges.remove(n);
                }
                vertices.remove(n);
                return vertex;
            }
        }      
        // vertex must be in Graph
        return null;
    }

    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        // graph does not allow equal vertex1 and vertex2 or null refrerences
        if(vertex1 == null || vertex2 == null || vertex1.equals(vertex2)) return false;
        
        // does Graph contain vertex1?
        boolean v1 = false;      
        // does Graph contain vertex2?
        boolean v2 = false;
        
        // temp for vertex1 and vertex2
        GraphNode temp1 = null;
        GraphNode temp2 = null;
        
        // Graph must contain vertex1
        for(GraphNode n : vertices) {
            if(n.value.equals(vertex1)) {
                v1 = true;
                temp1 = n;
            }
            if(n.value.equals(vertex2)) {
                v2 = true;
                temp2 = n;
            }
        }
        
        // edge must be added between vertices
        // .add() does not allow duplicates to be added
        if(v1 && v2) {
            temp1.edges.add(temp2);
            temp2.edges.add(temp1);
            return true;
        }
        
        // must return false if edge not added
        return false;              
    }    

    /**
     * Removes an Edge between vertices
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {       
        // vertex must be non-null and cannot equal eachother 
        if(vertex1 == null || vertex2 == null || vertex1.equals(vertex2)) return false;
        
        // vertex1 and vertex2 must exist
        boolean v1 = false;      
        boolean v2 = false;
        GraphNode temp1 = null;
        GraphNode temp2 = null;
        
        for(GraphNode n : vertices) {
            if(n.value.equals(vertex1)) {
                v1 = true;
                temp1 = n;
            }
            if(n.value.equals(vertex2)) {
                v2 = true;
                temp2 = n;
            }
        }

        
        // must remove edge if vertices exist
        if(v1 && v2) {
            temp1.edges.remove(temp2);
            temp2.edges.remove(temp1);  
            return true;
        }        
        // must remove an edge
        return false;
    }

    /**
     * Returns if vertex1 is adjacent to vertex2
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        // vertex must be non-null and cannot equal eachother 
        if(vertex1 == null || vertex2 == null || vertex1.equals(vertex2)) return false;
        
        // vertex1 and vertex2 must exist
        boolean v1 = false;      
        boolean v2 = false;
        GraphNode temp1 = null;
        GraphNode temp2 = null;
        
        for(GraphNode n : vertices) {
            if(n.value.equals(vertex1)) {
                v1 = true;
                temp1 = n;
            }
            if(n.value.equals(vertex2)) {
                v2 = true;
                temp2 = n;
            }
        }
        
        if(v1 && v2) {
            if(temp1.edges.contains(temp2)) return true;
        }      

        return false;
    }

    /**
     * Returns the edges of a vertices as an Iterator
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        // null values not allowed
        if(vertex == null) return null;
        
        // vertex must exist
        boolean v = false;      
        GraphNode temp = null;
        
        for(GraphNode n : vertices) {
            if(n.value.equals(vertex)) {
                v = true;
                temp = n;
            }
        }
        
        // if vertex exists retur the iterable list of its edges
        // vertex1 and vertex2 must exist
        if(v) {
            List<E> list = new ArrayList<>();
            for(Graph<E>.GraphNode edge : temp.edges) {
                list.add(edge.value);
            }
            return list;
        }
        
        return null;
    }

    /**
     * Returns all vertices in an Iterator
     */
    @Override
    public Iterable<E> getAllVertices() {
        List<E> list = new ArrayList<>();
        for(Graph<E>.GraphNode vertice : vertices) {
            list.add(vertice.value);
        }
        return list;
    }
}


