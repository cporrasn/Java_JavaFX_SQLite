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
import model.Minimo;
import util.ConnectionDatabase;

/**
 *
 * @author ernesto.alfonso
 */
public class MinimosController {

    private ObservableList<Minimo> minimos;

    public MinimosController() throws ClassNotFoundException, SQLException {
        this.minimos = FXCollections.observableArrayList();
        getMinimosFromDataBase();
    }

    public ObservableList<Minimo> getMininmos() {
        return minimos;
    }

    public void setMininmos(ObservableList<Minimo> mininmos) {
        this.minimos = mininmos;
    }

    public int insertMinimo(int idIng, float cant) throws ClassNotFoundException, SQLException {
        Statement st = null;
        int idInserted = -1;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM minimos WHERE (ingrediente_id = '" + idIng + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO minimos (ingrediente_id,cantidad) VALUES ('" + idIng + "','" + cant + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM minimos WHERE ingrediente_id = '" + idIng + "';";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idInserted = rsV.getInt("id");
            rsV.close();
        }
        st.close();
        rs.close();
        getMinimosFromDataBase();
        return idInserted;
    }

    public int updateMininmo(int idMinimo, int nuevoIdIng, float nuevaCant) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM minimos WHERE id = '" + idMinimo + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String val = "SELECT * FROM minimos WHERE (ingrediente_id = '" + nuevoIdIng + "' AND eliminado = '0');";
            ResultSet rs1 = st.executeQuery(val);
            if (!rs1.next() || rs1.getInt("id") == idMinimo) {
                String sql = "UPDATE minimos SET ingrediente_id = ('" + nuevoIdIng + "') WHERE id= '" + idMinimo + "';";
                String sql1 = "UPDATE minimos SET cantidad = ('" + nuevaCant + "') WHERE id= '" + idMinimo + "';";
                st.executeUpdate(sql);
                st.executeUpdate(sql1);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idUpdated = idMinimo;
            }
            rs1.close();
        }
        st.close();
        rs.close();
        getMinimosFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoMinimo(int idMininmo) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM minimos WHERE id= '" + idMininmo + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE minimos SET eliminado = ('" + 1 + "') WHERE id = '" + idMininmo + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }

        st.close();
        rs.close();
        getMinimosFromDataBase();
        return isDeleted;
    }

    private void getMinimosFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<Minimo> mins = new ArrayList<Minimo>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM minimos WHERE eliminado == 0;");
        Minimo min = null;
        while (rs.next()) {
            float cant = rs.getFloat("cantidad");
            int id = rs.getInt("id");
            int idIng = rs.getInt("ingrediente_id");
            min = new Minimo(id, idIng, cant);
            mins.add(min);
        }
        st.close();
        rs.close();
        minimos.clear();
        minimos.addAll(mins);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < minimos.size()) {
            if (id == minimos.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }
    
    /**
     * obtener el minimo dado el id
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Minimo getMinimo(int id) throws ClassNotFoundException, SQLException{
        Minimo min= null;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM minimos WHERE (id = '" + id +"' AND eliminado = 0);");
        if(rs.next()){
            min= new Minimo(rs.getInt("id"), rs.getInt("ingrediente_id"), rs.getFloat("cantidad"));
        }
        st.close();
        rs.close();
        return min;
    }
}
