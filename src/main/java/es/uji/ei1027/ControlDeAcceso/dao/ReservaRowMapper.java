package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class ReservaRowMapper implements RowMapper<Reserva> {
    public Reserva mapRow(ResultSet rs, int RowNum) throws SQLException{
        Reserva reserva = new Reserva();
        reserva.setId(rs.getString("id"));
        reserva.setDniCiudadano(rs.getString("dniCiudadano"));
        reserva.setFranjaEspacio(rs.getString("franja_espacio"));
        reserva.setEspacio_publico(rs.getString("espacio_publico"));
        reserva.setEstado_reserva(rs.getString("estado_reserva"));
        reserva.setZona(rs.getString("zona"));
        return reserva;

    }
}