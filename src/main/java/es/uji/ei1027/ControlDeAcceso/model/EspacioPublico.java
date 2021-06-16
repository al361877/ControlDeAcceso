package es.uji.ei1027.ControlDeAcceso.model;

public class EspacioPublico {

    private String id;
    private String municipio;
    private String tipo_espacio;
    private int cp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTipo_espacio() {
        return tipo_espacio;
    }

    public void setTipo_espacio(String tipo_espacio) {
        this.tipo_espacio = tipo_espacio;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "EspacioPublico{" +
                "id='" + id + '\'' +
                ", municipio='" + municipio + '\'' +
                ", tipo_espacio='" + tipo_espacio + '\'' +
                ", cp=" + cp +

                '}';
    }
}
