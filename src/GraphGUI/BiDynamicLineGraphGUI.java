/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGUI;

import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import java.io.File;
import javax.swing.JFileChooser;

import comp30040.GraphImporter;
import comp30040.BiDynamicLineGraph;
import comp30040.BiDynamicLineGraphLayout;
import comp30040.KnowledgeDiffusionCalculator;
import comp30040.VertexBDLG;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.LayoutScalingControl;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.collections15.Transformer;
/**
 *
 * @author Richard de Mellow
 */
public class BiDynamicLineGraphGUI extends javax.swing.JFrame {
    private Layout<VertexBDLG, String> layout = null;
    private Layout<String, String> layoutOneMode = null;
    private VisualizationViewer<VertexBDLG, String> vv = null;
    private VisualizationViewer<String, String> vvOneMode = null;
    private ScrollPane graphJPane = null;
    private File currentCVSFile = null;
    private BiDynamicLineGraph currentBidlg = null;
    private KnowledgeDiffusionCalculator kDC = null;
    int currentIndexOfSelectedView = 0;
    
    /**
     * Creates new form DynamicLineGraphGUI
     */
    public BiDynamicLineGraphGUI() {
        initComponents();
         /* Create and display the form */
    }
    
    public void drawWindow(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BiDynamicLineGraphGUI().setVisible(true);
            }
        });
    }
    
    private VisualizationViewer genrateVisualizationViewer(File fileToUse) throws FileNotFoundException{
        if(!fileToUse.exists())
            throw new FileNotFoundException();
        
        GraphImporter gi = new GraphImporter(fileToUse);
        this.currentBidlg = new BiDynamicLineGraph(gi);
        if(this.kDC == null)
            this.kDC = new KnowledgeDiffusionCalculator(this.currentBidlg);
        this.currentBidlg = this.kDC.getGraph();
        this.layout = new BiDynamicLineGraphLayout<>(this.currentBidlg, new Dimension(this.getWidth()-OptionsPanel.getWidth(), this.getHeight()));
        Transformer<VertexBDLG,Shape> newVertexSize = new Transformer<VertexBDLG, Shape>(){
            @Override
            public Shape transform(VertexBDLG v){
                double radius;
                System.out.println("This v's k = " +  v.getKnowlage());
                if(v.getKnowlage() != 0)
                    radius = 6/(double)v.getKnowlage();
                else
                    radius = 6;
                Ellipse2D circle;
                return circle = new Ellipse2D.Double(-radius/2, -radius/2, radius, radius);
            }
        };
        VisualizationViewer vv = new VisualizationViewer<>(this.layout);
        Transformer<String,EdgeShape> newEdgeTypes;
        /*newEdgeTypes = new Transformer<String, EdgeShape>(){
            @Override
            public EdgeShape.Line<String, String> transform(String i) {
                System.out.println(i);
                return new EdgeShape.Line<String,String>();
            }
        };*/
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<VertexBDLG,VertexBDLG>());
                
        
        DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        ScalingControl visualizationViewerScalingControl = new LayoutScalingControl();
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.GRAY));
        //vv.scaleToLayout(visualizationViewerScalingControl);
        vv.setGraphMouse(graphMouse);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);
        vv.getRenderContext().setVertexShapeTransformer(newVertexSize);
        return vv;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OptionsPanel = new javax.swing.JPanel();
        refreshGraphButton = new javax.swing.JButton();
        VisulizerPicker = new javax.swing.JComboBox<>();
        MainMenu = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        importcvs = new javax.swing.JMenuItem();
        export = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        EditMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bi-Dynamic Line Graph Viewer");
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("Home Frame"); // NOI18N

        OptionsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        OptionsPanel.setAlignmentX(0.0F);
        OptionsPanel.setAlignmentY(0.0F);
        OptionsPanel.setMaximumSize(new java.awt.Dimension(220, 32767));
        OptionsPanel.setPreferredSize(new java.awt.Dimension(220, 718));

        refreshGraphButton.setText("Refresh");

        VisulizerPicker.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BiDynamic Grid Layout", "BiDynamic Cluster Layout", "One-Mode Actor View", "One-Mode Event View", "View Knowlage Difusion"}));
        VisulizerPicker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                VisulizerPickerItemStateChanged(evt);
            }
        });
        VisulizerPicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VisulizerPickerActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout OptionsPanelLayout = new org.jdesktop.layout.GroupLayout(OptionsPanel);
        OptionsPanel.setLayout(OptionsPanelLayout);
        OptionsPanelLayout.setHorizontalGroup(
            OptionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(OptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(refreshGraphButton)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(VisulizerPicker, 0, 223, Short.MAX_VALUE)
        );
        OptionsPanelLayout.setVerticalGroup(
            OptionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(OptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(VisulizerPicker, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(82, 82, 82)
                .add(refreshGraphButton)
                .addContainerGap(931, Short.MAX_VALUE))
        );

        getContentPane().add(OptionsPanel, java.awt.BorderLayout.WEST);

        FileMenu.setText("File");

        importcvs.setText("Import 2-mode CVS");
        importcvs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importcvsActionPerformed(evt);
            }
        });
        FileMenu.add(importcvs);

        export.setText("Export");
        FileMenu.add(export);

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        FileMenu.add(exit);

        MainMenu.add(FileMenu);

        EditMenu.setText("Edit");
        MainMenu.add(EditMenu);

        setJMenuBar(MainMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void importcvsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importcvsActionPerformed
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv","csv");
        fc.setFileFilter(filter);
        fc.showOpenDialog(MainMenu);
        
        this.currentCVSFile = fc.getSelectedFile();
        try {
            this.vv = genrateVisualizationViewer(currentCVSFile);
            if(graphJPane != null)
                this.remove(graphJPane);
            graphJPane = new ScrollPane();
            graphJPane.add(vv);
            graphJPane.setPreferredSize(new Dimension(this.getWidth()-OptionsPanel.getWidth(), this.getHeight()));
            this.getContentPane().add(graphJPane, BorderLayout.CENTER);
            this.invalidate();
            this.repaint();
            this.pack();
            System.out.println(currentCVSFile.getAbsoluteFile());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BiDynamicLineGraphGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_importcvsActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void VisulizerPickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VisulizerPickerActionPerformed
        int currentSelectedItem = this.VisulizerPicker.getSelectedIndex();
        if(currentIndexOfSelectedView != currentSelectedItem)
        {
            DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
            Graph<String, String> gg;
            currentIndexOfSelectedView = currentSelectedItem;
            switch(currentSelectedItem){
                case 0:
                    try {
                        this.vv = genrateVisualizationViewer(currentCVSFile);
                        if(graphJPane != null)
                            this.remove(graphJPane);
                        graphJPane = new ScrollPane();
                        graphJPane.add(vv);
                        graphJPane.setPreferredSize(new Dimension(this.getWidth()-OptionsPanel.getWidth(), this.getHeight()));
                        this.getContentPane().add(graphJPane, BorderLayout.CENTER);
                        this.layout.setSize(this.getSize());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(BiDynamicLineGraphGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 1:
                    break;
                case 2:
                    gg = this.currentBidlg.getOneModeActorGraph();
                    this.layoutOneMode = new KKLayout<>(gg);
                    this.vvOneMode = new VisualizationViewer<>(this.layoutOneMode);
                    this.vvOneMode.setSize(new Dimension(this.getWidth()-OptionsPanel.getWidth(), this.getHeight()));
                    graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
                    this.vvOneMode.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.GRAY));
                    this.vvOneMode.setGraphMouse(graphMouse);
                    this.remove(this.graphJPane);
                    this.vvOneMode.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
                    this.graphJPane = new ScrollPane();
                    this.graphJPane.add(this.vvOneMode);
                    this.graphJPane.setPreferredSize(new Dimension(this.getWidth()-OptionsPanel.getWidth(), this.getHeight()));
                    this.add(graphJPane, BorderLayout.CENTER);
                    break;
                case 3:
                    gg = this.currentBidlg.getOneModeEventGraph();
                    this.layoutOneMode = new KKLayout<>(gg);
                    this.vvOneMode = new VisualizationViewer<>(this.layoutOneMode);
                    this.vvOneMode.setSize(new Dimension(this.getWidth()-OptionsPanel.getWidth(), this.getHeight()));
                    graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
                    this.vvOneMode.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.GRAY));
                    this.vvOneMode.setGraphMouse(graphMouse);
                    this.remove(this.graphJPane);
                    this.vvOneMode.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
                    this.graphJPane = new ScrollPane();
                    this.graphJPane.add(this.vvOneMode);
                    this.graphJPane.setPreferredSize(new Dimension(this.getWidth()-OptionsPanel.getWidth(), this.getHeight()));
                    this.add(graphJPane, BorderLayout.CENTER);
                    break;
                case 4:
                    this.remove(this.graphJPane);
                    this.graphJPane = new ScrollPane();
                    
                    this.graphJPane.add(this.kDC.getKnowlageTableAsJTable());
                    this.graphJPane.setPreferredSize(new Dimension(this.getWidth()-OptionsPanel.getWidth(), this.getHeight()));
                    this.add(graphJPane, BorderLayout.CENTER);
                    break;
            }
            validate();
            repaint();
        }
    }//GEN-LAST:event_VisulizerPickerActionPerformed

    private void VisulizerPickerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_VisulizerPickerItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_VisulizerPickerItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu EditMenu;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuBar MainMenu;
    private javax.swing.JPanel OptionsPanel;
    private javax.swing.JComboBox<String> VisulizerPicker;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenuItem export;
    private javax.swing.JMenuItem importcvs;
    private javax.swing.JButton refreshGraphButton;
    // End of variables declaration//GEN-END:variables

}
