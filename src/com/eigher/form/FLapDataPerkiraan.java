/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FPersediaanBrgDagang.java
 *
 * Created on Feb 12, 2012, 10:09:45 PM
 */
package com.eigher.form;

import javariesoft.*;
import com.erv.db.koneksi;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eigher
 */
public class FLapDataPerkiraan extends javax.swing.JInternalFrame {

    Connection c;

    /**
     * Creates new form FPersediaanBrgDagang
     */
    public FLapDataPerkiraan() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            isiCombo();
        } catch (SQLException ex) {
            Logger.getLogger(FLapAnalisaUmurPiutangLama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cboGrup = new javax.swing.JComboBox();
        pilihan = new javax.swing.JCheckBox();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FLapDataPerkiraan.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 30, 50, 15);

        jButton1.setFont(resourceMap.getFont("jButton2.font")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(30, 90, 130, 40);

        jButton2.setFont(resourceMap.getFont("jButton2.font")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(180, 90, 140, 40);

        cboGrup.setFont(resourceMap.getFont("cboGrup.font")); // NOI18N
        cboGrup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboGrup.setName("cboGrup"); // NOI18N
        cboGrup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGrupActionPerformed(evt);
            }
        });
        getContentPane().add(cboGrup);
        cboGrup.setBounds(110, 30, 190, 22);

        pilihan.setFont(resourceMap.getFont("pilihan.font")); // NOI18N
        pilihan.setText(resourceMap.getString("pilihan.text")); // NOI18N
        pilihan.setName("pilihan"); // NOI18N
        getContentPane().add(pilihan);
        pilihan.setBounds(110, 60, 140, 23);

        setBounds(0, 0, 383, 177);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        if (pilihan.isSelected()) {
            parameter.put("Pgrup", "%");
        } else {
            parameter.put("Pgrup", cboGrup.getSelectedIndex() + 1);
        }
        try {
            // jasperPrint = JasperFillManager.fillReport("report\\LapPerkiraan.jasper", parameter, koneksi.getKoneksiJ());
            URL url = new URL(global.REPORT + "/LapPerkiraan.jasper");
            InputStream in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            JasperViewer.viewReport(jasperPrint, false);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (Exception ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

private void cboGrupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGrupActionPerformed
// TODO add your handling code here:

}//GEN-LAST:event_cboGrupActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboGrup;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox pilihan;
    // End of variables declaration//GEN-END:variables
void isiCombo() {
        cboGrup.removeAllItems();
        try {
            Statement stat = c.createStatement();
            ResultSet rs = stat.executeQuery("select * from GRUP");
            while (rs.next()) {
                cboGrup.addItem(rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FormPerkiraan.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}