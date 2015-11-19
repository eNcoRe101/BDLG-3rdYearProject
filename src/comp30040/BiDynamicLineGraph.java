/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import comp30040.NetworkEvent;

import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 *
 * @author rich
 * @param <String>
 * @param <V>
 */
public class BiDynamicLineGraph<V, E> extends SparseGraph<String, String> {
    private GraphImporter imp = null;
    
    public BiDynamicLineGraph(){
        super();
    }
    
    public BiDynamicLineGraph(GraphImporter imp){
        super();
        this.imp = imp;
    }
    
    public int getNumberOfActors(){
        return this.imp.getNumberOfActors();
    }
    
    public int getNumberOfEvents(){
        return this.imp.getEvents().length;
    }
    
    public NetworkEvent[] getEvents(){
        return this.imp.getEvents();
    }
    
    private void genrateGraphFromImp(){
        int numberOfActors = imp.getNumberOfActors();
        NetworkEvent[] theEvents = imp.getEvents();
        for(int i = 0; i < theEvents.length; i++){
            NetworkEvent e = theEvents[i];
            for(Actor a : e.getActorsAtEvent())
            {
                this.addVertex(a + e.getLabel());
                
                for(Actor aa : e.getActorsAtEvent())
                {
                    if(!a.equals(aa)){
                        this.addEdge(a + e.getLabel() + aa + e.getLabel(), a + e.getLabel(), aa + e.getLabel(), EdgeType.UNDIRECTED);
                    }
                }
                for(int j = i+1; j < theEvents.length; j++)
                    if(!e.equals(theEvents[j]) 
                        && theEvents[j].isActorAtEvent(a)){
                            this.addEdge(a + e.getLabel() + theEvents[j].getLabel(),
                                    a + e.getLabel(), a + theEvents[j].getLabel(), EdgeType.DIRECTED);
                            break;
                    }
            }
        }
    }

}
