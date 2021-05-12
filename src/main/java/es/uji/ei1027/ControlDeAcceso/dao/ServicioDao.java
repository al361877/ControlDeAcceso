package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import es.uji.ei1027.ControlDeAcceso.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class ServicioDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS servicio
    public void addServicio(Servicio servicio) {
        try {
            jdbcTemplate.update("INSERT INTO servicio VALUES (?,?,?)",
                    servicio.getId(),servicio.getTipo_servicio(),servicio.getEstacion());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }


    //Datos servicio
    public List<Reserva> getServicios (){
        try{
            return jdbcTemplate.query("SELECT * FROM servicio ", new ReservarRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }



    //ACTUALIZAMOS servicio
    public void updateServicio(Servicio servicio) {
        jdbcTemplate.update("UPDATE servicio SET tipo_servicio=?,estacion=? WHERE id=?",
                servicio.getTipo_servicio(),servicio.getEstacion(),
                servicio.getId());

    }





}
