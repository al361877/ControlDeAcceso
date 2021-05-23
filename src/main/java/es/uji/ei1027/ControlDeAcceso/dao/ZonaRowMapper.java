package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Zona;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZonaRowMapper implements RowMapper<Zona> {
    public Zona mapRow(ResultSet rs, int RowNum) throws SQLException {
        Zona zona = new Zona();
        zona.setId(rs.getString("id"));
        zona.setNombre(rs.getString("nombre"));
        zona.setEspacio_publico(rs.getString("espacio_publico"));
        zona.setCp(rs.getInt("cp"));
        zona.setTipo_suelo(rs.getString("tipo_suelo"));
        zona.setTipo_acceso(rs.getString("tipo_acceso"));
        return zona;
    }
}
