/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Raul
 */
public class Cliente {
    
    private int id;
    private SimpleStringProperty nombre = new SimpleStringProperty();

    public Cliente(int id, SimpleStringProperty nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Cliente(int id, String nombre) {
        this.id = id;
        this.nombre.setValue(nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.getValue();
    }

    public void setNombre(SimpleStringProperty nombre) {
        this.nombre = nombre;
    }
    
    
}
