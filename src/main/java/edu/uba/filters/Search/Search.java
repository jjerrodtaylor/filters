package edu.uba.filters.Search;

import edu.uba.filters.Graph.Node;

/**
 * Created by jamaaltaylor on 12/29/15.
 */
public interface Search {

    public boolean hasNext();
    public Node next();
    public Double find(String valueToFind);
}
