
//TODO:
//  (1) Implement the graph!
//  (2) Update this code to meet the style and JavaDoc requirements.
//			Why? So that you get experience with the code for a graph!
//			Also, this happens a lot in industry (updating old code
//			to meet your new standards).

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.commons.collections15.Factory;


import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;



//import java.util.*;


/**
 * Class which implements the functions of the graph.
 * @author Kidus Tensay
 */
class FlexGraph implements Graph<GraphNode,GraphEdge>, DirectedGraph<GraphNode,GraphEdge> {
    //HINTS:
    //1 -- Learn about what methods are available in Java's LinkedList
    //	 class before trying this, a lot of them will come in handy...
    //2 -- You may want to become friendly with the ListIterator as well.
    //	 This iterator support things beyond Iterator, e.g. removal...


    //you may not have any other class variables, only this one
    //if you make more class variables your graph class will receive a 0,
    //no matter how well it works
    /**
     * Variable which sets the max number of nodes to 200.
     */
    private static final int MAX_NUMBER_OF_NODES = 200;

    //you may not have any other instance variables, only this one
    //if you make more instance variables your graph class will receive a 0,
    //no matter how well it works
    /**
     * Variable which sets the adjlist to null.
     */
    private LinkedList<Connection>[] adjList = null;


    //a (destination,edge) to store in the adjacency list
    //note: source is indicated by the first node of each list
    //you may not edit this inner private class

    /**
     * An inner class which creates the connection between node and edge.
     */
    private class Connection {
        /**
         * Node of the graph variable.
         */
        GraphNode node;
        /**
         * Edge of the graph variable.
         */
        GraphEdge edge;
        Connection(GraphNode n, GraphEdge e) { this.node = n; this.edge = e; }

    }

    //this is the only allowed constructor

    /**
     * Class constructor which sets the adjlist.
     */
    @SuppressWarnings("unchecked")
    public FlexGraph() {
        //reminder: you can NOT do this: ClassWithGeneric<T>[] items = (ClassWithGeneric<T>[]) new Object[10];
        //you must use this format instead: ClassWithGeneric<T>[] items = (ClassWithGeneric<T>[]) new ClassWithGeneric[10];
        adjList = (LinkedList<Connection>[]) new LinkedList[MAX_NUMBER_OF_NODES];
        for(int i = 0; i < MAX_NUMBER_OF_NODES; i++){
            adjList[i] = new LinkedList<>();
        }


    }


    /**
     * Returns a view of all edges in this graph. In general, this
     * obeys the Collection contract, and therefore makes no guarantees
     * about the ordering of the edges within the set.
     * @return a Collection view of all edges in this graph
     */
    public Collection<GraphEdge> getEdges() {
        //Hint: this method returns a Collection, look at the imports for
        //what collections you could return here.

        //O(n+e) where e is the number of edges in the graph and
        //n is the max number of nodes in the graph

        LinkedList<GraphEdge> edge = new LinkedList<>();
        for(LinkedList<Connection> connections : adjList) {
            if (connections != null) {
                for (Connection connection : connections) {
                    if (connection.edge != null) {
                        edge.add(connection.edge);
                    }
                }
            }
        }

        return edge; //default return, remove or change as needed

    }

    /**
     * Returns a view of all vertices in this graph. In general, this
     * obeys the Collection contract, and therefore makes no guarantees
     * about the ordering of the vertices within the set.
     * @return a Collection view of all vertices in this graph
     */
    public Collection<GraphNode> getVertices() {
        //Hint: this method returns a Collection, look at the imports for
        //what collections you could return here.

        //O(n) where n is the max number of nodes.

        LinkedList<GraphNode> vertices = new LinkedList<>();

        for (int i = 0; i < adjList.length; i++){
            if(adjList[i] != null && !adjList[i].isEmpty()){
                vertices.add(adjList[i].getFirst().node);
            }
        }

        return vertices; //default return, remove or change as needed


    }

    /**
     * Returns the number of edges in this graph.
     * @return the number of edges in this graph
     */
    public int getEdgeCount() {
        //O(n) where n is the max number of nodes in the graph
        //note: this is NOT O(n+e), just O(n)

        //Note: this runtime is not a mistake, think about how
        //you could find out the number of edges *without*
        //looking at each one
        int total = 0;

        for(LinkedList<Connection> connections : adjList) {
            if (connections != null) {
                for (Connection connection : connections) {
                    if (connection.edge != null) {
                        total++;
                    }
                }

            }
        }

        return total; //default return, remove or change as needed
    }

    /**
     * Returns the number of vertices in this graph.
     * @return the number of vertices in this graph
     */
    public int getVertexCount() {
        //O(n) where n is the max number of nodes in the graph
        int total = 0;
        for (int i = 0; i < getVertices().size(); i++){
            if (adjList[i] != null) {
                total++;
            }
        }

        return total; //default return, remove or change as needed
    }


    /**
     * Returns true if this graph's vertex collection contains vertex.
     * Equivalent to getVertices().contains(vertex).
     * @param vertex the vertex whose presence is being queried
     * @return true iff this graph contains a vertex vertex
     */
    public boolean containsVertex(GraphNode vertex) {
        //O(1) -- NOT O(n)!

        //Note: this runtime is not a mistake, look at
        //the storage overview in the project description for ideas
        if(adjList[vertex.getId()] != null){
            return true;
        }

        return false; //default return, remove or change as needed

    }


    /**
     * Returns a Collection view of the incoming edges incident to vertex
     * in this graph.
     * @param vertex	the vertex whose incoming edges are to be returned
     * @return  a Collection view of the incoming edges incident
    to vertex in this graph
     */
    public Collection<GraphEdge> getInEdges(GraphNode vertex) {
        //if vertex not present in graph, return null

        //O(n+e) where e is the number of edges in the graph and
        //n is the max number of nodes in the graph
        if (containsVertex(vertex)) {

            LinkedList<GraphEdge> edges = new LinkedList<>();

            for (GraphNode verticies : getVertices()) {
                for (Connection connection : adjList[verticies.getId()]) {
                    if (connection.node.equals(vertex)) {
                        if(connection.edge != null) {
                            edges.add(connection.edge);
                        }
                    }
                }
            }
            return edges; //default return, remove or change as needed
        }

        return null;

    }

    /**
     * Returns a Collection view of the outgoing edges incident to vertex
     * in this graph.
     * @param vertex	the vertex whose outgoing edges are to be returned
     * @return  a Collection view of the outgoing edges incident to vertex in this graph
     */
    public Collection<GraphEdge> getOutEdges(GraphNode vertex) {
        //if vertex not present in graph, return null

        //O(e) where e is the number of edges in the graph

        if(containsVertex(vertex)){
            LinkedList<GraphEdge> edges = new LinkedList<>();
            int index = vertex.getId();
            LinkedList<Connection> connections = adjList[index];

            if(connections != null){
                for(Connection connection : connections){
                    if(connection.edge != null) {
                        edges.add(connection.edge);
                    }
                }
            }
            return edges;
        }

        return null; //default return, remove or change as needed

    }

    /**
     * Returns the number of incoming edges incident to vertex.
     * Equivalent to getInEdges(vertex).size().
     * @param vertex	the vertex whose indegree is to be calculated
     * @return  the number of incoming edges incident to vertex
     */
    public int inDegree(GraphNode vertex) {
        //if vertex not present in graph, return -1

        //O(n+e) where e is the number of edges in the graph and
        //n is the max number of nodes in the graph
        if(vertex == null || !containsVertex(vertex)){
            return -1;
        }

        int total = 0;
        for(GraphNode verticies : getVertices()){
            if(verticies.equals(vertex)){
                continue;
            }
            if(isPredecessor(verticies, vertex)){
                total++;
            }
        }

        return total ; //default return, remove or change as needed
    }

    /**
     * Returns the number of outgoing edges incident to vertex.
     * Equivalent to getOutEdges(vertex).size().
     * @param vertex	the vertex whose outdegree is to be calculated
     * @return  the number of outgoing edges incident to vertex
     */
    public int outDegree(GraphNode vertex) {
        //if vertex not present in graph, return -1
        //O(1)


        if(vertex == null || !containsVertex(vertex)){
            return -1;
        }

        int total = 0;
        for(GraphNode verticies : getVertices()){
            if(verticies.equals(vertex)){
                continue;
            }
            if(isSuccessor(verticies, vertex)){
                total++;
            }
        }



        return total; //default return, remove or change as needed
    }


    /**
     * Returns a Collection view of the predecessors of vertex
     * in this graph.  A predecessor of vertex is defined as a vertex v
     * which is connected to
     * vertex by an edge e, where e is an outgoing edge of
     * v and an incoming edge of vertex.
     * @param vertex	the vertex whose predecessors are to be returned
     * @return  a Collection view of the predecessors of vertex in this graph
     */
    public Collection<GraphNode> getPredecessors(GraphNode vertex) {
        //if vertex not present in graph, return null

        //O(n+e) where e is the number of edges in the graph and
        //n is the max number of nodes in the graph
        if(containsVertex(vertex)){
            Collection<GraphNode> predecessors = new LinkedList<>();

            for(GraphNode vertex1: getVertices()){
                if(isPredecessor(vertex1, vertex)){
                    predecessors.add(vertex1);
                }
            }

            return predecessors;

        }

        return null; //default return, remove or change as needed


    }

    /**
     * Returns a Collection view of the successors of vertex
     * in this graph.  A successor of vertex is defined as a vertex v
     * which is connected to
     * vertex by an edge e, where e is an incoming edge of
     * v and an outgoing edge of vertex.
     * @param vertex	the vertex whose predecessors are to be returned
     * @return  a Collection view of the successors of vertex in this graph
     */
    public Collection<GraphNode> getSuccessors(GraphNode vertex) {
        //if vertex not present in graph, return null
        //O(e) where e is the number of edges in the graph

        if(containsVertex(vertex)){
            Collection<GraphNode> predecessors = new LinkedList<>();

            for(GraphNode vertex1: getVertices()){
                if(isSuccessor(vertex1, vertex)){
                    predecessors.add(vertex1);
                }
            }
            return predecessors;

        }

        return null; //default return, remove or change as needed
    }

    /**
     * Returns true if v1 is a predecessor of v2 in this graph.
     * @param v1 the first vertex to be queried
     * @param v2 the second vertex to be queried
     * @return true if v1 is a predecessor of v2, and false otherwise.
     */
    public boolean isPredecessor(GraphNode v1, GraphNode v2) {
        //O(e) where e is the number of edges in the graph
        for(GraphEdge edge : getOutEdges(v1)){
            if(getDest(edge).equals(v2) && getSource(edge).equals(v1)){
                return true;
            }
        }

        return false; //default return, remove or change as needed

    }

    /**
     * Returns true if v1 is a successor of v2 in this graph.
     * @param v1 the first vertex to be queried
     * @param v2 the second vertex to be queried
     * @return true if v1 is a successor of v2, and false otherwise.
     */
    public boolean isSuccessor(GraphNode v1, GraphNode v2) {
        //O(e) where e is the number of edges in the graph

        for (GraphEdge edge : getOutEdges(v2)) {
            if (getSource(edge).equals(v2) && getDest(edge).equals(v1)) {
                return true;
            }
        }
        return false; //default return, remove or change as needed

    }

    /**
     * Returns the collection of vertices which are connected to vertex
     * via any edges in this graph.
     * If vertex is connected to itself with a self-loop, then
     * it will be included in the collection returned.
     *
     * @param vertex the vertex whose neighbors are to be returned
     * @return  the collection of vertices which are connected to vertex, or null if vertex is not present
     */
    public Collection<GraphNode> getNeighbors(GraphNode vertex) {
        //O(n^2) where n is the max number of vertices in the graph

        //NOTE: there should be no duplicates in the neighbor list.
        if(containsVertex(vertex)){

            Collection<GraphNode> neighbors = new LinkedList<>();

            for(GraphNode s : getSuccessors(vertex)){
                if(!neighbors.contains(s)){
                    neighbors.add(s);
                }
            }
            return neighbors;

        }

        return null; //default return, remove or change as needed

    }


    /**
     * If directed_edge is a directed edge in this graph, returns the source;
     * otherwise returns null.
     * The source of a directed edge d is defined to be the vertex for which
     * d is an outgoing edge.
     * directed_edge is guaranteed to be a directed edge if
     * its EdgeType is DIRECTED.
     * @param directedEdge Edge which is directed to the node.
     * @return  the source of directed_edge if it is a directed edge in this graph, or null otherwise
     */
    public GraphNode getSource(GraphEdge directedEdge) {
        //O(n+e) where n is the max number of vertices in the graph
        // and e is the number of edges in the graph
        if(getEdgeType(directedEdge) == EdgeType.DIRECTED || directedEdge != null){
            for (GraphNode vertex : getVertices()){
                if(getOutEdges(vertex).contains(directedEdge)){
                    return vertex;
                }
            }
        }
        return null; //default return, remove or change as needed
    }

    /**
     * If directed_edge is a directed edge in this graph, returns the destination;
     * otherwise returns null.
     * The destination of a directed edge d is defined to be the vertex
     * incident to d for which
     * d is an incoming edge.
     * directed_edge is guaranteed to be a directed edge if
     * its EdgeType is DIRECTED.
     * @param directedEdge Edge which is directed to the node.
     * @return  the destination of directed_edge if it is a directed edge in this graph, or null otherwise
     */
    public GraphNode getDest(GraphEdge directedEdge) {
        //O(n+e) where n is the max number of vertices in the graph
        // and e is the number of edges in the graph


        if(getEdgeType(directedEdge) == EdgeType.DIRECTED || directedEdge != null){
            for (GraphNode vertex : getVertices()){
                if(getInEdges(vertex).contains(directedEdge)){
                    return vertex;
                }
            }
        }
        return null; //default return, remove or change as needed

    }


    /**
     * Returns an edge that connects v1 to v2.
     * If this edge is not uniquely
     * defined (that is, if the graph contains more than one edge connecting
     * v1 to v2), any of these edges
     * may be returned.  findEdgeSet(v1, v2) may be
     * used to return all such edges.
     * Returns null if either of the following is true:
     * <ul>
     * <li/>v1 is not connected to v2
     * <li/>either v1 or v2 are not present in this graph
     * </ul>
     * <b>Note</b>: for purposes of this method, v1 is only considered to be connected to
     * v2 via a given <i>directed</i> edge e if
     * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
     * (v1 and v2 are connected by an undirected edge u if
     * u is incident to both v1 and v2.)
     * @param v1 Fist node used to find the edge.
     * @param v2 Second node used to find the edge.
     * @return  an edge that connects v1 to v2, or null if no such edge exists (or either vertex is not present)
     * @see Hypergraph#findEdgeSet(Object, Object)
     */
    public GraphEdge findEdge(GraphNode v1, GraphNode v2) {
        //O(e) where e is the number of edges in the graph
        if(v1 != null || v2 != null){
            for(GraphEdge edge : getEdges()){
                if (getSource(edge).equals(v1) && getDest(edge).equals(v2)){
                    return edge;
                }
            }
            return null;
        }

        return null; //default return, remove or change as needed

    }

    /**
     * Returns true if vertex and edge
     * are incident to each other.
     * Equivalent to getIncidentEdges(vertex).contains(edge) and to
     * getIncidentVertices(edge).contains(vertex).
     * @param vertex The vertex used to determine if it's an incident of the edge.
     * @param edge The edge used to determine if it's an incident of the vertex.
     * @return true if vertex and edge are incident to each other.
     */
    public boolean isIncident(GraphNode vertex, GraphEdge edge) {
        //O(n+e) where n is the max number of vertices in the graph
        // and e is the number of edges in the graph
        if(vertex != null || edge != null){
            return getIncidentEdges(vertex).contains(edge) && getIncidentVertices(edge).contains(vertex);
        }

        return false; //default return, remove or change as needed


    }



    /**
     * Adds edge e to this graph such that it connects
     * vertex v1 to v2.
     * If this graph does not contain v1, v2,
     * or both, implementations may choose to either silently add
     * the vertices to the graph or throw an IllegalArgumentException.
     * If this graph assigns edge types to its edges, the edge type of
     * e will be the default for this graph.
     * See Hypergraph.addEdge() for a listing of possible reasons
     * for failure.
     * @param e the edge to be added
     * @param v1 the first vertex to be connected
     * @param v2 the second vertex to be connected
     * @return true if the add is successful, false otherwise
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(Object, Object, Object, EdgeType)
     */
    public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2) {
        //remember we do not allow self-loops nor parallel edges

        //O(n+e) where n is the max number of vertices in the graph
        // and e is the number of edges in the graph

        if(e != null && v1 != null && v2 != null ){
            if(!containsVertex(v1)){
                addVertex(v1);
            }
            if(!containsVertex(v2)){
                addVertex(v2);
            }

            if(findEdge(v1, v2) != null){
                return false;
            }
            Connection connection = new Connection(v2, e);
            adjList[v1.getId()].add(connection);
            return true;
        }



        return false; //default return, remove or change as needed

    }

    /**
     * Adds vertex to this graph.
     * Fails if vertex is null or already in the graph.
     *
     * @param vertex	the vertex to add
     * @return true if the add is successful, and false otherwise
     * @throws IllegalArgumentException if vertex is null
     */
    public boolean addVertex(GraphNode vertex) {

        //O(1)
        if(vertex != null){

            if(vertex.getId() < 0 || vertex.getId() >= MAX_NUMBER_OF_NODES){
                return false;
            }

            if(adjList[vertex.getId()] == null){
                adjList[vertex.getId()] = new LinkedList<>();
            }

            if(adjList[vertex.getId()].isEmpty()) {
                adjList[vertex.getId()].add(new Connection(vertex, null));
            }

            return true;
        }

        return false; //default return, remove or change as needed
    }

    /**
     * Removes edge from this graph.
     * Fails if edge is null, or is otherwise not an element of this graph.
     *
     * @param edge the edge to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeEdge(GraphEdge edge) {
        //O(n+e) where n is the max number of vertices in the graph
        // and e is the number of edges in the graph
        if(edge == null || !containsEdge(edge)){
            return false;
        }

        for(LinkedList<Connection> connections : adjList) {
            if (connections != null) {
                ListIterator<Connection> iterator = connections.listIterator();
                while (iterator.hasNext()) {
                    Connection connection = iterator.next();
                    if (connection.edge != null && connection.edge.equals(edge)) {
                        iterator.remove();
                        return true;
                    }
                }
            }
        }

        return false; //default return, remove or change as needed

    }

    /**
     * Removes vertex from this graph.
     * As a side effect, removes any edges e incident to vertex if the
     * removal of vertex would cause e to be incident to an illegal
     * number of vertices.  (Thus, for example, incident hyperedges are not removed, but
     * incident edges--which must be connected to a vertex at both endpoints--are removed.)
     *
     * <p>Fails under the following circumstances:
     * <ul>
     * <li/>vertex is not an element of this graph
     * <li/>vertex is null
     * </ul>
     *
     * @param vertex the vertex to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeVertex(GraphNode vertex) {
        //O(n+e) where n is the max number of vertices in the graph
        // and e is the number of edges in the graph
        if(vertex != null){
            if(vertex.getId() < 0 || vertex.getId() >= MAX_NUMBER_OF_NODES){
                return false;
            }

            LinkedList<Connection> connections = adjList[vertex.getId()];
            if(connections != null){
                for(Connection connection : new LinkedList<>(connections)){
                    removeEdge(connection.edge);
                }

                for(int i = 0; i < adjList.length; i++){
                    LinkedList<Connection> list = adjList[i];
                    if(list != null){

                        ListIterator<Connection> iterator = list.listIterator();
                        while(iterator.hasNext()) {
                            if (vertex != null) {
                                Connection connection = iterator.next();
                                if (connection.node.equals(vertex)) {
                                    removeEdge(connection.edge);
                                }
                            }
                        }
                    }
                }
                adjList[vertex.getId()] = null;
                return true;
            }
        }
        return false; //default return, remove or change as needed

    }




    /**
     *  Returns a string of the depth first traversal of the graph from the given vertex.
     *  If vertex is null or not present in graph, return an empty string.
     *  Otherwise, return the string with node IDs in the depth first traversal order,
     *  separated by a single space.
     *  When there are multiple outgoing paths to continue for a node, follow the storage
     *  order to decide which one to pick, i.e. you should process the connections in the
     *  adjacency list from start to end.
     *  Check the example in main() below.
     *  @param vertex the starting vertex of the depth first traversal, may be null
     *  @return a string representation of the depth first traversal, or an empty string if vertex is null or not present
     */

    public String depthFirstTraversal(GraphNode vertex){

        //Hint: feel free to define private helper method
        //Use StringBuilder!

        //O(n+e) where n is the max number of vertices in the graph
        // and e is the number of edges in the graph

        if(vertex != null || containsVertex(vertex)) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean[] done = new boolean[MAX_NUMBER_OF_NODES];
            depthFirstTraversal(vertex, done, stringBuilder);
            return stringBuilder.toString().trim();
        }

        return "";
    }

    /**
     * Helper method which traverses through the graph.
     * @param vertex The vertex to be added to the string.
     * @param done Boolean array which keeps track of which vertices have already been to.
     * @param traversal The string to hold the vertices.
     */
    private void depthFirstTraversal(GraphNode vertex, boolean[] done, StringBuilder traversal){
        done[vertex.getId()] = true;
        traversal.append(vertex.getId()).append(" ");
        for (GraphNode neighbor : getNeighbors(vertex)) {
            if (!done[neighbor.getId()]) {
                depthFirstTraversal(neighbor, done, traversal);
            }
        }
    }


    /**
     *  Counts the number of nodes that are reachable from the given vertex,
     *  and the number of nodes that can reach the given vertex in graph.
     *  Returns a pair of the two integer counters as the answer.
     *  If vertex is null or not present, return null.
     *  Check the example in main() below.
     *  @param vertex the node we check for reachable feature
     *  @return a pair of integer counters
     */

    public IntPair countReachable(GraphNode vertex){
        //O(n+e) where n is the max number of vertices in the graph
        // and e is the number of edges in the graph

        //Note: big-O is not O(n(n+e))
        //Re-check zyBookCh14 for graph traversals if you need more hints ...

        if(vertex != null && containsVertex(vertex)){
            boolean[] done = new boolean[MAX_NUMBER_OF_NODES];
            boolean[] done2 = new boolean[MAX_NUMBER_OF_NODES];
            return new IntPair(countReachable(vertex, done),canReach(vertex, done2) );
            //return new IntPair(getInEdges(vertex).size(), getOutEdges(vertex).size());

        }

        return null;
    }

    /**
     * Count reachable helper which count the numbers of successors.
     * @param vertex Vertex which successors will be counted for.
     * @param done Boolean array which keeps track of the vertices that are done.
     * @return The total amount of successors.
     */
    private int countReachable(GraphNode vertex, boolean[] done){
        done[vertex.getId()] = true;

        int total = 0;

        for(GraphNode successor : getSuccessors(vertex)){
            if(!done[successor.getId()] && !successor.equals(vertex)){
                total += 1 + countReachable(successor, done);
            }
        }
        return total;
    }

    /**
     * Count reachable helper which count the numbers of predecessors.
     * @param vertex Vertex which predecessors will be counted for.
     * @param done Boolean array which keeps track of the vertices that are done.
     * @return The total amount of predecessors.
     */
    private int canReach(GraphNode vertex, boolean[] done){
        done[vertex.getId()] = true;

        int total = 0;

        for(GraphNode predecessor : getPredecessors(vertex)){
            if(!done[predecessor.getId()] && !predecessor.equals(vertex)){
                total += 1 + canReach(predecessor, done);
            }
        }
        return total;
    }




    //********************************************************************************
    //   testing code goes here... edit this as much as you want!
    //********************************************************************************
    /**
     * Main method to test the methods.
     * @param args command line args
     */
    public static void main(String[] args) {
        //create a set of nodes and edges to test with
        GraphNode[] nodes = {new GraphNode(0), new GraphNode(1), new GraphNode(2), new GraphNode(3), new GraphNode(4), new GraphNode(5), new GraphNode(6), new GraphNode(7), new GraphNode(8), new GraphNode(9)};

        GraphEdge[] edges = {new GraphEdge(0), new GraphEdge(1), new GraphEdge(2), new GraphEdge(3), new GraphEdge(4), new GraphEdge(5), new GraphEdge(6), new GraphEdge(7)};

        //constructs a graph
        FlexGraph graph = new FlexGraph();
        for(GraphNode n : nodes) {
            graph.addVertex(n);
        }

        graph.addEdge(edges[0],nodes[0],nodes[1]);
        graph.addEdge(edges[1],nodes[1],nodes[2]); //1-->2
        graph.addEdge(edges[2],nodes[3],nodes[6]);
        graph.addEdge(edges[3],nodes[6],nodes[7]);
        graph.addEdge(edges[4],nodes[8],nodes[9]);
        graph.addEdge(edges[5],nodes[9],nodes[0]);
        graph.addEdge(edges[6],nodes[2],nodes[7]);
        graph.addEdge(edges[7],nodes[1],nodes[8]); //1-->8

        //System.out.println(graph.removeVertex(nodes[4]));

        //System.out.println(graph.getVertices());
        //System.out.println(graph.getVertexCount());
        //System.out.println(graph.inDegree(nodes[0]));
        //System.out.println(graph.outDegree(nodes[1]));
        //System.out.println(graph.depthFirstTraversal(nodes[1]).trim());



        if(graph.getVertexCount() == 10 && graph.getEdgeCount() == 8) {
            System.out.println("Yay 1");
        }

        if(graph.inDegree(nodes[0]) == 1 && graph.outDegree(nodes[1]) == 2) {
            System.out.println("Yay 2");
        }


        //lots more testing here...
        //If your graph "looks funny" you probably want to check:
        //getVertexCount(), getVertices(), getInEdges(vertex),
        //and getIncidentVertices(incomingEdge) first. These are
        //used by the layout class.


        //some testing for the advanced graph operations:

        if(graph.depthFirstTraversal(nodes[9]).trim().equals("9 0 1 2 7 8")) {
            System.out.println("Yay 3");
        }
        //NOTE: in traversal, after node 1, we visited node 2 before node 8
        //	  since edge 1-->2 was added into graph before edge 1-->8

        //System.out.println(graph.depthFirstTraversal(nodes[9]));

        IntPair counts = graph.countReachable(nodes[1]);
        if (counts.getFirst() == 5 && counts.getSecond() == 3){
            System.out.println("Yay 4");
        }

        //System.out.println(graph.countReachable(nodes[1]));


        //System.out.println(graph.getSuccessors(nodes[1]));
        //System.out.println(graph.getSuccessors(nodes[2]));
        //System.out.println(graph.getSuccessors(nodes[8]));
        //System.out.println(graph.getSuccessors(nodes[7]));
        //System.out.println(graph.getSuccessors(nodes[9]));
        //System.out.println(graph.getSuccessors(nodes[0]));
        //System.out.println(graph.countReachable(nodes[1]));

        //again, many more testing by yourself...

    }


    //********************************************************************************
    //   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
    //   NOTE: you do need to fix JavaDoc issues if there is any in this section.
    //********************************************************************************



    /**
     * Returns the number of edges incident to vertex.
     * Special cases of interest:
     * <li/> Incident self-loops are counted once.
     * <li> If there is only one edge that connects this vertex to
     * each of its neighbors (and vice versa), then the value returned
     * will also be equal to the number of neighbors that this vertex has
     * (that is, the output of getNeighborCount).
     * <li> If the graph is directed, then the value returned will be
     * the sum of this vertex's indegree (the number of edges whose
     * destination is this vertex) and its outdegree (the number
     * of edges whose source is this vertex), minus the number of
     * incident self-loops (to avoid double-counting).
     *
     * <p>Equivalent to getIncidentEdges(vertex).size().
     * @param vertex the vertex whose degree is to be returned
     * @return the degree of this node
     * @see Hypergraph#getNeighborCount(Object)
     */
    public int degree(GraphNode vertex) {
        return inDegree(vertex) + outDegree(vertex);
    }

    /**
     * Returns true if v1 and v2 share an incident edge.
     * Equivalent to getNeighbors(v1).contains(v2).
     *
     * @param v1 the first vertex to test
     * @param v2 the second vertex to test
     * @return true if v1 and v2 share an incident edge
     */
    public boolean isNeighbor(GraphNode v1, GraphNode v2) {
        return (findEdge(v1, v2) != null || findEdge(v2, v1)!=null);
    }

    /**
     * Returns the endpoints of edge as a pair.
     * @param edge the edge whose endpoints are to be returned
     * @return the endpoints (incident vertices) of edge
     */
    public Pair<GraphNode> getEndpoints(GraphEdge edge) {
        if (edge==null) return null;

        GraphNode v1 = getSource(edge);
        if (v1==null)
            return null;

        GraphNode v2 = getDest(edge);
        if (v2==null)
            return null;

        return new Pair<GraphNode>(v1, v2);
    }


    /**
     * Returns the collection of edges in this graph which are connected to vertex.
     *
     * @param vertex the vertex whose incident edges are to be returned.
     * @return the collection of edges which are connected to vertex, or null if vertex is not present.
     */
    public Collection<GraphEdge> getIncidentEdges(GraphNode vertex) {
        LinkedList<GraphEdge> ret = new LinkedList<>();
        ret.addAll(getInEdges(vertex));
        ret.addAll(getOutEdges(vertex));
        return ret;
    }

    /**
     * Returns the collection of vertices in this graph which are connected to edge.
     * Note that for some graph types there are guarantees about the size of this collection
     * (i.e., some graphs contain edges that have exactly two endpoints, which may or may
     * not be distinct).  Implementations for those graph types may provide alternate methods
     * that provide more convenient access to the vertices.
     *
     * @param edge the edge whose incident vertices are to be returned
     * @return  the collection of vertices which are connected to edge, or null if edge is not present.
     */
    public Collection<GraphNode> getIncidentVertices(GraphEdge edge) {
        Pair<GraphNode> p = getEndpoints(edge);
        LinkedList<GraphNode> ret = new LinkedList<>();
        ret.add(p.getFirst());
        ret.add(p.getSecond());
        return ret;
    }


    /**
     * Returns true if this graph's edge collection contains edge.
     * Equivalent to getEdges().contains(edge).
     * @param edge the edge whose presence is being queried
     * @return true iff this graph contains an edge edge
     */
    public boolean containsEdge(GraphEdge edge) {
        return (getEndpoints(edge) != null);
    }

    /**
     * Returns the collection of edges in this graph which are of type edge_type.
     * @param edgeType the type of edges to be returned
     * @return the collection of edges which are of type edge_type, or null if the graph does not accept edges of this type.
     * @see EdgeType
     */
    public Collection<GraphEdge> getEdges(EdgeType edgeType) {
        if(edgeType == EdgeType.DIRECTED) {
            return getEdges();
        }
        return null;
    }

    /**
     * Returns the number of edges of type edge_type in this graph.
     * @param edgeType the type of edge for which the count is to be returned
     * @return the number of edges of type edge_type in this graph
     */
    public int getEdgeCount(EdgeType edgeType) {
        if(edgeType == EdgeType.DIRECTED) {
            return getEdgeCount();
        }
        return 0;
    }

    /**
     * Returns the number of predecessors that vertex has in this graph.
     * Equivalent to vertex.getPredecessors().size().
     * @param vertex the vertex whose predecessor count is to be returned
     * @return  the number of predecessors that vertex has in this graph
     */
    public int getPredecessorCount(GraphNode vertex) {
        return inDegree(vertex);
    }

    /**
     * Returns the number of successors that vertex has in this graph.
     * Equivalent to vertex.getSuccessors().size().
     * @param vertex the vertex whose successor count is to be returned
     * @return  the number of successors that vertex has in this graph
     */
    public int getSuccessorCount(GraphNode vertex) {
        return outDegree(vertex);
    }

    /**
     * Returns the number of vertices that are adjacent to vertex
     * (that is, the number of vertices that are incident to edges in vertex's
     * incident edge set).
     *
     * <p>Equivalent to getNeighbors(vertex).size().
     * @param vertex the vertex whose neighbor count is to be returned
     * @return the number of neighboring vertices
     */
    public int getNeighborCount(GraphNode vertex) {
        if (!containsVertex(vertex))
            return -1;

        return getNeighbors(vertex).size();
    }

    /**
     * Returns the vertex at the other end of edge from vertex.
     * (That is, returns the vertex incident to edge which is not vertex.)
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return the vertex at the other end of edge from vertex
     */
    public GraphNode getOpposite(GraphNode vertex, GraphEdge edge) {
        if (getSource(edge).equals(vertex)){
            return getDest(edge);
        }
        else if (getDest(edge).equals(vertex)){
            return getSource(edge);
        }
        else
            return null;
    }

    /**
     * Returns all edges that connects v1 to v2.
     * If this edge is not uniquely
     * defined (that is, if the graph contains more than one edge connecting
     * v1 to v2), any of these edges
     * may be returned.  findEdgeSet(v1, v2) may be
     * used to return all such edges.
     * Returns null if v1 is not connected to v2.
     * <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
     *
     * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
     * v2 via a given <i>directed</i> edge d if
     * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
     * (v1 and v2 are connected by an undirected edge u if
     * u is incident to both v1 and v2.)
     * @param v1 First vertex used to determine the edges.
     * @param v2 Second vertex used to determine the edges.
     * @return a collection containing all edges that connect v1 to v2, or null if either vertex is not present.
     * @see Hypergraph#findEdge(Object, Object)
     */
    public Collection<GraphEdge> findEdgeSet(GraphNode v1, GraphNode v2) {
        GraphEdge edge = findEdge(v1, v2);
        if(edge == null) {
            return null;
        }

        LinkedList<GraphEdge> ret = new LinkedList<>();
        ret.add(edge);
        return ret;

    }

    /**
     * Returns true if vertex is the source of edge.
     * Equivalent to getSource(edge).equals(vertex).
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return true iff vertex is the source of edge
     */
    public boolean isSource(GraphNode vertex, GraphEdge edge) {
        return getSource(edge).equals(vertex);
    }

    /**
     * Returns true if vertex is the destination of edge.
     * Equivalent to getDest(edge).equals(vertex).
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return true iff vertex is the destination of edge
     */
    public boolean isDest(GraphNode vertex, GraphEdge edge) {
        return getDest(edge).equals(vertex);
    }

    /**
     * Adds edge e to this graph such that it connects
     * vertex v1 to v2.
     * If this graph does not contain v1, v2,
     * or both, implementations may choose to either silently add
     * the vertices to the graph or throw an IllegalArgumentException.
     * If edgeType is not legal for this graph, this method will
     * throw IllegalArgumentException.
     * See Hypergraph.addEdge() for a listing of possible reasons
     * for failure.
     * @param e the edge to be added
     * @param v1 the first vertex to be connected
     * @param v2 the second vertex to be connected
     * @param edgeType the type to be assigned to the edge
     * @return true if the add is successful, false otherwise
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(Object, Object, Object)
     */
    public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2, EdgeType edgeType) {
        //NOTE: Only directed edges allowed

        if(edgeType == EdgeType.UNDIRECTED) {
            throw new IllegalArgumentException();
        }

        return addEdge(e, v1, v2);
    }

    /**
     * Adds edge to this graph.
     * Fails under the following circumstances:
     * <ul>
     * <li/>edge is already an element of the graph
     * <li/>either edge or vertices is null
     * <li/>vertices has the wrong number of vertices for the graph type
     * <li/>vertices are already connected by another edge in this graph,
     * and this graph does not accept parallel edges
     * </ul>
     *
     * @param edge Edge to be added to the graph.
     * @param vertices Vertices used to determine if the edge can be added.
     * @return true if the add is successful, and false otherwise
     * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge.
     */
    @SuppressWarnings("unchecked")
    public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices) {
        if(edge == null || vertices == null || vertices.size() != 2) {
            return false;
        }

        GraphNode[] vs = (GraphNode[])vertices.toArray();
        return addEdge(edge, vs[0], vs[1]);
    }

    /**
     * Adds edge to this graph with type edge_type.
     * Fails under the following circumstances:
     * <ul>
     * <li/>edge is already an element of the graph
     * <li/>either edge or vertices is null
     * <li/>vertices has the wrong number of vertices for the graph type
     * <li/>vertices are already connected by another edge in this graph,
     * and this graph does not accept parallel edges
     * <li/>edge_type is not legal for this graph
     * </ul>
     *
     * @param edge Edge to be added to the graph.
     * @param vertices Vertices used to determine if the edge can be added.
     * @param edgeType The type of edge for the graph.
     * @return true if the add is successful, and false otherwise
     * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge.
     */
    @SuppressWarnings("unchecked")
    public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices, EdgeType edgeType) {
        if(edge == null || vertices == null || vertices.size() != 2) {
            return false;
        }

        GraphNode[] vs = (GraphNode[])vertices.toArray();
        return addEdge(edge, vs[0], vs[1], edgeType);
    }

    //********************************************************************************
    //   DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT FOR FIXING JAVADOC
    //********************************************************************************

    /**
     * Returns a {@code Factory} that creates an instance of this graph type.
     * @param <E> Edge in the graph.
     * @param <V> Vertex in the graph.
     * @return Return the flex graph.
     */

    public static <V,E> Factory<Graph<GraphNode,GraphEdge>> getFactory() {
        return new Factory<Graph<GraphNode,GraphEdge>> () {
            @SuppressWarnings("unchecked")
            public Graph<GraphNode,GraphEdge> create() {
                return (Graph<GraphNode,GraphEdge>) new FlexGraph();
            }
        };
    }



    /**
     * Returns the edge type of edge in this graph.
     * @param edge The edge in the graph.
     * @return the EdgeType of edge, or null if edge has no defined type
     */
    public EdgeType getEdgeType(GraphEdge edge) {
        return EdgeType.DIRECTED;
    }

    /**
     * Returns the default edge type for this graph.
     *
     * @return the default edge type for this graph
     */
    public EdgeType getDefaultEdgeType() {
        return EdgeType.DIRECTED;
    }

    /**
     * Returns the number of vertices that are incident to edge.
     * For hyperedges, this can be any nonnegative integer; for edges this
     * must be 2 (or 1 if self-loops are permitted).
     *
     * <p>Equivalent to getIncidentVertices(edge).size().
     * @param edge the edge whose incident vertex count is to be returned
     * @return the number of vertices that are incident to edge.
     */
    public int getIncidentCount(GraphEdge edge) {
        return 2;
    }



}

