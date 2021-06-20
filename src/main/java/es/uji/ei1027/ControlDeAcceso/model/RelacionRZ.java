package es.uji.ei1027.ControlDeAcceso.model;

public class RelacionRZ {
    private String id_reserva;
    private String id_zona;

    public String getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(String id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getId_zona() {
        return id_zona;
    }

    public void setId_zona(String id_zona) {
        this.id_zona = id_zona;
    }

    @Override
    public String toString() {
        return "RelacionRZ{" +
                "id_reserva='" + id_reserva + '\'' +
                ", id_zona='" + id_zona + '\'' +
                '}';
    }
}
