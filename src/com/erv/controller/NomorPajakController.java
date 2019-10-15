/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.controller;

import com.erv.db.NomorpajakDao;
import com.erv.function.JDBCAdapter;
import com.erv.model.NomorPajak;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javariesoft.FormNomorpajak;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class NomorPajakController {

    FormNomorpajak formNomorpajak;
    Date date = new Date();
    NomorPajak nomorPajak;

    public NomorPajakController(FormNomorpajak formNomorpajak) {
        this.formNomorpajak = formNomorpajak;
    }

    public void insert(Connection con) {
        String noawal = formNomorpajak.getTxtNomorawal().getText();
        String noakhir = formNomorpajak.getTxtNomorakhir().getText();
        String noakhirpakai = formNomorpajak.getTxtNomorakhirpakai().getText();
        Timestamp ts = new Timestamp(date.getTime());
        String tglrekam = ts.toString();
        String tglupdate = tglrekam;
        try {
            NomorpajakDao.insertIntoNomorpajak(con, noawal, noakhir, noakhirpakai, tglrekam, tglupdate);
            JOptionPane.showMessageDialog(formNomorpajak, "Entri Data Ok");
            clearForm();
        } catch (SQLException ex) {
            Logger.getLogger(NomorPajakController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Connection con) {

        String noawal = formNomorpajak.getTxtNomorawal().getText();
        String noakhir = formNomorpajak.getTxtNomorakhir().getText();
        String noakhirpakai = formNomorpajak.getTxtNomorakhirpakai().getText();
        Timestamp ts = new Timestamp(date.getTime());
        String tglupdate = ts.toString();
        nomorPajak.setNoawal(noawal);
        nomorPajak.setNoakhir(noakhir);
        nomorPajak.setNoakhirpakai(noakhirpakai);
        nomorPajak.setTglupdate(tglupdate);
        try {
            NomorpajakDao.updateNomorpajak(con, nomorPajak.getId(), nomorPajak.getNoawal(), nomorPajak.getNoakhir(), nomorPajak.getNoakhirpakai(), nomorPajak.getTglrekam(), nomorPajak.getTglupdate());
            JOptionPane.showMessageDialog(formNomorpajak, "Update Data Ok");
            clearForm();
        } catch (SQLException ex) {
            Logger.getLogger(NomorPajakController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Connection con) {
        try {
            NomorpajakDao.deleteFromNomorpajak(con, nomorPajak.getId());
            JOptionPane.showMessageDialog(formNomorpajak, "Delete Data Ok");
            clearForm();
        } catch (SQLException ex) {
            Logger.getLogger(NomorPajakController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getNomorPajak(Connection con, int id) {
        try {
            nomorPajak = NomorpajakDao.getNomorpajak(con, id);
            formNomorpajak.getTxtNomorawal().setText(nomorPajak.getNoawal());
            formNomorpajak.getTxtNomorakhir().setText(nomorPajak.getNoakhir());
            formNomorpajak.getTxtNomorakhirpakai().setText(nomorPajak.getNoakhirpakai());
        } catch (SQLException ex) {
            Logger.getLogger(NomorPajakController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearForm() {
        formNomorpajak.getTxtNomorawal().setText("");
        formNomorpajak.getTxtNomorakhir().setText("");
        formNomorpajak.getTxtNomorakhirpakai().setText("");
        nomorPajak = new NomorPajak();
    }

    public void reloadData(Connection con) {
        try {
            JDBCAdapter j = new JDBCAdapter(con);
            j.executeQuery("select * from nomorpajak order by id");
            formNomorpajak.getTabelNomorPajak().setModel(j);
            j.close();
        } catch (SQLException ex) {
            Logger.getLogger(NomorPajakController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
