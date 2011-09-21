/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.persistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author fernanda
 */
@Entity
public class Strip implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    @JoinColumn(nullable=false)
    private Author author;
    
    @Column(name="stripeUrl",length=1024)
    private String stripUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getStripUrl() {
        return stripUrl;
    }

    public void setStripUrl(String stripUrl) {
        this.stripUrl = stripUrl;
    }
    
    private String modifyTweeterUrl(String url){
        String doisPontos = "%3A";
        String interrogacao = "%3F";
        String igual = "%3D";
        String eComercial = "&amp;";
        String barra = "%2F";
        String mais = "%2B";
        
        url = url.replaceAll(":", doisPontos);
        url = url.replaceAll("/", barra);
        //url = url.replaceAll("?", interrogacao);
        //url = url.replaceAll("=", igual);
        url = url.replaceAll("&", eComercial);
        //url = url.replaceAll("+", mais);
        
        return url;
    }
    
    private String modifyUrl(String url){
        String doisPontos = "%3A";
        String interrogacao = "%3F";
        String igual = "%3D";
        String eComercial = "%26";
        String barra = "%2F";
        String mais = "%2B";
        
        url = url.replaceAll(":", doisPontos);
        url = url.replaceAll("/", barra);
        //url = url.replaceAll("?", interrogacao);
        //url = url.replaceAll("=", igual);
        //url = url.replaceAll("&", eComercial);
        //url = url.replaceAll("+", mais);
        
        return url;
    }
    
    public String acquireGooglePlusSrc(){
        String start=
                "https://plusone.google.com/u/0/_/+1/fastbutton?"+
                "url="+modifyUrl(stripUrl)+                
                "&amp;size=standard"+
                "&amp;count=true"+
                "&amp;annotation="+
                "&amp;hl=pt-BR"+
                "&amp;parent=http%3A%2F%2Fwww.lastiras.com"+
                "&amp;_methods=onPlusOne%2C_ready%2C_close%2C_open%2C_resizeMe";
        return start;
    }
    
    public String acquireTweeterSrc(){
        String start=
                "http://platform.twitter.com/widgets/tweet_button.html#"
                + "&amp;count=horizontal"
                + "&amp;id=twitter_tweet_button_14"
                + "&amp;lang=en"
                + "&amp;original_referer=http%3A%2F%2Fwww.lastiras.com"
                + "&amp;text=" + modifyUrl(stripUrl)
                + "&amp;url=" + modifyUrl(stripUrl)
                + "&amp;via=lastirasbr";
        return start;
    }
    
     public String acquireTweeterUrl(){
        String start= modifyUrl(stripUrl);
        return start;
    }
    
    public String acquireFacebookSrc(){
        String start = "http://www.facebook.com/plugins/like.php?"
                + "href=" + modifyUrl(stripUrl)
                + "&amp;layout=button_count"
                + "&amp;show_faces=false"
                + "&amp;action=recommended"
                + "&amp;colorscheme=light"
                + "&amp;width=100"
                + "&amp;height=21"
                + "&amp;font="
                + "&amp;locale=pt_BR";
        return start;
    }
    
    public String acquireId(){
        return "stripId"+id;
    }
    
    public String acquireJavaScript(){
        return "changeWidth('" + acquireId() + "',700,'" + stripUrl +"')";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Strip)) {
            return false;
        }
        Strip other = (Strip) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.lastiras.persistence.Stripe[ id=" + id + " ]";
    }
    
}