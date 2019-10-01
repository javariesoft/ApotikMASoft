/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * FormPelanggan.java
 *
 * Created on Nov 25, 2011, 5:11:03 AM
 */
package com.eigher.form;

import com.erv.db.koneksi;
import com.eigher.db.usertableDao;
import com.erv.function.JDBCAdapter;
import com.eigher.model.usertable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import java.awt.Font;
import java.text.SimpleDateFormat;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *
 * @author erwadi
 */
public class FormLogHistory extends javax.swing.JInternalFrame {

    usertable ut;
    usertableDao utdao;
    //Connection c;
    com.erv.function.Util u = new com.erv.function.Util();
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form FormPelanggan
     */
    public FormLogHistory() {
        initComponents();
        Connection c = null;
        try {
            c = koneksi.getKoneksiJ();
            c.createStatement().execute("set autocommit true");
            ut = new usertable();
            utdao = new usertableDao();
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            reloadUser(c);
            reloadTabel(c);
        } catch (SQLException ex) {
            Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblKodeAkun = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbUser = new javax.swing.JComboBox();
        tgl1 = new datechooser.beans.DateChooserCombo();
        tgl2 = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cmbTabel = new javax.swing.JComboBox();
        txtCari = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormLogHistory.class);
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

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setHorizontalScrollBar(null);
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        panelCool1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 110, 960, 490);

        lblKodeAkun.setFont(resourceMap.getFont("lblKodeAkun.font")); // NOI18N
        lblKodeAkun.setForeground(resourceMap.getColor("lblKodeAkun.foreground")); // NOI18N
        lblKodeAkun.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKodeAkun.setName("lblKodeAkun"); // NOI18N
        panelCool1.add(lblKodeAkun);
        lblKodeAkun.setBounds(420, 240, 230, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        panelCool1.add(jLabel3);
        jLabel3.setBounds(360, 20, 30, 16);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        panelCool1.add(jLabel4);
        jLabel4.setBounds(340, 80, 50, 16);

        cmbUser.setFont(resourceMap.getFont("cmbUser.font")); // NOI18N
        cmbUser.setName("cmbUser"); // NOI18N
        cmbUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUserActionPerformed(evt);
            }
        });
        panelCool1.add(cmbUser);
        cmbUser.setBounds(160, 50, 420, 21);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        tgl1.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tgl1OnCommit(evt);
            }
        });
        panelCool1.add(tgl1);
        tgl1.setBounds(160, 20, 180, 26);

        tgl2.setFieldFont(resourceMap.getFont("tgl2.dch_combo_fieldFont")); // NOI18N
        tgl2.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                tgl2OnCommit(evt);
            }
        });
        panelCool1.add(tgl2);
        tgl2.setBounds(400, 20, 180, 26);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel5.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        panelCool1.add(jLabel5);
        jLabel5.setBounds(40, 20, 110, 16);

        btnExit.setFont(resourceMap.getFont("btnExit.font")); // NOI18N
        btnExit.setIcon(resourceMap.getIcon("btnExit.icon")); // NOI18N
        btnExit.setText(resourceMap.getString("btnExit.text")); // NOI18N
        btnExit.setName("btnExit"); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        panelCool1.add(btnExit);
        btnExit.setBounds(600, 60, 140, 40);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setForeground(resourceMap.getColor("jLabel6.foreground")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        panelCool1.add(jLabel6);
        jLabel6.setBounds(40, 50, 110, 16);

        cmbTabel.setFont(resourceMap.getFont("cmbTabel.font")); // NOI18N
        cmbTabel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTabel.setName("cmbTabel"); // NOI18N
        cmbTabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTabelActionPerformed(evt);
            }
        });
        panelCool1.add(cmbTabel);
        cmbTabel.setBounds(160, 80, 170, 21);

        txtCari.setFont(resourceMap.getFont("txtCari.font")); // NOI18N
        txtCari.setText(resourceMap.getString("txtCari.text")); // NOI18N
        txtCari.setToolTipText(resourceMap.getString("txtCari.toolTipText")); // NOI18N
        txtCari.setName("txtCari"); // NOI18N
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        panelCool1.add(txtCari);
        txtCari.setBounds(400, 80, 180, 21);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        panelCool1.add(jLabel7);
        jLabel7.setBounds(40, 80, 110, 16);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 991, 648);
    }// </editor-fold>//GEN-END:initComponents

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jTable1MouseClicked

private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
    dispose();
}//GEN-LAST:event_btnExitActionPerformed

private void cmbUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUserActionPerformed
// TODO add your handling code here:
    Connection c = null;
    try {
        c = koneksi.getKoneksiJ();
        reloadData(c);
        reloadTabel(c);
    } catch (SQLException ex) {
        Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}//GEN-LAST:event_cmbUserActionPerformed

private void cmbTabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTabelActionPerformed
// TODO add your handling code here:
    Connection c = null;
    try {
        c = koneksi.getKoneksiJ();
        reloadData(c);
    } catch (SQLException ex) {
        Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_cmbTabelActionPerformed

private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
// TODO add your handling code here:
    Connection c = null;
    try {
        c = koneksi.getKoneksiJ();
        reloadData(c);
    } catch (SQLException ex) {
        Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_txtCariActionPerformed

private void tgl1OnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tgl1OnCommit
// TODO add your handling code here:
    Connection c = null;
    try {
        c = koneksi.getKoneksiJ();
        reloadData(c);
    } catch (SQLException ex) {
        Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_tgl1OnCommit

private void tgl2OnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_tgl2OnCommit
// TODO add your handling code here:
    Connection c = null;
    try {
        c = koneksi.getKoneksiJ();
        reloadData(c);
    } catch (SQLException ex) {
        Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}//GEN-LAST:event_tgl2OnCommit

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
//        if(c!=null){
//            try {
//                c.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JComboBox cmbTabel;
    private javax.swing.JComboBox cmbUser;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblKodeAkun;
    private com.erv.function.PanelCool panelCool1;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables

    void reloadData(Connection c) {

        try {
            JDBCAdapter j = new JDBCAdapter(c);
            //String period = txtTahun.getText() + "." + (cboBulan.getSelectedIndex() + 1);
            String sql = "SELECT * FROM LOGHISTORY where TANGGAL>='" + tgl1.getText() + "' "
                    + "and TANGGAL<='" + tgl2.getText() + "' AND USERIDENTITY='" + cmbUser.getSelectedItem() + "' "
                    + "and TABEL='" + cmbTabel.getSelectedItem() + "'";
            if (txtCari.getText().equals("")) {
                sql += "";
            } else {
                sql += " and NOREFF LIKE '%" + txtCari.getText() + "%'";
            }

            j.executeQuery(sql);
            jScrollPane2.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            JTableHeader header = jTable1.getTableHeader();
            //header.setBackground(Color.yellow);

            TableColumn col = jTable1.getColumnModel().getColumn(0);
            col.setPreferredWidth(6);
            col = jTable1.getColumnModel().getColumn(1);
            col.setPreferredWidth(20);
            col = jTable1.getColumnModel().getColumn(2);
            col.setPreferredWidth(15);
            col = jTable1.getColumnModel().getColumn(3);
            col.setPreferredWidth(15);
            col = jTable1.getColumnModel().getColumn(4);
            col.setPreferredWidth(20);
            col = jTable1.getColumnModel().getColumn(5);
            col.setPreferredWidth(20);
            col = jTable1.getColumnModel().getColumn(6);
            col.setPreferredWidth(20);
            col = jTable1.getColumnModel().getColumn(7);
            col.setPreferredWidth(500);
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jTable1.setFont(new Font("Tahoma", Font.BOLD, 11));
            //jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jScrollPane2.getViewport().add(jTable1);
            jScrollPane2.repaint();
        } catch (Exception ex) {
            Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadUser(Connection c) {
        cmbUser.removeAllItems();
        //cboBank.removeAllItems();
        String sql = "select USERIDENTITY from USERTABLE where USERIDENTITY<>''";
        try {
            Statement stat = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery(sql);

            int j = 0;
            if (rs.last()) {
                j = rs.getRow();
            }
            if (j > 0) {
                rs.beforeFirst();
                int ca = 0;
                while (rs.next()) {
                    cmbUser.addItem(rs.getString(1));
                    ca++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloadTabel(Connection c) {

        cmbTabel.removeAllItems();
        //cboBank.removeAllItems();
        String sql = "SELECT DISTINCT(TABEL) FROM LOGHISTORY where USERIDENTITY ='" + cmbUser.getSelectedItem() + "'";
        try {
            Statement stat = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery(sql);

            int j = 0;
            if (rs.last()) {
                j = rs.getRow();
            }
            if (j > 0) {
                rs.beforeFirst();
                int ca = 0;
                while (rs.next()) {
                    cmbTabel.addItem(rs.getString(1));
                    ca++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormLogHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
