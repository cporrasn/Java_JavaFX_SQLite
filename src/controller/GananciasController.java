/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ganancia;
import model.Pedido;
import model.PedidoProducto;

/**
 *
 * @author ernesto.alfonso
 */
public class GananciasController {

    private ObservableList<Ganancia> gananciaList;

    public GananciasController() throws ClassNotFoundException, SQLException {
        this.gananciaList = FXCollections.observableArrayList();
    }

    public ObservableList<Ganancia> gananciasMensuales() throws ClassNotFoundException, SQLException {
        ObservableList<Ganancia> ganancias = FXCollections.observableArrayList();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicial = fechaActual.minusMonths(1);
        ObservableList<Pedido> pedidos = Controladora.getController().getPedidosCTLR().pedidosCerrados();
        int cantPedidos = pedidos.size();
        while (cantPedidos > 0) {
            Ganancia ganancia = new Ganancia(fechaInicial.toString(), fechaActual.toString(), Float.valueOf(0), Float.valueOf(0), Float.valueOf(0));
            Iterator<Pedido> iter = pedidos.iterator();
            float costoP = 0;
            float gan = 0;
            float venta = 0;
            while (iter.hasNext()) {
                Pedido pedido = iter.next();
                LocalDate fecha = LocalDate.parse(pedido.getFecha());
                if ((fecha.equals(fechaActual) || fecha.isBefore(fechaActual)) && (fecha.isAfter(fechaInicial) || fecha.equals(fechaInicial))) {
                    Iterator<PedidoProducto> it = Controladora.getController().getPedidoProductoCTLR().getPPByPedido(pedido.getId()).iterator();
                    while (it.hasNext()) {
                        PedidoProducto pp = it.next();
                        if (pp.getMoneda_id().equalsIgnoreCase("CUP")) {
                            costoP += pp.getCostoUnitario() * pp.getCantidad() / 25;
                            gan += pp.getGanaciaUnitaria() * pp.getCantidad() / 25;
                            venta += pp.getPrecio() * pp.getCantidad() / 25;
                        } else {
                            costoP += pp.getCostoUnitario() * pp.getCantidad();
                            gan += pp.getGanaciaUnitaria() * pp.getCantidad();
                            venta += pp.getPrecio() * pp.getCantidad();
                        }
                    }
                    cantPedidos--;
                }
            }
            ganancia.setCostoProd(costoP);
            ganancia.setGanancia(gan);
            ganancia.setVenta(venta);
            if (costoP > 0 || gan > 0 || venta > 0) {
                ganancias.add(ganancia);
            }
            fechaActual = fechaInicial.minusDays(1);
            fechaInicial = fechaActual.minusMonths(1);

        }
        return ganancias;
    }

    public ObservableList<Ganancia> gananciasSemanales() throws ClassNotFoundException, SQLException {
        ObservableList<Ganancia> ganancias = FXCollections.observableArrayList();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicial = fechaActual.minusWeeks(1);
        ObservableList<Pedido> pedidos = Controladora.getController().getPedidosCTLR().pedidosCerrados();
        int cantPedidos = pedidos.size();
        while (cantPedidos > 0) {
            Ganancia ganancia = new Ganancia(fechaInicial.toString(), fechaActual.toString(), Float.valueOf(0), Float.valueOf(0), Float.valueOf(0));
            Iterator<Pedido> iter = pedidos.iterator();
            float costoP = 0;
            float gan = 0;
            float venta = 0;
            while (iter.hasNext()) {
                Pedido pedido = iter.next();
                LocalDate fecha = LocalDate.parse(pedido.getFecha());
                if ((fecha.equals(fechaActual) || fecha.isBefore(fechaActual)) && (fecha.isAfter(fechaInicial) || fecha.equals(fechaInicial))) {
                    Iterator<PedidoProducto> it = Controladora.getController().getPedidoProductoCTLR().getPPByPedido(pedido.getId()).iterator();
                    while (it.hasNext()) {
                        PedidoProducto pp = it.next();
                        if (pp.getMoneda_id().equalsIgnoreCase("CUP")) {
                            costoP += pp.getCostoUnitario() * pp.getCantidad() / 25;
                            gan += pp.getGanaciaUnitaria() * pp.getCantidad() / 25;
                            venta += pp.getPrecio() * pp.getCantidad() / 25;
                        } else {
                            costoP += pp.getCostoUnitario() * pp.getCantidad();
                            gan += pp.getGanaciaUnitaria() * pp.getCantidad();
                            venta += pp.getPrecio() * pp.getCantidad();
                        }
                    }
                    cantPedidos--;
                }
            }
            ganancia.setCostoProd(costoP);
            ganancia.setGanancia(gan);
            ganancia.setVenta(venta);
            if (costoP > 0 || gan > 0 || venta > 0) {
                ganancias.add(ganancia);
            }
            fechaActual = fechaInicial.minusDays(1);
            fechaInicial = fechaActual.minusWeeks(1);

        }
        return ganancias;
    }

}
