package comp30040;


import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import java.awt.Dimension;
import org.apache.commons.collections15.Transformer;

import java.awt.geom.Point2D;

/**
 * Created by rich on 26/10/2015.
 * @param <V>
 * @param <E>
 */
public class BiDynamicLineGraphLayout<V, E> extends AbstractLayout<String, String> {
    private BiDynamicLineGraph dglGraph;
    
    public BiDynamicLineGraphLayout(BiDynamicLineGraph<V, E> g){
        super(g);
        dglGraph = g;
    }

    public BiDynamicLineGraphLayout(BiDynamicLineGraph<V, E> g, Dimension size){
        super(g, size);
        dglGraph = g;
    }

    public BiDynamicLineGraphLayout(BiDynamicLineGraph<V,E> g, Transformer<String,Point2D> initializer){
        super(g, initializer);
        dglGraph = g;
    }

    public BiDynamicLineGraphLayout(BiDynamicLineGraph<V,E> g, Transformer<String,Point2D> initializer,
    Dimension size) {
        super(g, initializer, size);
        dglGraph = g;
    }

    @Override
    public void initialize() {
        Point2D p = new Point2D.Double(10, 10);
        double eventSpacingX = (size.getWidth()/dglGraph.getNumberOfActors())*10;
        double eventSpacingY = (size.getHeight()/dglGraph.getNumberOfEvents())*3;
        Point2D pp = new Point2D.Double(p.getX(), p.getY());
        for(NetworkEvent e : dglGraph.getEvents()){
            for(Actor a: e.getActorsAtEvent()){
                pp.setLocation(p.getX() + (eventSpacingX*Integer.parseInt(a.getLabel().substring(1))),
                              p.getY() );
                this.setLocation(a+e.getLabel(), pp);
            }
            p.setLocation(p.getX(), p.getY() + eventSpacingY);
        }
    }
    
    @Override
    public BiDynamicLineGraph getGraph(){
        return this.dglGraph;
    }
    
    public BiDynamicLineGraph getBiGraph(){
        return this.dglGraph;
    }

    @Override
    public void reset() {
        initialize();
    }
}
