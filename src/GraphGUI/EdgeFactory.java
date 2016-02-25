/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGUI;

import comp30040.Edge;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author rich
 */
class EdgeFactory<V> implements Factory<Edge> {

    public EdgeFactory() {
    }

    @Override
    public Edge create() {
        return null;
    }
    
    public Edge create(EdgeType et, V v, V vv) {
        return new Edge(et, v, vv);
    }
}
