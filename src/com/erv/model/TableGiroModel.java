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
 * @author ervan
 */
public class TableGiroModel extends AbstractTableModel {

    private List<Giro> list = new ArrayList<Giro>();

    public void setList(List<Giro> list) {
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
                return list.get(rowIndex).getID();
            case 1:
                return list.get(rowIndex).getNOMORGIRO();
            case 2:
                return list.get(rowIndex).getPlg().getNAMA();
            case 3:
                return list.get(rowIndex).getTGLGIRO();
            case 4:
                return list.get(rowIndex).getTGLJTEMPO();
            case 5:
                return list.get(rowIndex).getJUMLAH();
            case 6: 
                String status="";
                if(list.get(rowIndex).getSTATUS()==0){
                    status="Open";
                }else if(list.get(rowIndex).getSTATUS()==1){
                    status="Cair";
                }else if(list.get(rowIndex).getSTATUS()==2){
                    status="Batal";
                }
                return status;
            default:
                return null;
        }
    }

    public Giro get(int i) {
        return list.get(i);
    }

    public Giro set(int i, Giro e) {
        try {
            return list.set(i, e);
        }finally{
            fireTableCellUpdated(i, i);
        }
    }

    public boolean add(Giro e) {
        try{
            return list.add(e);
        }finally{
            fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
        }
    }

    public Giro remove(int i) {
        try{
        return list.remove(i);
        }finally{
            fireTableRowsDeleted(i, i);
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "NOMOR GIRO";
            case 2:
                return "PELANGGAN";
            case 3:
                return "TGL GIRO";
            case 4:
                return "TGL JATUH TEMPO";
            case 5:
                return "JUMLAH";
            case 6:
                return "STATUS";            
            default:
                return null;
        }
    }

    public void clear() {
        list.clear();
    }

    
}
