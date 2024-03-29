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
import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javariesoft.global;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author eigher
 */
public class FRekapDistribusi extends javax.swing.JInternalFrame {
Connection c;
java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    /** Creates new form FlapJualPerSales */
    public FRekapDistribusi() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            dateChooserCombo1.setDateFormat(d);
            dateChooserCombo2.setDateFormat(d); 
        } catch (SQLException ex) {
            Logger.getLogger(FRekapDistribusi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        btnKeluar = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FRekapDistribusi.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setToolTipText(resourceMap.getString("Form.toolTipText")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(250, 40, 30, 15);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        getContentPane().add(btnKeluar);
        btnKeluar.setBounds(250, 90, 150, 40);

        btnPreview.setFont(resourceMap.getFont("btnPreview.font")); // NOI18N
        btnPreview.setText(resourceMap.getString("btnPreview.text")); // NOI18N
        btnPreview.setName("btnPreview"); // NOI18N
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });
        getContentPane().add(btnPreview);
        btnPreview.setBounds(40, 90, 150, 40);

        dateChooserCombo1.setFieldFont(resourceMap.getFont("dateChooserCombo1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(dateChooserCombo1);
        dateChooserCombo1.setBounds(90, 40, 150, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 40, 70, 16);

        dateChooserCombo2.setFieldFont(resourceMap.getFont("dateChooserCombo2.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(dateChooserCombo2);
        dateChooserCombo2.setBounds(280, 40, 170, 20);

        setBounds(0, 0, 488, 179);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    HashMap parameter = new HashMap();
    JasperPrint jasperPrint = null;
    parameter.put("Ptgl1",dateChooserCombo1.getText() );
    parameter.put("Ptgl2",dateChooserCombo2.getText() );

    try {
        //jasperPrint = JasperFillManager.fillReport("report\\RekapDistribusi.jasper", parameter, c);
        URL url = new URL(global.REPORT + "/RekapDistribusi.jasper");
        InputStream in = url.openStream();
        jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        JasperViewer.viewReport(jasperPrint, false);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    } catch (Exception ex) {
        System.out.print(ex.toString());
        //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
    }

    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables


}
