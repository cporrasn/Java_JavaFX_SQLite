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
import model.PedidoProducto;
import model.Producto;
import util.ConnectionDatabase;

/**
 *
 * @author Raul
 */
public class PedidoProductoController {

    private ObservableList<PedidoProducto> pedidosProducto;

    public PedidoProductoController() throws ClassNotFoundException, SQLException {
        this.pedidosProducto = FXCollections.observableArrayList();
        getPedidoPedidosProductoFromDataBase();
    }

    public ObservableList<PedidoProducto> getPedidosProducto() {
        return pedidosProducto;
    }

    public void setPedidosProducto(ObservableList<PedidoProducto> pedidosProducto) {
        this.pedidosProducto = pedidosProducto;
    }

    public int insertPedidoProducto(int idProducto, int idPedido, int cantidad, float precio, int idMoneda, float costo, float ganacia) throws ClassNotFoundException, SQLException {
        Statement st = null;
        int idInserted = -1;
        st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation1 = "SELECT * FROM productos WHERE (id= '" + idProducto + "' AND eliminado = '0');";
        ResultSet rs1 = st.executeQuery(validation1);
        if (rs1.next()) {
            String validation2 = "SELECT * FROM pedidos WHERE (id= '" + idPedido + "' AND eliminado = '0');";
            ResultSet rs2 = st.executeQuery(validation2);
            if (rs2.next()) {
                String validation3 = "SELECT * FROM tipo_moneda WHERE (id= '" + idMoneda + "' AND eliminado = '0');";
                ResultSet rs3 = st.executeQuery(validation3);
                if (rs2.next()) {
                    String validation = "SELECT * FROM pedido_productos WHERE (producto_id= '" + idProducto + "' AND pedido_id = '" + idPedido + "' AND eliminado = '0');";
                    ResultSet rs = st.executeQuery(validation);
                    if (!rs.next()) {
                        String sql = "INSERT INTO pedido_productos (producto_id,pedido_id,cantidad,precio,moneda_id,costo_unitario,ganancia_unitaria) VALUES ('" + idProducto + "','" + idPedido + "','" + cantidad + "','" + precio + "','" + idMoneda + "','" + costo + "','" + ganacia + "')";
                        st.executeUpdate(sql);
                        ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                        String sqlSelect = "SELECT * FROM pedido_productos WHERE (producto_id= '" + idProducto + "' AND pedido_id = '" + idPedido + "');";
                        ResultSet rsV = st.executeQuery(sqlSelect);
                        idInserted = rsV.getInt("id");
                        rsV.close();
                    }
                    rs.close();
                }
                rs3.close();
            }
            rs2.close();
        }
        rs1.close();
        st.close();
        getPedidoPedidosProductoFromDataBase();
        return idInserted;
    }

    public int updatePedidoProducto(int id, int nuevoIdProducto, int nuevoIdPedido, int nuevaCantidad, float nuevoPrecio, int nuevoIdMoneda, float costoNuevo, float ganaciaNueva) throws ClassNotFoundException, SQLException {
        int idUpdated = -1;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM pedido_productos WHERE id = '" + id + "';";
        ResultSet rs = st.executeQuery(validation);
        if (rs.next()) {
            String validation1 = "SELECT * FROM productos WHERE (id= '" + nuevoIdProducto + "' AND eliminado = '0');";
            ResultSet rs1 = st.executeQuery(validation1);
            if (rs1.next()) {
                String validation2 = "SELECT * FROM pedidos WHERE (id= '" + nuevoIdPedido + "' AND eliminado = '0');";
                ResultSet rs2 = st.executeQuery(validation2);
                if (rs2.next()) {
                    String validation3 = "SELECT * FROM tipo_moneda WHERE (id= '" + nuevoIdMoneda + "' AND eliminado = '0');";
                    ResultSet rs3 = st.executeQuery(validation3);
                    if (rs3.next()) {
                        String val = "SELECT * FROM pedido_productos WHERE (producto_id= '" + nuevoIdProducto + "' AND pedido_id = '" + nuevoIdPedido + "' AND eliminado = '0');";
                        ResultSet rs4 = st.executeQuery(val);
                        if (!rs4.next() || rs4.getInt("id") == id) {
                            String sql1 = "UPDATE pedido_productos SET producto_id = ('" + nuevoIdProducto + "') WHERE id= '" + id + "';";
                            String sql2 = "UPDATE pedido_productos SET pedido_id = ('" + nuevoIdPedido + "') WHERE id= '" + id + "';";
                            String sql3 = "UPDATE pedido_productos SET cantidad = ('" + nuevaCantidad + "') WHERE id= '" + id + "';";
                            String sql4 = "UPDATE pedido_productos SET precio = ('" + nuevoPrecio + "') WHERE id= '" + id + "';";
                            String sql5 = "UPDATE pedido_productos SET moneda_id = ('" + nuevoIdMoneda + "') WHERE id= '" + id + "';";
                            String sql6 = "UPDATE pedido_productos SET costo_unitario = ('" + costoNuevo + "') WHERE id= '" + id + "';";
                            String sql7 = "UPDATE pedido_productos SET ganancia_unitaria = ('" + ganaciaNueva + "') WHERE id= '" + id + "';";
                            st.executeUpdate(sql1);
                            st.executeUpdate(sql2);
                            st.executeUpdate(sql3);
                            st.executeUpdate(sql4);
                            st.executeUpdate(sql5);
                            st.executeUpdate(sql6);
                            st.executeUpdate(sql7);
                            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
                            idUpdated = id;
                        }
                        rs4.close();
                    }
                    rs3.close();
                }
                rs2.close();
            }
            rs1.close();
        }
        st.close();
        rs.close();
        getPedidoPedidosProductoFromDataBase();
        return idUpdated;
    }

    public boolean eliminarLogicoPedidoProducto(int idPedidoProducto) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM pedido_productos WHERE id= '" + idPedidoProducto + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE pedido_productos SET eliminado = ('" + 1 + "') WHERE id = '" + idPedidoProducto + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }

        st.close();
        rs.close();
        getPedidoPedidosProductoFromDataBase();
        return isDeleted;
    }

    public void getPedidoPedidosProductoFromDataBase() throws ClassNotFoundException, SQLException {
        ArrayList<PedidoProducto> conceptos = new ArrayList<>();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM pedido_productos WHERE eliminado == 0;");
        PedidoProducto pedidoP = null;
        while (rs.next()) {
            pedidoP = new PedidoProducto(rs.getInt("id"), rs.getInt("producto_id"), rs.getInt("cantidad"), rs.getFloat("precio"), rs.getInt("pedido_id"), rs.getInt("moneda_id"), rs.getFloat("costo_unitario"), rs.getFloat("ganancia_unitaria"));
            conceptos.add(pedidoP);
        }
        st.close();
        rs.close();
        pedidosProducto.clear();
        pedidosProducto.addAll(conceptos);
    }

    public int getPosition(int id) {
        int pos = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < pedidosProducto.size()) {
            if (id == pedidosProducto.get(i).getId()) {
                found = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }

    public ObservableList<PedidoProducto> getPPByPedido(int idPedido) throws ClassNotFoundException, SQLException {

        ObservableList<PedidoProducto> conceptos = FXCollections.observableArrayList();
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM pedido_productos WHERE eliminado == 0 AND pedido_id='" + idPedido + "';");
        PedidoProducto pedidoP = null;
        while (rs.next()) {
            pedidoP = new PedidoProducto(rs.getInt("id"), rs.getInt("producto_id"), rs.getInt("cantidad"), rs.getFloat("precio"), rs.getInt("pedido_id"), rs.getInt("moneda_id"), rs.getFloat("costo_unitario"), rs.getFloat("ganancia_unitaria"));
            conceptos.add(pedidoP);
        }
        st.close();
        rs.close();
        return conceptos;
    }

    public ObservableList<Producto> getProductosByPedido(int idPedido) throws ClassNotFoundException, SQLException {
        ObservableList<Producto> prods = FXCollections.observableArrayList();
        ProductosController ped = new ProductosController();
        ObservableList<Producto> ps = ped.getProductos();
        ObservableList<PedidoProducto> pPs = getPPByPedido(idPedido);
        Iterator<PedidoProducto> iter = pPs.iterator();
        while (iter.hasNext()) {
            Iterator<Producto> it = ps.iterator();
            boolean found = false;
            PedidoProducto pedProd = iter.next();
            while (it.hasNext() && !found) {
                Producto prod = it.next();
                if (Controladora.getController().getProducto().getIDporNombre(pedProd.getProducto_id()) == prod.getId()) {
                    prods.add(prod);
                    found = true;
                }
            }
        }
        return prods;
    }

    public boolean eliminarPedido(int idPedido) throws SQLException, ClassNotFoundException {
        boolean isDeleted = false;
        Statement st = ConnectionDatabase.getConnectionDatabase().getConnection().createStatement();
        String validation = "SELECT * FROM pedido_productos WHERE pedido_id= '" + idPedido + "';";
        ResultSet rs = st.executeQuery(validation);

        if (rs.next()) {
            String sql = "UPDATE pedido_productos SET eliminado = ('" + 1 + "') WHERE pedido_id = '" + idPedido + "';";
            st.execute(sql);
            ConnectionDatabase.getConnectionDatabase().getConnection().commit();
            isDeleted = true;
        }

        st.close();
        rs.close();
        getPedidoPedidosProductoFromDataBase();
        return isDeleted;
    }
}
