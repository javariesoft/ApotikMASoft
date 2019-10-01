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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erwadi
 */
public class CetakFakturPajak {
    DecimalFormat df=new DecimalFormat("###,###,###,###");
    java.text.DateFormat d = new SimpleDateFormat("dd-MMMM-yyyy");
    java.util.Date tgl=new java.util.Date(); 
    public void cetakPrinter(String file){
        try {
            Runtime rt = Runtime.getRuntime();
            String[] cmd = {"cmd.exe", "/C","c:\\temp\\dwoprn.exe"+" "+file};
            rt.exec(cmd);
            System.out.print(cmd);
        } catch (Exception ex) {
            System.out.print(ex.toString());
        }
  }
    public void cetak(Connection con,String noFaktur) throws IOException, SQLException {
        String sql="SELECT PENJUALAN.\"ID\" AS PENJUALAN_ID,"                       //1
                + "PENJUALAN.\"FAKTUR\" AS PENJUALAN_FAKTUR,"                       //2
                + "PENJUALAN.\"TANGGAL\" AS PENJUALAN_TANGGAL,"                     //3
                + "PENJUALAN.\"KODEPELANGGAN\" AS PENJUALAN_KODEPELANGGAN,"         //4
                + "PENJUALAN.\"CASH\" AS PENJUALAN_CASH,"                           //5
                + "PENJUALAN.\"TGLLUNAS\" AS PENJUALAN_TGLLUNAS,"                   //6
                + "PENJUALAN.\"PPN\" AS PENJUALAN_PPN,"                             //7
                + "PENJUALAN.\"DP\" AS PENJUALAN_DP,"                               //8
                + "PENJUALAN.\"DISKON\" AS PENJUALAN_DISKON,"                       //9
                + "RINCIPENJUALAN.\"IDPENJUALAN\" AS RINCIPENJUALAN_IDPENJUALAN,"   //10    
                + "RINCIPENJUALAN.\"KODEBARANG\" AS RINCIPENJUALAN_KODEBARANG,"     //11
                + "RINCIPENJUALAN.\"JUMLAH\" AS RINCIPENJUALAN_JUMLAH,"             //12
                + "RINCIPENJUALAN.\"HARGA\" AS RINCIPENJUALAN_HARGA,"               //13
                + "RINCIPENJUALAN.\"DISKON\" AS RINCIPENJUALAN_DISKON,"             //14
                + "RINCIPENJUALAN.\"TOTAL\" AS RINCIPENJUALAN_TOTAL,"               //15
                + "PELANGGAN.\"KODEPELANGGAN\" AS PELANGGAN_KODEPELANGGAN,"         //16
                + "PELANGGAN.\"NAMA\" AS PELANGGAN_NAMA,"                           //17
                + "PELANGGAN.\"ALAMAT\" AS PELANGGAN_ALAMAT,"                       //18
                + "PELANGGAN.\"NPWP\" AS PELANGGAN_NPWP,"                           //19
                + "PELANGGAN.\"HP\" AS PELANGGAN_HP,"                               //20
                + "BARANG.\"KODEBARANG\" AS BARANG_KODEBARANG,"                     //21
                + "BARANG.\"NAMABARANG\" AS BARANG_NAMABARANG,"                     //22
                + "BARANG.\"SATUAN\" AS BARANG_SATUAN,"                             //23
                + "BARANG.\"HARGA\" AS BARANG_HARGA,"                               //24
                + "BARANG.\"COGS\" AS BARANG_COGS,"                                 //25
                + "BARANG.\"STOK\" AS BARANG_STOK,"                                 //26
                + "BARANG.\"IDJENIS\" AS BARANG_IDJENIS,"                           //27
                + "RINCIPENJUALAN.\"PPN\" AS RINCIPENJUALAN_PPN,"                   //28
                + "(RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA) - ((RINCIPENJUALAN.DISKON / 100) * (RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA)) AS TOTDIS,"  //29
                + "((RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA) - ((RINCIPENJUALAN.DISKON / 100) * (RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA))) * (RINCIPENJUALAN.PPN / 100) as PPNTOT,"  //30
                + "JENISBARANG.JENIS AS JENISBARANG_JENIS,"                         //31
                + "PENJUALAN.IDSALES AS PENJUALAN_IDSALES,"                         //32
                + "PAJAK.NOPAJAK,"                                                  //33
                + "(RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA) AS JumTot,"       //34
                + "(RINCIPENJUALAN.JUMLAH * RINCIPENJUALAN.HARGA) * (RINCIPENJUALAN.DISKON / 100) AS TOTdiskon"  //35
                + " FROM "
                + " \"PUBLIC\".\"RINCIPENJUALAN\" RINCIPENJUALAN INNER JOIN \"PUBLIC\".\"PENJUALAN\" PENJUALAN ON RINCIPENJUALAN.\"IDPENJUALAN\" = PENJUALAN.\"ID\""
                + " INNER JOIN \"PUBLIC\".\"PELANGGAN\" PELANGGAN ON PENJUALAN.\"KODEPELANGGAN\" = PELANGGAN.\"KODEPELANGGAN\""
                + " INNER JOIN \"PUBLIC\".\"BARANG\" BARANG ON RINCIPENJUALAN.\"KODEBARANG\" = BARANG.\"KODEBARANG\""
                + " INNER JOIN \"PUBLIC\".\"JENISBARANG\" JENISBARANG ON BARANG.\"IDJENIS\" = JENISBARANG.\"ID\""
                + " INNER JOIN \"PUBLIC\".\"PAJAK\" PAJAK ON PENJUALAN.\"ID\" = PAJAK.\"IDJUAL\" "
                + "WHERE PAJAK.\"NOPAJAK\" = '"+noFaktur+"' GROUP BY RINCIPENJUALAN.\"KODEBARANG\"";
        String spasi=" ";
        Statement stat=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=stat.executeQuery(sql);
        rs.next();
        Writer output = null;
        File file = new File("c:\\temp\\FPajakwrite.txt");
        output = new BufferedWriter(new FileWriter(file));
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        //output.write("\r\n");
        output.write(15);
        output.write(new PrintfFormat("%43s").sprintf(spasi)+new PrintfFormat("%-30s").sprintf(noFaktur)+  "\r\n");    //No Faktur Pajak
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write(new PrintfFormat("%43s").sprintf(spasi)+new PrintfFormat("%-50s").sprintf(rs.getString(17))+  "\r\n");    //Nama Pelanggan
        output.write(new PrintfFormat("%43s").sprintf(spasi)+new PrintfFormat("%-50s").sprintf(rs.getString(18))+  "\r\n");    //Alamat
        output.write(new PrintfFormat("%43s").sprintf(spasi)+new PrintfFormat("%-30s").sprintf(rs.getString(19))+  "\r\n");    //NPWP
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        output.write("\r\n");
        rs.beforeFirst();
       
        int no=1;
        double total=0,totalDiskon=0, ppn=0,totalppn=0;
        while(rs.next()){
            total=total+rs.getDouble(34);
            totalDiskon=totalDiskon+rs.getDouble(35);
            String nmbrg=rs.getString(22);
            if(nmbrg.length()>60){
                nmbrg=nmbrg.substring(0, 60);
            }
            output.write(new PrintfFormat("%6s").sprintf(""+no++) + "    "
                            + new PrintfFormat("%-100s").sprintf(nmbrg) + " "
                            + new PrintfFormat("%12s").sprintf(df.format(rs.getDouble(34))) + " "
                            + "\r\n");
        }
        if(no<22){
            for(int a=0;a<(22-no);a++){
                output.write("\r\n");
            }
        }
        
        ppn=total+totalDiskon;
        totalppn=0.1*ppn;
        //output.write("==========================================================================================================================" + "\r\n");
        //output.write("\r\n");
        output.write(new PrintfFormat("%110s").sprintf(spasi)+new PrintfFormat("%12s").sprintf(df.format(total))+  "\r\n");          //Total  
        output.write(new PrintfFormat("%110s").sprintf(spasi)+new PrintfFormat("%12s").sprintf(df.format(totalDiskon))+  "\r\n");    //Total Diskon
        output.write("\r\n");
        output.write(new PrintfFormat("%110s").sprintf(spasi)+new PrintfFormat("%12s").sprintf(df.format(ppn))+  "\r\n");          //Pajak  
        output.write(new PrintfFormat("%110s").sprintf(spasi)+new PrintfFormat("%12s").sprintf(df.format(totalppn))+  "\r\n");    //Total PPn  
        output.write("\r\n");
        //output.write("\r\n");
        output.write(new PrintfFormat("%102s").sprintf(spasi)+new PrintfFormat("%17s").sprintf(d.format(tgl).toString())+  "\r\n");
        output.write(12);
        output.close();
        rs.close();
        stat.close();
        System.out.println("Your file has been written");
    }
    public static void main(String[] args){
        Connection con = null;
        CetakFakturPajak c=new CetakFakturPajak();
            try {
                con= koneksi.getKoneksiJ();
                c.cetak(koneksi.getKoneksiJ(),"123");
                c.cetakPrinter("FPajakwrite.txt");
            } catch (Exception ex) {
                Logger.getLogger(CetakFakturPajak.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CetakFakturPajak.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        
    }
}
