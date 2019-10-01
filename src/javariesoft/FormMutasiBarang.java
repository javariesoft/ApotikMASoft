/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FormPenjualan.java
 *
 * Created on Nov 6, 2011, 1:07:44 AM
 */
package javariesoft;

import com.erv.db.koneksi;
import com.erv.function.JDBCAdapter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;
import com.erv.function.Util;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;

/**
 *
 * @author erwadi
 */
public class FormMutasiBarang extends javax.swing.JInternalFrame {

    Connection c = null;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df0 = new DecimalFormat("0.0");
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";

    /** Creates new form FormPenjualan */
    public FormMutasiBarang() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            lh = new loghistory();
            lhdao = new loghistoryDao();
            setLayar();
            setSize(dim.width, dim.height);
            setLocation(0, 0);
            isiCombo();
            tglTransaksi.setDateFormat(d);
            if (JavarieSoftApp.groupuser.equals("Penjualan")) {
                btnBayarPenerimaanPiutang.setVisible(false);
                btnDeletePenjualanAll.setVisible(false);
                btnview.setVisible(false);
            } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
                btnBayarPenerimaanPiutang.setVisible(true);
                btnDeletePenjualanAll.setVisible(true);
                btnview.setVisible(true);
            }
        } catch (Exception e) {
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

        panelCool1 = new com.erv.function.PanelCool();
        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDeletePenjualanAll = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtKriteria = new com.erv.function.TextFieldCool();
        cbokriteria = new javax.swing.JComboBox();
        tglTransaksi = new datechooser.beans.DateChooserCombo();
        btnFilter = new javax.swing.JButton();
        cboStatus = new javax.swing.JComboBox();
        btnview = new javax.swing.JButton();
        btnBayarPenerimaanPiutang = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormMutasiBarang.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setFont(resourceMap.getFont("Form.font")); // NOI18N
        setName("Form"); // NOI18N

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        btnAdd.setFont(resourceMap.getFont("jButton2.font")); // NOI18N
        btnAdd.setIcon(resourceMap.getIcon("btnAdd.icon")); // NOI18N
        btnAdd.setText(resourceMap.getString("btnAdd.text")); // NOI18N
        btnAdd.setName("btnAdd"); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd);
        btnAdd.setBounds(10, 10, 100, 25);

        btnDeletePenjualanAll.setFont(resourceMap.getFont("btnDeletePenjualanAll.font")); // NOI18N
        btnDeletePenjualanAll.setIcon(resourceMap.getIcon("btnDeletePenjualanAll.icon")); // NOI18N
        btnDeletePenjualanAll.setText(resourceMap.getString("btnDeletePenjualanAll.text")); // NOI18N
        btnDeletePenjualanAll.setName("btnDeletePenjualanAll"); // NOI18N
        btnDeletePenjualanAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePenjualanAllActionPerformed(evt);
            }
        });
        jPanel1.add(btnDeletePenjualanAll);
        btnDeletePenjualanAll.setBounds(360, 10, 100, 25);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(470, 10, 70, 15);

        txtKriteria.setText(resourceMap.getString("txtKriteria.text")); // NOI18N
        txtKriteria.setFont(resourceMap.getFont("txtKriteria.font")); // NOI18N
        txtKriteria.setName("txtKriteria"); // NOI18N
        jPanel1.add(txtKriteria);
        txtKriteria.setBounds(700, 10, 210, 24);

        cbokriteria.setFont(resourceMap.getFont("cbokriteria.font")); // NOI18N
        cbokriteria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbokriteria.setName("cbokriteria"); // NOI18N
        cbokriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbokriteriaActionPerformed(evt);
            }
        });
        jPanel1.add(cbokriteria);
        cbokriteria.setBounds(540, 10, 140, 21);

        tglTransaksi.setFieldFont(resourceMap.getFont("tglTransaksi.dch_combo_fieldFont")); // NOI18N
        jPanel1.add(tglTransaksi);
        tglTransaksi.setBounds(720, 10, 180, 20);

        btnFilter.setFont(resourceMap.getFont("btnFilter.font")); // NOI18N
        btnFilter.setIcon(resourceMap.getIcon("btnFilter.icon")); // NOI18N
        btnFilter.setText(resourceMap.getString("btnFilter.text")); // NOI18N
        btnFilter.setName("btnFilter"); // NOI18N
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        jPanel1.add(btnFilter);
        btnFilter.setBounds(920, 10, 90, 25);

        cboStatus.setFont(resourceMap.getFont("cboStatus.font")); // NOI18N
        cboStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboStatus.setName("cboStatus"); // NOI18N
        jPanel1.add(cboStatus);
        cboStatus.setBounds(710, 10, 180, 21);

        btnview.setFont(resourceMap.getFont("btnview.font")); // NOI18N
        btnview.setIcon(resourceMap.getIcon("btnview.icon")); // NOI18N
        btnview.setText(resourceMap.getString("btnview.text")); // NOI18N
        btnview.setName("btnview"); // NOI18N
        btnview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnviewActionPerformed(evt);
            }
        });
        jPanel1.add(btnview);
        btnview.setBounds(120, 10, 100, 25);

        btnBayarPenerimaanPiutang.setFont(resourceMap.getFont("btnBayarPenerimaanPiutang.font")); // NOI18N
        btnBayarPenerimaanPiutang.setIcon(resourceMap.getIcon("btnBayarPenerimaanPiutang.icon")); // NOI18N
        btnBayarPenerimaanPiutang.setText(resourceMap.getString("btnBayarPenerimaanPiutang.text")); // NOI18N
        btnBayarPenerimaanPiutang.setName("btnBayarPenerimaanPiutang"); // NOI18N
        btnBayarPenerimaanPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarPenerimaanPiutangActionPerformed(evt);
            }
        });
        jPanel1.add(btnBayarPenerimaanPiutang);
        btnBayarPenerimaanPiutang.setBounds(230, 10, 123, 25);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setIcon(resourceMap.getIcon("btnKeluar.icon")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        jPanel1.add(btnKeluar);
        btnKeluar.setBounds(1030, 10, 99, 25);

        panelCool1.add(jPanel1);
        jPanel1.setBounds(10, 10, 1140, 40);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setFont(resourceMap.getFont("jTable1.font")); // NOI18N
        jTable1.setName("jTable1"); // NOI18N
        jTable1.setRowHeight(20);
        jScrollPane1.setViewportView(jTable1);

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 60, 870, 402);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 1175, 503);
    }// </editor-fold>//GEN-END:initComponents

private void cbokriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbokriteriaActionPerformed
// TODO add your handling code here:
    pilihKriteria(cbokriteria.getSelectedIndex());
}//GEN-LAST:event_cbokriteriaActionPerformed

private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
// TODO add your handling code here:
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    reloadData();
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_btnFilterActionPerformed

private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
    DialogMutasiBarang dp = new DialogMutasiBarang(null, false);
    dp.setVisible(true);
}//GEN-LAST:event_btnAddActionPerformed

private void btnviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnviewActionPerformed
// TODO add your handling code here:
    try {
        DialogMutasiBarang dpen = new DialogMutasiBarang(null, false, Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()), Double.parseDouble(jTable1.getValueAt(jTable1.getSelectedRow(), 8).toString()));
        dpen.setVisible(true);
    } catch (Exception e) {
    }
}//GEN-LAST:event_btnviewActionPerformed

private void btnDeletePenjualanAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePenjualanAllActionPerformed
// TODO add your handling code here:
    int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Dihapus?", "", JOptionPane.YES_NO_OPTION);
    if (x == 0) {
        Statement sf = null;
        try {
            String tgal[] = Util.split(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString(), "-");
            String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
            if (cekperiodeAda(per)) {
                if (cekperiode(per)) {
                    aksilog = "Delete";
                    sf = koneksi.getKoneksiJ().createStatement();
                    sf.execute("delete from PENJUALAN where ID=" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "");
                    String sql = "SELECT ID from PIUTANG where IDPENJUALAN ='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "'";
                    ResultSet rs = sf.executeQuery(sql);
                    if (rs.next()) {
                        if (rs.getInt(1) != 0) {
                            sf.executeUpdate("delete from PIUTANGBAYAR where IDPIUTANG='" + rs.getInt(1) + "'");
                        }
                    }
                    //sf.execute("delete from STOK where IDPENJUALAN="+ jTable1.getValueAt(jTable1.getSelectedRow(), 0) +" AND KODETRANS='J'");
                    prosesUpdateLog();
                    JOptionPane.showMessageDialog(this, "Delete Ok");
                    sf.close();
                    reloadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                    btnDeletePenjualanAll.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                btnDeletePenjualanAll.requestFocus();
            }
        } catch (Exception ex) {
            Logger.getLogger(FormMutasiBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
        //sf.execute("delete from RINCIPENJUALAN where IDPENJUALAN=" + IDjual + "");
          
    } else {
        System.out.print("tidak");
    }
}//GEN-LAST:event_btnDeletePenjualanAllActionPerformed

private void btnBayarPenerimaanPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarPenerimaanPiutangActionPerformed
// TODO add your handling code here:
    DialogPiutang p = new DialogPiutang(null, true, jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
    p.setVisible(true);
}//GEN-LAST:event_btnBayarPenerimaanPiutangActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    public static javax.swing.JButton btnBayarPenerimaanPiutang;
    public static javax.swing.JButton btnDeletePenjualanAll;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnKeluar;
    public static javax.swing.JButton btnview;
    private javax.swing.JComboBox cboStatus;
    private javax.swing.JComboBox cbokriteria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private com.erv.function.PanelCool panelCool1;
    private datechooser.beans.DateChooserCombo tglTransaksi;
    private com.erv.function.TextFieldCool txtKriteria;
    // End of variables declaration//GEN-END:variables

    private void setLayar() {
        jPanel1.setSize(dim.width - 40, 40);
        jScrollPane1.setLocation(jPanel1.getX(), jPanel1.getY() + jPanel1.getHeight() + 10);
        jScrollPane1.setSize(dim.width - 40, dim.height - 200);
    }

    private void isiCombo() {
        cbokriteria.removeAllItems();
        cboStatus.removeAllItems();
        cbokriteria.addItem("TANGGAL");
        cbokriteria.addItem("PELANGGAN");
        cbokriteria.addItem("STATUS");
        cbokriteria.addItem("NOFAKTUR");
        cboStatus.addItem("LUNAS");
        cboStatus.addItem("HUTANG");
    }

    void pilihKriteria(int pil) {
        if (pil == 0) {
            tglTransaksi.setVisible(true);
            txtKriteria.setVisible(false);
            cboStatus.setVisible(false);
        } else if (pil == 1) {
            tglTransaksi.setVisible(false);
            txtKriteria.setVisible(true);
            cboStatus.setVisible(false);
        } else if (pil == 2) {
            tglTransaksi.setVisible(false);
            txtKriteria.setVisible(false);
            cboStatus.setVisible(true);
        }else if (pil == 3) {
            tglTransaksi.setVisible(false);
            txtKriteria.setVisible(true);
            cboStatus.setVisible(false);
        } 

    }

    void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(koneksi.getKoneksiJ());
            String sql = "select p.ID,p.FAKTUR,p.TANGGAL,pl.NAMA, "
                    + "ifnull((SELECT case PIUTANG.STATUS when '0' then 'LUNAS' when '1' then 'BELUM LUNAS' end FROM PIUTANG WHERE PIUTANG.IDPENJUALAN = p.ID),0) as STATUS ,"
                    + "p.TGLLUNAS,ifnull((SELECT CONVERT(PIUTANG.JUMLAH,LONG) FROM PIUTANG WHERE PIUTANG.IDPENJUALAN = p.ID),0) as HUTANG , "
                    + "ifnull((select sum(CONVERT(PIUTANGBAYAR.JUMLAH,LONG)) from PIUTANGBAYAR "
                    + "inner join PIUTANG on PIUTANGBAYAR.IDPIUTANG=PIUTANG.ID "
                    + "where PIUTANG.IDPENJUALAN=p.ID),0) as BAYAR, "
                    + "ifnull((SELECT CONVERT(PIUTANG.JUMLAH,LONG) FROM PIUTANG WHERE PIUTANG.IDPENJUALAN = p.ID),0) - "
                    + "ifnull((select sum(CONVERT(PIUTANGBAYAR.JUMLAH,LONG)) from PIUTANGBAYAR "
                    + "inner join PIUTANG on PIUTANGBAYAR.IDPIUTANG=PIUTANG.ID "
                    + "where PIUTANG.IDPENJUALAN=p.ID),0) as SISAHUTANG "
                    + " from PENJUALAN p,PELANGGAN pl "
                    + "where p.KODEPELANGGAN=pl.KODEPELANGGAN and pl.STATUSCABANG='1' ";
            if (cbokriteria.getSelectedIndex() == 0) {
                sql += " and p.TANGGAL='" + tglTransaksi.getText() + "'";
            }
            if (cbokriteria.getSelectedIndex() == 1) {
                sql += " and lower(pl.NAMA) like '%" + txtKriteria.getText().toLowerCase() + "%'";
            }
            if (cbokriteria.getSelectedIndex() == 2) {
                if (cboStatus.getSelectedIndex() == 0) {
                    sql += " and p.STATUS ='0'";
                } else if (cboStatus.getSelectedIndex() == 1) {
                    sql += " and p.STATUS ='1'";
                }
            }
            if (cbokriteria.getSelectedIndex() == 3) {
                sql += " and p.FAKTUR like '%" + txtKriteria.getText().toLowerCase() + "%'";
            }
            j.executeQuery(sql);
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            TableColumn col = jTable1.getColumnModel().getColumn(0);
            col.setPreferredWidth(50);
            col = jTable1.getColumnModel().getColumn(1);
            col.setPreferredWidth(100);
            col = jTable1.getColumnModel().getColumn(2);
            col.setPreferredWidth(100);
            col = jTable1.getColumnModel().getColumn(3);
            col.setPreferredWidth(200);
            col = jTable1.getColumnModel().getColumn(4);
            col.setPreferredWidth(100);
            jTable1.setRowHeight(20);
            jTable1.setFont(new Font("Tahoma", Font.BOLD, 14));
            jScrollPane1.getViewport().add(jTable1);
            jScrollPane1.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 

    }

//    void settingtombol(boolean simp, boolean edit, boolean hapus) {
//        cmdInsert.setEnabled(simp);
//        cmdUpdate.setEnabled(edit);
//        cmdDelete.setEnabled(hapus);
//    }
    void prosesUpdateLog() {

        //java.sql.Date tanggallog;
        String tanggallog;
        String jamlog = u.jamsekarang + ":" + u.menitsekarang + ":" + u.detiksekarang;
        //tanggallog = java.sql.Date.valueOf(u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang);
        tanggallog = u.thnsekarang + "-" + u.blnsekarang + "-" + u.tglsekarang;
        try {
            String ketlog = "";
            lh.setUSERIDENTITY(JavarieSoftApp.jenisuser);
            lh.setTANGGAL(tanggallog);
            lh.setJAM(jamlog);
            lh.setTABEL("TPENJUALAN");
            lh.setNOREFF(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
            lh.setAKSI(aksilog);
            if (aksilog.equals("Insert")) {
                ketlog = "Insert Data Penjualan " + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            } else if (aksilog.equals("Update")) {
                ketlog = "Update Data Penjualan " + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            } else if (aksilog.equals("Delete")) {
                ketlog = "Delete Data Penjualan No Faktur " + jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString() + " (" + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() + ") Pelanggan " + jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString();
            }
            lh.setKETERANGAN(ketlog);
            lhdao.insert(c, lh);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }

    public boolean cekperiode(String periode) throws SQLException {
        //String periode = thn + "." + bln;
        boolean hasil1 = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + periode + "' and STATUSSTOK='1'");

        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil1 = true;
            }
        }
        rs.close();
        s.close();
        return hasil1;
    }

    public boolean cekperiodeAda(String bul) throws SQLException {
        //String periode = thn + "." + bln;
        boolean ada = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + bul + "'");

        if (rs.next()) {
            if (rs.getString(1) != null) {
                ada = true;
            }
        }
        rs.close();
        s.close();
        return ada;
    }
}
