package edu.uba.filters.Graph;

import java.util.*;


public class DirectedGraph implements Graph {

    Map<String,Node> graph = new HashMap<String,Node>();

    public void addVertex(String label){
        Node vertex = new Node();
        vertex.setLabel(label);
        graph.put(label, vertex);
    }

    public void addEdge(String here, String there){
        Node nHere = graph.get(here);
        Node nThere = graph.get(there);
        nThere.addNeighbour(nHere);
        graph.put(there,nThere);
    }

    public List<Node> getNeighbors(String vertex){
        return graph.get(vertex).getAdjacencyList();
    }

    public int degree(String vertex){
        return graph.get(vertex).getAdjacencyList().size();
    }

    public boolean hasVertex(String vertex){
        return graph.containsKey(vertex);
    }

    public boolean hasEdge(String here, String there){
        Set<String> nThere = new HashSet<String>(graph.get(there).getAdjacencyListLabels());
        boolean thereConHere = nThere.contains(here);
        return (thereConHere);
    }
}
