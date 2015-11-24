package edu.uba.filters.Graph;

import java.util.*;


public class UndirectedGraph implements Graph {


    Map<String,Node> graph = new HashMap<String,Node>();

    public void addVertex(String label){
        Node vertex = new Node();
        vertex.setLabel(label);
        graph.put(label, vertex);
    }

    public void addEdge(String here, String there){
        Node nHere = graph.get(here);
        Node nThere = graph.get(there);
        nHere.addNeighbour(nThere);
        nThere.addNeighbour(nHere);
        graph.put(here,nHere);
        graph.put(there,nThere);
    }

    public List<Node> getNeighbors(String vertex){
        return graph.get(vertex).adjacencyList;
    }

    public int degree(String vertex){
        return graph.get(vertex).adjacencyList.size();
    }

    public boolean hasVertex(String vertex){
        return graph.containsKey(vertex);
    }

    public boolean hasEdge(String here, String there){
        Set<Node> nHere = new HashSet<Node>(graph.get(here).adjacencyList);
        Set<Node> nThere = new HashSet<Node>(graph.get(there).adjacencyList);

        boolean hereConThere = nHere.contains(there);
        boolean thereConHere = nThere.contains(here);

        return (hereConThere & thereConHere);
    }
}
