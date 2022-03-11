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
import model.Salario;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class SalariosController {

    private ObservableList<Salario> salarios;

    public ObservableList<Salario> getSalarios() {
        return salarios;
    }

    public SalariosController() throws ClassNotFoundException, SQLException {
        this.salarios = FXCollections.observableArrayList();
        getSalariosFromDataBase();
    }

    public int insertSalario(int idConcepto, int idMoneda, float monto) throws ClassNotFoundException, SQLException {
        int idInserted = -1;
        Statement st = null;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM salarios WHERE (concepto_id = '" + idConcepto + "' AND moneda_id = '" + idMoneda + "' AND monto ='" + monto + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String validation1 = "SELECT * FROM conceptos_pago WHERE (id= '" + idConcepto + "' AND eliminado = '0');";
            ResultSet rs1 = st.executeQuery(validation1);
            if (rs1.next()) {
                String validation2 = "SELECT * FROM tipo_moneda WHERE (id= '" + idMoneda + "' AND eliminado = '0');";
                ResultSet rs2 = st.executeQuery(validation2);
                if (rs2.next()) {
                    String sql = "INSERT INTO salarios (concepto_id,moneda_id,monto) VALUES ('" + idConcepto + "','" + idMoneda + "','" + monto + "');";
                    st.executeUpdate(sql);
                    ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                    String sqlSelect = "SELECT * FROM salarios WHERE (concepto_id= '" + idConcepto + "' AND moneda_id = '" + idMoneda + "' AND monto = '" + monto + "');";
                    ResultSet rsV = st.executeQuery(sqlSelect);
                    idInserted = rsV.getInt("id");
                    rsV.close();
                }
                rs2.close();
            }
            rs1.close();
        }
        st.close();
        rs.close();
        getSalariosFromDataBase();
        return idInserted;
    }

    public int updateSalario(int id, int nuevoIdConcepto, int nuevoIdMoneda, float nuevoMonto) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM salarios WHERE (concepto_id = '" + nuevoIdConcepto + "' AND moneda_id = '" + nuevoIdMoneda + "' AND monto ='" + nuevoMonto + "' AND eliminado = '0');";
        ResultSet rs = st.executeQuery(validation);
        if (!rs.next()) {
            String validation3 = "SELECT * FROM salarios WHERE id = '" + id + "';";
            ResultSet rs3 = st.executeQuery(validation3);
            if (rs3.next()) {
                String validation1 = "SELECT * FROM conceptos_pago WHERE (id= '" + nuevoIdConcepto + "' AND eliminado = '0');";
                ResultSet rs1 = st.executeQuery(validation1);
                if (rs1.next()) {
                    String validation2 = "SELECT * FROM tipo_moneda WHERE (id= '" + nuevoIdMoneda + "' AND eliminado = '0');";
                    ResultSet rs2 = st.executeQuery(validation2);
                    if (rs2.next()) {
                        String sql1 = "UPDATE salarios SET concepto_id = ('" + nuevoIdConcepto + "') WHERE id= '" + id + "';";
                        String sql2 = "UPDATE salarios SET moneda_id = ('" + nuevoIdMoneda + "') WHERE id= '" + id + "';";
                        String sql3 = "UPDATE salarios SET monto = ('" + nuevoMonto + "') WHERE id= '" + id + "';";
                        st.executeUpdate(sql1);
                        st.executeUpdate(sql2);
                        st.executeUpdate(sql3);
                        ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                        idUpdated = id;
                    }
                    rs2.close();
                }
                rs1.close();
            }
            rs3.close();
        }
        st.close();
        rs.close();
        getSalariosFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoSalario(int idSalrio) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM salarios WHERE id= '" + idSalrio + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String sql = "UPDATE salarios SET eliminado = ('" + 1 + "') WHERE id = '" + idSalrio + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }
        st.close();
        rs.close();
        getSalariosFromDataBase();
        return isDeleted;
    }

    public void getSalariosFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<Salario> slas = new ArrayList<Salario>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM salarios WHERE eliminado == 0;");
        Salario s = null;
        while (rs.next()) {
            s = new Salario(rs.getInt("id"), rs.getInt("concepto_id"), rs.getInt("moneda_id"), rs.getFloat("monto"));
            slas.add(s);
        }
        st.close();
        rs.close();
        salarios.clear();
        salarios.addAll(slas);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < salarios.size()) {
            if (id == salarios.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }

    public int getSalarioConceptoById(int id) throws ClassNotFoundException, SQLException {
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM salarios WHERE (id = '" + id + "');";
        ResultSet rs = st.executeQuery(validation);
        int um = -1;
        if (rs.next()) {
            um = rs.getInt("concepto_id");
        }
        return um;
    }
}
