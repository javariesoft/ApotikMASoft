/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.controller;

import com.erv.db.KontrolperiodeDao;
import com.erv.db.piutangDao;
import com.erv.function.Util;
import com.erv.model.Giro;
import com.erv.model.GiroModel;
import com.erv.view.GiroBayarView;
import com.erv.view.GiroView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ervan
 */
public class GiroController {

    private GiroModel model;

    public void setModel(GiroModel model) {
        this.model = model;
    }

    public void resetGiro() throws ClassNotFoundException, SQLException {
        model.resetGiro();
    }

    public void insertGiro(Connection c, GiroView view) {
        String nomorgiro = view.getTxtNomorGiro().getText();
        String jumlah = view.getTxtJumlah().getValue().toString();
        String tglgiro = view.getDcTanggalGiro().getText();
        String tgljtempo = view.getDcTanggalJTempo().getText();
        String namapenerima = view.getTxtNamaPenerima().getText();
        String kodepelanggan = view.getTxtPemilikGiro().getText();
        String bankasal = view.getTxtBankAsal().getText();
        String bankpenerima = view.getCboBankPenerima().getSelectedItem().toString();
        String bp[] = Util.split(bankpenerima, "-");
        double jumPiutang = 0;
        boolean statusBayar = false;
        try {
            jumPiutang = piutangDao.getJumlahPiutangPelanggan(c, kodepelanggan);
            double jumlahBayar = Double.parseDouble(jumlah);
            if (jumlahBayar > jumPiutang) {
                statusBayar = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GiroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nomorgiro.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Nomor Giro masih kosong");
        } else if (jumlah.trim().equals("0")) {
            JOptionPane.showMessageDialog(view, "Nilai Giro masih kosong");
        } else if (namapenerima.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Nama Penerima Masih Kosong");
        } else if (kodepelanggan.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Kode Pelanggan Masih Kosong");
        } else if (bankasal.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Bank Asal Masih Kosong");
        } else if (statusBayar) {
            JOptionPane.showMessageDialog(view, "Jumlah Bayar Besar dari jumlah Piutang");
        } else {
            try {
                String tgal[] = Util.split(tglgiro, "-");
                String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
                boolean c1 = KontrolperiodeDao.cekperiodeAda(c, per);
                boolean c2 = KontrolperiodeDao.cekperiode(c, per);
                if (c1) {
                    if (c2) {
                        model.setNOMORGIRO(nomorgiro);
                        model.setTGLGIRO(tglgiro);
                        model.setTGLJTEMPO(tgljtempo);
                        model.setJUMLAH(Double.parseDouble(jumlah));
                        model.setNAMAPENERIMA(namapenerima);
                        model.setSTATUS(view.getCboStatus().getSelectedIndex());
                        model.setKODEPELANGGAN(kodepelanggan);
                        model.setBANKASAL(bankasal);
                        model.setIDBANK(Integer.parseInt(bp[0]));
                        model.insert(c);
                        JOptionPane.showMessageDialog(view, "Giro berhasil dimasukkan");
                        model.resetGiro();
                    } else {
                        JOptionPane.showMessageDialog(view, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
                }
            } catch (Throwable throwable) {
                JOptionPane.showMessageDialog(view, new Object[]{
                    "Terjadi error di database dengan pesan :",
                    throwable.getMessage()
                });
            }
        }
    }

    public void updateGiro(Connection c, GiroView view) {
        String stat = view.getTabelGiro().getValueAt(view.getTabelGiro().getSelectedRow(), 6).toString();
        if (view.getTabelGiro().getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris yang akan diubah");
            return;
        } 
        if(stat.equals("Cair")){
            JOptionPane.showMessageDialog(view, "Giro Sudah Cair");
            return;
        }    
        
        Integer id = view.getId();
        String nomorgiro = view.getTxtNomorGiro().getText();
        String jumlah = view.getTxtJumlah().getValue().toString();
        String tglgiro = view.getDcTanggalGiro().getText();
        String tgljtempo = view.getDcTanggalJTempo().getText();
        String namapenerima = view.getTxtNamaPenerima().getText();
        String kodepelanggan = view.getTxtPemilikGiro().getText();
        String bankasal = view.getTxtBankAsal().getText();
        String bankpenerima = view.getCboBankPenerima().getSelectedItem().toString();
        String bp[] = Util.split(bankpenerima, "-");

        if (nomorgiro.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Nomor Giro masih kosong");
        } else if (jumlah.trim().equals("0")) {
            JOptionPane.showMessageDialog(view, "Nilai Giro masih kosong");
        } else if (namapenerima.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Nama Penerima Masih Kosong");
        } else if (kodepelanggan.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Kode Pelanggan Masih Kosong");
        } else if (bankasal.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Bank Asal Masih Kosong");
        } else {
            model.setID(id);
            model.setNOMORGIRO(nomorgiro);
            model.setTGLGIRO(tglgiro);
            model.setTGLJTEMPO(tgljtempo);
            model.setJUMLAH(Double.parseDouble(jumlah));
            model.setNAMAPENERIMA(namapenerima);
            model.setSTATUS(view.getCboStatus().getSelectedIndex());
            model.setKODEPELANGGAN(kodepelanggan);
            model.setBANKASAL(bankasal);
            model.setIDBANK(Integer.parseInt(bp[0]));
            try {
                model.update(c);
                JOptionPane.showMessageDialog(view, "Giro berhasil di Edit");
                //model.resetGiro();
            } catch (Throwable throwable) {
                JOptionPane.showMessageDialog(view, new Object[]{
                    "Terjadi error di database dengan pesan :",
                    throwable.getMessage()
                });
            }
        }
    }

    public Giro getGiro(Connection c, int id) {
        Giro giro = null;
        try {
            giro = model.getGiro(c, id);
        } catch (Throwable t) {
            t.printStackTrace();
            JOptionPane.showMessageDialog(null, new Object[]{
                "Terjadi error di database dengan pesan :",
                t.getMessage()
            });
        }
        return giro;
    }

    public void deleteGiro(Connection c, GiroView view) {

        if (view.getTabelGiro().getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris yang akan dihapus");
            return;
        }

        if (JOptionPane.showConfirmDialog(view, "Anda yakin akan menghapus?") == JOptionPane.OK_OPTION) {
            Integer id = view.getId();
            model.setID(id);
            try {
                model.delete(c);
                JOptionPane.showMessageDialog(view, "Cabang berhasil dihapus");
                model.resetGiro();
            } catch (Throwable t) {
                t.printStackTrace();
                JOptionPane.showMessageDialog(view, new Object[]{
                    "Terjadi error di database dengan pesan :",
                    t.getMessage()
                });
            }
        }
    }

    public void angsuranPiutang(Connection c, GiroBayarView view) {
        String stat = view.getTabelData().getValueAt(view.getTabelData().getSelectedRow(), 5).toString();
        String tglCair = view.getTanggalCair().getText();
        if (stat.equals("Cair")) {
            JOptionPane.showMessageDialog(view, "Giro Sudah Cair");
        } else {
            try {
                String id = view.getTabelData().getValueAt(view.getTabelData().getSelectedRow(), 0).toString();
                model.setID(Integer.parseInt(id));
                model.setTGLCAIR(tglCair);
                model.angsuranPiutang(c);
                JOptionPane.showMessageDialog(view, "Angsuran Berhasil");
            } catch (Throwable t) {
                //t.printStackTrace();
                JOptionPane.showMessageDialog(view, new Object[]{
                    "Terjadi error di database dengan pesan :",
                    t.getMessage()
                });
            }
        }
    }

    public void updateStatusBatal(Connection c, GiroBayarView view) {
        try {
            String stat = view.getTabelData().getValueAt(view.getTabelData().getSelectedRow(), 5).toString();
            if (stat.equals("Cair")) {
                JOptionPane.showMessageDialog(view, "Giro Sudah Cair");
            } else {
                String id = view.getTabelData().getValueAt(view.getTabelData().getSelectedRow(), 0).toString();
                model.setID(Integer.parseInt(id));
                model.updateStatusBatal(c);
                JOptionPane.showMessageDialog(view, "Pembatalan Giro Berhasil");
            }
        } catch (Throwable t) {
            //t.printStackTrace();
            JOptionPane.showMessageDialog(view, new Object[]{
                "Terjadi error dengan pesan :",
                t.getMessage()
            });
        }
    }
}
