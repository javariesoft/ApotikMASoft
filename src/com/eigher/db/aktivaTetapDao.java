/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eigher.db;
import com.eigher.model.aktivaTetap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author eigher
 */
public class aktivaTetapDao {
    public boolean insert(Connection con, aktivaTetap ut) throws SQLException
     {
        String sql="insert into AKTIVATETAP values (?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(1,ut.getID());
        pstmt.setString(2,ut.getNAMA());
        pstmt.setDate(3,ut.getTGLMASUK());
        pstmt.setDouble(4,ut.getHARGA());
        pstmt.setDouble(5,ut.getPERSENPENYUSUTAN());
        pstmt.setString(6,ut.getACCAKUMULASIPENYUSUTAN());
               
        boolean i = pstmt.execute();
        return i;
     }
    
    
    public List getAlldetails(Connection con)throws SQLException
    {

        PreparedStatement pstmt = con.prepareStatement("select * from AKTIVATETAP");
        ResultSet rs = pstmt.executeQuery();
        List list = new ArrayList();
        while(rs.next())
        {
            aktivaTetap ubean = new aktivaTetap();
            ubean.setID(rs.getString(1));
            ubean.setNAMA(rs.getString(2));
            ubean.setTGLMASUK(rs.getDate(3));
            ubean.setHARGA(rs.getDouble(4));
            ubean.setPERSENPENYUSUTAN(rs.getDouble(5));
            ubean.setACCAKUMULASIPENYUSUTAN(rs.getString(6));
            list.add(ubean);
        }
        rs.close();
        pstmt.close();

        return list;
    }
    
    public aktivaTetap getDetails(Connection con,String id)throws SQLException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from AKTIVATETAP where ID=?");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        aktivaTetap ubean = new aktivaTetap();
        while(rs.next())
        {
            ubean.setID(rs.getString(1));
            ubean.setNAMA(rs.getString(2));
            ubean.setTGLMASUK(rs.getDate(3));
            ubean.setHARGA(rs.getDouble(4));
            ubean.setPERSENPENYUSUTAN(rs.getDouble(5));
            ubean.setACCAKUMULASIPENYUSUTAN(rs.getString(6));
         }
        
        return ubean;
    }
    
    
    public boolean update(Connection con,aktivaTetap ut) throws SQLException
     {
        String sql="update AKTIVATETAP set NAMA=? ,TGLMASUK=? ,HARGA=? ,PERSENPENYUSUTAN=? ,ACCAKUMULASIPENYUSUTAN=? "
                + "where ID=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(1,ut.getNAMA());
        pstmt.setDate(2,ut.getTGLMASUK());
        pstmt.setDouble(3,ut.getHARGA());
        pstmt.setDouble(4,ut.getPERSENPENYUSUTAN());
        pstmt.setString(5,ut.getACCAKUMULASIPENYUSUTAN());
        pstmt.setString(6,ut.getID());
        boolean i = pstmt.execute();
        return i;
     }
  
    
   public void deleteDetails(Connection con,String id)throws SQLException, ClassNotFoundException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("delete from AKTIVATETAP where ID=?");
        pstmt.setString(1, id);
        pstmt.executeUpdate();
    }
   
//   public static String getID(Connection c) throws SQLException{
//        int hasil=1;
//        PreparedStatement pstmt = c.prepareStatement("select max(cast(KODEBARANG as int)) from BARANG");
//        ResultSet rs=pstmt.executeQuery();
//        if(rs.next()){
//            if(rs.getString(1)!=null){
//                hasil=rs.getInt(1)+1;
//            }
//        }
//        
//        return new PrintfFormat("%05d").sprintf(hasil);
//    }
}


