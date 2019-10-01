/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LaporanNeracaForm.java
 *
 * Created on Jan 4, 2012, 2:08:54 PM
 */
package javariesoft;

import com.erv.db.koneksi;
import java.awt.Cursor;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author erwadi
 */
public class LaporanNeracaForm extends javax.swing.JInternalFrame {

    com.erv.function.Util u = new com.erv.function.Util();
    Connection c = null;

    /**
     * Creates new form LaporanNeracaForm
     */
    public LaporanNeracaForm() {
        initComponents();
        cboBulan.setSelectedIndex(u.blnsekarang - 1);
        txtTahun.setText(u.thnsekarang + "");
        try {
            c = koneksi.getKoneksiJ();
        } catch (SQLException ex) {
            Logger.getLogger(LaporanNeracaForm.class.getName()).log(Level.SEVERE, null, ex);
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
        cboBulan = new javax.swing.JComboBox();
        txtTahun = new javax.swing.JTextField();
        btnPreview = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        pilihan = new javax.swing.JCheckBox();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(LaporanNeracaForm.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(27, 11, 80, 16);

        cboBulan.setFont(resourceMap.getFont("cboBulan.font")); // NOI18N
        cboBulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cboBulan.setName("cboBulan"); // NOI18N
        getContentPane().add(cboBulan);
        cboBulan.setBounds(100, 10, 150, 21);

        txtTahun.setFont(resourceMap.getFont("txtTahun.font")); // NOI18N
        txtTahun.setText(resourceMap.getString("txtTahun.text")); // NOI18N
        txtTahun.setName("txtTahun"); // NOI18N
        getContentPane().add(txtTahun);
        txtTahun.setBounds(260, 10, 80, 21);

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
        btnPreview.setBounds(30, 70, 130, 40);

        btnExit.setFont(resourceMap.getFont("btnExit.font")); // NOI18N
        btnExit.setIcon(resourceMap.getIcon("btnExit.icon")); // NOI18N
        btnExit.setText(resourceMap.getString("btnExit.text")); // NOI18N
        btnExit.setName("btnExit"); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit);
        btnExit.setBounds(180, 70, 140, 40);

        pilihan.setFont(resourceMap.getFont("pilihan.font")); // NOI18N
        pilihan.setText(resourceMap.getString("pilihan.text")); // NOI18N
        pilihan.setName("pilihan"); // NOI18N
        getContentPane().add(pilihan);
        pilihan.setBounds(100, 40, 120, 29);

        setBounds(0, 0, 377, 156);
    }// </editor-fold>//GEN-END:initComponents

private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    HashMap parameter = new HashMap();
    JasperPrint jasperPrint = null;
    parameter.put("bulan", cboBulan.getSelectedIndex() + 1);
    parameter.put("tahun", txtTahun.getText());
    parameter.put("Pperiode", cboBulan.getSelectedItem() + " - " + txtTahun.getText());
    try {
        URL url;
        InputStream in;
        if (pilihan.isSelected()) {
            url = new URL(global.REPORT + "/neracaMutasi.jasper");
            in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        } else {
            url = new URL(global.REPORT + "/neraca.jasper");
            in = url.openStream();
            jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        }
        JasperViewer.viewReport(jasperPrint, false);
        generateReport();
    } catch (Exception ex) {
        System.out.print(ex.toString());
        //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_btnPreviewActionPerformed

private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
    dispose();
}//GEN-LAST:event_btnExitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cboBulan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox pilihan;
    private javax.swing.JTextField txtTahun;
    // End of variables declaration//GEN-END:variables

    public  void generateReport() throws JRException, MalformedURLException, IOException {
        HashMap parameter = new HashMap();
        JasperPrint jasperPrint = null;
        parameter.put("bulan", cboBulan.getSelectedIndex() + 1);
        parameter.put("tahun", txtTahun.getText());
        parameter.put("Pperiode", cboBulan.getSelectedItem() + " - " + txtTahun.getText());
        URL url;
        InputStream in;
        url = new URL(global.REPORT + "/neraca.jasper");
        in = url.openStream();
        jasperPrint = JasperFillManager.fillReport(in, parameter, c);

        JRTextExporter exporter = new JRTextExporter();

        exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 120);
        exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "c:/temp/coba.txt");
        exporter.exportReport();
    }
}