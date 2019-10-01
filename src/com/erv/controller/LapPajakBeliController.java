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

/**
 *
 * @author USER
 */
public class LapPajakBeliController {

    Connection con;
    FormLapPajakBeli view;

    public LapPajakBeliController(Connection con, FormLapPajakBeli view) {
        this.con = con;
        this.view = view;
    }

    public void toCsv(String filename) {
        try {
            String tahun = view.getTxtTahun().getText();
            String sql="";
            if (view.getCboJenis().getSelectedIndex() == 0) {
                /*sql = "select MONTHNAME(st.TANGGAL) as BULAN,beli.NOFAKTUR, st.TANGGAL as TGL_BELI, sp.NAMA as NAMA_SUPPLIER,  "
                        + "sp.ALAMAT, br.NAMABARANG, rbeli.JUMLAH, rbeli.SATUAN, rbeli.HARGA, rbeli.DISKON, rbeli.TOTAL, "
                        + "case beli.CASH when 0 then 'Tunai' when 1 then 'Kredit' when 2 then 'Bank' end as CARA_BAYAR "
                        + "from STOK st "
                        + "inner join PEMBELIAN beli on beli.ID = st.IDPENJUALAN "
                        + "inner join RINCIPEMBELIAN rbeli on beli.id = rbeli.IDPEMBELIAN   "
                        + "inner join SUPPLIER sp on beli.IDSUPPLIER = sp.IDSUPPLIER  "
                        + "inner join BARANG br on st.KODEBARANG = br.KODEBARANG and rbeli.KODEBARANG = br.KODEBARANG "
                        + "where rbeli.BONUS<>'Bonus' AND year(st.TANGGAL) = " + tahun + " and st.KODETRANS='B' and beli.pajak=0 "
                        + "";*/
                sql = "select MONTHNAME(beli.TGLMASUK) as BULAN,beli.NOFAKTUR, "
                        + "beli.TGLMASUK as TGL_BELI, sp.NAMA as NAMA_SUPPLIER,"
                        + "sp.ALAMAT, br.NAMABARANG, rbeli.JUMLAH, rbeli.SATUAN,"
                        + "rbeli.HARGA, rbeli.DISKON, rbeli.TOTAL, "
                        + "case beli.CASH when 0 then 'Tunai' when 1 then 'Kredit' "
                        + "when 2 then 'Bank' end as CARA_BAYAR "
                        + "from PEMBELIAN as beli "
                        + "inner join RINCIPEMBELIAN rbeli on beli.id = rbeli.IDPEMBELIAN  "
                        + "inner join SUPPLIER sp on beli.IDSUPPLIER = sp.IDSUPPLIER  "
                        + "inner join BARANG br on rbeli.KODEBARANG = br.KODEBARANG   "
                        + "where rbeli.BONUS<>'Bonus' and beli.pajak=0 and year(beli.TGLMASUK) =  "+tahun+"  "
                        + "";
            } else {
                /*sql = "select MONTHNAME(st.TANGGAL) as BULAN,beli.NOFAKTUR, st.TANGGAL as TGL_BELI, sp.NAMA as NAMA_SUPPLIER,  "
                        + "sp.NPWP, br.NAMABARANG, rbeli.JUMLAH, rbeli.SATUAN, rbeli.HARGA, rbeli.DISKON, rbeli.TOTAL,  "
                        + "case beli.CASH when 0 then 'Tunai' when 1 then 'Kredit' when 2 then 'Bank' end as CARA_BAYAR, rbeli.PPN "
                        + "from STOK st "
                        + "inner join PEMBELIAN beli on beli.ID = st.IDPENJUALAN "
                        + "inner join RINCIPEMBELIAN rbeli on beli.id = rbeli.IDPEMBELIAN   "
                        + "inner join SUPPLIER sp on beli.IDSUPPLIER = sp.IDSUPPLIER  "
                        + "inner join BARANG br on st.KODEBARANG = br.KODEBARANG and rbeli.KODEBARANG = br.KODEBARANG "
                        + "where rbeli.BONUS<>'Bonus' AND year(st.TANGGAL) = "+tahun+" and st.KODETRANS='B' and beli.pajak<>0 "
                        + "";*/
                sql = "select MONTHNAME(beli.TGLMASUK) as BULAN,beli.NOFAKTUR, "
                        + "beli.TGLMASUK as TGL_BELI, sp.NAMA as NAMA_SUPPLIER, sp.NPWP,"
                        + "br.NAMABARANG, rbeli.JUMLAH, rbeli.SATUAN,"
                        + "rbeli.HARGA, rbeli.DISKON, rbeli.TOTAL, "
                        + "case beli.CASH when 0 then 'Tunai' when 1 then 'Kredit' "
                        + "when 2 then 'Bank' end as CARA_BAYAR, rbeli.PPN "
                        + "from PEMBELIAN as beli "
                        + "inner join RINCIPEMBELIAN rbeli on beli.id = rbeli.IDPEMBELIAN  "
                        + "inner join SUPPLIER sp on beli.IDSUPPLIER = sp.IDSUPPLIER  "
                        + "inner join BARANG br on rbeli.KODEBARANG = br.KODEBARANG   "
                        + "where rbeli.BONUS<>'Bonus' and beli.pajak<>0 and year(beli.TGLMASUK) =  "+tahun+"  "
                        + "";
            }
            Csv csv = new Csv();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            csv.write(filename, rs,null);
            JOptionPane.showMessageDialog(view, "Export Berhasil");
        } catch (SQLException ex) {
            Logger.getLogger(LapPajakBeliController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
