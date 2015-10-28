/*
 *
 */

package comp30040;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.KPartiteGraph;
import edu.uci.ics.jung.graph.OrderedKAryTree;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import GraphGUI.DynamicLineGraphGUI;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import javax.swing.SwingUtilities;

/**
 *
 * @author Richard de Mellow
 */
public class COMP30040 {

    public static void main(String[] args) {
        System.out.println("Starting Application");
        DirectedSparseGraph g = new DirectedSparseGraph();
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
        g.addEdge("E7", "B2", "B3");

        Layout<String, String> layout = new StaticLayout<String, String>(g);
        Point2D p = new Point2D.Double(40.0, 20.0);
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
        layout.setLocation("B3", p);


        DynamicLineGraphGUI mainWindow  = new DynamicLineGraphGUI();

        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<>(layout, new Dimension(600,600));
        vv.getRenderContext().setEdgeShapeTransformer(
                new EdgeShape.Line<String,String>());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        mainWindow.setVisible(false);
        mainWindow.setContentPane(vv);

        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
        //mainWindow.drawWindow();
    }
}
