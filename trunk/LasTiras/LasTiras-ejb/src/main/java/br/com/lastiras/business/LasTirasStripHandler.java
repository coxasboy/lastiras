/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.business;

import br.com.lastiras.dao.LasTirasStripDaoLocal;
import br.com.lastiras.persistence.LasTirasStrip;
import br.com.lastiras.persistence.Strip;
import br.com.lastiras.util.DateCorrector;
import java.text.SimpleDateFormat;
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
public class LasTirasStripHandler implements LasTirasStripHandlerLocal {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private static final SimpleDateFormat sdfReturn = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger logger = Logger.getLogger(LasTirasStripHandler.class.getSimpleName());

//    static {
//        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
//    }
    @EJB
    private LasTirasStripDaoLocal lasTirasDao;

    @Override
    public LasTirasStrip getOlderLasTiras() {
        return lasTirasDao.getLastLasTiras();
    }

    @Override
    public LasTirasStrip getLasTirasFromThisExactDate(Date date) {
        LasTirasStrip strip = lasTirasDao.getLasTirasFromThisExactDate(date);
        return strip;
    }

    @Override
    public List<LasTirasStrip> getLasTirasEqualOrOldierThenThis(Date date) {
        logger.log(Level.INFO, "Getting las tiras oldier then: " + date);
        List<LasTirasStrip> strip = lasTirasDao.getLasTirasEqualOrOldierThenThis(date);
        logger.log(Level.INFO, "Retrieve: " + strip.size());
        return strip;
    }

    @Override
    public List<LasTirasStrip> getLasTirasOldierThenThis(Date date) {
        logger.log(Level.INFO, "Getting las tiras oldier then: " + date);
        List<LasTirasStrip> strip = lasTirasDao.getLasTirasOldierThenThis(date);
        logger.log(Level.INFO, "Retrieve: " + strip.size());
        return strip;
    }

    @Override
    public List<LasTirasStrip> getLasTirasNewerThenThis(Date date) {
        logger.log(Level.INFO, "Getting las tiras newer then: " + date);
        List<LasTirasStrip> strip = lasTirasDao.getLasTirasNewerThenThis(date);
        logger.log(Level.INFO, "Retrieve: " + strip.size());
        return strip;
    }

    @Override
    public LasTirasStrip getLasTirasAfter(Date date) {
        logger.log(Level.INFO, "Getting las tiras after then: " + date);
        LasTirasStrip strip = lasTirasDao.getLasTirasAfter(date);
        return strip;
    }

    @Override
    public LasTirasStrip getLasTirasBefore(Date date) {
        logger.log(Level.INFO, "Getting las tiras before then: " + date);
        LasTirasStrip strip = lasTirasDao.getLasTirasBefore(date);
        return strip;
    }

    @Override
    public LasTirasStrip createLasTirasStrip(int day, int month, int year, Strip... strips) {
        try {
            logger.info("Creating lastirasstrip: " + day + "/" + month + "/" + year);
            Date stripDate = sdf.parse(day + "/" + month + "/" + year);
            LasTirasStrip strip = new LasTirasStrip();
            strip.setStripDate(stripDate);
            for (Strip strip1 : strips) {
                strip.addStrip(strip1);
            }
            if (day == 0 || month == 0 || year == 0) {
                logger.info("Invalid date: " + stripDate);
                return null;
            }
            logger.info("Creating strip: " + stripDate);
            return lasTirasDao.create(strip);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro criando lastiras strip", e);
            return null;
        }
    }

    @Override
    public LasTirasStrip getLasTirasFromToday() {
        try {
            Date date = DateCorrector.getNow();
            logger.info("Now: " + date);
            List<LasTirasStrip> stripFromToday = lasTirasDao.getByField("stripDate", (date));
            logger.info("List: " + stripFromToday.size());
            if ((stripFromToday == null || stripFromToday.isEmpty())) {
                return null;
            } else {
                return stripFromToday.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro pegando tira de hoje", e);
            return null;
        }
    }

    @Override
    public br.com.lastiras.persistence.LasTirasStrip getIndexLasTiras(Date date) {
        logger.info("Loading strip of: " + date);
        LasTirasStrip strip = getLasTirasFromThisExactDate(date);
        logger.info("Strip: " + strip);
        if (strip != null) {
            return strip;
        }
        List<LasTirasStrip> strips = getLasTirasEqualOrOldierThenThis(date);
        logger.info("Strips loaded: " + strips.size());
        if (strips == null || strips.size() == 0) {
            strips = getLasTirasNewerThenThis(date);
            if (strips == null || strips.size() == 0) {
                return null;
            } else {
                return strips.get(0);
            }
        }
        logger.info("Strip date: " + strips.get(0).getStripDate());
        return strips.get(0);
    }

//    private Date addADayToThisDateClock(Date date){
//        Calendar instance = Calendar.getInstance();
//        instance.setTime(date);
//        instance.add(Calendar.DAY_OF_MONTH, 1);
//        return instance.getTime();
//    }
    public LasTirasStrip getIndexLasTiras(int counter) {
        LasTirasStrip strip = getNewerLasTiras();
        List<LasTirasStrip> strips = getLasTirasOldierThenThis(strip.getStripDate());
        counter = Math.abs(counter);
        if (counter >= strips.size()) {
            return strips.get(strips.size() - 1);
        } else {
            return strips.get(counter - 1);
        }
    }

    @Override
    public LasTirasStrip getNewerLasTiras() {
        try {
            LasTirasStrip strip = getLasTirasFromToday();
            if (strip != null) {
                return strip;
            } else {
                List<LasTirasStrip> lasTiras = getLasTirasOldierThenThis(DateCorrector.getNow());
                if (lasTiras != null && !lasTiras.isEmpty()) {
                    return lasTiras.get(0);
                }
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro pegando tira de hoje", e);
            return null;
        }
    }

}
