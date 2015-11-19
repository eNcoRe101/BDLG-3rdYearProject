/*
 *
 */

package comp30040;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.KPartiteGraph;
import edu.uci.ics.jung.graph.OrderedKAryTree;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import GraphGUI.BiDynamicLineGraphGUI;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

import javax.swing.SwingUtilities;

import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.LayoutScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Richard de Mellow
 */
public class COMP30040 {
    
    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("Starting Application");
        Point2D p = new Point2D.Double(50.0, 0.0);
        GraphImporter imp = new GraphImporter("/home/mbax2rd2/COMP30040/SourceCode/COMP30040/data/mafia-2mode.csv");
        BiDynamicLineGraph g = new BiDynamicLineGraph(imp);
        
        
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
        System.out.println(g);
        Layout<String, String> layout =  new BiDynamicLineGraphLayout<>(g);
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
        
        


        BiDynamicLineGraphGUI mainWindow  = new BiDynamicLineGraphGUI();
        
        Transformer<String,Shape> newVertexSize = new Transformer<String, Shape>(){
            public Shape transform(String s){
                Ellipse2D circle;
                return circle = new Ellipse2D.Double(-7, -7, 14, 14);
            }
        };

        VisualizationViewer<String, String> vv = new VisualizationViewer<>(layout, new Dimension(1400,900));
        /*Transformer<Context<Graph<String,String>,String>,Shape> drawEdges = Transformer<Context<Graph<String,String>,String>,javax.media.j3d.Node>(){
        
        };*/
        vv.getRenderContext().setEdgeShapeTransformer(
                new EdgeShape.Line<String,String>());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        
        ScalingControl visualizationViewerScalingControl = new LayoutScalingControl();

        vv.scaleToLayout(visualizationViewerScalingControl);
        
        vv.setGraphMouse(graphMouse);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        vv.getRenderContext().setVertexShapeTransformer(newVertexSize);

        mainWindow.getContentPane().add(vv, BorderLayout.EAST);
        

        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
 
        //mainWindow.drawWindow();
    }
}
