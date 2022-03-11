/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cliente;
import model.ConceptoPago;
import model.EstadoPedido;
import model.Ganancia;
import model.GastoAuxiliar;
import model.Ingrediente;
import model.Minimo;
import model.Pedido;
import model.PedidoProducto;
import model.Producto;
import model.Receta;
import model.Salario;
import model.TipoMoneda;
import model.UnidadMedida;
import util.ConnectionDatabase;

/**
 *
 * @author Cynthia
 */
public class Controladora {

//////////Controladoras de elementos////////////////////
    private ConceptoPagoController conceptosPagosCTLR = null;
    private EstadoPedidosController estadosCTLR = null;
    private TipoMonedaController tipoMonedaCTLR = null;
    private UnidadesMedidaController unidadMedidaCTLR = null;
    private ProductosController prodCTL = null;
    private ClientesController clientesCTLR = null;
    private IngredientesController ingredientesCTLR = null;
    private MinimosController minimosCTLR = null;
    private RecetasController recetasCTLR = null;
    private GastosController gastosCTLR = null;
    private SalariosController salariosCTLR = null;
    private PedidosController pedidosCTLR = null;
    private GananciasController gananciasCTRL = null;
    private VentaController ventasCTLR = null;
    private PedidoProductoController pedidoProductoCTLR = null;

///////////////Patron Singleton///////////////////////
    private static Controladora controladora = null;

    public static Controladora getController() throws ClassNotFoundException, SQLException {
        if (controladora == null) {
            controladora = new Controladora();
        }
        return controladora;
    }

    public Controladora() throws ClassNotFoundException, SQLException {
        ConnectionDatabase.getConnectionDatabase();
        conceptosPagosCTLR = new ConceptoPagoController();
        estadosCTLR = new EstadoPedidosController();
        tipoMonedaCTLR = new TipoMonedaController();
        unidadMedidaCTLR = new UnidadesMedidaController();
        prodCTL = new ProductosController();
        clientesCTLR = new ClientesController();
        ingredientesCTLR = new IngredientesController();
        recetasCTLR = new RecetasController();
        minimosCTLR = new MinimosController();
        salariosCTLR = new SalariosController();
        gastosCTLR = new GastosController();
        pedidoProductoCTLR = new PedidoProductoController();
        ventasCTLR = new VentaController();
        gananciasCTRL = new GananciasController();
        pedidosCTLR = new PedidosController();
    }

///////////////Patron Singleton///////////////////////
    public PedidoProductoController getPedidoProductoCTLR() {
        return pedidoProductoCTLR;
    }

    public void setPedidoProductoCTLR(PedidoProductoController pedidoProductoCTLR) {
        this.pedidoProductoCTLR = pedidoProductoCTLR;
    }

    public ObservableList<PedidoProducto> obtenerTodosPedidoProducto() {
        return pedidoProductoCTLR.getPedidosProducto();
    }

    public ProductosController getProdCTL() {
        return prodCTL;
    }

    public MinimosController getMinimos() {
        return minimosCTLR;
    }

    public void setMinimos(MinimosController minimos) {
        this.minimosCTLR = minimos;
    }

    public GananciasController getGananciasCTRL() {
        return gananciasCTRL;
    }

    public void setGananciasCTRL(GananciasController gananciasCTRL) {
        this.gananciasCTRL = gananciasCTRL;
    }

    public VentaController getVentasCTLR() {
        return ventasCTLR;
    }

    public void setVentasCTLR(VentaController ventasCTLR) {
        this.ventasCTLR = ventasCTLR;
    }

    public ObservableList<Minimo> obtenerTodosMinimos() {
        return minimosCTLR.getMininmos();
    }

    public void setProdCTL(ProductosController prodCTL) {
        this.prodCTL = prodCTL;
    }

    public ObservableList<Producto> obtenerTodosProductos() {
        return prodCTL.getProductos();
    }

    public ObservableList<GastoAuxiliar> obtenerTodosGastos() {
        return gastosCTLR.getGastosAuxiliares();
    }

    public ObservableList<Pedido> obtenerTodosPedidos() {
        return pedidosCTLR.getPedidos();
    }

    public ObservableList<Salario> obtenerTodoSalarios() {
        return salariosCTLR.getSalarios();
    }

    public ConceptoPagoController getConceptosPagos() {
        return conceptosPagosCTLR;
    }

    public void setConceptosPagos(ConceptoPagoController conceptosPagos) {
        this.conceptosPagosCTLR = conceptosPagos;
    }

    public ObservableList<ConceptoPago> obtenerTodosConceptosPagos() {
        return conceptosPagosCTLR.getConceptosPago();
    }

    public EstadoPedidosController getEstados() {
        return estadosCTLR;
    }

    public void setEstados(EstadoPedidosController estados) {
        this.estadosCTLR = estados;
    }

    public ObservableList<EstadoPedido> obtenerTodosEstadosPedidos() {
        return estadosCTLR.getEstadoPedidos();
    }

    public TipoMonedaController getTipoMoneda() {
        return tipoMonedaCTLR;
    }

    public void setTipoMoneda(TipoMonedaController tipoMoneda) {
        this.tipoMonedaCTLR = tipoMoneda;
    }

    public ObservableList<TipoMoneda> obtenerTodosTipoMoneda() {
        return tipoMonedaCTLR.getTiposMoneda();
    }

    public ObservableList<String> obtenerTodosTipoMonedaString() {
        return tipoMonedaCTLR.obtenerTodosTipoMonedaString();
    }

    public ObservableList<String> obtenerTodosUnidadMedidaString() {
        return unidadMedidaCTLR.obtenerTodosUnidadMedidaString();
    }

    public ObservableList<String> obtenerTodosEstadosString() {
        return estadosCTLR.obtenerTodosEstadosString();
    }

    public UnidadesMedidaController getUnidadMedida() {
        return unidadMedidaCTLR;
    }

    public void setUnidadMedida(UnidadesMedidaController unidadMedida) {
        this.unidadMedidaCTLR = unidadMedida;
    }

    public ObservableList<UnidadMedida> obtenerTodosUnidadMedida() {
        return unidadMedidaCTLR.getUnidadesMedida();
    }

    public int insertConceptoPago(String concepto) throws ClassNotFoundException, SQLException {
        return conceptosPagosCTLR.insertConeceptoPago(concepto);
    }

    public void updateConceptoPago(int id, String nuevoConcepto) throws ClassNotFoundException, SQLException {
        conceptosPagosCTLR.updateConceptoPago(id, nuevoConcepto);
    }

    public void deleteConceptoPago(int pos) throws ClassNotFoundException, SQLException {
        conceptosPagosCTLR.eliminarLogicoConceptoPago(conceptosPagosCTLR.getConceptosPago().get(pos).getId());
    }

    public int insertarEstado(String estado) throws ClassNotFoundException, SQLException {
        return estadosCTLR.insertEstadoPedido(estado);
    }

    public PedidosController getPedidosCTLR() {
        return pedidosCTLR;
    }

    public void updateEstado(int id, String nuevoEstado) throws ClassNotFoundException, SQLException {
        estadosCTLR.updateEstadoPedido(id, nuevoEstado);
    }

    public void deleteEstado(int pos) throws ClassNotFoundException, SQLException {
        estadosCTLR.eliminarLogicoEstadoPedido(estadosCTLR.getEstadoPedidos().get(pos).getId());
    }

    public void insertTipoMoneda(String tm) throws ClassNotFoundException, SQLException {
        tipoMonedaCTLR.insertTipoMoneda(tm);
    }

    public void updateTipoMoneda(int id, String nuevoTM) throws ClassNotFoundException, SQLException {
        tipoMonedaCTLR.updateTipoMoneda(id, nuevoTM);
    }

    public void deleteTipoMoneda(int id) throws ClassNotFoundException, SQLException {
        tipoMonedaCTLR.eliminarLogicoTipoMoneda(id);
    }

    public void insertUM(String um) throws ClassNotFoundException, SQLException {
        unidadMedidaCTLR.insertUM(um);
    }

    public void updateUM(int id, String um) throws ClassNotFoundException, SQLException {
        unidadMedidaCTLR.updateUM(id, um);
    }

    public void deleteUM(int pos) throws ClassNotFoundException, SQLException {
        unidadMedidaCTLR.eliminarLogicoUM(unidadMedidaCTLR.getUnidadesMedida().get(pos).getId());
    }

    public void updateProducto(int id, String prod) throws ClassNotFoundException, SQLException {
        prodCTL.updateProducto(id, prod);
    }

    public void deleteProducto(int id) throws ClassNotFoundException, SQLException {
        prodCTL.eliminarLogicoProducto(id);
        recetasCTLR.eliminarProducto(id);
    }

    public void setClientes(ClientesController clientes) {
        this.clientesCTLR = clientes;
    }

    public ClientesController getClientes() {
        return clientesCTLR;
    }

    public int insertarCliente(String nombre) throws ClassNotFoundException, SQLException {
        int id = clientesCTLR.insertCliente(nombre);
        int pos = -1;
        if (id != -1) {
            pos = clientesCTLR.getPosition(id);
        }
        return pos;
    }

    public int modificarCliente(String nombre, int oldPos) throws ClassNotFoundException, SQLException {
        int id = clientesCTLR.updateCliente(clientesCTLR.getClientes().get(oldPos).getId(), nombre);
        int pos = -1;
        if (id != -1) {
            pos = clientesCTLR.getPosition(id);
        }
        return pos;
    }

    public void eliminarCliente(int pos) throws ClassNotFoundException, SQLException {
        clientesCTLR.eliminarLogicoCliente(clientesCTLR.getClientes().get(pos).getId());
    }

    public void eliminarGastoAuxiliar(int pos) throws ClassNotFoundException, SQLException {
        gastosCTLR.eliminarLogicoGasto(gastosCTLR.getGastosAuxiliares().get(pos).getId());
    }

    public void eliminarSalario(int pos) throws ClassNotFoundException, SQLException {
        salariosCTLR.eliminarLogicoSalario(salariosCTLR.getSalarios().get(pos).getId());
    }

    public ObservableList<Cliente> obtenerTodosClientes() {
        return clientesCTLR.getClientes();
    }

    public static void setControladora(Controladora controladora) {
        Controladora.controladora = controladora;
    }

    public IngredientesController getIngredientes() {
        return ingredientesCTLR;
    }

    public int insertarIngrediente(String nombre, int um, float cantidad, float costo, int moneda) throws ClassNotFoundException, SQLException {
        int pos = -1;
        int idUM = unidadMedidaCTLR.getUnidadesMedida().get(um).getId();
        if (tipoMonedaCTLR.getTipoMonedaById(moneda).equals("CUP")) {
            moneda = tipoMonedaCTLR.getIDporNombre("CUC");
            costo /= 25;
        }
        int idMoneda = tipoMonedaCTLR.getTiposMoneda().get(moneda).getId();
        int id = ingredientesCTLR.insertIngrediente(nombre, idUM, cantidad, costo, idMoneda);
        if (id != -1) {
            pos = ingredientesCTLR.getPosition(id);
        }
        return pos;
    }

    public ObservableList<Ingrediente> obtenerTodosIngredientes() {
        return ingredientesCTLR.getIngredientes();
    }

    public void eliminarIngredientes(int pos) throws ClassNotFoundException, SQLException {
        ingredientesCTLR.eliminarLogicoIngrediente(ingredientesCTLR.getIngredientes().get(pos).getId());
    }

    public void eliminarIngredienteRango(int pos) throws ClassNotFoundException, SQLException {
        minimosCTLR.eliminarLogicoMinimo(minimosCTLR.getMininmos().get(pos).getId());
    }

    public int modificarIngrediente(int oldPos, String nuevoNombre, int nuevaUM, float nuevaCantidad, float nuevoCosto, int nuevaMoneda) throws ClassNotFoundException, SQLException {
        int idUM = unidadMedidaCTLR.getUnidadesMedida().get(nuevaUM).getId();
        int idMoneda = tipoMonedaCTLR.getTiposMoneda().get(nuevaMoneda).getId();
        int id = ingredientesCTLR.updateIngrediente(ingredientesCTLR.getIngredientes().get(oldPos).getId(), nuevoNombre, idUM, nuevaCantidad, nuevoCosto, idMoneda);
        int pos = -1;
        if (id != -1) {
            pos = ingredientesCTLR.getPosition(id);
        }
        return pos;
    }

    public void eliminarProducto(int pos) throws ClassNotFoundException, SQLException {
        prodCTL.eliminarLogicoProducto(prodCTL.getProductos().get(pos).getId());
    }

    public int insertarProducto(String nombre, ObservableList<Receta> recetas) throws ClassNotFoundException, SQLException {
        int pos = -1;
        int id = prodCTL.insertProducto(nombre);
        if (id != -1) {
            Iterator<Receta> iter = recetas.iterator();
            boolean broken = false;
            while (iter.hasNext() && !broken) {
                Receta r = iter.next();
                int idRec = recetasCTLR.insertReceta(id, r.getIdIngrediente(), r.getCantidad(), r.getValue());
                if (idRec == -1) {
                    broken = true;
                }
            }
            if (broken) {
                prodCTL.eliminarLogicoProducto(id);
                recetasCTLR.eliminarProducto(id);
            } else {
                pos = prodCTL.getPosition(id);
            }
        }
        return pos;
    }

//    public int modificarProducto(int idProducto, String nombre, ObservableList<Receta> recetas) throws ClassNotFoundException, SQLException {
//        int pos = -1;
//        int id = prodCTL.updateProducto(idProducto, nombre);
//        if (id != -1) {
//            Iterator<Receta> iter = recetas.iterator();
//            boolean broken = false;
//            while (iter.hasNext() && !broken) {
//                Receta r = iter.next();
//                int idRec = recetasCTLR.updateReceta(r.getId(), id, r.getIdIngrediente(), r.getCantidad(), r.getValue());
//                if (idRec == -1) {
//                    broken = true;
//                }
//            }
//            if (broken) {
//                prodCTL.eliminarLogicoProducto(id);
//                recetasCTLR.eliminarProducto(id);
//            } else {
//                pos = prodCTL.getPosition(id);
//            }
//        }
//        return pos;
//    }
    public void setProducto(ProductosController producto) {
        this.prodCTL = producto;
    }

    public void setRecetas(RecetasController recetas) {
        this.recetasCTLR = recetas;
    }

    public ProductosController getProducto() {
        return prodCTL;
    }

    public RecetasController getRecetas() {
        return recetasCTLR;
    }

    public ObservableList<Receta> obtenerRecetaPorProducto(int idProducto) {
        return recetasCTLR.getRecetasByProducto(idProducto);
    }

    public float getCostoProduccionByProd(int idProducto) throws ClassNotFoundException, SQLException {
        float cp = 0;
        ObservableList<Receta> rects = FXCollections.observableArrayList();
        rects = recetasCTLR.getRecetasByProducto(idProducto);
        Iterator<Receta> iter = rects.iterator();
        while (iter.hasNext()) {
            Receta receta = iter.next();
            ObservableList<Ingrediente> ingredientesList = this.ingredientesCTLR.getIngredientes();
            Iterator<Ingrediente> it = ingredientesList.iterator();
            boolean found = false;
            while (it.hasNext() && !found) {
                Ingrediente ing = it.next();
                if (ing.getNombre().equalsIgnoreCase(receta.getIngrediente_id())) {
                    float cost = ing.getCosto();
                    if (ing.getMoneda_id().equalsIgnoreCase("CUP")) {
                        cost /= 25;
                    }
                    cp += receta.getCantidad() * cost;
                    found = true;
                }
            }
        }
        ObservableList<Salario> sals = getGastoByProducto(idProducto);
        Iterator<Salario> iterator = sals.iterator();
        while (iterator.hasNext()) {
            Salario salar = iterator.next();
            float monto = salar.getMonto();
            if (salar.getMoneda_id().equalsIgnoreCase("CUP")) {
                monto = monto / 25;
            }
            cp += monto;
        }
        return cp;
    }

    public ObservableList<Pedido> obtenerPedidosByCliente(int idCliente) throws ClassNotFoundException, SQLException {
        return pedidosCTLR.getPedidosByCliente(idCliente);
    }

    public void setIngredientes(IngredientesController ingredientes) {
        this.ingredientesCTLR = ingredientes;
    }

    public void setGastos(GastosController gastos) {
        this.gastosCTLR = gastos;
    }

    public void setSalarios(SalariosController salarios) {
        this.salariosCTLR = salarios;
    }

    public GastosController getGastos() {
        return gastosCTLR;
    }

    public SalariosController getSalarios() {
        return salariosCTLR;
    }

    public ObservableList<Salario> getGastoByProducto(int idProducto) throws ClassNotFoundException, SQLException {
        ObservableList<Salario> sals = FXCollections.observableArrayList();
        ObservableList<GastoAuxiliar> gas = gastosCTLR.getGastosByProducto(idProducto);
        Iterator<GastoAuxiliar> iter = gas.iterator();
        while (iter.hasNext()) {
            GastoAuxiliar ga = iter.next();
            ObservableList<Salario> salars = this.salariosCTLR.getSalarios();
            Iterator<Salario> it = salars.iterator();
            boolean found = false;
            while (it.hasNext() && !found) {
                Salario sal = it.next();
                if (sal.getId() == ga.getId_Salario()) {
                    found = true;
                    sals.add(sal);
                }
            }
        }
        return sals;
    }

    public boolean availableIngrediente(Ingrediente ingr, float cant) {
        boolean isPosible = false;
        if (ingr.getCantidad() >= cant) {
            isPosible = true;
        }
        return isPosible;
    }

    public boolean availableProducto(int productoPos, Ingrediente ingr) {
        boolean isPosible = false;
        Iterator<Receta> iter = recetasCTLR.getRecetasByProducto(productoPos).iterator();
        boolean broken = false;
        while (iter.hasNext() && !broken) {
            Receta receta = iter.next();
            if (!availableIngrediente(ingr, receta.getCantidad())) {
                broken = true;
            }
        }
        isPosible = !broken;
        return isPosible;
    }

    public int cantProductosDisponiblesConstruir(int prodPos) throws ClassNotFoundException, SQLException {
        int cant = 0;
        Producto prod = obtenerTodosProductos().get(prodPos);
        boolean broken = false;
        ObservableList<Receta> rects = recetasCTLR.getRecetasByProducto(prod.getId());
        while (!broken) {
            Iterator<Receta> iter = rects.iterator();
            while (iter.hasNext() && !broken) {
                Receta rec = iter.next();
                Ingrediente ingrec = ingredientesCTLR.getIngrediente(rec.getIdIngrediente());
                Iterator<Ingrediente> it = ingredientesCTLR.getIngredientes().iterator();
                boolean found = false;
                while (it.hasNext() && !found && !broken) {
                    Ingrediente ingr = it.next();
                    if (ingr.getNombre().equals(ingrec.getNombre())) {
                        found = true;
                        if (ingr.getCantidad() >= rec.getCantidad()) {
                            float c = ingr.getCantidad() - rec.getCantidad();
                            ingr.setCantidad(c);
                        } else {
                            broken = true;
                        }
                    }
                }
            }
            if (!broken) {
                cant++;
            }
        }
        ingredientesCTLR.getIngredientesFromDatabase();
        return cant;
    }

//    public void insertProductoReceta(String nombre, ObservableList<Receta> recetas) throws ClassNotFoundException, SQLException {
//        int idProd = prodCTL.insertProducto(nombre);
//        if (idProd != -1) {
//            for (Receta r : recetas) {
//                recetasCTLR.insertReceta(idProd, r.getIdIngrediente(), r.getCantidad(), r.getValue());
//            }
//        }
//    }
    public void disminuirAlmacenPorProducto(int idProd) throws ClassNotFoundException, SQLException {
        Producto p = prodCTL.getProducto(idProd);
        if (p != null) {
            ObservableList<Receta> recetas = recetasCTLR.getRecetasByProducto(idProd);
            Iterator<Receta> iter = recetas.iterator();
            while (iter.hasNext()) {
                Receta rec = iter.next();
                String nombIngrediente = ingredientesCTLR.getIngredienteById(rec.getIdIngrediente());
                ObservableList<Ingrediente> ingrs = ingredientesCTLR.getIngredientes();
                Ingrediente ing = null;
                for (Ingrediente i : ingrs) {
                    if (i.getNombre().equalsIgnoreCase(nombIngrediente) && i.getCantidad() >= rec.getCantidad()) {
                        if (ing == null) {
                            ing = i;
                        } else if (i.getCosto() < ing.getCosto()) {
                            ing = i;
                        }
                    }
                }
                if (ing != null) {
                    ingredientesCTLR.disminuirIngrediente(ing.getId(), rec.getCantidad());
                }
            }
        }
    }

    public void aumentarAlmacenPorProducto(int idProd, int cantProd) throws ClassNotFoundException, SQLException {
        Producto p = prodCTL.getProducto(idProd);
        if (p != null) {
            ObservableList<Receta> recetas = recetasCTLR.getRecetasByProducto(idProd);
            Iterator<Receta> iter = recetas.iterator();
            while (iter.hasNext()) {
                Receta rec = iter.next();
                ObservableList<Ingrediente> ingrs = ingredientesCTLR.getIngredientes();
                Iterator<Ingrediente> it = ingrs.iterator();
                boolean found = false;
                while (it.hasNext() && !found) {
                    Ingrediente ingred = it.next();
                    if (ingred.getId() == rec.getIdIngrediente()) {
                        found = true;
                        float nuevaCantidad = ingred.getCantidad() + (rec.getCantidad() * cantProd);
                        ingredientesCTLR.updateIngrediente(rec.getIdIngrediente(), ingred.getNombre(), ingred.getIdUm(), nuevaCantidad, ingred.getCosto(), ingred.getIdMoneda());
                    }
                }
            }
        }
    }

    public int insertarPedido(int idCliente, String fecha, int idEstado, ObservableList<PedidoProducto> pps) throws ClassNotFoundException, SQLException {
        int pos = -1;
        int idPedido = pedidosCTLR.insertPedido(idCliente, fecha, idEstado);
        if (idPedido != -1) {
            Iterator<PedidoProducto> iter = pps.iterator();
            boolean broken = false;
            while (iter.hasNext() && !broken) {
                PedidoProducto pProducto = iter.next();
                int idPp = pedidoProductoCTLR.insertPedidoProducto(prodCTL.getIDporNombre(pProducto.getProducto_id()), idPedido, pProducto.getCantidad(), pProducto.getPrecio(), tipoMonedaCTLR.getIDporNombre(pProducto.getMoneda_id()), pProducto.getCostoUnitario(), pProducto.getGanaciaUnitaria());
                if (idPp == -1) {
                    broken = true;
                } else if (idEstado != estadosCTLR.getIDEstadoByNombre("Almacén Insuficiente")) {
                    for (int i = 0; i < pps.size(); i++) {
                        for (int j = 0; j < pps.get(i).getCantidad(); j++) {
                            disminuirAlmacenPorProducto(prodCTL.getIDporNombre(pps.get(i).getProducto_id()));
                        }
                    }
                }
            }
            if (broken) {
                pedidosCTLR.eliminarLogicoPedido(idPedido);
                pedidoProductoCTLR.eliminarPedido(idPedido);
            } else {
                pos = pedidosCTLR.getPosition(idPedido);
            }
        }
        return pos;
    }

    ///modificar pedido
    public int modificarPedido(int idpedido, int idCliente, String fecha, int idEstado, ObservableList<PedidoProducto> pps) throws ClassNotFoundException, SQLException {
        int pos = -1;
        //   int idPedido = pedidosCTLR.updatePedido(idpedido,idCliente, fecha, idEstado);
        if (idpedido != -1) {
            ObservableList<PedidoProducto> pedidosViejos = pedidosCTLR.getPedidoProductoByPedido(idpedido);
            Iterator<PedidoProducto> iter = pps.iterator();
            boolean broken = false;
            while (iter.hasNext() && !broken) {
                PedidoProducto pProducto = iter.next();
                Iterator<PedidoProducto> iter1 = pedidosViejos.iterator();
                boolean found = false;
                while (iter1.hasNext() && !found) {
                    PedidoProducto pProductoViejo = iter1.next();
                    // si el pedido producto fue modificado hay que actualizarlo
                    if (idpedido == pProductoViejo.getIdPedido() && pProducto.getProducto_id().equals(pProductoViejo.getProducto_id())) {
                        pedidoProductoCTLR.updatePedidoProducto(pProductoViejo.getId(), prodCTL.getIDporNombre(pProducto.getProducto_id()), idpedido, pProducto.getCantidad(), pProducto.getPrecio(), tipoMonedaCTLR.getIDporNombre(pProducto.getMoneda_id()), pProducto.getCostoUnitario(), pProducto.getGanaciaUnitaria());
                        found = true;
                        if (pProducto.getCantidad() > pProductoViejo.getCantidad()) {
                            int cantToDism = pProducto.getCantidad() - pProductoViejo.getCantidad();
                            int posProd = prodCTL.getPosition(pProducto.getidProducto());
                            int cantPosible = cantProductosDisponiblesConstruir(posProd);
                            if (cantPosible < cantToDism) {
                                int idEstadoAI = estadosCTLR.getIDEstadoByNombre("Almacén Insuficiente");
                                pedidosCTLR.updatePedido(idpedido, idCliente, fecha, idEstadoAI);
                            } else {
                                for (int i = 0; i < cantToDism; i++) {
                                    disminuirAlmacenPorProducto(pProducto.getidProducto());
                                }
                            }
                        } else if (pProducto.getCantidad() < pProductoViejo.getCantidad()) {
                            if (idEstado != 3) {
                                int cantToAumen = pProductoViejo.getCantidad() - pProducto.getCantidad();
                                aumentarAlmacenPorProducto(pProducto.getidProducto(), cantToAumen);
                            }
                        }
                    }
                }
                ///si no encontro el nuevo pedidoproducto, se debe insertar
                if (!found) {
                    int idPp = pedidoProductoCTLR.insertPedidoProducto(pProducto.getidProducto(), idpedido, pProducto.getCantidad(), pProducto.getPrecio(), tipoMonedaCTLR.getIDporNombre(pProducto.getMoneda_id()), pProducto.getCostoUnitario(), pProducto.getGanaciaUnitaria());
                    if (idPp == -1) {
                        broken = true;
                    } else if (idEstado != 3) {
                        for (int i = 0; i < pps.size(); i++) {
                            for (int j = 0; j < pps.get(i).getCantidad(); j++) {
                                disminuirAlmacenPorProducto(pps.get(i).getidProducto());
                            }
                        }
                    }
                }
                if (broken) {
                    pedidosCTLR.eliminarLogicoPedido(idpedido);
                    pedidoProductoCTLR.eliminarPedido(idpedido);
                }/* else {
                pos = pedidosCTLR.getPosition(idpedido);
            }*/
            }
            //si hay algun pedido producto que antes estaba y ahora no hay que eliminarlo
            Iterator<PedidoProducto> iter1 = pedidosViejos.iterator();
            PedidoProducto pProducto = null;
            while (iter1.hasNext()) {
                PedidoProducto pProducto1 = iter1.next();
                boolean found = false;
                Iterator<PedidoProducto> iter2 = pps.iterator();
                while (iter2.hasNext() && !found) {
                    pProducto = iter2.next();
                    if (idpedido == pProducto1.getIdPedido() && pProducto.getProducto_id().equals(pProducto1.getProducto_id())) {
                        found = true;
                    }
                }
                if (!found) {
                    pedidoProductoCTLR.eliminarLogicoPedidoProducto(pProducto1.getId());
                    int cantToAumen = pProducto1.getCantidad();
                    aumentarAlmacenPorProducto(pProducto1.getidProducto(), cantToAumen);
                }
            }
        }
        pedidosCTLR.updatePedido(idpedido, idCliente, fecha, idEstado);
        return pos;
    }

    //modificar producto con recetas
    public int modificarProducto(int idProducto, String nombre, ObservableList<Receta> recetas) throws ClassNotFoundException, SQLException {
        int pos = -1;
        if (idProducto != -1) {
            ObservableList<Receta> recetasViejas = recetasCTLR.getRecetasByProducto(idProducto);
            Iterator<Receta> iterator = recetas.iterator();
            boolean broken = false;
            while (iterator.hasNext() && !broken) {
                Receta receta = iterator.next();
                Iterator<Receta> iter = recetasViejas.iterator();
                boolean found = false;
                while (iter.hasNext() && !found) {
                    Receta recetaVieja = iter.next();
                    if (receta.getIdIngrediente() == recetaVieja.getIdIngrediente()) {
                        recetasCTLR.updateReceta(recetaVieja.getId(), receta.getProducto_id(), receta.getIdIngrediente(), receta.getCantidad(), receta.getValue());
                        found = true;
                    }
                }
                if (!found) {
                    int idRec = recetasCTLR.insertReceta(receta.getProducto_id(), receta.getIdIngrediente(), receta.getCantidad(), receta.getValue());
                    if (idRec == -1) {
                        broken = true;
                    }
                }
                if (broken) {
                    recetasCTLR.eliminarProducto(idProducto);
                    prodCTL.eliminarLogicoProducto(idProducto);
                }
            }
            Iterator<Receta> iter1 = recetasViejas.iterator();
            while (iter1.hasNext()) {
                Receta rec1 = iter1.next();
                boolean found = false;
                Iterator<Receta> iter2 = recetas.iterator();
                while (iter2.hasNext() && !found) {
                    Receta rec2 = iter2.next();
                    if (rec1.getIdIngrediente() == rec2.getIdIngrediente()) {
                        found = true;
                    }
                }
                if (!found) {
                    recetasCTLR.eliminarLogicoReceta(rec1.getId());
                }
            }
            prodCTL.updateProducto(idProducto, nombre);
        }
        return pos;
    }

    /**
     * insertar los minimos de productos en el almacen para la alarma de que
     * quedan pocos
     *
     * @param idIgrediente
     * @param cant
     * @return position in minimos list
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public int insertarRangoCritico(int idIgrediente, float cant) throws ClassNotFoundException, SQLException {
        int pos = -1;
        int idMinimo = minimosCTLR.insertMinimo(idIgrediente, cant);
        if (idMinimo != -1) {
            pos = minimosCTLR.getPosition(idMinimo);
        }
        return pos;
    }

    /**
     * obtener todos los ingredientes del almacen que se estan agotando
     *
     * @return ingreds
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ObservableList<Integer> getIngredientesAcabandose() throws ClassNotFoundException, SQLException {
        ObservableList<Minimo> mins = obtenerTodosMinimos();
        ObservableList<Integer> ingreds = FXCollections.observableArrayList();
        Iterator<Minimo> iter = mins.iterator();
        while (iter.hasNext()) {
            Minimo min = iter.next();
            Ingrediente ing = ingredientesCTLR.getIngrediente(min.getIdIngrediente());
            if (ing != null) {
                if (ing.getCantidad() <= min.getCantidad()) {
                    ingreds.add(ingredientesCTLR.getPosition(min.getIdIngrediente()));
                }
            }
        }
        return ingreds;
    }

    public PedidoProducto obtenerGaranciaProductoPedido(PedidoProducto ped) throws ClassNotFoundException, SQLException {
        float costo = ped.getCostoUnitario();
        float precio = ped.getPrecio();
        if (ped.getMoneda_id().equals("CUP")) {
            precio /= 25;
        }
        ped.setMoneda_id(tipoMonedaCTLR.getIDporNombre("CUC"));
        ped.setGanaciaUnitaria(precio - costo);
        ped.setPrecio(precio);
        return ped;
    }

    public void insertarProductoPedidoInterfaz(ObservableList<PedidoProducto> pedidosProductos, PedidoProducto ped) throws ClassNotFoundException, SQLException {
        boolean enc = false;
        for (int i = 0; i < pedidosProductos.size() && !enc; i++) {
            if (prodCTL.getIDporNombre(ped.getProducto_id()) == prodCTL.getIDporNombre(pedidosProductos.get(i).getProducto_id())) {
                enc = true;
                if (ped.getMoneda_id().equals(pedidosProductos.get(i).getMoneda_id())) {
                    if (!ped.getPrecio().equals(pedidosProductos.get(i).getPrecio())) {
                        pedidosProductos.add(ped);
                    } else {
                        pedidosProductos.get(i).setCantidad(pedidosProductos.get(i).getCantidad() + ped.getCantidad());
                    }
                } else if (ped.getMoneda_id().equals("CUP")) {
                    Float precio = ped.getPrecio() / 25;
                    if (!precio.equals(pedidosProductos.get(i).getPrecio())) {
                        pedidosProductos.add(ped);
                    } else {
                        pedidosProductos.get(i).setCantidad(pedidosProductos.get(i).getCantidad() + ped.getCantidad());
                    }
                }
            }
        }
        if (!enc) {
            pedidosProductos.add(ped);
        }
    }

    //funcion que modifica una lista de productos pedidos dado un pedidoProducto
    public void modificarProductoPedidoInterfaz(ObservableList<PedidoProducto> pedidosProductos, PedidoProducto ped, int posViejo) throws ClassNotFoundException, SQLException {
//        boolean enc = false;
        ped.setId(pedidosProductos.get(posViejo).getId());
        pedidosProductos.set(posViejo, ped);
//        for (int i = 0; i < pedidosProductos.size() && !enc; i++) {
//            if (prodCTL.getIDporNombre(ped.getProducto_id()) == prodCTL.getIDporNombre(pedidosProductos.get(i).getProducto_id())) {
//                enc = true;
//                if (Objects.equals(pedidosProductos.get(i).getPrecio(), ped.getPrecio())) {
//                    pedidosProductos.set(i, ped);
//                } else if (i != posViejo) {
//                    pedidosProductos.remove(posViejo);
//                    pedidosProductos.get(i).setCantidad(ped.getCantidad() + pedidosProductos.get(i).getCantidad());
//                }
//            }
//        }
//        if (!enc) {
//            pedidosProductos.add(ped);
//        }
    }

    public void eliminarProductoPedidoInterfaz(ObservableList<PedidoProducto> pedidosProductos, PedidoProducto ped) throws ClassNotFoundException, SQLException {
        boolean enc = false;
        for (int i = 0; i < pedidosProductos.size() && !enc; i++) {
            if (prodCTL.getIDporNombre(ped.getProducto_id()) == prodCTL.getIDporNombre(pedidosProductos.get(i).getProducto_id())
                    && Objects.equals(ped.getPrecio(), pedidosProductos.get(i).getPrecio())
                    && ped.getMoneda_id().equals(pedidosProductos.get(i).getMoneda_id())) {
                enc = true;
                pedidosProductos.remove(i);
            }
        }
    }

    public int newCantidadProductoPedido(ObservableList<PedidoProducto> pedidosProductos, int posProd, int cant) throws ClassNotFoundException, SQLException {
        boolean enc = false;
        int cantNew = cant;
        for (int i = 0; i < pedidosProductos.size() && !enc; i++) {
            if (prodCTL.getProductos().get(posProd).getId() == prodCTL.getIDporNombre(pedidosProductos.get(i).getProducto_id())) {
                cantNew += pedidosProductos.get(i).getCantidad();
            }
        }
        return cantNew;
    }

    public int newCantidadProductoPedidoUpdate(ObservableList<PedidoProducto> pedidosProductos, int posProd, int cant, int posPediOld) throws ClassNotFoundException, SQLException {
        boolean enc = false;
        int cantNew = cant;
        for (int i = 0; i < pedidosProductos.size() && !enc; i++) {
            if (prodCTL.getProductos().get(posProd).getId() == prodCTL.getIDporNombre(pedidosProductos.get(i).getProducto_id()) && i != posPediOld) {
                cantNew += pedidosProductos.get(i).getCantidad();
            }
        }
        return cantNew;
    }

    public boolean eliminarProductoPedido(PedidoProducto ped) throws ClassNotFoundException, SQLException {
        return pedidoProductoCTLR.eliminarLogicoPedidoProducto(ped.getId());
    }

    public boolean eliminarPedido(int idPedido) throws ClassNotFoundException, SQLException {
        boolean pos = pedidosCTLR.eliminarLogicoPedido(idPedido);
        if (pos) {
            pedidoProductoCTLR.eliminarPedido(idPedido);
        }
        return pos;
    }

    public ObservableList<Ganancia> gananciasSemanales() throws ClassNotFoundException, SQLException {
        return gananciasCTRL.gananciasSemanales();
    }

    public ObservableList<Ganancia> gananciasMensuales() throws ClassNotFoundException, SQLException {
        return gananciasCTRL.gananciasMensuales();
    }

    public ObservableList<Pedido> pedidosFiltradosFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        ObservableList<Pedido> filtro = FXCollections.observableArrayList();
        for (Pedido ped : getPedidosCTLR().getPedidos()) {
            LocalDate fecha = LocalDate.parse(ped.getFecha());
            if ((fecha.equals(fechaFin) || fecha.isBefore(fechaFin)) && (fecha.isAfter(fechaInicio) || fecha.equals(fechaInicio))) {
                filtro.add(ped);
            }
        }
        return filtro;
    }

//    public ObservableList<Venta> ventasSemanales() throws ClassNotFoundException, SQLException{
//        return getVentasCTLR().ventasMensuales();
//    }
    public boolean insertarIngredientereceta(String cant, int posIng, ObservableList<Receta> recetas) throws ClassNotFoundException, SQLException {
        boolean enc = false;
        int pos = 0;
        float cant1 = Float.valueOf(cant);
        int iding = Controladora.getController().getIngredientes().getIngredientes().get(posIng).getId();
        if (!recetas.isEmpty()) {
            while (!enc && recetas.size() > pos) {
                if (recetas.get(pos).getIdIngrediente() == iding) {
                    enc = true;
                }
                pos++;
            }
        }
        if (!enc) {
            Receta rec = new Receta(iding, cant1);
            recetas.add(rec);
        }
        return enc;
    }

    public boolean modificarIngredientereceta(String cant, int posIng, int pos, ObservableList<Receta> recetas) throws ClassNotFoundException, SQLException {
        boolean enc = false;
        int pos1 = 0;
        int iding = Controladora.getController().getIngredientes().getIngredientes().get(posIng).getId();
        while (!enc && recetas.size() > pos1) {
            if (recetas.get(pos1).getIdIngrediente() == iding && pos1 != pos) {
                enc = true;
            }
            pos1++;
        }
        if (!enc) {
            recetas.get(pos).setCantidad(Float.valueOf(cant));
            recetas.get(pos).setIngrediente_id(Controladora.getController().getIngredientes().getIngredientes().get(posIng).getId());
        }
        return enc;
    }

}
