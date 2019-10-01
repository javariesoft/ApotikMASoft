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
import com.erv.view.FormLapPajakReturBeli;

/**
 *
 * @author USER
 */
public class LapPajakReturBeliController {

    Connection con;
    FormLapPajakReturBeli view;

    public LapPajakReturBeliController(Connection con, FormLapPajakReturBeli view) {
        this.con = con;
        this.view = view;
    }

    public void toCsv(String filename) {
        try {
            String tahun = view.getTxtTahun().getText();
            String sql="";
            if (view.getCboJenis().getSelectedIndex() == 0) { 
                sql = "select MONTHNAME(rtbeli.TANGGAL) as BULAN, "
                        + "rtbeli.KODERETURBELI , "
                        + "rtbeli.TANGGAL as TANGGAL, "
                        + "sp.NAMA as NAMA_SUPPLIER, "
                        + "sp.ALAMAT, "
                        + "br.NAMABARANG, "
                        + "rtbelirinci.JUMLAH, "
                        + "rtbelirinci.SATUAN, "
                        + "rtbelirinci.HARGA, rtbelirinci.DISKON, rtbelirinci.TOTAL "
                        + "from RETURBELI as rtbeli "
                        + "inner join RETURBELIRINCI rtbelirinci on rtbeli.id = rtbelirinci.IDRETURBELI "
                        + "inner join SUPPLIER sp on rtbeli.IDSUPPLIER = sp.IDSUPPLIER "
                        + "inner join BARANG br on rtbelirinci.KODEBARANG = br.KODEBARANG "
                        + "where rtbeli.STATUS=1 AND rtbeli.TOTALPPN=0 AND year(rtbeli.TANGGAL) = "+tahun+"  "
                        + "";
            } else {              
                sql = "select MONTHNAME(rtbeli.TANGGAL) as BULAN, "
                        + "rtbeli.KODERETURBELI , "
                        + "rtbeli.TANGGAL as TANGGAL, "
                        + "sp.NAMA as NAMA_SUPPLIER, "
                        + "sp.NPWP, "
                        + "br.NAMABARANG, "
                        + "rtbelirinci.JUMLAH, "
                        + "rtbelirinci.SATUAN, "
                        + "rtbelirinci.HARGA, rtbelirinci.DISKON, rtbelirinci.TOTAL, rtbelirinci.PPN "
                        + "from RETURBELI as rtbeli "
                        + "inner join RETURBELIRINCI rtbelirinci on rtbeli.id = rtbelirinci.IDRETURBELI "
                        + "inner join SUPPLIER sp on rtbeli.IDSUPPLIER = sp.IDSUPPLIER "
                        + "inner join BARANG br on rtbelirinci.KODEBARANG = br.KODEBARANG "
                        + "where rtbeli.STATUS=1 AND rtbeli.TOTALPPN<>0 AND year(rtbeli.TANGGAL) = "+tahun+"  "
                        + "";
            }
            Csv csv = new Csv();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            csv.write(filename, rs,null);
            JOptionPane.showMessageDialog(view, "Export Berhasil");
        } catch (SQLException ex) {
            Logger.getLogger(LapPajakReturBeliController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
