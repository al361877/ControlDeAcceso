package es.uji.ei1027.ControlDeAcceso.model;

import java.time.LocalDate;
import java.util.Date;

public class Estacion {
    private String id;
    private LocalDate fechaIni;
    private LocalDate fechaFin;
    private String fechaIniString;
    private String fechaFinString;

    public String getFechaIniString() {
        return fechaIniString;
    }

    public void setFechaIni(String fechaIniString) {
        this.fechaIniString=fechaIniString;
        LocalDate localDate1 = LocalDate.parse(fechaIniString);
        this.fechaIni = localDate1;
    }

    public String getFechaFinString() {
        return fechaFinString;
    }

    public void setFechaFin(String fechaFinString) {
        this.fechaFinString=fechaFinString;
        LocalDate localDate1 = LocalDate.parse(fechaFinString);
        this.fechaFin = localDate1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }


}
