package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD{

    Connection conectar = null;

    String usuario = "Prueba_Arkade";
    String password = "12345";
    String bd = "Prueba_Arkade";
    String ip = "localhost";
    String puerto = "1433";

    public Connection establecerConexion(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String cadena = "jdbc:sqlserver://"+ip+":"+puerto+";"+"databaseName="+bd+";"+"encrypt=true;trustServerCertificate=true";

            conectar = DriverManager.getConnection(cadena,usuario,password);

            //JOptionPane.showMessageDialog(null,"Se conecto");

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null,"No se conecto");
            throw new RuntimeException(e);
        }

        return conectar;
    }
}