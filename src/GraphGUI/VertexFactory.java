/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGUI;

import comp30040.BiDynamicLineGraph;
import comp30040.VertexBDLG;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author rich
 */
class VertexFactory implements Factory<VertexBDLG> {
    BiDynamicLineGraph g;
    public VertexFactory(BiDynamicLineGraph g) {
        this.g = g;
    }

    @Override
    public VertexBDLG create() {
        return null;
    }
    
}
