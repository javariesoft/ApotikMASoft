/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FormSetting.java
 *
 * Created on Dec 31, 2011, 8:56:55 AM
 */
package javariesoft;

import com.erv.db.koneksi;
import com.erv.db.perkiraanDao;
import com.erv.db.settingDao;
import com.erv.function.JDBCAdapter;
import com.erv.model.setting;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author erwadi
 */
public class FormSetting extends javax.swing.JInternalFrame {
    Connection c;
    setting s;
    /** Creates new form FormSetting */
    public FormSetting() {
        initComponents();
        try {
            c=koneksi.getKoneksiJ();
            c.createStatement().execute("set autocommit true");
            s=new setting();
            kosongkan();
            isiCombo();
            reloadData();
        } catch (SQLException ex) {
            Logger.getLogger(FormSetting.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel1 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDeskripsi = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cboKodePerkiraan = new javax.swing.JComboBox();

        setClosable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FormSetting.class);
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
        jLabel1.setBounds(20, 80, 120, 15);

        txtKode.setFont(resourceMap.getFont("txtKode.font")); // NOI18N
        txtKode.setText(resourceMap.getString("txtKode.text")); // NOI18N
        txtKode.setName("txtKode"); // NOI18N
        panelCool1.add(txtKode);
        txtKode.setBounds(180, 20, 170, 21);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        panelCool1.add(jLabel2);
        jLabel2.setBounds(20, 20, 70, 16);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        panelCool1.add(jLabel3);
        jLabel3.setBounds(20, 50, 120, 16);

        txtDeskripsi.setFont(resourceMap.getFont("txtDeskripsi.font")); // NOI18N
        txtDeskripsi.setText(resourceMap.getString("txtDeskripsi.text")); // NOI18N
        txtDeskripsi.setName("txtDeskripsi"); // NOI18N
        panelCool1.add(txtDeskripsi);
        txtDeskripsi.setBounds(180, 80, 330, 21);

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
        btnInsert.setBounds(20, 120, 90, 25);

        jButton2.setFont(resourceMap.getFont("jButton2.font")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        panelCool1.add(jButton2);
        jButton2.setBounds(120, 120, 100, 25);

        btnDelete.setFont(resourceMap.getFont("btnDelete.font")); // NOI18N
        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N
        panelCool1.add(btnDelete);
        btnDelete.setBounds(230, 120, 110, 25);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

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
        jScrollPane1.setBounds(22, 152, 530, 240);

        cboKodePerkiraan.setFont(resourceMap.getFont("cboKodePerkiraan.font")); // NOI18N
        cboKodePerkiraan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKodePerkiraan.setName("cboKodePerkiraan"); // NOI18N
        panelCool1.add(cboKodePerkiraan);
        cboKodePerkiraan.setBounds(180, 50, 330, 21);

        getContentPane().add(panelCool1, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 588, 432);
    }// </editor-fold>//GEN-END:initComponents

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try {
               perkiraanDao dbpk=new perkiraanDao();
            // TODO add your handling code here:
                s=settingDao.getDetails(c, jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                txtKode.setText(s.getKODE());
                cboKodePerkiraan.setSelectedItem(dbpk.getDetails(c,s.getAKUN()).getKODEPERKIRAAN()+"-"+dbpk.getDetails(c,s.getAKUN()).getNAMAPERKIRAAN());
                txtDeskripsi.setText(s.getDESKRIPSI());
        } catch (SQLException ex) {
            Logger.getLogger(FormSetting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}//GEN-LAST:event_jTable1MouseClicked

private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_btnInsertActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        if(c!=null){
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormSetting.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JComboBox cboKodePerkiraan;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private com.erv.function.PanelCool panelCool1;
    private javax.swing.JTextField txtDeskripsi;
    private javax.swing.JTextField txtKode;
    // End of variables declaration//GEN-END:variables
    private void kosongkan(){
        txtKode.setText("");
        txtDeskripsi.setText("");
    }
    private void isiCombo(){
        cboKodePerkiraan.removeAllItems();
        Statement stat;
        try {
            stat = c.createStatement();
            ResultSet rs=stat.executeQuery("Select * from PERKIRAAN order by KODEPERKIRAAN");
            while (rs.next()){
                cboKodePerkiraan.addItem(rs.getString(1)+"-"+rs.getString(2));
            }
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void reloadData(){
         try {
            JDBCAdapter j = new JDBCAdapter(c);
            String sql = "select * from SETTING";
            j.executeQuery(sql);
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
            jScrollPane1.repaint();
            j.close();
        } catch (Exception ex) {
            Logger.getLogger(FormBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}