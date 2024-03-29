/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LaporanBukuBesarDetailForm.java
 *
 * Created on Jan 3, 2012, 11:18:16 AM
 */
package javariesoft;

import com.erv.db.jurnalDao;
import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import com.erv.function.Util;
import com.erv.model.jurnal;
import java.awt.Cursor;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author erwadi
 */
public class LaporanBukuBesarDetailFormTanggal extends javax.swing.JInternalFrame {

    com.erv.function.Util u = new com.erv.function.Util();
    String akun[], kodeGrup[];
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    Connection c;
    String tipe="";

    /** Creates new form LaporanBukuBesarDetailForm */
    public LaporanBukuBesarDetailFormTanggal() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            jScrollPane1.setSize(500, 150);
            jScrollPane1.setVisible(false);
            tglTrans.setDateFormat(d);
            tglTrans1.setDateFormat(d); 
        } catch (SQLException ex) {
            Logger.getLogger(LaporanBukuBesarDetailFormTanggal.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnOk = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtKodePerkiraan = new javax.swing.JTextField();
        txtNamaPerkiraan = new javax.swing.JTextField();
        tglTrans = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        tglTrans1 = new datechooser.beans.DateChooserCombo();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(LaporanBukuBesarDetailFormTanggal.class);
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
        jScrollPane1.setBounds(130, 40, 60, 30);

        btnOk.setFont(resourceMap.getFont("btnOk.font")); // NOI18N
        btnOk.setIcon(resourceMap.getIcon("btnOk.icon")); // NOI18N
        btnOk.setText(resourceMap.getString("btnOk.text")); // NOI18N
        btnOk.setName("btnOk"); // NOI18N
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        getContentPane().add(btnOk);
        btnOk.setBounds(30, 140, 160, 40);

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
        btnKeluar.setBounds(200, 140, 160, 40);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(300, 50, 30, 16);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 20, 90, 16);

        txtKodePerkiraan.setFont(resourceMap.getFont("txtKodePerkiraan.font")); // NOI18N
        txtKodePerkiraan.setText(resourceMap.getString("txtKodePerkiraan.text")); // NOI18N
        txtKodePerkiraan.setName("txtKodePerkiraan"); // NOI18N
        txtKodePerkiraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodePerkiraanActionPerformed(evt);
            }
        });
        txtKodePerkiraan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodePerkiraanKeyPressed(evt);
            }
        });
        getContentPane().add(txtKodePerkiraan);
        txtKodePerkiraan.setBounds(130, 20, 120, 21);

        txtNamaPerkiraan.setFont(resourceMap.getFont("txtNamaPerkiraan.font")); // NOI18N
        txtNamaPerkiraan.setText(resourceMap.getString("txtNamaPerkiraan.text")); // NOI18N
        txtNamaPerkiraan.setName("txtNamaPerkiraan"); // NOI18N
        getContentPane().add(txtNamaPerkiraan);
        txtNamaPerkiraan.setBounds(260, 20, 280, 20);

        tglTrans.setFieldFont(resourceMap.getFont("tglTrans.dch_combo_fieldFont")); // NOI18N
        tglTrans.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tglTransOnCommit(evt);
            }
        });
        getContentPane().add(tglTrans);
        tglTrans.setBounds(130, 50, 155, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 50, 80, 16);

        tglTrans1.setFieldFont(resourceMap.getFont("tglTrans1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tglTrans1);
        tglTrans1.setBounds(340, 50, 155, 20);

        setBounds(0, 0, 652, 236);
    }// </editor-fold>//GEN-END:initComponents

private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
// TODO add your handling code here:
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    HashMap parameter = new HashMap();
    Util u1 = new Util();
    JasperPrint jasperPrint = null;
    String tgl[] = Util.split(tglTrans.getText(), "-");
    
    parameter.put("bulan", tgl[1]);
    parameter.put("tahun", tgl[0]);
    parameter.put("tanggal", tglTrans.getText());
    parameter.put("tanggal1", tglTrans1.getText());
    if(tipe.equals("SD")){
        parameter.put("akun", txtKodePerkiraan.getText());
    }else{
        parameter.put("akun", (txtKodePerkiraan.getText()+"%"));
    }
    parameter.put("namaakun", txtNamaPerkiraan.getText());
    parameter.put("akunnya", txtKodePerkiraan.getText());
    if(tipe.equals("SD")){
        parameter.put("akun", txtKodePerkiraan.getText());
    }else{
        parameter.put("akun", (txtKodePerkiraan.getText()+"%"));
    }
    parameter.put("namaakun", txtNamaPerkiraan.getText());
    parameter.put("akunnya", txtKodePerkiraan.getText());
    URL url;
    try {
        if(jurnalDao.getJurnalTanggal(koneksi.getKoneksiJ(), txtKodePerkiraan.getText(), tglTrans.getText(), tglTrans1.getText() )){
            url = new URL(global.REPORT+ "/BukuBesarHarian.jasper");
        }else{
            url = new URL(global.REPORT+ "/BukuBesarHariankosong.jasper");
        }
            InputStream in = url.openStream();
        jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        JasperViewer.viewReport(jasperPrint, false);
    } catch (Exception ex) {
        System.out.print(ex.toString());
        //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_btnOkActionPerformed

private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
    dispose();
}//GEN-LAST:event_btnKeluarActionPerformed

private void txtKodePerkiraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePerkiraanActionPerformed
// TODO add your handling code here:
    jScrollPane1.setVisible(true);
    reloadData();
}//GEN-LAST:event_txtKodePerkiraanActionPerformed

private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
// TODO add your handling code here:
    if(evt.getKeyCode()==10){
    txtKodePerkiraan.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
    txtNamaPerkiraan.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
    tipe=jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString();
    jScrollPane1.setVisible(false);
    }
}//GEN-LAST:event_jTable1KeyPressed

private void txtKodePerkiraanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodePerkiraanKeyPressed
// TODO add your handling code here:
    if (evt.getKeyCode() == 40) {
        jScrollPane1.setVisible(true);
        jTable1.requestFocus();
        jTable1.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane1.setVisible(false);
    }
}//GEN-LAST:event_txtKodePerkiraanKeyPressed

    private void tglTransOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tglTransOnCommit
        // TODO add your handling code here:
        Calendar cal = Calendar.getInstance();
        //cal.setTime(Fungsi.dateAdd(c, "DAY", 30, java.sql.Date.valueOf(tanggal.getText())));
        cal.setTime(java.sql.Date.valueOf(tglTrans.getText()));
        tglTrans1.setSelectedDate(cal);
    }//GEN-LAST:event_tglTransOnCommit

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private datechooser.beans.DateChooserCombo tglTrans;
    private datechooser.beans.DateChooserCombo tglTrans1;
    private javax.swing.JTextField txtKodePerkiraan;
    private javax.swing.JTextField txtNamaPerkiraan;
    // End of variables declaration//GEN-END:variables

    void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("Select * from PERKIRAAN where KODEPERKIRAAN like '"+ txtKodePerkiraan.getText() +"%' or lower(NAMAPERKIRAAN) like '%" + txtKodePerkiraan.getText().toLowerCase() + "%'");
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
        } catch (Exception ex) {
            Logger.getLogger(LaporanBukuBesarDetailFormTanggal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    void isiComboGrup() {
//        try {
//            cboGrup.removeAllItems();
//            Statement stat = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            ResultSet rs = stat.executeQuery("Select * from PERKIRAAN where TIPE='D'");
//            int jum = 0;
//            rs.last();
//            if (rs.getRow() > 0) {
//                jum = rs.getRow();
//            }
//            if (jum > 0) {
//                rs.beforeFirst();
//                kodeGrup = new String[jum];
//                jum = 0;
//                while (rs.next()) {
//                    kodeGrup[jum] = rs.getString(1);
//                    cboGrup.addItem(rs.getString(2));
//                    jum++;
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(LaporanBukuBesarDetailForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
}
