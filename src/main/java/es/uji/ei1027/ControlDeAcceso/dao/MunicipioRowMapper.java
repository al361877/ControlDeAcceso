package es.uji.ei1027.ControlDeAcceso.dao;

import es.uji.ei1027.ControlDeAcceso.model.Municipio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MunicipioRowMapper implements RowMapper<Municipio> {
    public Municipio mapRow(ResultSet rs, int RowNum) throws SQLException {
        Municipio municipio = new Municipio();
        municipio.setId(rs.getString("id_municipio"));
        municipio.setNombre(rs.getString("nombre"));
        municipio.setCoordenadas(rs.getString("coordenadas"));
        municipio.setHabitantes(rs.getInt("habitantes"));
        return municipio;

    }
}


