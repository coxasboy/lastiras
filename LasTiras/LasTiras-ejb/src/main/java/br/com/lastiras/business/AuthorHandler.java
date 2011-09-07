/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.business;

import br.com.lastiras.dao.AuthorDaoLocal;
import br.com.lastiras.persistence.Author;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author matheus
 */
@Stateless
public class AuthorHandler implements AuthorHandlerLocal {

    @EJB
    private AuthorDaoLocal authorDao;
    
    private static final Logger logger = Logger.getLogger(AuthorDaoLocal.class.getName());
    
    @Override
    public boolean addAuthor(String name, String webSite, String imageUrl){
        try{
            if(isValidParameters(name,webSite,imageUrl)){
                Author author = createInstance(name, webSite, imageUrl);
                author = authorDao.create(author);           
                return true;
            }
            return false;
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Erro adicionando pessoa", e);
            return false;
        }
    }
    
    @Override
    public boolean updateAuthor(Long id, String name, String webSite, String imageUrl){
        try{
            Author author = authorDao.read(id);
            if(author!=null){
                author.setName(name);
                author.setImageUrl(imageUrl);
                author.setWebSite(webSite);
                authorDao.update(author);
                return true;
            }
            return false;
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Erro atualizando pessoa", e);
            return false;
        }    
    }
    
    @Override
    public boolean updateAuthor(Author author){
        try {
            authorDao.update(author);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro atualizando pessoa", e);
            return false;
        }
    }
    
    @Override
    public Author getByName(String name){
        try {
            List<Author> authors = this.authorDao.getByField("name", name);
            return authors.size()>0?authors.get(0):null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro atualizando pessoa", e);
            return null;
        }
    }    

    @Override
    public List<Author> getAll(){
        try {
            List<Author> authors = this.authorDao.getAll();
            logger.log(Level.INFO,"Authors: " + authors.size());
            return authors;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro atualizando pessoa", e);
            return null;
        }
    }
    
    private boolean isValidParameters(String name, String webSite, String imageUrl){
        if(name == null || name.length()<1){
            return false;
        }
        if(webSite == null || webSite.length()<1){
            return false;
        }
        if(imageUrl == null || imageUrl.length()<1){
            return false;
        }
        return true;
    }
    
    private Author createInstance(String name, String webSite, String imageUrl){
        Author author = new Author();
        author.setName(name);
        author.setWebSite(webSite);
        author.setImageUrl(imageUrl);
        return author;
    }
    
}
