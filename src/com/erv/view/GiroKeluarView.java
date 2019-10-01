/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.view;

import com.erv.controller.GiroKeluarController;
import com.erv.database.Database;
import com.erv.db.koneksi;
import com.erv.db.supplierDao;
import com.erv.exception.JavarieException;
import com.erv.function.ArrayTable;
import com.erv.function.ExecuteQuery;
import com.erv.fungsi.DecimalFormatRenderer;
import com.erv.model.GiroKeluarModel;
import com.erv.model.Girokeluar;
import com.erv.model.TableGiroKeluarModel;
import com.erv.model.bank;
import com.erv.model.dao.GirokeluarDAO;
import com.erv.model.event.GiroKeluarListener;
import com.erv.model.supplier;
import datechooser.beans.DateChooserCombo;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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

/**
 *
 * @author ervan
 */
public final class GiroKeluarView extends javax.swing.JPanel implements GiroKeluarListener, ListSelectionListener {

    /**
     * Creates new form GiroKeluarView
     */
    TableGiroKeluarModel tableGiroKeluarModel;
    GiroKeluarModel model;
    GiroKeluarController controller;
    private int id;
    private final SimpleDateFormat df;

    public GiroKeluarView() {
        tableGiroKeluarModel = new TableGiroKeluarModel();
        model = new GiroKeluarModel();
        model.setListener(this);
        controller = new GiroKeluarController();
        controller.setModel(model);
        initComponents();
        tabelGiroKeluar.getSelectionModel().addListSelectionListener(this);
        tabelGiroKeluar.setModel(tableGiroKeluarModel);
        //reloaddata();
        df = new SimpleDateFormat("yyyy-MM-dd");
        dcTanggalGiro.setDateFormat(df);
        dcTanggalJTempo.setDateFormat(df);
        jScrollPane2.setVisible(false);
        jScrollPane2.setSize(350, 150);
//        try {
//            loadDatabase();
//            reset();
//        } catch (SQLException ex) {
//            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JavarieException ex) {
//            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JComboBox getCboBank() {
        return cboBank;
    }

    public void setCboBank(JComboBox cboBank) {
        this.cboBank = cboBank;
    }

    public JComboBox getCboKriteria() {
        return cboKriteria;
    }

    public void setCboKriteria(JComboBox cboKriteria) {
        this.cboKriteria = cboKriteria;
    }

    public JComboBox getCboStatus() {
        return cboStatus;
    }

    public void setCboStatus(JComboBox cboStatus) {
        this.cboStatus = cboStatus;
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

    public JTextField getTxtBankPenerima() {
        return txtBankPenerima;
    }

    public void setTxtBankPenerima(JTextField txtBankPenerima) {
        this.txtBankPenerima = txtBankPenerima;
    }

    public JFormattedTextField getTxtJumlah() {
        return txtJumlah;
    }

    public void setTxtJumlah(JFormattedTextField txtJumlah) {
        this.txtJumlah = txtJumlah;
    }

    public JTextField getTxtKriteria() {
        return txtKriteria;
    }

    public void setTxtKriteria(JTextField txtKriteria) {
        this.txtKriteria = txtKriteria;
    }

    public JTextField getTxtNamaPenerima() {
        return txtNamaPenerima;
    }

    public void setTxtNamaPenerima(JTextField txtNamaPenerima) {
        this.txtNamaPenerima = txtNamaPenerima;
    }

    public JTextField getTxtNamaSupplier() {
        return txtNamaSupplier;
    }

    public void setTxtNamaSupplier(JTextField txtNamaSupplier) {
        this.txtNamaSupplier = txtNamaSupplier;
    }

    public JTextField getTxtNomorGiro() {
        return txtNomorGiro;
    }

    public void setTxtNomorGiro(JTextField txtNomorGiro) {
        this.txtNomorGiro = txtNomorGiro;
    }

    public JTextField getTxtSupplier() {
        return txtSupplier;
    }

    public void setTxtSupplier(JTextField txtSupplier) {
        this.txtSupplier = txtSupplier;
    }

    public JTable getTabelGiroKeluar() {
        return tabelGiroKeluar;
    }

    public void setTabelGiroKeluar(JTable tabelGiroKeluar) {
        this.tabelGiroKeluar = tabelGiroKeluar;
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
        jLabel2 = new javax.swing.JLabel();
        dcTanggalGiro = new datechooser.beans.DateChooserCombo();
        txtNomorGiro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dcTanggalJTempo = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNamaPenerima = new javax.swing.JTextField();
        cboStatus = new javax.swing.JComboBox();
        txtSupplier = new javax.swing.JTextField();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboBank = new javax.swing.JComboBox();
        txtBankPenerima = new javax.swing.JTextField();
        cmdFilter = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtKriteria = new javax.swing.JTextField();
        cboKriteria = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelGiroKeluar = new javax.swing.JTable();
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
        jScrollPane2.setBounds(520, 60, 70, 40);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(javariesoft.JavarieSoftApp.class).getContext().getResourceMap(GiroKeluarView.class);
        jLabel1.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(10, 14, 127, 15);

        jLabel2.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(10, 37, 127, 15);

        dcTanggalGiro.setFieldFont(resourceMap.getFont("dcTanggalGiro.dch_combo_fieldFont")); // NOI18N
        add(dcTanggalGiro);
        dcTanggalGiro.setBounds(141, 37, 150, 20);

        txtNomorGiro.setFont(resourceMap.getFont("txtNomorGiro.font")); // NOI18N
        txtNomorGiro.setName("txtNomorGiro"); // NOI18N
        txtNomorGiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomorGiroActionPerformed(evt);
            }
        });
        add(txtNomorGiro);
        txtNomorGiro.setBounds(141, 11, 246, 21);

        jLabel3.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(10, 63, 127, 15);

        dcTanggalJTempo.setFieldFont(resourceMap.getFont("dcTanggalGiro.dch_combo_fieldFont")); // NOI18N
        add(dcTanggalJTempo);
        dcTanggalJTempo.setBounds(141, 63, 150, 20);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        add(jLabel4);
        jLabel4.setBounds(10, 92, 127, 15);

        txtJumlah.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtJumlah.setFont(resourceMap.getFont("txtJumlah.font")); // NOI18N
        txtJumlah.setName("txtJumlah"); // NOI18N
        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });
        add(txtJumlah);
        txtJumlah.setBounds(141, 89, 246, 21);

        jLabel5.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        add(jLabel5);
        jLabel5.setBounds(10, 118, 127, 15);

        txtNamaPenerima.setFont(resourceMap.getFont("txtNamaPenerima.font")); // NOI18N
        txtNamaPenerima.setName("txtNamaPenerima"); // NOI18N
        txtNamaPenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaPenerimaActionPerformed(evt);
            }
        });
        add(txtNamaPenerima);
        txtNamaPenerima.setBounds(141, 115, 246, 21);

        cboStatus.setFont(resourceMap.getFont("cboStatus.font")); // NOI18N
        cboStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboStatus.setName("cboStatus"); // NOI18N
        add(cboStatus);
        cboStatus.setBounds(522, 11, 150, 21);

        txtSupplier.setFont(resourceMap.getFont("txtNamaSupplier.font")); // NOI18N
        txtSupplier.setName("txtSupplier"); // NOI18N
        txtSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupplierActionPerformed(evt);
            }
        });
        txtSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSupplierKeyPressed(evt);
            }
        });
        add(txtSupplier);
        txtSupplier.setBounds(522, 37, 100, 21);

        txtNamaSupplier.setFont(resourceMap.getFont("txtNamaSupplier.font")); // NOI18N
        txtNamaSupplier.setName("txtNamaSupplier"); // NOI18N
        add(txtNamaSupplier);
        txtNamaSupplier.setBounds(630, 37, 200, 21);

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        add(jLabel7);
        jLabel7.setBounds(410, 37, 100, 15);

        jLabel6.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        add(jLabel6);
        jLabel6.setBounds(410, 11, 100, 15);

        jLabel8.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        add(jLabel8);
        jLabel8.setBounds(410, 65, 100, 15);

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        add(jLabel10);
        jLabel10.setBounds(50, 167, 60, 15);

        cboBank.setFont(resourceMap.getFont("cboBank.font")); // NOI18N
        cboBank.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBank.setName("cboBank"); // NOI18N
        add(cboBank);
        cboBank.setBounds(522, 89, 310, 21);

        txtBankPenerima.setFont(resourceMap.getFont("txtBankPenerima.font")); // NOI18N
        txtBankPenerima.setName("txtBankPenerima"); // NOI18N
        txtBankPenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBankPenerimaActionPerformed(evt);
            }
        });
        add(txtBankPenerima);
        txtBankPenerima.setBounds(522, 63, 310, 21);

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
        cmdFilter.setBounds(584, 158, 112, 30);

        jSeparator1.setName("jSeparator1"); // NOI18N
        add(jSeparator1);
        jSeparator1.setBounds(10, 146, 816, 5);

        txtKriteria.setFont(resourceMap.getFont("txtKriteria.font")); // NOI18N
        txtKriteria.setName("txtKriteria"); // NOI18N
        add(txtKriteria);
        txtKriteria.setBounds(267, 165, 310, 20);

        cboKriteria.setFont(resourceMap.getFont("cboKriteria.font")); // NOI18N
        cboKriteria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKriteria.setName("cboKriteria"); // NOI18N
        cboKriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKriteriaActionPerformed(evt);
            }
        });
        add(cboKriteria);
        cboKriteria.setBounds(141, 164, 120, 21);

        jLabel11.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        add(jLabel11);
        jLabel11.setBounds(410, 91, 110, 15);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tabelGiroKeluar.setFont(resourceMap.getFont("tabelGiroKeluar.font")); // NOI18N
        tabelGiroKeluar.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelGiroKeluar.setName("tabelGiroKeluar"); // NOI18N
        jScrollPane1.setViewportView(tabelGiroKeluar);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 192, 816, 309);

        CboStatCari.setFont(resourceMap.getFont("CboStatCari.font")); // NOI18N
        CboStatCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Open", "Clear" }));
        CboStatCari.setName("CboStatCari"); // NOI18N
        add(CboStatCari);
        CboStatCari.setBounds(270, 165, 110, 22);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupplierActionPerformed

    private void txtSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupplierKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            List<List<Object>> rows = new ArrayList<List<Object>>();
            String where = "STATUSAKTIF='0' AND IDSUPPLIER like '" + txtSupplier.getText() + "%' OR lower(NAMA) like '%" + txtSupplier.getText() + "%'";
            List<supplier> supList;
            List<Object> newRow;
            try {
                supList = supplierDao.getAllFilter(koneksi.getKoneksiJ(), where, 100);
                String header[] = {"Kode", "Nama Supplier"};
                for (ListIterator<supplier> it = supList.listIterator(); it.hasNext();) {
                    supplier n = it.next();
                    newRow = new ArrayList<Object>();
                    newRow.add(n.getIDSUPPLIER());
                    newRow.add(n.getNAMA());
                    rows.add(newRow);
                }
                ArrayTable a;
                a = new ArrayTable(header, rows);
                jScrollPane2.getViewport().remove(tabelPelanggan);
                tabelPelanggan = new JTable(a);
                tabelPelanggan.setFont(new Font("Tahoma", Font.BOLD, 12));
                tabelPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        tabelPelangganKeyPressed(evt);
                    }
                });
                jScrollPane2.getViewport().add(tabelPelanggan);
                jScrollPane2.repaint();
            } catch (SQLException ex) {

            }
            jScrollPane2.setVisible(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            jScrollPane2.setVisible(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tabelPelanggan.requestFocus();
            tabelPelanggan.getSelectionModel().setSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_txtSupplierKeyPressed

    private void cmdFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFilterActionPerformed
        try {
            //        try {
//            // TODO add your handling code here:
//            GirokeluarDAO dao = Database.getGiroKeluarDao();
//            tableGiroKeluarModel.setList(dao.selectAll(cboKriteria.getSelectedItem().toString(), txtKriteria.getText(), 0));
//            tabelGiroKeluar.repaint();
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        reloaddata();
            loadDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavarieException ex) {
            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmdFilterActionPerformed

    private void tabelPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelPelangganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSupplier.setText(tabelPelanggan.getValueAt(tabelPelanggan.getSelectedRow(), 0).toString());
            txtNamaSupplier.setText(tabelPelanggan.getValueAt(tabelPelanggan.getSelectedRow(), 1).toString());
            jScrollPane2.setVisible(false);
            txtBankPenerima.requestFocus();
        }
    }//GEN-LAST:event_tabelPelangganKeyPressed

    private void txtNomorGiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomorGiroActionPerformed
        // TODO add your handling code here:
        dcTanggalGiro.requestFocus();
    }//GEN-LAST:event_txtNomorGiroActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
        txtNamaPenerima.requestFocus();
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtNamaPenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaPenerimaActionPerformed
        // TODO add your handling code here:
        cboStatus.requestFocus();
    }//GEN-LAST:event_txtNamaPenerimaActionPerformed

    private void txtBankPenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBankPenerimaActionPerformed
        // TODO add your handling code here:
        cboBank.requestFocus();
    }//GEN-LAST:event_txtBankPenerimaActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CboStatCari;
    private javax.swing.JComboBox cboBank;
    private javax.swing.JComboBox cboKriteria;
    private javax.swing.JComboBox cboStatus;
    private javax.swing.JButton cmdFilter;
    private datechooser.beans.DateChooserCombo dcTanggalGiro;
    private datechooser.beans.DateChooserCombo dcTanggalJTempo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tabelGiroKeluar;
    private javax.swing.JTable tabelPelanggan;
    private javax.swing.JTextField txtBankPenerima;
    private javax.swing.JFormattedTextField txtJumlah;
    private javax.swing.JTextField txtKriteria;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNomorGiro;
    private javax.swing.JTextField txtSupplier;
    // End of variables declaration//GEN-END:variables

    public void onChange(GiroKeluarModel model) {
        Calendar cld = Calendar.getInstance();
        setId(model.getId());
        txtNomorGiro.setText(model.getNomorgiro());
        cld.setTime(model.getTglgiro());
        dcTanggalGiro.setSelectedDate(cld);
        cld.setTime(model.getTgljtempo());
        dcTanggalJTempo.setSelectedDate(cld);
        txtJumlah.setValue(model.getJumlah());
        txtNamaPenerima.setText(model.getNamapenerima());
        cboStatus.setSelectedIndex(model.getStatus());
        txtSupplier.setText(model.getKodesupplier());
        txtBankPenerima.setText(model.getBanktujuan());
    }

    public void onInsert(Girokeluar girokeluar) {
        tableGiroKeluarModel.add(girokeluar);
    }

    public void onUpdate(Girokeluar girokeluar) {
        int index = tabelGiroKeluar.getSelectedRow();
        tableGiroKeluarModel.set(index, girokeluar);
    }

    public void onDelete() {
        int index = tabelGiroKeluar.getSelectedRow();
        tableGiroKeluarModel.remove(index);
    }

    public void valueChanged(ListSelectionEvent lse) {
        Calendar cld = Calendar.getInstance();
        Girokeluar giroKeluar = tableGiroKeluarModel.get(tabelGiroKeluar.getSelectedRow());
        setId(giroKeluar.getId());
        txtNomorGiro.setText(giroKeluar.getNomorgiro());
        cld.setTime(giroKeluar.getTglgiro());
        dcTanggalGiro.setSelectedDate(cld);
        cld.setTime(giroKeluar.getTgljtempo());
        dcTanggalJTempo.setSelectedDate(cld);
        txtJumlah.setValue(giroKeluar.getJumlah());
        txtNamaPenerima.setText(giroKeluar.getNamapenerima());
        cboStatus.setSelectedIndex(giroKeluar.getStatus());
        txtSupplier.setText(giroKeluar.getKodesupplier());
        txtBankPenerima.setText(giroKeluar.getBanktujuan());
        Connection c;
        try {
            c = koneksi.getKoneksiJ();
            supplier s = supplierDao.getDetails(c, giroKeluar.getKodesupplier());
            txtNamaSupplier.setText(s.getNAMA());
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(GiroKeluarView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void isiCombo(Connection con) {
        try {
            cboStatus.removeAllItems();
            cboStatus.addItem("Open");
            cboStatus.addItem("Clear");
            cboBank.removeAllItems();
            List<List<Object>> pk;
            pk = ExecuteQuery.Query(con, "select kodeperkiraan,namaperkiraan from perkiraan where kodeperkiraan like '11120.%'");
            for (List<Object> list : pk) {
                cboBank.addItem(list.get(0) + "-" + list.get(1));
            }
            cboKriteria.removeAllItems();
            cboKriteria.addItem("NOMORGIRO");
            cboKriteria.addItem("STATUS");
            cboKriteria.addItem("SUPPLIER");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }

    public void loadDatabase() throws SQLException, ClassNotFoundException, JavarieException {
        GirokeluarDAO dao = Database.getGiroKeluarDao();
        String kriteria = "";
        if (cboKriteria.getSelectedIndex() == 0) {
            if (txtKriteria.getText().equals("")) {
                kriteria += "AND girokeluar.STATUS=0 ";
            } else {
                kriteria += "AND girokeluar.NOMORGIRO='" + txtKriteria.getText() + "' ";
            }
        } else if (cboKriteria.getSelectedIndex() == 1) {
            kriteria += "AND girokeluar.STATUS='" + CboStatCari.getSelectedIndex() + "' ";
        } else if (cboKriteria.getSelectedIndex() == 2) {
            kriteria += "AND lower(supplier.NAMA) like '%" + txtKriteria.getText() + "%' ";
        }
        tableGiroKeluarModel.setList(dao.selectAll(kriteria, 100));
        tabelGiroKeluar.revalidate();
        repaint();
    }

    public void insert(Connection c) {
        controller.insertGiroKeluar(c, this);
    }

    public void update(Connection c) {
        controller.updateGiroKeluar(c, this);
    }

    public void delete() {
        controller.deleteGiroKeluar(this);
    }

    public void reset() {
        try {
            controller.resetGiroKeluar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GiroView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void reloaddata() {
        List<List<Object>> rows = new ArrayList<List<Object>>();
        String sql = "select gk.ID,gk.NOMORGIRO,sp.NAMA,gk.TGLGIRO,TGLJTEMPO,JUMLAH,case gk.STATUS when 0 then 'Open' "
                + "when 1 then 'Cair' end as Status  from GIROKELUAR gk inner join SUPPLIER sp "
                + "on gk.KODESUPPLIER = sp.IDSUPPLIER where 1=1 ";
        if (cboKriteria.getSelectedIndex() == 0) {
            if (txtKriteria.getText().equals("")) {
                sql += "AND gk.STATUS=0 ";
            } else {
                sql += "AND gk.NOMORGIRO='" + txtKriteria.getText() + "' ";
            }
        } else if (cboKriteria.getSelectedIndex() == 1) {
            sql += "AND gk.STATUS='" + CboStatCari.getSelectedIndex() + "' ";
        } else if (cboKriteria.getSelectedIndex() == 2) {
            sql += "AND lower(sp.NAMA) like '%" + txtKriteria.getText() + "%' ";
        }

        try {
            Connection c = koneksi.getKoneksiJ();
            rows = ExecuteQuery.Query(c, sql);
            c.close();
            String header[] = {"ID", "NOMOR GIRO", "SUPPLIER", "TGL GIRO", "JATUH TEMPO", "JUMLAH", "STATUS"};
            ArrayTable a;
            a = new ArrayTable(header, rows);
            jScrollPane1.getViewport().remove(tabelGiroKeluar);
            tabelGiroKeluar = new JTable(a);
            tabelGiroKeluar.getColumnModel().getColumn(5).setCellRenderer(new DecimalFormatRenderer());
            tabelGiroKeluar.setFont(new Font("Tahoma", Font.BOLD, 12));

            jScrollPane1.getViewport().add(tabelGiroKeluar);
            jScrollPane1.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(GiroKeluarBayarView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
