package es.uji.ei1027.ControlDeAcceso.dao;


import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class EspacioRowMapper implements RowMapper<EspacioPublico> {
    public EspacioPublico mapRow(ResultSet rs, int RowNum) throws SQLException{
        EspacioPublico espacioPublico = new EspacioPublico();
        espacioPublico.setId(rs.getString("id_espacio"));
        espacioPublico.setMunicipio(rs.getString("n_municipio"));
        espacioPublico.setTipo_espacio(rs.getString("tipo_espacio"));
        espacioPublico.setCp(rs.getInt("cp"));

        return espacioPublico;

    }
}