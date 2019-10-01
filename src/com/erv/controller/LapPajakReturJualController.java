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
import com.erv.view.FormLapPajakReturJual;

/**
 *
 * @author USER
 */
public class LapPajakReturJualController {

    Connection con;
    FormLapPajakReturJual view;

    public LapPajakReturJualController(Connection con, FormLapPajakReturJual view) {
        this.con = con;
        this.view = view;
    }

    public void toCsv(String filename) {
        try {
            String tahun = view.getTxtTahun().getText();
            String sql="";
            if (view.getCboJenis().getSelectedIndex() == 0) { 
                sql = "select MONTHNAME(rtjual.TANGGAL) as BULAN, "
                        + "rtjual.KODERETUR , "
                        + "rtjual.TANGGAL as TANGGAL, "
                        + "pel.NAMA as PELANGGAN, "
                        + "pel.ALAMAT, "
                        + "br.NAMABARANG, "
                        + "rtjualrinci.JUMLAH, "
                        + "rtjualrinci.SATUAN, "
                        + "rtjualrinci.HARGA, rtjualrinci.DISKON, rtjualrinci.TOTAL "
                        + "from RETUR as rtjual "
                        + "inner join RETURRINCI rtjualrinci on rtjual.id = rtjualrinci.IDRETUR "
                        + "inner join PELANGGAN pel on rtjual.KODEPELANGGAN = pel.KODEPELANGGAN "
                        + "inner join BARANG br on rtjualrinci.KODEBARANG = br.KODEBARANG "
                        + "where rtjual.STATUS=1 AND rtjual.TOTALPPN=0 AND year(rtjual.TANGGAL) = "+tahun+"  "
                        + "";
            } else {              
                sql = "select MONTHNAME(rtjual.TANGGAL) as BULAN, "
                        + "rtjual.KODERETUR , "
                        + "rtjual.TANGGAL as TANGGAL, "
                        + "pel.NAMA as PELANGGAN, "
                        + "pel.NPWP, "
                        + "br.NAMABARANG, "
                        + "rtjualrinci.JUMLAH, "
                        + "rtjualrinci.SATUAN, "
                        + "rtjualrinci.HARGA, rtjualrinci.DISKON, rtjualrinci.TOTAL, rtjualrinci.PPN "
                        + "from RETUR as rtjual "
                        + "inner join RETURRINCI rtjualrinci on rtjual.id = rtjualrinci.IDRETUR "
                        + "inner join PELANGGAN pel on rtjual.KODEPELANGGAN = pel.KODEPELANGGAN "
                        + "inner join BARANG br on rtjualrinci.KODEBARANG = br.KODEBARANG "
                        + "where rtjual.STATUS=1 AND rtjual.TOTALPPN<>0 AND year(rtjual.TANGGAL) = "+tahun+"  "
                        + "";
            }
            Csv csv = new Csv();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            csv.write(filename, rs,null);
            JOptionPane.showMessageDialog(view, "Export Berhasil");
        } catch (SQLException ex) {
            Logger.getLogger(LapPajakReturJualController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
