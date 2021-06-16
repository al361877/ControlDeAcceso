package es.uji.ei1027.ControlDeAcceso.model;

public class Zona {
    private String id;
    private String nombre;
    private String espacio_publico;
    private int cp;
    private String tipo_suelo;
    private String tipo_acceso;
    private int aforo_actual;
    private int aforo_maximo;

    public int getAforo_actual() {
        return aforo_actual;
    }

    public void setAforo_actual(int aforo_actual) {
        this.aforo_actual = aforo_actual;
    }

    public int getAforo_maximo() {
        return aforo_maximo;
    }

    public void setAforo_maximo(int aforo_maximo) {
        this.aforo_maximo = aforo_maximo;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspacio_publico() {
        return espacio_publico;
    }

    public void setEspacio_publico(String espacio_publico) {
        this.espacio_publico = espacio_publico;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getTipo_suelo() {
        return tipo_suelo;
    }

    public void setTipo_suelo(String tipo_suelo) {
        this.tipo_suelo = tipo_suelo;
    }

    public String getTipo_acceso() {
        return tipo_acceso;
    }

    public void setTipo_acceso(String tipo_acceso) {
        this.tipo_acceso = tipo_acceso;
    }

    @Override
    public String toString() {
        return "Zona{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", espacio_publico='" + espacio_publico + '\'' +
                ", cp=" + cp +
                ", tipo_suelo='" + tipo_suelo + '\'' +
                ", tipo_acceso='" + tipo_acceso + '\'' +
                ", aforo_actual=" + aforo_actual +
                ", aforo_maximo=" + aforo_maximo +
                '}';
    }
}
