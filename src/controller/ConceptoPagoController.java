/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.ConceptoPago;
import util.ConnectionDatabase;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Raul
 */
public class ConceptoPagoController {

    private ObservableList<ConceptoPago> conceptosPago;

    public ConceptoPagoController() throws ClassNotFoundException, SQLException {
        this.conceptosPago = FXCollections.observableArrayList();
        getConceptosPagoFromDataBase();
    }

    public ObservableList<ConceptoPago> getConceptosPago() {
        return conceptosPago;
    }

    public void setConceptosPago(ObservableList conceptosPago) {
        this.conceptosPago = conceptosPago;
    }

    public int insertConeceptoPago(String nuevoConcepto) throws ClassNotFoundException, SQLException {
        Statement st = null;
        int idInserted = -1;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM conceptos_pago WHERE concepto= '" + nuevoConcepto + "';";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String sql = "INSERT INTO conceptos_pago (concepto) VALUES ('" + nuevoConcepto + "');";
            st.executeUpdate(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            String sqlSelect = "SELECT * FROM conceptos_pago WHERE concepto= '" + nuevoConcepto + "';";
            ResultSet rsV = st.executeQuery(sqlSelect);
            idInserted = rsV.getInt("id");
        } else {
            if (rs.getInt("eliminado") == 1) {
                int idEliminado = rs.getInt("id");
                String sql = "UPDATE conceptos_pago SET eliminado = ('" + 0 + "') WHERE id = '" + idEliminado + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idInserted = idEliminado;
            }
        }
        st.close();
        rs.close();
        getConceptosPagoFromDataBase();
        return idInserted;
    }

    public int updateConceptoPago(int idConcepto, String nuevoConcepto) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM conceptos_pago WHERE id = '" + idConcepto + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String val1 = "SELECT * FROM conceptos_pago WHERE (concepto = '" + nuevoConcepto + "');";
            ResultSet rs1 = st.executeQuery(val1);
            if (!rs1.next()) {
                String sql = "UPDATE conceptos_pago SET concepto = ('" + nuevoConcepto + "') WHERE id= '" + idConcepto + "';";
                st.executeUpdate(sql);
                ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                idUpdated = idConcepto;
            } else {
                if (rs1.getInt("eliminado") == 1) {
                    int idEliminado = rs1.getInt("id");
                    String sql = "UPDATE conceptos_pago SET eliminado = ('" + 1 + "') WHERE id= '" + idConcepto + "';";
                    String sql1 = "UPDATE conceptos_pago SET eliminado = ('" + 0 + "') WHERE id= '" + idEliminado + "';";
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
        getConceptosPagoFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoConceptoPago(int idConcepto) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM conceptos_pago WHERE id= '" + idConcepto + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE conceptos_pago SET eliminado = ('" + 1 + "') WHERE id = '" + idConcepto + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }

        st.close();
        rs.close();
        getConceptosPagoFromDataBase();
        return isDeleted;
    }

    public void getConceptosPagoFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<ConceptoPago> conceptos = new ArrayList<>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM conceptos_pago WHERE eliminado = '0';");
        ConceptoPago conceptoPago = null;
        while (rs.next()) {
            conceptoPago = new ConceptoPago(rs.getInt("id"), rs.getString("concepto"));
            conceptos.add(conceptoPago);
        }
        st.close();
        rs.close();
        conceptosPago.clear();
        conceptosPago.addAll(conceptos);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < conceptosPago.size()) {
            if (id == conceptosPago.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }
    
    public String getConceptoById(int id) throws ClassNotFoundException, SQLException{
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM conceptos_pago WHERE (id = '" + id + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        String concepto= "error";
        if (rs.next()) {
            concepto= rs.getString("concepto");
        }
        return concepto;
    }
}
