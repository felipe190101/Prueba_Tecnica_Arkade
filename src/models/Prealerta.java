package models;

import java.util.Date;

public class Prealerta {
    private int id;
    private String nombre;
    private String guia;
    private Date fechaCreacion;
    private int status;

    public Prealerta(int id, String nombre, String guia, Date fechaCreacion, int status) {
        this.id = id;
        this.nombre = nombre;
        this.guia = guia;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGuia() {
        return guia;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prealerta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", guia='" + guia + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", estado='" + status + '\'' +
                '}';
    }
}
