package es.uji.ei1027.ControlDeAcceso.model;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Reserva {
    private String id;
    private String dniCiudadano;

    private String espacio_publico;
    private String estado_reserva;
    private String zona;

    private Time horaIni;
    private Time horaFin;
    private String horaIniString;
    private String horaFinString;
    private int numPersonas;

    private LocalDate fechaIniDate;
    private LocalDate fechaFinDate;

    private String fechaIniString;
    private String fechaFinString;

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }
    public LocalDate getFechaIni(){
        fechaIniDate = LocalDate.parse(fechaIniString);
        return this.fechaIniDate;
    }
    public LocalDate getFechaFin(){
        fechaFinDate = LocalDate.parse(fechaFinString);
        return this.fechaFinDate;
    }

    public String getFechaIniString() {
        return fechaIniString;
    }
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

    public void setFechaIniString(String fechaIniString) {

        this.fechaIniString = fechaIniString;
    }

    public String getFechaFinString() {
        return fechaFinString;
    }

    public void setFechaFinString(String fechaFinString) {
        this.fechaFinString = fechaFinString;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public Time getHoraIni() {
        return horaIni;
    }


    public void setFechaIni(String fechaIni) {

        this.fechaIniString=fechaIni;
        LocalDate localDate1 = LocalDate.parse(fechaIni);
        this.fechaIniDate = localDate1;
    }
    public void setFechaFin(String fechaFin) {

        this.fechaFinString=fechaFin;
        LocalDate localDate1 = LocalDate.parse(fechaFin);
        this.fechaFinDate = localDate1;
    }


    public void setHoraFin(String horaFinS) {
        this.horaFinString = horaFinS+":00";

        this.horaFin=Time.valueOf(horaFinS+":00");

    }

    public void setHoraFinRow(Time horaFinS) {

        this.horaFin=horaFinS;
        this.horaFinString=horaFinS.toString();

    }
    public void setHoraIniRow(Time horaIniS) {

        this.horaIni=horaIniS;
        this.horaIniString=horaIniS.toString();

    }
    public void setHoraIni(String horaIniS) {

        this.horaIniString = horaIniS+":00";

        this.horaIni=Time.valueOf(horaIniS+":00");


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
                ", espacio_publico='" + espacio_publico + '\'' +
                ", estado_reserva='" + estado_reserva + '\'' +
                ", zona='" + zona + '\'' +
                ", horaIni=" + horaIni +
                ", horaFin=" + horaFin +
                ", horaIniString='" + horaIniString + '\'' +
                ", horaFinString='" + horaFinString + '\'' +
                ", numPersonas=" + numPersonas +
                ", fechaIniDate=" + fechaIniDate +
                ", fechaFinDate=" + fechaFinDate +
                ", fechaIniString='" + fechaIniString + '\'' +
                ", fechaFinString='" + fechaFinString + '\'' +
                '}';
    }
}
