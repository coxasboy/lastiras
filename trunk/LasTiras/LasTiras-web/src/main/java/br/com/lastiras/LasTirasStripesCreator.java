/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras;

import br.com.lastiras.business.AuthorHandlerLocal;
import br.com.lastiras.business.LasTirasStripHandlerLocal;
import br.com.lastiras.business.StripHandlerLocal;
import br.com.lastiras.persistence.Author;
import br.com.lastiras.persistence.LasTirasStrip;
import br.com.lastiras.persistence.Strip;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author matheus
 */
@ManagedBean(name = "lasTirasStripesCreator")
@SessionScoped
public class LasTirasStripesCreator {

    @EJB
    private LasTirasStripHandlerLocal stripLasTirashandler;
    @EJB
    private AuthorHandlerLocal authorhandler;
    @EJB
    private StripHandlerLocal stripHandler;
    private final Logger logger = Logger.getLogger(LasTirasStripesCreator.class.getSimpleName());
    
    private int day;
    private int month;
    private int year;
    private int authorIdOne;
    private String stripeUrlOne="";
    private int authorIdTwo;
    private String stripeUrlTwo="";
    private int authorIdThree;
    private String stripeUrlThree="";
    private int authorIdFour;
    private String stripeUrlFour="";
    private int authorIdFive;
    private String stripeUrlFive="";
    private int authorIdSix;
    private String stripeUrlSix="";

    public LasTirasStripesCreator() {
    }

    public List<Author> getAuthors() {
        return authorhandler.getAll();
    }

    public int getAuthorIdFive() {
        return authorIdFive;
    }

    public void setAuthorIdFive(int authorIdFive) {
        this.authorIdFive = authorIdFive;
    }

    public int getAuthorIdFour() {
        return authorIdFour;
    }

    public void setAuthorIdFour(int authorIdFour) {
        this.authorIdFour = authorIdFour;
    }

    public int getAuthorIdOne() {
        return authorIdOne;
    }

    public void setAuthorIdOne(int authorIdOne) {
        this.authorIdOne = authorIdOne;
    }

    public int getAuthorIdSix() {
        return authorIdSix;
    }

    public void setAuthorIdSix(int authorIdSix) {
        this.authorIdSix = authorIdSix;
    }

    public int getAuthorIdThree() {
        return authorIdThree;
    }

    public void setAuthorIdThree(int authorIdThree) {
        this.authorIdThree = authorIdThree;
    }

    public int getAuthorIdTwo() {
        return authorIdTwo;
    }

    public void setAuthorIdTwo(int authorIdTwo) {
        this.authorIdTwo = authorIdTwo;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getStripeUrlFive() {
        return stripeUrlFive;
    }

    public void setStripeUrlFive(String stripeUrlFive) {
        this.stripeUrlFive = stripeUrlFive;
    }

    public String getStripeUrlFour() {
        return stripeUrlFour;
    }

    public void setStripeUrlFour(String stripeUrlFour) {
        this.stripeUrlFour = stripeUrlFour;
    }

    public String getStripeUrlOne() {
        return stripeUrlOne;
    }

    public void setStripeUrlOne(String stripeUrlOne) {
        this.stripeUrlOne = stripeUrlOne;
    }

    public String getStripeUrlSix() {
        return stripeUrlSix;
    }

    public void setStripeUrlSix(String stripeUrlSix) {
        this.stripeUrlSix = stripeUrlSix;
    }

    public String getStripeUrlThree() {
        return stripeUrlThree;
    }

    public void setStripeUrlThree(String stripeUrlThree) {
        this.stripeUrlThree = stripeUrlThree;
    }

    public String getStripeUrlTwo() {
        return stripeUrlTwo;
    }

    public void setStripeUrlTwo(String stripeUrlTwo) {
        this.stripeUrlTwo = stripeUrlTwo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void saveStrip() {
       
        Strip[] strips = new Strip[6];

        logger.log(Level.INFO, "Creating strip...");

        if (stripeUrlOne != null && stripeUrlOne.length() > 0) {
            logger.log(Level.INFO, "Strip one: " + stripeUrlOne);
            strips[0] = (stripHandler.createStrip(authorIdOne, stripeUrlOne));
        }
        if (stripeUrlTwo != null && stripeUrlTwo.length() > 0) {
            logger.log(Level.INFO, "Strip two: " + stripeUrlTwo);
            strips[1] = (stripHandler.createStrip(authorIdTwo, stripeUrlTwo));
        }
        if (stripeUrlThree != null && stripeUrlThree.length() > 0) {
            logger.log(Level.INFO, "Strip three: " + stripeUrlThree);
            strips[2] = (stripHandler.createStrip(authorIdThree, stripeUrlThree));
        }
        if (stripeUrlFour != null && stripeUrlFour.length() > 0) {
            logger.log(Level.INFO, "Strip four: " + stripeUrlFour);
            strips[3] = (stripHandler.createStrip(authorIdFour, stripeUrlFour));
        }
        if (stripeUrlFive != null && stripeUrlFive.length() > 0) {
            logger.log(Level.INFO, "Strip five: " + stripeUrlFive);
            strips[4] = (stripHandler.createStrip(authorIdFive, stripeUrlFive));
        }
        if (stripeUrlSix != null && stripeUrlSix.length() > 0) {
            logger.log(Level.INFO, "Strip six: " + stripeUrlSix);
            strips[5] = (stripHandler.createStrip(authorIdSix, stripeUrlSix));
        }

        LasTirasStrip strip = stripLasTirashandler.createLasTirasStrip(day, month, year, strips);


    }
}
