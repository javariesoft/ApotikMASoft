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
import com.erv.function.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author UserXP
 */
public class ClassPrint {

    public static final char[] EXTENDED = {0x00C7, 0x00FC, 0x00E9, 0x00E2,
        0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
        0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
        0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
        0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
        0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
        0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
        0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
        0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
        0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
        0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
        0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
        0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
        0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
        0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
        0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
        0x207F, 0x00B2, 0x25A0, 0x00A0};

    Connection c;

    public static void main(String[] args) {
        //Connection c;       
        ClassPrint cs = new ClassPrint();
        try {
            koneksi.createPoolKoneksi();
            cs.cetakfaktur("15.000001");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public static final char getAscii(int code) {
        if (code >= 0x80 && code <= 0xFF) {
            return EXTENDED[code - 0x7F];
        }
        return (char) code;
    }

    public static final void printChar(int code) {
        System.out.printf("%c%n", getAscii(code));
    }

    public void cetakfaktur(String nfak) {

        ClassPrint cp = new ClassPrint();
        java.text.DateFormat d = new SimpleDateFormat("dd-MMMM-yyyy");
        java.util.Date tgl = new java.util.Date();
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
                    + "SALES.NAMA AS SALES_NAMA, " //38
                    + "SALES.INISIAL AS SALES_INISIAL, " //39
                    + "RINCIPENJUALAN.SATUAN AS RINCIPENJUALAN_SATUAN, " //40
                    + "RINCIPENJUALAN.KODEBATCH AS RINCIPENJUALAN_KODEBATCH," //41
                    + "IFNULL(RINCIPENJUALAN.EXPIRE,'-') AS RINCIPENJUALAN_EXPIRE," //42
                    + "RINCIPENJUALAN.DISKONP AS RINCIPENJUALAN_DISKONP, " //43
                    + "PENJUALAN.PPN AS PENJUALAN_PPN " //44
                    + "FROM RINCIPENJUALAN RINCIPENJUALAN INNER JOIN PENJUALAN PENJUALAN ON RINCIPENJUALAN.IDPENJUALAN = PENJUALAN.ID "
                    + "INNER JOIN PELANGGAN PELANGGAN ON PENJUALAN.KODEPELANGGAN = PELANGGAN.KODEPELANGGAN "
                    + "INNER JOIN BARANG BARANG ON RINCIPENJUALAN.KODEBARANG = BARANG.KODEBARANG "
                    + "INNER JOIN JENISBARANG JENISBARANG ON BARANG.IDJENIS = JENISBARANG.ID "
                    + "INNER JOIN SALES SALES ON PENJUALAN.IDSALES = SALES.IDSALES "
                    + "WHERE PENJUALAN.FAKTUR = '" + nfak + "'";

            String spasi = " ";
            Statement stat = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery(sql);
            Statement stat1 = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1 = stat1.executeQuery(sql);
            //ResultSet rs1 = stat.executeQuery(sql);
            rs1.next();
            try {
                String pel = "", Altpel = "", Altpel1 = "", Altpel2 = "";
                if (rs1.getString(17).length() >= 45) {
                    pel = rs1.getString(17).substring(0, 45);
                } else {
                    pel = rs1.getString(17);
                }
                if (rs1.getString(18).length() >= 45) {
                    Altpel = rs1.getString(18).substring(0, 45);
                    Altpel1 = rs1.getString(18).substring(45, 90);
                } else {
                    Altpel = rs1.getString(18);
                }
                //p.append(data0);
                //p.append(data1);
                //p.append(data2);
                //p.append(data3);                                                                                             //       //              //
                p.append("" + getAscii(185) + "\r\n");
                p.append("PT.MIRANTI ADILHA                                                                                   No Faktur   : " + cp.cetak(rs1.getString(2), 10, 1) + "\r\n");
                p.append("No Izin PBF : HK.07.01/V/302/14                                                                           Tanggal     : " + cp.cetak(rs1.getString(3), 10, 1) + "\r\n");
                p.append("No Izin PAK : HK.07.ALKES/IV/372/AK.2/2014                                                                Jatuh Tempo : " + cp.cetak(rs1.getString(6), 10, 1) + "\r\n");
                p.append("NPWP        : 31.640.740.2.201.000                                                                        Cara Bayar  : " + cp.cetak(rs1.getString(37), 6, 1) + "\r\n");
                p.append("Jl. Gajah Mada No. 03 Gunung Pangilun-Padang                                                       \r\n");
                p.append("Email : ptmirantiadilha@gmail.com \r\n");
                p.append("------------------------------------------------------------------------------------------------------------------------------------------ \r\n");
                p.append("| Kepada : " + cp.cetak(pel, 126, 0) + "|");
                p.append("|       " + cp.cetak(Altpel, 129, 0) + "|");
                p.append("------------------------------------------------------------------------------------------------------------------------------------------ \r\n");
                p.append("|  " + cp.cetak(rs1.getString(39), 3, 0) + "                                                           FAKTUR JUAL                                                             |\r\n");
                p.append("------------------------------------------------------------------------------------------------------------------------------------------ \r\n");
                p.append("| No | Banyak |     Satuan      | Nama Barang                        |   Batch    |  Expired  |   Harga (Rp)  |Disc(%)|     Jumlah(Rp)   | \r\n");
                p.append("------------------------------------------------------------------------------------------------------------------------------------------ \r\n");

            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ClassPrint.class.getName()).log(Level.SEVERE, null, ex);
            }

            int no = 0, totqty = 0;
            double total = 0, ppn = 0, totalbersih = 0;
            //int jml=0;
            String kdbrg = "", nmbrg = "";
            while (rs.next()) {
                no++;
                totqty = totqty + Integer.parseInt(rs.getString(12));
                total = total + rs.getDouble(33);
                kdbrg = rs.getString(11);
                nmbrg = rs.getString(23);

                //System.out.println(no + " " + cd_product + " " + nm_product + "\r\n");
                try {
                    p.append(cp.cetak("|", 1, 1) + "" + cp.cetak("" + no, 3, 1) + " | " + cp.cetak(df.format(Integer.parseInt(rs.getString(12)))/* jmlh */, 6, 1) + " | " + cp.cetak(rs.getString(40) /*satuan*/, 15, 0) + " | " + cp.cetak(nmbrg, 34, 0) + " | " + cp.cetak(rs.getString(41) /*Batch*/, 10, 0) + " |" + cp.cetak(rs.getString(42) /*Expire*/, 10, 0) + " | " + cp.cetak(df.format(rs.getDouble(13))/* harga */, 13, 1) + " | " + cp.cetak(df.format(rs.getDouble(43))/* Disc */, 5, 1) + " | " + cp.cetak(df.format(rs.getDouble(15))/* total */, 16, 1) + " |\r\n");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ClassPrint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            totalbersih = (total - rs1.getDouble(9) - rs1.getDouble(8)) + rs1.getDouble(44);
            double terbilang = 0;
            if (totalbersih == 0) {
                terbilang = total;
            } else {
                terbilang = totalbersih;
            }
            try {
                p.append("------------------------------------------------------------------------------------------------------------------------------------------\r\n");
                p.append("|    |" + cp.cetak(df.format(totqty), 7, 1) + " |" + cp.cetak(" ", 56, 0) + "                                     Jumlah : |" + cp.cetak(df.format(total)/* total */, 18, 1) + " |\r\n");
                p.append(cp.cetak("| ", 98, 0) + " Diskon Tambahan : |" + cp.cetak(df.format(rs1.getDouble(9)), 18, 1) + " |\r\n");
                p.append(cp.cetak("| ", 110, 0) + " PPN : |" + cp.cetak(df.format(rs1.getDouble(44)), 18, 1) + " |\r\n");
                //p.append(cp.cetak("| Terbilang : " + com.erv.fungsi.Fungsi.bilang((long) total), 103, 0)+"     Bayar : |"+cp.cetak(df.format(rs1.getDouble(8)), 18, 1)+" |\r\n");
                p.append(cp.cetak("| Terbilang : " + com.erv.fungsi.Fungsi.bilangDouble((double) terbilang), 104, 0) + "     Bayar : |" + cp.cetak(df.format(rs1.getDouble(8)), 18, 1) + " |\r\n");
                p.append(cp.cetak("| ", 100, 0) + "         Total : |" + cp.cetak(df.format(totalbersih), 18, 1) + " |\r\n");
                p.append("------------------------------------------------------------------------------------------------------------------------------------------\r\n");
                p.append(cp.cetak(" ", 110, 0) + " Padang, " + cp.cetak(d.format(tgl), 18, 0) + "\r\n");
                p.append(" Diterima Dengan Baik,                Apoteker Penanggung Jawab,                Operator,                         Pimpinan\r\n");
                p.append(cp.cetak(" ", 0, 0) + "\r\n");
                p.append(cp.cetak(" ", 0, 0) + "\r\n");
                p.append("  (                )               (                  )                (" + cp.cetak(JavarieSoftApp.jenisuser, 15, 0) + "   )                   (                   )\r\n");
//                p.append("(               )                                  (   "+ cp.cetak(rs1.getString(38), 12, 0)+"    )     ( "+cp.cetak(JavarieSoftApp.jenisuser,12,0)+" )\r\n"      );
//                p.append("\r\n");
                //p.append("  Catatan: 1.Pembayaran dengan cek / giro / dianggap sah setelah diuangkan.\r\n");
//                p.append(" Catatan : Barang yang dibeli tidak dapat dikembalikan kecuali dengan perjanjian.      \r\n");
//                p.append(" Rek Bank Atas Nama RAHMAT SUKRI : BNI 005-918-6688 , MANDIRI 111-00-0573906-1, BRI 0617-01-001552-50-0       \r\n");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ClassPrint.class.getName()).log(Level.SEVERE, null, ex);
            }

            p.setOutputPath("c:\\temp\\coba.txt");
            p.append(data4);
            p.append(data0);
            try {
                if (ps != null) {
                    p.setPrintService(ps);
                    p.print();
                }
                p.printToFile();
            } catch (Exception ex) {
                Logger.getLogger(ClassPrint.class.getName()).log(Level.SEVERE, null, ex);
            }
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Map<String, Object> cetakfakturMap(Connection con, String nfak) {
        java.text.DateFormat d = new SimpleDateFormat("dd-MMMM-yyyy");
        java.util.Date tgl=new java.util.Date();
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        Map<String, Object> p = new HashMap<String, Object>();

        try {

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
                    + "SALES.NAMA AS SALES_NAMA, " //38
                    + "SALES.INISIAL AS SALES_INISIAL, " //39
                    + "RINCIPENJUALAN.SATUAN AS RINCIPENJUALAN_SATUAN, " //40
                    + "RINCIPENJUALAN.KODEBATCH AS RINCIPENJUALAN_KODEBATCH," //41
                    + "IFNULL(RINCIPENJUALAN.EXPIRE,'-') AS RINCIPENJUALAN_EXPIRE," //42
                    + "RINCIPENJUALAN.DISKONP AS RINCIPENJUALAN_DISKONP, " //43
                    + "PENJUALAN.PPN AS PENJUALAN_PPN, " //44
                    + "(select namalengkap from usertable where groupuser='Apoteker' and statusaktif='0') as apoteker, " //45 -46
                    + "(select keterangan from usertable where groupuser='Apoteker' and statusaktif='0') as ketapoteker " //46 -47
                    + "FROM RINCIPENJUALAN RINCIPENJUALAN INNER JOIN PENJUALAN PENJUALAN ON RINCIPENJUALAN.IDPENJUALAN = PENJUALAN.ID "
                    + "INNER JOIN PELANGGAN PELANGGAN ON PENJUALAN.KODEPELANGGAN = PELANGGAN.KODEPELANGGAN "
                    + "INNER JOIN BARANG BARANG ON RINCIPENJUALAN.KODEBARANG = BARANG.KODEBARANG "
                    + "INNER JOIN JENISBARANG JENISBARANG ON BARANG.IDJENIS = JENISBARANG.ID "
                    + "INNER JOIN SALES SALES ON PENJUALAN.IDSALES = SALES.IDSALES "
                    + "WHERE PENJUALAN.FAKTUR = '" + nfak + "'";

            String spasi = " ";
            Statement stat = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery(sql);
            Statement stat1 = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1 = stat1.executeQuery(sql);
            //ResultSet rs1 = stat.executeQuery(sql);
            rs1.next();
            String pel = "", Altpel = "", Altpel1 = "", Altpel2 = "";
            if (rs1.getString(17).length() >= 45) {
                pel = rs1.getString(17).substring(0, 45);
            } else {
                pel = rs1.getString(17);
            }

            Altpel = rs1.getString(18);
            p.put("FAKTUR", rs1.getString(2));
            p.put("TANGGAL", rs1.getString(3));
            p.put("TGLLUNAS", rs1.getString(6));
            p.put("CASH", rs1.getString(37));
            p.put("PELANGGAN", pel);
            p.put("ALAMATPELANGGAN", Altpel);
            p.put("INISIAL", rs1.getString(39));

            int no = 0, totqty = 0;
            double total = 0, ppn = 0, totalbersih = 0, bayar =0;
            //int jml=0;
            String kdbrg = "", nmbrg = "";
            List<Map<String, Object>> tables = new ArrayList<Map<String, Object>>();
            Map<String, Object> line;
            while (rs.next()) {
                no++;
                line = new HashMap<String, Object>();
                totqty = totqty + Integer.parseInt(rs.getString(12));
                total = total + rs.getDouble(33);
                kdbrg = rs.getString(11);
                nmbrg = rs.getString(23);
                line.put("no", no);
                line.put("banyak", rs.getString(12));
                line.put("satuan", rs.getString(40));
                line.put("nmbrg", nmbrg);
                line.put("batch", rs.getString(41));
                line.put("expire", rs.getString(42));
                line.put("harga", df.format(Math.round(rs.getDouble(13))));
                line.put("diskon", df.format(rs.getDouble(43)));
                line.put("jumlah", df.format(rs.getDouble(33)));
                line.put("kode", rs.getString(11));
                tables.add(line);
            }
            while (true) {
                if (no % 7 != 0) {
                    line = new HashMap<String, Object>();
                    line.put("no", "");
                    line.put("banyak", "");
                    line.put("satuan", "");
                    line.put("nmbrg", "");
                    line.put("batch", "");
                    line.put("expire", "");
                    line.put("harga", "");
                    line.put("diskon", "");
                    line.put("jumlah", "");
                    tables.add(line);
                    no++;
                }else{
                    break;
                }
            }
            p.put("table_source", tables);
            p.put("TOTQTY", totqty);
            p.put("total", Math.round(total));
            p.put("diskontambahan", Math.round(rs1.getDouble(9)));
            p.put("ppn", Math.round(rs1.getDouble(44)));

            //bayar = (rs1.getString(37).equals("0") ? total +rs1.getDouble(44) +rs1.getDouble(45)  : (rs1.getDouble(8)!=0 ? rs1.getDouble(8):0)); ada ongkir
            bayar = (rs1.getString(37).equals("0") ? total +rs1.getDouble(44)  : (rs1.getDouble(8)!=0 ? rs1.getDouble(8):0));
//            totalbersih = (total - rs1.getDouble(9) - rs1.getDouble(8)) + rs1.getDouble(44);
            //totalbersih = (rs1.getString(37).equals("0") ? 0 : (rs1.getDouble(8)!=0 ? (total + rs1.getDouble(44) + rs1.getDouble(45) - rs1.getDouble(8)) : total + rs1.getDouble(44) + rs1.getDouble(45) )); ada ongkir
            totalbersih = (rs1.getString(37).equals("0") ? 0 : (rs1.getDouble(8)!=0 ? (total - rs1.getDouble(8)) : total + rs1.getDouble(44) ));
            
            double terbilang = 0;
            if (totalbersih == 0) {
                terbilang = bayar;
            } else {
                terbilang = totalbersih;
            }
            String terbilangkata = com.erv.fungsi.Fungsi.bilangDouble((double) terbilang) + " Rupiah";
            String t[] = Util.split(terbilangkata, " ");
            String t1 = "";
            String t2 = "";
            if (t.length > 9) {
                for (int i = 0; i < 9; i++) {
                    t1 += " " + t[i];
                }
                for (int i = 9; i < t.length; i++) {
                    t2 += " " + t[i];
                }
            } else {
                t1 = terbilangkata;
            }
            p.put("terbilang", t1);
            p.put("terbilanglanjut", t2);
//            p.put("bayar", Math.round(rs1.getDouble(8)));
            p.put("bayar", Math.round(bayar));
            p.put("totalbersih", Math.round(totalbersih));
            p.put("tglcetak",d.format(tgl));
            p.put("namauser",JavarieSoftApp.jenisuser);
            p.put("salesinisial", rs1.getString(39));
            //p.put("ongkir", Math.round(rs1.getDouble(45)));
            p.put("apotek", rs1.getString(45));
            p.put("ketapotek", rs1.getString(46));
        } catch (Exception e) {
            System.out.println(e);
        }
        return p;
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
