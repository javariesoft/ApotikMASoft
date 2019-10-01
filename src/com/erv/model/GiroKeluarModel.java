/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;

import com.erv.database.Database;
import com.erv.db.koneksi;
import com.erv.db.supplierDao;
import com.erv.exception.GiroException;
import com.erv.exception.JavarieException;
import com.erv.model.dao.GirokeluarDAO;
import com.erv.model.event.GiroKeluarListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ervan
 */
public class GiroKeluarModel {
    /* ID, PK */

    protected int id;

    /* NOMORGIRO */
    protected String nomorgiro;

    /* TGLGIRO */
    protected java.util.Date tglgiro;

    /* TGLJTEMPO */
    protected java.util.Date tgljtempo;

    /* JUMLAH */
    protected double jumlah;

    /* NAMAPENERIMA */
    protected String namapenerima;

    /* STATUS */
    protected int status;

    /* KODESUPPLIER */
    protected String kodesupplier;

    /* IDBANK */
    protected String idbank;

    /* BANKTUJUAN */
    protected String banktujuan;
    protected String tanggalcair;
    private GiroKeluarListener listener;

    public GiroKeluarModel() {
        tglgiro = new Date();
        tgljtempo = new Date();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        fireOnChage();
    }

    public String getNomorgiro() {
        return nomorgiro;
    }

    public void setNomorgiro(String nomorgiro) {
        this.nomorgiro = nomorgiro;
        fireOnChage();
    }

    public Date getTglgiro() {
        return tglgiro;
    }

    public void setTglgiro(Date tglgiro) {
        this.tglgiro = tglgiro;
        fireOnChage();
    }

    public Date getTgljtempo() {
        return tgljtempo;
    }

    public void setTgljtempo(Date tgljtempo) {
        this.tgljtempo = tgljtempo;
        fireOnChage();
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
        fireOnChage();
    }

    public String getNamapenerima() {
        return namapenerima;
    }

    public void setNamapenerima(String namapenerima) {
        this.namapenerima = namapenerima;
        fireOnChage();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        fireOnChage();
    }

    public String getKodesupplier() {
        return kodesupplier;
    }

    public void setKodesupplier(String kodesupplier) {
        this.kodesupplier = kodesupplier;
        fireOnChage();
    }

    public String getIdbank() {
        return idbank;
    }

    public void setIdbank(String idbank) {
        this.idbank = idbank;
        fireOnChage();
    }

    public String getBanktujuan() {
        return banktujuan;
    }

    public void setBanktujuan(String banktujuan) {
        this.banktujuan = banktujuan;
        fireOnChage();
    }

    public String getTanggalcair() {
        return tanggalcair;
    }

    public void setTanggalcair(String tanggalcair) {
        this.tanggalcair = tanggalcair;
    }

    public GiroKeluarListener getListener() {
        return listener;
    }

    public void setListener(GiroKeluarListener listener) {
        this.listener = listener;
    }

    protected void fireOnChage() {
        if (listener != null) {
            listener.onChange(this);
        }
    }

    protected void fireOnInsert(Girokeluar girokeluar) {
        if (listener != null) {
            listener.onInsert(girokeluar);
        }
    }

    protected void fireOnUpdate(Girokeluar girokeluar) {
        if (listener != null) {
            listener.onUpdate(girokeluar);
        }
    }

    protected void fireOnDelete() {
        if (listener != null) {
            listener.onDelete();
        }
    }

    public void resetGiroKeluar() throws ClassNotFoundException, SQLException {
        //GirokeluarDAO dao = Database.getGiroKeluarDao();
        setId(0);
        //setNomorgiro(dao.getKodeGiroKeluar());
        setNomorgiro("");
        setTglgiro(new Date());
        setTgljtempo(new Date());
        setJumlah(0.0);
        setNamapenerima("");
        setStatus(0);
        setKodesupplier("");
        setBanktujuan("");
        setIdbank("");
    }

    public void insert(Connection c) throws JavarieException, ClassNotFoundException, SQLException {
        GirokeluarDAO girokeluarDAO = Database.getGiroKeluarDao();
        Girokeluar girokeluar = new Girokeluar();
        girokeluar.setNomorgiro(nomorgiro);
        girokeluar.setTglgiro(tglgiro);
        girokeluar.setTgljtempo(tgljtempo);
        girokeluar.setJumlah(jumlah);
        girokeluar.setNamapenerima(namapenerima);
        girokeluar.setStatus(status);
        girokeluar.setKodesupplier(kodesupplier);
        girokeluar.setBanktujuan(banktujuan);
        girokeluar.setIdbank(idbank);
        girokeluarDAO.create(girokeluar);
        fireOnInsert(girokeluar);
    }

    public void update(Connection c) throws ClassNotFoundException, SQLException, JavarieException {
        GirokeluarDAO girokeluarDAO = Database.getGiroKeluarDao();
        Girokeluar girokeluar = new Girokeluar();
        girokeluar.setId(id);
        girokeluar.setNomorgiro(nomorgiro);
        girokeluar.setTglgiro(tglgiro);
        girokeluar.setTgljtempo(tgljtempo);
        girokeluar.setJumlah(jumlah);
        girokeluar.setNamapenerima(namapenerima);
        girokeluar.setStatus(status);
        girokeluar.setKodesupplier(kodesupplier);
        girokeluar.setBanktujuan(banktujuan);
        girokeluar.setIdbank(idbank);
        girokeluarDAO.update(girokeluar);
        fireOnUpdate(girokeluar);
    }

    public void delete() throws ClassNotFoundException, SQLException, JavarieException {
        GirokeluarDAO girokeluarDAO = Database.getGiroKeluarDao();
        GirokeluarKey key = new GirokeluarKey();
        key.setId(id);
        girokeluarDAO.delete(key);
        fireOnDelete();
    }
    
    public Girokeluar get(int id) throws ClassNotFoundException, SQLException, JavarieException {
        GirokeluarDAO girokeluarDAO = Database.getGiroKeluarDao();
        GirokeluarKey key = new GirokeluarKey();
        key.setId(id);
        Girokeluar girokeluar = girokeluarDAO.load(key);
        return girokeluar;
    }
    
}
