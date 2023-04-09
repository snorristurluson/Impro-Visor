/**
 * This Java Class is part of the Impro-Visor Application.
 * <p>
 * Copyright (C) 2016 Robert Keller and Harvey Mudd College.
 * <p>
 * Impro-Visor is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * <p>
 * Impro-Visor is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of merchantability or fitness
 * for a particular purpose. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * Impro-Visor; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package imp.gui;

/**
 *
 * @author Robert Keller
 */

public class ViewAdjustmentDialog extends javax.swing.JDialog {

    private final Notate notate;

    /**
     * Creates new form ViewAdjustmentDialog
     * @param notate
     */
    public ViewAdjustmentDialog(Notate notate, boolean modal) {
        super(notate, modal);
        initComponents();
        this.notate = notate;
    }

    public int getParallax() {
        return Integer.parseInt(parallaxSpinner.getValue().toString());
    }

    public void setTrackerDelay(double value) {
        trackerDelayTextField2.setText("" + value);
    }

    public String getTrackerDelay() {
        return trackerDelayTextField2.getText();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        parallaxSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        trackerDelayTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setTitle("Impro-Visor View Adjustment");
        setBounds(new java.awt.Rectangle(25, 25, 500, 200));
        setLocation(new java.awt.Point(25, 25));
        setMaximumSize(new java.awt.Dimension(500, 200));
        setMinimumSize(new java.awt.Dimension(500, 200));
        setPreferredSize(new java.awt.Dimension(500, 300));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        parallaxSpinner.setToolTipText("Sets the vertical parallax for mouse clicks on staves.");
        parallaxSpinner.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parallax", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N
        parallaxSpinner.setMaximumSize(new java.awt.Dimension(500, 50));
        parallaxSpinner.setMinimumSize(new java.awt.Dimension(200, 50));
        parallaxSpinner.setPreferredSize(new java.awt.Dimension(450, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(parallaxSpinner, gridBagConstraints);

        jLabel1.setText("<html>\nHigher parallax pixels enters note higher than click point.\n<br>\nNegative parallax pixels enters note lower than click point.\n</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        trackerDelayTextField2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        trackerDelayTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        trackerDelayTextField2.setText("0.0");
        trackerDelayTextField2.setToolTipText("Set the delay between the tracker and playback.");
        trackerDelayTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tracker Delay", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N
        trackerDelayTextField2.setInheritsPopupMenu(true);
        trackerDelayTextField2.setMaximumSize(new java.awt.Dimension(500, 50));
        trackerDelayTextField2.setMinimumSize(new java.awt.Dimension(200, 50));
        trackerDelayTextField2.setPreferredSize(new java.awt.Dimension(450, 50));
        trackerDelayTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trackerDelayTextField2ActionPerformed(evt);
            }
        });
        trackerDelayTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                trackerDelayTextField2FocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                trackerDelayTextField2FocusLost(evt);
            }
        });
        trackerDelayTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                trackerDelayTextField2KeyTyped(evt);
            }

            public void keyPressed(java.awt.event.KeyEvent evt) {
                trackerDelayTextField2KeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                trackerDelayTextField2KeyReleased(evt);
            }
        });
        trackerDelayTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                trackerDelayTextField2MousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        getContentPane().add(trackerDelayTextField2, gridBagConstraints);

        jLabel3.setText("<html>Positive tracker delay causes tracker to lag playback by seconds.\n<br>Negative tracker delay causes tracker to lead by specified seconds.\n<br>Decimal fractions of a second are allowed.\n</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void trackerDelayTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trackerDelayTextField2ActionPerformed
        notate.setTrackerDelay(trackerDelayTextField2.getText());
    }//GEN-LAST:event_trackerDelayTextField2ActionPerformed

    private void trackerDelayTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_trackerDelayTextField2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_trackerDelayTextField2FocusGained

    private void trackerDelayTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_trackerDelayTextField2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_trackerDelayTextField2FocusLost

    private void trackerDelayTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_trackerDelayTextField2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_trackerDelayTextField2KeyTyped

    private void trackerDelayTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_trackerDelayTextField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_trackerDelayTextField2KeyPressed

    private void trackerDelayTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_trackerDelayTextField2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_trackerDelayTextField2KeyReleased

    private void trackerDelayTextField2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trackerDelayTextField2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_trackerDelayTextField2MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSpinner parallaxSpinner;
    private javax.swing.JTextField trackerDelayTextField2;
    // End of variables declaration//GEN-END:variables
}