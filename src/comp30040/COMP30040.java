/*
 *
 */

package comp30040;

import GraphGUI.BiDynamicLineGraphGUI;

import java.io.FileNotFoundException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        BiDynamicLineGraphGUI mainWindow  = new BiDynamicLineGraphGUI();
        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
    }
}