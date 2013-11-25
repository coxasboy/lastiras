/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.business;

import br.com.lastiras.dao.VisitDaoLocal;
import br.com.lastiras.persistence.Visit;
import br.com.lastiras.util.DateCorrector;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author matheus
 */
@Stateless
public class VisitiHandler implements VisitiHandlerLocal {

    @EJB
    private VisitDaoLocal visitDao;
    
    private static final Logger logger = Logger.getLogger(VisitiHandler.class.getName());
    
    @Override
    public void createVisit(String ip){
        try{
            Date date = DateCorrector.getNow();
            Visit visit = new Visit();
            visit.setDateVisit(date);
            visit.setIp(ip);
            visitDao.create(visit);
        }
        catch(Exception e){
            logger.log(Level.SEVERE,"Erro criando visita",e);
        }
    }
    
    @Override
    public List<Visit> getVisitsCurrentDay(){
        try{
            Calendar c = Calendar.getInstance();
            Date date = c.getTime();
            c.set(Calendar.MILLISECOND, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 0);
            return visitDao.getVisitBetween(c.getTime(), date);        
        }
        catch(Exception e){
            logger.log(Level.SEVERE,"Erro pegando visitas",e);
            return new ArrayList<Visit>();
        }
    }
    
    @Override
    public List<Visit> getVisitsCurrentMonth(){
        try{
            Calendar c = Calendar.getInstance();
            Date date = c.getTime();
            c.set(Calendar.MILLISECOND, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);
            return visitDao.getVisitBetween(c.getTime(), date);        
        }
        catch(Exception e){
            logger.log(Level.SEVERE,"Erro pegando visitas",e);
            return new ArrayList<Visit>();
        }
    }
    
}
