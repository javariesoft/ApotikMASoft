/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.controller;

import com.erv.database.Database;
import com.erv.db.KontrolperiodeDao;
import com.erv.db.hutangDao;
import com.erv.db.koneksi;
import com.erv.function.Util;
import com.erv.model.GiroKeluarModel;
import com.erv.model.Girokeluar;
import com.erv.model.GirokeluarKey;
import com.erv.model.dao.GirokeluarDAO;
import com.erv.model.pesan;
import com.erv.view.GiroKeluarBayarView;
import com.erv.view.GiroKeluarView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ervan
 */
public class GiroKeluarController {

    private GiroKeluarModel model;

    public void setModel(GiroKeluarModel model) {
        this.model = model;
    }

    public void resetGiroKeluar() throws ClassNotFoundException, SQLException {
        model.resetGiroKeluar();
    }

    public void insertGiroKeluar(Connection c, GiroKeluarView view) {
        try {
            String nomorgiro = view.getTxtNomorGiro().getText();
            String jumlah = view.getTxtJumlah().getValue().toString();
            String tglgiro = view.getDcTanggalGiro().getText();
            String tgljtempo = view.getDcTanggalJTempo().getText();
            String namapenerima = view.getTxtNamaPenerima().getText();
            String kodesupplier = view.getTxtSupplier().getText();
            String bank = view.getCboBank().getSelectedItem().toString();
            String bankpenerima = view.getTxtBankPenerima().getText();
            String bp[] = Util.split(bank, "-");
            String tgal[] = Util.split(tglgiro, "-");
            String tgljtemposplit[] = Util.split(tgljtempo, "-");
            String per = tgal[0] + "." + Integer.parseInt(tgal[1]);
            double jumlahHutang = 0;
            boolean statusBayar = false;
            try {
                jumlahHutang = hutangDao.getJumlahHutangSupplier(c, kodesupplier);
                double jumlahBayar = Double.parseDouble(jumlah);
                if (jumlahBayar > jumlahHutang) {

                    statusBayar = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GiroKeluarController.class.getName()).log(Level.SEVERE, null, ex);
            } 
            if (nomorgiro.trim().equals("")) {
                JOptionPane.showMessageDialog(view, "Nomor Giro masih kosong");
            } else if (jumlah.trim().equals("0")) {
                JOptionPane.showMessageDialog(view, "Nilai Giro masih kosong");
            } else if (namapenerima.trim().equals("")) {
                JOptionPane.showMessageDialog(view, "Nama Penerima Masih Kosong");
            } else if (kodesupplier.trim().equals("")) {
                JOptionPane.showMessageDialog(view, "Kode Pelanggan Masih Kosong");
            } else if (bankpenerima.trim().equals("")) {
                JOptionPane.showMessageDialog(view, "Bank Penerima Masih Kosong");
            } else if (statusBayar) {
                JOptionPane.showMessageDialog(view, "Jumlah Bayar besar dari jumlah hutang");
            } else if (jumlahHutang == 0) {
                JOptionPane.showMessageDialog(view, "Hutang Tidak Ada");
            } else if (!KontrolperiodeDao.cekperiodeAda(c, per)) {
                JOptionPane.showMessageDialog(view, "Transaksi Untuk Periode Ini Belum Dibuka.. !");
            } else if (!KontrolperiodeDao.cekperiode(c, per)) {
                JOptionPane.showMessageDialog(view, "Transaksi Untuk Periode Ini Sudah Di Tutup.. !");
            } else {
                model.setNomorgiro(nomorgiro);
                model.setTglgiro(Util.getDate(Integer.parseInt(tgal[2]), Integer.parseInt(tgal[1]), Integer.parseInt(tgal[0])));
                model.setTgljtempo(Util.getDate(Integer.parseInt(tgljtemposplit[2]), Integer.parseInt(tgljtemposplit[1]), Integer.parseInt(tgljtemposplit[0])));
                model.setJumlah(Double.parseDouble(jumlah));
                model.setNamapenerima(namapenerima);
                model.setStatus(view.getCboStatus().getSelectedIndex());
                model.setKodesupplier(kodesupplier);
                model.setBanktujuan(bankpenerima);
                model.setIdbank((bp[0]));
                try {
                    model.insert(c);
                    JOptionPane.showMessageDialog(view, "Giro berhasil dimasukkan");
                    model.resetGiroKeluar();
                } catch (Throwable throwable) {
                    JOptionPane.showMessageDialog(view, new Object[]{
                        "Terjadi error di database dengan pesan :",
                        throwable.getMessage()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }

    public void updateGiroKeluar(Connection c, GiroKeluarView view) {
        if (view.getTabelGiroKeluar().getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris yang akan diubah");
            return;
        }
        Integer id = view.getId();
        String nomorgiro = view.getTxtNomorGiro().getText();
        String jumlah = view.getTxtJumlah().getValue().toString();
        String tglgiro = view.getDcTanggalGiro().getText();
        String tgljtempo = view.getDcTanggalJTempo().getText();
        String namapenerima = view.getTxtNamaPenerima().getText();
        String kodesupplier = view.getTxtSupplier().getText();
        String bank = view.getCboBank().getSelectedItem().toString();
        String bankpenerima = view.getTxtBankPenerima().getText();
        String bp[] = Util.split(bank, "-");
        if (nomorgiro.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Nomor Giro masih kosong");
        } else if (jumlah.trim().equals("0")) {
            JOptionPane.showMessageDialog(view, "Nilai Giro masih kosong");
        } else if (namapenerima.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Nama Penerima Masih Kosong");
        } else if (kodesupplier.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Kode Pelanggan Masih Kosong");
        } else if (bankpenerima.trim().equals("")) {
            JOptionPane.showMessageDialog(view, "Bank Penerima Masih Kosong");
        } else {
            model.setId(id);
            model.setNomorgiro(nomorgiro);
            model.setTglgiro(view.getDcTanggalGiro().getSelectedDate().getTime());
            model.setTgljtempo(view.getDcTanggalJTempo().getSelectedDate().getTime());
            model.setJumlah(Double.parseDouble(jumlah));
            model.setNamapenerima(namapenerima);
            model.setStatus(view.getCboStatus().getSelectedIndex());
            model.setKodesupplier(kodesupplier);
            model.setBanktujuan(bankpenerima);
            model.setIdbank((bp[0]));
            try {
                model.update(c);
                JOptionPane.showMessageDialog(view, "Giro berhasil Di Update");
                model.resetGiroKeluar();
            } catch (Throwable throwable) {
                JOptionPane.showMessageDialog(view, new Object[]{
                    "Terjadi error di database dengan pesan :",
                    throwable.getMessage()
                });
            }
        }
    }

    public Girokeluar getGiroKeluar(int id) {
        Girokeluar girokeluar = null;
        try {
            girokeluar = model.get(id);
        } catch (Throwable t) {
            t.printStackTrace();
            JOptionPane.showMessageDialog(null, new Object[]{
                "Terjadi error di database dengan pesan :",
                t.getMessage()
            });
        }
        return girokeluar;
    }

    public void deleteGiroKeluar(GiroKeluarView view) {

        if (view.getTabelGiroKeluar().getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris yang akan dihapus");
            return;
        }

        if (JOptionPane.showConfirmDialog(view, "Anda yakin akan menghapus?") == JOptionPane.OK_OPTION) {
            Integer id = view.getId();
            model.setId(id);
            try {
                model.delete();
                JOptionPane.showMessageDialog(view, "Cabang berhasil dihapus");
                model.resetGiroKeluar();
            } catch (Throwable t) {
                t.printStackTrace();
                JOptionPane.showMessageDialog(view, new Object[]{
                    "Terjadi error di database dengan pesan :",
                    t.getMessage()
                });
            }
        }
    }

    public void angsuranHutang(GiroKeluarBayarView view) {
        String stat = view.getTabelData().getValueAt(view.getTabelData().getSelectedRow(), 5).toString();
        String tglCair = view.getTanggalCair().getText();
        if (stat.equals("Cair")) {
            JOptionPane.showMessageDialog(view, "Giro Sudah Cair");
        } else {
            try {
                String id = view.getTabelData().getValueAt(view.getTabelData().getSelectedRow(), 0).toString();
                GirokeluarDAO girokeluarDAO = Database.getGiroKeluarDao();
                GirokeluarKey key = new GirokeluarKey();
                key.setId(Integer.parseInt(id));
                Girokeluar girokeluar = girokeluarDAO.load(key);
                girokeluarDAO.angsuranHutang(girokeluar,tglCair);
                JOptionPane.showMessageDialog(view, "Angsuran Berhasil");
            } catch (Throwable t) {
                t.printStackTrace();
                JOptionPane.showMessageDialog(view, new Object[]{
                    "Terjadi error di database dengan pesan :",
                    t.getMessage()
                });
            }
        }
    }

    public void updateStatusBatal(GiroKeluarBayarView view) {
        String stat = view.getTabelData().getValueAt(view.getTabelData().getSelectedRow(), 5).toString();
        if (stat.equals("Cair")) {
            JOptionPane.showMessageDialog(view, "Giro Sudah Cair");
        } else {
            try {
                String id = view.getTabelData().getValueAt(view.getTabelData().getSelectedRow(), 0).toString();
                GirokeluarDAO girokeluarDAO = Database.getGiroKeluarDao();
                GirokeluarKey key = new GirokeluarKey();
                key.setId(Integer.parseInt(id));
                Girokeluar girokeluar = girokeluarDAO.load(key);
                girokeluar.setStatus(2);
                girokeluarDAO.update(girokeluar);
                JOptionPane.showMessageDialog(view, "Pembatalan Giro Berhasil");
            } catch (Throwable t) {
                t.printStackTrace();
                JOptionPane.showMessageDialog(view, new Object[]{
                    "Terjadi error di database dengan pesan :",
                    t.getMessage()
                });
            }
        }
    }
}
