/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eigher.db;
import com.eigher.model.usertable;
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
public class usertableDao {
    public boolean insert(Connection con, usertable ut) throws SQLException
     {
        String sql="insert into USERTABLE values (?,ENCRYPT('AES', '00', STRINGTOUTF8(?)),?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(1,ut.getUSERIDENTITY());
        pstmt.setString(2,ut.getSECURITYLOG());
        pstmt.setString(3,ut.getGROUPUSER());
        pstmt.setString(4,ut.getNAMALENGKAP());
        pstmt.setInt(5,ut.getSTATUSAKTIF());
        pstmt.setString(6,ut.getKETERANGAN());       
        boolean i = pstmt.execute();
        return i;
     }
    
    
    public List getAlldetails(Connection con)throws SQLException
    {

        PreparedStatement pstmt = con.prepareStatement("select * from USERTABLE");
        ResultSet rs = pstmt.executeQuery();
        List list = new ArrayList();
        while(rs.next())
        {
            usertable ubean = new usertable();
            ubean.setUSERIDENTITY(rs.getString(1));
            ubean.setSECURITYLOG(rs.getString(2));
            ubean.setGROUPUSER(rs.getString(3));
            ubean.setNAMALENGKAP(rs.getString(4));
            ubean.setSTATUSAKTIF(rs.getInt(5));
            ubean.setKETERANGAN(rs.getString(6));
            list.add(ubean);
        }
        rs.close();
        pstmt.close();

        return list;
    }
    
    public usertable getDetails(Connection con,String id)throws SQLException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from USERTABLE where USERIDENTITY=?");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        usertable ubean = new usertable();
        while(rs.next())
        {
            ubean.setUSERIDENTITY(rs.getString(1));
            ubean.setSECURITYLOG(rs.getString(2));
            ubean.setGROUPUSER(rs.getString(3));
            ubean.setNAMALENGKAP(rs.getString(4));
            ubean.setSTATUSAKTIF(rs.getInt(5));
            ubean.setKETERANGAN(rs.getString(6));
         }
        
        return ubean;
    }
    
    public usertable showPass(Connection con,String id)throws SQLException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select TRIM(CHAR(0) FROM UTF8TOSTRING(DECRYPT('AES', '00', '?'))) as pass");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        usertable ubean = new usertable();
        while(rs.next())
        {
            ubean.setSECURITYLOG(rs.getString(2));
         }
        
        return ubean;
    }
    
    public boolean cekdata(Connection con,String id,String id1)throws SQLException
    {
       //here we will write code to get a single record from database
        boolean hasil=false;
        PreparedStatement pstmt = con.prepareStatement("select * from USERTABLE where STATUSAKTIF='0' AND USERIDENTITY=? and SECURITYLOG=ENCRYPT('AES', '00', STRINGTOUTF8(?))");
        pstmt.setString(1, id);
        pstmt.setString(2, id1);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next())
        {
            if(rs.getString(1)!=null){
                hasil=true;
            }
         }
        
        return hasil;
    }
    
    public boolean update(Connection con,usertable ut) throws SQLException
     {
        String sql="update USERTABLE set SECURITYLOG=ENCRYPT('AES', '00', STRINGTOUTF8(?)),GROUPUSER=?,NAMALENGKAP=?,STATUSAKTIF=?, KETERANGAN=? "
                + "where USERIDENTITY=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(1,ut.getSECURITYLOG());
        pstmt.setString(2,ut.getGROUPUSER());
        pstmt.setString(3,ut.getNAMALENGKAP());
        pstmt.setInt(4,ut.getSTATUSAKTIF());
        pstmt.setString(5,ut.getKETERANGAN());
        pstmt.setString(6,ut.getUSERIDENTITY());
        boolean i = pstmt.execute();
        return i;
     }
    public boolean updatePassw(Connection con,usertable ut) throws SQLException
     {
        String sql="update USERTABLE set SECURITYLOG=ENCRYPT('AES', '00', STRINGTOUTF8(?)) where USERIDENTITY=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(1,ut.getSECURITYLOG());
        pstmt.setString(2,ut.getUSERIDENTITY());
        boolean i = pstmt.execute();
        return i;
     }
    
   public void deleteDetails(Connection con,String id)throws SQLException, ClassNotFoundException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("delete from USERTABLE where USERIDENTITY=?");
        pstmt.setString(1, id);
        pstmt.executeUpdate();
    }
//    public static String getID(Connection c) throws SQLException{
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
