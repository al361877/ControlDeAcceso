package es.uji.ei1027.ControlDeAcceso.model;

public class Controlador extends Usuario{
    private String dni;
    private String espacio_publico;
    private String dias_semana_trabaja;

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

    public  void setDiasSemana(String dias_semana_trabaja){this.dias_semana_trabaja=dias_semana_trabaja;}

    public String getDiasSemana(){return dias_semana_trabaja;}

    @Override
    public String toString() {
        return "Controlador{" +
                "dni='" + dni + '\'' +
                ", espacio_publico='" + espacio_publico + '\'' +
                ", dias_semana_trabaja='" + dias_semana_trabaja + '\'' +
                '}';
    }
}
