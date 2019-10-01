/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eigher.model;

/**
 *
 * @author eigher
 */
public class usertable {
    private String USERIDENTITY;
    private String SECURITYLOG;
    private String GROUPUSER;
    private String NAMALENGKAP;
    private int STATUSAKTIF;
    private String KETERANGAN;

    public String getGROUPUSER() {
        return GROUPUSER;
    }

    public void setGROUPUSER(String GROUPUSER) {
        this.GROUPUSER = GROUPUSER;
    }

    public String getNAMALENGKAP() {
        return NAMALENGKAP;
    }

    public void setNAMALENGKAP(String NAMALENGKAP) {
        this.NAMALENGKAP = NAMALENGKAP;
    }

    public String getSECURITYLOG() {
        return SECURITYLOG;
    }

    public void setSECURITYLOG(String SECURITYLOG) {
        this.SECURITYLOG = SECURITYLOG;
    }

    public String getUSERIDENTITY() {
        return USERIDENTITY;
    }

    public void setUSERIDENTITY(String USERIDENTITY) {
        this.USERIDENTITY = USERIDENTITY;
    }

    public int getSTATUSAKTIF() {
        return STATUSAKTIF;
    }

    public void setSTATUSAKTIF(int STATUSAKTIF) {
        this.STATUSAKTIF = STATUSAKTIF;
    }

    public String getKETERANGAN() {
        return KETERANGAN;
    }

    public void setKETERANGAN(String KETERANGAN) {
        this.KETERANGAN = KETERANGAN;
    }
    
    
}
