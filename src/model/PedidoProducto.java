/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controladora;
import java.sql.SQLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Raul
 */
public class PedidoProducto {

    private int id;
    private int producto_id;
    private int pedido_id;
    private SimpleIntegerProperty cantidad = new SimpleIntegerProperty();
    private SimpleFloatProperty precio = new SimpleFloatProperty();
    private SimpleFloatProperty costoUnitario = new SimpleFloatProperty();

    private SimpleFloatProperty ganaciaUnitaria = new SimpleFloatProperty();
    private int moneda_id;

    public PedidoProducto(int id, int producto_id, int pedido_id, SimpleIntegerProperty cantidad, SimpleFloatProperty precio, int moneda_id) {
        this.id = id;
        this.producto_id = producto_id;
        this.pedido_id = pedido_id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.moneda_id = moneda_id;
    }

    public PedidoProducto(int id, int producto_id, int cantidad, float precio, int pedido_id, int moneda_id, float costo, float ganacia) {
        this.id = id;
        this.producto_id = producto_id;
        this.cantidad.setValue(cantidad);
        this.precio.setValue(precio);
        this.pedido_id = pedido_id;
        this.moneda_id = moneda_id;
        this.costoUnitario.setValue(costo);
        this.ganaciaUnitaria.setValue(ganacia);
    }

    public PedidoProducto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto_id() throws ClassNotFoundException, SQLException {
         return Controladora.getController().getProdCTL().getProductoById(producto_id);
    }

    public int getidProducto()  {
         return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public Integer getCantidad() {
        return cantidad.getValue();
    }

    public void setCantidad(int cantidad) {
        this.cantidad.setValue(cantidad);
    }

    public Float getPrecio() {
        return precio.getValue();
    }

    public void setPrecio(float precio) {
        this.precio.setValue(precio);
    }

    public int getIdPedido() {
        return pedido_id;
    }

    public String getMoneda_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getTipoMoneda().getTipoMonedaById(moneda_id);
    }

    public void setMoneda_id(int moneda_id) {
        this.moneda_id = moneda_id;
    }

    public void setCostoUnitario(float costoUnitario) {
        this.costoUnitario.setValue(costoUnitario);
    }

    public void setGanaciaUnitaria(float ganaciaUnitaria) {
        this.ganaciaUnitaria.setValue(ganaciaUnitaria);
    }

    public float getCostoUnitario() {
        return costoUnitario.getValue();
    }

    public float getGanaciaUnitaria() {
        return ganaciaUnitaria.getValue();
    }

}
