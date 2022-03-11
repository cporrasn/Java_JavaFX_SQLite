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
public class Receta {
    private int id;
    private int producto_id;
    private int ingrediente_id;
    private SimpleFloatProperty cantidad = new SimpleFloatProperty();
    private SimpleFloatProperty valor = new SimpleFloatProperty();

    public Receta(int id, int producto_id, int ingrediente_id, SimpleFloatProperty cantidad, SimpleFloatProperty valor) {
        this.id = id;
        this.producto_id = producto_id;
        this.ingrediente_id = ingrediente_id;
        this.cantidad = cantidad;
        this.valor = valor;
    }
    
     public Receta(int ingrediente_id, float cantidad) {
        this.ingrediente_id = ingrediente_id;
        this.cantidad.setValue(cantidad);
    }

    public Receta(int id, int producto_id, int ingrediente_id, float cantidad, float valor) {
        this.id = id;
        this.producto_id = producto_id;
        this.ingrediente_id = ingrediente_id;
        this.cantidad.setValue(cantidad);
        this.valor.setValue(valor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public String getIngrediente_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getIngredientes().getIngredienteById(ingrediente_id);
    }

    public void setIngrediente_id(int ingrediente_id) {
        this.ingrediente_id = ingrediente_id;
    }

    public Float getCantidad() {
        return cantidad.getValue();
    }

    public void setCantidad(float cantidad) {
        this.cantidad.setValue(cantidad);
    }

    public SimpleFloatProperty getValor() {
        return valor;
    }

    public void setValor(SimpleFloatProperty valor) {
        this.valor = valor;
    }
    
    public int getIdIngrediente() {
        return ingrediente_id;
    }
    
    public Float getValue(){
        return valor.getValue();        
    }
}
