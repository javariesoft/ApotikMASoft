/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eigher.model;

import java.sql.Date;

/**
 *
 * @author eigher
 */
public class loghistory {
    private int ID;
    private String USERIDENTITY;
    private String TANGGAL;
    private String JAM;
    private String TABEL;
    private String NOREFF;
    private String AKSI;
    private String KETERANGAN;

    public String getAKSI() {
        return AKSI;
    }

    public void setAKSI(String AKSI) {
        this.AKSI = AKSI;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getJAM() {
        return JAM;
    }

    public void setJAM(String JAM) {
        this.JAM = JAM;
    }

    public String getKETERANGAN() {
        return KETERANGAN;
    }

    public void setKETERANGAN(String KETERANGAN) {
        this.KETERANGAN = KETERANGAN;
    }

    public String getNOREFF() {
        return NOREFF;
    }

    public void setNOREFF(String NOREFF) {
        this.NOREFF = NOREFF;
    }

    public String getTABEL() {
        return TABEL;
    }

    public void setTABEL(String TABEL) {
        this.TABEL = TABEL;
    }

    public String getTANGGAL() {
        return TANGGAL;
    }

    public void setTANGGAL(String TANGGAL) {
        this.TANGGAL = TANGGAL;
    }

    public String getUSERIDENTITY() {
        return USERIDENTITY;
    }

    public void setUSERIDENTITY(String USERIDENTITY) {
        this.USERIDENTITY = USERIDENTITY;
    }

    
}
