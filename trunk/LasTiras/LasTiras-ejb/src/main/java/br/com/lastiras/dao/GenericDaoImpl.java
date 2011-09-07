/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lastiras.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author matheus
 */
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    
    @PersistenceContext(unitName = "lasTirasMysql")
    private EntityManager entityManager;  
    
    private Class<T> oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  
    public EntityManager getEntityManager() {  
        return entityManager;  
    }
    
    public void setEntityManager(EntityManager entityManager) {  
        this.entityManager = entityManager;
    }

    public Class<T> getoClass() {
        return oClass;
    }
    
    @Override
    public int getNumberOfInstances(){
         Query query = entityManager.createQuery(
                "SELECT count(x) FROM " + oClass.getSimpleName() + " as x");
        return ((Long)query.getSingleResult()).intValue();
    }
    
    @Override
    public List<T> getAll(){
        Query query = entityManager.createQuery(
                "SELECT x FROM " + oClass.getSimpleName() + " as x");
        return (List<T>)query.getResultList();
    }
//    
//    @Override
//    public List<T> getByField(String field, String value){
//        Query query = entityManager.createQuery(
//                "SELECT x FROM " + oClass.getSimpleName() + " x WHERE x." + field + " = ?1");
//        query.setParameter(1, value);
//        return (List<T>)query.getResultList();
//    }
    
    @Override
    public List<T> getByField(String field, Object value){
        Query query = entityManager.createQuery(
                "SELECT x FROM " + oClass.getSimpleName() + " x WHERE x." + field + " = ?1");
        query.setParameter(1, value);
        return (List<T>)query.getResultList();
    }
    
    @Override
    public T create(T newInstance){
        return this.entityManager.merge(newInstance);
    }

    @Override
    public T read(PK id){
        return this.entityManager.find(oClass, id);
    }

    @Override
    public void update(T transientObject){
        this.entityManager.merge(transientObject);
    }

    @Override
    public void delete(T persistentObject){
        this.entityManager.remove(persistentObject);
    }
    
}
