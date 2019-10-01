/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eigher.db;

import com.eigher.model.loghistory;
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
public class loghistoryDao {

    public boolean insert(Connection con, loghistory lh) throws SQLException {
        int no = 0;
        PreparedStatement pstmt1 = con.prepareStatement("select max(id) from LOGHISTORY");
        ResultSet rs = pstmt1.executeQuery();
        if (rs.next()) {
            no = rs.getInt(1) + 1;
        } else {
            no = 1;
        }
        String sql = "insert into LOGHISTORY values (?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setInt(1, no);
        pstmt.setString(2, lh.getUSERIDENTITY());
        pstmt.setString(3, lh.getTANGGAL());
        pstmt.setString(4, lh.getJAM());
        pstmt.setString(5, lh.getTABEL());
        pstmt.setString(6, lh.getNOREFF());
        pstmt.setString(7, lh.getAKSI());
        pstmt.setString(8, lh.getKETERANGAN());

        boolean i = pstmt.execute();
        return i;
    }

    public List getAlldetails(Connection con) throws SQLException {

        PreparedStatement pstmt = con.prepareStatement("select * from LOGHISTORY");
        ResultSet rs = pstmt.executeQuery();
        List list = new ArrayList();
        while (rs.next()) {
            loghistory ubean = new loghistory();
            ubean.setID(rs.getInt(1));
            ubean.setUSERIDENTITY(rs.getString(2));
            ubean.setTANGGAL(rs.getString(3));
            ubean.setJAM(rs.getString(4));
            ubean.setTABEL(rs.getString(5));
            ubean.setNOREFF(rs.getString(6));
            ubean.setAKSI(rs.getString(7));
            ubean.setKETERANGAN(rs.getString(8));
            list.add(ubean);
        }
        rs.close();
        pstmt.close();

        return list;
    }

    public loghistory getDetails(Connection con, String id) throws SQLException {
        //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from LOGHISTORY where ID=?");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        loghistory ubean = new loghistory();
        while (rs.next()) {
            ubean.setID(rs.getInt(1));
            ubean.setUSERIDENTITY(rs.getString(2));
            ubean.setTANGGAL(rs.getString(3));
            ubean.setJAM(rs.getString(4));
            ubean.setTABEL(rs.getString(5));
            ubean.setNOREFF(rs.getString(6));
            ubean.setAKSI(rs.getString(7));
            ubean.setKETERANGAN(rs.getString(8));
        }

        return ubean;
    }
//    public boolean cekdata(Connection con,String id,String id1)throws SQLException
//    {
//       //here we will write code to get a single record from database
//        boolean hasil=false;
//        PreparedStatement pstmt = con.prepareStatement("select * from USERTABLE where USERIDENTITY=? and SECURITYLOG=ENCRYPT('AES', '00', STRINGTOUTF8(?))");
//        pstmt.setString(1, id);
//        pstmt.setString(2, id1);
//        ResultSet rs = pstmt.executeQuery();
//        if(rs.next())
//        {
//            if(rs.getString(1)!=null){
//                hasil=true;
//            }
//         }
//        
//        return hasil;
//    }
//    public boolean update(Connection con,loghistory ut) throws SQLException
//     {
//        String sql="update USERTABLE set SECURITYLOG=ENCRYPT('AES', '00', STRINGTOUTF8(?)),GROUPUSER=?,NAMALENGKAP=? "
//                + "where USERIDENTITY=?";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        //set values to prepared statement object by getting values from bean object
//        pstmt.setString(1,ut.getSECURITYLOG());
//        pstmt.setString(2,ut.getGROUPUSER());
//        pstmt.setString(3,ut.getNAMALENGKAP());
//        pstmt.setString(4,ut.getUSERIDENTITY());
//        boolean i = pstmt.execute();
//        return i;
//     }
//    public boolean updatePassw(Connection con,usertable ut) throws SQLException
//     {
//        String sql="update USERTABLE set SECURITYLOG=ENCRYPT('AES', '00', STRINGTOUTF8(?)) where USERIDENTITY=?";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        //set values to prepared statement object by getting values from bean object
//        pstmt.setString(1,ut.getSECURITYLOG());
//        pstmt.setString(2,ut.getUSERIDENTITY());
//        boolean i = pstmt.execute();
//        return i;
//     }
//    
//   public void deleteDetails(Connection con,String id)throws SQLException, ClassNotFoundException
//    {
//       //here we will write code to get a single record from database
//        PreparedStatement pstmt = con.prepareStatement("delete from USERTABLE where USERIDENTITY=?");
//        pstmt.setString(1, id);
//        pstmt.executeUpdate();
//    }
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
