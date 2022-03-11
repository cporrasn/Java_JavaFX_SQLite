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
public class UnidadMedida {
    private int id;
    private SimpleStringProperty unidad_medida = new SimpleStringProperty();

    public UnidadMedida(int id, SimpleStringProperty unidad_medida) {
        this.id = id;
        this.unidad_medida = unidad_medida;
    }

    public UnidadMedida(int id, String unidad_medida) {
        this.id = id;
        this.unidad_medida.setValue(unidad_medida);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnidad_medida() {
        return unidad_medida.getValue();
    }

    public void setUnidad_medida(SimpleStringProperty unidad_medida) {
        this.unidad_medida = unidad_medida;
    }
    
}
