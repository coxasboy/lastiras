/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras;

import br.com.lastiras.business.EmailHandlerLocal;
import br.com.lastiras.business.LasTirasStripHandlerLocal;
import br.com.lastiras.business.VisitiHandlerLocal;
import br.com.lastiras.persistence.LasTirasStrip;
import br.com.lastiras.persistence.Strip;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author matheus
 */
@ManagedBean(name="lasTirasList")
@SessionScoped
public class LasTirasList {

    @EJB
    private LasTirasStripHandlerLocal lasTirasHandler;
    @EJB
    private EmailHandlerLocal emailHandler;
    private final Logger logger = Logger.getLogger(LasTirasList.class.getSimpleName());
    private LasTirasStrip strip;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String email;
    private String message;
    private int counter;
    private boolean visited = false;
    private boolean tryToGo2morrow = false;
    
    @EJB
    private VisitiHandlerLocal visitHandler;
    
    public LasTirasList() {
        
    }

    public int getCounter() {
        return counter;
    }
    
    

    public void setCounter(int counter) {
        
        if(counter>=0){
            if(counter==1){
                tryToGo2morrow = true;
            }
            this.counter = 0;
        }
        else{
            int maxCount = this.lasTirasHandler.getMaxLow();
            if((counter)<-maxCount){
                this.counter = -maxCount;
            }
            else{
                this.counter = counter;                
            }
        }
    }
    
    
    public LasTirasStrip getCurrentStripe() {
        if(!visited){
            HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();  
            String ip = httpServletRequest.getRemoteAddr();  
            visitHandler.createVisit(ip);
            visited = true;
        }
        if (strip == null) {
            if(counter>=0){
                this.strip = this.lasTirasHandler.getNewerLasTiras();
            }
            else{
                this.strip = this.lasTirasHandler.getIndexLasTiras(counter);
            }
        }
        return strip;
    }
     
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasMessage() {
        if (this.message != null && this.message.length() > 0) {
            return true;
        }
        return false;
    }

    public void saveEmail() {
        if (this.email == null || this.email.length() < 1) {
            this.message = "EMAIL em branco";
        } else {
            this.message = this.emailHandler.digestEmail(email);
        }
    }

    public String acquireStripDate() {
        Date date = this.getCurrentStripe().getStripDate();
        logger.log(Level.INFO, "Date: " + date);
        return sdf.format(date);
    }
    
    public String acquireId(Strip stripSelected){
        return "imageId"+stripSelected.getId();
    }
    
    public String acquireJavaScritCall(Strip stripSelected){
        return "loadImage('" + stripSelected.getStripUrl() + "','" + acquireId(stripSelected) + "')";
    }
        
    public void cleanPage(){
        message = null;
        email = null;
        strip = null;
    }
    
    public String toLasTiras(){
        cleanPage();
        return "index.xhtml";
    }
    
    public String toAuthors(){
        cleanPage();
        return "authors.xhtml";        
    }
    
     public String toContacts(){
        cleanPage();
        return "contato.xhtml";        
    }
     
     public String toImageToNext(){
         if(counter!=0){
             return "resources/images/proximoOff.png";
         }
         else{
             if(tryToGo2morrow){
                return "resources/images/samanha.png";
             }
             else{
                 return "resources/images/proximoOff.png";
             }
         }
     }
     
     public String toImageToNextOn(){
         if(counter!=0){
             return "resources/images/proximoOn.png";
         }
         else{
             if(tryToGo2morrow){
                return "resources/images/samanha.png";
             }
             else{
                 return "resources/images/proximoOn.png";
             }
         }
     }
}
