/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.EstadoPedido;
import util.ConnectionDatabase;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Raul
 */
public class EstadoPedidosController {

    private ObservableList<EstadoPedido> estadoPedidos;

    public EstadoPedidosController() throws ClassNotFoundException, SQLException {
        this.estadoPedidos = FXCollections.observableArrayList();
        getEstadosPedidosFromDataBase();
    }

    public ObservableList<EstadoPedido> getEstadoPedidos() {
        return estadoPedidos;
    }

    public void setEstadoPedidos(ObservableList estadoPedidos) {
        this.estadoPedidos = estadoPedidos;
    }

    public int insertEstadoPedido(String nuevoEstado) throws ClassNotFoundException, SQLException {
        Statement st = null;
        int idInserted = -1;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM estados_pedidos WHERE estado= '" + nuevoEstado + "';";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO estados_pedidos (estado) VALUES ('" + nuevoEstado + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM estados_pedidos WHERE estado= '" + nuevoEstado + "';";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idInserted = rsV.getInt("id");
            rsV.close();
        } else {
            if (rs.getInt("eliminado") == 1) {
                int idEliminado = rs.getInt("id");
                String sql = "UPDATE estados_pedidos SET eliminado = ('" + 0 + "') WHERE id = '" + idEliminado + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idInserted = idEliminado;
            }
        }
        st.close();
        rs.close();
        getEstadosPedidosFromDataBase();
        return idInserted;
    }

    public int updateEstadoPedido(int idEstado, String nuevoEstado) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM estados_pedidos WHERE id = '" + idEstado + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String val1 = "SELECT * FROM estados_pedidos WHERE (estado = '" + nuevoEstado + "');";
            ResultSet rs1 = st.executeQuery(val1);
            if (!rs1.next()) {
                String sql = "UPDATE estados_pedidos SET estado = ('" + nuevoEstado + "') WHERE id= '" + idEstado + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idUpdated = idEstado;
            } else {
                if (rs1.getInt("eliminado") == 1) {
                    int idEliminado = rs1.getInt("id");
                    String sql = "UPDATE estados_pedidos SET eliminado = ('" + 1 + "') WHERE id= '" + idEstado + "';";
                    String sql1 = "UPDATE estados_pedidos SET eliminado = ('" + 0 + "') WHERE id= '" + idEliminado + "';";
                    st.executeUpdate(sql);
                    st.executeUpdate(sql1);
                    ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                    idUpdated = idEliminado;
                }
            }
            rs1.close();
        }

        st.close();
        rs.close();
        getEstadosPedidosFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoEstadoPedido(int idEstado) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM estados_pedidos WHERE id= '" + idEstado + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE estados_pedidos SET eliminado = ('" + 1 + "') WHERE id = '" + idEstado + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }

        st.close();
        rs.close();
        getEstadosPedidosFromDataBase();
        return isDeleted;
    }

    public void getEstadosPedidosFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<EstadoPedido> estados = new ArrayList<EstadoPedido>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM estados_pedidos WHERE eliminado == 0;");
        EstadoPedido estadoPedido = null;
        while (rs.next()) {
            estadoPedido = new EstadoPedido(rs.getInt("id"), rs.getString("estado"));
            estados.add(estadoPedido);
        }
        st.close();
        rs.close();
        estadoPedidos.clear();
        estadoPedidos.addAll(estados);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < estadoPedidos.size()) {
            if (id == estadoPedidos.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }
    
    public int getIDEstadoByNombre(String nombre) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < estadoPedidos.size()) {
            if (nombre.equals( estadoPedidos.get(i).getEstado())) {
                found = true;
                pos = i;
            }
            i++;
        }
        return estadoPedidos.get(pos).getId();
    }
    
    public ObservableList<String> obtenerTodosEstadosString() {
        ObservableList<String> lista =FXCollections.observableArrayList();
          for (int i = 0; i < estadoPedidos.size(); i++) {
              lista.add(estadoPedidos.get(i).getEstado());
          }
        return lista;
    }
    
    public String getEstadoById(int id) throws ClassNotFoundException, SQLException{
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM estados_pedidos WHERE (id = '" + id + "' AND eliminado = 0);";
        ResultSet rs = st.executeQuery(validation);
        String estado= "error";
        if (rs.next()) {
            estado= rs.getString("estado");
        }
        return estado;
    }
}
