package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.FranjaEspacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class FranjaEspacioDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS franja
    public void addFranja(FranjaEspacio franja) {
        try {
            jdbcTemplate.update("INSERT INTO franjaespacio VALUES (?,?,?,?,?)",
                    franja.getId(),franja.getFechaIni(),franja.getFechaFin(),franja.getHoraFin(),franja.getHoraFin());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }


    //Datos franja
    public List<FranjaEspacio> getFranjas (){
        try{
            return jdbcTemplate.query("SELECT * FROM franjaespacio ", new FranjaEspacioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }



    //ACTUALIZAMOS franja
    public void updateFranja(FranjaEspacio franjaEspacio) {
        jdbcTemplate.update("UPDATE franjaespacio SET fechaini=?,fechafin=?,horaini=?,horafin=? WHERE id=?",
                franjaEspacio.getFechaIni(),franjaEspacio.getFechaFin(),
                franjaEspacio.getHoraIni(),franjaEspacio.getHoraFin(),
                franjaEspacio.getId());

    }





}
