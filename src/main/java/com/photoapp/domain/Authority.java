package com.photoapp.domain;

import com.photoapp.persistence.Record;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * Created by fran on 22/08/14.
 */
@Entity
public class Authority extends Record {

    private Account account;

    private AuthoritiesEnum authority;

    public static enum AuthoritiesEnum {
        ROLE_USER,
        ROLE_ADMIN
    }

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Enumerated(EnumType.STRING)
    public AuthoritiesEnum getAuthority() {
        return authority;
    }

    public void setAuthority(AuthoritiesEnum authority) {
        this.authority = authority;
    }
}
