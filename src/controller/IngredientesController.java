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
import model.Ingrediente;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class IngredientesController {

    private ObservableList<Ingrediente> ingredientes;

    public IngredientesController() throws ClassNotFoundException, SQLException {
        this.ingredientes = FXCollections.observableArrayList();
        getIngredientesFromDatabase();
    }

    public ObservableList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ObservableList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public int insertIngrediente(String nombre, int um_id, float cantidad, float costo, int moneda_id) throws ClassNotFoundException, SQLException {
        int idToReturn = -1;
        Statement st = null;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM ingredientes WHERE (nombre = '" + nombre + "' AND costo = '" + costo + "' AND moneda_id ='" + moneda_id + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO ingredientes (nombre,um_id,cantidad,costo,moneda_id) VALUES ('" + nombre + "','" + um_id + "','" + cantidad + "','" + costo + "','" + moneda_id + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM ingredientes WHERE (nombre= '" + nombre + "' AND costo = '" + costo + "' AND moneda_id = '" + moneda_id + "' AND um_id = '" + um_id + "');";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idToReturn = rsV.getInt("id");
            rsV.close();
        } else {
            int idRs = rs.getInt("id");
//            String sql1 = "UPDATE ingredientes SET nombre = '" + nombre + "' WHERE id= '" + rs.getInt("id") + "';";
//            String sql2 = "UPDATE ingredientes SET um_id = '" + um_id + "' WHERE id= '" + rs.getInt("id") + "';";
            float cant = rs.getFloat("cantidad") + cantidad;
            String sql3 = "UPDATE ingredientes SET cantidad = '" + cant + "' WHERE id= '" + rs.getInt("id") + "';";
//            String sql4 = "UPDATE ingredientes SET costo = '" + costo + "' WHERE id= '" + rs.getInt("id") + "';";
//            String sql5 = "UPDATE ingredientes SET moneda_id = '" + moneda_id + "' WHERE id= '" + rs.getInt("id") + "';";
            String sql6 = "UPDATE ingredientes SET eliminado = 0 WHERE id= '" + rs.getInt("id") + "';";
//            st.executeUpdate(sql1);
//            st.executeUpdate(sql2);
            st.executeUpdate(sql3);
//            st.executeUpdate(sql4);
//            st.executeUpdate(sql5);
            st.executeUpdate(sql6);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            idToReturn = idRs;
        }
        st.close();
        rs.close();
        getIngredientesFromDatabase();
        return idToReturn;
    }

    public int updateIngrediente(int id, String nuevoNombre, int nuevaUM_id, float nuevaCantidad, float nuevoCosto, int nuevaMonedaId) throws ClassNotFoundException, SQLException {
        int idToReturn = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM ingredientes WHERE (id = '" + id + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        // valido que el ingrediente exista
        if (rs.next()) {
            String validation1 = "SELECT * FROM tipo_moneda WHERE (id = '" + nuevaMonedaId + "' AND eliminado = '0');";
            ResultSet rs1 = st.executeQuery(validation1);
            String validation2 = "SELECT * FROM unidades_medida WHERE (id = '" + nuevaUM_id + "' AND eliminado = '0');";
            ResultSet rs2 = st.executeQuery(validation2);
            //valido que existan la moneda y la UM
            if (rs1.next() && rs2.next()) {
                String validation3 = "SELECT * FROM ingredientes WHERE (nombre = '" + nuevoNombre + "' AND um_id = '" + nuevaUM_id + "' AND cantidad = '" + nuevaCantidad + "' AND costo = '" + nuevoCosto + "' AND moneda_id = '" + nuevaMonedaId + "' AND id <> '" + id + "' AND eliminado = '0');";
                ResultSet rs3 = st.executeQuery(validation3);
                // valido que no exista otro ingrediente con los mismos datos del que pretende ser modificado
                if (!rs3.next()) {
                    int idRs = rs.getInt("id");
                    String sql1 = "UPDATE ingredientes SET nombre = '" + nuevoNombre + "' WHERE id= '" + rs.getInt("id") + "';";
                    String sql2 = "UPDATE ingredientes SET um_id = '" + nuevaUM_id + "' WHERE id= '" + rs.getInt("id") + "';";
                    String sql3 = "UPDATE ingredientes SET cantidad = '" + nuevaCantidad + "' WHERE id= '" + rs.getInt("id") + "';";
                    String sql4 = "UPDATE ingredientes SET costo = '" + nuevoCosto + "' WHERE id= '" + rs.getInt("id") + "';";
                    String sql5 = "UPDATE ingredientes SET moneda_id = '" + nuevaMonedaId + "' WHERE id= '" + rs.getInt("id") + "';";
                    st.executeUpdate(sql1);
                    st.executeUpdate(sql2);
                    st.executeUpdate(sql3);
                    st.executeUpdate(sql4);
                    st.executeUpdate(sql5);
                    ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                    idToReturn = idRs;
                }

            }
            rs1.close();
            rs2.close();
        }
        st.close();
        rs.close();
        getIngredientesFromDatabase();
        return idToReturn;
    }

    public boolean eliminarLogicoIngrediente(int idIngrediente) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM ingredientes WHERE id= '" + idIngrediente + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE ingredientes SET eliminado = ('" + 1 + "') WHERE id = '" + idIngrediente + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }

        st.close();
        rs.close();
        getIngredientesFromDatabase();
        return isDeleted;
    }

    public void getIngredientesFromDatabase() throws ClassNotFoundException, SQLException {
        ArrayList<Ingrediente> ings = new ArrayList<>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ingredientes WHERE eliminado == 0;");
        Ingrediente ing = null;
        while (rs.next()) {
            ing = new Ingrediente(rs.getInt("id"), rs.getString("nombre"), rs.getInt("um_id"), rs.getFloat("cantidad"), rs.getFloat("costo"), rs.getInt("moneda_id"));
            ings.add(ing);
        }
        st.close();
        rs.close();
        ingredientes.clear();
        ingredientes.addAll(ings);
    }

    public int getPosition(int id) throws ClassNotFoundException, SQLException {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < ingredientes.size()) {
            if (id == ingredientes.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }

    /**
     * obtener nombre de un ingrediente
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String getIngredienteById(int id) throws ClassNotFoundException, SQLException {
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM ingredientes WHERE (id = '" + id + "');";
        ResultSet rs = st.executeQuery(validation);
        String ing = " ";
        if (rs.next()) {
            ing = rs.getString("nombre");
        }
        return ing;
    }

    /**
     * obtener ingrediente
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Ingrediente getIngrediente(int id) throws ClassNotFoundException, SQLException {
        Ingrediente ing = null;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ingredientes WHERE (id = '" + id + "' AND eliminado = 0);");
        if (rs.next()) {
            ing = new Ingrediente(rs.getInt("id"), rs.getString("nombre"), rs.getInt("um_id"), rs.getFloat("cantidad"), rs.getFloat("costo"), rs.getInt("moneda_id"));
        }
        st.close();
        rs.close();
        return ing;
    }

    /**
     * disminuir la cantidad del ingrediente en el almacen
     *
     * @param id
     * @param cantidad
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean disminuirIngrediente(int id, float cantidad) throws ClassNotFoundException, SQLException {
        boolean dis = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String query = "SELECT * FROM ingredientes WHERE (id = '" + id + "' AND eliminado = '0')";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            if (rs.getFloat("cantidad") >= cantidad) {
                Float cantResult = rs.getFloat("cantidad") - cantidad;
                String sql = "UPDATE ingredientes SET cantidad = '" + cantResult + "' WHERE id = '" + id + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                dis = true;
            }
        }
        getIngredientesFromDatabase();
        st.close();
        rs.close();
        return dis;
    }
}
