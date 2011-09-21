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
import java.util.List;
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
@ManagedBean(name = "lasTirasList")
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
    //private int counter;
    private boolean visited = false;
    private static final SimpleDateFormat sdfReqParameter = new SimpleDateFormat("yyyyMMdd");
    private String currentDate;
    @EJB
    private VisitiHandlerLocal visitHandler;

    public LasTirasList() {
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
    
    public String getLasTirasLink(){
        return "http://www.kmtech.com.br:8080/LasTiras-web/faces/index.xhtml?q="+getCurrentFormattedDate();
    }
    
    public String getFacebookSrc(){
        String src = "http://www.facebook.com/plugins/like.php?"
                + "href= http%3A%2F%2Fwww.kmtech.com.br%3A8080%2FLasTiras-web%2Ffaces%2Findex.xhtml?q="+getCurrentFormattedDate()
                + "&amp;layout=button_count"
                + "&amp;show_faces=false"
                + "&amp;action=recommended"
                + "&amp;colorscheme=light"
                + "&amp;width=175"
                + "&amp;height=21"
                + "&amp;font="
                + "&amp;locale=pt_BR";
        return src;
    }
    
    private String getCurrentFormattedDate(){
        LasTirasStrip lasStrip = getCurrentStripe();
        Date date = lasStrip.getStripDate();
        return sdfReqParameter.format(date);
    }

    public String getNextDate() {
        Date date = getParameterDate();
        LasTirasStrip lasStrip = getCurrentStripe();
        List<LasTirasStrip> newOnes = lasTirasHandler.getLasTirasNewerThenThis(lasStrip.getStripDate());
        if(newOnes==null || newOnes.isEmpty()){
            date = lasStrip.getStripDate();
        }
        else{
            date = newOnes.get(0).getStripDate();
        }
        return sdfReqParameter.format(date);
    }

    public String getLastDate() {
        Date date = getParameterDate();

        LasTirasStrip lasStrip = getCurrentStripe();
        
        date = lasStrip.getStripDate();

        Date dayBefore = new Date(date.getTime() - (1000 * 60 * 60 * 24));
        return sdfReqParameter.format(dayBefore);
    }

//    public int getCounter() {
//        return counter;
//    }
//    
//    public void setCounter(int counter) {
//        
//        if(counter>=0){
//            if(counter==1){
//                tryToGo2morrow = true;
//            }
//            this.counter = 0;
//        }
//        else{
//            int maxCount = this.lasTirasHandler.getMaxLow();
//            if((counter)<-maxCount){
//                this.counter = -maxCount;
//            }
//            else{
//                this.counter = counter;                
//            }
//        }
//    }
    private String getPageParameter() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return req.getParameter("q");
    }

    private Date getParameterDate() {
        try {
            String dateString = getPageParameter();
            if (dateString == null) {
                return null;
            }
            return sdfReqParameter.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }
    

    public LasTirasStrip getCurrentStripe() {
        if (!visited) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String ip = httpServletRequest.getRemoteAddr();
            visitHandler.createVisit(ip);
            visited = true;
        }

        if (strip == null) {
            Date stripDate = getParameterDate();
            if (stripDate == null) {
                this.strip = this.lasTirasHandler.getNewerLasTiras();
            } else {
                Date today = new Date();
                if(stripDate.after(today)){
                    this.strip = this.lasTirasHandler.getIndexLasTiras(today);                
                }
                else{
                    this.strip = this.lasTirasHandler.getIndexLasTiras(stripDate);                
                }
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

    public String acquireId(Strip stripSelected) {
        return "imageId" + stripSelected.getId();
    }

    public String acquireJavaScritCall(Strip stripSelected) {
        return "loadImage('" + stripSelected.getStripUrl() + "','" + acquireId(stripSelected) + "')";
    }

    public void cleanPage() {
        message = null;
        email = null;
        strip = null;
    }

    public String toLasTiras() {
        cleanPage();
        return "index.xhtml";
    }

    public String toAuthors() {
        cleanPage();
        return "authors.xhtml";
    }

    public String toContacts() {
        cleanPage();
        return "contato.xhtml";
    }
}
