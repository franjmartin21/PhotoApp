package com.photoapp.dao.core;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by fran on 23/08/14.
 */
public class AbstractJPADao <T extends Serializable> {
    private Class< T > clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public void setClazz( Class< T > clazzToSet ){
        this.clazz = clazzToSet;
    }

    public T findOne( Long id ){
        return this.entityManager.find( this.clazz, id );
    }

    public List< T > findAll(){
        return this.entityManager.createQuery( "from " + this.clazz.getName() )
                .getResultList();
    }

    public void save( T entity ){
        this.entityManager.persist( entity );
    }

    public void update( T entity ){
        this.entityManager.merge( entity );
    }

    public void delete( T entity ){
        this.entityManager.remove( entity );
    }

    public void deleteById( Long entityId ){
        T entity = this.findOne( entityId );

        this.delete( entity );
    }
}

