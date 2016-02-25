/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import edu.uci.ics.jung.graph.util.EdgeType;

/**
 *
 * @author rich
 */
public class Edge<V> {
    private final EdgeType et;
    private final V firstV;
    private final V secondV;
    
    public Edge(EdgeType et, V v, V vv){
        this.et = et;
        this.firstV = v;
        this.secondV = vv;
    }
  
    public EdgeType getEdgeType() {
        return et;
    }

    public V getFirstVertex() {
        return firstV;
    }

    public V getSecondVertex() {
        return secondV;
    }
    
    @Override
    public String toString(){
        return this.firstV.toString() + this.secondV.toString();
    }
}
