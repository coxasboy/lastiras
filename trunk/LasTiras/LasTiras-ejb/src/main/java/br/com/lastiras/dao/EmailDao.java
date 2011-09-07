/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.Email;
import javax.ejb.Stateless;

/**
 *
 * @author matheus
 */
@Stateless
public class EmailDao extends GenericDaoImpl<Email, Long> implements EmailDaoLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
