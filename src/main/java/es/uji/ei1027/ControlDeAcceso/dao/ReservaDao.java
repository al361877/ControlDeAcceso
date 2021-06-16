package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class ReservaDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS reserva
    public void addReserva(Reserva reserva) {
        try {
            jdbcTemplate.update("INSERT INTO reserva VALUES (?,?,?,?,?,?,?,?,?)",
                   reserva.getId(),reserva.getDniCiudadano(),reserva.getEspacio_publico(),reserva.getEstado_reserva(),reserva.getZona(),reserva.getFechaIni(),reserva.getFechaFin(),reserva.getHoraIni(),reserva.getHoraFin());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }


    //Datos reserva
    public List<Reserva> getReservas (){
        try{
            return jdbcTemplate.query("SELECT * FROM rerserva ", new ReservaRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Reserva getReservaId (String id){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM reserva WHERE id=?", new ReservaRowMapper(), id);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Reserva> getReservasByDni(String dni){
        try{
            System.out.println("Estas en la Lista de reservas");
            List<Reserva> lista =
                    (List<Reserva>) jdbcTemplate.query("SELECT * FROM reserva WHERE dni_ciudadano=?", new ReservaRowMapper(),dni);

            for(Reserva res:lista){
                System.out.println(res.toString());
            }

            return lista;
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }
    public List<Reserva> getReservasPendientesByDni(String dni){
        try{

            List<Reserva> lista =
                    (List<Reserva>) jdbcTemplate.query("SELECT * FROM reserva WHERE dni_ciudadano=? and estado_reserva='pendienteDeUso'", new ReservaRowMapper(),dni);



            return lista;
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    //ACTUALIZAMOS reserva
    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE reserva SET dni_ciudadano=?,franja_espacio=?,espacio_publico=?,estado_reserva=?,zona=? WHERE id=?",
                reserva.getDniCiudadano(),
                reserva.getEspacio_publico(),reserva.getEstado_reserva(),
                reserva.getZona(),reserva.getId());

    }




    public void canceladaPorUsuarioReserva(String id){


            jdbcTemplate.update("UPDATE reserva SET estado_reserva=?,zona=? WHERE id=?","canceladaU",id);
        }


    public void canceladaPorControladorReserva(String id){


        jdbcTemplate.update("UPDATE reserva SET estado_reserva=?,zona=? WHERE id=?","canceladaC",id);
    }
    public void finUsoReserva(String id){


        jdbcTemplate.update("UPDATE reserva SET estado_reserva=?,zona=? WHERE id=?","finUso",id);
    }


}
