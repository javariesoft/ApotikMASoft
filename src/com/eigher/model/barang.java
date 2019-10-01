/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eigher.model;

/**
 *
 * @author erwadi
 */
public class barang {
    private String KODEBARANG;
    private String NAMABARANG;
    private String SATUAN;
    private double HARGA;
    private String KODEAKUN;
    private String PENDAPATAN_ACC;
    private String COGS_ACC;
    private double COGS;
    private int STOK;
    private String IDKATEGORI;
    private String JENISBARANG;

    public double getCOGS() {
        return COGS;
    }

    public void setCOGS(double COGS) {
        this.COGS = COGS;
    }

    public String getCOGS_ACC() {
        return COGS_ACC;
    }

    public void setCOGS_ACC(String COGS_ACC) {
        this.COGS_ACC = COGS_ACC;
    }

    public double getHARGA() {
        return HARGA;
    }

    public void setHARGA(double HARGA) {
        this.HARGA = HARGA;
    }

    public String getKODEAKUN() {
        return KODEAKUN;
    }

    public void setKODEAKUN(String KODEAKUN) {
        this.KODEAKUN = KODEAKUN;
    }

    public String getKODEBARANG() {
        return KODEBARANG;
    }

    public void setKODEBARANG(String KODEBARANG) {
        this.KODEBARANG = KODEBARANG;
    }

    public String getNAMABARANG() {
        return NAMABARANG;
    }

    public void setNAMABARANG(String NAMABARANG) {
        this.NAMABARANG = NAMABARANG;
    }

    public String getSATUAN() {
        return SATUAN;
    }

    public void setSATUAN(String SATUAN) {
        this.SATUAN = SATUAN;
    }

    public int getSTOK() {
        return STOK;
    }

    public void setSTOK(int STOK) {
        this.STOK = STOK;
    }

    public String getIDKATEGORI() {
        return IDKATEGORI;
    }

    public void setIDKATEGORI(String IDKATEGORI) {
        this.IDKATEGORI = IDKATEGORI;
    }

    public String getJENISBARANG() {
        return JENISBARANG;
    }

    public void setJENISBARANG(String JENISBARANG) {
        this.JENISBARANG = JENISBARANG;
    }

    public String getPENDAPATAN_ACC() {
        return PENDAPATAN_ACC;
    }

    public void setPENDAPATAN_ACC(String PENDAPATAN_ACC) {
        this.PENDAPATAN_ACC = PENDAPATAN_ACC;
    }
    
}
