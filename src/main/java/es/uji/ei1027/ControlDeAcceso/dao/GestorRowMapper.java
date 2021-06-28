package es.uji.ei1027.ControlDeAcceso.dao;

import es.uji.ei1027.ControlDeAcceso.model.Gestor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorRowMapper implements RowMapper<Gestor> {
    public Gestor mapRow(ResultSet rs, int RowNum) throws SQLException {
        Gestor usuario = new Gestor();
        usuario.setMunicipio(rs.getString("n_municipio"));
        usuario.setDni(rs.getString("dni"));


        return usuario;

    }
}
