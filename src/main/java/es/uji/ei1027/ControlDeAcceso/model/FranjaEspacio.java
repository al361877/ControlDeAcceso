package es.uji.ei1027.ControlDeAcceso.model;

import java.sql.Time;
import java.util.Date;

public class FranjaEspacio {
    private String id;
    private Date fechaIni;
    private Date fechaFin;
    private Time horaIni;
    private Time horaFin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Date getFechaIni(){
        return fechaFin;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public Time getHoraIni() {
        return horaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public void setHoraIni(Time horaIni) {
        this.horaIni = horaIni;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaFin() {
        return fechaFin;
    }


}
