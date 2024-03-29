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

    public java.util.List<br.com.lastiras.persistence.LasTirasStrip> getLasTirasEqualOrOldierThenThis(java.util.Date date);

    public br.com.lastiras.persistence.LasTirasStrip getIndexLasTiras(java.util.Date date);

    public br.com.lastiras.persistence.LasTirasStrip getOlderLasTiras();
    
    public LasTirasStrip getLasTirasFromThisExactDate(Date date);

    public LasTirasStrip getLasTirasAfter(Date date);

    public LasTirasStrip getLasTirasBefore(Date date);
    
}
