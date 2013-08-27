/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Matheus
 */
public class DateCorrector {
    
    
    public static Date getNow(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        calendar.add(Calendar.HOUR_OF_DAY, 8);
        return calendar.getTime();
    }
    
}
