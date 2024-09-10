package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilitiesDAO {

    private ConexionBD connection;

    public UtilitiesDAO(ConexionBD connection) {
        this.connection = connection;
    }

    public List<String> obtenerNombresDeColumnas(String nombreTabla) {
        List<String> nombresDeColumnas = new ArrayList<>();
        ConexionBD conexionBD = new ConexionBD();

        try (Connection conexion = conexionBD.establecerConexion()) {
            String query = "SELECT * FROM " + nombreTabla + " WHERE 1 = 0";  // Consulta que no devuelve filas, solo metadatos

            try (Statement stmt = conexion.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                ResultSetMetaData metaData = rs.getMetaData();
                int numeroColumnas = metaData.getColumnCount();

                for (int i = 1; i <= numeroColumnas; i++) {
                    String nombreColumna = metaData.getColumnName(i);
                    nombresDeColumnas.add(nombreColumna);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombresDeColumnas;
    }
}
