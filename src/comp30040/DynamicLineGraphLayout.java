package comp30040;


import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.Transformer;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by rich on 26/10/2015.
 */
public class DynamicLineGraphLayout<V, E> extends AbstractLayout<V, E> {
    public DynamicLineGraphLayout(Graph<V, E> g){
        super(g);
    }

    public DynamicLineGraphLayout(Graph<V, E> g, Dimension size){
        super(g, size);
    }

    public DynamicLineGraphLayout(Graph<V,E> g, Transformer<V,Point2D> initializer){
        super(g, initializer);
    }

    public DynamicLineGraphLayout(Graph<V,E> g, Transformer<V,Point2D> initializer,
    Dimension size) {
        super(g, initializer, size);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void reset() {

    }
}
