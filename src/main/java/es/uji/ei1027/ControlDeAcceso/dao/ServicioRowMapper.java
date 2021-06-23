package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Servicio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class ServicioRowMapper implements RowMapper<Servicio> {
    public Servicio mapRow(ResultSet rs, int RowNum) throws SQLException{
        Servicio servicio = new Servicio();
        servicio.setId(rs.getString("id_servicio"));
        servicio.setTipo_servicio(rs.getString("tipo_servicio"));
        servicio.setEstacion(rs.getString("id_estacion"));
        servicio.setIdEspacio(rs.getString("id_espacio"));

        return servicio;

    }
}