/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controladora;
import java.sql.SQLException;
import javafx.beans.property.SimpleFloatProperty;

/**
 *
 * @author Raul
 */
public class Salario {

    private int id;
    private int concepto_id;
    private int moneda_id;
    private SimpleFloatProperty monto = new SimpleFloatProperty();

    public Salario(int id, int concepto_id, int moneda_id, SimpleFloatProperty monto) {
        this.id = id;
        this.concepto_id = concepto_id;
        this.moneda_id = moneda_id;
        this.monto = monto;
    }

    public Salario(int id, int concepto_id, int moneda_id, float monto) {
        this.id = id;
        this.concepto_id = concepto_id;
        this.moneda_id = moneda_id;
        this.monto.setValue(monto);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcepto_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getConceptosPagos().getConceptoById(concepto_id);
    }

    public void setConcepto_id(int concepto_id) {
        this.concepto_id = concepto_id;
    }

    public String getMoneda_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getTipoMoneda().getTipoMonedaById(moneda_id);
    }

    public void setMoneda_id(int moneda_id) {
        this.moneda_id = moneda_id;
    }

    public Float getMonto() {
        return monto.getValue();
    }

    public void setMonto(SimpleFloatProperty monto) {
        this.monto = monto;
    }

}
