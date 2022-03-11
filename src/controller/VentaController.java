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
import model.Pedido;
import model.PedidoProducto;
import model.Producto;
import model.Venta;

/**
 *
 * @author ernesto.alfonso
 */
public class VentaController {

    public ObservableList<Venta> ventasSemanales() throws ClassNotFoundException, SQLException {
        ObservableList<Venta> ventas = FXCollections.observableArrayList();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicial = fechaActual.minusWeeks(1);
        ObservableList<Pedido> pedidos = Controladora.getController().getPedidosCTLR().pedidosCerrados();
        int cantPedidos = pedidos.size();
        while (cantPedidos > 0) {
            Iterator<Pedido> iter = pedidos.iterator();
            ObservableList<Venta> ventasParciales = FXCollections.observableArrayList();
            int cant = 0;
            float costoP = 0;
            float gan = 0;
            float ven = 0;
            while (iter.hasNext()) {
                Pedido pedido = iter.next();
                LocalDate fecha = LocalDate.parse(pedido.getFecha());
                if ((fecha.equals(fechaActual) || fecha.isBefore(fechaActual)) && (fecha.isAfter(fechaInicial) || fecha.equals(fechaInicial))) {
                    ObservableList<PedidoProducto> pps = Controladora.getController().getPedidoProductoCTLR().getPPByPedido(pedido.getId());
                    Iterator<PedidoProducto> itera = pps.iterator();
                    while (itera.hasNext()) {
                        PedidoProducto pp = itera.next();
                        int pos = existeVenta(ventasParciales, pp.getProducto_id());
                        if (pos != -1) {
                            cant += pp.getCantidad();
                            if (pp.getMoneda_id().equalsIgnoreCase("CUP")) {
                                costoP += pp.getCostoUnitario() * pp.getCantidad() / 25;
                                gan += pp.getGanaciaUnitaria() * pp.getCantidad() / 25;
                                ven += pp.getPrecio() * pp.getCantidad() / 25;
                            } else {
                                costoP += pp.getCostoUnitario() * pp.getCantidad();
                                gan += pp.getGanaciaUnitaria() * pp.getCantidad();
                                ven += pp.getPrecio() * pp.getCantidad();
                            }
                            ventasParciales.get(pos).setCantidad(ventasParciales.get(pos).getCantidad() + cant);
                            ventasParciales.get(pos).setCostoProd(ventasParciales.get(pos).getCostoProd() + costoP);
                            ventasParciales.get(pos).setGanancia(ventasParciales.get(pos).getGanancia() + gan);
                            ventasParciales.get(pos).setVenta(ventasParciales.get(pos).getVenta() + ven);
                        } else {
                            Venta venta = new Venta(pp.getProducto_id(), pp.getCantidad(), fechaInicial.toString(), fechaActual.toString(), Float.valueOf(0), Float.valueOf(0), Float.valueOf(0));
                            cant = pp.getCantidad();
                            if (pp.getMoneda_id().equalsIgnoreCase("CUP")) {
                                costoP = pp.getCostoUnitario() * pp.getCantidad() / 25;
                                gan = pp.getGanaciaUnitaria() * pp.getCantidad() / 25;
                                ven = pp.getPrecio() * pp.getCantidad() / 25;
                            } else {
                                costoP = pp.getCostoUnitario() * pp.getCantidad();
                                gan = pp.getGanaciaUnitaria() * pp.getCantidad();
                                ven = pp.getPrecio() * pp.getCantidad();
                            }
                            venta.setCantidad(cant);
                            venta.setCostoProd(costoP);
                            venta.setGanancia(gan);
                            venta.setVenta(ven);
                            ventasParciales.add(venta);
                        }
                    }
                    cantPedidos--;
                }
            }
            for (int a = 0; a < ventasParciales.size() - 1; a++) {
                for (int b = 1; b < ventasParciales.size(); b++) {
                    if (ventasParciales.get(b).getCantidad() > ventasParciales.get(a).getCantidad()) {
                        Venta ventTemp = ventasParciales.get(b);
                        ventasParciales.set(b, ventasParciales.get(a));
                        ventasParciales.set(a, ventTemp);
                    }
                }
            }
            ventas.addAll(ventasParciales);
            fechaActual = fechaInicial.minusDays(1);
            fechaInicial = fechaActual.minusWeeks(1);
        }
        return ventas;
    }
    
    public ObservableList<Venta> ventasMensuales() throws ClassNotFoundException, SQLException {
        ObservableList<Venta> ventas = FXCollections.observableArrayList();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicial = fechaActual.minusMonths(1);
        ObservableList<Pedido> pedidos = Controladora.getController().getPedidosCTLR().pedidosCerrados();
        int cantPedidos = pedidos.size();
        while (cantPedidos > 0) {
            Iterator<Pedido> iter = pedidos.iterator();
            ObservableList<Venta> ventasParciales = FXCollections.observableArrayList();
            int cant = 0;
            float costoP = 0;
            float gan = 0;
            float ven = 0;
            while (iter.hasNext()) {
                Pedido pedido = iter.next();
                LocalDate fecha = LocalDate.parse(pedido.getFecha());
                if ((fecha.equals(fechaActual) || fecha.isBefore(fechaActual)) && (fecha.isAfter(fechaInicial) || fecha.equals(fechaInicial))) {
                    ObservableList<PedidoProducto> pps = Controladora.getController().getPedidoProductoCTLR().getPPByPedido(pedido.getId());
                    Iterator<PedidoProducto> itera = pps.iterator();
                    while (itera.hasNext()) {
                        PedidoProducto pp = itera.next();
                        int pos = existeVenta(ventasParciales, pp.getProducto_id());
                        if (pos != -1) {
                            cant += pp.getCantidad();
                            if (pp.getMoneda_id().equalsIgnoreCase("CUP")) {
                                costoP += pp.getCostoUnitario() * pp.getCantidad() / 25;
                                gan += pp.getGanaciaUnitaria() * pp.getCantidad() / 25;
                                ven += pp.getPrecio() * pp.getCantidad() / 25;
                            } else {
                                costoP += pp.getCostoUnitario() * pp.getCantidad();
                                gan += pp.getGanaciaUnitaria() * pp.getCantidad();
                                ven += pp.getPrecio() * pp.getCantidad();
                            }
                            ventasParciales.get(pos).setCantidad(ventasParciales.get(pos).getCantidad() + cant);
                            ventasParciales.get(pos).setCostoProd(ventasParciales.get(pos).getCostoProd() + costoP);
                            ventasParciales.get(pos).setGanancia(ventasParciales.get(pos).getGanancia() + gan);
                            ventasParciales.get(pos).setVenta(ventasParciales.get(pos).getVenta() + ven);
                        } else {
                            Venta venta = new Venta(pp.getProducto_id(), pp.getCantidad(), fechaInicial.toString(), fechaActual.toString(), Float.valueOf(0), Float.valueOf(0), Float.valueOf(0));
                            cant = pp.getCantidad();
                            if (pp.getMoneda_id().equalsIgnoreCase("CUP")) {
                                costoP = pp.getCostoUnitario() * pp.getCantidad() / 25;
                                gan = pp.getGanaciaUnitaria() * pp.getCantidad() / 25;
                                ven = pp.getPrecio() * pp.getCantidad() / 25;
                            } else {
                                costoP = pp.getCostoUnitario() * pp.getCantidad();
                                gan = pp.getGanaciaUnitaria() * pp.getCantidad();
                                ven = pp.getPrecio() * pp.getCantidad();
                            }
                            venta.setCantidad(cant);
                            venta.setCostoProd(costoP);
                            venta.setGanancia(gan);
                            venta.setVenta(ven);
                            ventasParciales.add(venta);
                        }
                    }
                    cantPedidos--;
                }
            }
            for (int a = 0; a < ventasParciales.size() - 1; a++) {
                for (int b = 1; b < ventasParciales.size(); b++) {
                    if (ventasParciales.get(b).getCantidad() > ventasParciales.get(a).getCantidad()) {
                        Venta ventTemp = ventasParciales.get(b);
                        ventasParciales.set(b, ventasParciales.get(a));
                        ventasParciales.set(a, ventTemp);
                    }
                }
            }
            ventas.addAll(ventasParciales);
            fechaActual = fechaInicial.minusDays(1);
            fechaInicial = fechaActual.minusMonths(1);
        }
        return ventas;
    }

    public int existeVenta(ObservableList<Venta> ventas, String producto) {
        int pos = -1;
        int i = 0;
        while (i < ventas.size() && pos == -1) {
            Venta venta = ventas.get(i);
            if (venta.getProducto().equalsIgnoreCase(producto)) {
                pos = i;
            }
            i++;
        }
        return pos;
    }

}
