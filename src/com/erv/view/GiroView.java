/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.view;

import com.erv.controller.GiroController;
import com.erv.db.GiroDao;
import com.erv.db.bankDao;
import com.erv.db.koneksi;
import com.erv.db.pelangganDao;
import com.erv.exception.GiroException;
import com.erv.function.ArrayTable;
import com.erv.function.ExecuteQuery;
import com.erv.fungsi.DecimalFormatRenderer;
import com.erv.model.Giro;
import com.erv.model.GiroModel;
import com.erv.model.TableGiroModel;
import com.erv.model.bank;
import com.erv.model.event.GiroListener;
import com.erv.model.pelanggan;
import datechooser.beans.DateChooserCombo;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author ervan
 */
public final class GiroView extends javax.swing.JPanel implements GiroListener, ListSelectionListener {

    /**
     * Creates new form GiroView
     */
    private final TableGiroModel tableGiroModel;
    private GiroModel model;
    private final GiroController controller;
    private int id;
    private final SimpleDateFormat df;

    public GiroView() {
        tableGiroModel = new TableGiroModel();
        model = new GiroModel();
        model.setListener(this);
        controller = new GiroController();
        controller.setModel(model);
        initComponents();
        tabelGiro.getSelectionModel().addListSelectionListener(this);
        tabelGiro.setModel(tableGiroModel);
        //reloaddata();
        
        df = new SimpleDateFormat("yyyy-MM-dd");
        dcTanggalGiro.setDateFormat(df);
        dcTanggalJTempo.setDateFormat(df);
        jScrollPane2.setVisible(false);
        jScrollPane2.setSize(350, 150);
//        txtKriteria.setVisible(true);
        CboStatCari.setVisible(false);
//        try {
//            //loadDatabase();
//            //isiCombo();
//            reset();
//        } catch (SQLException ex) {
//            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GiroException ex) {
//            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public JComboBox getCboBankPenerima() {
        return cboBankPenerima;
    }

    public void setCboBankPenerima(JComboBox cboBankPenerima) {
        this.cboBankPenerima = cboBankPenerima;
    }

    public JComboBox getCboStatus() {
        return cboStatus;
    }

    public void setCboStatus(JComboBox cboStatus) {
        this.cboStatus = cboStatus;
    }

    public JTextField getTxtBankAsal() {
        return txtBankAsal;
    }

    public void setTxtBankAsal(JTextField txtBankAsal) {
        this.txtBankAsal = txtBankAsal;
    }

    public JFormattedTextField getTxtJumlah() {
        return txtJumlah;
    }

    public void setTxtJumlah(JFormattedTextField txtJumlah) {
        this.txtJumlah = txtJumlah;
    }

    public JTextField getTxtNamaPenerima() {
        return txtNamaPenerima;
    }

    public void setTxtNamaPenerima(JTextField txtNamaPenerima) {
        this.txtNamaPenerima = txtNamaPenerima;
    }

    public JTextField getTxtNomorGiro() {
        return txtNomorGiro;
    }

    public void setTxtNomorGiro(JTextField txtNomorGiro) {
        this.txtNomorGiro = txtNomorGiro;
    }

    public JTextField getTxtPemilikGiro() {
        return txtPemilikGiro;
    }

    public void setTxtPemilikGiro(JTextField txtPemilikGiro) {
        this.txtPemilikGiro = txtPemilikGiro;
    }

    public DateChooserCombo getDcTanggalGiro() {
        return dcTanggalGiro;
    }

    public void setDcTanggalGiro(DateChooserCombo dcTanggalGiro) {
        this.dcTanggalGiro = dcTanggalGiro;
    }

    public DateChooserCombo getDcTanggalJTempo() {
        return dcTanggalJTempo;
    }

    public void setDcTanggalJTempo(DateChooserCombo dcTanggalJTempo) {
        this.dcTanggalJTempo = dcTanggalJTempo;
    }

    public GiroModel getGiroModel() {
        return model;
    }

    public void setGiroModel(GiroModel model) {
        this.model = model;
    }

    public JTable getTabelGiro() {
        return tabelGiro;
    }

    public void setTabelGiro(JTable tabelGiro) {
        this.tabelGiro = tabelGiro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tabelPelanggan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtNomorGiro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dcTanggalGiro = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        dcTanggalJTempo = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNamaPenerima = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtPemilikGiro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBankAsal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboBankPenerima = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelGiro = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        cboKriteria = new javax.swing.JComboBox();
        txtKriteria = new javax.swing.JTextField();
        cmdFilter = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtNamaPGiro = new javax.swing.JTextField();
        CboStatCari = new javax.swing.JComboBox();

        setName("Form"); // NOI18N
        setLayout(null);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tabelPelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelPelanggan.setName("tabelPelanggan"); // NOI18N
        tabelPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelPelangganKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelPelanggan);

        add(jScrollPane2);
        jScrollPane2.setBounds(510, 60, 60, 30);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(GiroView.class);
        jLabel1.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(10, 14, 127, 15);

        txtNomorGiro.setFont(resourceMap.getFont("txtNomorGiro.font")); // NOI18N
        txtNomorGiro.setText(resourceMap.getString("txtNomorGiro.text")); // NOI18N
        txtNomorGiro.setName("txtNomorGiro"); // NOI18N
        txtNomorGiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomorGiroActionPerformed(evt);
            }
        });
        add(txtNomorGiro);
        txtNomorGiro.setBounds(141, 11, 246, 21);

        jLabel2.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(10, 37, 127, 15);

        dcTanggalGiro.setFieldFont(resourceMap.getFont("dcTanggalJTempo.dch_combo_fieldFont")); // NOI18N
        add(dcTanggalGiro);
        dcTanggalGiro.setBounds(141, 37, 160, 20);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(10, 63, 127, 15);

        dcTanggalJTempo.setFieldFont(resourceMap.getFont("dcTanggalJTempo.dch_combo_fieldFont")); // NOI18N
        add(dcTanggalJTempo);
        dcTanggalJTempo.setBounds(141, 63, 160, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        add(jLabel4);
        jLabel4.setBounds(10, 92, 127, 15);

        txtJumlah.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtJumlah.setText(resourceMap.getString("txtJumlah.text")); // NOI18N
        txtJumlah.setFont(resourceMap.getFont("txtJumlah.font")); // NOI18N
        txtJumlah.setName("txtJumlah"); // NOI18N
        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });
        add(txtJumlah);
        txtJumlah.setBounds(141, 89, 246, 21);

        jLabel5.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        add(jLabel5);
        jLabel5.setBounds(10, 118, 127, 15);

        txtNamaPenerima.setFont(resourceMap.getFont("txtNamaPenerima.font")); // NOI18N
        txtNamaPenerima.setText(resourceMap.getString("txtNamaPenerima.text")); // NOI18N
        txtNamaPenerima.setName("txtNamaPenerima"); // NOI18N
        txtNamaPenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaPenerimaActionPerformed(evt);
            }
        });
        add(txtNamaPenerima);
        txtNamaPenerima.setBounds(141, 115, 246, 21);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        add(jLabel6);
        jLabel6.setBounds(420, 11, 90, 15);

        cboStatus.setFont(resourceMap.getFont("cboStatus.font")); // NOI18N
        cboStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboStatus.setName("cboStatus"); // NOI18N
        add(cboStatus);
        cboStatus.setBounds(522, 11, 150, 21);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        add(jLabel7);
        jLabel7.setBounds(420, 37, 90, 15);

        txtPemilikGiro.setFont(resourceMap.getFont("txtPemilikGiro.font")); // NOI18N
        txtPemilikGiro.setText(resourceMap.getString("txtPemilikGiro.text")); // NOI18N
        txtPemilikGiro.setName("txtPemilikGiro"); // NOI18N
        txtPemilikGiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPemilikGiroActionPerformed(evt);
            }
        });
        txtPemilikGiro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPemilikGiroKeyPressed(evt);
            }
        });
        add(txtPemilikGiro);
        txtPemilikGiro.setBounds(522, 37, 100, 21);

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        add(jLabel8);
        jLabel8.setBounds(420, 64, 90, 15);

        txtBankAsal.setFont(resourceMap.getFont("txtBankAsal.font")); // NOI18N
        txtBankAsal.setText(resourceMap.getString("txtBankAsal.text")); // NOI18N
        txtBankAsal.setName("txtBankAsal"); // NOI18N
        txtBankAsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBankAsalActionPerformed(evt);
            }
        });
        add(txtBankAsal);
        txtBankAsal.setBounds(522, 63, 340, 21);

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        add(jLabel9);
        jLabel9.setBounds(420, 92, 100, 15);

        cboBankPenerima.setFont(resourceMap.getFont("cboBankPenerima.font")); // NOI18N
        cboBankPenerima.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBankPenerima.setName("cboBankPenerima"); // NOI18N
        add(cboBankPenerima);
        cboBankPenerima.setBounds(522, 89, 190, 21);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tabelGiro.setFont(resourceMap.getFont("tabelGiro.font")); // NOI18N
        tabelGiro.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelGiro.setName("tabelGiro"); // NOI18N
        jScrollPane1.setViewportView(tabelGiro);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 192, 850, 309);

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        add(jLabel10);
        jLabel10.setBounds(70, 168, 70, 15);

        cboKriteria.setFont(resourceMap.getFont("cboKriteria.font")); // NOI18N
        cboKriteria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKriteria.setName("cboKriteria"); // NOI18N
        cboKriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKriteriaActionPerformed(evt);
            }
        });
        add(cboKriteria);
        cboKriteria.setBounds(141, 164, 140, 21);

        txtKriteria.setFont(resourceMap.getFont("txtKriteria.font")); // NOI18N
        txtKriteria.setText(resourceMap.getString("txtKriteria.text")); // NOI18N
        txtKriteria.setName("txtKriteria"); // NOI18N
        txtKriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKriteriaActionPerformed(evt);
            }
        });
        add(txtKriteria);
        txtKriteria.setBounds(288, 164, 290, 21);

        cmdFilter.setFont(resourceMap.getFont("cmdFilter.font")); // NOI18N
        cmdFilter.setIcon(resourceMap.getIcon("cmdFilter.icon")); // NOI18N
        cmdFilter.setText(resourceMap.getString("cmdFilter.text")); // NOI18N
        cmdFilter.setName("cmdFilter"); // NOI18N
        cmdFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFilterActionPerformed(evt);
            }
        });
        add(cmdFilter);
        cmdFilter.setBounds(590, 156, 112, 30);

        jSeparator1.setName("jSeparator1"); // NOI18N
        add(jSeparator1);
        jSeparator1.setBounds(10, 146, 816, 5);

        txtNamaPGiro.setFont(resourceMap.getFont("txtNamaPGiro.font")); // NOI18N
        txtNamaPGiro.setText(resourceMap.getString("txtNamaPGiro.text")); // NOI18N
        txtNamaPGiro.setName("txtNamaPGiro"); // NOI18N
        add(txtNamaPGiro);
        txtNamaPGiro.setBounds(630, 37, 230, 21);

        CboStatCari.setFont(resourceMap.getFont("CboStatCari.font")); // NOI18N
        CboStatCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Open", "Clear" }));
        CboStatCari.setName("CboStatCari"); // NOI18N
        add(CboStatCari);
        CboStatCari.setBounds(290, 164, 90, 21);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPemilikGiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPemilikGiroActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPemilikGiroActionPerformed

    private void txtPemilikGiroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPemilikGiroKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pelangganDao bDao;
            List<List<Object>> rows = new ArrayList<List<Object>>();
            String where = "KODEPELANGGAN like '" + txtPemilikGiro.getText() + "%' OR lower(NAMA) like '%" + txtPemilikGiro.getText() + "%'";
            List<pelanggan> pelangganList;
            List<Object> newRow;
            try {
                Connection c = koneksi.getKoneksiJ();
                bDao = new pelangganDao(c);
                pelangganList = bDao.getAllFilter(where, 100);
                String header[] = {"Kode", "Nama", "Alamat"};
                for (ListIterator<pelanggan> it = pelangganList.listIterator(); it.hasNext();) {
                    pelanggan n = it.next();
                    newRow = new ArrayList<Object>();
                    newRow.add(n.getKODEPELANGGAN());
                    newRow.add(n.getNAMA());
                    newRow.add(n.getALAMAT());
                    rows.add(newRow);
                }
                ArrayTable a;
                a = new ArrayTable(header, rows);
                jScrollPane2.getViewport().remove(tabelPelanggan);
                tabelPelanggan = new JTable(a);
                TableColumn col = tabelPelanggan.getColumnModel().getColumn(0);
                col.setPreferredWidth(30);
                tabelPelanggan.setFont(new Font("Tahoma", Font.BOLD, 12));
                tabelPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        tabelPelangganKeyPressed(evt);
                    }
                });
                jScrollPane2.getViewport().add(tabelPelanggan);
                jScrollPane2.repaint();
                bDao.close();
                c.close();
            } catch (SQLException ex) {

            } 
            jScrollPane2.setVisible(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            jScrollPane2.setVisible(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tabelPelanggan.requestFocus();
            tabelPelanggan.getSelectionModel().setSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_txtPemilikGiroKeyPressed

    private void tabelPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelPelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPemilikGiro.setText(tabelPelanggan.getValueAt(tabelPelanggan.getSelectedRow(), 0).toString());
            txtNamaPGiro.setText(tabelPelanggan.getValueAt(tabelPelanggan.getSelectedRow(), 1).toString());
            jScrollPane2.setVisible(false);
            txtBankAsal.requestFocus();
        }
    }//GEN-LAST:event_tabelPelangganKeyPressed

    private void cmdFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFilterActionPerformed
        try {
            //reloaddata();
            Connection c = koneksi.getKoneksiJ();
            loadDatabase(c);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GiroException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmdFilterActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
        txtNamaPenerima.requestFocus();
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtNomorGiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomorGiroActionPerformed
        // TODO add your handling code here:
        dcTanggalGiro.requestFocus();
    }//GEN-LAST:event_txtNomorGiroActionPerformed

    private void txtNamaPenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaPenerimaActionPerformed
        // TODO add your handling code here:
        cboStatus.requestFocus();
    }//GEN-LAST:event_txtNamaPenerimaActionPerformed

    private void txtBankAsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBankAsalActionPerformed
        // TODO add your handling code here:
        cboBankPenerima.requestFocus();
    }//GEN-LAST:event_txtBankAsalActionPerformed

    private void cboKriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKriteriaActionPerformed
        // TODO add your handling code here:
        if (cboKriteria.getSelectedIndex() == 0) {
            txtKriteria.setVisible(true);
            CboStatCari.setVisible(false);
        } else if (cboKriteria.getSelectedIndex() == 1) {
            txtKriteria.setVisible(false);
            CboStatCari.setVisible(true);
        }
    }//GEN-LAST:event_cboKriteriaActionPerformed

    private void txtKriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKriteriaActionPerformed
        try {
            // TODO add your handling code here:
            Connection c = koneksi.getKoneksiJ();
            reloaddata(c);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtKriteriaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CboStatCari;
    private javax.swing.JComboBox cboBankPenerima;
    private javax.swing.JComboBox cboKriteria;
    private javax.swing.JComboBox cboStatus;
    private javax.swing.JButton cmdFilter;
    private datechooser.beans.DateChooserCombo dcTanggalGiro;
    private datechooser.beans.DateChooserCombo dcTanggalJTempo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tabelGiro;
    private javax.swing.JTable tabelPelanggan;
    private javax.swing.JTextField txtBankAsal;
    private javax.swing.JFormattedTextField txtJumlah;
    private javax.swing.JTextField txtKriteria;
    private javax.swing.JTextField txtNamaPGiro;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNomorGiro;
    private javax.swing.JTextField txtPemilikGiro;
    // End of variables declaration//GEN-END:variables

    public void onChange(GiroModel model) {
        setId(model.getID());
        txtNomorGiro.setText(model.getNOMORGIRO());
        dcTanggalGiro.setText(model.getTGLGIRO());
        dcTanggalJTempo.setText(model.getTGLJTEMPO());
        txtJumlah.setValue(model.getJUMLAH());
        txtNamaPenerima.setText(model.getNAMAPENERIMA());
        cboStatus.setSelectedIndex(model.getSTATUS());
        txtPemilikGiro.setText(model.getKODEPELANGGAN());
        txtBankAsal.setText(model.getBANKASAL());
        cboBankPenerima.setSelectedItem(model.getIDBANK());
    }

    public void onInsert(Giro cabang) {
        tableGiroModel.add(cabang);
    }

    public void onUpdate(Giro cabang) {
        int index = tabelGiro.getSelectedRow();
        tableGiroModel.set(index, cabang);
    }

    public void onDelete() {
        int index = tabelGiro.getSelectedRow();
        tableGiroModel.remove(index);
    }

    public void valueChanged(ListSelectionEvent lse) {
        try {
            Calendar cld = Calendar.getInstance();
            Giro giro = tableGiroModel.get(tabelGiro.getSelectedRow());
            setId(giro.getID());
            txtNomorGiro.setText(giro.getNOMORGIRO());
            cld.setTime(df.parse(giro.getTGLGIRO()));
            dcTanggalGiro.setSelectedDate(cld);
            cld.setTime(df.parse(giro.getTGLJTEMPO()));
            dcTanggalJTempo.setSelectedDate(cld);
            txtJumlah.setValue(giro.getJUMLAH());
            txtNamaPenerima.setText(giro.getNAMAPENERIMA());
            cboStatus.setSelectedIndex(giro.getSTATUS());
            txtPemilikGiro.setText(giro.getKODEPELANGGAN());
            txtNamaPGiro.setText(giro.getPlg().getNAMA()); 
            txtBankAsal.setText(giro.getBANKASAL());
            cboBankPenerima.setSelectedItem(giro.getIDBANK());
        } catch (IndexOutOfBoundsException exception) {
        } catch (ParseException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadDatabase(Connection con) throws SQLException, GiroException, ClassNotFoundException {
        GiroDao dao = new GiroDao(con);
        String kriteria="";
        if (cboKriteria.getSelectedIndex() == 0) {
            if(txtKriteria.getText().equals("")){
                kriteria += "AND STATUS=0 ";
            }else{
                kriteria += "AND NOMORGIRO='" + txtKriteria.getText() + "' ";
            }           
        } else if (cboKriteria.getSelectedIndex() == 1) {
            kriteria += "AND STATUS='" + CboStatCari.getSelectedIndex() + "' ";
        } else if (cboKriteria.getSelectedIndex() == 2) {
            kriteria += "AND lower(pelanggan.NAMA) like '%" + txtKriteria.getText()+ "%' ";
        }
        tableGiroModel.setList(dao.selectAll(kriteria));
        tabelGiro.revalidate();
        repaint();
    }

    public void isiCombo(Connection con) {
        try {
            cboStatus.removeAllItems();
            cboStatus.addItem("Open");
            cboBankPenerima.removeAllItems();
            List<bank> banks = bankDao.getAlldetails(con);
            for (bank object : banks) {
                cboBankPenerima.addItem(object.getIDBANK() + "-" + object.getNAMABANK());
            }
            cboKriteria.removeAllItems();
            cboKriteria.addItem("NOMORGIRO");       
            cboKriteria.addItem("STATUS");
            cboKriteria.addItem("PELANGGAN");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        } 
    }

    public void insert(Connection c) {
        controller.insertGiro(c,this);
    }

    public void update(Connection c) {
        controller.updateGiro(c, this);
    }

    public void delete(Connection c) {
        controller.deleteGiro(c,this);
    }

    public void reset() {
        try {
            controller.resetGiro();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloaddata(Connection c) {
        List<List<Object>> rows = new ArrayList<List<Object>>();
        String sql = "select g.ID, g.NOMORGIRO,p.NAMA, g.TGLGIRO, g.TGLJTEMPO, g.JUMLAH, case g.STATUS when 0 then 'Open' when 1 then 'Clear' end as Status from GIRO g \n"
                + "inner join PELANGGAN p on g.KODEPELANGGAN = p.KODEPELANGGAN\n"
                + "inner join BANK b on g.IDBANK = b.IDBANK WHERE 1=1 ";
        if (cboKriteria.getSelectedIndex() == 0) {
            if(txtKriteria.getText().equals("")){
                sql += "AND g.STATUS=0 ";
            }else{
                sql += "AND g.NOMORGIRO='" + txtKriteria.getText() + "' ";
            }           
        } else if (cboKriteria.getSelectedIndex() == 1) {
            sql += "AND g.STATUS='" + CboStatCari.getSelectedIndex() + "' ";
        } else if (cboKriteria.getSelectedIndex() == 2) {
            sql += "AND lower(p.NAMA) like '%" + txtKriteria.getText()+ "%' ";
        }

        try {
            
            rows = ExecuteQuery.Query(c, sql);
            String header[] = {"ID", "NOMOR GIRO","PELANGGAN", "TGL GIRO", "TGL JATUH TEMPO", "JUMLAH", "STATUS"};
            ArrayTable a;
            a = new ArrayTable(header, rows);
            jScrollPane1.getViewport().remove(tabelGiro);
            tabelGiro = new JTable(a);
            tabelGiro.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
            tabelGiro.setFont(new Font("Tahoma", Font.BOLD, 12));
            jScrollPane1.getViewport().add(tabelGiro);
            jScrollPane1.repaint();
        }  catch (SQLException ex) {
            Logger.getLogger(GiroBayarView.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
