package es.uji.ei1027.ControlDeAcceso.dao;

import es.uji.ei1027.ControlDeAcceso.model.FranjaEspacio;
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
            jdbcTemplate.update("INSERT INTO zona VALUES (?,?,?,?,?,?,?,?)",
                    zona.getId(),zona.getNombre(),zona.getEspacio_publico(),zona.getCp(),zona.getTipo_suelo(),zona.getTipo_acceso(),zona.getAforo_actual(), zona.getAforo_maximo());
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
    //Datos zona
    public List<Zona> getZonasDisponiblesPorEspacio (String espacio){
        try{
            List<Zona> lista =  jdbcTemplate.query("select z.* from espaciopublico as e right join zona as z using (id_espacio) where id_espacio=? and tipo_acceso='abierto'", new ZonaRowMapper(),espacio);
            return lista;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Zona> getZonasByEspacio (String espacio){
        try{
            System.out.println("Estás en el dao de zona : " + espacio);
            List<Zona> lista = jdbcTemplate.query("SELECT * FROM zona WHERE id_espacio=?", new ZonaRowMapper(), espacio);

            return lista;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Zona getZonaId (String id){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM zona WHERE id_zona=?", new ZonaRowMapper(), id);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //ACTUALIZAMOS zona
    public void updateZona(Zona zona) {
        jdbcTemplate.update("UPDATE zona SET nombre=?,tipo_suelo=?,tipo_acceso=?, aforo_actual=?, aforo_maximo=? WHERE id_zona=?",
                zona.getNombre(),zona.getTipo_suelo(), zona.getTipo_acceso(),
                zona.getAforo_actual(), zona.getAforo_maximo(), zona.getId());

    }

    //ACTUALIZAMOS zona
    public void deleteZona(String id_zona) {
        jdbcTemplate.update("DELETE FROM zona WHERE id_zona=?", id_zona);
    }

}
