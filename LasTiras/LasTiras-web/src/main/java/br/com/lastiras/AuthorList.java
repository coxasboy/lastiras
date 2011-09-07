/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras;

import br.com.lastiras.business.AuthorHandlerLocal;
import br.com.lastiras.persistence.Author;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author matheus
 */
@ManagedBean(name="authorList")
@RequestScoped
public class AuthorList {
    
    @EJB
    private AuthorHandlerLocal authorHandler;
    
    List<Author> list = new ArrayList<Author> ();
        
    /** Creates a new instance of AuthorList */
    public AuthorList() {
        
    }

    public List<Author> acquireAllAuthors() {
        if(list.isEmpty()){
            list = authorHandler.getAll();
        }
        Collections.shuffle(list);
        return list;
    }

    
}
