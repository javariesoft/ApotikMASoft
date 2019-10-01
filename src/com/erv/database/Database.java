/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.erv.database;

import com.erv.db.koneksi;
import com.erv.model.dao.GirokeluarDAO;
import com.erv.model.orm.GirokeluarDAOImpl;
import java.sql.SQLException;

/**
 *
 * @author ervan
 */
public class Database {
    private static GirokeluarDAO giroKeluarDao;

    public static GirokeluarDAO getGiroKeluarDao() throws ClassNotFoundException, SQLException {
        if(giroKeluarDao==null){
            giroKeluarDao = new GirokeluarDAOImpl(koneksi.getKoneksiJ()); 
        }
        return giroKeluarDao;
    }
}
