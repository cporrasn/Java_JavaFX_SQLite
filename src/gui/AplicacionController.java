/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Cynthia
 */
import controller.Controladora;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
import model.Venta;
import util.Carteles;

public class AplicacionController implements Initializable {

    private static AnchorPane page;
    public static Stage primaryStageListClientes = new Stage();

    @FXML
    private AnchorPane PedidosAnchorPane;

    @FXML
    private AnchorPane almacenAnchorPane;

    @FXML
    private DatePicker fechaInicioPedidoDatePicker;

    @FXML
    private DatePicker fechaFinPedidoDatePicker;

    @FXML
    private Button guardarCambiosRecetaButton;
    @FXML
    private TitledPane clientesTitledPane;

    @FXML
    private TextField nombreClienteTextField;

    @FXML
    private Button adicionarClienteButton;

    @FXML
    private Button modificarClienteButton;

    @FXML
    private Button eliminarClienteButton13;

    @FXML
    private TableView<Cliente> clientesTableView;

    private void configureTableClientes() throws ClassNotFoundException, SQLException {
        this.nombreClienteColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));

        this.clientesTableView.setItems(Controladora.getController().obtenerTodosClientes());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.clientesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.clientesTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarClientes(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    private TitledPane pedidosTitledPane;

    @FXML
    private TitledPane pedidosGestionTitledPane;

    @FXML
    void seleccionarClientes(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = clientesTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                nombreClienteTextField.setText(Controladora.getController().getClientes().getClientes().get(pos).getNombre());
                configureTablePedidos(Controladora.getController().getClientes().getClientes().get(pos).getId());
                pedidosTitledPane.setExpanded(true);
                modificarClienteButton.setDisable(false);
                eliminarClienteButton13.setDisable(false);
            }
        }
    }

    @FXML
    private TableColumn nombreClienteColumn;

    @FXML
    private TableView<Producto> productosPedidosTableView;

    private void configureTableProductosPedidos() throws ClassNotFoundException, SQLException {
        this.todosProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        this.todosCostoProductoPedidoColum.setCellValueFactory(new PropertyValueFactory<Producto, Float>("costo"));

        this.productosPedidosTableView.setItems(Controladora.getController().obtenerTodosProductos());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.productosPedidosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.productosPedidosTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarPedidosProductos(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarPedidosProductos(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = pedidosProductosTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                cantidadProductoPedidoTextField.setText(String.valueOf(productosPedidos.get(pos).getCantidad()));
                precioProductoPedidoTextField.setText(String.valueOf(productosPedidos.get(pos).getPrecio()));
                int idMoneda = Controladora.getController().getTipoMoneda().getIDporNombre(productosPedidos.get(pos).getMoneda_id());
                int posMoneda = Controladora.getController().getTipoMoneda().getPosition(idMoneda);
                int posPord = Controladora.getController().getProdCTL().getPosition(productosPedidos.get(pos).getidProducto());
                productosPedidosTableView.getSelectionModel().select(posPord);
                monedaProductoPedidoComboBox.getSelectionModel().select(posMoneda);
                modificarProductoPedidoButton.setDisable(false);
                eliminarProductoPedidoButton.setDisable(false);
            }
        }
    }

    @FXML
    private TableColumn todosProductoPedidoColumn;

    @FXML
    private TableColumn todosCostoProductoPedidoColum;

    @FXML
    private Label costoUnitarioIngredienteColumn1;

    @FXML
    private Label nombreIngredienteColumn1;

    @FXML
    private TextField cantidadProductoPedidoTextField;

    @FXML
    private TextField precioProductoPedidoTextField;

    @FXML
    private ComboBox<String> monedaProductoPedidoComboBox;

    public void configuracionMonedaProductoPedidoComboBox() throws ClassNotFoundException, SQLException {
        monedaProductoPedidoComboBox.setItems(Controladora.getController().obtenerTodosTipoMonedaString());
    }

    @FXML
    private DatePicker fechaProductoPedidoDatePicker;

    @FXML
    private ComboBox<String> estadoProductoPedidoComboBox;

    public void configuracionEstadoProductoPedidoComboBox() throws ClassNotFoundException, SQLException {
        estadoProductoPedidoComboBox.setItems(Controladora.getController().obtenerTodosEstadosString());
    }

    @FXML
    private Button eliminarPedidoButton;

    @FXML
    private Button modificarPedidoButton;

    @FXML
    private Button adicionarPedidoButton;

    @FXML
    private Button modificarIngredientesProductoPedidoButton;

    @FXML
    private Button modificarProductoPedidoButton;

    @FXML
    private Button eliminarProductoPedidoButton;

    @FXML
    private Button guardarPedidoButton;

    @FXML
    private TableView<Pedido> pedidosTableView;

    private void configureTablePedidos(int idCliente) throws ClassNotFoundException, SQLException {
//        this.clientePedidoColumn.setCellValueFactory(new PropertyValueFactory<Pedido, String>("cliente_id"));
        this.ganaciaPedidoColumn.setCellValueFactory(new PropertyValueFactory<Pedido, Float>("ganancia"));
        this.fechaProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<Pedido, String>("fecha"));
        this.estadoProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<Pedido, String>("estado_id"));

        if (idCliente != -1) {
            this.pedidosTableView.setItems(Controladora.getController().obtenerPedidosByCliente(idCliente));
        } else {
            this.pedidosTableView.setItems(Controladora.getController().getPedidosCTLR().getPedidos());
        }
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.pedidosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.pedidosTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarPedidos(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void configureTablePedidos(ObservableList<Pedido> pedidos) throws ClassNotFoundException, SQLException {
//        this.clientePedidoColumn.setCellValueFactory(new PropertyValueFactory<Pedido, String>("cliente_id"));
        this.ganaciaPedidoColumn.setCellValueFactory(new PropertyValueFactory<Pedido, Float>("ganancia"));
        this.fechaProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<Pedido, String>("fecha"));
        this.estadoProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<Pedido, String>("estado_id"));

        this.pedidosTableView.setItems(pedidos);
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.pedidosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.pedidosTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarPedidos(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    void seleccionarPedidos(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            Pedido pos = pedidosTableView.getSelectionModel().getSelectedItem();
            if (pos != null) {
                productosPedidos = Controladora.getController().getPedidoProductoCTLR().getPPByPedido(pos.getId());
                int isEstado = Controladora.getController().getEstados().getIDEstadoByNombre(pos.getEstado_id());
                int posEstado = Controladora.getController().getEstados().getPosition(isEstado);
                estadoProductoPedidoComboBox.getSelectionModel().select(posEstado);
                fechaProductoPedidoDatePicker.getEditor().setText(pos.getFecha());
                configureTablePedidosProductos(productosPedidos);
                modificarPedidoButton.setDisable(false);
                eliminarPedidoButton.setDisable(false);
            }
        }
    }

    ObservableList<PedidoProducto> productosPedidos = FXCollections.observableArrayList();

    @FXML
    private TableView<PedidoProducto> pedidosProductosTableView;

    private void configureTablePedidosProductos(ObservableList<PedidoProducto> pedidosProductos) {
        this.productoPedidoColumn.setCellValueFactory(new PropertyValueFactory<PedidoProducto, String>("producto_id"));
        this.precioProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<PedidoProducto, Float>("precio"));
        this.cantidadProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<PedidoProducto, Integer>("cantidad"));
        this.monedaProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<PedidoProducto, String>("moneda_id"));
        this.costoProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<PedidoProducto, Float>("costoUnitario"));
        this.ganaciaProductoPedidoColumn.setCellValueFactory(new PropertyValueFactory<PedidoProducto, Float>("ganaciaUnitaria"));

        this.pedidosProductosTableView.setItems(pedidosProductos);
        this.pedidosProductosTableView.refresh();
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.pedidosProductosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);

    }

    @FXML
    private TableColumn productoPedidoColumn;

    @FXML
    private TableColumn ganaciaPedidoColumn;

    @FXML
    private TableColumn cantidadProductoPedidoColumn;

    @FXML
    private TableColumn precioProductoPedidoColumn;

    @FXML
    private TableColumn costoProductoPedidoColumn;

    @FXML
    private TableColumn ganaciaProductoPedidoColumn;

    @FXML
    private TableColumn monedaProductoPedidoColumn;

    @FXML
    private TableColumn fechaProductoPedidoColumn;

    @FXML
    private TableColumn estadoProductoPedidoColumn;

    @FXML
    private TableView<Ingrediente> ingredientesTableViwe;

    private void configureTableIngredientes() throws ClassNotFoundException, SQLException {
        this.nombreIngredienteColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("nombre"));
        this.unidadIngredienteColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("um_id"));
        this.monedaIngredienteColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("moneda_id"));
        this.cantidadIngredienteColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, Float>("cantidad"));
        this.costoIngredienteColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("costo"));

        this.ingredientesTableViwe.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.ingredientesTableViwe.setItems(Controladora.getController().obtenerTodosIngredientes());

        ingredientesTableViwe.setRowFactory(param -> new TableRow<Ingrediente>() {
            @Override
            protected void updateItem(Ingrediente item, boolean empty) {
                try {
                    if (item == null) {
                        return;
                    }
                    String estilo;

                    ObservableList<Integer> lista = Controladora.getController().getIngredientesAcabandose();

                    if (lista.contains(ingredientesTableViwe.getItems().indexOf(item))) {
                        estilo = "-fx-background-color: linear-gradient(#FFC0CB 0%, #FFC0CB 90%, #e0e0e0 90%);";
                    } else {
//                        estilo = "-fx-background-color: linear-gradient(white 0%, white 90%, #e0e0e0 90%);";
                        estilo = productosTableView.getStyle();
                    }
                    setStyle(estilo);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//        assert this.tvTablaDem.getItems() == listDemandsNodes;
//        ingredientesTableViwe.getRowFactory();
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.ingredientesTableViwe.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarIngrediente(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarIngrediente(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = ingredientesTableViwe.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                nombreIngredienteTextField.setText(Controladora.getController().getIngredientes().getIngredientes().get(pos).getNombre());

                int idUnidad = Controladora.getController().getUnidadMedida().getIDporNombre(Controladora.getController().getIngredientes().getIngredientes().get(pos).getUm_id());
                int posUnidad = Controladora.getController().getUnidadMedida().getPosition(idUnidad);
                unidadIngredienteComboBox.getSelectionModel().select(posUnidad);

                float costo = Controladora.getController().getIngredientes().getIngredientes().get(pos).getCosto();
                costoIngredienteTextField.setText(String.valueOf(costo));

                int idMoneda = Controladora.getController().getTipoMoneda().getIDporNombre(Controladora.getController().getIngredientes().getIngredientes().get(pos).getMoneda_id());
                int posMoneda = Controladora.getController().getTipoMoneda().getPosition(idMoneda);
                tipoMonedaIngredienteComboBox.getSelectionModel().select(posMoneda);

                float cant = Controladora.getController().getIngredientes().getIngredientes().get(pos).getCantidad();
                cantidadIngredienteTextField.setText(String.valueOf(cant));

                ingrediantesTitledPane.setExpanded(true);
                modificarIngredienteButton.setDisable(false);
                eliminarIngredienteButton.setDisable(false);
            }
        }
    }

    @FXML
    private TableColumn nombreIngredienteColumn;

    @FXML
    private TableColumn cantidadIngredienteColumn;

    @FXML
    private TableColumn unidadIngredienteColumn;

    @FXML
    private TableColumn costoIngredienteColumn;

    @FXML
    private TableColumn monedaIngredienteColumn;

    @FXML
    private TitledPane ingrediantesTitledPane;

    @FXML
    private TextField nombreIngredienteTextField;

    @FXML
    private ComboBox<String> unidadIngredienteComboBox;

    public void configuracionUnidadIngredienteComboBox() throws ClassNotFoundException, SQLException {
        unidadIngredienteComboBox.setItems(Controladora.getController().obtenerTodosUnidadMedidaString());
    }

    @FXML
    private TextField costoIngredienteTextField;

    @FXML
    private ComboBox<String> tipoMonedaIngredienteComboBox;

    public void configuracionTipoMonedaIngredienteComboBox() throws ClassNotFoundException, SQLException {
        tipoMonedaIngredienteComboBox.setItems(Controladora.getController().obtenerTodosTipoMonedaString());
    }

    @FXML
    private ComboBox<String> cicloGanaciaComboBox;

    @FXML
    private ComboBox<String> cicloProductosComboBox;

    public void configuracionCiclosReportesComboBox() throws ClassNotFoundException, SQLException {
        cicloGanaciaComboBox.getItems().add("Semanal");
        cicloProductosComboBox.getItems().add("Semanal");
        cicloGanaciaComboBox.getItems().add("Mensual");
        cicloProductosComboBox.getItems().add("Mensual");
    }

    @FXML
    private TextField cantidadIngredienteTextField;

    @FXML
    private Button adicionarIngredienteButton;

    @FXML
    private Button modificarIngredienteButton;

    @FXML
    private Button eliminarIngredienteButton;

    @FXML
    private TableView<Producto> productosTableView;

    private void configureTableProductos() throws ClassNotFoundException, SQLException {
        this.nombreProductoColumn.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        this.productosTableView.setItems(Controladora.getController().obtenerTodosProductos());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.productosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.productosTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarProductosRecetas(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    void seleccionarProductosRecetas(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = productosTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                configureTableIngredientesAgregadosProductoReceta(Controladora.getController().getProducto().getProductos().get(pos).getId());
                configureTableRecetasTableView(Controladora.getController().getProducto().getProductos().get(pos).getId());
                nombreProductoTextField.setText(Controladora.getController().getProducto().getProductos().get(pos).getNombre());
                modificarProductoButton.setDisable(false);
                eliminarProductoButton.setDisable(false);
            }
        }
    }

    @FXML
    private TableColumn nombreProductoColumn;

    @FXML
    private TableView<Receta> recetasTableView;

    private void configureTableRecetasTableView(int idProducto) throws ClassNotFoundException, SQLException {
        this.nombreIngredienteRecetaColumn.setCellValueFactory(new PropertyValueFactory<Receta, String>("ingrediente_id"));
        this.cantidadIngredienteRecetaColumn.setCellValueFactory(new PropertyValueFactory<Receta, Float>("cantidad"));

        this.recetasTableView.setItems(Controladora.getController().obtenerRecetaPorProducto(idProducto));
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.recetasTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);

    }

    @FXML
    private TableColumn nombreIngredienteRecetaColumn;

    @FXML
    private TableColumn cantidadIngredienteRecetaColumn;

//    @FXML
//    private TableColumn valorIngredienteRecetaColumn;
    @FXML
    private TextField nombreProductoTextField;

    @FXML
    private TitledPane seleccionIngredientesProductoTitledPane;

    @FXML
    private TitledPane almacenTitledView;

    @FXML
    private TableView<Ingrediente> ingredientesProductoRecetaTableView;

    private void configureTableIngredientesProductoReceta() throws ClassNotFoundException, SQLException {
        this.nombreSeleccionIngredienteProductoRecetaColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("nombre"));
        this.unidadSeleccionIngredienteProductoRecetaColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("um_id"));

        this.ingredientesProductoRecetaTableView.setItems(Controladora.getController().obtenerTodosIngredientes());
        this.ingredientesProductoRecetaTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.ingredientesProductoRecetaTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarIngredienteReceta(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarIngredienteReceta(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = ingredientesProductoRecetaTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                int posIng = ingredientesAgregadosProductoRecetaTableView.getSelectionModel().getSelectedIndex();
                if (posIng == -1) {
                    modificarIngredienteProductoRecetaButton.setDisable(true);
                    eliminarIngredienteProductoRecetaButton.setDisable(true);
                } else {
                    modificarIngredienteProductoRecetaButton.setDisable(false);
                    eliminarIngredienteProductoRecetaButton.setDisable(true);
                }
                adicionarIngredienteProductoRecetaButton.setDisable(false);
            }
        }
    }

    @FXML
    private TableColumn nombreSeleccionIngredienteProductoRecetaColumn;

    @FXML
    private TableColumn unidadSeleccionIngredienteProductoRecetaColumn;

    @FXML
    private Button crearRecetaProductoButton;

    @FXML
    private TitledPane ingredientesAgregadosProductoRecetaTitledPane;

    @FXML
    private TableView<Receta> ingredientesAgregadosProductoRecetaTableView;

    ObservableList<Receta> recetas = FXCollections.observableArrayList();

    private void configureTableIngredientesAgregadosProductoReceta(int idProducto) throws ClassNotFoundException, SQLException {
        this.nombreIngredienteProductoRecetaColumn.setCellValueFactory(new PropertyValueFactory<Receta, String>("ingrediente_id"));
        this.cantidadIngredienteProductoRecetaColumn.setCellValueFactory(new PropertyValueFactory<Receta, Float>("cantidad"));

        if (idProducto != -1) {
            recetas = Controladora.getController().obtenerRecetaPorProducto(idProducto);
        }
        this.ingredientesAgregadosProductoRecetaTableView.setItems(recetas);
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.ingredientesAgregadosProductoRecetaTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.ingredientesAgregadosProductoRecetaTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarIngredienteAgregado(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarIngredienteAgregado(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = ingredientesAgregadosProductoRecetaTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                cantidadIngredienteProductotextFiel.setText(String.valueOf(recetas.get(pos).getCantidad()));
                int posIng = Controladora.getController().getIngredientes().getPosition(recetas.get(pos).getIdIngrediente());
                ingredientesProductoRecetaTableView.getSelectionModel().select(posIng);
                modificarIngredienteProductoRecetaButton.setDisable(false);
                eliminarIngredienteProductoRecetaButton.setDisable(false);
                adicionarIngredienteProductoRecetaButton.setDisable(true);
            }
        }
    }

    @FXML
    private TableColumn nombreIngredienteProductoRecetaColumn;

    @FXML
    private TableColumn cantidadIngredienteProductoRecetaColumn;

    @FXML
    private TextField cantidadIngredienteProductotextFiel;

    @FXML
    private Button adicionarIngredienteProductoRecetaButton;

    @FXML
    private Button eliminarProductoButton;

    @FXML
    private Button modificarProductoButton;

    @FXML
    private Button modificarIngredienteProductoRecetaButton;

    @FXML
    private Button eliminarIngredienteProductoRecetaButton;

    @FXML
    private TitledPane monedaTitledPane;

    @FXML
    private TextField monedaTextField;

    @FXML
    private Button adicionarMonedaButton;

    @FXML
    private Button modificarMonedaButton;

    @FXML
    private Button eliminarMonedaButton;

    @FXML
    private TableView<TipoMoneda> monedasTableView;

    private void configureTableMonedas() throws ClassNotFoundException, SQLException {
        this.monedaColumn.setCellValueFactory(new PropertyValueFactory<TipoMoneda, String>("tipo"));

        this.monedasTableView.setItems(Controladora.getController().obtenerTodosTipoMoneda());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.monedasTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);

        this.monedasTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarMoneda(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarMoneda(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = monedasTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                monedaTextField.setText(Controladora.getController().getTipoMoneda().getTiposMoneda().get(pos).getTipo());
                modificarMonedaButton.setDisable(false);
                eliminarMonedaButton.setDisable(false);

                limpiarPanelEstados();
                limpiarPanelIngredientesRango();
                limpiarPanelMedidas();
            }
        }
    }

    @FXML
    private TableColumn monedaColumn;

    @FXML
    private TitledPane medidasTitledPane;

    @FXML
    private TextField medidaTextField;

    @FXML
    private Button adicionarMedidaButton;

    @FXML
    private Button modificarMedidaButton;

    @FXML
    private Button eliminarMedidaButton;

    @FXML
    private TableView<UnidadMedida> medidasTableView;

    private void configureTableMedidas() throws ClassNotFoundException, SQLException {
        this.medidaColumn.setCellValueFactory(new PropertyValueFactory<UnidadMedida, String>("unidad_medida"));

        this.medidasTableView.setItems(Controladora.getController().obtenerTodosUnidadMedida());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.medidasTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);

        this.medidasTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarMedida(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarMedida(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = medidasTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                medidaTextField.setText(Controladora.getController().getUnidadMedida().getUnidadesMedida().get(pos).getUnidad_medida());
                modificarMedidaButton.setDisable(false);
                eliminarMedidaButton.setDisable(false);

                limpiarPanelConceptos();
                limpiarPanelEstados();
                limpiarPanelGastos();
                limpiarPanelGastosProductos();
                limpiarPanelIngredientesRango();
                limpiarPanelMonedas();
            }
        }
    }

    @FXML
    private TableColumn medidaColumn;

    @FXML
    private TitledPane conceptoPagoTitledPane;

    @FXML
    private TextField conceptoPagoTextField;

    @FXML
    private Button adicionarConceptoButton;

    @FXML
    private Button modificarConceptoButton;

    @FXML
    private Button eliminarConceptoButton;

    @FXML
    private TableView<ConceptoPago> conceptosPagoTableView;

    private void configureTableConceptos() throws ClassNotFoundException, SQLException {
        this.conceptoPagoColumn.setCellValueFactory(new PropertyValueFactory<ConceptoPago, String>("concepto"));

        this.conceptosPagoTableView.setItems(Controladora.getController().obtenerTodosConceptosPagos());

//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.conceptosPagoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.conceptosPagoTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarConcepto(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarConcepto(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = conceptosPagoTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                conceptoPagoTextField.setText(Controladora.getController().getConceptosPagos().getConceptosPago().get(pos).getConcepto());
                modificarConceptoButton.setDisable(false);
                eliminarConceptoButton.setDisable(false);

                limpiarPanelEstados();
                limpiarPanelIngredientesRango();
                limpiarPanelMedidas();
            }
        }
    }

    @FXML
    private TableColumn conceptoPagoColumn;

    @FXML
    private TitledPane estadosTitledPane;

    @FXML
    private TextField nombreEstadoTextField;

    @FXML
    private Button adicionarEstadosButton;

    @FXML
    private Button modificarEstadosButton;

    @FXML
    private Button eliminarEstadosButton;

    @FXML
    private TableView<EstadoPedido> estadosTableView;

    private void configureTableEstados() throws ClassNotFoundException, SQLException {
        this.estadoColumn.setCellValueFactory(new PropertyValueFactory<EstadoPedido, String>("estado"));

        this.estadosTableView.setItems(Controladora.getController().obtenerTodosEstadosPedidos());

//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.estadosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);

        this.estadosTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarEstado(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarEstado(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = estadosTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                nombreEstadoTextField.setText(Controladora.getController().getEstados().getEstadoPedidos().get(pos).getEstado());
                modificarEstadosButton.setDisable(false);
                eliminarEstadosButton.setDisable(false);

                limpiarPanelConceptos();
                limpiarPanelGastos();
                limpiarPanelGastos();
                limpiarPanelGastosProductos();
                limpiarPanelIngredientesRango();
                limpiarPanelMedidas();
                limpiarPanelMonedas();
            }
        }
    }

    @FXML
    private TableColumn estadoColumn;

    @FXML
    private TitledPane gastosAuxiliaresTitledPane;

    @FXML
    private TextField salarioTextField;

    @FXML
    private Button adicionarGastoButton;

    @FXML
    private Button modificarGastoButton;

    @FXML
    private Button eliminaGastoButton;

    @FXML
    private TableView<Salario> gastosTableView;

    private void configureTableSalario() throws ClassNotFoundException, SQLException {
        this.conceptoPagoGastoColumn.setCellValueFactory(new PropertyValueFactory<Salario, String>("concepto_id"));
        this.monedaGastoColumn.setCellValueFactory(new PropertyValueFactory<Salario, String>("moneda_id"));
        this.salarioGastoColumn.setCellValueFactory(new PropertyValueFactory<Salario, Float>("monto"));

        this.gastosTableView.setItems(Controladora.getController().obtenerTodoSalarios());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.gastosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);

        this.gastosTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarSalario(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarSalario(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = gastosTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                salarioTextField.setText(String.valueOf(Controladora.getController().getSalarios().getSalarios().get(pos).getMonto()));
                modificarGastoButton.setDisable(false);
                eliminaGastoButton.setDisable(false);

                limpiarPanelEstados();
                limpiarPanelIngredientesRango();
                limpiarPanelMedidas();
            }
        }
    }

    @FXML
    private TableColumn conceptoPagoGastoColumn;

    @FXML
    private TableColumn monedaGastoColumn;

    @FXML
    private TableColumn salarioGastoColumn;

    @FXML
    private TitledPane productogastoAuxiliarTitledPane;

    @FXML
    private TableView<Producto> productosGastosTableView;

    private void configureTableProductosGastos() throws ClassNotFoundException, SQLException {
        this.nombreProductosGastosColumn.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));

        this.productosGastosTableView.setItems(Controladora.getController().obtenerTodosProductos());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.productosGastosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
    }

    @FXML
    private TableColumn nombreProductosGastosColumn;

    @FXML
    private Button eliminarGastoProductoButton;

    @FXML
    private Button modificarGastoProductoButton;

    @FXML
    private Button adicionarGastoProductoButton;

    @FXML
    private TableView<GastoAuxiliar> gastosAuxiliaresProductoTableView;

    private void configureTableProductosGastosAsignado() throws ClassNotFoundException, SQLException {
        this.nombreGastosAuxiliaresProductoColumn.setCellValueFactory(new PropertyValueFactory<GastoAuxiliar, String>("producto_id"));
        this.gastoAuxiliarProductoColumn.setCellValueFactory(new PropertyValueFactory<GastoAuxiliar, String>("salario_id"));

        this.gastosAuxiliaresProductoTableView.setItems(Controladora.getController().obtenerTodosGastos());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.gastosAuxiliaresProductoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.gastosAuxiliaresProductoTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarGastoProducto(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarGastoProducto(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = gastosAuxiliaresProductoTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                modificarGastoProductoButton.setDisable(false);
                eliminarGastoProductoButton.setDisable(false);

                limpiarPanelConceptos();
                limpiarPanelEstados();
                limpiarPanelIngredientesRango();
                limpiarPanelMedidas();
                limpiarPanelMonedas();
            }
        }
    }

    @FXML
    private TitledPane ingredientesRangoTitledPane;

    @FXML
    private TableView<Ingrediente> IngredientesRangoTableView;

    private void configureIngredientesRango() throws ClassNotFoundException, SQLException {
        this.nombreIngredienteRangoColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("nombre"));
        this.unidadMedidaIngredienteRangoColumn.setCellValueFactory(new PropertyValueFactory<Ingrediente, String>("um_id"));

        this.IngredientesRangoTableView.setItems(Controladora.getController().obtenerTodosIngredientes());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.IngredientesRangoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
        this.IngredientesRangoTableView.setOnMouseClicked((MouseEvent event) -> {
            try {
                seleccionarRangoIngrediente(event);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    void seleccionarRangoIngrediente(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 0) {
            int pos = IngredientesRangoTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                rangoCriticoTextField.setText(String.valueOf(Controladora.getController().getMinimos().getMininmos().get(pos).getCantidad()));
                modificarIngredienteRangoButton.setDisable(false);
                eliminarIngredienteRangoButton.setDisable(false);

                limpiarPanelConceptos();
                limpiarPanelEstados();
                limpiarPanelGastos();
                limpiarPanelGastosProductos();
                limpiarPanelMonedas();
                limpiarPanelMedidas();
            }
        }
    }

    @FXML
    private TableColumn nombreIngredienteRangoColumn;

    @FXML
    private TableColumn unidadMedidaIngredienteRangoColumn;

    @FXML
    private TextField rangoCriticoTextField;

    @FXML
    private Button eliminarIngredienteRangoButton;

    @FXML
    private Button modificarIngredienteRangoButton;

    @FXML
    private Button adicionarIngredienteRangoButton;

    @FXML
    private TableView<Minimo> ingredientesRangoInsertadosTableView;  //tbla que falta en la BD, falta la entidad y falta la controladora

    private void configureTableIngredientesRangoInsertados() throws ClassNotFoundException, SQLException {
        this.ingredienteInsertadoRangoColumn.setCellValueFactory(new PropertyValueFactory<Minimo, String>("ingrediente_id"));
        this.rangoInsertadoRangoColumn.setCellValueFactory(new PropertyValueFactory<Minimo, String>("cantidad"));

        this.ingredientesRangoInsertadosTableView.setItems(Controladora.getController().obtenerTodosMinimos());
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.ingredientesRangoInsertadosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
    }

    @FXML
    private TableColumn ingredienteInsertadoRangoColumn;

    @FXML
    private TableColumn rangoInsertadoRangoColumn;

    @FXML
    private TableColumn nombreGastosAuxiliaresProductoColumn;

    @FXML
    private TableColumn gastoAuxiliarProductoColumn;

    @FXML
    private Pane datosCantidadIngredientesRecetaPane;

    @FXML
    private TitledPane ganaciaTitledPane;

    @FXML
    private TableView<Ganancia> ganaciaTableView;

    private void configureTableGanacia(ObservableList<Ganancia> ventas) throws ClassNotFoundException, SQLException {
        this.fechaInicioColumn.setCellValueFactory(new PropertyValueFactory<Ganancia, String>("fechaInicio"));
        this.fechaFinColumn.setCellValueFactory(new PropertyValueFactory<Ganancia, String>("fechaFin"));
        this.costoReporteColumn.setCellValueFactory(new PropertyValueFactory<Ganancia, Float>("costoProd"));
        this.ganaciaReporteColumn.setCellValueFactory(new PropertyValueFactory<Ganancia, Float>("ganancia"));
        this.ventaReporteColumn.setCellValueFactory(new PropertyValueFactory<Ganancia, Float>("venta"));

        this.ganaciaTableView.setItems(ventas);
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.ganaciaTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
    }

    @FXML
    private TableColumn fechaInicioColumn;

    @FXML
    private TableColumn fechaFinColumn;

    @FXML
    private TableColumn costoReporteColumn;

    @FXML
    private TableColumn ganaciaReporteColumn;

    @FXML
    private TableColumn ventaReporteColumn;

    @FXML
    private TableView<Venta> productosReporteTableView;

    private void configureProductosReporteTableView(ObservableList<Venta> ventas) throws ClassNotFoundException, SQLException {
        this.productoReporteColumn.setCellValueFactory(new PropertyValueFactory<Venta, String>("producto"));
        this.fechaInicioProductoReporteColumn.setCellValueFactory(new PropertyValueFactory<Venta, String>("fechaInicio"));
        this.fechaFinProductoReporteColumn.setCellValueFactory(new PropertyValueFactory<Venta, String>("fechaFin"));
        this.costoProductoReporteColumn.setCellValueFactory(new PropertyValueFactory<Venta, Float>("costoProd"));
        this.ganaciaProductoReporteColumn.setCellValueFactory(new PropertyValueFactory<Venta, Float>("ganancia"));
        this.ventaProductoReporteColumn.setCellValueFactory(new PropertyValueFactory<Venta, Float>("venta"));
        this.cantidadProductoReporteColumn.setCellValueFactory(new PropertyValueFactory<Venta, Integer>("cantidad"));

        this.productosReporteTableView.setItems(ventas);
//        assert this.tvTablaDem.getItems() == listDemandsNodes;
        this.productosReporteTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tvTablaDem.setPrefWidth(628);
//        tvTablaDemCaract1.setVisible(false);
    }

    @FXML
    private TableColumn productoReporteColumn;

    @FXML
    private TableColumn fechaInicioProductoReporteColumn;

    @FXML
    private TableColumn fechaFinProductoReporteColumn;

    @FXML
    private TableColumn costoProductoReporteColumn;

    @FXML
    private TableColumn ganaciaProductoReporteColumn;

    @FXML
    private TableColumn ventaProductoReporteColumn;

    @FXML
    private TableColumn cantidadProductoReporteColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            configureTableClientes();
            configureTableConceptos();
            configureTableEstados();

            configureTableIngredientesRangoInsertados();
            configureIngredientesRango();
            configureTableIngredientes();

            configureTableIngredientesProductoReceta();
            configureTableMedidas();
            configureTableMonedas();

            configureTableProductosGastos();
            configureTableProductosGastosAsignado();
            configureTableProductos();
            configureTableProductosPedidos();
            configureTablePedidos(-1);

            configureTableSalario();
            configuracionTipoMonedaIngredienteComboBox();
            configuracionUnidadIngredienteComboBox();
            configuracionMonedaProductoPedidoComboBox();
            configuracionEstadoProductoPedidoComboBox();
            configuracionCiclosReportesComboBox();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AplicacionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void adicionarCliente(ActionEvent event) throws ClassNotFoundException, SQLException {
        String nombre = nombreClienteTextField.getText();
        if (!nombre.isEmpty()) {
            if (Carteles.insersion("Cliente").get() == ButtonType.OK) {
                int pos = Controladora.getController().insertarCliente(nombre);
                if (pos == -1) {
                    nombreClienteTextField.clear();
                    clientesTableView.getSelectionModel().clearSelection();
                    eliminarClienteButton13.setDisable(true);
                    modificarClienteButton.setDisable(true);
                } else {
                    clientesTableView.getSelectionModel().select(pos);
                }
            }
        } else {
            nombreClienteTextField.requestFocus();
        }
    }

    @FXML
    void adicionarConceptoPago(ActionEvent event) throws ClassNotFoundException, SQLException {
        String nombre = conceptoPagoTextField.getText();
        if (!nombre.isEmpty()) {
            if (Carteles.insersion("Concepto de pago").get() == ButtonType.OK) {
                Controladora.getController().insertConceptoPago(nombre);
                limpiarPanelConceptos();
                configuracionEstadoProductoPedidoComboBox();
            }
        } else {
            conceptoPagoTextField.requestFocus();
        }
    }

    @FXML
    void adicionarEstado(ActionEvent event) throws ClassNotFoundException, SQLException {
        String nombre = nombreEstadoTextField.getText();
        if (!nombre.isEmpty()) {
            if (Carteles.insersion("Estado").get() == ButtonType.OK) {
                if (Controladora.getController().insertarEstado(nombre) != -1) {
                    limpiarPanelEstados();
                    configuracionEstadoProductoPedidoComboBox();
                } else {
                    estadosTableView.requestFocus();
                }
            }
        } else {
            nombreEstadoTextField.requestFocus();
        }
    }

    @FXML
    void adicionarGasto(ActionEvent event) throws ClassNotFoundException, SQLException {
        String salario = salarioTextField.getText();
        if (!salario.isEmpty()) {
            int posConcepto = conceptosPagoTableView.getSelectionModel().getSelectedIndex();
            if (posConcepto != -1) {
                int posmedida = monedasTableView.getSelectionModel().getSelectedIndex();
                if (posmedida != -1) {
                    if (Carteles.insersion("Gasto auxiliar").get() == ButtonType.OK) {
                        float salarioGasto = Float.valueOf(salario);
                        int pos = Controladora.getController().getSalarios().insertSalario(Controladora.getController().getConceptosPagos().getConceptosPago().get(posConcepto).getId(),
                                Controladora.getController().getTipoMoneda().getTiposMoneda().get(posmedida).getId(),
                                salarioGasto);
                        limpiarPanelConceptos();
                        limpiarPanelMonedas();
                        limpiarPanelGastos();
                        if (pos == -1) {
                            salarioTextField.requestFocus();
                            monedasTableView.requestFocus();
                            conceptosPagoTableView.requestFocus();
                        }
                    }
                } else {
                    monedasTableView.requestFocus();
                }
            } else {
                conceptosPagoTableView.requestFocus();
            }
        } else {
            salarioTextField.requestFocus();
        }
    }

    @FXML
    void adicionarGastoProducto(ActionEvent event) throws ClassNotFoundException, SQLException {
        int posGasto = gastosTableView.getSelectionModel().getSelectedIndex();
        if (posGasto != -1) {
            int posProducto = productosGastosTableView.getSelectionModel().getSelectedIndex();
            if (posProducto != -1) {
                if (Carteles.insersion("Asignación de gasto a producto").get() == ButtonType.OK) {
                    Controladora.getController().getGastos().insertGasto(Controladora.getController().getProdCTL().getProductos().get(posProducto).getId(),
                            Controladora.getController().getSalarios().getSalarios().get(posGasto).getId());
                    limpiarPanelGastosProductos();
                    
                    configureTableProductosPedidos();
                }
            } else {
                productosGastosTableView.requestFocus();
            }
        } else {
            gastosTableView.requestFocus();
        }
    }

    @FXML
    void adicionarIngrediente(ActionEvent event) throws ClassNotFoundException, SQLException {
        String nombre = nombreIngredienteTextField.getText();
        if (!nombre.isEmpty()) {
            int posUnidad = unidadIngredienteComboBox.getSelectionModel().getSelectedIndex();
            if (posUnidad != -1) {
                String costo = costoIngredienteTextField.getText();
                if (!costo.isEmpty()) {
                    int tipoMoneda = tipoMonedaIngredienteComboBox.getSelectionModel().getSelectedIndex();
                    if (tipoMoneda != -1) {
                        String cantidad = cantidadIngredienteTextField.getText();
                        if (!cantidad.isEmpty()) {
                            if (Carteles.insersion("Ingrediente").get() == ButtonType.OK) {
                                float costoIng = Float.valueOf(costo);
                                float cantidadIng = Float.valueOf(cantidad);
                                int pos = Controladora.getController().insertarIngrediente(nombre, posUnidad, cantidadIng, costoIng, tipoMoneda);
                                if (pos == -1) {
                                    nombreIngredienteTextField.clear();
                                    unidadIngredienteComboBox.getSelectionModel().clearSelection();
                                    costoIngredienteTextField.clear();
                                    tipoMonedaIngredienteComboBox.getSelectionModel().clearSelection();
                                    cantidadIngredienteTextField.clear();
                                    modificarIngredienteButton.setDisable(true);
                                    eliminarIngredienteButton.setDisable(true);
                                } else {
                                    ingredientesTableViwe.getSelectionModel().select(pos);
                                }
                            }
                        } else {
                            cantidadIngredienteProductotextFiel.requestFocus();
                        }

                    } else {
                        tipoMonedaIngredienteComboBox.requestFocus();
                    }
                } else {
                    costoIngredienteTextField.requestFocus();
                }
            } else {
                unidadIngredienteComboBox.requestFocus();
            }
        } else {
            nombreIngredienteTextField.requestFocus();
        }
    }

    @FXML
    void adicionarIngredienteRango(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = IngredientesRangoTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            String rango = rangoCriticoTextField.getText();
            if (!rango.isEmpty()) {
                if (Carteles.insersion("Rango crítico de ingrediente").get() == ButtonType.OK) {
                    float rango1 = Float.valueOf(rango);
                    int idIng = Controladora.getController().getIngredientes().getIngredientes().get(pos).getId();
                    Controladora.getController().insertarRangoCritico(idIng, rango1);
                    limpiarPanelIngredientesRango();
                }
            } else {
                rangoCriticoTextField.requestFocus();
            }
        } else {
            IngredientesRangoTableView.requestFocus();
        }
    }

    @FXML
    void adicionarIngredienteReceta(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = ingredientesProductoRecetaTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            String cant = cantidadIngredienteProductotextFiel.getText();
            if (!cant.isEmpty()) {
                if (!Controladora.getController().insertarIngredientereceta(cant, pos, recetas)) {
                    ingredientesAgregadosProductoRecetaTableView.getSelectionModel().clearSelection();
                    adicionarIngredienteProductoRecetaButton.setDisable(true);
                    modificarIngredienteProductoRecetaButton.setDisable(true);
                    eliminarIngredienteProductoRecetaButton.setDisable(true);
                    cantidadIngredienteProductotextFiel.clear();
                    ingredientesProductoRecetaTableView.getSelectionModel().clearSelection();
                    configureTableIngredientesAgregadosProductoReceta(-1);
                } else {
                    ingredientesProductoRecetaTableView.requestFocus();
                }
            } else {
                cantidadIngredienteProductotextFiel.requestFocus();
            }
        } else {
            ingredientesProductoRecetaTableView.requestFocus();
        }
    }

    @FXML
    void adicionarMedida(ActionEvent event) throws ClassNotFoundException, SQLException {
        String medida = medidaTextField.getText();
        if (!medida.isEmpty()) {
            if (Carteles.insersion("Unidad de medida").get() == ButtonType.OK) {
                Controladora.getController().getUnidadMedida().insertUM(medida);
                configuracionUnidadIngredienteComboBox();
                limpiarPanelMedidas();
            }
        } else {
            medidaTextField.requestFocus();
        }
    }

    @FXML
    void adicionarMonedas(ActionEvent event) throws ClassNotFoundException, SQLException {
        String moneda = monedaTextField.getText();
        if (!moneda.isEmpty()) {
            if (Carteles.insersion("Tipo de moneda").get() == ButtonType.OK) {
                Controladora.getController().insertTipoMoneda(moneda);
                configuracionMonedaProductoPedidoComboBox();
                configuracionTipoMonedaIngredienteComboBox();
                limpiarPanelMonedas();
            }
        } else {
            monedaTextField.requestFocus();
        }
    }

    @FXML
    void adicionarPedido(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (!productosPedidos.isEmpty()) {
            String fecha = fechaProductoPedidoDatePicker.getEditor().getText();
            if (!fecha.isEmpty()) {
                LocalDate fechaActual = LocalDate.now();
                LocalDate fechaA = fechaProductoPedidoDatePicker.getValue();
                if (fechaActual.isAfter(fechaA)) {
                    int estado = estadoProductoPedidoComboBox.getSelectionModel().getSelectedIndex();
                    if (estado != -1) {
                        int posCliente = clientesTableView.getSelectionModel().getSelectedIndex();
                        if (posCliente != -1) {
                            if (Carteles.insersion("Pedido").get() == ButtonType.OK) {
                                fecha = fechaProductoPedidoDatePicker.getValue().toString();
                                int idCliente = Controladora.getController().getClientes().getClientes().get(posCliente).getId();
                                int idEstado = Controladora.getController().getEstados().getEstadoPedidos().get(estado).getId();
                                Controladora.getController().insertarPedido(idCliente, fecha, idEstado, productosPedidos);
                                configureTablePedidos(idCliente);
                                estadoProductoPedidoComboBox.getSelectionModel().clearSelection();
                                fechaProductoPedidoDatePicker.getEditor().clear();
                                productosPedidos.clear();
                                pedidosTableView.getSelectionModel().clearSelection();
                                eliminarPedidoButton.setDisable(true);
                                modificarPedidoButton.setDisable(true);

                            }
                        } else {
                            clientesTableView.requestFocus();
                        }
                    } else {
                        estadoProductoPedidoComboBox.requestFocus();
                    }
                } else {
                    Carteles.fechaIncorrectaPedido();
                    fechaProductoPedidoDatePicker.requestFocus();
                }
            } else {
                fechaProductoPedidoDatePicker.requestFocus();
            }
        } else {
            pedidosProductosTableView.requestFocus();
        }
    }

    @FXML
    void adicionarProductoPedido(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = productosPedidosTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            String cantidad = cantidadProductoPedidoTextField.getText();
            if (!cantidad.isEmpty() && !cantidad.equals("0") && !cantidad.contains(".")) {
                String precio = precioProductoPedidoTextField.getText();
                if (!precio.isEmpty()) {
                    int tipoMoneda = monedaProductoPedidoComboBox.getSelectionModel().getSelectedIndex();
                    if (tipoMoneda != -1) {
                        int cantidad1 = Integer.valueOf(cantidad);
                        PedidoProducto ped = new PedidoProducto();
                        ped.setProducto_id(Controladora.getController().getProdCTL().getProductos().get(pos).getId());
                        ped.setCantidad(cantidad1);
                        ped.setMoneda_id(Controladora.getController().getTipoMoneda().getTiposMoneda().get(tipoMoneda).getId());
                        ped.setPrecio(Float.valueOf(precio));
                        ped.setCostoUnitario(Controladora.getController().getCostoProduccionByProd(Controladora.getController().getProducto().getProductos().get(pos).getId()));
                        Controladora.getController().insertarProductoPedidoInterfaz(productosPedidos, ped);
                        Controladora.getController().obtenerGaranciaProductoPedido(ped);
                        cantidadProductoPedidoTextField.clear();
                        monedaProductoPedidoComboBox.getSelectionModel().clearSelection();
                        precioProductoPedidoTextField.clear();
                        cantidadProductoPedidoTextField.clear();
                        productosPedidosTableView.getSelectionModel().clearSelection();
                        pedidosProductosTableView.getSelectionModel().clearSelection();
                        eliminarProductoPedidoButton.setDisable(true);
                        modificarProductoPedidoButton.setDisable(true);
                        configureTablePedidosProductos(productosPedidos);

                        if (ped.getCantidad() > Controladora.getController().cantProductosDisponiblesConstruir(pos)) {
                            estadoProductoPedidoComboBox.setDisable(true);
                            estadoProductoPedidoComboBox.getSelectionModel().select("Almacén Insuficiente");
                            Carteles.alertaCantidadAlmacenInsuficiente("Productos");
                        }

                        boolean found = false;
                        for (int i = 0; i < productosPedidos.size() && !found; i++) {
                            if (productosPedidos.get(i).getCantidad() > Controladora.getController().cantProductosDisponiblesConstruir(Controladora.getController().getProdCTL().getPosition(productosPedidos.get(i).getidProducto()))) {
                                found = true;
                            }
                        }
                        if (!found) {
                            estadoProductoPedidoComboBox.setDisable(false);
                            estadoProductoPedidoComboBox.getSelectionModel().clearSelection();
                        }

                    } else {
                        monedaProductoPedidoComboBox.requestFocus();
                    }
                } else {
                    precioProductoPedidoTextField.requestFocus();
                }
            } else {
                cantidadProductoPedidoTextField.requestFocus();
            }
        } else {
            productosPedidosTableView.requestFocus();
        }
    }

    @FXML
    void crearReceta(ActionEvent event) throws ClassNotFoundException, SQLException {
        String nombre = nombreProductoTextField.getText();
        if (!nombre.isEmpty()) {
            if (productosTableView.getSelectionModel().getSelectedIndex() != -1) {
                productosTableView.getSelectionModel().clearSelection();
                modificarProductoButton.setDisable(true);
                eliminarProductoButton.setDisable(true);
            } else {
                recetas.clear();
            }
            seleccionIngredientesProductoTitledPane.setExpanded(true);
            ingredientesAgregadosProductoRecetaTitledPane.setExpanded(true);
            seleccionIngredientesProductoTitledPane.setDisable(false);
            ingredientesAgregadosProductoRecetaTitledPane.setDisable(false);
            datosCantidadIngredientesRecetaPane.setVisible(true);
            guardarCambiosRecetaButton.setDisable(false);
        } else {
            nombreProductoTextField.requestFocus();
        }
    }

    @FXML
    void eliminarCliente(ActionEvent event) throws ClassNotFoundException, SQLException {
        int posCl = clientesTableView.getSelectionModel().getSelectedIndex();
        if (posCl != -1) {
            if (Carteles.eliminacion("Clientes").get() == ButtonType.OK) {
                Controladora.getController().eliminarCliente(posCl);
                clientesTableView.getSelectionModel().clearSelection();
                nombreClienteTextField.clear();
                modificarClienteButton.setDisable(true);
                eliminarClienteButton13.setDisable(true);
                configureTablePedidos(-1);
            }
        } else {
            clientesTableView.requestFocus();
        }
    }

    @FXML
    void eliminarConceptoPago(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = conceptosPagoTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Carteles.eliminacion("Concepto de pago").get() == ButtonType.OK) {
                Controladora.getController().deleteConceptoPago(pos);
                limpiarPanelConceptos();
            }
        } else {
            conceptosPagoTableView.requestFocus();
        }
    }

    @FXML
    void eliminarEstado(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = estadosTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Controladora.getController().getEstados().getEstadoPedidos().get(pos).getEstado().equals("Cerrado") || Controladora.getController().getEstados().getEstadoPedidos().get(pos).getEstado().equals("Almacén Insuficiente")) {
                Carteles.eliminacionNoPermitida(Controladora.getController().getEstados().getEstadoPedidos().get(pos).getEstado());
            } else if (Carteles.eliminacion("Estados").get() == ButtonType.OK) {
                Controladora.getController().deleteEstado(pos);
                limpiarPanelEstados();
                configuracionEstadoProductoPedidoComboBox();
            }
        } else {
            estadosTableView.requestFocus();
        }
    }

    @FXML
    void eliminarGastoButton(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = gastosTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Carteles.eliminacion("Gasto auxiliar").get() == ButtonType.OK) {
                Controladora.getController().eliminarSalario(pos);
                limpiarPanelMonedas();
                limpiarPanelConceptos();
                limpiarPanelGastos();
            }
        } else {
            gastosTableView.requestFocus();
        }
    }

    @FXML
    void eliminarGastoProducto(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = gastosAuxiliaresProductoTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Carteles.eliminacion("Asignación de gasto a producto").get() == ButtonType.OK) {
                Controladora.getController().eliminarGastoAuxiliar(pos);
                limpiarPanelGastosProductos();
                configureTableProductosPedidos();
            }
        } else {
            gastosAuxiliaresProductoTableView.requestFocus();
        }
    }

    @FXML
    void eliminarIngrediente(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = ingredientesTableViwe.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Carteles.eliminacion("Ingrediente").get() == ButtonType.OK) {
                Controladora.getController().eliminarIngredientes(pos);
                ingredientesTableViwe.getSelectionModel().clearSelection();
                modificarIngredienteButton.setDisable(true);
                eliminarIngredienteButton.setDisable(true);
                nombreIngredienteTextField.clear();
                unidadIngredienteComboBox.getSelectionModel().clearSelection();
                costoIngredienteTextField.clear();
                tipoMonedaIngredienteComboBox.getSelectionModel().clearSelection();
                cantidadIngredienteTextField.clear();
            }
        } else {
            almacenTitledView.requestFocus();
        }
    }

    @FXML
    void eliminarIngredienteRango(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = IngredientesRangoTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Carteles.eliminacion("Rango crítico de ingrediente").get() == ButtonType.OK) {
                Controladora.getController().eliminarIngredienteRango(pos);
                limpiarPanelIngredientesRango();
            }
        } else {
            IngredientesRangoTableView.requestFocus();
        }
    }

    @FXML
    void eliminarIngredienteReceta(ActionEvent event) {
        int pos = ingredientesAgregadosProductoRecetaTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            recetas.remove(pos);
            ingredientesAgregadosProductoRecetaTableView.getSelectionModel().clearSelection();
            adicionarIngredienteProductoRecetaButton.setDisable(true);
            modificarIngredienteProductoRecetaButton.setDisable(true);
            eliminarIngredienteProductoRecetaButton.setDisable(true);
            cantidadIngredienteProductotextFiel.clear();
            ingredientesProductoRecetaTableView.getSelectionModel().clearSelection();
        } else {
            ingredientesAgregadosProductoRecetaTableView.requestFocus();
        }
    }

    @FXML
    void eliminarMedida(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = medidasTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Carteles.eliminacion("Unidad de medida").get() == ButtonType.OK) {
                Controladora.getController().deleteUM(pos);
                configuracionUnidadIngredienteComboBox();
                limpiarPanelMedidas();
            }
        } else {
            medidasTableView.requestFocus();
        }
    }

    @FXML
    void eliminarMonedas(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = monedasTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (!Controladora.getController().getTipoMoneda().getTiposMoneda().get(pos).getTipo().equals("CUC")
                    && !Controladora.getController().getTipoMoneda().getTiposMoneda().get(pos).getTipo().equals("CUP")) {
                if (Carteles.eliminacion("Tipo de moneda").get() == ButtonType.OK) {
                    Controladora.getController().deleteTipoMoneda(Controladora.getController().
                            getTipoMoneda().getTiposMoneda().get(pos).getId());
                    configuracionMonedaProductoPedidoComboBox();
                    configuracionTipoMonedaIngredienteComboBox();
                    limpiarPanelMonedas();
                }
            } else {
                Carteles.eliminacionNoPermitidaMoneda(Controladora.getController().getTipoMoneda().getTiposMoneda().get(pos).getTipo());
                monedasTableView.requestFocus();
            }
        } else {
            monedasTableView.requestFocus();
        }
    }

    @FXML
    void eliminarPedido(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = pedidosTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Carteles.eliminacion("Pedido").get() == ButtonType.OK) {
                int id = Controladora.getController().obtenerPedidosByCliente(Controladora.getController().getClientes().getClientes().get(clientesTableView.getSelectionModel().getSelectedIndex()).getId()).get(pos).getId();
                Controladora.getController().eliminarPedido(id);
                configureTablePedidos(Controladora.getController().getClientes().getClientes().get(clientesTableView.getSelectionModel().getSelectedIndex()).getId());
                pedidosTableView.getSelectionModel().clearSelection();
                estadoProductoPedidoComboBox.getSelectionModel().clearSelection();
                fechaProductoPedidoDatePicker.getEditor().clear();
                productosPedidos.clear();
                eliminarPedidoButton.setDisable(true);
                modificarPedidoButton.setDisable(true);
            }
        } else {
            pedidosTableView.requestFocus();
        }
    }

    @FXML
    void eliminarProducto(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = productosTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            if (Carteles.eliminacion("Producto").get() == ButtonType.OK) {
                Controladora.getController().deleteProducto(Controladora.getController().getProdCTL().getProductos().get(pos).getId());
                recetasTableView.getItems().clear();
                productosTableView.getSelectionModel().clearSelection();
                recetas.clear();
                nombreProductoTextField.clear();
                modificarProductoButton.setDisable(true);
                eliminarProductoButton.setDisable(true);
                seleccionIngredientesProductoTitledPane.setExpanded(false);
                ingredientesAgregadosProductoRecetaTitledPane.setExpanded(false);
                seleccionIngredientesProductoTitledPane.setDisable(true);
                ingredientesAgregadosProductoRecetaTitledPane.setDisable(true);
                datosCantidadIngredientesRecetaPane.setVisible(false);
                cantidadIngredienteProductotextFiel.clear();
            }
        } else {
            productosTableView.requestFocus();
        }
    }

    @FXML
    void eliminarProductoPedido(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = pedidosProductosTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            Controladora.getController().eliminarProductoPedidoInterfaz(productosPedidos, productosPedidos.get(pos));
            pedidosProductosTableView.getSelectionModel().clearSelection();
            eliminarProductoPedidoButton.setDisable(true);
            modificarProductoPedidoButton.setDisable(true);
            cantidadProductoPedidoTextField.clear();
            precioProductoPedidoTextField.clear();
            monedaProductoPedidoComboBox.getSelectionModel().clearSelection();
            configureTablePedidosProductos(productosPedidos);
            boolean found = false;
            for (int i = 0; i < productosPedidos.size() && !found; i++) {
                if (productosPedidos.get(i).getCantidad() > Controladora.getController().cantProductosDisponiblesConstruir(Controladora.getController().getProdCTL().getPosition(productosPedidos.get(i).getidProducto()))) {
                    estadoProductoPedidoComboBox.setDisable(true);
                    estadoProductoPedidoComboBox.getSelectionModel().select("Almacén Insuficiente");
                    found = true;
                }
            }
            if (!found) {
                estadoProductoPedidoComboBox.setDisable(false);
                estadoProductoPedidoComboBox.getSelectionModel().clearSelection();
            }
        } else {
            pedidosProductosTableView.requestFocus();
        }
    }

    @FXML
    void modificarCliente(ActionEvent event) throws ClassNotFoundException, SQLException {
        String nombre = nombreClienteTextField.getText();
        if (!nombre.isEmpty()) {
            int pos = clientesTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                if (Carteles.modificar("Cliente").get() == ButtonType.OK) {
                    pos = Controladora.getController().modificarCliente(nombre, pos);
                    if (pos == -1) {
                        nombreClienteTextField.clear();
                        clientesTableView.getSelectionModel().clearSelection();
                        modificarClienteButton.setDisable(true);
                        eliminarClienteButton13.setDisable(true);
                    } else {
                        clientesTableView.getSelectionModel().select(pos);
                    }
                }
            } else {
                clientesTableView.requestFocus();
            }
        } else {
            nombreClienteTextField.requestFocus();
        }
    }

    @FXML
    void modificarConceptoPago(ActionEvent event) throws ClassNotFoundException, SQLException {
        String concepto = conceptoPagoTextField.getText();
        if (!concepto.isEmpty()) {
            int pos = conceptosPagoTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                if (Carteles.modificar("Concepto de pago").get() == ButtonType.OK) {
                    Controladora.getController().getConceptosPagos().
                            updateConceptoPago(Controladora.getController().getConceptosPagos().getConceptosPago().
                                    get(pos).getId(),
                                    concepto);
                    limpiarPanelConceptos();
                }
            } else {
                conceptosPagoTableView.requestFocus();
            }
        } else {
            conceptoPagoTextField.requestFocus();
        }
    }

    @FXML
    void modificarEstado(ActionEvent event) throws ClassNotFoundException, SQLException {
        String nombre = nombreEstadoTextField.getText();
        if (!nombre.isEmpty()) {
            int pos = estadosTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                if (Controladora.getController().getEstados().getEstadoPedidos().get(pos).getEstado().equals("Cerrado") || Controladora.getController().getEstados().getEstadoPedidos().get(pos).getEstado().equals("Almacén Insuficiente")) {
                    Carteles.modificacionNoPermitida(Controladora.getController().getEstados().getEstadoPedidos().get(pos).getEstado());
                } else if (Carteles.modificar("Estado").get() == ButtonType.OK) {
                    Controladora.getController().getEstados().
                            updateEstadoPedido(Controladora.getController().getEstados().getEstadoPedidos().
                                    get(pos).getId(),
                                    nombre);
                    limpiarPanelEstados();
                    configuracionEstadoProductoPedidoComboBox();
                }
            } else {
                estadosTableView.requestFocus();
            }
        } else {
            nombreEstadoTextField.requestFocus();
        }
    }

    @FXML
    void modificarGasto(ActionEvent event) throws ClassNotFoundException, SQLException {
        int posSal = gastosTableView.getSelectionModel().getSelectedIndex();
        if (posSal != -1) {
            String salario = salarioTextField.getText();
            if (!salario.isEmpty()) {
                int posConcepto = conceptosPagoTableView.getSelectionModel().getSelectedIndex();
                if (posConcepto != -1) {
                    int posmedida = monedasTableView.getSelectionModel().getSelectedIndex();
                    if (posmedida != -1) {
                        if (Carteles.modificar("Gasto auxiliar").get() == ButtonType.OK) {
                            float salarioGasto = Float.valueOf(salario);
                            int pos = Controladora.getController().getSalarios().
                                    updateSalario(Controladora.getController().getSalarios().getSalarios().
                                            get(posSal).getId(), Controladora.getController().getConceptosPagos().getConceptosPago().get(posConcepto).getId(),
                                            Controladora.getController().getTipoMoneda().getTiposMoneda().get(posmedida).getId(),
                                            salarioGasto);
                            limpiarPanelConceptos();
                            limpiarPanelMonedas();
                            limpiarPanelGastos();
                            if (pos == -1) {
                                monedasTableView.requestFocus();
                                conceptosPagoTableView.requestFocus();
                                salarioTextField.requestFocus();
                                gastosTableView.requestFocus();
                            }
                        }
                    } else {
                        monedasTableView.requestFocus();
                    }
                } else {
                    conceptosPagoTableView.requestFocus();
                }
            } else {
                salarioTextField.requestFocus();
            }
        } else {
            gastosTableView.requestFocus();
        }
    }

    @FXML
    void modificarGastoProducto(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = gastosAuxiliaresProductoTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            int posgas = productosGastosTableView.getSelectionModel().getSelectedIndex();
            if (posgas != -1) {
                int possal = gastosTableView.getSelectionModel().getSelectedIndex();
                if (possal != -1) {
                    if (Carteles.modificar("Asignación de gasto a producto").get() == ButtonType.OK) {
                        Controladora.getController().getGastos().updateGasto(Controladora.getController().getGastos().getGastosAuxiliares().get(pos).getId(),
                                Controladora.getController().getProdCTL().getProductos().get(posgas).getId(),
                                Controladora.getController().getSalarios().getSalarios().get(possal).getId());
                        limpiarPanelGastosProductos();
                        configureTableProductosPedidos();
                    }
                } else {
                    gastosTableView.getSelectionModel().clearSelection();
                }
            } else {
                productosGastosTableView.requestFocus();
            }
        } else {
            gastosAuxiliaresProductoTableView.requestFocus();
        }
    }

    @FXML
    void modificarIngrediente(ActionEvent event) throws ClassNotFoundException, SQLException {
        String nombre = nombreIngredienteTextField.getText();
        if (!nombre.isEmpty()) {
            int posUnidad = unidadIngredienteComboBox.getSelectionModel().getSelectedIndex();
            if (posUnidad != -1) {
                String costo = costoIngredienteTextField.getText();
                if (!costo.isEmpty()) {
                    int tipoMoneda = tipoMonedaIngredienteComboBox.getSelectionModel().getSelectedIndex();
                    if (tipoMoneda != -1) {
                        String cantidad = cantidadIngredienteTextField.getText();
                        if (!cantidad.isEmpty()) {
                            int posTable = ingredientesTableViwe.getSelectionModel().getSelectedIndex();
                            if (posTable != -1) {
                                if (Carteles.modificar("Ingrediente").get() == ButtonType.OK) {
                                    float costoIng = Float.valueOf(costo);
                                    float cantidadIng = Float.valueOf(cantidad);
                                    Controladora.getController().modificarIngrediente(posTable, nombre, posUnidad, cantidadIng, costoIng, tipoMoneda);
                                    nombreIngredienteTextField.clear();
                                    unidadIngredienteComboBox.getSelectionModel().clearSelection();
                                    costoIngredienteTextField.clear();
                                    cantidadIngredienteTextField.clear();
                                    tipoMonedaIngredienteComboBox.getSelectionModel().clearSelection();
                                    cantidadIngredienteProductotextFiel.clear();
                                    modificarIngredienteButton.setDisable(true);
                                    eliminarIngredienteButton.setDisable(true);
                                }
                            } else {
                                almacenTitledView.requestFocus();
                            }
                        } else {
                            cantidadIngredienteProductotextFiel.requestFocus();
                        }
                    } else {
                        tipoMonedaIngredienteComboBox.requestFocus();
                    }
                } else {
                    costoIngredienteTextField.requestFocus();
                }
            } else {
                unidadIngredienteComboBox.requestFocus();
            }
        } else {
            nombreIngredienteTextField.requestFocus();
        }
    }

    @FXML
    void modificarIngredienteRango(ActionEvent event) throws ClassNotFoundException, SQLException {
        String rango = rangoCriticoTextField.getText();
        if (!rango.isEmpty()) {
            int pos = IngredientesRangoTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                int posIng = ingredientesRangoInsertadosTableView.getSelectionModel().getSelectedIndex();
                if (posIng != -1) {
                    if (Carteles.modificar("Rango crítico de un ingrediente").get() == ButtonType.OK) {
                        float rango1 = Float.valueOf(rango);
                        Controladora.getController().getMinimos().updateMininmo(Controladora.getController().getMinimos().getMininmos().get(pos).getId(), Controladora.getController().getIngredientes().getIngredientes().get(posIng).getId(), rango1);
                        limpiarPanelIngredientesRango();
                    }
                } else {
                    ingredientesRangoInsertadosTableView.requestFocus();
                }
            } else {
                IngredientesRangoTableView.requestFocus();
            }
        } else {
            rangoCriticoTextField.requestFocus();
        }
    }

    @FXML
    void modificarIngredienteReceta(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = ingredientesAgregadosProductoRecetaTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            int posI = ingredientesProductoRecetaTableView.getSelectionModel().getSelectedIndex();
            if (posI != -1) {
                String cant = cantidadIngredienteProductotextFiel.getText();
                if (!cant.isEmpty()) {
                    if (!recetas.isEmpty()) {
                        if (!Controladora.getController().modificarIngredientereceta(cant, posI, pos, recetas)) {
                            configureTableIngredientesAgregadosProductoReceta(-1);
                            ingredientesAgregadosProductoRecetaTableView.refresh();
                            ingredientesAgregadosProductoRecetaTableView.getSelectionModel().clearSelection();
                            adicionarIngredienteProductoRecetaButton.setDisable(true);
                            modificarIngredienteProductoRecetaButton.setDisable(true);
                            eliminarIngredienteProductoRecetaButton.setDisable(true);
                            cantidadIngredienteProductotextFiel.clear();
                            ingredientesProductoRecetaTableView.getSelectionModel().clearSelection();
                        } else {
                            ingredientesProductoRecetaTableView.requestFocus();
                        }
                    } else {
                        ingredientesAgregadosProductoRecetaTableView.requestFocus();
                    }
                } else {
                    cantidadIngredienteProductotextFiel.requestFocus();
                }
            } else {
                ingredientesProductoRecetaTableView.requestFocus();
            }
        } else {
            ingredientesAgregadosProductoRecetaTableView.requestFocus();
        }
    }

    @FXML
    void modificarMedida(ActionEvent event) throws ClassNotFoundException, SQLException {
        String medida = medidaTextField.getText();
        if (!medida.isEmpty()) {
            int pos = medidasTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                if (Carteles.modificar("Unidad de medida").get() == ButtonType.OK) {
                    Controladora.getController().getUnidadMedida().
                            updateUM(Controladora.getController().getUnidadMedida().getUnidadesMedida().
                                    get(pos).getId(),
                                    medida);
                    configuracionUnidadIngredienteComboBox();

                    limpiarPanelMedidas();
                }
            } else {
                medidasTableView.requestFocus();
            }
        } else {
            medidaTextField.requestFocus();
        }
    }

    @FXML
    void modificarMonedas(ActionEvent event) throws ClassNotFoundException, SQLException {
        String moneda = monedaTextField.getText();
        if (!moneda.isEmpty()) {
            int pos = monedasTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                if (!Controladora.getController().getTipoMoneda().getTiposMoneda().get(pos).getTipo().equals("CUC")
                        && !Controladora.getController().getTipoMoneda().getTiposMoneda().get(pos).getTipo().equals("CUP")) {
                    if (Carteles.modificar("Tipo de moneda").get() == ButtonType.OK) {
                        Controladora.getController().getTipoMoneda().
                                updateTipoMoneda(Controladora.getController().getTipoMoneda().getTiposMoneda().
                                        get(pos).getId(),
                                        moneda);
                        configuracionMonedaProductoPedidoComboBox();
                        configuracionTipoMonedaIngredienteComboBox();
                        limpiarPanelMonedas();
                    }
                } else {
                    Carteles.modificarNoPermitidaPedidoMoneda(Controladora.getController().getTipoMoneda().getTiposMoneda().get(pos).getTipo());
                    monedasTableView.requestFocus();
                }
            } else {
                monedasTableView.requestFocus();
            }
        } else {
            monedaTextField.requestFocus();
        }
    }

    @FXML
    void modificarPedido(ActionEvent event) throws ClassNotFoundException, SQLException {
        int posPed = pedidosTableView.getSelectionModel().getSelectedIndex();
        if (posPed != -1) {
            if (!productosPedidos.isEmpty()) {
                String fecha = fechaProductoPedidoDatePicker.getEditor().getText();
                if (!fecha.isEmpty()) {
                    int estado = estadoProductoPedidoComboBox.getSelectionModel().getSelectedIndex();
                    if (estado != -1) {
                        int posCliente = clientesTableView.getSelectionModel().getSelectedIndex();
                        if (posCliente != -1) {
                            String idEstadoViejo = pedidosTableView.getItems().get(posPed).getEstado_id();
                            if (!idEstadoViejo.equals("Cerrado")) {
                                if (Carteles.modificar("Pedido").get() == ButtonType.OK) {
                                    int idCliente = Controladora.getController().getClientes().getClientes().get(posCliente).getId();
                                    int idEstado = Controladora.getController().getEstados().getEstadoPedidos().get(estado).getId();
                                    Controladora.getController().modificarPedido(Controladora.getController().getPedidosCTLR().getPedidosByCliente(idCliente).get(posPed).getId(), idCliente, fecha, idEstado, productosPedidos);
                                    eliminarPedidoButton.setDisable(true);
                                    modificarPedidoButton.setDisable(true);
                                    estadoProductoPedidoComboBox.getSelectionModel().clearSelection();
                                    fechaProductoPedidoDatePicker.getEditor().clear();
                                    productosPedidos.clear();
                                    configureTablePedidos(idCliente);
                                }
                            } else {
                                Carteles.modificarNoPermitidaPedido("Cerrado");
                                estadoProductoPedidoComboBox.requestFocus();
                            }
                        } else {
                            clientesTableView.requestFocus();
                        }
                    } else {
                        estadoProductoPedidoComboBox.requestFocus();
                    }
                } else {
                    fechaProductoPedidoDatePicker.requestFocus();
                }
            } else {
                pedidosProductosTableView.requestFocus();
            }
        } else {
            pedidosProductosTableView.requestFocus();
        }
    }

    @FXML
    void modificarProductoPedido(ActionEvent event) throws ClassNotFoundException, SQLException {
        int posPed = pedidosProductosTableView.getSelectionModel().getSelectedIndex();
        if (posPed != -1) {
            int pos = productosPedidosTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                String cantidad = cantidadProductoPedidoTextField.getText();
                if (!cantidad.isEmpty() && !cantidad.equals("0")) {
                    String precio = precioProductoPedidoTextField.getText();
                    if (!precio.isEmpty()) {
                        int tipoMoneda = monedaProductoPedidoComboBox.getSelectionModel().getSelectedIndex();
                        if (tipoMoneda != -1) {
                            int cantidad1 = Integer.valueOf(cantidad);
                            PedidoProducto ped = new PedidoProducto();
//                            ped.setId(Controladora.getController().getPedidoProductoCTLR().getPedidosProducto().get(posPed).getId());
                            ped.setProducto_id(Controladora.getController().getProdCTL().getProductos().get(pos).getId());
                            ped.setCantidad(cantidad1);
                            ped.setMoneda_id(Controladora.getController().getTipoMoneda().getTiposMoneda().get(tipoMoneda).getId());
                            ped.setPrecio(Float.valueOf(precio));
                            ped.setCostoUnitario(Controladora.getController().getCostoProduccionByProd(Controladora.getController().getProducto().getProductos().get(pos).getId()));

                            cantidadProductoPedidoTextField.clear();
                            monedaProductoPedidoComboBox.getSelectionModel().clearSelection();
                            precioProductoPedidoTextField.clear();
                            cantidadProductoPedidoTextField.clear();
                            productosPedidosTableView.getSelectionModel().clearSelection();
                            modificarProductoPedidoButton.setDisable(true);
                            eliminarProductoPedidoButton.setDisable(true);
                            configureTablePedidosProductos(productosPedidos);
                            if (cantidad1 - productosPedidos.get(posPed).getCantidad() > Controladora.getController().cantProductosDisponiblesConstruir(pos)) {
                                estadoProductoPedidoComboBox.setDisable(true);
                                estadoProductoPedidoComboBox.getSelectionModel().select("Almacén Insuficiente");
                                Carteles.alertaCantidadAlmacenInsuficiente("Productos");
                            }
                            boolean found = false;
                            for (int i = 0; i < productosPedidos.size() && !found; i++) {
                                if (cantidad1 - productosPedidos.get(posPed).getCantidad() > Controladora.getController().cantProductosDisponiblesConstruir(Controladora.getController().getProdCTL().getPosition(productosPedidos.get(i).getidProducto()))) {
                                    found = true;
                                }
                            }
                            if (!found) {
                                estadoProductoPedidoComboBox.setDisable(false);
                                estadoProductoPedidoComboBox.getSelectionModel().clearSelection();
                            }
                            Controladora.getController().modificarProductoPedidoInterfaz(productosPedidos, ped, posPed);
                            Controladora.getController().obtenerGaranciaProductoPedido(ped);
                        } else {
                            monedaProductoPedidoComboBox.requestFocus();
                        }
                    } else {
                        precioProductoPedidoTextField.requestFocus();
                    }
                } else {
                    cantidadProductoPedidoTextField.requestFocus();
                }
            } else {
                productosPedidosTableView.requestFocus();
            }
        } else {
            pedidosProductosTableView.requestFocus();
        }
    }

    @FXML
    void modificarProducto(ActionEvent event) throws ClassNotFoundException, SQLException {
        int pos = productosTableView.getSelectionModel().getSelectedIndex();
        if (pos != -1) {
            String nombre = nombreProductoTextField.getText();
            if (!nombre.isEmpty()) {
                recetas.clear();
                int id = Controladora.getController().getProdCTL().getProductos().get(pos).getId();
                configureTableIngredientesAgregadosProductoReceta(id);
                seleccionIngredientesProductoTitledPane.setExpanded(true);
                ingredientesAgregadosProductoRecetaTitledPane.setExpanded(true);
                seleccionIngredientesProductoTitledPane.setDisable(false);
                ingredientesAgregadosProductoRecetaTitledPane.setDisable(false);
                datosCantidadIngredientesRecetaPane.setVisible(true);
                guardarCambiosRecetaButton.setDisable(false);
            } else {
                nombreProductoTextField.requestFocus();
            }
        }
    }

    @FXML
    void obtenerGanancia(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (cicloGanaciaComboBox.getSelectionModel().getSelectedItem().equals("Semanal")) {
            configureTableGanacia(Controladora.getController().getGananciasCTRL().gananciasSemanales());
        } else if (cicloGanaciaComboBox.getSelectionModel().getSelectedItem().equals("Mensual")) {
            configureTableGanacia(Controladora.getController().getGananciasCTRL().gananciasMensuales());
        }
    }

    @FXML
    void obtenerProductosGanancia(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (cicloProductosComboBox.getSelectionModel().getSelectedItem().equals("Semanal")) {
            configureProductosReporteTableView(Controladora.getController().getVentasCTLR().ventasSemanales());
        } else if (cicloProductosComboBox.getSelectionModel().getSelectedItem().equals("Mensual")) {
            configureProductosReporteTableView(Controladora.getController().getVentasCTLR().ventasMensuales());
        }
    }

    @FXML
    void validarCantidadIngrediente(KeyEvent event) {
        util.Validate.numbersOnly(cantidadIngredienteTextField);
    }

    @FXML
    void validarCantidadIngredienteReceta(KeyEvent event) {
        util.Validate.numbersOnly(cantidadIngredienteProductotextFiel);
    }

    @FXML
    void validarCantidadProductoPedido(KeyEvent event) {
        util.Validate.numbersOnly(cantidadProductoPedidoTextField);
    }

    @FXML
    void validarCostoIngrediente(KeyEvent event) {
        util.Validate.numbersOnly(costoIngredienteTextField);
    }

    @FXML
    void validarRangoCritico(KeyEvent event) {
        util.Validate.numbersOnly(rangoCriticoTextField);
    }

    @FXML
    void validarMontoSalario(KeyEvent event) {
        util.Validate.numbersOnly(salarioTextField);
    }

    @FXML
    void validarPrecioProductoPedido(KeyEvent event) {
        util.Validate.numbersOnly(precioProductoPedidoTextField);
    }

    @FXML
    void validarNombreTipoMoneda(KeyEvent event) {
        util.Validate.lettersOnly(monedaTextField);
    }

    @FXML
    void validarConceptoPago(KeyEvent event) {
        util.Validate.lettersOnly(conceptoPagoTextField);
    }

    @FXML
    void validarEstadosPedidos(KeyEvent event) {
        util.Validate.lettersOnly(nombreEstadoTextField);
    }

    @FXML
    void filtrarPedidosFin(ActionEvent event) throws ClassNotFoundException, SQLException {
        String fechaFin = fechaFinPedidoDatePicker.getEditor().getText();
        if (!fechaFin.isEmpty()) {
            pedidosTitledPane.setExpanded(true);
            String fechaInicio = fechaInicioPedidoDatePicker.getEditor().getText();
            if (!fechaInicio.isEmpty()) {
                if (!fechaFinPedidoDatePicker.getValue().isBefore(fechaInicioPedidoDatePicker.getValue())) {
                    configureTablePedidos(Controladora.getController().pedidosFiltradosFecha(fechaInicioPedidoDatePicker.getValue(), fechaFinPedidoDatePicker.getValue()));
                } else {
                    fechaFinPedidoDatePicker.requestFocus();
                    fechaInicioPedidoDatePicker.requestFocus();
                }
            } else {
                configureTablePedidos(Controladora.getController().pedidosFiltradosFecha(fechaInicioPedidoDatePicker.getValue(), LocalDate.now()));
            }
        } else {
            fechaInicioPedidoDatePicker.requestFocus();
        }
    }

    @FXML
    void filtrarPedidosInicio(ActionEvent event) throws ClassNotFoundException, SQLException {
        String fechaInicio = fechaInicioPedidoDatePicker.getEditor().getText();
        if (!fechaInicio.isEmpty()) {
            pedidosTitledPane.setExpanded(true);
            String fechaFin = fechaFinPedidoDatePicker.getEditor().getText();
            if (!fechaFin.isEmpty()) {
                if (!fechaFinPedidoDatePicker.getValue().isBefore(fechaInicioPedidoDatePicker.getValue())) {
                    configureTablePedidos(Controladora.getController().pedidosFiltradosFecha(fechaInicioPedidoDatePicker.getValue(), fechaFinPedidoDatePicker.getValue()));
                } else {
                    fechaFinPedidoDatePicker.requestFocus();
                    fechaInicioPedidoDatePicker.requestFocus();
                }
            } else {
                configureTablePedidos(Controladora.getController().pedidosFiltradosFecha(fechaInicioPedidoDatePicker.getValue(), LocalDate.now()));
            }
        } else {
            fechaInicioPedidoDatePicker.requestFocus();
        }
    }

    @FXML
    void guardarCambiosReceta(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (productosTableView.getSelectionModel().getSelectedIndex() == -1) {
            String nombre = nombreProductoTextField.getText();
            if (!nombre.isEmpty()) {
                if (!recetas.isEmpty()) {
                    if (Carteles.insersion("Producto").get() == ButtonType.OK) {
                        Controladora.getController().insertarProducto(nombre, recetas);
                        recetas.clear();
                        nombreProductoTextField.clear();
                        modificarProductoButton.setDisable(true);
                        eliminarProductoButton.setDisable(true);
                        seleccionIngredientesProductoTitledPane.setExpanded(false);
                        ingredientesAgregadosProductoRecetaTitledPane.setExpanded(false);
                        seleccionIngredientesProductoTitledPane.setDisable(true);
                        ingredientesAgregadosProductoRecetaTitledPane.setDisable(true);
                        datosCantidadIngredientesRecetaPane.setVisible(false);
                        cantidadIngredienteProductotextFiel.clear();
                        guardarCambiosRecetaButton.setDisable(true);
                    }
                } else {
                    ingredientesAgregadosProductoRecetaTableView.requestFocus();
                }
            } else {
                nombreProductoTextField.requestFocus();
            }
        } else {
            int pos = productosTableView.getSelectionModel().getSelectedIndex();
            if (pos != -1) {
                String nombre = nombreProductoTextField.getText();
                if (!nombre.isEmpty()) {
                    if (Carteles.modificar("Producto").get() == ButtonType.OK) {
                        Controladora.getController().modificarProducto(Controladora.getController().getProdCTL().getProductos().get(pos).getId(), nombre, recetas);
                        recetas.clear();
                        nombreProductoTextField.clear();
                        modificarProductoButton.setDisable(true);
                        eliminarProductoButton.setDisable(true);
                        seleccionIngredientesProductoTitledPane.setExpanded(false);
                        ingredientesAgregadosProductoRecetaTitledPane.setExpanded(false);
                        seleccionIngredientesProductoTitledPane.setDisable(true);
                        ingredientesAgregadosProductoRecetaTitledPane.setDisable(true);
                        datosCantidadIngredientesRecetaPane.setVisible(false);
                        cantidadIngredienteProductotextFiel.clear();
                        productosTableView.getSelectionModel().clearSelection();
                        recetasTableView.getItems().clear();
                        guardarCambiosRecetaButton.setDisable(true);
                    }
                } else {
                    nombreProductoTextField.requestFocus();
                }
            } else {
                productosTableView.requestFocus();
            }
        }
    }

    public static Stage initialicePage() throws IOException {
        page = (AnchorPane) FXMLLoader.load(GESTION_DULCERIA.class
                .getResource("Aplicacion.fxml"));
        Scene scene = new Scene(page);

        primaryStageListClientes.setScene(scene);

        primaryStageListClientes.setTitle(
                "Dulcería");
        primaryStageListClientes.setMaximized(true);
        primaryStageListClientes.show();
        return primaryStageListClientes;
    }

    @FXML
    void limpiarTabPedidos(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 1) {
            pedidosTableView.getSelectionModel().clearSelection();
            estadoProductoPedidoComboBox.getSelectionModel().clearSelection();
            fechaProductoPedidoDatePicker.getEditor().clear();
            productosPedidos.clear();
            eliminarPedidoButton.setDisable(true);
            modificarPedidoButton.setDisable(true);
            productosPedidosTableView.getSelectionModel().clearSelection();
            nombreClienteTextField.clear();
            clientesTableView.getSelectionModel().clearSelection();
            cantidadProductoPedidoTextField.clear();
            precioProductoPedidoTextField.clear();
            monedaProductoPedidoComboBox.getSelectionModel().clearSelection();
            configureTablePedidos(-1);
            fechaInicioPedidoDatePicker.getEditor().clear();
            fechaFinPedidoDatePicker.getEditor().clear();
        }
    }

    @FXML
    void limpiarTabAlamcen(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 1) {
            ingredientesTableViwe.getSelectionModel().clearSelection();
            modificarIngredienteButton.setDisable(true);
            eliminarIngredienteButton.setDisable(true);
            nombreIngredienteTextField.clear();
            unidadIngredienteComboBox.getSelectionModel().clearSelection();
            costoIngredienteTextField.clear();
            tipoMonedaIngredienteComboBox.getSelectionModel().clearSelection();
            cantidadIngredienteTextField.clear();
        }
    }

    @FXML
    void limpiarTabReceta(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 1) {
            productosTableView.getSelectionModel().clearSelection();
            recetas.clear();
            nombreProductoTextField.clear();
            modificarProductoButton.setDisable(true);
            eliminarProductoButton.setDisable(true);
            seleccionIngredientesProductoTitledPane.setExpanded(false);
            ingredientesAgregadosProductoRecetaTitledPane.setExpanded(false);
            seleccionIngredientesProductoTitledPane.setDisable(true);
            ingredientesAgregadosProductoRecetaTitledPane.setDisable(true);
            datosCantidadIngredientesRecetaPane.setVisible(false);
            cantidadIngredienteProductotextFiel.clear();
            recetasTableView.getItems().clear();
            cantidadIngredienteTextField.clear();
        }
    }

    @FXML
    void limpiarTabConfiguracion(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 1) {
            limpiarPanelConceptos();
            limpiarPanelEstados();
            limpiarPanelGastos();
            limpiarPanelGastosProductos();
            limpiarPanelIngredientesRango();
            limpiarPanelMedidas();
            limpiarPanelMonedas();
        }
    }

    public void limpiarPanelConceptos() {
        conceptoPagoTextField.clear();
        conceptosPagoTableView.getSelectionModel().clearSelection();
        modificarConceptoButton.setDisable(true);
        eliminarConceptoButton.setDisable(true);
    }

    public void limpiarPanelMonedas() {
        monedaTextField.clear();
        modificarMonedaButton.setDisable(true);
        eliminarMonedaButton.setDisable(true);
        monedasTableView.getSelectionModel().clearSelection();
    }

    public void limpiarPanelMedidas() {
        medidasTableView.getSelectionModel().clearSelection();
        medidaTextField.clear();
        modificarMedidaButton.setDisable(true);
        eliminarMedidaButton.setDisable(true);
    }

    public void limpiarPanelEstados() {
        nombreEstadoTextField.clear();
        estadosTableView.getSelectionModel().clearSelection();
        modificarEstadosButton.setDisable(true);
        eliminarEstadosButton.setDisable(true);
    }

    public void limpiarPanelGastos() {
        salarioTextField.clear();
        eliminaGastoButton.setDisable(true);
        modificarGastoButton.setDisable(true);
        gastosTableView.getSelectionModel().clearSelection();
    }

    public void limpiarPanelGastosProductos() {
        salarioTextField.clear();
        gastosTableView.getSelectionModel().clearSelection();
        productosGastosTableView.getSelectionModel().clearSelection();
        eliminarGastoProductoButton.setDisable(true);
        modificarGastoProductoButton.setDisable(true);
        gastosAuxiliaresProductoTableView.getSelectionModel().clearSelection();
    }

    public void limpiarPanelIngredientesRango() {
        IngredientesRangoTableView.getSelectionModel().clearSelection();
        rangoCriticoTextField.clear();
        ingredientesRangoInsertadosTableView.getSelectionModel().clearSelection();
        modificarIngredienteRangoButton.setDisable(true);
        eliminarIngredienteRangoButton.setDisable(true);
    }

    @FXML
    void limpiarTabReportes(MouseEvent event) throws ClassNotFoundException, SQLException {
        if (event.getClickCount() > 1) {
            ganaciaTableView.getItems().clear();
            productosReporteTableView.getItems().clear();
            cicloGanaciaComboBox.getSelectionModel().clearSelection();
            cicloProductosComboBox.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void limpiarSeleccionTablaProducto(MouseEvent event) {
        if (event.getClickCount() > 1) {
            productosTableView.getSelectionModel().clearSelection();
            modificarProductoButton.setDisable(true);
            eliminarProductoButton.setDisable(true);
        }
    }

}
