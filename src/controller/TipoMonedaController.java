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
import model.TipoMoneda;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class TipoMonedaController {

    private ObservableList<TipoMoneda> tiposMoneda;

    public TipoMonedaController() throws ClassNotFoundException, SQLException {
        this.tiposMoneda = FXCollections.observableArrayList();
        getTiposMonedaFromDataBase();
    }

    public ObservableList<TipoMoneda> getTiposMoneda() {
        return tiposMoneda;
    }

    public void setTiposMoneda(ObservableList tiposMoneda) {
        this.tiposMoneda = tiposMoneda;
    }

    public int insertTipoMoneda(String nuevoTipo) throws ClassNotFoundException, SQLException {
        Statement st = null;
        int idInserted = -1;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM tipo_moneda WHERE tipo= '" + nuevoTipo + "';";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO tipo_moneda (tipo) VALUES ('" + nuevoTipo + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM tipo_moneda WHERE tipo= '" + nuevoTipo + "';";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idInserted = rsV.getInt("id");
            rsV.close();
        } else if (rs.getInt("eliminado") == 1) {
            int idEliminado = rs.getInt("id");
            String sql = "UPDATE tipo_moneda SET eliminado = ('" + 0 + "') WHERE id = '" + idEliminado + "';";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            idInserted = idEliminado;
        }
        st.close();
        rs.close();
        getTiposMonedaFromDataBase();
        return idInserted;
    }

    public int updateTipoMoneda(int idTipo, String nuevoTipo) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM tipo_moneda WHERE id = '" + idTipo + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String val1 = "SELECT * FROM tipo_moneda WHERE (tipo = '" + nuevoTipo + "');";
            ResultSet rs1 = st.executeQuery(val1);
            if (!rs1.next()) {
                String sql = "UPDATE tipo_moneda SET tipo = ('" + nuevoTipo + "') WHERE id= '" + idTipo + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idUpdated = idTipo;
            } else if (rs1.getInt("eliminado") == 1) {
                int idEliminado = rs1.getInt("id");
                String sql = "UPDATE tipo_moneda SET eliminado = ('" + 1 + "') WHERE id= '" + idTipo + "';";
                String sql1 = "UPDATE tipo_moneda SET eliminado = ('" + 0 + "') WHERE id= '" + idEliminado + "';";
                st.executeUpdate(sql);
                st.executeUpdate(sql1);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idUpdated = idEliminado;
            }
            rs1.close();
        }
        st.close();
        rs.close();
        getTiposMonedaFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoTipoMoneda(int idTipo) throws ClassNotFoundException, SQLException {
        boolean deleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM tipo_moneda WHERE id= '" + idTipo + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String sql = "UPDATE tipo_moneda SET eliminado = ('" + 1 + "') WHERE id = '" + idTipo + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            deleted = true;
        }
        st.close();
        rs.close();
        getTiposMonedaFromDataBase();
        return deleted;
    }

    public void getTiposMonedaFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<TipoMoneda> tipos = new ArrayList<TipoMoneda>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM tipo_moneda WHERE eliminado == 0;");
        TipoMoneda tipoMoneda = null;
        while (rs.next()) {
            tipoMoneda = new TipoMoneda(rs.getInt("id"), rs.getString("tipo"));
            tipos.add(tipoMoneda);
        }
        st.close();
        rs.close();
        tiposMoneda.clear();
        tiposMoneda.addAll(tipos);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < tiposMoneda.size()) {
            if (id == tiposMoneda.get(i).getId()) {
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
        while (!found && i < tiposMoneda.size()) {
            if (nombre.equals(tiposMoneda.get(i).getTipo())) {
                found = true;
                id = tiposMoneda.get(i).getId();
            }
            i++;
        }
        return id;
    }

    public ObservableList<String> obtenerTodosTipoMonedaString() {
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (int i = 0; i < tiposMoneda.size(); i++) {
            lista.add(tiposMoneda.get(i).getTipo());
        }
        return lista;
    }
    
    public String getTipoMonedaById(int id) throws ClassNotFoundException, SQLException{
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM tipo_moneda WHERE (id = '" + id + "' AND eliminado = 0);";
        ResultSet rs = st.executeQuery(validation);
        String tipo= "error";
        if (rs.next()) {
            tipo= rs.getString("tipo");
        }
        return tipo;
    }
}
