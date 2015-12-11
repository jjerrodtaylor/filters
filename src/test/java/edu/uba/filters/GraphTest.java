package edu.uba.filters;


import edu.uba.filters.Graph.DirectedGraph;
import edu.uba.filters.Graph.Graph;
import edu.uba.filters.Graph.Node;
import edu.uba.filters.Graph.UndirectedGraph;
import org.junit.Test;
import static org.junit.Assert.*;


public class GraphTest {

    @Test
    public void nodeTest(){
        Node node = new Node();
        node.setLabel("test");
        assertEquals("test",node.getLabel());
        Node neighbour = new Node();
        node.addNeighbour(neighbour);
        assertEquals(neighbour,node.getAdjacencyList().get(0));
    }

    @Test
    public void undirectedGraphTest(){
        Graph undirected = new UndirectedGraph();
        undirected.addVertex("A");
        assertEquals(true, undirected.hasVertex("A"));
        undirected.addVertex("B");
        undirected.addEdge("A","B");
        assertEquals(true,undirected.hasEdge("A","B"));
        assertEquals(true, undirected.hasEdge("B", "A"));
        undirected.addVertex("C");
        undirected.addEdge("A","C");
        assertEquals(2, undirected.degree("A"));
    }

    @Test
    public void directedGraphTest(){
        Graph directed = new DirectedGraph();
        directed.addVertex("A");
        directed.addVertex("B");
        directed.addVertex("C");
        assertEquals(true,directed.hasVertex("A"));
        directed.addEdge("A","B");
        assertEquals(true,directed.hasEdge("A","B"));
        assertEquals(false,directed.hasEdge("B","A"));
    }
}
