/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.UnidadMedida;
import util.ConnectionDatabase;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Raul
 */
public class UnidadesMedidaController {

    private ObservableList<UnidadMedida> unidadesMedida;

    public UnidadesMedidaController() throws SQLException, ClassNotFoundException {
        this.unidadesMedida = FXCollections.observableArrayList();
        getUnidadesMedidasFromDataBase();
    }

    public ObservableList<UnidadMedida> getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(ObservableList<UnidadMedida> unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }

    public int insertUM(String nuevaUM) throws ClassNotFoundException, SQLException {
        Statement st = null;
        int idInserted = -1;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM unidades_medida WHERE unidad_medida= '" + nuevaUM + "';";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO unidades_medida (unidad_medida) VALUES ('" + nuevaUM + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM unidades_medida WHERE unidad_medida= '" + nuevaUM + "';";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idInserted = rsV.getInt("id");
            rsV.close();
        } else {
            if (rs.getInt("eliminado") == 1) {
                int idEliminado = rs.getInt("id");
                String sql = "UPDATE unidades_medida SET eliminado = ('" + 0 + "') WHERE id = '" + idEliminado + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idInserted = idEliminado;
            }
        }
        st.close();
        rs.close();
        getUnidadesMedidasFromDataBase();
        return idInserted;
    }

    public int updateUM(int idUM, String nuevaUM) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM unidades_medida WHERE id = '" + idUM + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String val1 = "SELECT * FROM unidades_medida WHERE (unidad_medida = '" + nuevaUM + "');";
            ResultSet rs1 = st.executeQuery(val1);
            if (!rs1.next()) {
                String sql = "UPDATE unidades_medida SET unidad_medida = ('" + nuevaUM + "') WHERE id= '" + idUM + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idUpdated = idUM;
            } else {
                if (rs1.getInt("eliminado") == 1) {
                    int idEliminado = rs1.getInt("id");
                    String sql = "UPDATE unidades_medida SET eliminado = ('" + 1 + "') WHERE id= '" + idUM + "';";
                    String sql1 = "UPDATE unidades_medida SET eliminado = ('" + 0 + "') WHERE id= '" + idEliminado + "';";
                    st.executeUpdate(sql);
                    st.executeUpdate(sql1);
                    ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                    idUpdated = idEliminado;
                }
            }
        }

        st.close();
        rs.close();
        getUnidadesMedidasFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoUM(int idUM) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM unidades_medida WHERE id= '" + idUM + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE unidades_medida SET eliminado = ('" + 1 + "') WHERE id ='" + idUM + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }

        st.close();
        rs.close();
        getUnidadesMedidasFromDataBase();
        return isDeleted;
    }

    public ArrayList<UnidadMedida> getUnidadesMedidasFromDataBase() throws SQLException, ClassNotFoundException {
        ArrayList<UnidadMedida> ums = new ArrayList<>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM unidades_medida WHERE eliminado == 0;");
        UnidadMedida um = null;
        while (rs.next()) {
            um = new UnidadMedida(rs.getInt("id"), rs.getString("unidad_medida"));
            ums.add(um);
        }
        st.close();
        rs.close();
        unidadesMedida.clear();
        unidadesMedida.addAll(ums);
        return ums;
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < unidadesMedida.size()) {
            if (id == unidadesMedida.get(i).getId()) {
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
        while (!found && i < unidadesMedida.size()) {
            if (nombre.equals(unidadesMedida.get(i).getUnidad_medida())) {
                found = true;
                id = unidadesMedida.get(i).getId();
            }
            i++;
        }
        return id;
    }
    
    public ObservableList<String> obtenerTodosUnidadMedidaString() {
        ObservableList<String> lista =FXCollections.observableArrayList();
          for (int i = 0; i < unidadesMedida.size(); i++) {
              lista.add(unidadesMedida.get(i).getUnidad_medida());
          }
        return lista;
    }
    
    public String getUnidadMedidaById(int id) throws ClassNotFoundException, SQLException{
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM unidades_medida WHERE (id = '" + id + "');";
        ResultSet rs = st.executeQuery(validation);
        String um= " ";
        if (rs.next()) {
            um= rs.getString("unidad_medida");
        }
        return um;
    }
}
