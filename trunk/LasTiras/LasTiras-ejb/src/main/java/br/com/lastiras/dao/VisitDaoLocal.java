/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.Visit;
import javax.ejb.Local;

/**
 *
 * @author matheus
 */
@Local
public interface VisitDaoLocal extends GenericDao<Visit, Long> {

    public java.util.List<br.com.lastiras.persistence.Visit> getVisitBetween(java.util.Date thisDate, java.util.Date anotherDate);
    
}
