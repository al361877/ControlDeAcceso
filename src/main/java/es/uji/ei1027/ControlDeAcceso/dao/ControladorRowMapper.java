package es.uji.ei1027.ControlDeAcceso.dao;

import es.uji.ei1027.ControlDeAcceso.model.Controlador;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorRowMapper implements RowMapper<Controlador> {
    public Controlador mapRow(ResultSet rs, int RowNum) throws SQLException {
        Controlador controlador = new Controlador();
        controlador.setDni(rs.getString("dni"));
        controlador.setEspacio_publico(rs.getString("id_espacio"));
        controlador.setDiasSemana(rs.getString("dias_semana_trabaja"));

        return controlador;
    }
}
