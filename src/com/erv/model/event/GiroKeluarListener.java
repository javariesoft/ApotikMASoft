/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.erv.model.event;

import com.erv.model.GiroKeluarModel;
import com.erv.model.Girokeluar;

/**
 *
 * @author ervan
 */
public interface GiroKeluarListener {
    public void onChange(GiroKeluarModel model);
    public void onInsert(Girokeluar girokeluar);
    public void onUpdate(Girokeluar girokeluar);
    public void onDelete();
}
