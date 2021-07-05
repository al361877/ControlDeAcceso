package es.uji.ei1027.ControlDeAcceso.model;

public class Controlador extends Usuario{
    private String dni;
    private String espacio_publico;
    private String diaSemanaTrabaja;

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

    public  void setDiasSemanaTrabaja(String diaSemanaTrabaja){this.diaSemanaTrabaja=diaSemanaTrabaja;}

    public String getDiasSemanaTrabaja(){return diaSemanaTrabaja;}

    @Override
    public String toString() {
        return "Controlador{" +
                "dni='" + dni + '\'' +
                ", espacio_publico='" + espacio_publico + '\'' +
                ", dias_semana_trabaja='" + diaSemanaTrabaja + '\'' +
                '}';
    }
}
