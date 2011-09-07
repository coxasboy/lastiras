/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.business;

import br.com.lastiras.persistence.LasTirasStrip;
import br.com.lastiras.persistence.Strip;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author matheus
 */
@Local
public interface LasTirasStripHandlerLocal {

    public LasTirasStrip createLasTirasStrip(int day, int month, int year, Strip ... strips);
    
    public br.com.lastiras.persistence.LasTirasStrip getLasTirasFromToday();

    public List<LasTirasStrip> getLasTirasOldierThenThis(java.util.Date date);

    public List<LasTirasStrip> getLasTirasNewerThenThis(Date date);

    public br.com.lastiras.persistence.LasTirasStrip getNewerLasTiras();

    public br.com.lastiras.persistence.LasTirasStrip getIndexLasTiras(int counter);

    public int getMaxLow();
    
}
