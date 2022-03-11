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
 * @author ernesto.alfonso
 */
public class Minimo {
    private int id;
    private int ingrediente_id;
    private SimpleFloatProperty cantidad = new SimpleFloatProperty();

    public Minimo(int id, int ingrediente_id, SimpleFloatProperty cantidad) {
        this.id = id;
        this.ingrediente_id = ingrediente_id;
        this.cantidad = cantidad;
    }
    
    public Minimo(int id, int ingrediente_id, float cantidad) {
        this.id = id;
        this.ingrediente_id = ingrediente_id;
        this.cantidad.setValue(cantidad);
    }

    public int getId() {
        return id;
    }
    
    public int getIdIngrediente() {
        return ingrediente_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngrediente_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getIngredientes().getIngredienteById(ingrediente_id);
    }

    public void setIngrediente_id(int ingrediente_id) {
        this.ingrediente_id = ingrediente_id;
    }

    public float getCantidad() {
        return cantidad.getValue();
    }

    public void setCantidad(SimpleFloatProperty cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
