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
            jdbcTemplate.update("INSERT INTO reserva VALUES (?,?,?,?,?,?)",
                   reserva.getId(),reserva.getDniCiudadano(),reserva.getFranjaEspacio(),reserva.getEspacio_publico(),reserva.getEstado_reserva(),reserva.getZona());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }


    //Datos reserva
    public List<Reserva> getReservas (){
        try{
            return jdbcTemplate.query("SELECT * FROM rerserva ", new ReservarRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    
    //ACTUALIZAMOS reserva
    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE reserva SET dni_ciudadano=?,franja_espacio=?,espacio_publico=?,estado_reserva=?,zona=? WHERE id=?",
                reserva.getDniCiudadano(),reserva.getFranjaEspacio(),
                reserva.getEspacio_publico(),reserva.getEstado_reserva(),
                reserva.getZona(),reserva.getId());

    }





}
