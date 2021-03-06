package es.uji.ei1027.ControlDeAcceso.model;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Reserva {
    private String id;
    private String dniCiudadano;
    private String franja;
    private String espacio_publico;
    private String estado_reserva;
    private String zona;
    private String nombreEspacio;
    private String horaIniString;
    private String horaFinString;
    private int numPersonas;

    private LocalDate fechaIniDate;
    private String fechaIniString;

    public String getHoraIniString() {
        return horaIniString;
    }

    public void setHoraIniString(String horaIniString) {
        this.horaIniString = horaIniString;
    }

    public String getHoraFinString() {
        return horaFinString;
    }

    public void setHoraFinString(String horaFinString) {
        this.horaFinString = horaFinString;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public String getNombreEspacio() {
        return nombreEspacio;
    }

    public void setNombreEspacio(String nombreEspacio) {
        this.nombreEspacio = nombreEspacio;
    }

    public LocalDate getFechaIni(){
        fechaIniDate = LocalDate.parse(fechaIniString);
        return this.fechaIniDate;
    }


    public String getFechaIniString() {
        return fechaIniString;
    }



    public void setFechaIniString(String fechaIniString) {

        this.fechaIniString = fechaIniString;
    }



    public void setFechaIni(String fechaIni) {

        this.fechaIniString=fechaIni;
        LocalDate localDate1 = LocalDate.parse(fechaIni);
        this.fechaIniDate = localDate1;
    }


    public String getFranja() {
        return franja;
    }

    public void setFranja(String franja) {
        this.franja = franja;
    }

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

    @Override
    public String toString() {
        return "Reserva{" +
                "id='" + id + '\'' +
                ", dniCiudadano='" + dniCiudadano + '\'' +
                ", franja='" + franja + '\'' +
                ", espacio_publico='" + espacio_publico + '\'' +
                ", estado_reserva='" + estado_reserva + '\'' +
                ", zona='" + zona + '\'' +
                ", numPersonas=" + numPersonas +
                ", fechaIniDate=" + fechaIniDate +
                ", fechaIniString='" + fechaIniString + '\'' +
                '}';
    }
}
