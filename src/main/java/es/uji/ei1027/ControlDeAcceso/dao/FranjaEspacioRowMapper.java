package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.FranjaEspacio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class FranjaEspacioRowMapper implements RowMapper<FranjaEspacio> {
    public FranjaEspacio mapRow(ResultSet rs, int RowNum) throws SQLException{
        FranjaEspacio franjaEspacio = new FranjaEspacio();
        franjaEspacio.setId(rs.getString("id"));
        franjaEspacio.setFechaFin(rs.getDate("fechaini"));
        franjaEspacio.setFechaFin(rs.getDate("fechafin"));
        franjaEspacio.setHoraIni(rs.getTime("horaini"));
        franjaEspacio.setHoraFin(rs.getTime("horafin"));
        return franjaEspacio;

    }
}