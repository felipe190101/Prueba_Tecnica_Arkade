package dao;

import models.Prealerta;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrealertaDAO {

    private ConexionBD connection;

    public PrealertaDAO(ConexionBD connection) {
        this.connection = connection;
    }

    public void crearPrealerta(String nombre, String guia, Date fecha_creacion, int status) throws SQLException {
        String query = "INSERT INTO Prealerta (nombre, guia, fecha_creacion, estado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, guia);
            stmt.setTimestamp(3, new Timestamp(fecha_creacion.getTime()));
            stmt.setInt(4, status);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Prealerta creada","", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Prealerta no creada","", JOptionPane.ERROR_MESSAGE);

            throw new RuntimeException(e);
        }
    }

    public List<Prealerta> obtenerPrealertas() throws SQLException {

        List<Prealerta> prealertas = new ArrayList<>();
        String query = "SELECT * FROM Prealerta";

        try {
            Statement stmt = connection.establecerConexion().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                Prealerta prealerta = new Prealerta(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("guia"),
                        rs.getDate("fecha_creacion"),
                        rs.getInt("estado")
                );
                prealertas.add(prealerta);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prealertas;
    }

    public Prealerta obtenerPrealertaPorId(int id) throws SQLException {

        String query = "SELECT * FROM Prealerta WHERE id = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Prealerta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("guia"),
                            rs.getDate("fecha_creacion"),
                            rs.getInt("estado")
                    );
                }
            }
        }
        return null;
    }

    public Prealerta obtenerPrealertaPorNombre(String nombre) throws SQLException {

        String query = "SELECT * FROM Prealerta WHERE nombre = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Prealerta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("guia"),
                            rs.getDate("fecha_creacion"),
                            rs.getInt("estado")
                    );
                }
            }
        }
        return null;
    }

    public Prealerta obtenerPrealertaPorGuia(String guia) throws SQLException {

        String query = "SELECT * FROM Prealerta WHERE guia = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, guia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Prealerta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("guia"),
                            rs.getDate("fecha_creacion"),
                            rs.getInt("estado")
                    );
                }
            }
        }
        return null;
    }

    public Prealerta getPrealertaByNameAndGuide(String name,String guia) throws SQLException {

        String query = "SELECT * FROM Prealerta WHERE nombre = ? AND guia = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, guia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Prealerta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("guia"),
                            rs.getDate("fecha_creacion"),
                            rs.getInt("estado")
                    );
                }
            }
        }
        return null;
    }

    public List<Prealerta> buscarPorRangoDeFechas(Date fechaInicio, Date fechaFin) throws SQLException {
        List<Prealerta> prealertas = new ArrayList<>();
        String query = "SELECT * FROM Prealerta WHERE fecha_creacion BETWEEN ? AND ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {

            stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Prealerta prealerta = new Prealerta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("guia"),
                            rs.getDate("fecha_creacion"),
                            rs.getInt("estado")
                    );
                    prealertas.add(prealerta);
                }
            }
        }
        return prealertas;
    }

    public Prealerta searchByNameAndDate(String name, Date fechaInicio, Date fechaFin) throws SQLException {
        String query = "SELECT * FROM Prealerta WHERE nombre = ? AND fecha_creacion BETWEEN ? AND ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setDate(2, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(3, new java.sql.Date(fechaFin.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return new Prealerta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("guia"),
                            rs.getDate("fecha_creacion"),
                            rs.getInt("estado")
                    );
                }
            }
        }
        return null;
    }

    public Prealerta searchByGuideAndDate(String guide, Date fechaInicio, Date fechaFin) throws SQLException {
        String query = "SELECT * FROM Prealerta WHERE guia = ? AND fecha_creacion BETWEEN ? AND ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {

            stmt.setString(1, guide);
            stmt.setDate(2, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(3, new java.sql.Date(fechaFin.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return new Prealerta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("guia"),
                            rs.getDate("fecha_creacion"),
                            rs.getInt("estado")
                    );
                }
            }
        }
        return null;
    }

    public void actualizarPrealerta(String name, String guia, int id) throws SQLException {
        String query = "UPDATE Prealerta SET nombre = ?, guia = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, guia);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Actualizacion correcta");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de actualizacion");
            throw new RuntimeException(e);
        }
    }

    public void actualizarEstadoPrealerta(int id) throws SQLException {
        String query = "UPDATE Prealerta SET estado = 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de actualización");
            throw new RuntimeException(e);
        }
    }

    public void eliminarPrealerta(int id) throws SQLException {
        String query = "DELETE FROM Prealerta WHERE id = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de eliminacion, verifique el id ingresado");
            throw new RuntimeException(e);
        }
    }
}
