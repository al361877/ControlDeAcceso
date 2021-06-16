package es.uji.ei1027.ControlDeAcceso.model;

import java.time.LocalDate;

public class Usuario {
    private String nombre;
    private String dni;
    private String usuario;
    private String contraseña;
    private String email;
    private String ciudad;
    private String telefono;
    private String calle;
    private String tipoUsuario;
    private int cp;

    private String nacimientoString;

    private LocalDate nacimientoDate;


    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }


    public LocalDate getNacimiento(){
        nacimientoDate = LocalDate.parse(nacimientoString);
        return this.nacimientoDate;
    }
//    public String getNacimiento(){
//
//        return this.nacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//    }


    public String getNacimientoString(){

        return this.nacimientoString;
    }

    public  void setNacimientoString(String nacimiento){
        this.nacimientoString=nacimiento;
    }
    public void setNacimiento(String nacimiento) {
        this.nacimientoString=nacimiento;
        LocalDate localDate1 = LocalDate.parse(nacimiento);
        this.nacimientoDate = localDate1;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", calle='" + calle + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", cp=" + cp +
                ", nacimiento=" + nacimientoDate +
                '}';
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}