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
public class EstadoPedido {
    private int id;
    private SimpleStringProperty estado = new SimpleStringProperty();

    public EstadoPedido(int id, SimpleStringProperty estado) {
        this.id = id;
        this.estado = estado;
    }

    public EstadoPedido(int id, String estado) {
        this.id = id;
        this.estado.setValue(estado);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado.getValue();
    }

    public void setEstado(String estado) {
        this.estado.setValue(estado);
    }
    
    
    
}
