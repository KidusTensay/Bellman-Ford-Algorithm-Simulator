//TODO: Implement the required methods and add JavaDoc for them.
//Remember: Do NOT add any additional instance or class variables (local variables are ok)
//and do NOT alter any provided methods or change any method signatures!

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;


/**
 *  Simulation of Bellman-Ford algorithm.
 *
 */
class BellmanFord implements GraphAlg {
    /**
     *  The graph the algorithm will run on.
     */
    Graph<GraphNode, GraphEdge> graph;

    /**
     *  The list of edges in graph.
     */
    LinkedList<GraphEdge> edgeList;

    /**
     *  The iterator of edges from edgeList.
     */
    private Iterator<GraphEdge> iter;

    /**
     *  The edge checked in current step.
     */
    private GraphEdge currentEdge;

    /**
     *  Whether or not the algorithm has been started.
     */
    private boolean started = false;

    /**
     *  Whether or not the algorithm has completed.
     */
    private boolean completed = false;

    /**
     *  Whether or not the graph has a nagative weighted cycle reachable from source node.
     */
    private boolean hasNegCycle = false;

    /**
     * The round in simulation.
     */
    private int round = 0;

    /**
     *  The source node of the shortest path task.
     */
    private GraphNode sourceNode;

    /**
     *  Whether or not the relaxation has triggered updates of shortest paths.
     */
    private boolean changed = false;

    /**
     *  The color when a node has "no color".
     */
    public static final Color COLOR_NONE_NODE = Color.WHITE;

    /**
     *  The color when an edge has "no color".
     */
    public static final Color COLOR_NONE_EDGE = Color.BLACK;

    /**
     *  The color when a node/edge is highlighted.
     */
    public static final Color COLOR_HIGHLIGHT = new Color(255,204,51);

    /**
     *  The color when an edge is marked as part of the shortest path.
     */
    public static final Color COLOR_ANS = Color.BLUE;

    /**
     *  The color when a node or edge is in warning.
     */
    public static final Color COLOR_WARNING = new Color(255,51,51);

    /**
     *  The infinity sign.
     */
    public static final String INFINITY_SIGN = "\u221e";

    /**
     *  {@inheritDoc}
     */
    public EdgeType graphEdgeType() {
        return EdgeType.DIRECTED;
    }

    /**
     *  {@inheritDoc}
     */
    public void reset(Graph<GraphNode, GraphEdge> graph) {
        this.graph = graph;
        started = false;
        completed = false;
        edgeList = null;
        iter = null;
        round = 0;
        changed = false;
        currentEdge = null;
        hasNegCycle = false;
    }

    /**
     *  {@inheritDoc}
     */
    public boolean isStarted() {
        return started;
    }

    /**
     *  Report whether or not we have identified a negative-weighted cycle reachable from source node.
     *
     *  @return whether or not we have identified a negative-weighted cycle reachable from source node
     */
    public boolean hasNegCycle(){
        return hasNegCycle;
    }

    /**
     *  Report the current round in Bellman-Ford simulation.
     *
     *  @return the current round in Bellman-Ford simulation
     */
    public int getRound(){
        return round;
    }


    /**
     *  Sets the node to display a given cost (or infinity if appropriate).
     *
     *  @param n the node to set
     *  @param cost the cost to set
     */
    private void setNodeText(GraphNode n, int cost) {
        String text = (cost == Integer.MAX_VALUE ? INFINITY_SIGN : ""+cost);
        n.setText(text);
    }

    /**
     *  Sets the node to display a given cost (or infinity if appropriate)
     *  while also displaying the change from a previous value.
     *
     *  @param n the node to set
     *  @param oldCost the previous cost
     *  @param newCost the new cost
     */
    private void setNodeText(GraphNode n, int oldCost, int newCost) {
        String text = (oldCost == Integer.MAX_VALUE ? INFINITY_SIGN : ""+oldCost);
        n.setText("" + text + "->" + newCost);
    }


    /**
     *  {@inheritDoc}
     */
    public void start() {
        started = true;
        completed = false;

        //set up source node to be the vertex with highest ID
        //----------------------
        //YOU IMPLEMENT THIS!
        //----------------------
        sourceNode = getMaximumIdNode();

        if (sourceNode==null){
            completed = true;
            return;
        }
        sourceNode.setColor(COLOR_HIGHLIGHT);

        //only one node or no edges
        if (graph.getEdgeCount()==0|| graph.getVertexCount()==1){
            completed = true;
            return;
        }

        //create an list of edges, sorted by id
        edgeList = new LinkedList<>();
        for (GraphEdge e: graph.getEdges())
            edgeList.add(e);

        //sort edges based on edge ID
        Collections.sort(edgeList);

        //initialize
        iter = edgeList.iterator();
        round = 0;
        changed = false;
        currentEdge = null;

        //initialize the cost (display) for all vertices
        //----------------------
        //YOU IMPLEMENT THIS!
        //----------------------
        initCost();
    }


    /**
     *  {@inheritDoc}
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     *  Cleanup the highlighted edge/node from last step.
     */
    private void cleanUpLastEdge(){
        if (currentEdge==null) return;
        currentEdge.setColor(COLOR_NONE_EDGE);
        GraphNode dest = graph.getDest(currentEdge);
        setNodeText(dest, dest.getCost());

    }

    /**
     *  {@inheritDoc}
     */
    public void doNextStep() {

        //simulation done
        if (completed) return;


        if (!iter.hasNext()){
            //one round completed
            if (changed == false){
                //early termination if no updates
                cleanUpLastEdge();
                completed = true;
                return;
            }
            else{
                //start a new round
                round++;

                if (round == graph.getVertexCount()-1){
                    //already repeated |V|-1 times (starting from round=0)
                    cleanUpLastEdge();

                    //verify negative-weighted cycle
                    //----------------------
                    //YOU IMPLEMENT THIS!
                    //----------------------
                    hasNegCycle = findNegativeCycle();
                    completed = true;
                    return;
                }
                else{
                    //normal new round
                    iter = edgeList.iterator();
                    changed = false;
                }
            }
        }

        //set up for next edge
        if (iter.hasNext()){ //should be true; being extra careful

            GraphEdge nextEdge = iter.next();
            cleanUpLastEdge();

            //reset current edge
            currentEdge = nextEdge;
            currentEdge.setColor(COLOR_HIGHLIGHT);

            //relax and update for currentEdge
            //----------------------
            //YOU IMPLEMENT THIS!
            //----------------------
            relax();

        }


    }



    //----------------------------------------------------
    // TODO: Implement the methods below to complete the algorithm.
    // - DO NOT change the signature of any required public method;
    // - Feel free to define additional method but they must be private.
    // - Add JavaDoc and comments for these methods.
    //
    // YOUR CODE HERE
    //----------------------------------------------------

    /**
     * This method finds the maximum id node from all the vertices.
     * @return the maximum id node from all vertices.
     */
    public GraphNode getMaximumIdNode() {
        // return the maximum id node from all vertices in graph.
        // This is chosen as the starting spot for our algorithm.
        // return null if graph is null or has 0 vertices.

        if(graph != null && graph.getVertexCount() != 0){
            GraphNode max = null;

            for (GraphNode node : graph.getVertices()){
                if(max == null || node.getId() > max.getId()){
                    max = node;
                }
            }
            return max;
        }

        return null;
    }

    /**
     * This method initializes the math length and text for each node.
     */
    public void initCost() {
        //initialize the path length and text (display) for each node
        // - source node: 0
        // - all other nodes: infinity

        sourceNode.setCost(0);
        setNodeText(sourceNode, 0);

        for(GraphNode node : graph.getVertices()){
            if(!node.equals(sourceNode)){
                node.setCost(Integer.MAX_VALUE);
                setNodeText(node, Integer.MAX_VALUE);
            }
        }

    }

    /**
     * This method performs relaxation for currentEdge.
     */
    public void relax() {
        //perform relaxation for currentEdge
        //check spec PDF to see the details (this is Step 2 for one edge)
        //remember to update the display as needed

        //you can assume currentEdge has been set before relax is called and is not null
        GraphNode source = graph.getSource(currentEdge);
        GraphNode destination = graph.getDest(currentEdge);


        int cost = source.getCost() + currentEdge.getWeight();

        if(cost < destination.getCost()){
            destination.setCost(cost);
            destination.setParent(source);
            setNodeText(destination, cost);
            changed = true;
        }



    }

    /**
     * This method performs the final checking of negative weighted cycle.
     * @return true or false based on if the cycle is negative.
     */
    public boolean findNegativeCycle(){
        //perform the final checking of negative weighted cycle
        //check spec PDF to see the details (this is Step 3)

        //you can assume we only call this in the last step of simulation

        //return true if there is at least one negative-weighted cycle reachable from source node
        //return false otherwise

        for(int i = 0; i < graph.getVertexCount() - 1; i++){
            //boolean relaxed = false;
            for(GraphEdge edge : graph.getEdges()){
                int cost = graph.getSource(edge).getCost() + edge.getWeight();
                if(cost < graph.getDest(edge).getCost()){
                    return true;
                }
            }

        }

        return false;
    }

    /**
     *  {@inheritDoc}
     */
    public void finish() {

        started = false; //keep this line!

        //clean up
        // - if no negative-weighted cycles: highlight shortest paths identified for each node,
        //   keep the path length display for each node
        // - otherwise, update the display for each node to be its nodeId

        if(!hasNegCycle){
            for(GraphNode node : graph.getVertices()){
                if(node.equals(sourceNode)){
                    continue;
                }
                GraphNode p = node.getParent();
                while (p != null){
                    GraphEdge edge = graph.findEdge(p, node);
                    if(edge != null){
                        edge.setColor(COLOR_ANS);
                        node.setColor(COLOR_ANS);
                        node = p;
                        p = node.getParent();
                    }else{
                        break;
                    }
                }
            }
        }else{
            for(GraphNode node : graph.getVertices()){
                if(node.getCost() == Integer.MAX_VALUE){
                    node.setText(INFINITY_SIGN);
                }else {
                    node.setText(Integer.toString((node.getId())));
                }
            }
        }


    }


    //********************************************************************************
    //   testing code goes here... edit this as much as you want!
    //********************************************************************************

    /**
     * Main method to test the methods.
     * @param args command line args
     */
    public static void main(String[] args){
        FlexGraph graph = new FlexGraph();
        BellmanFord simulation = new BellmanFord();


        //test 1
        GraphNode[] nodes = { new GraphNode(0), new GraphNode(1)};
        GraphEdge[] edges = {	new GraphEdge(0, -5)	}; //weight -5

        //set up graph with 2 nodes, 1 edge
        graph.addVertex(nodes[0]);
        graph.addVertex(nodes[1]);
        graph.addEdge(edges[0], nodes[1], nodes[0]); //1-->0

        //set up simulation
        simulation.reset(graph);
        while (simulation.step()) {} //execution of all steps

        //check result (shortest paths w/ source node as nodes[1])
        if (nodes[0].getCost() == -5 && nodes[0].getParent().equals(nodes[1])
                && simulation.getMaximumIdNode().equals(nodes[1]) &&
                !simulation.hasNegCycle() && simulation.getRound() == 1)
                System.out.println("pass one-edge graph!");

        //System.out.println(nodes[0].getCost());








        //test 2
        GraphNode[] nodes2 = {new GraphNode(0), new GraphNode(1), new GraphNode(2), new GraphNode(3),};

        GraphEdge[] edges2 = {new GraphEdge(0,-5), new GraphEdge(1,5), new GraphEdge(2,10), new GraphEdge(3,-1)};

        graph = new FlexGraph(); //graph from samplerun.pdf Example 4
        graph.addVertex(nodes2[0]);
        graph.addVertex(nodes2[1]);
        graph.addVertex(nodes2[2]);
        graph.addVertex(nodes2[3]);


        graph.addEdge(edges2[0], nodes2[1], nodes2[0]);
        graph.addEdge(edges2[1], nodes2[1], nodes2[2]);
        graph.addEdge(edges2[2], nodes2[1], nodes2[3]);
        graph.addEdge(edges2[3], nodes2[3], nodes2[1]);

        simulation.reset(graph);
        while (simulation.step()) {} //execution of all steps


        System.out.println("Good");

        if (!simulation.hasNegCycle() && simulation.getRound()==2 &&
                nodes2[0].getCost()==-6 && nodes2[1].getCost()==-1 && nodes2[2].getCost()==4 &&
                nodes2[1].getParent().equals(nodes2[3]) && edges2[1].getColor()== COLOR_ANS) {
            System.out.println("pass samplerun.pdf Example4!");
        }

        //write your own testing code ...

        //System.out.println(simulation.getRound());
        //System.out.println(nodes2[0].getCost());
    }

}