/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Producto;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class ProductosController {

    private ObservableList<Producto> productos;

    public ProductosController() throws ClassNotFoundException, SQLException {
        this.productos = FXCollections.observableArrayList();
        getProductosFromDataBase();
    }

    public ObservableList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ObservableList productos) {
        this.productos = productos;
    }

    public int insertProducto(String nombre) throws ClassNotFoundException, SQLException {
        Statement st = null;
        int idInserted = -1;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM productos WHERE (nombre= '" + nombre + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO productos (nombre) VALUES ('" + nombre + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM productos WHERE (nombre= '" + nombre + "' AND eliminado = '0');";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idInserted = rsV.getInt("id");
            rsV.close();
        }
        st.close();
        rs.close();
        getProductosFromDataBase();
        return idInserted;
    }

    public int updateProducto(int idProducto, String nuevoNombre) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM productos WHERE (id = '" + idProducto + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String val1 = "SELECT * FROM productos WHERE (nombre = '" + nuevoNombre + "' AND eliminado= '0');";
            ResultSet rs1 = st.executeQuery(val1);
            if (!rs1.next()) {
                String sql = "UPDATE productos SET nombre = ('" + nuevoNombre + "') WHERE id= '" + idProducto + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idUpdated = idProducto;
            }
            rs1.close();
        }
        st.close();
        rs.close();
        getProductosFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoProducto(int idProducto) throws ClassNotFoundException, SQLException {
        boolean deleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM productos WHERE (id= '" + idProducto + "' AND eliminado= '0');";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String sql = "UPDATE productos SET eliminado = ('" + 1 + "') WHERE id = '" + idProducto + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            deleted = true;
        }
        st.close();
        rs.close();
        getProductosFromDataBase();
        return deleted;
    }

    public void getProductosFromDataBase() throws SQLException, ClassNotFoundException {
        ArrayList<Producto> productos1 = new ArrayList<>();

        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM productos WHERE eliminado = '0';");
        Producto prod = null;
        while (rs.next()) {
            prod = new Producto(rs.getInt("id"), rs.getString("nombre"));
            productos1.add(prod);
        }
        st.close();
        rs.close();
        this.productos.clear();
        this.productos.addAll(productos1);
    }
    
     public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < productos.size()) {
            if (id == productos.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }
    
    public int getIDporNombre(String nombre) {
        int id = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < productos.size()) {
            if (nombre.equals(productos.get(i).getNombre())) {
                found = true;
                id = productos.get(i).getId();
            }
            i++;
        }
        return id;
    }
    
    public String getProductoById(int id) throws ClassNotFoundException, SQLException{
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM productos WHERE (id = '" + id + "');";
        ResultSet rs = st.executeQuery(validation);
        String um= " ";
        if (rs.next()) {
            um= rs.getString("nombre");
        }
        rs.close();
        st.close();
        return um;
    }
    
    public Producto getProducto(int id) throws ClassNotFoundException, SQLException{
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        Producto producto= null;
        String validation = "SELECT * FROM productos WHERE (id = '" + id + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            producto= new Producto(rs.getInt("id"), rs.getString("nombre"));
        }
        return producto;
    }
}
