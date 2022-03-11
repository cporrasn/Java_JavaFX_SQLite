/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import model.Controller;

/**
 *
 * @author JULIETA
 */
public class ConnectionDatabase {
    
        private Connection c= null;
        private static ConnectionDatabase connectionDatabase= null;
        
        public static ConnectionDatabase getConnectionDatabase() throws ClassNotFoundException, SQLException {
		if (connectionDatabase == null) {
			connectionDatabase = new ConnectionDatabase();
		}
		return connectionDatabase;
	}
       
        public void connect() throws ClassNotFoundException, SQLException{
            Class.forName("org.sqlite.JDBC");
            c =DriverManager.getConnection("jdbc:sqlite:BD.db");
            c.setAutoCommit(false);
        }
         
        public ConnectionDatabase() throws ClassNotFoundException, SQLException{
            connect();
        }
         
        public Connection getConnection(){
            return c;
        }
    
}
