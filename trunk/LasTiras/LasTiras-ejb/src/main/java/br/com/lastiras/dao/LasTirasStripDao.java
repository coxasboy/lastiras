/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.LasTirasStrip;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author matheus
 */
@Stateless
public class LasTirasStripDao extends GenericDaoImpl<LasTirasStrip, Long> implements LasTirasStripDaoLocal {

    @Override
    public List<LasTirasStrip> getLasTirasOldierThenThis(Date date){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate < ?1 order by x.stripDate desc");
        query.setParameter(1, date);
        return (List<LasTirasStrip>)query.getResultList();
    }
    
    @Override
    public List<LasTirasStrip> getLasTirasNewerThenThis(Date date){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate > ?1 order by x.stripDate desc");
        query.setParameter(1, date);
        return (List<LasTirasStrip>)query.getResultList();
    }
    
    
}
