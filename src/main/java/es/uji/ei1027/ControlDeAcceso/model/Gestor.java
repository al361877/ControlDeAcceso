package es.uji.ei1027.ControlDeAcceso.model;

import java.util.List;

public class Gestor extends Usuario{
    private String municipio;
    private String dni;



    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipios){this.municipio = municipios;}
}
