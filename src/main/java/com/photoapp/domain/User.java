package com.photoapp.domain;

import javax.persistence.Entity;

/**
 * Created by fran on 17/08/14.
 */
@Entity
public class User {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
