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
import model.Receta;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class RecetasController {

    private ObservableList<Receta> recetas;

    public RecetasController() throws ClassNotFoundException, SQLException {
        this.recetas = FXCollections.observableArrayList();
        getRecetasFromDataBase();
    }

    public ObservableList<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(ObservableList<Receta> recetas) {
        this.recetas = recetas;
    }

    public int insertReceta(int idProducto, int idIngrediente, float cantidad, float valor) throws ClassNotFoundException, SQLException {
        int idInserted = -1;
        Statement st = null;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation1 = "SELECT * FROM productos WHERE (id= '" + idProducto + "' AND eliminado = '0');";
        ResultSet rs1 = st.executeQuery(validation1);
        if (rs1.next()) {
            String validation2 = "SELECT * FROM ingredientes WHERE (id= '" + idIngrediente + "' AND eliminado = '0');";
            ResultSet rs2 = st.executeQuery(validation2);
            if (rs2.next()) {
                String validation3 = "SELECT * FROM recetas WHERE (producto_id= '" + idProducto + "' AND ingrediente_id = '" + idIngrediente + "' AND eliminado = '0');";
                ResultSet rs3 = st.executeQuery(validation3);
                if (!rs3.next()) {
                    String sql = "INSERT INTO recetas (producto_id,ingrediente_id,cantidad,valor) VALUES ('" + idProducto + "','" + idIngrediente + "','" + cantidad + "','" + valor + "');";
                    st.executeUpdate(sql);
                    ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                    String sqlSelect = "SELECT * FROM recetas WHERE (producto_id= '" + idProducto + "' AND ingrediente_id = '" + idIngrediente + "' AND cantidad = '" + cantidad + "' AND valor = '" + valor + "');";
                    ResultSet rsV = st.executeQuery(sqlSelect);
                    idInserted = rsV.getInt("id");
                    rsV.close();
                }
                rs3.close();
            }
            rs2.close();
        }
        st.close();
        rs1.close();
        getRecetasFromDataBase();
        return idInserted;
    }

    public int updateReceta(int id, int nuevoIdProducto, int nuevoIdIngrediente, float nuevaCantidad, float nuevoValor) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM recetas WHERE (id = '" + id + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String validation1 = "SELECT * FROM productos WHERE (id= '" + nuevoIdProducto + "' AND eliminado = '0');";
            ResultSet rs1 = st.executeQuery(validation1);
            if (rs1.next()) {
                String validation2 = "SELECT * FROM ingredientes WHERE (id= '" + nuevoIdIngrediente + "' AND eliminado = '0');";
                ResultSet rs2 = st.executeQuery(validation2);
                if (rs2.next()) {
                    String validation3 = "SELECT * FROM recetas WHERE (producto_id= '" + nuevoIdProducto + "' AND ingrediente_id = '" + nuevoIdIngrediente + "' AND eliminado = '0');";
                    ResultSet rs3 = st.executeQuery(validation3);
                    if (rs3.getInt("id") == id) {
                        String sql1 = "UPDATE recetas SET producto_id = ('" + nuevoIdProducto + "') WHERE id= '" + id + "';";
                        String sql2 = "UPDATE recetas SET ingrediente_id = ('" + nuevoIdIngrediente + "') WHERE id= '" + id + "';";
                        String sql3 = "UPDATE recetas SET cantidad = ('" + nuevaCantidad + "') WHERE id= '" + id + "';";
                        String sql4 = "UPDATE recetas SET valor = ('" + nuevoValor + "') WHERE id= '" + id + "';";
                        st.executeUpdate(sql1);
                        st.executeUpdate(sql2);
                        st.executeUpdate(sql3);
                        st.executeUpdate(sql4);
                        ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                        idUpdated = id;
                    }
                }
                rs2.close();
            }
            rs1.close();
        }
        st.close();
        rs.close();
        getRecetasFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoReceta(int idReceta) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM recetas WHERE id= '" + idReceta + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE recetas SET eliminado = ('" + 1 + "') WHERE id = '" + idReceta + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }

        st.close();
        rs.close();
        getRecetasFromDataBase();
        return isDeleted;
    }

    public void getRecetasFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<Receta> rts = new ArrayList<Receta>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM recetas WHERE eliminado == 0;");
        Receta rec = null;
        while (rs.next()) {
            rec = new Receta(rs.getInt("id"), rs.getInt("producto_id"), rs.getInt("ingrediente_id"), rs.getFloat("cantidad"), rs.getFloat("valor"));
            rts.add(rec);
        }
        st.close();
        rs.close();
        recetas.clear();
        recetas.addAll(rts);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < recetas.size()) {
            if (id == recetas.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }

    public ObservableList<Receta> getRecetasByProducto(int idProd){
        ObservableList<Receta> rcts = FXCollections.observableArrayList();
        for (Receta rec : recetas) {
            if (rec.getProducto_id() == idProd) {
                rcts.add(rec);
            }
        }
        return rcts;
    }
    
    public boolean eliminarProducto (int idProducto) throws SQLException, ClassNotFoundException {
        boolean isDeleted= false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM recetas WHERE producto_id= '" + idProducto + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE recetas SET eliminado = ('" + 1 + "') WHERE producto_id = '" + idProducto + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted= true;
        }

        st.close();
        rs.close();
        getRecetasFromDataBase();
        return isDeleted;
    }
}
