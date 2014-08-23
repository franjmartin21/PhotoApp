package com.photoapp.dao.core;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fran on 23/08/14.
 */
public interface IGenericDao<T extends Serializable> {

    T findOne(final Long id);

    List<T> findAll();

    void save(final T entity);

    void update(final T entity);

    void delete(final T entity);

    void deleteById(final Long entityId);
}