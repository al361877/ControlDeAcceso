package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class ReservaRowMapper implements RowMapper<Reserva> {
    public Reserva mapRow(ResultSet rs, int RowNum) throws SQLException{
        Reserva reserva = new Reserva();
        reserva.setId(rs.getString("id_reserva"));
        reserva.setDniCiudadano(rs.getString("dni_ciudadano"));
        reserva.setFranja(rs.getString("id_franja"));
        reserva.setEspacio_publico(rs.getString("id_espacio"));
        reserva.setEstado_reserva(rs.getString("estado_reserva"));
        reserva.setFechaIni(rs.getString("fechaini"));
        reserva.setNumPersonas(rs.getInt("numpersonas"));
        return reserva;

    }
}