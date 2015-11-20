/*
 *
 */

package comp30040;

import GraphGUI.BiDynamicLineGraphGUI;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.LayoutScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.Shape;

import java.awt.geom.Ellipse2D;
import java.io.FileNotFoundException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Richard de Mellow
 */
public class COMP30040 {
    
    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("Starting Application");
        //GraphImporter imp = new GraphImporter("/Users/rich/uni/COMP30040/SourceCode/COMP30040/data/mafia-2mode.csv");
        //BiDynamicLineGraph g = new BiDynamicLineGraph(imp);
        
        //System.out.println(g);
        //set theam Nimbus
        //Layout<String, String> layout =  new BiDynamicLineGraphLayout<>(g);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        BiDynamicLineGraphGUI mainWindow  = new BiDynamicLineGraphGUI();
        
        Transformer<String,Shape> newVertexSize = new Transformer<String, Shape>(){
            public Shape transform(String s){
                Ellipse2D circle;
                return circle = new Ellipse2D.Double(-3, -3, 6, 6);
            }
        };

        //VisualizationViewer<String, String> vv = new VisualizationViewer<>(layout);
        /*Transformer<Context<Graph<String,String>,String>,Shape> drawEdges = Transformer<Context<Graph<String,String>,String>,javax.media.j3d.Node>(){
        
        };*/
        //vv.getRenderContext().setEdgeShapeTransformer(
        //        new EdgeShape.Line<String,String>());
        //vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        
        ScalingControl visualizationViewerScalingControl = new LayoutScalingControl();

        //vv.scaleToLayout(visualizationViewerScalingControl);
        
        //vv.setGraphMouse(graphMouse);
        //vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
//        vv.getRenderContext().setVertexShapeTransformer(newVertexSize);
        
        ScrollPane sp = new ScrollPane();
        //sp.add(vv);
        mainWindow.getContentPane().add(sp, BorderLayout.CENTER);
        

        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
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