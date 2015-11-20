/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGUI;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Richard de Mellow
 */
public class BiDynamicLineGraphGUI extends javax.swing.JFrame {
    /**
     * Creates new form DynamicLineGraphGUI
     */
    public BiDynamicLineGraphGUI() {
        initComponents();
         /* Create and display the form */
    }
    
    public void drawWindow(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BiDynamicLineGraphGUI().setVisible(true);
            }
        });
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
        MainMenu = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        importcvs = new javax.swing.JMenuItem();
        export = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        EditMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bi-Dynamic Line Graph Viewer");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("Home Frame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1440, 900));
        setSize(new java.awt.Dimension(1400, 900));

        OptionsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        OptionsPanel.setAlignmentX(0.0F);
        OptionsPanel.setAlignmentY(0.0F);
        OptionsPanel.setMaximumSize(new java.awt.Dimension(200, 32767));
        OptionsPanel.setPreferredSize(new java.awt.Dimension(200, 718));

        refreshGraphButton.setText("Refresh");

        org.jdesktop.layout.GroupLayout OptionsPanelLayout = new org.jdesktop.layout.GroupLayout(OptionsPanel);
        OptionsPanel.setLayout(OptionsPanelLayout);
        OptionsPanelLayout.setHorizontalGroup(
            OptionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(OptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(refreshGraphButton)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        OptionsPanelLayout.setVerticalGroup(
            OptionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(OptionsPanelLayout.createSequentialGroup()
                .add(121, 121, 121)
                .add(refreshGraphButton)
                .addContainerGap(926, Short.MAX_VALUE))
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
        fc.showOpenDialog(MainMenu);
        File newFile = fc.getSelectedFile();
        System.out.println(newFile.getAbsoluteFile());
    }//GEN-LAST:event_importcvsActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu EditMenu;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuBar MainMenu;
    private javax.swing.JPanel OptionsPanel;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenuItem export;
    private javax.swing.JMenuItem importcvs;
    private javax.swing.JButton refreshGraphButton;
    // End of variables declaration//GEN-END:variables

}
