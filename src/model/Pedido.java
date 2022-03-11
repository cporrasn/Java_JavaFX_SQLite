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
public class Pedido {

    private int id;
    private int cliente_id;
    private SimpleStringProperty fecha = new SimpleStringProperty();
    private int estado_id;
    private SimpleFloatProperty ganancia = new SimpleFloatProperty();

    public Pedido(int id, int cliente_id, SimpleStringProperty fecha, int estado_id, float ganancia) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.fecha = fecha;
        this.estado_id = estado_id;
        this.ganancia.setValue(ganancia);
    }

    public Pedido(int id, int cliente_id, String fecha, int estado_id, float ganancia) throws ClassNotFoundException, SQLException {
        this.id = id;
        this.cliente_id = cliente_id;
        this.fecha.setValue(fecha);
        this.estado_id = estado_id;
        this.ganancia.setValue(ganancia);
    }

    public void setGanancia(float ganacia) {
        this.ganancia.setValue(id);
    }

    public float getGanancia() throws ClassNotFoundException, SQLException {
        return this.ganancia.getValue();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getFecha() {
        return fecha.getValue();
    }

    public void setFecha(SimpleStringProperty fecha) {
        this.fecha = fecha;
    }

    public String getEstado_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getEstados().getEstadoById(estado_id);
    }

    public void setEstado_id(int estado_id) {
        this.estado_id = estado_id;
    }
}
