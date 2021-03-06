package es.uji.ei1027.ControlDeAcceso.dao;

import es.uji.ei1027.ControlDeAcceso.model.Controlador;
import es.uji.ei1027.ControlDeAcceso.model.Gestor;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class UsuarioDao {
    private JdbcTemplate jdbcTemplate;

        //Obtenermos el jbcTemplate a partir del Data Source
        @Autowired
        public void setDataSource(DataSource dataSource) {
            jdbcTemplate=new JdbcTemplate(dataSource);
        }

        //AÑADIMOS Ciudadano

        public void addCiudadano(Usuario usuario) {
//            System.out.println(usuario.toString());
            try {

                jdbcTemplate.update("INSERT INTO Usuario VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                        usuario.getDni(), usuario.getUsuario(),usuario.getNombre(), usuario.getTelefono(),
                        usuario.getEmail(),usuario.getContraseña(), usuario.getNacimiento(),
                        usuario.getCiudad(),usuario.getCalle(),usuario.getCp(), "Ciudadano");
            } catch (EmptyResultDataAccessException e){
                return;
            }
        }

        //AÑADIMOS ciudadano
        public void addCiudadano(String dni) {
            jdbcTemplate.update("INSERT INTO Ciudadano VALUES (?)",dni);
        }


    //AÑADIMOS Ciudadano

    public void addControlador(Usuario usuario) {
//        System.out.println(usuario.toString());
        try {

            jdbcTemplate.update("INSERT INTO Usuario VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                    usuario.getDni(), usuario.getUsuario(),usuario.getNombre(), usuario.getTelefono(),
                    usuario.getEmail(),usuario.getContraseña(), usuario.getNacimiento(),
                    usuario.getCiudad(),usuario.getCalle(),usuario.getCp(), "Controlador");
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }

    //AÑADIMOS ciudadano
    public void addControlador(String dni) {
        jdbcTemplate.update("INSERT INTO Ciudadano VALUES (?)",dni);
    }


        //Datos ciudadanos
        public List<Usuario> getCiudadanos (){
            try{
                return jdbcTemplate.query("SELECT * FROM usuario WHERE tipo_usuario='Ciudadano'", new UsuarioRowMapper());
            }
            catch (EmptyResultDataAccessException e){
                return null;
            }
        }
    //Datos ciudadanos
    public List<Usuario> getAllUsers (){
        try{
            return jdbcTemplate.query("SELECT * FROM usuario ", new UsuarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

        //AÑADIMOS Controlador
        public void addControlador(String dni, String espacio_publico) {
            jdbcTemplate.update("INSERT INTO Controlador VALUES (?,?)",
                    dni, espacio_publico);
        }

        //ACTUALIZAMOS Controlador
        public void updateControlador(Controlador controlador) {
            jdbcTemplate.update("UPDATE Controlador SET espacio_publico=? WHERE dni=?",
                    controlador.getEspacio_publico(), controlador.getDni());

        }

        //Datos usuarios controlador
        public List<Usuario> getUsuariosControlador (){
            try{
                return jdbcTemplate.query("SELECT * FROM usuario WHERE tipo_usuario='Controlador'", new UsuarioRowMapper());
            }
            catch (EmptyResultDataAccessException e){
                return null;
            }
        }

    public List<Controlador> getControladores (){
        try{
            return jdbcTemplate.query("SELECT * FROM controlador", new ControladorRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //Get Para poder sacar los espacios que tiene asignados para vigilar un controlador
    public List<Controlador> getControladorByDni(String dni){
        try{
            System.out.println("Estas en el if del controlador dao: " + dni);
            return jdbcTemplate.query("SELECT * FROM controlador WHERE dni=?", new ControladorRowMapper(), dni);

        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    public List<Controlador> getControladorByEspacio(String idEspacio){
        try{
            return jdbcTemplate.query("SELECT * FROM controlador WHERE id_espacio=?", new ControladorRowMapper(), idEspacio);

        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }



        //Get getsor
        public Gestor getGestorByDni(String dni){
            try{
                return jdbcTemplate.queryForObject("SELECT * FROM gestor WHERE dni=?", new GestorRowMapper(), dni);
            }
            catch (EmptyResultDataAccessException e){
                return null;
            }
        }
        //AÑADIMOS gestor
        public void addGestor(String dni, String municipio) {
            jdbcTemplate.update("INSERT INTO Gestor VALUES (?,?)",
                    dni, municipio);
        }
        //ACTUALIZAMOS gestor
        public void updateGestor(Gestor gestor) {
            jdbcTemplate.update("UPDATE gestor SET municipio=? WHERE dni=?",
                    gestor.getMunicipio(),gestor.getDni());

        }
        //Datos gestores
        public List<Usuario> getGestores (){
            try{
                return jdbcTemplate.query("SELECT * FROM usuario WHERE tipo_usuario='gestor'", new UsuarioRowMapper());
            }
            catch (EmptyResultDataAccessException e){
                return null;
            }
        }

    //OCULTAMOS Usuario
    public void cifrarPassword(String dni,String pass) {
        jdbcTemplate.update("UPDATE Usuario SET contraseña=? WHERE dni=?", pass, dni);
    }


    //OCULTAMOS Usuario
    public void deleteUsuario(String dni) {
        jdbcTemplate.update("UPDATE Usuario SET tipo_usuario='Borrado' WHERE dni=?", dni);
    }



        //ACTUALIZAMOS Usuario
        public void updateUsuario(Usuario usuario){
//            System.out.println("entro en el update");
            usuario.setNacimiento(usuario.getNacimientoString());
//            System.out.println(usuario.toString());

            jdbcTemplate.update(" UPDATE Usuario SET  nombre_y_apellidos=?," +
                            " telefono=?, nacimiento=?, email=?, ciudad=?,calle=?, cp=? " +
                            " WHERE dni=?",
                    usuario.getNombre(),usuario.getTelefono(),
                    usuario.getNacimiento(),usuario.getEmail(),usuario.getCiudad(),usuario.getCalle(),usuario.getCp(),
                    usuario.getDni());
        }
        public Usuario getUsuario (String usuario ){
            try{
//                System.out.println("entro con usuario = "+usuario);
                return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE nombre_usuario=?", new UsuarioRowMapper(), usuario);
            }
            catch (EmptyResultDataAccessException e){
                return null;
            }
        }
    public Usuario getUsuarioConPass (String usuario,String pass ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE nombre_usuario=? and contraseña=?", new UsuarioRowMapper(), usuario,pass);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
        public Usuario getUsuarioDni (String dni ){
            try{
                return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE dni=?", new UsuarioRowMapper(), dni);
            }
            catch (EmptyResultDataAccessException e){
                return null;
            }
        }






}
