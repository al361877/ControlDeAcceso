package es.uji.ei1027.ControlDeAcceso.services;

import es.uji.ei1027.ControlDeAcceso.model.Controlador;
import java.util.List;


public interface ControladoresService {
    public List<Controlador> listControladoresPorMunicipio(String idMunicipio);

}
