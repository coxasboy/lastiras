/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import br.com.lastiras.persistence.Author;
import javax.ejb.Stateless;

/**
 *
 * @author matheus
 */
@Stateless
public class AuthorDao extends GenericDaoImpl<Author, Long> implements AuthorDaoLocal {

    
    
}
