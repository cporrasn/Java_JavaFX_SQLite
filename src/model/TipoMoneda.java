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
public class TipoMoneda {
    private int id;
    private SimpleStringProperty tipo = new SimpleStringProperty();

    public TipoMoneda(int id, SimpleStringProperty tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public TipoMoneda(int id, String tipo) {
        this.id = id;
        this.tipo.setValue(tipo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo.getValue();
    }

    public void setTipo(SimpleStringProperty tipo) {
        this.tipo = tipo;
    }
}
