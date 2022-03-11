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
 * @author Raul
 */
public class Producto {
    private int id;
    private SimpleStringProperty nombre= new SimpleStringProperty();
    private SimpleFloatProperty costo=new SimpleFloatProperty();
    

    public Producto(int id, SimpleStringProperty nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Producto(int id, String nombre) {
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
    
      public Float getCosto() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getCostoProduccionByProd(id);
    }

    public void setCosto(SimpleFloatProperty costo) {
        this.costo = costo;
    }
      
      
    
}
