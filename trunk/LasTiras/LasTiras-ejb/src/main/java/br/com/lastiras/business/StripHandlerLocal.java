/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.business;

import br.com.lastiras.persistence.Strip;
import javax.ejb.Local;

/**
 *
 * @author matheus
 */
@Local
public interface StripHandlerLocal {

    public Strip createStrip(long id, String url);
    
}
