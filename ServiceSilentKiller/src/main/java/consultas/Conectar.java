/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author franc
 */
public class Conectar {
      private String username = "appdist";
    private String pass = "@Aplicaciones@Distribuidas@2021";
    private String classname = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://190.15.134.7:8080/SilentKiller";
    private Connection conn;

    public Conectar() {
        try {
            Class.forName(classname);
            conn = DriverManager.getConnection(url, username, pass);

        } catch (Exception e) {
            System.err.println(e.getMessage() + " error");
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void closeconnection() throws SQLException {
        conn.close();
    }
   
    
}
