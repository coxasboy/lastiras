/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.Email;
import javax.ejb.Local;

/**
 *
 * @author matheus
 */
@Local
public interface EmailDaoLocal extends GenericDao<Email, Long> {
    
}
