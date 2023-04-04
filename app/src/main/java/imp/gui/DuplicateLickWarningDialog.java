/**
 * This Java Class is part of the Impro-Visor Application
 * <p>
 * Copyright (C) 2005-2017 Robert Keller and Harvey Mudd College
 * <p>
 * Impro-Visor is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * Impro-Visor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * merchantability or fitness for a particular purpose.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Impro-Visor; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package imp.gui;

import static imp.Constants.IGNORE;
import static imp.Constants.SAVE;
import static imp.Constants.OVERWRITE;


/**
 *
 * @author keller
 */
public class DuplicateLickWarningDialog extends javax.swing.JDialog {
    Notate notate;

    /**
     * Creates new form DuplicateLickWarningDialog
     */
    public DuplicateLickWarningDialog(Notate notate, boolean modal) {
        super(notate, modal);
        initComponents();
        this.notate = notate;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        duplicateLickLabel = new javax.swing.JLabel();
        duplicateLickScroll = new javax.swing.JScrollPane();
        duplicateLickText = new javax.swing.JTextPane();
        saveDuplicate = new javax.swing.JButton();
        ignoreDuplicate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        duplicateLickLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        duplicateLickLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        duplicateLickLabel.setText("Warning: Duplicate Lick");
        duplicateLickLabel.setToolTipText("");
        duplicateLickLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 233;
        gridBagConstraints.ipady = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(21, 175, 0, 0);
        getContentPane().add(duplicateLickLabel, gridBagConstraints);

        duplicateLickScroll.setMinimumSize(new java.awt.Dimension(500, 100));
        duplicateLickScroll.setPreferredSize(new java.awt.Dimension(800, 300));

        duplicateLickText.setEditable(false);
        duplicateLickScroll.setViewportView(duplicateLickText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(duplicateLickScroll, gridBagConstraints);

        saveDuplicate.setBackground(java.awt.Color.yellow);
        saveDuplicate.setText("Save This Anyway");
        saveDuplicate.setToolTipText("Saves the lick in the vocabulary");
        saveDuplicate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        saveDuplicate.setDefaultCapable(false);
        saveDuplicate.setOpaque(true);
        saveDuplicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDuplicateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(saveDuplicate, gridBagConstraints);

        ignoreDuplicate.setBackground(java.awt.Color.green);
        ignoreDuplicate.setText("Ignore This One");
        ignoreDuplicate.setToolTipText("Do not save the duplicate in the vocabulary.");
        ignoreDuplicate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ignoreDuplicate.setOpaque(true);
        ignoreDuplicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ignoreDuplicateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(ignoreDuplicate, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ignoreDuplicateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ignoreDuplicateActionPerformed
    {//GEN-HEADEREND:event_ignoreDuplicateActionPerformed
        notate.setIgnoreDuplicateLick(IGNORE);
        setVisible(false);
    }//GEN-LAST:event_ignoreDuplicateActionPerformed

    private void saveDuplicateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveDuplicateActionPerformed
    {//GEN-HEADEREND:event_saveDuplicateActionPerformed
        notate.setIgnoreDuplicateLick(SAVE);
        setVisible(false);
    }//GEN-LAST:event_saveDuplicateActionPerformed

    public void setMessage(String text) {
        duplicateLickText.setText(text);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel duplicateLickLabel;
    private javax.swing.JScrollPane duplicateLickScroll;
    private javax.swing.JTextPane duplicateLickText;
    private javax.swing.JButton ignoreDuplicate;
    private javax.swing.JButton saveDuplicate;
    // End of variables declaration//GEN-END:variables
}
