package es.uji.ei1027.ControlDeAcceso.model;

public class Reserva {
    private String id;
    private String dniCiudadano;
    private String franjaEspacio;
    private String espacio_publico;
    private String estado_reserva;
    private String zona;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEspacio_publico() {
        return espacio_publico;
    }

    public void setEspacio_publico(String espacio_publico) {
        this.espacio_publico = espacio_publico;
    }

    public String getDniCiudadano() {
        return dniCiudadano;
    }

    public void setDniCiudadano(String dniCiudadano) {
        this.dniCiudadano = dniCiudadano;
    }

    public String getFranjaEspacio() {
        return franjaEspacio;
    }

    public void setFranjaEspacio(String franjaEspacio) {
        this.franjaEspacio = franjaEspacio;
    }

    public String getEstado_reserva() {
        return estado_reserva;
    }

    public void setEstado_reserva(String estado_reserva) {
        this.estado_reserva = estado_reserva;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
