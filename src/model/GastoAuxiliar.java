/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controladora;
import java.sql.SQLException;

/**
 *
 * @author Raul
 */
public class GastoAuxiliar {

    private int id;
    private int producto_id;
    private int salario_id;

    public GastoAuxiliar(int id, int producto_id, int salario_id) {
        this.id = id;
        this.producto_id = producto_id;
        this.salario_id = salario_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdProducto(){
        return producto_id;
    }

    public String getProducto_id() throws ClassNotFoundException, SQLException {
        return Controladora.getController().getProducto().getProductoById(producto_id);
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public String getSalario_id() throws ClassNotFoundException, SQLException {
        int idConcepto=Controladora.getController().getSalarios().getSalarioConceptoById(salario_id);
        return Controladora.getController().getConceptosPagos().getConceptoById(idConcepto);
    }

    public void setSalario_id(int salario_id) {
        this.salario_id = salario_id;
    }
    
    public int getId_Salario(){
        return salario_id;
    }

}
