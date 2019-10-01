/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import com.erv.db.koneksi;
import com.erv.function.PrintfFormat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author erwadi
 */
public class CetakFakturPanjang {

    DecimalFormat df = new DecimalFormat("###,###,###,###");

    public void cetakPrinter(String file) {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] cmd = {"cmd.exe", "/C", "c:\\temp\\dwoprn.exe" + " " + file};
            rt.exec(cmd);
            System.out.print(cmd);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public void cetak(Connection con, String noFaktur) throws IOException, SQLException {
        String sql = "SELECT\n"
                + "     PENJUALAN.\"ID\" AS PENJUALAN_ID,\n"
                + "     PENJUALAN.\"FAKTUR\" AS PENJUALAN_FAKTUR,\n"
                + "     PENJUALAN.\"TANGGAL\" AS PENJUALAN_TANGGAL,\n"
                + "     PENJUALAN.\"KODEPELANGGAN\" AS PENJUALAN_KODEPELANGGAN,\n"
                + "     PENJUALAN.\"CASH\" AS PENJUALAN_CASH,\n"
                + "     PENJUALAN.\"TGLLUNAS\" AS PENJUALAN_TGLLUNAS,\n"
                + "     PENJUALAN.\"PPN\" AS PENJUALAN_PPN,\n"
                + "     PENJUALAN.\"DP\" AS PENJUALAN_DP,\n"
                + "     PENJUALAN.\"DISKON\" AS PENJUALAN_DISKON,\n"
                + "     RINCIPENJUALAN.\"IDPENJUALAN\" AS RINCIPENJUALAN_IDPENJUALAN,\n"
                + "     RINCIPENJUALAN.\"KODEBARANG\" AS RINCIPENJUALAN_KODEBARANG,\n"
                + "     RINCIPENJUALAN.\"JUMLAH\" AS RINCIPENJUALAN_JUMLAH,\n"
                + "     RINCIPENJUALAN.\"HARGA\" AS RINCIPENJUALAN_HARGA,\n"
                + "     RINCIPENJUALAN.\"DISKON\" AS RINCIPENJUALAN_DISKON,\n"
                + "     RINCIPENJUALAN.\"TOTAL\" AS RINCIPENJUALAN_TOTAL,\n"
                + "     PELANGGAN.\"KODEPELANGGAN\" AS PELANGGAN_KODEPELANGGAN,\n"
                + "     PELANGGAN.\"NAMA\" AS PELANGGAN_NAMA,\n"
                + "     PELANGGAN.\"ALAMAT\" AS PELANGGAN_ALAMAT,\n"
                + "     PELANGGAN.\"HP\" AS PELANGGAN_HP,\n"
                + "     PELANGGAN.\"TGLREG\" AS PELANGGAN_TGLREG,\n"
                + "     PELANGGAN.\"BATASKREDIT\" AS PELANGGAN_BATASKREDIT,\n"
                + "     BARANG.\"KODEBARANG\" AS BARANG_KODEBARANG,\n"
                + "     BARANG.\"NAMABARANG\" AS BARANG_NAMABARANG,\n"
                + "     BARANG.\"SATUAN\" AS BARANG_SATUAN,\n"
                + "     BARANG.\"HARGA\" AS BARANG_HARGA,\n"
                + "     BARANG.\"KODEAKUN\" AS BARANG_KODEAKUN,\n"
                + "     BARANG.\"PENDAPATAN_ACC\" AS BARANG_PENDAPATAN_ACC,\n"
                + "     BARANG.\"COGS_ACC\" AS BARANG_COGS_ACC,\n"
                + "     BARANG.\"COGS\" AS BARANG_COGS,\n"
                + "     BARANG.\"STOK\" AS BARANG_STOK,\n"
                + "     BARANG.\"IDJENIS\" AS BARANG_IDJENIS,\n"
                + "     RINCIPENJUALAN.\"PPN\" AS RINCIPENJUALAN_PPN,\n"
                + "    (RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA) - ((RINCIPENJUALAN.DISKON / 100) * (RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA)) AS TOTDIS,\n"
                + "    ((RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA) - ((RINCIPENJUALAN.DISKON / 100) * (RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA))) * (RINCIPENJUALAN.PPN / 100) as PPNTOT,\n"
                + "    JENISBARANG.JENIS AS JENISBARANG_JENIS,PENJUALAN.IDSALES AS PENJUALAN_IDSALES\n"
                + "\n"
                + "\n"
                + "FROM\n"
                + "     \"PUBLIC\".\"RINCIPENJUALAN\" RINCIPENJUALAN INNER JOIN \"PUBLIC\".\"PENJUALAN\" PENJUALAN ON RINCIPENJUALAN.\"IDPENJUALAN\" = PENJUALAN.\"ID\"\n"
                + "     INNER JOIN \"PUBLIC\".\"PELANGGAN\" PELANGGAN ON PENJUALAN.\"KODEPELANGGAN\" = PELANGGAN.\"KODEPELANGGAN\"\n"
                + "     INNER JOIN \"PUBLIC\".\"BARANG\" BARANG ON RINCIPENJUALAN.\"KODEBARANG\" = BARANG.\"KODEBARANG\"\n"
                + "     INNER JOIN \"PUBLIC\".\"JENISBARANG\" JENISBARANG ON BARANG.\"IDJENIS\" = JENISBARANG.\"ID\"\n"
                + "WHERE\n"
                + "     PENJUALAN.\"FAKTUR\" ='" + 13.002374 + "'";
        String spasi = " ";
        Statement stat = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stat.executeQuery(sql);
        rs.next();
        Writer output = null;
        File file = new File("c:\\temp\\write.txt");
        output = new BufferedWriter(new FileWriter(file));
        //output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write(15);
        String jdl1 = "Kepada: ";
        String jdl2 = "" + rs.getString(17);
        String jdl3 = "" + rs.getString(18);
        String jdl5 = "" + rs.getString(2);    // No Faktur
        String jdl6 = "" + rs.getString(3);      // Tanggal
        String jdl7 = "" + rs.getString(36) + "                                           " + rs.getString(6);     // Jatuh Tempo

        //output.write(new PrintfFormat("%110s").sprintf(spasi) + jdl1+"\r\n");
        output.write(new PrintfFormat("%93s").sprintf(spasi) + jdl2 + "\r\n");
        output.write(new PrintfFormat("%93s").sprintf(spasi) + jdl3 + "\r\n");
        output.write("\r\n");
        output.write("\r\n");
        //output.write("\r\n");
        //output.write("\r\n");
        output.write(new PrintfFormat("%55s").sprintf(spasi) + jdl5 + "\r\n");
        output.write(new PrintfFormat("%55s").sprintf(spasi) + jdl6 + "\r\n");
        output.write(new PrintfFormat("%11s").sprintf(spasi) + jdl7 + "\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        String No = new PrintfFormat("%6s").sprintf("No");              //1
        String KdBrg = new PrintfFormat("%-7s").sprintf(" Kd Brg");       //2
        String NmBrg = new PrintfFormat("%-50s").sprintf(" Nama Barang"); //3
        String Volume = new PrintfFormat("%-10s").sprintf(" Volume");     //4
        String Harga = new PrintfFormat("%-8s").sprintf(" Harga(Rp)");    //5    
        String Disc = new PrintfFormat("%-8s").sprintf(" Disc(%)");       //6
        String Jumlah = new PrintfFormat("%-12s").sprintf(" Jumlah(Rp)"); //7
        //output.write("   ==========================================================================================================" + "\r\n");
        //output.write("   |"+No+"|"+KdBrg+"|"+NmBrg+"|"+Volume+"|"+Harga+"|"+Disc+"|"+Jumlah+"|" + "\r\n");
        //output.write("   ==========================================================================================================" + "\r\n");
//        output.write("\r\n");
//        output.write("\r\n");
        output.write(15);
        rs.beforeFirst();

        int no = 1;
        double total = 0, ppn = 0, totalbersih = 0;
        while (rs.next()) {
            total = total + rs.getDouble(33);
            String nmbrg = rs.getString(23);
            if (nmbrg.length() > 36) {
                nmbrg = nmbrg.substring(0, 36);
            }
            String merk = rs.getString(35);  //merk
            if (merk.length() > 12) {
                merk = merk.substring(0, 12);
            }
            output.write(new PrintfFormat("%6s").sprintf("" + no++) + "    "
                    + new PrintfFormat("%-7s").sprintf(rs.getString(22)) + "       "
                    + new PrintfFormat("%-40s").sprintf(nmbrg) + new PrintfFormat("%-12s").sprintf(merk) + "    "
                    + new PrintfFormat("%6s").sprintf(" " + df.format(Integer.parseInt(rs.getString(12)))) + new PrintfFormat("%4s").sprintf(" " + rs.getString(24)) + "      "
                    + new PrintfFormat("%10s").sprintf(df.format(rs.getDouble(13))) + "    "
                    // + new PrintfFormat("%9s").sprintf(df.format(rs.getDouble(14))) + "|"
                    + new PrintfFormat("%7.2f").sprintf(rs.getDouble(14)) + "       "
                    //+ new PrintfFormat("%11.0f").sprintf(rs.getDouble(33)) + "|"
                    + new PrintfFormat("%12s").sprintf(df.format(rs.getDouble(33))) + ""
                    + "\r\n");
        }
        if (no < 53) {
            for (int a = 0; a < (53 - no); a++) {
                output.write("\r\n");
            }
        }

        ppn = 0.1 * total;
        totalbersih = total + ppn;
        //output.write("   ==========================================================================================================" + "\r\n");
        //output.write("\r\n");
        output.write(15);
        output.write(new PrintfFormat("%15s").sprintf(spasi) + new PrintfFormat("%-110s").sprintf(com.erv.fungsi.Fungsi.bilang((long) totalbersih)) + new PrintfFormat("%12s").sprintf(df.format(total)) + "\r\n");    //Total
        //output.write("\r\n");
        //output.write(new PrintfFormat("%125s").sprintf(spasi)+new PrintfFormat("%12s").sprintf(df.format(total))+  "\r\n");    //Total
        output.write(new PrintfFormat("%125s").sprintf(spasi) + new PrintfFormat("%12s").sprintf(df.format(ppn)) + "\r\n");      //PPn
        output.write(new PrintfFormat("%125s").sprintf(spasi) + new PrintfFormat("%12s").sprintf(df.format(totalbersih)) + "\r\n");        //Total Bersih
        output.write(12);
        output.close();
        rs.close();
        stat.close();
        System.out.println("Your file has been written");
    }

    
    public static void main(String[] args) {
        Connection con = null;
        CetakFakturPanjang c = new CetakFakturPanjang();
        
        try {
            Class.forName("org.h2.Driver").getInterfaces();
                    con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/", "", "");

            c.cetak(con, "12.000003");
            c.cetakPrinter("c:\\temp\\write.txt");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }


    }
}