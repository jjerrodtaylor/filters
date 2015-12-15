package edu.uba.filters.Graph;

import edu.uba.filters.Frequency;

import java.util.*;

/**
 * Created by jamaaltaylor on 11/24/15.
 */
public class Node{
    private String label;
    private List<Node> adjacencyList = new ArrayList<Node>();
    private Frequency<String> distribution = new Frequency<String>();
    private Map<String,Double> weights = new HashMap<String,Double>();
    private String[] featureLabels;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Node> getAdjacencyList(){
        return adjacencyList;
    }

    public List<String> getAdjacencyListLabels(){
        List<String> labels = new ArrayList<String>();

        for(Node n:adjacencyList){
            labels.add(n.getLabel());
        }

        return labels;
    }

    public void setWeight(String label, Double weight){
        weights.put(label,weight);
    }

    public Map<String,Double> getWeights(){
        return weights;
    }

    public void addNeighbour(Node neighbour){
        adjacencyList.add(neighbour);
    }

    public String[] getFeatureLabels() {
        return featureLabels;
    }

    public void setFeatureLabels(String[] featureLabels) {
        this.featureLabels = featureLabels;
    }

    public void setDistribution(List<String> data){

        for(String s:data){
            distribution.addValue(s);
        }
    }

    public double getDistributionValue(String value){
        return distribution.getPct(value);
    }
}
