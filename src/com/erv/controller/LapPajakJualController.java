/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.h2.tools.Csv;
import com.erv.view.FormLapPajakBeli;
import com.erv.view.FormLapPajakJual;

/**
 *
 * @author USER
 */
public class LapPajakJualController {

    Connection con;
    FormLapPajakJual view;

    public LapPajakJualController(Connection con, FormLapPajakJual view) {
        this.con = con;
        this.view = view;
    }

    public void toCsv(String filename) {
        try {
            String tahun = view.getTxtTahun().getText();
            String sql="";
            if (view.getCboJenis().getSelectedIndex() == 0) {
                sql = "select MONTHNAME(jual.TANGGAL) as BULAN,jual.FAKTUR, "
                        + "jual.TANGGAL as TANGGAL, pel.NAMA as PELANGGAN, "
                        + "pel.ALAMAT, br.NAMABARANG, rjual.JUMLAH, rjual.SATUAN, "
                        + "rjual.HARGA, rjual.DISKON, rjual.TOTAL, "
                        + "case jual.CASH when 0 then 'Tunai' when 1 then 'Kredit' when 2 then 'Bank' end as CARA_BAYAR "
                        + "from PENJUALAN as jual "
                        + "inner join RINCIPENJUALAN rjual on jual.id = rjual.IDPENJUALAN "
                        + "inner join PELANGGAN pel on jual.KODEPELANGGAN = pel.KODEPELANGGAN "
                        + "inner join BARANG br on rjual.KODEBARANG = br.KODEBARANG "
                        + "where rjual.BONUS<>'Bonus' and jual.PPN=0 and year(jual.TANGGAL) = "+tahun+"  "
                        + "ORDER BY jual.FAKTUR "
                        + "";
            } else {
                sql = "select MONTHNAME(jual.TANGGAL) as BULAN,jual.FAKTUR, "
                        + "jual.TANGGAL as TANGGAL, pel.NAMA as PELANGGAN, "
                        + "pel.NPWP, br.NAMABARANG, rjual.JUMLAH, rjual.SATUAN, "
                        + "rjual.HARGA, rjual.DISKON, rjual.TOTAL, "
                        + "case jual.CASH when 0 then 'Tunai' when 1 then 'Kredit' when 2 then 'Bank' end as CARA_BAYAR, rjual.PPN "
                        + "from PENJUALAN as jual "
                        + "inner join RINCIPENJUALAN rjual on jual.id = rjual.IDPENJUALAN "
                        + "inner join PELANGGAN pel on jual.KODEPELANGGAN = pel.KODEPELANGGAN "
                        + "inner join BARANG br on rjual.KODEBARANG = br.KODEBARANG "
                        + "where rjual.BONUS<>'Bonus' and jual.PPN<>0 and year(jual.TANGGAL) = "+tahun+"  "
                        + "ORDER BY jual.FAKTUR "
                        + "";
            }
            Csv csv = new Csv();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            csv.write(filename, rs,null);
            JOptionPane.showMessageDialog(view, "Export Berhasil");
        } catch (SQLException ex) {
            Logger.getLogger(LapPajakJualController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
