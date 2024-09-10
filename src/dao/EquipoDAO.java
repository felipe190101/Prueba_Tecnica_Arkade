package dao;

import models.Equipo;
import models.Prealerta;
import models.TipoEquipo;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    private ConexionBD connection;

    public EquipoDAO(ConexionBD connection) {
        this.connection = connection;
    }

    public void crearEquipo(String serial, String mac, String observaciones, int tipo_equipo_id, int prealerta_id, int status){
        String query = "INSERT INTO Equipo (serial,mac,observaciones,tipo_equipo,prealerta, estado) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, serial);
            stmt.setString(2, mac);
            stmt.setString(3, observaciones);
            stmt.setInt(4, tipo_equipo_id);
            stmt.setInt(5, prealerta_id);
            stmt.setInt(6, status);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarEquipo(int id) throws SQLException {
        String query = "DELETE FROM Equipo WHERE id = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void eliminarEquipoPorSerialYMac(String serial, String mac) throws SQLException {
        String query = "DELETE FROM Equipo WHERE serial = ? AND mac = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, serial);
            stmt.setString(2, mac);
        }
    }

    public void actualizarEquipo(String serial, String mac) throws SQLException {
        String query = "UPDATE Equipo SET estado = 1 WHERE serial = ? AND mac = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, serial);
            stmt.setString(2, mac);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Equipo obtenerEquipoPorSerial(String serial) throws SQLException {
        String query = "SELECT e.id, e.serial, e.mac, e.observaciones, e.estado," +
                "te.id AS tipo_equipo, te.nombre AS tipo_equipo_nombre, te.largo_serial, te.largo_mac, " +
                "p.id AS prealerta, p.nombre AS prealerta_nombre, p.guia, p.fecha_creacion, p.estado " +
                "FROM Equipo e " +
                "JOIN TipoEquipo te ON e.tipo_equipo = te.id " +
                "JOIN Prealerta p ON e.prealerta = p.id " +
                "WHERE e.serial = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, serial);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear el objeto TipoEquipo
                    TipoEquipo tipoEquipo = new TipoEquipo(
                            rs.getInt("tipo_equipo"),
                            rs.getString("tipo_equipo_nombre"),
                            rs.getInt("largo_serial"),
                            rs.getInt("largo_mac")
                    );

                    // Crear el objeto Prealerta
                    Prealerta prealerta = new Prealerta(
                            rs.getInt("prealerta"),
                            rs.getString("prealerta_nombre"),
                            rs.getString("guia"),
                            rs.getTimestamp("fecha_creacion"),
                            rs.getInt("estado")
                    );

                    // Crear y devolver el objeto Equipo
                    return new Equipo(
                            rs.getInt("id"),
                            rs.getString("serial"),
                            rs.getString("mac"),
                            rs.getString("observaciones"),
                            rs.getInt("estado"),
                            tipoEquipo,
                            prealerta

                    );
                } else {
                    // No se encontró un equipo con el serial y mac proporcionados
                    return null;
                }
            }
        }
    }

    public Equipo obtenerEquipoPorMAC(String mac) throws SQLException {
        String query = "SELECT e.id, e.serial, e.mac, e.observaciones, e.estado," +
                "te.id AS tipo_equipo, te.nombre AS tipo_equipo_nombre, te.largo_serial, te.largo_mac, " +
                "p.id AS prealerta, p.nombre AS prealerta_nombre, p.guia, p.fecha_creacion, p.estado " +
                "FROM Equipo e " +
                "JOIN TipoEquipo te ON e.tipo_equipo = te.id " +
                "JOIN Prealerta p ON e.prealerta = p.id " +
                "WHERE e.mac = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setString(1, mac);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear el objeto TipoEquipo
                    TipoEquipo tipoEquipo = new TipoEquipo(
                            rs.getInt("tipo_equipo"),
                            rs.getString("tipo_equipo_nombre"),
                            rs.getInt("largo_serial"),
                            rs.getInt("largo_mac")
                    );

                    // Crear el objeto Prealerta
                    Prealerta prealerta = new Prealerta(
                            rs.getInt("prealerta"),
                            rs.getString("prealerta_nombre"),
                            rs.getString("guia"),
                            rs.getTimestamp("fecha_creacion"),
                            rs.getInt("estado")
                    );

                    // Crear y devolver el objeto Equipo
                    return new Equipo(
                            rs.getInt("id"),
                            rs.getString("serial"),
                            rs.getString("mac"),
                            rs.getString("observaciones"),
                            rs.getInt("estado"),
                            tipoEquipo,
                            prealerta

                    );
                } else {
                    // No se encontró un equipo con el serial y mac proporcionados
                    return null;
                }
            }
        }
    }

    public Equipo obtenerEquipoPorId(int id) throws SQLException {
        String query = "SELECT e.id, e.serial, e.mac, e.observaciones, e.estado," +
                "te.id AS tipo_equipo_id, te.nombre AS tipo_equipo_nombre, te.largo_serial, te.largo_mac, " +
                "p.id AS prealerta_id, p.nombre AS prealerta_nombre, p.guia, p.fecha_creacion, p.estado " +
                "FROM Equipo e " +
                "JOIN TipoEquipo te ON e.tipo_equipo = te.id " +
                "JOIN Prealerta p ON e.prealerta = p.id " +
                "WHERE e.id = ?";

        Equipo equipo = null;

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    TipoEquipo tipoEquipo = new TipoEquipo(
                            rs.getInt("id"),
                            rs.getString("tipo_equipo_nombre"),
                            rs.getInt("largo_serial"),
                            rs.getInt("largo_mac")
                    );

                    Prealerta prealerta = new Prealerta(
                            rs.getInt("id"),
                            rs.getString("prealerta_nombre"),
                            rs.getString("guia"),
                            rs.getTimestamp("fecha_creacion"),
                            rs.getInt("estado")
                    );

                    equipo = new Equipo(
                            rs.getInt("id"),
                            rs.getString("serial"),
                            rs.getString("mac"),
                            rs.getString("observaciones"),
                            rs.getInt("estado"),
                            tipoEquipo,
                            prealerta

                    );
                }
            }
        }

        return equipo;
    }

    public List<Equipo> obtenerTodosLosEquiposToPrealerta(int idPrealerta) throws SQLException {
        String query = "SELECT e.id, e.serial, e.mac, e.observaciones, e.estado, " +
                "te.id AS tipo_equipo, te.nombre AS tipo_equipo_nombre, te.largo_serial, te.largo_mac, " +
                "p.id AS prealerta, p.nombre AS prealerta_nombre, p.guia, p.fecha_creacion, p.estado " +
                "FROM Equipo e " +
                "JOIN TipoEquipo te ON e.tipo_equipo = te.id " +
                "JOIN Prealerta p ON e.prealerta = p.id " +
                "WHERE e.prealerta = ?";

        List<Equipo> equipos = new ArrayList<>();

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, idPrealerta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TipoEquipo tipoEquipo = new TipoEquipo(
                            rs.getInt("tipo_equipo"),
                            rs.getString("tipo_equipo_nombre"),
                            rs.getInt("largo_serial"),
                            rs.getInt("largo_mac")
                    );

                    Prealerta prealerta = new Prealerta(
                            rs.getInt("prealerta"),
                            rs.getString("prealerta_nombre"),
                            rs.getString("guia"),
                            rs.getTimestamp("fecha_creacion"),
                            rs.getInt("estado")
                    );

                    Equipo equipo = new Equipo(
                            rs.getInt("id"),
                            rs.getString("serial"),
                            rs.getString("mac"),
                            rs.getString("observaciones"),
                            rs.getInt("estado"),
                            tipoEquipo,
                            prealerta

                    );

                    equipos.add(equipo);
                }
            }
        }

        return equipos;
    }

    public List<Equipo> obtenerTodosLosEquiposByEquipmentType(int idEquipmentType) throws SQLException {
        String query = "SELECT e.id, e.serial, e.mac, e.observaciones, e.estado, " +
                "te.id AS tipo_equipo, te.nombre AS tipo_equipo_nombre, te.largo_serial, te.largo_mac, " +
                "p.id AS prealerta, p.nombre AS prealerta_nombre, p.guia, p.fecha_creacion, p.estado " +
                "FROM Equipo e " +
                "JOIN TipoEquipo te ON e.tipo_equipo = te.id " +
                "JOIN Prealerta p ON e.prealerta = p.id " +
                "WHERE e.tipo_equipo = ?";

        List<Equipo> equipos = new ArrayList<>();

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, idEquipmentType);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TipoEquipo tipoEquipo = new TipoEquipo(
                            rs.getInt("tipo_equipo"),
                            rs.getString("tipo_equipo_nombre"),
                            rs.getInt("largo_serial"),
                            rs.getInt("largo_mac")
                    );

                    Prealerta prealerta = new Prealerta(
                            rs.getInt("prealerta"),
                            rs.getString("prealerta_nombre"),
                            rs.getString("guia"),
                            rs.getTimestamp("fecha_creacion"),
                            rs.getInt("estado")
                    );

                    Equipo equipo = new Equipo(
                            rs.getInt("id"),
                            rs.getString("serial"),
                            rs.getString("mac"),
                            rs.getString("observaciones"),
                            rs.getInt("estado"),
                            tipoEquipo,
                            prealerta

                    );

                    equipos.add(equipo);
                }
            }
        }
        return equipos;
    }

    public List<Equipo> obtenerTodosLosEquipos() throws SQLException {
        // Consulta SQL con filtro por prealerta_id
        String query = "SELECT e.id, e.serial, e.mac, e.observaciones, e.estado, " +
                "te.id AS tipo_equipo, te.nombre AS tipo_equipo_nombre, te.largo_serial, te.largo_mac, " +
                "p.id AS prealerta, p.nombre AS prealerta_nombre, p.guia, p.fecha_creacion, p.estado " +
                "FROM Equipo e " +
                "JOIN TipoEquipo te ON e.tipo_equipo = te.id " +
                "JOIN Prealerta p ON e.prealerta = p.id ";

        List<Equipo> equipos = new ArrayList<>();

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Crear el objeto TipoEquipo
                    TipoEquipo tipoEquipo = new TipoEquipo(
                            rs.getInt("tipo_equipo"),
                            rs.getString("tipo_equipo_nombre"),
                            rs.getInt("largo_serial"),
                            rs.getInt("largo_mac")
                    );

                    // Crear el objeto Prealerta
                    Prealerta prealerta = new Prealerta(
                            rs.getInt("prealerta"),
                            rs.getString("prealerta_nombre"),
                            rs.getString("guia"),
                            rs.getTimestamp("fecha_creacion"),
                            rs.getInt("estado")
                    );

                    // Crear el objeto Equipo
                    Equipo equipo = new Equipo(
                            rs.getInt("id"),
                            rs.getString("serial"),
                            rs.getString("mac"),
                            rs.getString("observaciones"),
                            rs.getInt("estado"),
                            tipoEquipo,
                            prealerta

                    );

                    // Agregar el equipo a la lista
                    equipos.add(equipo);
                }
            }
        }

        return equipos;
    }

    public int contarEquiposConEstadoUno(int prealertaId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Equipo WHERE estado = 1 AND prealerta = ?";

        try (PreparedStatement stmt = connection.establecerConexion().prepareStatement(query)) {
            stmt.setInt(1, prealertaId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna el conteo de registros
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar equipos");
            throw new RuntimeException(e);
        }
        return 0;
    }
}
