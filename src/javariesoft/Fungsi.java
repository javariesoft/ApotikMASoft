/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javariesoft;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author TI-PNP
 */
public class Fungsi {
    public static boolean cekperiodeAda(Connection c, String bul) throws SQLException {
        //String periode = thn + "." + bln;
        boolean ada = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + bul + "'");
        if (rs.next()) {
            if (rs.getString(1) != null) {
                ada = true;
            }
        }
        rs.close();
        s.close();
        return ada;
    }

    public static boolean cekperiode(Connection c, String periode) throws SQLException {
        //String periode = thn + "." + bln;
        boolean hasil1 = false;
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from KONTROLPERIODE where PERIODE='" + periode + "' and STATUSSTOK='1'");

        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil1 = true;
            }
        }
        rs.close();
        s.close();
        return hasil1;
    }
    
    public static double getHargaKini(Connection c, String kdplg, String kdbrg, String kdsatuan) throws SQLException {
        double harga = 0.0;
        String sql = "select max(p.id) from RINCIPENJUALAN rp inner join PENJUALAN p on rp.IDPENJUALAN = p.ID\n"
                + "where p.KODEPELANGGAN='" + kdplg + "' and rp.KODEBARANG='" + kdbrg + "'";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        int idjual = 0;
        if (rs.next()) {
            idjual = rs.getInt(1);
        }
        //System.out.print("err"+idjual);
        if (idjual > 0) {
            String sql1 = "select rp.harga from RINCIPENJUALAN rp inner join PENJUALAN p on rp.IDPENJUALAN = p.ID\n"
                    + "where rp.idpenjualan=" + idjual + " and p.KODEPELANGGAN='" + kdplg + "' and rp.KODEBARANG='" + kdbrg + "' and rp.satuan='"+ kdsatuan +"'";
            //System.out.print("err"+sql1);
            Statement s1 = c.createStatement();
            ResultSet rs1 = s1.executeQuery(sql1);
            if (rs1.next()) {
                harga = rs1.getDouble(1);
            }
        }
        return harga;
    }
    
    public static double getDiskonItemJualKini(Connection c, String kdplg, String kdbrg, String kdsatuan) throws SQLException {
        double diskonItemPersen = 0.0;
        String sql = "select max(p.id) from RINCIPENJUALAN rp inner join PENJUALAN p on rp.IDPENJUALAN = p.ID\n"
                + "where p.KODEPELANGGAN='" + kdplg + "' and rp.KODEBARANG='" + kdbrg + "'";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        int idjual = 0;
        if (rs.next()) {
            idjual = rs.getInt(1);
        }
        //System.out.print("err"+idjual);
        if (idjual > 0) {
            String sql1 = "select rp.diskonp from RINCIPENJUALAN rp inner join PENJUALAN p on rp.IDPENJUALAN = p.ID\n"
                    + "where rp.idpenjualan=" + idjual + " and p.KODEPELANGGAN='" + kdplg + "' and rp.KODEBARANG='" + kdbrg + "' and rp.satuan='"+ kdsatuan +"'";
            //System.out.print("err"+sql1);
            Statement s1 = c.createStatement();
            ResultSet rs1 = s1.executeQuery(sql1);
            if (rs1.next()) {
                diskonItemPersen = rs1.getDouble(1);
            }
        }
        return diskonItemPersen;
    }
    
    public static double getHargaBeliAkhir(Connection c, String kdsupp, String kdbrg, String kdsatuan) throws SQLException {
        double hargabl = 0.0;
        String sql = "select max(p.id) from RINCIPEMBELIAN rp inner join PEMBELIAN p on rp.IDPEMBELIAN = p.ID "
                + "where p.IDSUPPLIER='"+ kdsupp +"' and rp.KODEBARANG='"+ kdbrg +"'";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        int idjual = 0;
        if (rs.next()) {
            idjual = rs.getInt(1);
        }
        //System.out.print("err"+idjual);
        if (idjual > 0) {
            String sql1 = "select rp.harga from RINCIPEMBELIAN rp inner join PEMBELIAN p on rp.IDPEMBELIAN = p.ID\n"
                    + "where rp.IDPEMBELIAN=" + idjual + " and p.IDSUPPLIER='" + kdsupp + "' and rp.KODEBARANG='" + kdbrg + "' and rp.satuan='"+ kdsatuan +"'";
            //System.out.print("err"+sql1);
            Statement s1 = c.createStatement();
            ResultSet rs1 = s1.executeQuery(sql1);
            if (rs1.next()) {
                hargabl = rs1.getDouble(1);
            }
        }
        return hargabl;
    }
    
    public static double getDiskonItemBeliAkhir(Connection c, String kdsupp, String kdbrg, String kdsatuan) throws SQLException {
        double diskonItemBeli = 0.0;
        String sql = "select max(p.id) from RINCIPEMBELIAN rp inner join PEMBELIAN p on rp.IDPEMBELIAN = p.ID "
                + "where p.IDSUPPLIER='"+ kdsupp +"' and rp.KODEBARANG='"+ kdbrg +"'";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        int idjual = 0;
        if (rs.next()) {
            idjual = rs.getInt(1);
        }
        //System.out.print("err"+idjual);
        if (idjual > 0) {
            String sql1 = "select rp.diskonp from RINCIPEMBELIAN rp inner join PEMBELIAN p on rp.IDPEMBELIAN = p.ID\n"
                    + "where rp.IDPEMBELIAN=" + idjual + " and p.IDSUPPLIER='" + kdsupp + "' and rp.KODEBARANG='" + kdbrg + "' and rp.satuan='"+ kdsatuan +"'";
            //System.out.print("err"+sql1);
            Statement s1 = c.createStatement();
            ResultSet rs1 = s1.executeQuery(sql1);
            if (rs1.next()) {
                diskonItemBeli = rs1.getDouble(1);
            }
        }
        return diskonItemBeli;
    }
}
