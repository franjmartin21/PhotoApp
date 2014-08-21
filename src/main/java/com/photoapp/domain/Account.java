package com.photoapp.domain;


import com.photoapp.persistence.Record;

import javax.persistence.Entity;

/**
 * Created by fran on 17/08/14.
 */
@Entity
public class Account extends Record{

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
