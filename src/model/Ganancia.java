/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Raul
 */
public class Ganancia {
    private SimpleStringProperty fechaInicio = new SimpleStringProperty();
    private SimpleStringProperty fechaFin = new SimpleStringProperty();
    private SimpleFloatProperty costoProd = new SimpleFloatProperty();
    private SimpleFloatProperty ganancia = new SimpleFloatProperty();
    private SimpleFloatProperty venta = new SimpleFloatProperty();

    public Ganancia(SimpleStringProperty fechaInicio, SimpleStringProperty fechaFin, SimpleFloatProperty costoProd, SimpleFloatProperty ganancia, SimpleFloatProperty venta) {
        this.fechaInicio= fechaInicio;
        this.fechaFin= fechaFin;
        this.costoProd= costoProd;
        this.ganancia= ganancia;
        this.venta= venta;
    }
    
    public Ganancia(String fechaInicio, String fechaFin, Float costoProd, Float ganancia, Float venta) {
        this.fechaInicio.setValue(fechaInicio);
        this.fechaFin.setValue(fechaFin);
        this.costoProd.setValue(costoProd);
        this.ganancia.setValue(ganancia);
        this.venta.setValue(venta);
    }

    public String getFechaInicio() {
        return fechaInicio.getValue();
    }

    public void setFechaInicio(SimpleStringProperty fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin.getValue();
    }

    public void setFechaFin(SimpleStringProperty fechaFin) {
        this.fechaFin = fechaFin;
    }

    public float getCostoProd() {
        return costoProd.getValue();
    }

    public void setCostoProd(SimpleFloatProperty costoProd) {
        this.costoProd = costoProd;
    }
    
    public void setCostoProd(float costoProd) {
        this.costoProd.set(costoProd);
    }

    public float getGanancia() {
        return ganancia.getValue();
    }

    public void setGanancia(SimpleFloatProperty ganancia) {
        this.ganancia = ganancia;
    }
    
    public void setGanancia(float ganancia) {
        this.ganancia.setValue(ganancia);
    }

    public float getVenta() {
        return venta.getValue();
    }

    public void setVenta(SimpleFloatProperty venta) {
        this.venta = venta;
    }
    
    public void setVenta(float venta) {
        this.venta.setValue(venta);
    }
}
