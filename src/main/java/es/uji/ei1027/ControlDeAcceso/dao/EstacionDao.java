package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Estacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class EstacionDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS estacion
    public void addEstacion(Estacion estacion) {
        try {
            jdbcTemplate.update("INSERT INTO estacion VALUES (?,?,?)",
                    estacion.getId(),estacion.getFechaIni(),estacion.getFechaFin());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }


    //Datos estacion
    public List<Estacion> getEstacion (){
        try{
            return jdbcTemplate.query("SELECT * FROM estacion ", new EstacionRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }



    //ACTUALIZAMOS estacion
    public void updateEstacion(Estacion estacion) {
        jdbcTemplate.update("UPDATE estacion SET fechaini=?,fechafin=? WHERE id_estacion=?",
                estacion.getFechaIni(),estacion.getFechaFin(),
                estacion.getId());

    }





}
