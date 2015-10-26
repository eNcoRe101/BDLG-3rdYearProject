/*
 *
 */

package comp30040;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import java.awt.Dimension;
import javax.swing.JFrame;
import GraphGUI.DynamicLineGraphGUI;

/**
 *
 * @author Richard de Mellow
 */
public class COMP30040 {

    public static void main(String[] args) {
        System.out.println("Starting Application");
        DynamicLineGraphGUI mainWindow  = new DynamicLineGraphGUI();
        mainWindow.drawWindow();
    }
}
