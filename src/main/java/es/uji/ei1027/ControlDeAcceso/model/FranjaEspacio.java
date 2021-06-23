package es.uji.ei1027.ControlDeAcceso.model;

import java.sql.Time;


public class FranjaEspacio {
    private String id;
    private Time horaIni;
    private Time horaFin;
    private String horaIniString;
    private String horaFinString;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Time getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(Time horaIni) {
        this.horaIni = horaIni;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
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


    public void setHoraFin(String horaFinS) {
        this.horaFinString = horaFinS;

        this.horaFin=Time.valueOf(horaFinS);

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

        this.horaIniString = horaIniS;

        this.horaIni=Time.valueOf(horaIniS);


    }
    @Override
    public String toString() {
        return "FranjaEspacio{" +
                "id='" + id + '\'' +
                ", horaIni=" + horaIni +
                ", horaFin=" + horaFin +
                ", horaIniString='" + horaIniString + '\'' +
                ", horaFinString='" + horaFinString + '\'' +
                '}';
    }
}
