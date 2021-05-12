package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class EspacioPublicoDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS espacio publico
    public void addEspacio(EspacioPublico espacioPublico) {
        try {
            jdbcTemplate.update("INSERT INTO EspacioPublico VALUES (?,?,?,?)",
                   espacioPublico.getId(),espacioPublico.getMunicipio(),espacioPublico.getTipo_espacio(),espacioPublico.getCp());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }


    //Datos espacios
    public List<EspacioPublico> getEspacios (){
        try{
            return jdbcTemplate.query("SELECT * FROM espaciopublico ", new EspacioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }




    //ACTUALIZAMOS espacio
    public void updateEspacio(EspacioPublico espacioPublico) {
        jdbcTemplate.update("UPDATE espaciopublico SET municipio=?,tipo_espacio=? WHERE id=?",
                espacioPublico.getMunicipio(),espacioPublico.getTipo_espacio(),espacioPublico.getId());

    }





}
