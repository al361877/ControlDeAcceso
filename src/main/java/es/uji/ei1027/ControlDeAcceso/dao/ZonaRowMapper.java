package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Zona;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZonaRowMapper implements RowMapper<Zona> {
    public Zona mapRow(ResultSet rs, int RowNum) throws SQLException {
        Zona zona = new Zona();
        zona.setId(rs.getString("id_zona"));
        zona.setNombre(rs.getString("nombre"));
        zona.setEspacio_publico(rs.getString("id_espacio"));
        zona.setCp(rs.getInt("cp"));
        zona.setTipo_suelo(rs.getString("tipo_suelo"));
        zona.setTipo_acceso(rs.getString("tipo_acceso"));
        zona.setAforo_actual(rs.getInt("aforo_actual"));
        zona.setAforo_maximo(rs.getInt("aforo_maximo"));
        return zona;
    }
}
