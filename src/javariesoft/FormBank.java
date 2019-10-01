/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FormBank.java
 *
 * Created on Dec 26, 2011, 9:16:08 AM
 */
package javariesoft;

import com.erv.db.bankDao;
import com.erv.db.koneksi;
import com.erv.db.perkiraanDao;
import com.erv.function.JDBCAdapter;
import com.erv.model.bank;
import com.erv.model.perkiraan;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.eigher.db.loghistoryDao;
import com.eigher.model.loghistory;

/**
 *
 * @author erwadi
 */
public class FormBank extends javax.swing.JInternalFrame {

    bank b;
    perkiraan pr;
    perkiraanDao dbpr;
    Connection c;
    loghistory lh;
    loghistoryDao lhdao;
    com.erv.function.Util u = new com.erv.function.Util();
    String aksilog = "";

    /**
     * Creates new form FormBank
     */
    public FormBank() {
        initComponents();
        try {
            c = koneksi.getKoneksiJ();
            c.createStatement().execute("set autocommit true");
            b = new bank();
            pr = new perkiraan();
            dbpr = new perkiraanDao();
            lh = new loghistory();
            lhdao = new loghistoryDao();
            cektombol();
            reloadData();
            kosongkan();
        } catch (SQLException ex) {
            Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
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

        panelCool1 = new com.erv.function.PanelCool();
        jLabel1 = new javax.swing.JLabel();
        txtidbank = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnamaBank = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtkodeperkiraan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtnamapenabung = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtnorekening = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnInsert = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnCancel = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnKeluar = new javax.swing.JButton();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormBank.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panelCool1.setName("panelCool1"); // NOI18N
        panelCool1.setLayout(null);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        panelCool1.add(jLabel1);
        jLabel1.setBounds(20, 140, 110, 20);

        txtidbank.setEditable(false);
        txtidbank.setFont(resourceMap.getFont("txtidbank.font")); // NOI18N
        txtidbank.setText(resourceMap.getString("txtidbank.text")); // NOI18N
        txtidbank.setName("txtidbank"); // NOI18N
        panelCool1.add(txtidbank);
        txtidbank.setBounds(170, 50, 210, 21);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        panelCool1.add(jLabel2);
        jLabel2.setBounds(20, 50, 110, 20);

        txtnamaBank.setFont(resourceMap.getFont("txtnamaBank.font")); // NOI18N
        txtnamaBank.setText(resourceMap.getString("txtnamaBank.text")); // NOI18N
        txtnamaBank.setName("txtnamaBank"); // NOI18N
        txtnamaBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaBankActionPerformed(evt);
            }
        });
        txtnamaBank.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtnamaBankFocusLost(evt);
            }
        });
        panelCool1.add(txtnamaBank);
        txtnamaBank.setBounds(170, 20, 340, 21);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        panelCool1.add(jLabel3);
        jLabel3.setBounds(20, 20, 90, 20);

        txtkodeperkiraan.setEditable(false);
        txtkodeperkiraan.setFont(resourceMap.getFont("txtkodeperkiraan.font")); // NOI18N
        txtkodeperkiraan.setText(resourceMap.getString("txtkodeperkiraan.text")); // NOI18N
        txtkodeperkiraan.setName("txtkodeperkiraan"); // NOI18N
        panelCool1.add(txtkodeperkiraan);
        txtkodeperkiraan.setBounds(170, 80, 210, 21);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        panelCool1.add(jLabel4);
        jLabel4.setBounds(20, 80, 110, 20);

        txtnamapenabung.setFont(resourceMap.getFont("txtnamapenabung.font")); // NOI18N
        txtnamapenabung.setText(resourceMap.getString("txtnamapenabung.text")); // NOI18N
        txtnamapenabung.setName("txtnamapenabung"); // NOI18N
        txtnamapenabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamapenabungActionPerformed(evt);
            }
        });
        txtnamapenabung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamapenabungKeyPressed(evt);
            }
        });
        panelCool1.add(txtnamapenabung);
        txtnamapenabung.setBounds(170, 110, 340, 21);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel5.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        panelCool1.add(jLabel5);
        jLabel5.setBounds(20, 110, 110, 20);

        txtnorekening.setFont(resourceMap.getFont("txtnorekening.font")); // NOI18N
        txtnorekening.setText(resourceMap.getString("txtnorekening.text")); // NOI18N
        txtnorekening.setName("txtnorekening"); // NOI18N
        txtnorekening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnorekeningActionPerformed(evt);
            }
        });
        panelCool1.add(txtnorekening);
        txtnorekening.setBounds(170, 140, 340, 21);

        jSeparator1.setName("jSeparator1"); // NOI18N
        panelCool1.add(jSeparator1);
        jSeparator1.setBounds(10, 210, 510, 10);

        btnInsert.setFont(resourceMap.getFont("btnInsert.font")); // NOI18N
        btnInsert.setIcon(resourceMap.getIcon("btnInsert.icon")); // NOI18N
        btnInsert.setText(resourceMap.getString("btnInsert.text")); // NOI18N
        btnInsert.setName("btnInsert"); // NOI18N
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        panelCool1.add(btnInsert);
        btnInsert.setBounds(20, 180, 90, 25);

        btnEdit.setFont(resourceMap.getFont("btnEdit.font")); // NOI18N
        btnEdit.setIcon(resourceMap.getIcon("btnEdit.icon")); // NOI18N
        btnEdit.setText(resourceMap.getString("btnEdit.text")); // NOI18N
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelCool1.add(btnEdit);
        btnEdit.setBounds(110, 180, 90, 25);

        btnDelete.setFont(resourceMap.getFont("btnDelete.font")); // NOI18N
        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        panelCool1.add(btnDelete);
        btnDelete.setBounds(200, 180, 100, 25);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setFont(resourceMap.getFont("jTable1.font")); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panelCool1.add(jScrollPane1);
        jScrollPane1.setBounds(20, 220, 500, 180);

        btnCancel.setFont(resourceMap.getFont("btnCancel.font")); // NOI18N
        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        panelCool1.add(btnCancel);
        btnCancel.setBounds(320, 180, 100, 25);

        jSeparator2.setName("jSeparator2"); // NOI18N
        panelCool1.add(jSeparator2);
        jSeparator2.setBounds(10, 170, 510, 10);

        btnKeluar.setFont(resourceMap.getFont("btnKeluar.font")); // NOI18N
        btnKeluar.setIcon(resourceMap.getIcon("btnKeluar.icon")); // NOI18N
        btnKeluar.setText(resourceMap.getString("btnKeluar.text")); // NOI18N
        btnKeluar.setName("btnKeluar"); // NOI18N
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        panelCool1.add(btnKeluar);
        btnKeluar.setBounds(420, 180, 100, 25);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 552, 448);
    }// </editor-fold>//GEN-END:initComponents

private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
// TODO add your handling code here:
    int x = JOptionPane.showConfirmDialog(this, "Apakah Data Akan Disimpan?", "", JOptionPane.YES_NO_OPTION);
    if (x == 0) {
        if ((txtnamaBank.getText().equals("")) || (txtidbank.getText().equals("")) || (txtkodeperkiraan.getText().equals("")) || (txtnamapenabung.getText().equals("")) || (txtnorekening.getText().equals(""))) {
            JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
            txtnamaBank.requestFocus();
        } else {
            aksilog = "Insert";
            prosesUpdate(0);
            prosesUpdateAkun(0);
            prosesUpdateLog();
            reloadData();
            cektombol();
            kosongkan();
            txtnamaBank.requestFocus();
        }
    } else {
        txtnamaBank.requestFocus();
    }
}//GEN-LAST:event_btnInsertActionPerformed

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    try {
        // TODO add your handling code here:
        b = bankDao.getDetails(c, Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
        txtnamaBank.setText(b.getNAMABANK());
        txtidbank.setText("" + b.getIDBANK());
        txtkodeperkiraan.setText(b.getKODEAKUN());
        txtnamapenabung.setText(b.getNAMAPENABUNG());
        txtnorekening.setText(b.getNOREK());
        cektombol();
    } catch (SQLException ex) {
        Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jTable1MouseClicked

private void txtnamaBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaBankActionPerformed
    try {
        txtidbank.setText("" + bankDao.getID(c));
        txtkodeperkiraan.setText(bankDao.getAkun(c));
        txtnamaBank.setText(txtnamaBank.getText().toUpperCase());
        txtnamapenabung.requestFocus();
    } catch (SQLException ex) {
        Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_txtnamaBankActionPerformed

private void txtnamapenabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamapenabungActionPerformed
// TODO add your handling code here:
    txtnamapenabung.setText(txtnamapenabung.getText().toUpperCase());
    txtnorekening.requestFocus();

}//GEN-LAST:event_txtnamapenabungActionPerformed

private void txtnorekeningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnorekeningActionPerformed
// TODO add your handling code here:
    btnInsert.requestFocus();
}//GEN-LAST:event_txtnorekeningActionPerformed

private void txtnamapenabungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamapenabungKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_txtnamapenabungKeyPressed

private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
// TODO add your handling code here:
    int x = JOptionPane.showConfirmDialog(this, "Apakah Data Di Edit?", "", JOptionPane.YES_NO_OPTION);
    if (x == 0) {
        if ((txtnamaBank.getText().equals("")) || (txtidbank.getText().equals("")) || (txtkodeperkiraan.getText().equals("")) || (txtnamapenabung.getText().equals("")) || (txtnorekening.getText().equals(""))) {
            JOptionPane.showMessageDialog(null, "Data Belum Lengkap.. !");
            txtnamaBank.requestFocus();
        } else {
            aksilog = "Update";
            prosesUpdate(1);
            prosesUpdateAkun(1);
            prosesUpdateLog();
            reloadData();
            cektombol();
            kosongkan();
            txtnamaBank.requestFocus();
        }
    } else {
        txtnamaBank.requestFocus();
    }
}//GEN-LAST:event_btnEditActionPerformed

private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    try {
        int x = JOptionPane.showConfirmDialog(this, "Apakah Data Di Hapus?", "", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            aksilog = "Delete";
            bankDao.deleteFromBANK(c, Integer.parseInt(txtidbank.getText()));
            prosesUpdateLog();
            reloadData();
            cektombol();
            kosongkan();
            txtnamaBank.requestFocus();

        } else {
            txtnamaBank.requestFocus();
        }
    } catch (SQLException ex) {
        Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_btnDeleteActionPerformed

private void txtnamaBankFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnamaBankFocusLost
// TODO add your handling code here:
    txtnamapenabung.requestFocus();
}//GEN-LAST:event_txtnamaBankFocusLost

private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
// TODO add your handling code here:
    kosongkan();
    cektombol();
    txtnamaBank.requestFocus();
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        try {
            // TODO add your handling code here:
            if (c != null) {
                c.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JTextField txtidbank;
    private javax.swing.JTextField txtkodeperkiraan;
    private javax.swing.JTextField txtnamaBank;
    private javax.swing.JTextField txtnamapenabung;
    private javax.swing.JTextField txtnorekening;
    // End of variables declaration//GEN-END:variables

    private void kosongkan() {
        txtidbank.setText("");
        txtnamaBank.setText("");
        txtnamapenabung.setText("");
        txtnorekening.setText("");
        txtkodeperkiraan.setText("");
    }

    void prosesUpdate(int pil) {
        try {
            b.setIDBANK(Integer.parseInt(txtidbank.getText()));
            b.setNAMABANK(txtnamaBank.getText());
            b.setNAMAPENABUNG(txtnamapenabung.getText());
            b.setKODEAKUN(txtkodeperkiraan.getText());
            b.setNOREK(txtnorekening.getText());

            boolean stat;
            if (pil == 0) {

                stat = bankDao.insertIntoBANK(c, b);
            } else {

                stat = bankDao.updateBANK(c, b);
            }
            if (stat) {
                JOptionPane.showMessageDialog(this, "Update Data Ok");
            } else {
                JOptionPane.showMessageDialog(this, "Update Data Gagal");
            }

        } catch (SQLException ex) {
            Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void prosesUpdateAkun(int pil) {
        pr.setKODEPERKIRAAN(txtkodeperkiraan.getText());
        pr.setNAMAPERKIRAAN("BANK " + txtnamaBank.getText());
        pr.setGRUP(1);
        pr.setTIPE("SD");
        try {
            boolean stat;
            if (pil == 0) {
                stat = dbpr.insert(c, pr);
            } else {
                stat = dbpr.update(c, pr);
            }
            if (!stat) {
                JOptionPane.showMessageDialog(this, "Update Data Akun Ok");
            } else {
                JOptionPane.showMessageDialog(this, "Update Data Akun Gagal");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }

    private void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            String sql = "select * from BANK";
            j.executeQuery(sql);
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
            jScrollPane1.repaint();
        } catch (Exception ex) {
            Logger.getLogger(FormBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void settingtombol(boolean simp, boolean edit, boolean hapus) {
        btnInsert.setEnabled(simp);
        btnEdit.setEnabled(edit);
        btnDelete.setEnabled(hapus);
    }

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
            lh.setTABEL("TBANK");
            lh.setNOREFF(txtidbank.getText());
            lh.setAKSI(aksilog);
            if (aksilog.equals("Insert")) {
                ketlog = "Insert Data Bank " + txtidbank.getText();
            } else if (aksilog.equals("Update")) {
                ketlog = "Update Data Bank " + txtidbank.getText();
            } else if (aksilog.equals("Delete")) {
                ketlog = "Delete Data Bank " + txtidbank.getText();
            }
            lh.setKETERANGAN(ketlog);
            lhdao.insert(c, lh);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }

    }

    void cektombol() {
        if (JavarieSoftApp.groupuser.equals("Pembelian")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Penjualan")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Administrator")) {
            settingtombol(true, true, false);
        } else if (JavarieSoftApp.groupuser.equals("KaGudang")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Operator")) {
            settingtombol(false, false, false);
        } else if (JavarieSoftApp.groupuser.equals("Accounting")) {
            settingtombol(false, false, false);
        }
    }
}