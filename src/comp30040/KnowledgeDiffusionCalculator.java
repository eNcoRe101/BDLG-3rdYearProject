/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    
    public double[] getKnowlageFromActors(Actor aOne, Actor aTwo){
        return findKnowlageDifusionActorToOther(aOne, aTwo);
        //return this.finalKnowlageTable[aOne.getId()][aTwo.getId()];
    }
    
    private double[] findKnowlageDifusionActorToOther(Actor i, Actor j){
        double[][] knowlageMatrix = new double[2][graph.getNumberOfEvents()];
        for(int index = 0; index < graph.getNumberOfEvents(); index++){
            knowlageMatrix[0][index] = 0;
            knowlageMatrix[1][index] = 0;
        }
        //TODO: fill out stub, add path finding and prams
        int k = 1; // k == (2-1) dude to compsie index starting at 0
        while(k < graph.getNumberOfEvents()){
            knowlageMatrix[0][k] = knowlageMatrix[0][k-1];
            if(graph.isActorAtEvent(i, graph.getEvents()[k])){
                //caculater knowlage for I at K event
                knowlageMatrix[0][k] += this.alphaEventKnowlageGain;
                //get paths from this actor at this event to any event J attends
                PathFinder pathF = new PathFinder(this.graph);
                pathF.getPathsFrom(new VertexBDLG(i, graph.getEvents()[k]), j, new ArrayList<PathPair>());
                ArrayList<List<PathPair>> pathsToUse = pathF.getPaths();
                //caculate knowlage transfer 
                double knowlageGainForThisPath = knowlageMatrix[0][k];
                for(List<PathPair> pp : pathsToUse){
                    
                    for(PathPair p : pp){
                        if(p.et == null) break;
                        if(p.et == EdgeType.UNDIRECTED)
                            knowlageGainForThisPath *= this.betaActorKnowlageDiffusionCoeffient;
                    }
                    knowlageMatrix[1][k] += knowlageGainForThisPath;
                    knowlageGainForThisPath = knowlageMatrix[0][k] - knowlageMatrix[1][k];
                }
                
                    //place path on ferbiden list
            }
            k++;
            
        }
        
        return knowlageMatrix[1];
    }
    
    private ArrayList<ArrayList<VertexBDLG>> getShortestPathsFromVertexItoJ(VertexBDLG vI, VertexBDLG vJ){
        
        return null;
    }
}
