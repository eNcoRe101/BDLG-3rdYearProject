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
public class Edge {
    private final EdgeType et;
    private final VertexBDLG firstV;
    private final VertexBDLG secondV;
    
    public Edge(EdgeType et, VertexBDLG v, VertexBDLG vv){
        this.et = et;
        this.firstV = v;
        this.secondV = vv;
    }

    public EdgeType getEdgeType() {
        return et;
    }

    public VertexBDLG getFirstVertex() {
        return firstV;
    }

    public VertexBDLG getSecondVertex() {
        return secondV;
    }
}
