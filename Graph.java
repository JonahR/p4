import java.util.HashMap;
import java.util.HashSet;
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
    private Set<GraphNode> vertices;
    // number of edges
    private int size;
    // number of verticies
    private int degree;
    
    /**
     * Create new GraphNode
     */
    private class GraphNode{        

        private E value;
        private Set<GraphNode> edges;
        
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
        this.size = 0;
        this.degree = 0;
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
            if(n.value == vertex) return null;
        }
        
        // vertex must be a GraphNode 
        GraphNode node = new GraphNode(vertex);
      
        // GraphNode must be added to Graph
        vertices.add(node);
        
        // degree of graph increases
        degree++;
        
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
            if(n.value == vertex) {
                vertices.remove(n);
                return vertex;
            }
        }      
        // vertex must be in Graph
        return null;
    }

    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        // does Graph contain vertex1?
        boolean v1 = false;      
        // does Graph contain vertex2?
        boolean v2 = false;
        
        // temp for vertex1 and vertex2
        GraphNode temp1 = null;
        GraphNode temp2 = null;
        
        // Graph must contain vertex1
        for(GraphNode n : vertices) {
            if(n.value == vertex1) {
                v1 = true;
                temp1 = n;
            }
        }
        
        // Graph must contain vertex2
        for(GraphNode n : vertices) {
            if(n.value == vertex2) {
                v2 = true;
                temp2 = n;
            }
        }
        
        // edge must be added between vertices
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
        // vertex1 and vertex2 must exist
        boolean v1 = false;      
        boolean v2 = false;
        GraphNode temp1 = null;
        GraphNode temp2 = null;
        
        for(GraphNode n : vertices) {
            if(n.value == vertex1) {
                v1 = true;
                temp1 = n;
            }
        }
        for(GraphNode n : vertices) {
            if(n.value == vertex2) {
                v2 = true;
                temp2 = n;
            }
        }
        
        // must remove edge if vertices exist
        if(v1 && v2) {
            for(GraphNode n : temp1.edges) {
                // temp1 edges must contain temp2
                if(n.value == temp2) {
                    temp1.edges.remove(temp2);
                    temp2.edges.remove(temp1);
                }                
            }           
        }        
        // must remove an edge
        return false;
    }

    /**
     * Returns if vertex1 is adjacent to vertex2
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        
        // vertex1 must exist
        for(GraphNode n : vertices) {
            if(n.value == vertex1) {
                for(GraphNode edges : n.edges) {
                    if(n.value == edges.value) return true;
                }
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        return null;
    }
}