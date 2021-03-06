package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Estacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class EstacionRowMapper implements RowMapper<Estacion> {
    public Estacion mapRow(ResultSet rs, int RowNum) throws SQLException{
        Estacion estacion = new Estacion();
        estacion.setId(rs.getString("id_estacion"));
        estacion.setFechaIni(rs.getString("fechaini"));
        estacion.setFechaFin(rs.getString("fechafin"));
        return estacion;

    }
}