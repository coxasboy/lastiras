/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras;

import br.com.lastiras.business.AuthorHandlerLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author matheus
 */
@ManagedBean(name="authorCreator")
@RequestScoped
public class AuthorCreator {

    String name;
    
    String bannerUrl;
    
    String webSite;
    
    String message;
    
    Long id;
    
    @EJB AuthorHandlerLocal authorHandler;
    
    public AuthorCreator() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean haveMessage(){
        if(this.message!=null){
            return true;
        }
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
        
    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    
    public void saveAuthor(){
        boolean result = false;
        if(id!=null){
            result = authorHandler.updateAuthor(id, name, webSite, bannerUrl);
        }
        else{
            result = authorHandler.addAuthor(name, webSite, bannerUrl);
        }
        if(result){
            message = "Operação realizada com sucesso";
        }
        else{
            message = "Operação FALHOU!";
        }
    }
}
