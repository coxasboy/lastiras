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
import br.com.lastiras.util.DateCorrector;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class LasTirasList implements Serializable{
    
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
        return "http://ps71499.dreamhost.com:80/LasTiras-web/faces/index.xhtml?q="+getCurrentFormattedDate();
    }
    
    public String getFacebookSrc(){
        String src = "http://www.facebook.com/plugins/like.php?"
                + "href=http%3A%2F%2Fwww.facebook.com%2Fpages%2FLas-Tiras%2F257280717636317"
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
        if(date == null){
            date = DateCorrector.getNow();
        }
        LasTirasStrip theOneAfter = lasTirasHandler.getLasTirasAfter(date);
        if(theOneAfter==null){
            date = DateCorrector.getNow();
        }
        else{
            date = theOneAfter.getStripDate();
        }
        return sdfReqParameter.format(date);
    }
    
    public String getLastDate() {
        Date date = getParameterDate();
        if(date == null){
            date = DateCorrector.getNow();
        }
        LasTirasStrip oldOnes = lasTirasHandler.getLasTirasBefore(date);
        if(oldOnes==null){
            date = DateCorrector.getNow();
        }
        else{
            date = oldOnes.getStripDate();
        }
        return sdfReqParameter.format(date);
    }
    
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
            logger.info("Parameter date: " + dateString);
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
            logger.info("Strip date: "+ stripDate);
            if (stripDate == null) {
                logger.info("Getting newer las tiras....");
                this.strip = this.lasTirasHandler.getNewerLasTiras();
            } else {
                Date now = DateCorrector.getNow();
                if(stripDate.after(now)){
                    logger.info("Now and Parameter: " + now + "/" + stripDate);
                    this.strip = this.lasTirasHandler.getNewerLasTiras();
                }
                else{
                    logger.info("Getting index: " + stripDate);
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
    
    public String getCurrentStripeUrlTweeter(long id){
        //20110823#IamDivNumber812
        LasTirasStrip lasStrip = getCurrentStripe();
        StringBuilder sb = new StringBuilder();
        sb.append("http://ps71499.dreamhost.com:80/LasTiras-web/faces/strip.xhtml?q=");
        sb.append(sdfReqParameter.format(lasStrip.getStripDate()));
        sb.append("&i=");
        sb.append(getIndexId(id,lasStrip));
        return sb.toString();
    }
    
    public String getFacebookSharerPage(long id){
        //https://www.facebook.com/sharer.php?s=100&p[url]=http%3A%2F%2Fps71499.dreamhost.com%2FLasTiras-web%2Ffaces%2Fstrip.xhtml%3Fq%3D20130607%26i%3D1&p[images][0]=http%3A%2F%2F2.bp.blogspot.com%2F_m8u1XODqWpc%2FS3WwhPf23ZI%2FAAAAAAAAAcU%2FBVU4H1QyYsY%2Fs1600%2Fwillpower.jpg&p[title]=TesteMM&p[summary]=lastiras"
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.facebook.com/sharer.php?s=100&");
        try {
            sb.append("p[url]="+URLEncoder.encode(getCurrentStripeUrl(id), "UTF-8"));
            sb.append("&p[images][0]="+URLEncoder.encode(getImageStripURL(id), "UTF-8"));
            sb.append("&p[title]="+URLEncoder.encode("Las Tiras", "UTF-8"));
            sb.append("&p[summary]="+URLEncoder.encode("Las Tiras apresenta tira do autor " + getAuthorName(id), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LasTirasList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
    
    private String getAuthorName(long id){
        LasTirasStrip lasStrip = getCurrentStripe();
        List<Strip> strips = lasStrip.getStrips();
        for (Strip strip : strips) {
            if(strip.getId().longValue()==id){
                return strip.getAuthor().getName();
            }
        }
        return null;
    }
    
    private String getImageStripURL(long id){
        LasTirasStrip lasStrip = getCurrentStripe();
        List<Strip> strips = lasStrip.getStrips();
        for (Strip strip : strips) {
            if(strip.getId().longValue()==id){
                return strip.getStripUrl();
            }
        }
        return null;
    }
    
    public String getCurrentStripeUrl(long id){
        //20110823#IamDivNumber812
        LasTirasStrip lasStrip = getCurrentStripe();
        StringBuilder sb = new StringBuilder();
        sb.append("http://ps71499.dreamhost.com:80/LasTiras-web/faces/strip.xhtml?q=");
        sb.append(sdfReqParameter.format(lasStrip.getStripDate()));
        sb.append("&i=");
        sb.append(getIndexId(id,lasStrip));
        return sb.toString();
    }
    
    public String acquireStripDate() {
        Date date = this.getCurrentStripe().getStripDate();
        logger.info("Date: " + date);
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
    
    /*------------------------------------*/
    
    public String acquireGooglePlusSrc(int id){
        String start = null;
        try {
            start = "https://plusone.google.com/u/0/_/+1/fastbutton?"+
                    "url="+URLEncoder.encode(getCurrentStripeUrl(id),"UTF-8")+
                    "&amp;size=standard"+
                    "&amp;count=true"+
                    "&amp;annotation="+
                    "&amp;hl=pt-BR"+
                    "&amp;parent=http%3A%2F%2Fwww.lastiras.com"+
                    "&amp;_methods=onPlusOne%2C_ready%2C_close%2C_open%2C_resizeMe";
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return start;
    }
    
    public String acquireTweeterSrc(long id){
        String start=
                "http://platform.twitter.com/widgets/tweet_button.html#"
                + "&amp;count=horizontal"
                + "&amp;id=twitter_tweet_button_14"
                + "&amp;lang=en"
                + "&amp;original_referer=http%3A%2F%2Fwww.lastiras.com"
                + "&amp;text=" + getCurrentStripeUrl(id)
                + "&amp;url=" + getCurrentStripeUrl(id)
                + "&amp;via=lastirasbr";
        return start;
    }
    
    public String acquireTweeterUrl(String stripUrl){
        String start = null;
        try {
            start = URLEncoder.encode(stripUrl,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LasTirasList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return start;
    }
    
    public String acquireFacebookSrc(long id){
        String start = null;
        try {
            start = "http://www.facebook.com/plugins/like.php?"
                    + "href=" + URLEncoder.encode(getCurrentStripeUrl(id),"UTF-8")
                    + "&amp;layout=button_count"
                    + "&amp;show_faces=false"
                    + "&amp;action=recommended"
                    + "&amp;colorscheme=light"
                    + "&amp;width=100"
                    + "&amp;height=21"
                    + "&amp;font="
                    + "&amp;locale=pt_BR";
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LasTirasList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return start;
    }
    
    //    public String acquireId(long id){
    //        return "stripId"+id;
    //    }
    //
    //    public String acquireJavaScript(long id, String stripUrl){
    //        return "changeWidth('" + acquireId(id) + "',700,'" + stripUrl +"')";
    //    }
    
    public long getIndexId(long id, LasTirasStrip lasTirasStrip){
        List<Strip> strips = lasTirasStrip.getStrips();
        int cont = 0;
        for (Strip strip : strips) {
            if(strip.getId().longValue()==id){
                return cont;
            }
            cont++;
        }
        return 8;
        //return "IamDivNumber"+id;
    }
    
    public String getDivId(long id){
        return "IamDivNumber"+id;
    }
}
