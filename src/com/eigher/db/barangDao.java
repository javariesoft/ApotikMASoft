/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eigher.db;
import com.erv.function.PrintfFormat;
import com.erv.model.barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author erwadi
 */
public class barangDao {
    Connection con;

    public barangDao(Connection con) {
        this.con = con;
    }
    public boolean insert(barang bb) throws SQLException
     {
        String sql="insert into BARANG values (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(1,bb.getKODEBARANG());
        pstmt.setString(2,bb.getNAMABARANG());
        pstmt.setString(3,bb.getSATUAN());
        pstmt.setDouble(4,bb.getHARGA());
        pstmt.setString(5,bb.getKODEAKUN());
        pstmt.setString(6,bb.getPENDAPATAN_ACC());
        pstmt.setString(7,bb.getCOGS_ACC());
        pstmt.setDouble(8,bb.getCOGS());
        pstmt.setInt(9,bb.getSTOK());
        pstmt.setString(10,bb.getIDKATEGORI());
        pstmt.setString(11, bb.getJENISBARANG());
        
        boolean i = pstmt.execute();
        return i;
     }
    public List getAlldetails()throws SQLException
    {

        PreparedStatement pstmt = con.prepareStatement("select * from BARANG");
        ResultSet rs = pstmt.executeQuery();
        List list = new ArrayList();
        while(rs.next())
        {
            barang ubean = new barang();
            ubean.setKODEBARANG(rs.getString(1));
            ubean.setNAMABARANG(rs.getString(2));
            ubean.setSATUAN(rs.getString(3));
            ubean.setHARGA(rs.getDouble(4));
            ubean.setKODEAKUN(rs.getString(5));
            ubean.setPENDAPATAN_ACC(rs.getString(6));
            ubean.setCOGS_ACC(rs.getString(7));
            ubean.setCOGS(rs.getDouble(8));
            ubean.setSTOK(rs.getInt(9));
            ubean.setIDKATEGORI(rs.getString(10));
            ubean.setJENISBARANG(rs.getString(11));
            list.add(ubean);
        }
        rs.close();
        pstmt.close();

        return list;
    }
    public barang getDetails(String id)throws SQLException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from BARANG where KODEBARANG=?");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        barang ubean = new barang();
        while(rs.next())
        {
            ubean.setKODEBARANG(rs.getString(1));
            ubean.setNAMABARANG(rs.getString(2));
            ubean.setSATUAN(rs.getString(3));
            ubean.setHARGA(rs.getDouble(4));
            ubean.setKODEAKUN(rs.getString(5));
            ubean.setPENDAPATAN_ACC(rs.getString(6));
            ubean.setCOGS_ACC(rs.getString(7));
            ubean.setCOGS(rs.getDouble(8));
            ubean.setSTOK(rs.getInt(9));
            ubean.setIDKATEGORI(rs.getString(10));
            ubean.setJENISBARANG(rs.getString(11));
        }
        
        return ubean;
    }
    public boolean update(barang bb) throws SQLException
     {
        String sql="update BARANG set NAMABARANG=?,SATUAN=?,HARGA=?,KODEAKUN=?,"
                + "PENDAPATAN_ACC=?,COGS_ACC=?, COGS=?,STOK=?, IDKATEGORI=?, IDJENIS=? where KODEBARANG=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(11,bb.getKODEBARANG());
        pstmt.setString(1,bb.getNAMABARANG());
        pstmt.setString(2,bb.getSATUAN());
        pstmt.setDouble(3,bb.getHARGA());
        pstmt.setString(4,bb.getKODEAKUN());
        pstmt.setString(5,bb.getPENDAPATAN_ACC());
        pstmt.setString(6,bb.getCOGS_ACC());
        pstmt.setDouble(7,bb.getCOGS());
        pstmt.setInt(8,bb.getSTOK());
        pstmt.setString(9,bb.getIDKATEGORI());
        pstmt.setString(10,bb.getJENISBARANG());
        boolean i = pstmt.execute();
        return i;
     }
    public void deleteDetails(String id)throws SQLException, ClassNotFoundException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("delete from BARANG where KODEBARANG=?");
        pstmt.setString(1, id);
        pstmt.executeUpdate();
    }
    public static String getID(Connection c) throws SQLException{
        int hasil=1;
        PreparedStatement pstmt = c.prepareStatement("select max(cast(KODEBARANG as int)) from BARANG");
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            if(rs.getString(1)!=null){
                hasil=rs.getInt(1)+1;
            }
        }
        
        return new PrintfFormat("%05d").sprintf(hasil);
    }
}
