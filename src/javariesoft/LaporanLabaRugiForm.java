/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LaporanLabaRugiForm.java
 *
 * Created on Jan 11, 2012, 10:26:26 PM
 */
package javariesoft;

import com.erv.db.koneksi;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author erwadi
 */
public class LaporanLabaRugiForm extends javax.swing.JInternalFrame {
    com.erv.function.Util u = new com.erv.function.Util();
    /** Creates new form LaporanLabaRugiForm */
    public LaporanLabaRugiForm() {
        initComponents();
        cboBulan.setSelectedIndex(u.blnsekarang-1);
        txtTahun.setText(u.thnsekarang+"");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cboBulan = new javax.swing.JComboBox();
        txtTahun = new javax.swing.JTextField();
        btnPreview = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(LaporanLabaRugiForm.class);
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
        cboBulan.setBounds(100, 10, 150, 22);

        txtTahun.setFont(resourceMap.getFont("txtTahun.font")); // NOI18N
        txtTahun.setName("txtTahun"); // NOI18N
        getContentPane().add(txtTahun);
        txtTahun.setBounds(260, 10, 80, 22);

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
        btnPreview.setBounds(20, 50, 130, 40);

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
        btnKeluar.setBounds(160, 50, 140, 40);

        setBounds(0, 0, 381, 135);
    }// </editor-fold>//GEN-END:initComponents

private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));    
    HashMap parameter=new HashMap();
        JasperPrint jasperPrint=null;
        parameter.put("bulan", cboBulan.getSelectedIndex()+1);
        parameter.put("tahun", txtTahun.getText());
        parameter.put("Pperiode", cboBulan.getSelectedItem()+" - "+txtTahun.getText());
        try { 
            Connection c=koneksi.getKoneksiJ();
            URL url = new URL(global.REPORT+ "/labarugi.jasper");
            InputStream in = url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter,c);
            JasperViewer.viewReport(jasperPrint,false);
            c.close();
        } catch (Exception ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    
}//GEN-LAST:event_btnPreviewActionPerformed

private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
// TODO add your handling code here:
    dispose();
}//GEN-LAST:event_btnKeluarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cboBulan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtTahun;
    // End of variables declaration//GEN-END:variables
}