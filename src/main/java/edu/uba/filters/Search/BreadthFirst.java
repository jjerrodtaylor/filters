package edu.uba.filters.Search;

import edu.uba.filters.Graph.Graph;
import edu.uba.filters.Graph.Node;
import edu.uba.filters.Search.Search;

import java.util.*;

/**
 * Created by jamaaltaylor on 12/15/15.
 */
public class BreadthFirst implements Search {

    private Graph graph;
    private Queue<Node> queue = new LinkedList<Node>();
    private Set<String> visited = new HashSet<String>();

    public BreadthFirst(Graph graph,String startingVertex){
        this.graph = graph;
        this.queue.add(graph.getVertex(startingVertex));
        this.visited.add(startingVertex);
    }

    public BreadthFirst(Graph graph){
        this.graph = graph;
    }

    public boolean hasNext(){
        return !this.queue.isEmpty();
    }

    public Node next(){

        Node next = queue.remove();
        for(Node neighbour:graph.getNeighbors(next.getLabel())){
            if(!visited.contains(neighbour.getLabel())){
                queue.add(neighbour);
                visited.add(neighbour.getLabel());
            }
        }
        return next;
    }

    public Double find(String valueToFind){
        boolean found = false;
        Node searchHere;
        Double value = null;
        Map<String,Double> weights;

        while(found == false){
            if(this.hasNext() == true){
                searchHere = next();
                weights = searchHere.getWeights();
                if(weights.containsKey(valueToFind) == true){
                    found = true;
                    value = weights.get(valueToFind);
                }
            }
        }

        return value;
    }


}
