/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;

import com.erv.db.GiroDao;
import com.erv.db.koneksi;
import com.erv.db.pelangganDao;
import com.erv.exception.GiroException;
import com.erv.model.event.GiroListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ervan
 */
public class GiroModel {

    private int ID;
    private String NOMORGIRO;
    private String TGLGIRO;
    private String TGLJTEMPO;
    private double JUMLAH;
    private String NAMAPENERIMA;
    private int STATUS;
    private String KODEPELANGGAN;
    private String BANKASAL;
    private int IDBANK;
    private String TGLCAIR;

    private GiroListener listener;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
        fireOnChage();
    }

    public String getNOMORGIRO() {
        return NOMORGIRO;
    }

    public void setNOMORGIRO(String NOMORGIRO) {
        this.NOMORGIRO = NOMORGIRO;
        fireOnChage();
    }

    public String getTGLGIRO() {
        return TGLGIRO;
    }

    public void setTGLGIRO(String TGLGIRO) {
        this.TGLGIRO = TGLGIRO;
        fireOnChage();
    }

    public String getTGLJTEMPO() {
        return TGLJTEMPO;
    }

    public void setTGLJTEMPO(String TGLJTEMPO) {
        this.TGLJTEMPO = TGLJTEMPO;
        fireOnChage();
    }

    public double getJUMLAH() {
        return JUMLAH;
    }

    public void setJUMLAH(double JUMLAH) {
        this.JUMLAH = JUMLAH;
        fireOnChage();
    }

    public String getNAMAPENERIMA() {
        return NAMAPENERIMA;
    }

    public void setNAMAPENERIMA(String NAMAPENERIMA) {
        this.NAMAPENERIMA = NAMAPENERIMA;
        fireOnChage();
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
        fireOnChage();
    }

    public String getKODEPELANGGAN() {
        return KODEPELANGGAN;
    }

    public void setKODEPELANGGAN(String KODEPELANGGAN) {
        this.KODEPELANGGAN = KODEPELANGGAN;
        fireOnChage();
    }

    public String getBANKASAL() {
        return BANKASAL;
    }

    public void setBANKASAL(String BANKASAL) {
        this.BANKASAL = BANKASAL;
        fireOnChage();
    }

    public int getIDBANK() {
        return IDBANK;
    }

    public void setIDBANK(int IDBANK) {
        this.IDBANK = IDBANK;
        fireOnChage();
    }

    public String getTGLCAIR() {
        return TGLCAIR;
    }

    public void setTGLCAIR(String TGLCAIR) {
        this.TGLCAIR = TGLCAIR;
    }
    

    public GiroListener getListener() {
        return listener;
    }

    public void setListener(GiroListener listener) {
        this.listener = listener;
    }

    protected void fireOnChage() {
        if (listener != null) {
            listener.onChange(this);
        }
    }

    protected void fireOnInsert(Giro giro) {
        if (listener != null) {
            listener.onInsert(giro);
        }
    }

    protected void fireOnUpdate(Giro giro) {
        if (listener != null) {
            listener.onUpdate(giro);
        }
    }

    protected void fireOnDelete() {
        if (listener != null) {
            listener.onDelete();
        }
    }

    public void resetGiro() throws ClassNotFoundException, SQLException {
        setID(0);
        //setNOMORGIRO(new GiroDao(koneksi.getKoneksiJ()).getKodeGiro());
        setNOMORGIRO("");
        setTGLGIRO("");
        setTGLJTEMPO("");
        setJUMLAH(0.0);
        setNAMAPENERIMA("-");
        setSTATUS(0);
        setKODEPELANGGAN("");
        setBANKASAL("");
        setIDBANK(0);
    }

    public void insert(Connection c) throws ClassNotFoundException, SQLException, GiroException {
        GiroDao giroDao = new GiroDao(c);
        Giro giro = new Giro();
        giro.setNOMORGIRO(NOMORGIRO);
        giro.setTGLGIRO(TGLGIRO);
        giro.setTGLJTEMPO(TGLJTEMPO);
        giro.setJUMLAH(JUMLAH);
        giro.setNAMAPENERIMA(NAMAPENERIMA);
        giro.setSTATUS(STATUS);
        giro.setKODEPELANGGAN(KODEPELANGGAN);
        giro.setBANKASAL(BANKASAL);
        giro.setIDBANK(IDBANK);
        giro.setPlg(new pelangganDao(c).getDetails(giro.getKODEPELANGGAN()));
        giroDao.insert(giro);
        fireOnInsert(giro);
        //giroDao.close();
    }

    public void update(Connection c) throws ClassNotFoundException, SQLException, GiroException {
        GiroDao giroDao = new GiroDao(c);
        Giro giro = new Giro();
        giro.setID(ID);
        giro.setNOMORGIRO(NOMORGIRO);
        giro.setTGLGIRO(TGLGIRO);
        giro.setTGLJTEMPO(TGLJTEMPO);
        giro.setJUMLAH(JUMLAH);
        giro.setNAMAPENERIMA(NAMAPENERIMA);
        giro.setSTATUS(STATUS);
        giro.setKODEPELANGGAN(KODEPELANGGAN);
        giro.setBANKASAL(BANKASAL);
        giro.setIDBANK(IDBANK);
        giro.setPlg(new pelangganDao(c).getDetails(giro.getKODEPELANGGAN()));
        giroDao.update(giro);
        fireOnUpdate(giro);
        //giroDao.close();
    }

    public void delete(Connection c) throws ClassNotFoundException, SQLException, GiroException {
        GiroDao giroDao = new GiroDao(c);
        giroDao.delete(ID);
        fireOnDelete();
        //giroDao.close();
    }

    public Giro getGiro(Connection c, int id) throws ClassNotFoundException, SQLException, GiroException {
        GiroDao giroDao = new GiroDao(c);
        Giro giro = giroDao.getGiro(id);
        //giroDao.close();
        return giro;
    }

    public List<Giro> selectAll(Connection c, String kriteria) throws ClassNotFoundException, SQLException, GiroException {
        GiroDao giroDao = new GiroDao(c);
        List<Giro> list =giroDao.selectAll(kriteria);
        //giroDao.close();
        return list;
    }

    public void angsuranPiutang(Connection c) throws GiroException {
        try {
            GiroDao giroDao = new GiroDao(c);
            Giro giro;
            giro = giroDao.getGiro(ID);
            giroDao.angsuranPiutang(giro, TGLCAIR);
            //giroDao.close();
        } catch (Exception ex) {
            throw new GiroException(ex.getMessage());
        }
    }

    public void updateStatusBatal(Connection c) throws GiroException {
        try {
            GiroDao giroDao = new GiroDao(c);
            Giro giro;
            giro = giroDao.getGiro(ID);
            giro.setSTATUS(2);
            giroDao.update(giro);
            //giroDao.close();
        } catch (Exception ex) {
            throw new GiroException(ex.getMessage());
        }
    }

}
