/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public interface GenericDao <T, PK extends Serializable> {

    /** Persist the newInstance object into database */
    T create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    T read(PK id);
    
    List<T> getAll();
    
    int getNumberOfInstances();

    /** Save changes made to a persistent object.  */
    void update(T transientObject);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);
    
    List<T> getByField(String field, Object value);
    
    
}