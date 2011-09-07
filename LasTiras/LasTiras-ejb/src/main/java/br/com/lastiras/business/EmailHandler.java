/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.business;

import br.com.lastiras.dao.EmailDaoLocal;
import br.com.lastiras.persistence.Email;
import java.util.ArrayList;
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
public class EmailHandler implements EmailHandlerLocal {

    @EJB
    private EmailDaoLocal emailDao;
    
    private static final Logger logger = Logger.getLogger(EmailHandler.class.getName());
    
    @Override
    public String digestEmail(String email){
       try{
        List<Email> emailDb = emailDao.getByField("email", email);
        if(emailDb!=null && !emailDb.isEmpty()){
            emailDao.delete(emailDb.get(0));
            return "Descadastrado";
        }
        else{
            Email newEmail = new Email();
            newEmail.setEmail(email);
            emailDao.create(newEmail);
            return "Cadastrado";
        }
       }
       catch(Exception e){
           logger.log(Level.SEVERE, "Erro trabalhando com email",e);
           return "Erro ao trabalhar email";
       }
    }
    
    public List<Email> getAllMails(){
        try{
            return emailDao.getAll();
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Erro pegando emails",e);
            return new ArrayList<Email>();
        }
    }

    
}
