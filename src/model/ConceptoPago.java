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
public class ConceptoPago {
    
    private int id;
    private SimpleStringProperty concepto = new SimpleStringProperty();

    public ConceptoPago(int id, SimpleStringProperty concepto) {
        this.id = id;
        this.concepto = concepto;
    }

    public ConceptoPago(int id, String concepto) {
        this.id = id;
        this.concepto.setValue(concepto);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto.getValue();
    }

    public void setConcepto(String concepto) {
        this.concepto.setValue(concepto);
    }
    
    
}
