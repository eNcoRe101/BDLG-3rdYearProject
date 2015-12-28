/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author rich
 */
public class KnowledgeDiffusionCalculator {
    private BiDynamicLineGraph graph;
    private double[][] finalKnowlageTable = null;
    private double alphaEventKnowlageGain = 1;
    private double betaActorKnowlageDiffusionCoeffient = 0.5;
    
    public KnowledgeDiffusionCalculator(BiDynamicLineGraph graph){
        this.graph = graph;
       this.finalKnowlageTable = new double[this.graph.getNumberOfActors()][this.graph.getNumberOfActors()];
    }
    
    public void updateAlphaGain(double alpha){
        this.alphaEventKnowlageGain = alpha;
    }
    
    public void updateBetaCoeffient(double beta){
        this.betaActorKnowlageDiffusionCoeffient = beta;
    }
    
    public double[][] getKnowlageTable(){
        return this.finalKnowlageTable;
    }
    
    public double getKnowlageFromActors(Actor aOne, Actor aTwo){
        return this.finalKnowlageTable[aOne.getId()][aTwo.getId()];
    }
    
    private double findKnowlageDifusionActorToOther(Actor i, Actor j){
        double[][] knowlageMatrix = new double[graph.getNumberOfActors()][graph.getNumberOfEvents()];
        for(int index = 0; index < graph.getNumberOfActors(); index++){
            knowlageMatrix[0][index] = 0;
        }
        //TODO: fill out stub, add path finding and prams
        int k = 1; // k == (2-1) dude to compsie index starting at 0
        while(k < graph.getNumberOfEvents()){
            if(graph.isActorAtEvent(i, graph.getEvents()[k])){
                PathFinder pathF = new PathFinder(this.graph);
                pathF.getPathsFrom(new VertexBDLG(i, graph.getEvents()[k]), j, new ArrayList<PathPair>());
                ArrayList<ArrayList<PathPair>> pathsToUse = pathF.getPaths();
                //caculater knowlage for I at K event
                knowlageMatrix[1][k] = knowlageMatrix[1][k-1] + this.alphaEventKnowlageGain;
                //get paths from this actor at this event to any event J attends
                    //caculate knowlage transfer 
                    //place path on ferbiden list
            }
            k++;
        }
        
        return 0.0;
    }
    
    private ArrayList<ArrayList<VertexBDLG>> getShortestPathsFromVertexItoJ(VertexBDLG vI, VertexBDLG vJ){
        
        return null;
    }
}
