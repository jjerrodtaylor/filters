package edu.uba.filters.Search;

import edu.uba.filters.Graph.Graph;
import edu.uba.filters.Graph.Node;

import java.util.*;


public class DepthFirst implements Search {

    private Graph graph;
    private Deque<Node> queue = new LinkedList<>();
    private Set<String> visited = new HashSet<>();

    public DepthFirst(Graph g, String startingVertex){
        this.graph = g;
        this.queue.addFirst(g.getVertex(startingVertex));
        this.visited.add(startingVertex);
    }

    public boolean hasNext(){
        return !this.queue.isEmpty();
    }

    public Node next(){

        //remove node from the queue
        Node next = queue.removeFirst();

        //for each neighbour in the node that you are currently examining
        for(Node neighbour:graph.getNeighbors(next.getLabel())){

            //if the node hasn't been visited
            if(!visited.contains(neighbour.getLabel())){

                //add it to the front of the queue
                this.queue.addFirst(neighbour);
                this.visited.add(neighbour.getLabel());
            }
        }

        return next;
    }

    public Double find(String valueToFind){

        boolean found = false;
        Node searchHere;
        Double value = null;
        Map<String,Double> weights;

        //search as long as you haven't found your target
        while (found == false){

            //if there is a next option
            if(this.hasNext() == true){

                //grab the next one
                searchHere = next();
                weights = searchHere.getWeights();
                if(weights.containsKey(valueToFind)){
                    found = true;
                    value = weights.get(valueToFind);
                }
            }
        }
        return value;
    }
}
