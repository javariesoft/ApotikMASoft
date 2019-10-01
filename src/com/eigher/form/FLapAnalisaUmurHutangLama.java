/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FlapJualPerSales.java
 *
 * Created on Jan 29, 2012, 9:06:20 PM
 */
package com.eigher.form;

import javariesoft.*;
import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import java.text.SimpleDateFormat;

/**
 *
 * @author eigher
 */
public class FLapAnalisaUmurHutangLama extends javax.swing.JInternalFrame {

    Connection c;
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form FlapJualPerSales
     */
    public FLapAnalisaUmurHutangLama() {
        initComponents();
        try {
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            c = koneksi.getKoneksiJ();
        } catch (SQLException ex) {
            Logger.getLogger(FLapAnalisaUmurHutangLama.class.getName()).log(Level.SEVERE, null, ex);
        }
        jScrollPane1.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnKeluar = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtnamasupplier = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtkodesupplier = new javax.swing.JTextField();
        tgl1 = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        tgl2 = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        pilihan = new javax.swing.JCheckBox();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FLapAnalisaUmurHutangLama.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(170, 50, 320, 100);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setIcon(resourceMap.getIcon("btnKeluar.icon")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(btnKeluar);
        btnKeluar.setBounds(230, 150, 150, 40);

        btnPreview.setFont(resourceMap.getFont("btnPreview.font")); // NOI18N
        btnPreview.setIcon(resourceMap.getIcon("btnPreview.icon")); // NOI18N
        btnPreview.setText(resourceMap.getString("btnPreview.text")); // NOI18N
        btnPreview.setName("btnPreview"); // NOI18N
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });
        getContentPane().add(btnPreview);
        btnPreview.setBounds(60, 150, 140, 40);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 20, 110, 20);

        txtnamasupplier.setFont(resourceMap.getFont("txtkodesupplier.font")); // NOI18N
        txtnamasupplier.setName("txtnamasupplier"); // NOI18N
        txtnamasupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamasupplierActionPerformed(evt);
            }
        });
        txtnamasupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamasupplierKeyPressed(evt);
            }
        });
        getContentPane().add(txtnamasupplier);
        txtnamasupplier.setBounds(140, 50, 300, 21);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 80, 120, 20);

        txtkodesupplier.setFont(resourceMap.getFont("txtkodesupplier.font")); // NOI18N
        txtkodesupplier.setName("txtkodesupplier"); // NOI18N
        getContentPane().add(txtkodesupplier);
        txtkodesupplier.setBounds(140, 80, 70, 21);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl1);
        tgl1.setBounds(140, 20, 120, 20);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(260, 20, 30, 16);

        tgl2.setFieldFont(resourceMap.getFont("tgl2.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl2);
        tgl2.setBounds(290, 20, 130, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 50, 110, 20);

        pilihan.setFont(resourceMap.getFont("pilihan.font")); // NOI18N
        pilihan.setText(resourceMap.getString("pilihan.text")); // NOI18N
        pilihan.setName("pilihan"); // NOI18N
        getContentPane().add(pilihan);
        pilihan.setBounds(140, 110, 140, 25);

        setBounds(0, 0, 507, 229);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("Ptgl1", tgl1.getText());
        parameter.put("Ptgl2", tgl2.getText());
        parameter.put("Psupplier", txtkodesupplier.getText());
        try {
            //jasperPrint = JasperFillManager.fillReport("report\\LapAnalisaHutang.jasper", parameter, c);
            if (pilihan.isSelected()) {
                URL url = new URL(global.REPORT + "/RekapAnalisaHutangAllSupplier.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            } else {
                URL url = new URL(global.REPORT + "/LapAnalisaHutang.jasper");
                InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter, c);
            }
            JasperViewer.viewReport(jasperPrint, false);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (Exception ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 10) {
        txtkodesupplier.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
        txtnamasupplier.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
        jScrollPane1.setVisible(false);
    }
}//GEN-LAST:event_jTable1KeyPressed

private void txtnamasupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamasupplierKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        jScrollPane1.setVisible(true);
        jTable1.requestFocus();
        jTable1.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane1.setVisible(false);
    }
}//GEN-LAST:event_txtnamasupplierKeyPressed

private void txtnamasupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamasupplierActionPerformed
// TODO add your handling code here:
    jScrollPane1.setVisible(true);
    reloadData();
}//GEN-LAST:event_txtnamasupplierActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JCheckBox pilihan;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtkodesupplier;
    private javax.swing.JTextField txtnamasupplier;
    // End of variables declaration//GEN-END:variables
void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("Select IDSUPPLIER , NAMA  from SUPPLIER where STATUSAKTIF='0' AND (IDSUPPLIER like '" + txtnamasupplier.getText() + "%' or lower(NAMA) like '%" + txtnamasupplier.getText().toLowerCase() + "%')");
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
        } catch (Exception ex) {
            Logger.getLogger(FRekapJualPerPelanggan.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}