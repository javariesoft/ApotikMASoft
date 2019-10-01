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
public class aktivaTetap {
    private String ID;
    private String NAMA;
    private Date TGLMASUK;
    private double HARGA;
    private double PERSENPENYUSUTAN;
    private String ACCAKUMULASIPENYUSUTAN;

    public String getACCAKUMULASIPENYUSUTAN() {
        return ACCAKUMULASIPENYUSUTAN;
    }

    public void setACCAKUMULASIPENYUSUTAN(String ACCAKUMULASIPENYUSUTAN) {
        this.ACCAKUMULASIPENYUSUTAN = ACCAKUMULASIPENYUSUTAN;
    }

    public double getHARGA() {
        return HARGA;
    }

    public void setHARGA(double HARGA) {
        this.HARGA = HARGA;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAMA() {
        return NAMA;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public double getPERSENPENYUSUTAN() {
        return PERSENPENYUSUTAN;
    }

    public void setPERSENPENYUSUTAN(double PERSENPENYUSUTAN) {
        this.PERSENPENYUSUTAN = PERSENPENYUSUTAN;
    }

    public Date getTGLMASUK() {
        return TGLMASUK;
    }

    public void setTGLMASUK(Date TGLMASUK) {
        this.TGLMASUK = TGLMASUK;
    }

}
