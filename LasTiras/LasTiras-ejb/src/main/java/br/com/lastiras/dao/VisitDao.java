/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.Visit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author matheus
 */
@Stateless
public class VisitDao extends GenericDaoImpl<Visit, Long> implements VisitDaoLocal {

    @Override
    public List<Visit> getVisitBetween(Date thisDate, Date anotherDate){
        Query query = getEntityManager().createQuery(
                "SELECT x FROM " + getoClass().getSimpleName() + " x WHERE x.dateVisit >= ?1 AND x.dateVisit < ?2");
        query.setParameter(1, thisDate);
        query.setParameter(2, anotherDate);
        return (List<Visit>)query.getResultList();
    }
    
}
