/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;

import com.erv.db.koneksi;
import com.erv.db.supplierDao;
import com.erv.function.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ervan
 */
public class TableGiroKeluarModel extends AbstractTableModel {
    java.text.DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    private List<Girokeluar> list = new ArrayList<Girokeluar>();

    public void setList(List<Girokeluar> list) {
        this.list = list;
    }

    public int getRowCount() {
        return list.size();
    }

    public int getColumnCount() {
        return 7;
    }

    public Object getValueAt(int rowIndex, int colIndex) {
        switch (colIndex) {
            case 0:
                return list.get(rowIndex).getId();
            case 1:
                return list.get(rowIndex).getNomorgiro();
            case 2:
                Connection c;
                supplier s=null;
                try {
                    c = koneksi.getKoneksiJ();
                    s = supplierDao.getDetails(c, list.get(rowIndex).getKodesupplier());
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TableGiroKeluarModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                return s.getNAMA();
            case 3:
                return d.format(list.get(rowIndex).getTglgiro());
            case 4:
                return d.format(list.get(rowIndex).getTgljtempo());
            case 5:
                return Util.toMoney((float) list.get(rowIndex).getJumlah());
            //return list.get(rowIndex).getJumlah();
            case 6:
                String stat = "";
                if (list.get(rowIndex).getStatus() == 0) {
                    stat = "Open";
                } else if (list.get(rowIndex).getStatus() == 1) {
                    stat = "Cair";
                } else if (list.get(rowIndex).getStatus() == 2) {
                    stat = "Batal";
                }
                return stat;
            default:
                return null;
        }
    }

    public Girokeluar get(int i) {
        return list.get(i);
    }

    public Girokeluar set(int i, Girokeluar e) {
        try {
            return list.set(i, e);
        } finally {
            fireTableCellUpdated(i, i);
        }
    }

    public boolean add(Girokeluar e) {
        try {
            return list.add(e);
        } finally {
            fireTableRowsInserted(getColumnCount() - 1, getRowCount() - 1);
        }
    }

    public Girokeluar remove(int i) {
        try {
            return list.remove(i);
        } finally {
            fireTableRowsDeleted(i, i);
        }
    }

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0:
                return "Id";
            case 1:
                return "Nomor Giro";
            case 2:
                return "Supplier";
            case 3:
                return "Tgl Giro";
            case 4:
                return "Tgl Jatuh Tempo";
            case 5:
                return "Jumlah";
            case 6:
                return "Status";
            default:
                return null;
        }
    }

}
