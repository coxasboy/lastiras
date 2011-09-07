/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author fernanda
 */
@Entity
public class LasTirasStrip implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable=false,unique=true)
    private Date stripDate;
    
    @OneToMany
    private List<Strip> strips = new ArrayList<Strip>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStripDate() {
        return stripDate;
    }

    public void setStripDate(Date stripDate) {
        this.stripDate = stripDate;
    }

    public List<Strip> getStrips() {
        return strips;
    }

    public void setStrips(List<Strip> strips) {
        this.strips = strips;
    }
    
    public void addStrip(Strip strip){
        boolean add = strips.add(strip);
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
        if (!(object instanceof LasTirasStrip)) {
            return false;
        }
        LasTirasStrip other = (LasTirasStrip) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.lastiras.persistence.LasTirasStripe[ id=" + id + " ]";
    }
    
}
