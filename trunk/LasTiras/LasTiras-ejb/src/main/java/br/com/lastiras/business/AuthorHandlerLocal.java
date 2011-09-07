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
public interface AuthorHandlerLocal {

    public boolean addAuthor(java.lang.String name, java.lang.String webSite, java.lang.String imageUrl);

    public boolean updateAuthor(br.com.lastiras.persistence.Author author);

    public br.com.lastiras.persistence.Author getByName(java.lang.String name);

    public java.util.List<br.com.lastiras.persistence.Author> getAll();

    public boolean updateAuthor(java.lang.Long id, java.lang.String name, java.lang.String webSite, java.lang.String imageUrl);
    
}
