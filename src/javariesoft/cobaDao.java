/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javariesoft;

import com.erv.db.koneksi;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TI-PNP
 */
public class cobaDao {
    private final Connection con;

    public cobaDao(Connection con) {
        this.con = con;
    }

    public void close(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(cobaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
