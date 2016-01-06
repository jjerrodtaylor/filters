package edu.uba.filters;

import edu.uba.filters.Search.BreadthFirst;
import edu.uba.filters.Graph.Graph;
import edu.uba.filters.Graph.UndirectedGraph;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BreadthFirstTest {

    @Test
    public void testHasNext(){
        Graph graph = new UndirectedGraph();

        BreadthFirst search = new BreadthFirst(graph);
        Boolean wontPass = search.hasNext();

        assertEquals(false,wontPass);
    }

    @Test
    public void testNext(){
        Graph graph = new UndirectedGraph();
        graph.addVertex("head");
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");

        graph.addEdge("head","a");
        graph.addEdge("head","b");
        graph.addEdge("a","c");

        BreadthFirst bf = new BreadthFirst(graph,"head");

        assertEquals(graph.getVertex("head"),bf.next());
        assertEquals(graph.getVertex("a"),bf.next());
        assertEquals(graph.getVertex("b"), bf.next());
        assertEquals(graph.getVertex("c"),bf.next());
    }

    @Test
    public void testFind(){
        Graph graph = new UndirectedGraph();
        graph.addVertex("head");
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.getVertex("c").getWeights().put("test",.85);

        graph.addEdge("head","a");
        graph.addEdge("head","b");
        graph.addEdge("a","c");

        BreadthFirst bf = new BreadthFirst(graph,"head");

        assertEquals(.85,bf.find("test"),0.0);
    }


}
