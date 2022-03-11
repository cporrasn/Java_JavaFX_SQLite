/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Cynthia
 */
public class GESTION_DULCERIA extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        System.setProperty("glass.accessible.force", "false");
        try {

            AplicacionController.initialicePage();
            
        }
        catch (IOException ex) {
            Logger.getLogger(GESTION_DULCERIA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Application.launch(GESTION_DULCERIA.class, (java.lang.String[]) null);

    }
    
     private static GESTION_DULCERIA principal;

    public static GESTION_DULCERIA getPrincipal() {
        if (principal == null) {
            principal = new GESTION_DULCERIA();
        }
        return principal;
    }
    
}
