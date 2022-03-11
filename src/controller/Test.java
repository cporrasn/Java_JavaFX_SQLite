/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.Ganancia;
import model.Minimo;
import model.PedidoProducto;
import model.Producto;
import model.Receta;
import model.Venta;

/**
 *
 * @author Raul
 */
public class Test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {

            TipoMonedaController tm = new TipoMonedaController();
            ConceptoPagoController cp = new ConceptoPagoController();
            EstadoPedidosController ep = new EstadoPedidosController();
            UnidadesMedidaController um = new UnidadesMedidaController();
            ProductosController p = new ProductosController();
            IngredientesController i = new IngredientesController();
            MinimosController m = new MinimosController();
            PedidoProductoController pp = new PedidoProductoController();
            PedidosController pe = new PedidosController();
            RecetasController r = new RecetasController();
            SalariosController s = new SalariosController();
            GastosController g= new GastosController();
            GananciasController gananciasCTLR= new GananciasController();
            Controladora c= new Controladora();
            VentaController ventas=new VentaController();

//        try {
            ObservableList<Venta> a= ventas.ventasMensuales();
            System.out.println(String.valueOf(a));
        } catch (Exception e) {
            System.out.println(e);
        }

//            ObservableList<Producto> list = pp.getProductosByPedido(1);
//            for (Producto pro : list) {
//                System.out.println(pro.getId());
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        
//        try {
//            String a = cp.getConceptoById(1);
//            System.out.println(a);
//        } catch (Exception e) {
//            System.out.println(e);
//        }

//            for(Minimo min : m.getMininmos()) {
//                System.out.println(min.getId() +" "+ min.getIngrediente_id() +" "+ min.getCantidad());
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }

//        try {
//            int a = cp.insertConeceptoPago("Test3CP");
//            if(a != -1) {
//                System.out.println(String.valueOf(a));
//            } else {
//                System.out.println(String.valueOf(a));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            int a = cp.updateConceptoPago(12, "mandados");
//            if (a != -1) {
//                System.out.println(String.valueOf(a));
//            } else {
//                System.out.println(String.valueOf(a));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//         try {
//            int a = ep.updateEstadoPedido(4, "encargo");
//            if (a != -1) {
//                System.out.println(String.valueOf(a));
//            } else {
//                System.out.println(String.valueOf(a));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//         try {
//            int a = ep.getPosition(1);
//            if (a != -1) {
//                System.out.println(String.valueOf(a));
//            } else {
//                System.out.println(String.valueOf(a));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            int a = um.insertUM("botella(s)");
//            if (a != -1) {
//                System.out.println(String.valueOf(a));
//            } else {
//                System.out.println(String.valueOf(a));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            int a = cp.getPosition(11);
//            if(a != -1) {
//                System.out.println(String.valueOf(a));
//            } else {
//                System.out.println(String.valueOf(a));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            int a = i.getPosition(53, 2);
//            
//            System.out.println(String.valueOf(a));
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            boolean a = p.updateProducto(5, "Browny", null);
//            if(a) {
//                System.out.println("ok");
//            } else {
//                System.out.println("mal");
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
