package es.uji.ei1027.ControlDeAcceso.model;

public class Ciudadano  extends  Usuario{
    private String dni;


    public String getDni(){
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
