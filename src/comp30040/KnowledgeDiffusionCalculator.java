/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

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
    
    private double findKnowlageDifusionBetweenVertexs(){
        //TODO: fill out stub, add path finding and prams
        return 0.0;
    }
    
    
}
