package dao;


import models.TipoEquipo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoEquipoDAO {

    private ConexionBD connection;

    public TipoEquipoDAO(ConexionBD connection) {
        this.connection = connection;
    }

    public List<TipoEquipo> obtenerTiposEquipos() throws SQLException {

        List<TipoEquipo> tiposEquipos = new ArrayList<>();
        String query = "SELECT * FROM TipoEquipo";

        try {
            Statement stmt = connection.establecerConexion().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                TipoEquipo tipoEquipo = new TipoEquipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("largo_serial"),
                        rs.getInt("largo_mac")
                );
                tiposEquipos.add(tipoEquipo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tiposEquipos;
    }

    public void crearTipoEquipo(String name, int largo_serial, int  largo_MAC){
        String query = "INSERT INTO TipoEquipo (nombre,largo_serial,largo_MAC) VALUES (?,?,?)";

        try {

            CallableStatement cs = connection.establecerConexion().prepareCall(query);

            cs.setString(1,name);
            cs.setInt(2,largo_serial);
            cs.setInt(3,largo_MAC);

            cs.execute();

            JOptionPane.showMessageDialog(null,"Se inserto correctamente");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error de insercion");
            throw new RuntimeException(e);
        }

    }

    public void actualizarTipoEquipo(int largo_serial, int largo_MAC, int id) throws SQLException {
        String query = "UPDATE TipoEquipo SET largo_serial = ?, largo_mac = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, largo_serial);
            stmt.setInt(2, largo_MAC);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Actualizacion correcta");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de actualizacion");
            throw new RuntimeException(e);
        }
    }

    public TipoEquipo obtenerTipoEquipoPorId(int id) throws SQLException {
        String query = "SELECT * FROM TipoEquipo WHERE id = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoEquipo(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getInt("largo_serial"),
                            rs.getInt("largo_mac")
                    );
                }
            }
        }
        return null;
    }
    public TipoEquipo obtenerEquipmentTypeByName(String nombre) throws SQLException {

        String query = "SELECT * FROM TipoEquipo WHERE nombre = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoEquipo(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getInt("largo_serial"),
                            rs.getInt("largo_mac")
                    );
                }
            }
        }
        return null;
    }

    public void eliminarTipoEquipo(int id) throws SQLException {
        String query = "DELETE FROM TipoEquipo WHERE id = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Eliminacion Correcta");

        }
    }
}
