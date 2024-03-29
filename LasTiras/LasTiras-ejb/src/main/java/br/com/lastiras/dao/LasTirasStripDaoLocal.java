/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.LasTirasStrip;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author matheus
 */
@Local
public interface LasTirasStripDaoLocal extends GenericDao<LasTirasStrip, Long> {

    public java.util.List<br.com.lastiras.persistence.LasTirasStrip> getLasTirasOldierThenThis(java.util.Date date);

    public java.util.List<br.com.lastiras.persistence.LasTirasStrip> getLasTirasNewerThenThis(java.util.Date date);

    public java.util.List<br.com.lastiras.persistence.LasTirasStrip> getLasTirasEqualOrOldierThenThis(java.util.Date date);
    
    public br.com.lastiras.persistence.LasTirasStrip getLastLasTiras();

    public br.com.lastiras.persistence.LasTirasStrip getLasTirasFromThisExactDate(java.util.Date date);

    public LasTirasStrip getLasTirasAfter(Date date);

    public LasTirasStrip getLasTirasBefore(Date date);
    
}
