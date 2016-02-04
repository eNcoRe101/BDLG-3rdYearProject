/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

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
       for(Actor aOne : graph.getActors()){
           for(Actor aTwo : graph.getActors()){
                if(aOne.equals(aTwo)){
                   this.finalKnowlageTable[aOne.getId()-1][aTwo.getId()-1] = 0.0;
                   continue;
                }
                double[] tmpKnowlage = findKnowlageDifusionActorToOther(aOne, aTwo);
                for(int i = 0; i < tmpKnowlage.length; i++)
                    this.finalKnowlageTable[aOne.getId()-1][aTwo.getId()-1] += tmpKnowlage[i];
          
           }
       }
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
    
    public JTable getKnowlageTableAsJTable(){
        JTable tableToReturn = new JTable();
        return tableToReturn;
    }
    
    public double getKnowlageFromActors(Actor aOne, Actor aTwo){
        return this.finalKnowlageTable[aOne.getId()-1][aTwo.getId()-1];
    }
    
    public double[] getKnowlageFromActorsAsArray(Actor aOne, Actor aTwo){
        return findKnowlageDifusionActorToOther(aOne, aTwo);
    }
    
    private double[] findKnowlageDifusionActorToOther(Actor i, Actor j){
        
        double[][] knowlageMatrix = new double[2][graph.getNumberOfEvents()]; //knowlage carred forward && 
                                                                              //diffused knowlage to actor J at event K
        if(i.equals(j)) return knowlageMatrix[1];
        
        //TODO: fill out stub, add path finding and prams
        int k = 1; // k == (2-1) dude to compsie index starting at 0
        while(k < graph.getNumberOfEvents()){
            knowlageMatrix[0][k] = knowlageMatrix[0][k-1];
            if(graph.isActorAtEvent(i, graph.getEvents()[k])){
                //caculater knowlage for I at K event
                if(!graph.isActorsFirstEvent(i, graph.getEvents()[k]))
                    knowlageMatrix[0][k] += this.alphaEventKnowlageGain;
                //get paths from this actor at this event to any event J attends
                PathFinder pathF = new PathFinder(this.graph);
                pathF.getPathsFrom(new VertexBDLG(i, graph.getEvents()[k]), j, new ArrayList<PathPair>());
                ArrayList<List<PathPair>> pathsToUse = pathF.getPaths();
                //caculate knowlage transfer 
                double knowlageGainForThisPath = knowlageMatrix[0][k];
                double demominator;
                for(List<PathPair> pp : pathsToUse){
                    System.out.print(pathF.printPath(pp));
                    System.out.println("Inistal K : " + knowlageMatrix[0][k]);
                    demominator = 1;
                    for(PathPair p : pp){
                        if(p.et == null) break;
                        if(p.et == EdgeType.UNDIRECTED)
                            demominator *= this.betaActorKnowlageDiffusionCoeffient;
                    }
                    System.out.println("Dem : " + demominator);
                    knowlageMatrix[1][k] += ((knowlageMatrix[0][k]) * demominator);
                    System.out.println("Diffused " + (knowlageMatrix[0][k]) * demominator);
                    knowlageMatrix[0][k] -= (knowlageMatrix[0][k]) * demominator;
                    System.out.println("left over knowlage : " + knowlageMatrix[0][k]);
                    if(knowlageMatrix[0][k] < 0)
                        knowlageMatrix[0][k] = 0;
                }
                
                    //place path on ferbiden list
            }
            k++;
            
        }
        
        return knowlageMatrix[1];
    }
}
