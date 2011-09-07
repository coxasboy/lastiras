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
public interface EmailHandlerLocal {

    public java.lang.String digestEmail(java.lang.String email);

    public java.util.List<br.com.lastiras.persistence.Email> getAllMails();
    
}
