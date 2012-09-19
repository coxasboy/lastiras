/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.ws;

import br.com.lastiras.business.EmailHandlerLocal;
import br.com.lastiras.business.LasTirasStripHandlerLocal;
import br.com.lastiras.business.VisitiHandlerLocal;
import br.com.lastiras.persistence.Email;
import br.com.lastiras.persistence.LasTirasStrip;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;

/**
 *
 * @author matheus
 */
@WebService(serviceName = "LasTirasWs")
@Stateless()
public class LasTirasWs {

    @EJB
    private LasTirasStripHandlerLocal lasTirasHandler; 
    
    @EJB
    private EmailHandlerLocal emailHandler;
    
    @EJB
    private VisitiHandlerLocal visitHandler;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    
    /** This is a sample web service operation */
    @WebMethod
    public LasTirasStrip getLasTirasFromToday() {
        try{
            LasTirasStrip strip = lasTirasHandler.getLasTirasFromToday();
            return strip;
        }
        catch(Exception e){
            return null;
        }
    }
    
    @WebMethod
    public LasTirasStrip getLasTirasByDate(String date) {
        try{
            LasTirasStrip strip = lasTirasHandler.getLasTirasFromThisExactDate(sdf.parse(date));
            return strip;
        }
        catch(Exception e){
            return null;
        }
    }
    
    @WebMethod
    public List<Email> getAllMail() {
        try{
            return emailHandler.getAllMails();
        }
        catch(Exception e){
            return null;
        }
    }
    
    @WebMethod
    public int getNumberOfVisitsToday() {
        try{
            return visitHandler.getVisitsCurrentDay().size();
        }
        catch(Exception e){
            return 0;
        }
    }
    
    @WebMethod
    public int getNumberOfVisitsLastMonth() {
        try{
            return visitHandler.getVisitsCurrentMonth().size();
        }
        catch(Exception e){
            return 0;
        }
    }
}
