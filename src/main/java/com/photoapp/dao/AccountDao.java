package com.photoapp.dao;

import com.photoapp.dao.core.AbstractJPADao;
import com.photoapp.domain.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by fran on 23/08/14.
 */
@Repository
public class AccountDao extends AbstractJPADao<Account> {

    public Account findAccountByUsername(String username) {
        Query query = entityManager.createNamedQuery("account.getByName", Account.class);
        query.setParameter("username", username);
        return (Account)query.getSingleResult();
    }
}
