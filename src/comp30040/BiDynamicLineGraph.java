/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;

/**
 *
 * @author rich
 * @param <V>
 * @param <E>
 */
public class BiDynamicLineGraph<V, E> extends SparseGraph<VertexBDLG, String> {
    private GraphImporter imp = null;
    
    public BiDynamicLineGraph(){
        super();
    }
    
    public BiDynamicLineGraph(GraphImporter imp){
        super();
        this.imp = imp;
        genrateGraphFromImp();
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
    
    public Graph<String, String> getOneModeActorGraph(){
        Graph<String, String> newOneModeG = new SparseGraph<>();
        for(Actor a : imp.getActors())
        {
            newOneModeG.addVertex(a.getLabel());
            for(NetworkEvent e : imp.getEvents())
            {
                if(e.isActorAtEvent(a))
                {
                    for(Actor acAtE : e.getActorsAtEvent()){
                        if(!a.equals(acAtE))
                            newOneModeG.addEdge(a.getLabel() + acAtE.getLabel(),
                                    a.getLabel(), acAtE.getLabel());
                    }
                }
            }
                
        }
        return newOneModeG;
    }
     
    public Graph<String, String> getOneModeEventGraph(){
        Graph<String, String> newOneModeG = new SparseGraph<>();
        for(NetworkEvent e : imp.getEvents())
        {
            newOneModeG.addVertex(e.getLabel());
            for(Actor a : e.getActorsAtEvent())
            {
                for(NetworkEvent eOther : imp.getEvents()){
                    if(!e.equals(eOther) && eOther.isActorAtEvent(a))
                    {
                        newOneModeG.addEdge(e.getLabel() + eOther.getLabel(),
                                            e.getLabel(), eOther.getLabel());
                    }
                }
            }
                
        }
        return newOneModeG;
    }
    
    private void genrateGraphFromImp(){
        for(NetworkEvent e : imp.getEvents()){
            for(Actor a : e.getActorsAtEvent())
            {
                this.addVertex(new VertexBDLG(a, e));
            }
            
        }
        for(VertexBDLG v : this.getVertices())
        {
            for(VertexBDLG vv : this.getVertices())
            {
                if(v.getEvent().equals(vv.getEvent())){
                    this.addEdge(v.toString() + vv.toString(),
                                 v, vv, EdgeType.UNDIRECTED);
                }
                else if(v.getActor().equals(vv.getActor())
                        && v.getEvent().getEventId() < vv.getEvent().getEventId())
                {
                    this.addEdge(v.toString() + vv.toString(),
                                 v, vv, EdgeType.DIRECTED);
                }
            }
        }
    }

}
