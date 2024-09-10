package models;

public class TipoEquipo {
    private int id;
    private String nombre;
    private int largoSerial;
    private int largoMac;

    public TipoEquipo(int id, String nombre, int largoSerial, int largoMac) {
        this.id = id;
        this.nombre = nombre;
        this.largoSerial = largoSerial;
        this.largoMac = largoMac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLargoSerial() {
        return largoSerial;
    }

    public void setLargoSerial(int largoSerial) {
        this.largoSerial = largoSerial;
    }

    public int getLargoMac() {
        return largoMac;
    }

    public void setLargoMac(int largoMac) {
        this.largoMac = largoMac;
    }

    @Override
    public String toString() {
        return "TipoEquipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", largoSerial='" + largoSerial + '\'' +
                ", largoMac='" + largoMac + '\'' +
                '}';
    }
}
