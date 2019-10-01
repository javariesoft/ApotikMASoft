/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.erv.model.event;

import com.erv.model.Giro;
import com.erv.model.GiroModel;

/**
 *
 * @author ervan
 */
public interface GiroListener {
    public void onChange(GiroModel model);
    public void onInsert(Giro cabang);
    public void onUpdate(Giro cabang);
    public void onDelete();
}
