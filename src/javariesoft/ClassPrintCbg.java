/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;
import com.erv.db.koneksi;
import javax.swing.JOptionPane;
import org.h2.expression.Function;

/**
 *
 * @author UserXP
 */
public class ClassPrintCbg {
        Connection c;
    public static void main(String[] args) {
        //Connection c;
        ClassPrintCbg cs = new ClassPrintCbg();
        try {       
//            cs.cetakfaktur("14.000000");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } 
    }
    public void cetakfaktur(String nfak){
        
        ClassPrintCbg cp = new ClassPrintCbg();
        java.text.DateFormat d = new SimpleDateFormat("dd-MMMM-yyyy");
        java.util.Date tgl=new java.util.Date();
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        byte data0[] = {27, 64};
        byte data1[] = {27, 02};
        byte data2[] = {27, 67, 33};
        byte data3[] = {15};
        byte data4[] = {12};
        
        try {
            c = koneksi.getKoneksiJ();

            PrintService ps = PrintServiceMatcher.findPrinter("zebra");
            PrintRaw p = new PrintRaw();
            String sql = "SELECT PENJUALAN.ID AS PENJUALAN_ID," //1
                    + "PENJUALAN.FAKTUR AS PENJUALAN_FAKTUR," //2
                    + "FORMATDATETIME(PENJUALAN.TANGGAL,'d-MM-yyyy') AS PENJUALAN_TANGGAL," //3
                    + "PENJUALAN.KODEPELANGGAN AS PENJUALAN_KODEPELANGGAN," //4
                    + "PENJUALAN.CASH AS PENJUALAN_CASH," //5
                    + "FORMATDATETIME(PENJUALAN.TGLLUNAS,'d-MM-yyyy') AS PENJUALAN_TGLLUNAS," //6
                    + "PENJUALAN.PPN AS PENJUALAN_PPN," //7
                    + "PENJUALAN.DP AS PENJUALAN_DP," //8
                    + "PENJUALAN.DISKON AS PENJUALAN_DISKON," //9
                    + "RINCIPENJUALAN.IDPENJUALAN AS RINCIPENJUALAN_IDPENJUALAN," //10
                    + "RINCIPENJUALAN.KODEBARANG AS RINCIPENJUALAN_KODEBARANG," //11  
                    + "RINCIPENJUALAN.JUMLAH AS RINCIPENJUALAN_JUMLAH," //12
                    + "RINCIPENJUALAN.HARGA AS RINCIPENJUALAN_HARGA," //13   
                    + "RINCIPENJUALAN.DISKON AS RINCIPENJUALAN_DISKON," //14
                    + "RINCIPENJUALAN.TOTAL AS RINCIPENJUALAN_TOTAL," //15
                    + "PELANGGAN.KODEPELANGGAN AS PELANGGAN_KODEPELANGGAN," //16
                    + "PELANGGAN.NAMA AS PELANGGAN_NAMA," //17
                    + "PELANGGAN.ALAMAT AS PELANGGAN_ALAMAT," //18  
                    + "PELANGGAN.HP AS PELANGGAN_HP," //19
                    + "PELANGGAN.TGLREG AS PELANGGAN_TGLREG," //20
                    + "PELANGGAN.BATASKREDIT AS PELANGGAN_BATASKREDIT," //21
                    + "BARANG.KODEBARANG AS BARANG_KODEBARANG," //22
                    + "BARANG.NAMABARANG AS BARANG_NAMABARANG," //23
                    + "BARANG.SATUAN AS BARANG_SATUAN," //24  
                    + "BARANG.HARGA AS BARANG_HARGA," //25  
                    + "BARANG.KODEAKUN AS BARANG_KODEAKUN," //26  
                    + "BARANG.PENDAPATAN_ACC AS BARANG_PENDAPATAN_ACC," //27  
                    + "BARANG.COGS_ACC AS BARANG_COGS_ACC," //28
                    + "BARANG.COGS AS BARANG_COGS," //29
                    + "BARANG.STOK AS BARANG_STOK," //30  
                    + "BARANG.IDJENIS AS BARANG_IDJENIS," //31  
                    + "RINCIPENJUALAN.PPN AS RINCIPENJUALAN_PPN," //32  
                    + "(RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA) - (RINCIPENJUALAN.DISKON) AS TOTDIS," //33
                    + "((RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA) - ((RINCIPENJUALAN.DISKON / 100) * (RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA))) * (RINCIPENJUALAN.PPN / 100) as PPNTOT," //34
                    + "JENISBARANG.JENIS AS JENISBARANG_JENIS," //35
                    + "PENJUALAN.IDSALES AS PENJUALAN_IDSALES," //36  
                    + "case PENJUALAN.CASH when 0 then 'Tunai' when 1 then 'Kredit' when 2 then 'Bank' end as pembayaran," //37
                    + "SALES.NAMA AS SALES_NAMA, "  //38
                    + "PENJUALAN.TAMBAHANTOTAL AS PENJUALAN_TAMBAHANTOTAL " //39
                    + "FROM RINCIPENJUALAN RINCIPENJUALAN INNER JOIN PENJUALAN PENJUALAN ON RINCIPENJUALAN.IDPENJUALAN = PENJUALAN.ID "
                    + "INNER JOIN PELANGGAN PELANGGAN ON PENJUALAN.KODEPELANGGAN = PELANGGAN.KODEPELANGGAN "
                    + "INNER JOIN BARANG BARANG ON RINCIPENJUALAN.KODEBARANG = BARANG.KODEBARANG "
                    + "INNER JOIN JENISBARANG JENISBARANG ON BARANG.IDJENIS = JENISBARANG.ID "
                    + "INNER JOIN SALES SALES ON PENJUALAN.IDSALES = SALES.IDSALES "
                    + "WHERE PENJUALAN.FAKTUR = '"+ nfak +"'";

            String spasi = " ";
            Statement stat = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery(sql);
            Statement stat1 = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1 = stat1.executeQuery(sql);
            //ResultSet rs1 = stat.executeQuery(sql);
            rs1.next();
            try {
                String pel="",Altpel="";
                if(rs1.getString(17).length()>=17){
                    pel=rs1.getString(17).substring(0, 17);
                }else{
                    pel=rs1.getString(17);
                }
                if(rs1.getString(18).length()>=23){
                    Altpel=rs1.getString(18).substring(0, 23);
                }else{
                    Altpel=rs1.getString(18);
                }
                p.append(data0);
                p.append(data1);
                p.append(data2);
                p.append(data3);
                p.append("TOKO ATENA COLLECTION                                                                              Kepada : "+ cp.cetak(pel, 17, 0) +"\r\n");
                p.append("Jl. BLK Terminal No 16, Aur Kuning-Bukittinggi.                                                             "+ cp.cetak(Altpel, 23, 0) +"\r\n");
                p.append("Telp. (0752)-627694                                                                                         No Faktur   : "+cp.cetak(rs1.getString(2), 10, 1)+"\r\n");
                p.append("                                                                                                            Tanggal     : "+cp.cetak(rs1.getString(3), 10, 1) +"\r\n");
                p.append("                                                                                                               \r\n");
                p.append("                                                             FAKTUR PENGIRIMAN BARANG                                        "+cp.cetak(rs1.getString(37), 6, 1)+"\r\n");
                p.append("---------------------------------------------------------------------------------------------------------------------------------------- \r\n");
                p.append("| NO | Kd Brg  |        Nama Barang                                  |  Banyak | Harga Modal(Rp) | Harga Jual (Rp) |        Jumlah(Rp) | \r\n");
                p.append("---------------------------------------------------------------------------------------------------------------------------------------- \r\n");

            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ClassPrintCbg.class.getName()).log(Level.SEVERE, null, ex);
            }

            int no = 0,totqty=0;
            double total = 0, ppn = 0, totalbersih = 0;
            //int jml=0;
            String kdbrg = "", nmbrg = "";
            while (rs.next()) {
                no++;
                totqty= totqty + Integer.parseInt(rs.getString(12));
                total = total + rs.getDouble(33);
                kdbrg = rs.getString(11);
                nmbrg = rs.getString(23);
                 
                //System.out.println(no + " " + cd_product + " " + nm_product + "\r\n");
                try {
                    p.append(cp.cetak("|", 1, 1) + ""+ cp.cetak(""+no, 3, 1) + " | " + cp.cetak(kdbrg, 7, 0) + " | " + cp.cetak(nmbrg, 51, 0) + " | " + cp.cetak(df.format(Integer.parseInt(rs.getString(12)))/* jmlh */, 6, 1) + "  | " + cp.cetak(df.format(rs.getDouble(13))/* harga */, 15, 1) + " | " + cp.cetak(df.format(rs.getDouble(25))/* Hrg Jual */, 15, 1) + " | " + cp.cetak(df.format(rs.getDouble(15))/* total */, 17, 1) + " |\r\n");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ClassPrintCbg.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            totalbersih=total + rs1.getDouble(39) - rs1.getDouble(8);
            try {
                p.append("----------------------------------------------------------------------------------------------------------------------------------------\r\n");
                p.append(cp.cetak("| Terbilang : " + com.erv.fungsi.Fungsi.bilang((long) total), 69, 0) + "| "+cp.cetak(df.format(totqty), 6, 1)+"  |                           Total : |" + cp.cetak(df.format(total)/* total */, 18, 1) + " |\r\n");
                p.append(cp.cetak("| ", 69, 0)+"|         |                  Tambahan 2.5 % : |"+cp.cetak(df.format(rs1.getDouble(39)), 18, 1)+" |\r\n");
                p.append(cp.cetak("| ", 69, 0)+"|         |                       Uang Muka : |"+cp.cetak(df.format(rs1.getDouble(8)), 18, 1)+" |\r\n");
                p.append(cp.cetak("| ", 69, 0)+"|         |                     Sisa Hutang : |"+cp.cetak(df.format(totalbersih), 18, 1)+" |\r\n");
                p.append("----------------------------------------------------------------------------------------------------------------------------------------\r\n");
                p.append("                                                                                                      Bukittinggi, "+cp.cetak(d.format(tgl), 18, 0)+"\r\n");
                p.append("    Ttd Cbg                                          Diperiksa Oleh                                             Operator,\r\n");
                p.append("   Adm Cabang,                                         Sales\r\n");
                p.append("\r\n");
                p.append("(               )                                  (   "+ cp.cetak(rs1.getString(38), 12, 0)+"    )                                   (   "+cp.cetak(JavarieSoftApp.jenisuser,12,0)+" )\r\n"   );
                p.append("\r\n");
//                p.append("  Catatan: 1.Pembayaran dengan cek / giro / dianggap sah setelah diuangkan.\r\n");
//                p.append("           2.Barang yang dibeli tidak dapat dikembalikan kecuali dengan perjanjian.      \r\n");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ClassPrintCbg.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            p.setOutputPath("c:\\temp\\coba.txt");

            p.append(data4);
            p.append(data0);
            try {
                if (ps != null) {
                    p.setPrintService(ps);
                    p.print();
                }
//                p.printToFile();
            } catch (Exception ex) {
                Logger.getLogger(ClassPrintCbg.class.getName()).log(Level.SEVERE, null, ex);
            }
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public String cetak(String data, int panjang, int align) {
        String hasil = data;
        if (align == 0) {
            for (int i = data.length(); i < panjang; i++) {
                hasil += " ";
            }
        } else if (align == 1) {
            String a = "";
            for (int i = 0; i < (panjang - data.length()); i++) {
                a += " ";
            }
            hasil = a + hasil;
        }
        return hasil;
    }

    public char chr(int data) {
        char a = (char) data;
        return a;
    }
}
