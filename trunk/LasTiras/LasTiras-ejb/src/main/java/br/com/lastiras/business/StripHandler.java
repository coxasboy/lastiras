/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.business;

import br.com.lastiras.dao.AuthorDaoLocal;
import br.com.lastiras.dao.StripDaoLocal;
import br.com.lastiras.persistence.Author;
import br.com.lastiras.persistence.Strip;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author matheus
 */
@Stateless
public class StripHandler implements StripHandlerLocal {

    @EJB
    private AuthorDaoLocal authorDao;
    
    @EJB
    private StripDaoLocal stripDao;
    
    private static final Logger logger = Logger.getLogger(StripHandler.class.getSimpleName());
    
    @Override
    public Strip createStrip(long id, String url){
        try{
            Author author = authorDao.read(id);
            Strip strip = new Strip();
            strip.setAuthor(author);
            strip.setStripUrl(url);
            return stripDao.create(strip);            
        }
        catch(Exception e){
            logger.log(Level.SEVERE,"Erro adicionando strip", e);
            return null;
        }
    }
    
}
