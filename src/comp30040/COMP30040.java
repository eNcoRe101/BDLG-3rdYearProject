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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            
        }
        BiDynamicLineGraphGUI mainWindow  = new BiDynamicLineGraphGUI();
        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
    }
}