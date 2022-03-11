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
import model.Cliente;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class ClientesController {

    private ObservableList<Cliente> clientes;

    public ClientesController() throws ClassNotFoundException, SQLException {
        this.clientes = FXCollections.observableArrayList();
        getClientesFromDataBase();
    }

    public ObservableList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ObservableList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public int insertCliente(String nuevoNombre) throws ClassNotFoundException, SQLException {
        Statement st = null;
        int idInserted = -1;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM clientes WHERE (nombre= '" + nuevoNombre + "' AND eliminado= '0');";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO clientes (nombre) VALUES ('" + nuevoNombre + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM clientes WHERE (nombre= '" + nuevoNombre + "' AND eliminado = '0');";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idInserted = rsV.getInt("id");
            rsV.close();
        } 
        st.close();
        rs.close();
        getClientesFromDataBase();
        return idInserted;
    }

    public int updateCliente(int idCliente, String nuevoNombre) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM clientes WHERE (id = '" + idCliente + "' AND eliminado= '0');";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String val1 = "SELECT * FROM clientes WHERE (nombre = '" + nuevoNombre + "' AND eliminado= '0');";
            ResultSet rs1 = st.executeQuery(val1);
            if (!rs1.next()) {
                String sql = "UPDATE clientes SET nombre = ('" + nuevoNombre + "') WHERE id= '" + idCliente + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idUpdated= idCliente;
            }
            rs1.close();
        }
        st.close();
        rs.close();
        getClientesFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoCliente(int idCliente) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM clientes WHERE id= '" + idCliente + "' AND eliminado= '0';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String sql = "UPDATE clientes SET eliminado = ('" + 1 + "') WHERE id = '" + idCliente + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }
        st.close();
        rs.close();
        getClientesFromDataBase();
        return isDeleted;
    }

    public void getClientesFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<Cliente> cls = new ArrayList<>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM clientes WHERE eliminado == 0;");
        Cliente cl = null;
        while (rs.next()) {
            cl = new Cliente(rs.getInt("id"), rs.getString("nombre"));
            cls.add(cl);
        }
        st.close();
        rs.close();
        clientes.clear();
        clientes.addAll(cls);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < clientes.size()) {
            if (id == clientes.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }
}
