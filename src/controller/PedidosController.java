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
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Pedido;
import model.PedidoProducto;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class PedidosController {

    private ObservableList<Pedido> pedidos;

    public PedidosController() throws ClassNotFoundException, SQLException {
        this.pedidos = FXCollections.observableArrayList();
        getPedidosFromDataBase();
    }

    public ObservableList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ObservableList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public int insertPedido(int idCliente, String fecha, int idEstado) throws ClassNotFoundException, SQLException {
        int idInserted = -1;
        Statement st = null;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation1 = "SELECT * FROM clientes WHERE (id= '" + idCliente + "' AND eliminado = '0');";
        ResultSet rs1 = st.executeQuery(validation1);
        if (rs1.next()) {
            String validation2 = "SELECT * FROM estados_pedidos WHERE (id= '" + idEstado + "' AND eliminado = '0');";
            ResultSet rs2 = st.executeQuery(validation2);
            if (rs2.next()) {
                String val = "SELECT * FROM pedidos WHERE (cliente_id = '" + idCliente + "' AND fecha = '" + fecha + "' AND estado_id = '" + idEstado + "' AND eliminado = '0');";
                ResultSet rsVal = st.executeQuery(val);
                if (!rsVal.next()) {
                    String sql = "INSERT INTO pedidos (cliente_id,fecha,estado_id,eliminado) VALUES ('" + idCliente + "','" + fecha + "','" + idEstado + "','0');";
                    st.executeUpdate(sql);
                    ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                    String sqlSelect = "SELECT * FROM pedidos WHERE (cliente_id= '" + idCliente + "' AND fecha = '" + fecha + "' AND estado_id = '" + idEstado + "' AND eliminado='0');";
                    ResultSet rsV = st.executeQuery(sqlSelect);
                    idInserted = rsV.getInt("id");
                    rsV.close();
                }
            }
            rs2.close();
        }
        st.close();
        rs1.close();
        getPedidosFromDataBase();
        return idInserted;
    }

    public int updatePedido(int id, int nuevoIdCliente, String nuevaFecha, int nuevoIdEstado) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM pedidos WHERE id = '" + id + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String validation1 = "SELECT * FROM clientes WHERE (id= '" + nuevoIdCliente + "' AND eliminado = '0');";
            ResultSet rs1 = st.executeQuery(validation1);
            if (rs1.next()) {
                String validation2 = "SELECT * FROM estados_pedidos WHERE (id= '" + nuevoIdEstado + "' AND eliminado = '0');";
                ResultSet rs2 = st.executeQuery(validation2);
                if (rs2.next()) {
                    String sql1 = "UPDATE pedidos SET cliente_id = ('" + nuevoIdCliente + "') WHERE id= '" + id + "';";
                    String sql3 = "UPDATE pedidos SET fecha = ('" + nuevaFecha + "') WHERE id= '" + id + "';";
                    String sql4 = "UPDATE pedidos SET estado_id = ('" + nuevoIdEstado + "') WHERE id= '" + id + "';";
                    st.executeUpdate(sql1);
                    st.executeUpdate(sql3);
                    st.executeUpdate(sql4);
                    ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                    idUpdated = id;
                }
                rs2.close();
            }
            rs1.close();
        }
        st.close();
        rs.close();
        getPedidosFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoPedido(int idPedido) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM pedidos WHERE id= '" + idPedido + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String sql = "UPDATE pedidos SET eliminado = ('" + 1 + "') WHERE id = '" + idPedido + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }
        st.close();
        rs.close();
        getPedidosFromDataBase();
        return isDeleted;
    }

    public void getPedidosFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<Pedido> conceptos = new ArrayList<>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM pedidos WHERE eliminado == 0;");
        Pedido pedido = null;
        while (rs.next()) {
            pedido = new Pedido(rs.getInt("id"), rs.getInt("cliente_id"), rs.getString("fecha"), rs.getInt("estado_id"), gananciaByPedido(rs.getInt("id")));
            conceptos.add(pedido);
        }
        st.close();
        rs.close();
        pedidos.clear();
        pedidos.addAll(conceptos);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < pedidos.size()) {
            if (id == pedidos.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }

    public ObservableList<Pedido> getPedidosByCliente(int idCliente) throws ClassNotFoundException, SQLException {
        ObservableList<Pedido> pedidos1 = FXCollections.observableArrayList();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM pedidos WHERE eliminado == 0 AND cliente_id= '" + idCliente + "';");
        Pedido pedido = null;
        while (rs.next()) {
            pedido = new Pedido(rs.getInt("id"), rs.getInt("cliente_id"), rs.getString("fecha"), rs.getInt("estado_id"), gananciaByPedido(rs.getInt("id")));
            pedidos1.add(pedido);
        }
        st.close();
        rs.close();
        return pedidos1;
    }

    public Pedido getPedidoById(int id) {
        Pedido p = null;
        Iterator<Pedido> iter = getPedidos().iterator();
        boolean found = false;
        while (iter.hasNext() && !found) {
            Pedido pedido = iter.next();
            if (pedido.getId() == id) {
                found = true;
                p = pedido;
            }
        }
        return p;
    }

    /*   public ObservableList<Producto> productosPorPedido(int id) throws ClassNotFoundException, SQLException {
        ObservableList<Producto> prods = FXCollections.observableArrayList();
        Pedido p = getPedidoById(id);
        ObservableList<PedidoProducto> pps = Controladora.getController().obtenerTodosPedidoProducto();
        Iterator<PedidoProducto> iter = pps.iterator();
        while (iter.hasNext()) {
            PedidoProducto pp = iter.next();
            prods.add(Controladora.getController().getProdCTL().getProducto(pp.getidProducto()));
        }
        return prods;
    }*/
    public ObservableList<Pedido> pedidosCerrados() throws ClassNotFoundException, SQLException {
        ObservableList<Pedido> peds = FXCollections.observableArrayList();
        for (Pedido p : getPedidos()) {
            if (p.getEstado_id().equalsIgnoreCase("Cerrado")) {
                peds.add(p);
            }
        }
        return peds;
    }

    public float gananciaByPedido(int id) throws ClassNotFoundException, SQLException {
        float ganancia = Float.valueOf(0);
        ObservableList<PedidoProducto> pps = getPedidoProductoByPedido(id);
        for (PedidoProducto pedido : pps) {
            ganancia += pedido.getGanaciaUnitaria() * pedido.getCantidad();
        }
        return ganancia;
    }

    public ObservableList<PedidoProducto> getPedidoProductoByPedido(int idPedido) throws ClassNotFoundException, SQLException {
        ObservableList<PedidoProducto> pps = FXCollections.observableArrayList();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM pedido_productos WHERE eliminado == 0 AND pedido_id= '" + idPedido + "';");
        PedidoProducto pp = null;
        while (rs.next()) {
            //int id, int producto_id, int cantidad, float precio, int pedido_id, int moneda_id, float costo, float ganacia
            pp = new PedidoProducto(rs.getInt("id"), rs.getInt("producto_id"), rs.getInt("cantidad"), rs.getFloat("precio"), rs.getInt("pedido_id"), rs.getInt("moneda_id"), rs.getFloat("costo_unitario"), rs.getFloat("ganancia_unitaria"));
            pps.add(pp);
        }
        st.close();
        rs.close();
        return pps;
    }
}
