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
import model.GastoAuxiliar;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class GastosController {

    private ObservableList<GastoAuxiliar> gastosAuxiliares;

    public GastosController() throws ClassNotFoundException, SQLException {
        this.gastosAuxiliares = FXCollections.observableArrayList();
        getGastosFromDataBase();
    }

    public ObservableList<GastoAuxiliar> getGastosAuxiliares() {
        return gastosAuxiliares;
    }

    public void setGastosAuxiliares(ObservableList<GastoAuxiliar> gastosAuxiliares) {
        this.gastosAuxiliares = gastosAuxiliares;
    }

    public int insertGasto(int idProducto, int idSalario) throws ClassNotFoundException, SQLException {
        int idToReturn = -1;
        Statement st = null;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM gastos_auxiliares WHERE (producto_id = '" + idProducto + "' AND salario_id = '" + idSalario + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO gastos_auxiliares (producto_id,salario_id) VALUES ('" + idProducto + "','" + idSalario + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM gastos_auxiliares WHERE (producto_id= '" + idProducto + "' AND salario_id = '" + idSalario + "');";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idToReturn = rsV.getInt("id");
            rsV.close();
        }
        st.close();
        rs.close();
        Controladora.getController().getProdCTL().getProductosFromDataBase();
        getGastosFromDataBase();
        return idToReturn;
    }

    public int updateGasto(int id, int nuevoIdProducto, int nuevoIdSalario) throws ClassNotFoundException, SQLException {
        int idToReturn = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM gastos_auxiliares WHERE (id = '" + id + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        // valido que el gasto exista
        if (rs.next()) {
            String validation2 = "SELECT * FROM productos WHERE (id = '" + nuevoIdProducto + "' AND eliminado = '0');";
            ResultSet rs2 = st.executeQuery(validation2);
            if (rs2.next()) {
                String validation3 = "SELECT * FROM salarios WHERE (id = '" + nuevoIdSalario + "' AND eliminado = '0');";
                ResultSet rs3 = st.executeQuery(validation3);
                // valido que el producto y el salario existan
                if (rs3.next()) {
                    String validation1 = "SELECT * FROM gastos_auxiliares WHERE (producto_id = '" + nuevoIdProducto + "' AND salario_id = '" + nuevoIdSalario + "' AND eliminado = '0');";
                    ResultSet rs1 = st.executeQuery(validation1);
                    // valido que no exista nigun gasto igual al que sea modificado
                    if (!rs1.next()) {
                        String sql1 = "UPDATE gastos_auxiliares SET producto_id = '" + nuevoIdProducto + "' WHERE id= '" + id+ "';";
                        String sql2 = "UPDATE gastos_auxiliares SET salario_id = '" + nuevoIdSalario + "' WHERE id= '" + id + "';";
                        st.executeUpdate(sql1);
                        st.executeUpdate(sql2);
                        ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                        idToReturn = id;
                    }
                    rs1.close();
                }
                rs3.close();
            }
            rs2.close();
        }
        rs.close();
        st.close();
        Controladora.getController().getProdCTL().getProductosFromDataBase();
        getGastosFromDataBase();
        return idToReturn;
    }

    public boolean eliminarLogicoGasto(int idGasto) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM gastos_auxiliares WHERE id= '" + idGasto + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE gastos_auxiliares SET eliminado = ('" + 1 + "') WHERE id = '" + idGasto + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }
        st.close();
        rs.close();
        Controladora.getController().getProdCTL().getProductosFromDataBase();
        getGastosFromDataBase();
        return isDeleted;
    }

    public void getGastosFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<GastoAuxiliar> gastosAux = new ArrayList<>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM gastos_auxiliares WHERE eliminado == 0;");
        GastoAuxiliar ga = null;
        while (rs.next()) {
            ga = new GastoAuxiliar(rs.getInt("id"), rs.getInt("producto_id"), rs.getInt("salario_id"));
            gastosAux.add(ga);
        }
        st.close();
        rs.close();
        gastosAuxiliares.clear();
        gastosAuxiliares.addAll(gastosAux);
    }

    public int getPosition(int id) throws ClassNotFoundException, SQLException {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < gastosAuxiliares.size()) {
            if (id == gastosAuxiliares.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }

    public ObservableList<GastoAuxiliar> getGastosByProducto(int idProd) throws ClassNotFoundException, SQLException {
        ObservableList<GastoAuxiliar> gastos = FXCollections.observableArrayList();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM gastos_auxiliares WHERE (producto_id = '" + idProd + "' AND eliminado = '0');");
        while (rs.next()) {
            if (rs.getInt("producto_id") == idProd) {
                GastoAuxiliar ga = new GastoAuxiliar(rs.getInt("id"), rs.getInt("producto_id"), rs.getInt("salario_id"));
                gastos.add(ga);
            }
        }
        st.close();
        rs.close();
        return gastos;
    }
}
