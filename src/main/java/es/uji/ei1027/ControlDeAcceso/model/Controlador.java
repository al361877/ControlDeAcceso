package es.uji.ei1027.ControlDeAcceso.model;

public class Controlador extends Usuario{
    private String dni;
    private String espacio_publico;

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public String getEspacio_publico() {
        return espacio_publico;
    }

    public void setEspacio_publico(String espacio_publico) {
        this.espacio_publico = espacio_publico;
    }

}
