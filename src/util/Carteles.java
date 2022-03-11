/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Cynthia
 */
public class Carteles {

    public static void eliminacionNoPermitida(String elemento) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No se puede eliminar el estado " + elemento);
        alert.showAndWait();
    }
    
    public static void fechaIncorrectaPedido() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("La fecha especificada para el pedido es posterior a la fecha actual");
        alert.showAndWait();
    }
    
    public static void modificarNoPermitidaPedido(String elemento) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No se puede modificar un pedido con estado " + elemento);
        alert.showAndWait();
    }
    
    public static void eliminacionNoPermitidaMoneda(String elemento) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No se puede eliminar el tipo de moneda " + elemento);
        alert.showAndWait();
    }
    
    public static void modificarNoPermitidaPedidoMoneda(String elemento) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No se puede modificar el tipo de moneda " + elemento);
        alert.showAndWait();
    }

    public static void modificacionNoPermitida(String elemento) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No se puede modificar el estado " + elemento);
        alert.showAndWait();
    }
    
    public static Optional<ButtonType> eliminacion(String elemento) {
        Optional<ButtonType> response;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText("Eliminar " + elemento);
        alert.setContentText("¿Desea eliminar este elemento?");
        response = alert.showAndWait();
        return response;
    }
    
    public static Optional<ButtonType> insersion(String elemento) {
        Optional<ButtonType> response;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Adicionar");
        alert.setHeaderText("Adicionar " + elemento);
        alert.setContentText("¿Desea adicionar este elemento?");
        response = alert.showAndWait();
        return response;
    }
    
    public static Optional<ButtonType> modificar(String elemento) {
        Optional<ButtonType> response;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modificar");
        alert.setHeaderText("Modificar " + elemento);
        alert.setContentText("¿Desea modificar este elemento?");
        response = alert.showAndWait();
        return response;
    }
    
    public static Optional<ButtonType> alertaCantidadAlmacenInsuficiente(String elemento) {
        Optional<ButtonType> response;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Cantidad de " + elemento);
        alert.setContentText("En al almacen no hay suficientes ingredientes para hacer este pedido");
        response = alert.showAndWait();
        return response;
    }

}
