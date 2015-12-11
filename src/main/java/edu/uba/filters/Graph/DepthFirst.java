package edu.uba.filters.Graph;


import java.util.*;

/**
 * Created by jamaaltaylor on 12/11/15.
 */
public class DepthFirst implements Iterator<String> {

    private Set<String> visited = new HashSet<String>();
    private Deque<Iterator<String>> stack = new LinkedList<Iterator<String>>();
    private Graph graph = new UndirectedGraph();

    public void remove(){

    }

    public String next(){
        return "";
    }

    public boolean hasNext(){
        return true;
    }

}
