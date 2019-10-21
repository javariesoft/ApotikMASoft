/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  USER
 * Created: Jul 31, 2019
 */

CREATE TABLE PUBLIC.NOMORPAJAK (
    ID SERIAL(0) NOT NULL,
    NOAWAL VARCHAR(15) NOT NULL,
    NOAKHIR VARCHAR(15) NOT NULL,
    NOAKHIRPAKAI VARCHAR(15) NOT NULL,
    TGLREKAM TIMESTAMP(0) NOT NULL,
    TGLUPDATE TIMESTAMP NOT NULL,
    CONSTRAINT PK_NOMORPAJAK PRIMARY KEY (ID));