/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.LasTirasStrip;
import javax.ejb.Local;

/**
 *
 * @author matheus
 */
@Local
public interface LasTirasStripDaoLocal extends GenericDao<LasTirasStrip, Long> {

    public java.util.List<br.com.lastiras.persistence.LasTirasStrip> getLasTirasOldierThenThis(java.util.Date date);

    public java.util.List<br.com.lastiras.persistence.LasTirasStrip> getLasTirasNewerThenThis(java.util.Date date);
    
}
