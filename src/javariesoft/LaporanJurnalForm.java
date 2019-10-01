/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LaporanJurnalForm.java
 *
 * Created on Jan 12, 2012, 6:11:07 AM
 */
package javariesoft;

import com.erv.db.koneksi;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author erwadi
 */
public class LaporanJurnalForm extends javax.swing.JInternalFrame {
 com.erv.function.Util u = new com.erv.function.Util();
 java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    /** Creates new form LaporanJurnalForm */
    public LaporanJurnalForm() {
        initComponents();
        cboBulan.setSelectedIndex(u.blnsekarang-1);
        txtTahun.setText(u.thnsekarang+"");
        tgl.setDateFormat(d); 
        pilihKriteria(cboPil.getSelectedIndex());
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        JLabelTanggal = new javax.swing.JLabel();
        cboPil = new javax.swing.JComboBox();
        JLabelPeriode = new javax.swing.JLabel();
        tgl = new datechooser.beans.DateChooserCombo();
        JLabelKodeJurnal = new javax.swing.JLabel();
        txtKodeJurnal = new javax.swing.JTextField();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(LaporanJurnalForm.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        getContentPane().setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 10, 80, 16);

        cboBulan.setFont(resourceMap.getFont("cboBulan.font")); // NOI18N
        cboBulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cboBulan.setName("cboBulan"); // NOI18N
        getContentPane().add(cboBulan);
        cboBulan.setBounds(120, 40, 150, 22);

        txtTahun.setFont(resourceMap.getFont("txtTahun.font")); // NOI18N
        txtTahun.setName("txtTahun"); // NOI18N
        getContentPane().add(txtTahun);
        txtTahun.setBounds(280, 40, 80, 22);

        jButton1.setFont(resourceMap.getFont("jButton1.font")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(30, 100, 130, 40);

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
        jButton2.setBounds(170, 100, 130, 40);

        JLabelTanggal.setFont(resourceMap.getFont("JLabelTanggal.font")); // NOI18N
        JLabelTanggal.setText(resourceMap.getString("JLabelTanggal.text")); // NOI18N
        JLabelTanggal.setName("JLabelTanggal"); // NOI18N
        getContentPane().add(JLabelTanggal);
        JLabelTanggal.setBounds(30, 50, 80, 16);

        cboPil.setFont(resourceMap.getFont("cboPil.font")); // NOI18N
        cboPil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bulan", "Tanggal", "Kode Jurnal" }));
        cboPil.setName("cboPil"); // NOI18N
        cboPil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPilActionPerformed(evt);
            }
        });
        getContentPane().add(cboPil);
        cboPil.setBounds(120, 10, 150, 21);

        JLabelPeriode.setFont(resourceMap.getFont("JLabelPeriode.font")); // NOI18N
        JLabelPeriode.setText(resourceMap.getString("JLabelPeriode.text")); // NOI18N
        JLabelPeriode.setName("JLabelPeriode"); // NOI18N
        getContentPane().add(JLabelPeriode);
        JLabelPeriode.setBounds(30, 40, 80, 16);

        tgl.setFieldFont(resourceMap.getFont("tgl.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl);
        tgl.setBounds(120, 50, 150, 20);

        JLabelKodeJurnal.setFont(resourceMap.getFont("JLabelKodeJurnal.font")); // NOI18N
        JLabelKodeJurnal.setText(resourceMap.getString("JLabelKodeJurnal.text")); // NOI18N
        JLabelKodeJurnal.setName("JLabelKodeJurnal"); // NOI18N
        getContentPane().add(JLabelKodeJurnal);
        JLabelKodeJurnal.setBounds(30, 70, 80, 16);

        txtKodeJurnal.setFont(resourceMap.getFont("txtKodeJurnal.font")); // NOI18N
        txtKodeJurnal.setName("txtKodeJurnal"); // NOI18N
        getContentPane().add(txtKodeJurnal);
        txtKodeJurnal.setBounds(120, 70, 180, 22);

        setBounds(0, 0, 389, 195);
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        HashMap parameter=new HashMap();
        JasperPrint jasperPrint=null;
        Connection c;
        try { 
            URL url;
            InputStream in;
            c=koneksi.getKoneksiJ();
            if(cboPil.getSelectedIndex()==0){
                parameter.put("BULAN", cboBulan.getSelectedIndex()+1);
                parameter.put("TAHUN", txtTahun.getText());
                url= new URL(global.REPORT+ "/jurnal.jasper");
                in= url.openStream();
                jasperPrint = JasperFillManager.fillReport(in, parameter,c);
            }else if(cboPil.getSelectedIndex()==1){
                url= new URL(global.REPORT+ "/jurnalhari.jasper");
                in= url.openStream();
                parameter.put("tanggal", tgl.getText());
                jasperPrint = JasperFillManager.fillReport(in, parameter,c);
            }else if(cboPil.getSelectedIndex()==2){
                url= new URL(global.REPORT+ "/jurnalPerKode.jasper");
                in= url.openStream();
                parameter.put("PkodeJurnal", txtKodeJurnal.getText());
                jasperPrint = JasperFillManager.fillReport(in, parameter,c);
            }
            JasperViewer.viewReport(jasperPrint,false);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            c.close();
        } catch (Exception ex) {
            System.out.print(ex.toString());
            //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
    dispose();
}//GEN-LAST:event_jButton2ActionPerformed

    private void cboPilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPilActionPerformed
        // TODO add your handling code here:
        pilihKriteria(cboPil.getSelectedIndex());
    }//GEN-LAST:event_cboPilActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabelKodeJurnal;
    private javax.swing.JLabel JLabelPeriode;
    private javax.swing.JLabel JLabelTanggal;
    private javax.swing.JComboBox cboBulan;
    private javax.swing.JComboBox cboPil;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private datechooser.beans.DateChooserCombo tgl;
    private javax.swing.JTextField txtKodeJurnal;
    private javax.swing.JTextField txtTahun;
    // End of variables declaration//GEN-END:variables

void pilihKriteria(int pil) {
        if (pil == 0) {
            cboBulan.setVisible(true);
            txtTahun.setVisible(true);
            JLabelPeriode.setVisible(true);
            tgl.setVisible(false);
            JLabelTanggal.setVisible(false);
            txtKodeJurnal.setVisible(false);
            JLabelKodeJurnal.setVisible(false);
        } else if (pil == 1) {
            cboBulan.setVisible(false);
            txtTahun.setVisible(false);
            JLabelPeriode.setVisible(false);
            tgl.setVisible(true);
            JLabelTanggal.setVisible(true);
            txtKodeJurnal.setVisible(false);
            JLabelKodeJurnal.setVisible(false);
        } else if (pil == 2) {
            cboBulan.setVisible(false);
            txtTahun.setVisible(false);
            JLabelPeriode.setVisible(false);
            tgl.setVisible(false);
            JLabelTanggal.setVisible(false);
            txtKodeJurnal.setVisible(true);
            JLabelKodeJurnal.setVisible(true);
        } 

    }

}
