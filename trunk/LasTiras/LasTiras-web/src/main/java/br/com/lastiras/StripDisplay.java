/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras;

import br.com.lastiras.business.LasTirasStripHandlerLocal;
import br.com.lastiras.persistence.LasTirasStrip;
import br.com.lastiras.persistence.Strip;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Matheus
 */
@ManagedBean(name = "stripDisplay")
@RequestScoped
public class StripDisplay {

    @EJB
    private LasTirasStripHandlerLocal lasTirasHandler;
    private static final SimpleDateFormat sdfReqParameter = new SimpleDateFormat("yyyyMMdd");
    private String currentDate;
    private String currentIndex;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(String currentIndex) {
        this.currentIndex = currentIndex;
    }
    
    private String getQParameter() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return req.getParameter("q");
    }
    
    private String getIParameter() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return req.getParameter("i");
    }
    
    private int getParameterIndex(){
         try {
            String index = getIParameter();
            if (index == null) {
                return 0;
            }
            return Integer.parseInt(index);
        } catch (Exception e) {
            return 0;
        }
    }
    
    private Date getParameterDate() {
        try {
            String dateString = getQParameter();
            if (dateString == null) {
                return null;
            }
            return sdfReqParameter.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public StripDisplay() {
    }
    
    public String acquireImageUrl(){
        Date date = getParameterDate();
        if(date!=null){
            int index = getParameterIndex();
            LasTirasStrip lasTirasStrip = this.lasTirasHandler.getIndexLasTiras(date);
            int cont = 0;
            for (Strip strip : lasTirasStrip.getStrips()) {
                if(index==cont){
                    return strip.getStripUrl();
                }
                cont = cont + 1;
            }
            return "http://192.241.185.26/LasTiras-web/resources/images/lastiraserror.jpg";
        }
        else{
            return "http://192.241.185.26/LasTiras-web/resources/images/lastiraserror.jpg";
        }        
    }
    
    
    
    
}
