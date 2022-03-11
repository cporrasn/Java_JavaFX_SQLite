/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controladora;
import java.sql.SQLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Cynthia
 */
public class Ingrediente {

    private int id;
    private SimpleStringProperty nombre = new SimpleStringProperty();
    private int um_id;
    private SimpleFloatProperty cantidad = new SimpleFloatProperty();
    private SimpleFloatProperty costo = new SimpleFloatProperty();
    private int moneda_id;
//    private SimpleStringProperty fecha = new SimpleStringProperty();

    public Ingrediente(int id, SimpleStringProperty nombre, int um_id, SimpleFloatProperty cantidad, SimpleFloatProperty costo, int moneda_id) {
        this.id = id;
        this.nombre = nombre;
        this.um_id = um_id;
        this.cantidad = cantidad;
        this.costo = costo;
        this.moneda_id = moneda_id;
//        this.fecha = fecha;
    }

    public Ingrediente(int id, String nombre, int um_id, float cantidad, float costo, int moneda_id) {
        this.id = id;
        this.nombre.setValue(nombre);
        this.um_id = um_id;
        this.cantidad.setValue(cantidad);
        this.costo.setValue(costo);
        this.moneda_id = moneda_id;
//        this.fecha.setValue(fecha);
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

    public String getUm_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getUnidadMedida().getUnidadMedidaById(um_id);
    }

    public void setUm_id(int um_id) {
        this.um_id = um_id;
    }

    public Float getCantidad() {
        return cantidad.getValue();
    }

    public void setCantidad(SimpleFloatProperty cantidad) {
        this.cantidad = cantidad;
    }
    
    public void setCantidad(float cantidad) {
        this.cantidad.setValue(cantidad);
    }

    public Float getCosto() {
        return costo.getValue();
    }

    public void setCosto(SimpleFloatProperty costo) {
        this.costo = costo;
    }

    public String getMoneda_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getTipoMoneda().getTipoMonedaById(moneda_id);
    }

    public void setMoneda_id(int moneda_id) {
        this.moneda_id = moneda_id;
    }

    public int getIdUm() {
        return um_id;
    }

    public int getIdMoneda() {
        return moneda_id;
    } 
    
}
