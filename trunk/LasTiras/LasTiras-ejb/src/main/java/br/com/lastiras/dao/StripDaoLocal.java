/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.Strip;
import javax.ejb.Local;

/**
 *
 * @author matheus
 */
@Local
public interface StripDaoLocal extends GenericDao<Strip, Long> {
    
}
