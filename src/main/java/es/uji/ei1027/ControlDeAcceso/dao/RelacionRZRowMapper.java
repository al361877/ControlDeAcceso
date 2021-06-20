package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.RelacionRZ;
import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class RelacionRZRowMapper implements RowMapper<RelacionRZ> {
    public RelacionRZ mapRow(ResultSet rs, int RowNum) throws SQLException{
        RelacionRZ relacionRZ= new RelacionRZ();
        relacionRZ.setId_reserva(rs.getString("id_reserva"));
        relacionRZ.setId_zona(rs.getString("id_zona"));
        return relacionRZ;

    }
}