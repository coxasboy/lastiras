/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.business.LasTirasStripHandler;
import br.com.lastiras.persistence.LasTirasStrip;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author matheus
 */
@Stateless
public class LasTirasStripDao extends GenericDaoImpl<LasTirasStrip, Long> implements LasTirasStripDaoLocal {

    private static final Logger logger = Logger.getLogger(LasTirasStripDao.class.getSimpleName());
    
    @Override
    public LasTirasStrip getLasTirasFromThisExactDate(Date date){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate = ?1");
        query.setParameter(1, date);
        query.setMaxResults(1);
        List<LasTirasStrip> list = (List<LasTirasStrip>)query.getResultList();
        if(list==null || list.isEmpty()){
            return null;
        }
        else{
            return list.get(0);
        }
    }
    
    @Override
    public List<LasTirasStrip> getLasTirasOldierThenThis(Date date){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate < ?1 order by x.stripDate desc");
        query.setParameter(1, date);
        return (List<LasTirasStrip>)query.getResultList();
    }
    
    @Override
    public LasTirasStrip getLastLasTiras(){
        Calendar calendar = Calendar.getInstance(); 
        calendar.set(Calendar.YEAR, 1900);
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate >= ?1 order by x.stripDate");
        query.setParameter(1, calendar.getTime());
        query.setMaxResults(1);
        List<LasTirasStrip> list = (List<LasTirasStrip>)query.getResultList();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public List<LasTirasStrip> getLasTirasEqualOrOldierThenThis(Date date){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate <= ?1 order by x.stripDate desc");
        query.setParameter(1, date);
        return (List<LasTirasStrip>)query.getResultList();
    }
    
    @Override
    public LasTirasStrip getLasTirasBefore(Date date){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate < ?1 order by x.stripDate desc");
        query.setParameter(1, date);
        query.setMaxResults(1);
        List<LasTirasStrip> resultList = (List<LasTirasStrip>)query.getResultList();
        if(resultList==null || resultList.isEmpty()){
            return null;
        }
        else{
            return resultList.get(0);
        }
    }
    
    @Override
    public LasTirasStrip getLasTirasAfter(Date date){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate > ?1 order by x.stripDate");
        query.setParameter(1, date);
        query.setMaxResults(1);
        List<LasTirasStrip> resultList = (List<LasTirasStrip>)query.getResultList();
        if(resultList==null || resultList.isEmpty()){
            return null;
        }
        else{
            return resultList.get(0);
        }
    }
    
    @Override
    public List<LasTirasStrip> getLasTirasNewerThenThis(Date date){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.stripDate > ?1 order by x.stripDate");
        query.setParameter(1, date);
        return (List<LasTirasStrip>)query.getResultList();
    }
    
    
}
