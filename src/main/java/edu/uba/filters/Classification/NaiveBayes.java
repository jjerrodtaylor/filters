package edu.uba.filters.Classification;

import edu.uba.filters.Primatives.Frequency;
import edu.uba.filters.Graph.Graph;
import edu.uba.filters.Graph.UndirectedGraph;
import edu.uba.filters.Primatives.Probability;

import java.util.List;

/**
 * Created by jamaaltaylor on 12/11/15.
 */
public class NaiveBayes {

    private Graph graph = new UndirectedGraph();
    private String classificationLabel;
    private Probability probability = new Probability();
    private Frequency<String> frequency = new Frequency<String>();
    public NaiveBayes(){
        super();
    }

    public void setClassificationNode(String label){
        this.classificationLabel = label;
        graph.addVertex(label);
    }

    public void setFeatureNode(String label){
        graph.addVertex(label);
        graph.addEdge(classificationLabel,label);
    }

    public void setClassificationWeights(List<String> data){
        frequency.clear();
        String[] keys;
        for(String s:data){
            frequency.addValue(s);
        }

        keys = frequency.getKeys();
        graph.getVertex(classificationLabel).setFeatureLabels(keys);
        for(int i=0;i<keys.length;i++){
            graph.getVertex(classificationLabel)
                    .setWeight(keys[i], frequency.getPct(keys[i]));
        }
        frequency.clear();
    }

    public void setFeatureWeight(List<String> interestedSet, List<String> reducingSet, String label){
        frequency.clear();
        String[] interestedKeys;
        for(String s:interestedSet){
            frequency.addValue(s);
        }
        interestedKeys = frequency.getKeys();

        String[] reducingKeys;
        for(String s:reducingSet){
            frequency.addValue(s);
        }
        reducingKeys = frequency.getKeys();
        graph.getVertex(label).setFeatureLabels(interestedKeys);
        for(int i=0;i<interestedKeys.length;i++){
            for(int j=0;i<reducingKeys.length;j++){
                String newlabel = interestedKeys[i]+"|"+reducingKeys[j];
                graph.getVertex(label).
                        setWeight(newlabel,probability.
                                conditionalProbability(interestedSet,reducingSet,interestedKeys[i],reducingKeys[j]));
            }
        }
        frequency.clear();
    }

    public double getFeatureWeight(String label, String interested, String reducing){
        String newLabel = interested+"|"+reducing;
        Double featureWeight = graph.getVertex(label).getWeights().get(newLabel);
        return featureWeight;
    }




}
