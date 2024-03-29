/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FlapJualPerSales.java
 *
 * Created on Jan 29, 2012, 9:06:20 PM
 */

package javariesoft;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author eigher
 */
public class FRekapJualPerPelanggan extends javax.swing.JInternalFrame {
Connection c;
java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
java.text.DateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
    /** Creates new form FlapJualPerSales */
    public FRekapJualPerPelanggan() {
        initComponents();
        try {
            tgl1.setDateFormat(d);
            tgl2.setDateFormat(d);
            c = koneksi.getKoneksiJ();
        } catch (SQLException ex) {
            Logger.getLogger(FRekapJualPerPelanggan.class.getName()).log(Level.SEVERE, null, ex);
        }
        jScrollPane1.setVisible(false);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtnamapelanggan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtkodepelanggan = new javax.swing.JTextField();
        btnKeluar = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        tgl1 = new datechooser.beans.DateChooserCombo();
        tgl2 = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(FRekapJualPerPelanggan.class);
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
        jScrollPane1.setBounds(140, 80, 300, 100);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 60, 110, 15);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 30, 70, 15);

        txtnamapelanggan.setFont(resourceMap.getFont("txtnamapelanggan.font")); // NOI18N
        txtnamapelanggan.setForeground(resourceMap.getColor("txtnamapelanggan.foreground")); // NOI18N
        txtnamapelanggan.setText(resourceMap.getString("txtnamapelanggan.text")); // NOI18N
        txtnamapelanggan.setDisabledTextColor(resourceMap.getColor("txtnamapelanggan.disabledTextColor")); // NOI18N
        txtnamapelanggan.setName("txtnamapelanggan"); // NOI18N
        txtnamapelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamapelangganActionPerformed(evt);
            }
        });
        txtnamapelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamapelangganKeyPressed(evt);
            }
        });
        getContentPane().add(txtnamapelanggan);
        txtnamapelanggan.setBounds(140, 60, 260, 21);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 90, 110, 15);

        txtkodepelanggan.setEditable(false);
        txtkodepelanggan.setFont(resourceMap.getFont("txtkodepelanggan.font")); // NOI18N
        txtkodepelanggan.setText(resourceMap.getString("txtkodepelanggan.text")); // NOI18N
        txtkodepelanggan.setName("txtkodepelanggan"); // NOI18N
        txtkodepelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodepelangganActionPerformed(evt);
            }
        });
        txtkodepelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkodepelangganKeyPressed(evt);
            }
        });
        getContentPane().add(txtkodepelanggan);
        txtkodepelanggan.setBounds(140, 90, 260, 21);

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
        btnKeluar.setBounds(220, 130, 130, 40);

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
        btnPreview.setBounds(60, 130, 130, 40);

        tgl1.setFieldFont(resourceMap.getFont("tgl1.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl1);
        tgl1.setBounds(140, 30, 120, 26);

        tgl2.setFieldFont(resourceMap.getFont("tgl2.dch_combo_fieldFont")); // NOI18N
        getContentPane().add(tgl2);
        tgl2.setBounds(290, 30, 130, 26);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(260, 30, 30, 16);

        setBounds(0, 0, 454, 218);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            txtkodepelanggan.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            txtnamapelanggan.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
            jScrollPane1.setVisible(false);
            btnPreview.requestFocus();
        }

    }//GEN-LAST:event_jTable1KeyPressed

    private void txtkodepelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodepelangganActionPerformed
        // TODO add your handling code here:
    

    }//GEN-LAST:event_txtkodepelangganActionPerformed

    private void txtkodepelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkodepelangganKeyPressed
        // TODO add your handling code here:
        

    }//GEN-LAST:event_txtkodepelangganKeyPressed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    HashMap parameter = new HashMap();
    JasperPrint jasperPrint = null;
//    parameter.put("Pbulan", cmbbulan.getSelectedIndex() + 1);
//    parameter.put("Ptahun", txttahun.getText());
    parameter.put("Ptgl1", tgl1.getText());
    parameter.put("Ptgl2", tgl2.getText());
    parameter.put("Ppelanggan", txtkodepelanggan.getText());
    try {
        URL url = new URL(global.REPORT+ "/PenjualanPerPelanggan.jasper");
        InputStream in = url.openStream();
        jasperPrint = JasperFillManager.fillReport(in, parameter, c);
        JasperViewer.viewReport(jasperPrint, false);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,ex.toString());
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        //Logger.getLogger(formlaporan.class.getName()).log(Level.SEVERE, null, ex);
    }

    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void txtnamapelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamapelangganActionPerformed
        // TODO add your handling code here:
        jScrollPane1.setVisible(true);
    reloadData();
    }//GEN-LAST:event_txtnamapelangganActionPerformed

    private void txtnamapelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamapelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 40) {
        jScrollPane1.setVisible(true);
        jTable1.requestFocus();
        jTable1.getSelectionModel().setSelectionInterval(0, 0);
    }
    if (evt.getKeyCode() == 27) {
        jScrollPane1.setVisible(false);
    }
    }//GEN-LAST:event_txtnamapelangganKeyPressed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    try {
        // TODO add your handling code here:
        c.close();
    } catch (SQLException ex) {
        Logger.getLogger(FRekapJualPerPelanggan.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPreview;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private datechooser.beans.DateChooserCombo tgl1;
    private datechooser.beans.DateChooserCombo tgl2;
    private javax.swing.JTextField txtkodepelanggan;
    private javax.swing.JTextField txtnamapelanggan;
    // End of variables declaration//GEN-END:variables
void reloadData() {
        try {
            JDBCAdapter j = new JDBCAdapter(c);
            j.executeQuery("Select KODEPELANGGAN , NAMA, ALAMAT  from PELANGGAN where STATUSAKTIF='0' AND (KODEPELANGGAN like '"+ txtnamapelanggan.getText() +"%' or lower(NAMA) like '%" + txtnamapelanggan.getText().toLowerCase() + "%')");
            jScrollPane1.getViewport().remove(jTable1);
            jTable1 = new JTable(j);
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            jScrollPane1.getViewport().add(jTable1);
            j.close();
        } catch (Exception ex) {
            Logger.getLogger(FRekapJualPerPelanggan.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
