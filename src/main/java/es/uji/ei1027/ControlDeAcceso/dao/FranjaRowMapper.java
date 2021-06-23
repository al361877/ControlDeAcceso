package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import es.uji.ei1027.ControlDeAcceso.model.FranjaEspacio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class FranjaRowMapper implements RowMapper<FranjaEspacio> {
    public FranjaEspacio mapRow(ResultSet rs, int RowNum) throws SQLException{
        FranjaEspacio franja = new FranjaEspacio();
        franja.setId(rs.getString("id_franja"));
        franja.setHoraIniRow(rs.getTime("horaini"));
        franja.setHoraFinRow(rs.getTime("horafin"));

        return franja;

    }
}