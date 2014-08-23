package com.photoapp.dao.core;

import com.photoapp.persistence.Record;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * Created by fran on 23/08/14.
 */
@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class GenericJpaDao< E extends Record > extends AbstractJPADao< E > implements IGenericDao<E>{

}