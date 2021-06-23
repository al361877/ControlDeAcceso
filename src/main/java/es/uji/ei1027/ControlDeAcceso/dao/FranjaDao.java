package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Estacion;
import es.uji.ei1027.ControlDeAcceso.model.FranjaEspacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class FranjaDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS estacion
    public void addFranja(FranjaEspacio franjaEspacio) {
        try {
            jdbcTemplate.update("INSERT INTO franjaespacio VALUES (?,?,?)",
                    franjaEspacio.getId(),franjaEspacio.getHoraIni(),franjaEspacio.getHoraFin());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }


    //Datos franja
    public List<FranjaEspacio> getFranjas(){
        try{
            return jdbcTemplate.query("SELECT * FROM franjaespacio ", new FranjaRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //Datos franja
    public FranjaEspacio getFranja(String id){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM franjaespacio where id_franja=? ", new FranjaRowMapper(),id);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }



    //ACTUALIZAMOS franja
    public void updateFranja(FranjaEspacio franjaEspacio) {
        jdbcTemplate.update("UPDATE franjaespacio SET horaini=?,horafin=? WHERE id_franja=?",
                franjaEspacio.getHoraIni(),franjaEspacio.getHoraFin(),
                franjaEspacio.getId());

    }
    //ACTUALIZAMOS franja
    public void deleteFranja(FranjaEspacio franjaEspacio) {
        jdbcTemplate.update("DELETE franjaespacio WHERE id_franja=?",
                franjaEspacio.getId());

    }




}
