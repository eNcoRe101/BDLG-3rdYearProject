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
    BiDynamicLineGraph graph;
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
    
    
    
}
