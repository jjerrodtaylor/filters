package edu.uba.filters.Graph;


import java.util.List;

public interface Graph {

    public void addVertex(String label);

    public void addEdge(String here, String there);

    public List<Node> getNeighbors(String vertex);

    public int degree(String vertex);

    public boolean hasVertex(String vertex);

    public boolean hasEdge(String here, String there);

    public Node getVertex(String label);

}
