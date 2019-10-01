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
public class ClassPrintBukuBesar {
        Connection c;
    public static void main(String[] args) {
        //Connection c;
        ClassPrintBukuBesar cs = new ClassPrintBukuBesar();
        try {       
           //cs.cetakfaktur("11110","9","2014");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } 
    }
    public void cetakfaktur(String KdPer,String bul,String thn){
        
        ClassPrintBukuBesar cp = new ClassPrintBukuBesar();
        java.text.DateFormat d = new SimpleDateFormat("dd-MMMM-yyyy");
        java.util.Date tgl=new java.util.Date();
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        DecimalFormat dfjml = new DecimalFormat(".00");
        byte data0[] = {27, 64};
        byte data1[] = {27, 02};
        byte data2[] = {27, 67, 33};
        byte data3[] = {15};
        byte data4[] = {12};
        
        try {
            c = koneksi.getKoneksiJ();

            PrintService ps = PrintServiceMatcher.findPrinter("zebra");
            PrintRaw p = new PrintRaw();
            String sql = "SELECT FORMATDATETIME(JURNAL.TANGGAL,'d-MM-yyyy') AS JURNAL_TANGGAL," //1
                    + "JURNAL.KODEJURNAL AS JURNAL_KODEJURNAL,"     //2
                    + "JURNAL.DESKRIPSI AS JURNAL_DESKRIPSI,"       //3
                    + "RINCIJURNAL.DEBET AS RINCIJURNAL_DEBET,"     //4
                    + "RINCIJURNAL.KREDIT AS RINCIJURNAL_KREDIT,"   //5
                    + "PERKIRAAN.KODEPERKIRAAN AS PERKIRAAN_KODEPERKIRAAN,"     //6
                    + "PERKIRAAN.NAMAPERKIRAAN AS PERKIRAAN_NAMAPERKIRAAN,"     //7
                    + "ifnull((SELECT sum(SALDOPERIODE.SALDO) AS SALDOPERIODE_SALDO FROM SALDOPERIODE SALDOPERIODE "
                    + "WHERE SALDOPERIODE.KODEAKUN like '"+ KdPer +"' "
                    + "AND substr(saldoperiode.periode,6,2)= casewhen("+bul+" - 1=0,12,"+bul+"-1) "
                    + "and left(saldoperiode.periode,4)=casewhen("+bul+" - 1=0,"+thn+"-1,"+thn+")),0) as SALDOPERIODE_SALDO,"       //8
                    + "PERKIRAAN.GRUP AS PERKIRAAN_GRUP "   //9
                    + "FROM JURNAL JURNAL INNER JOIN RINCIJURNAL RINCIJURNAL "
                    + "ON JURNAL.ID = RINCIJURNAL.KODEJURNAL "
                    + "INNER JOIN PERKIRAAN PERKIRAAN ON RINCIJURNAL.KODEPERKIRAAN = PERKIRAAN.KODEPERKIRAAN "
                    + "WHERE PERKIRAAN.KODEPERKIRAAN LIKE '"+ KdPer +"' "
                    + "AND Month(JURNAL.TANGGAL) = "+bul+" AND YEAR(JURNAL.TANGGAL) = "+thn+" "
                    + "order by JURNAL.TANGGAL";
//            System.out.println(sql);
            String spasi = " ";
            Statement stat = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery(sql);
            Statement stat1 = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1 = stat1.executeQuery(sql);
            //ResultSet rs1 = stat.executeQuery(sql);
            rs1.next();
            try {
                p.append(data0);
                p.append(data1);
                p.append(data2);
                p.append(data3);                                                //     //                                       //      //   
                p.append("TOKO ATENA COLLECTION\r\n");
                p.append("LAPORAN DETAIL BUKU BANTU\r\n");         
                p.append("PERIODE : "+cp.cetak(bul, 2, 0)+"-"+cp.cetak(thn, 4, 0)+"\r\n");        
                p.append("\r\n");
                p.append("Nomor Perkiraan : "+cp.cetak(KdPer, 10, 0)+"\r\n");
                p.append("Nama Perkiraan  : "+cp.cetak(rs1.getString(7), 40, 0)+"\r\n");
                p.append("--------------------------------------------------------------------------------------------------------------------------------------- \r\n");
                p.append("|  NO |  Tanggal |  Kode Trans    |         Keterangan                              |     Debet     |      Kredit   |      Saldo      |\r\n");
                p.append("--------------------------------------------------------------------------------------------------------------------------------------- \r\n");
                p.append("|                                                                                                       SALDO AWAL  | "+ cp.cetak(df.format(rs1.getDouble(8)), 15, 1) /*SaldoAwal*/ +" |\r\n");
                p.append("--------------------------------------------------------------------------------------------------------------------------------------- \r\n");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ClassPrintBukuBesar.class.getName()).log(Level.SEVERE, null, ex);
            }

            int no = 0;
            double debet1 = 0, kredit1 = 0, saldo = 0,totaldebet1=0,totalkredit1=0,totalsaldo=0;
            String desk="";
            //int jml=0;
            while (rs.next()) {
                no++;
                if(rs.getString(3).length()>=47){
                    desk=rs.getString(3).substring(0, 47);
                }else{
                    desk=rs.getString(3);
                }
                debet1=debet1+rs.getDouble(4);
                kredit1=kredit1+rs.getDouble(5);
                saldo =(rs.getString(9).equals("1") || rs.getString(9).equals("5") || rs.getString(9).equals("6")) ? (rs.getDouble(8)+debet1-kredit1) : (rs.getDouble(8)-debet1+kredit1);
                //System.out.println(no + " " + cd_product + " " + nm_product + "\r\n");
                try {
                    p.append(cp.cetak("|", 1, 1) + ""+ cp.cetak(""+no, 4, 1) + " |" + cp.cetak(rs.getString(1)/* Tanggal */, 10, 0) + "| " + cp.cetak(rs.getString(2)/* kodetrans */, 14, 0) + " | " + cp.cetak(desk, 47, 0) /*Deskripsi*/ + " | " + cp.cetak(df.format(rs.getDouble(4)), 13, 1) /*Debet*/ + " | " + cp.cetak(df.format(rs.getDouble(5))/* Kredit */, 13, 1) + " | " + cp.cetak(df.format(saldo)/* Saldo */, 15, 1) + " |\r\n");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ClassPrintBukuBesar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            totaldebet1=totaldebet1 + debet1;
            totalkredit1=totalkredit1 + kredit1;
            totalsaldo=totalsaldo + saldo;
            try {
                p.append("---------------------------------------------------------------------------------------------------------------------------------------\r\n");
                p.append("|                                                                           Total : | " + cp.cetak(df.format(totaldebet1)/* total */, 13, 1) + " | " + cp.cetak(df.format(totalkredit1)/* total */, 13, 1) + " |" + cp.cetak(df.format(totalsaldo)/* total */, 16, 1) + " |\r\n");               
                p.append("---------------------------------------------------------------------------------------------------------------------------------------\r\n");
                
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ClassPrintBukuBesar.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ClassPrintBukuBesar.class.getName()).log(Level.SEVERE, null, ex);
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
