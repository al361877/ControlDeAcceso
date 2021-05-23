package es.uji.ei1027.ControlDeAcceso.dao;

import es.uji.ei1027.ControlDeAcceso.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class ZonaDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS zona
    public void addZona(Zona zona) {
        try {
            jdbcTemplate.update("INSERT INTO zona VALUES (?,?,?,?,?,?)",
                    zona.getId(),zona.getNombre(),zona.getEspacio_publico(),zona.getCp(),zona.getTipo_suelo(),zona.getTipo_acceso());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }

    //Datos zona
    public List<Zona> getZonas (){
        try{
            return jdbcTemplate.query("SELECT * FROM zona ", new ZonaRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Zona getZonaId (String id){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM zona WHERE id=?", new ZonaRowMapper(), id);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //ACTUALIZAMOS zona
    public void updateZona(Zona zona) {
        jdbcTemplate.update("UPDATE zona SET nombre=?,espacio_publico=?,cp=?,tipo_suelo=?,tipo_acceso=? WHERE id=?",
                zona.getNombre(),zona.getEspacio_publico(),
                zona.getCp(),zona.getTipo_suelo(),
                zona.getTipo_acceso(),zona.getId());

    }


    /*
    public void deleteReserva(String id){

        jdbcTemplate.delete()
    }
 */

}
