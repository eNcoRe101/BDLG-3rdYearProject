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
import java.awt.Dimension;
import javax.swing.JFrame;
import GraphGUI.DynamicLineGraphGUI;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
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
        g.addVertex("A1");
        g.addVertex("A2");
        g.addVertex("B2");
        g.addVertex("C2");
        g.addEdge("E1", "A1", "A2");
        g.addEdge("E2", "A2", "B2");
        g.addEdge("E22", "B2", "A2");
        g.addEdge("E3", "A2", "C2");
        g.addEdge("E32", "C2", "A2");
        g.addEdge("E4", "B2", "C2");
        g.addEdge("E42", "C2", "B2");
        Layout<String, String> layout = new SpringLayout2<String, String>(g);
        DynamicLineGraphGUI mainWindow  = new DynamicLineGraphGUI();
        
        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<>(layout, new Dimension(300,300));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        mainWindow.setVisible(false);
        mainWindow.setContentPane(vv);

        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
        //mainWindow.drawWindow();
    }
}
