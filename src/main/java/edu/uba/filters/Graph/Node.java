package edu.uba.filters.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamaaltaylor on 11/24/15.
 */
public class Node{
    private String label;
    List<Node> adjacencyList = new ArrayList<Node>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void addNeighbour(Node neighbour){
        adjacencyList.add(neighbour);
    }

}
