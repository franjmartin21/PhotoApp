package com.photoapp.domain;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fran on 17/08/14.
 */
@Entity
public class Account {
    @Id
    private long id;

    private String nombre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
