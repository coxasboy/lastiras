/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.business;

import javax.ejb.Local;

/**
 *
 * @author matheus
 */
@Local
public interface VisitiHandlerLocal {

    public void createVisit(java.lang.String ip);

    public java.util.List<br.com.lastiras.persistence.Visit> getVisitsCurrentMonth();

    public java.util.List<br.com.lastiras.persistence.Visit> getVisitsCurrentDay();
    
}
