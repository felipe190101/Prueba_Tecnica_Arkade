package models;

public class Equipo {
    private int id;
    private String serial;
    private String mac;
    private String observaciones;
    private TipoEquipo tipoEquipo;
    private Prealerta prealerta;
    private int status;

    public Equipo(int id, String serial, String mac, String observaciones, int status,TipoEquipo tipoEquipo, Prealerta prealerta) {
        this.id = id;
        this.serial = serial;
        this.mac = mac;
        this.observaciones = observaciones;
        this.status = status;
        this.tipoEquipo = tipoEquipo;
        this.prealerta = prealerta;
    }

    public int getId() {
        return id;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public String getSerial() {
        return serial;
    }

    public String getMac() {
        return mac;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Prealerta getPrealerta() {
        return prealerta;
    }

    public void setPrealerta(Prealerta prealerta) {
        this.prealerta = prealerta;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", serial='" + serial + '\'' +
                ", mac='" + mac + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", estado='" + status + '\'' +
                ", tipoEquipo=" + tipoEquipo +
                ", prealerta=" + prealerta +
                '}';
    }
}