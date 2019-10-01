/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author TI-PNP
 */
public class TableKontrolTanggalModel extends AbstractTableModel {

    private List<Kontroltanggal> list = new ArrayList<Kontroltanggal>();

    public void setList(List<Kontroltanggal> list) {
        this.list = list;
    }

    public List<Kontroltanggal> getList() {
        return list;
    }
    
    public int getRowCount() {
        return list.size();
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getId();
            case 1:
                return list.get(rowIndex).getTanggal();
            case 2:
                int status = list.get(rowIndex).getStatus();
                if (status == 0) {
                    return "Buka";
                } else {
                    return "Tutup";
                }
            default:
                return null;
        }
    }

    public boolean add(Kontroltanggal e) {
        try {
            return list.add(e);
        } finally {
            fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
        }
    }

    public Kontroltanggal get(int index) {
        return list.get(index);
    }

    public Kontroltanggal set(int index, Kontroltanggal element) {
        try {
            return list.set(index, element);
        } finally {
            fireTableRowsUpdated(index, index);
        }
    }

    public Kontroltanggal remove(int index) {
        try {
            return list.remove(index);
        } finally {
            fireTableRowsDeleted(index, index);
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Tanggal";
            case 2:
                return "Status";
            default:
                return null;
        }
    }

}
