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
public class BiDynamicLineGraphLayout<V, E> extends AbstractLayout<V, E> {
    private BiDynamicLineGraph dglGraph;
    
    public BiDynamicLineGraphLayout(BiDynamicLineGraph<V, E> g){
        super(g);
        dglGraph = g;
    }

    public BiDynamicLineGraphLayout(BiDynamicLineGraph<V, E> g, Dimension size){
        super(g, size);
        dglGraph = g;
    }

    public BiDynamicLineGraphLayout(BiDynamicLineGraph<V,E> g, Transformer<V,Point2D> initializer){
        super(g, initializer);
        dglGraph = g;
    }

    public BiDynamicLineGraphLayout(BiDynamicLineGraph<V,E> g, Transformer<V,Point2D> initializer,
    Dimension size) {
        super(g, initializer, size);
        dglGraph = g;
    }

    @Override
    public void initialize() {
        Point2D p = new Point2D.Double(10, 10);
        System.out.println("W" + size.getWidth() + " H " + size.getHeight());
        double eventSpacingX = (this.size.getWidth()/dglGraph.getNumberOfActors());
        double eventSpacingY = (this.size.getHeight()/dglGraph.getNumberOfEvents());
        System.out.println("W" + eventSpacingX + " H " + eventSpacingY);
        Point2D pp = new Point2D.Double(p.getX(), p.getY());
        for(NetworkEvent e : dglGraph.getEvents()){
            for(Actor a: e.getActorsAtEvent()){
                pp.setLocation(p.getX() + (eventSpacingX * a.getId()),
                               p.getY());
                this.setLocation((V) new VertexBDLG(a, e), pp);
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
