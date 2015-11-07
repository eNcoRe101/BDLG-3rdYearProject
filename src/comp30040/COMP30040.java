/*
 *
 */

package comp30040;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.KPartiteGraph;
import edu.uci.ics.jung.graph.OrderedKAryTree;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import GraphGUI.DynamicLineGraphGUI;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

import javax.swing.SwingUtilities;

import comp30040.GraphImporter;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.LayoutScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import java.util.Random;

/**
 *
 * @author Richard de Mellow
 */
public class COMP30040 {

    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("Starting Application");
        Point2D p = new Point2D.Double(50.0, 0.0);
        DirectedSparseGraph g = new DirectedSparseGraph();
        GraphImporter imp = new GraphImporter("/Users/rich/uni/COMP30040/SourceCode/COMP30040/data/mafia-2mode.csv");
        NetworkEvent[] theEvents = imp.getEvents();
        for(int i = 0; i < theEvents.length; i++){
            NetworkEvent e = theEvents[i];
            for(Actor a : e.getActorsAtEvent())
            {
                g.addVertex(a + e.getLabel());
                
                for(Actor aa : e.getActorsAtEvent())
                {
                    if(!a.equals(aa)){
                        g.addEdge(a + e.getLabel() + aa + e.getLabel(), a + e.getLabel(), aa + e.getLabel());
                    }
                }
                if(i < theEvents.length -1 
                       && !e.equals(theEvents[i+1]) 
                        && theEvents[i+1].isActorAtEvent(a)){
                        g.addEdge(a + e.getLabel() + theEvents[i+1].getLabel(),
                                a + e.getLabel(), a + theEvents[i+1].getLabel());
                }
            }
        }
        /*
        //1
        g.addVertex("A1");
        //2
        g.addVertex("A2");
        g.addVertex("B2");
        g.addVertex("C2");
        //3
        g.addVertex("A3");
        g.addVertex("B3");

        g.addEdge("E1", "A1", "A2");
        g.addEdge("E2", "A2", "B2");
        g.addEdge("E22", "B2", "A2");
        g.addEdge("E3", "A2", "C2");
        g.addEdge("E32", "C2", "A2");
        g.addEdge("E4", "B2", "C2");
        g.addEdge("E42", "C2", "B2");
        g.addEdge("E5", "A2", "A3");
        g.addEdge("E6", "A3", "B3");
        g.addEdge("E62", "B3", "A3");
        g.addEdge("E7", "B2", "B3");*/
        
        Layout<String, String> layout =  new DynamicLineGraphLayout<String, String>(g);
        /* p = new Point2D.Double(40.0, 20.0);
        layout.setLocation("A1", p);
        p = new Point2D.Double(40.0, 140.0);
        layout.setLocation("A2", p);
        p = new Point2D.Double(80.0, 100.0);
        layout.setLocation("B2", p);
        p = new Point2D.Double(100.0, 140.0);
        layout.setLocation("C2", p);

        p = new Point2D.Double(40.0, 240.0);
        layout.setLocation("A3", p);
        p = new Point2D.Double(80.0, 240.0);
        layout.setLocation("B3", p);*/
        
        double eventSpacingX = 1440/imp.getEvents().length*1.5;
        double eventSpacingY = 900/imp.getEvents().length*5;
        Random randomGenerator = new Random();
        for(NetworkEvent e : imp.getEvents()){
            int numberOfActorSeen = 0;
            for(Actor a: e.getActorsAtEvent()){
                Point2D pp = new Point2D.Double(p.getX(), p.getY());
                pp.setLocation(p.getX() + (numberOfActorSeen*50),
                              p.getY() );
                layout.setLocation(a+e.getLabel(), pp);
                numberOfActorSeen++;
            }
            p.setLocation(p.getX() , p.getY() + eventSpacingY);
        }


        DynamicLineGraphGUI mainWindow  = new DynamicLineGraphGUI();

        VisualizationViewer<String, String> vv = new VisualizationViewer<>(layout, new Dimension(1440,900));
        vv.getRenderContext().setEdgeShapeTransformer(
                new EdgeShape.Line<String,String>());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        
        ScalingControl visualizationViewerScalingControl = new LayoutScalingControl();

        vv.scaleToLayout(visualizationViewerScalingControl);
        
        vv.setGraphMouse(graphMouse);
        mainWindow.setVisible(false);
        mainWindow.setContentPane(vv);

        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
        //mainWindow.drawWindow();
    }
}
