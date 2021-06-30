package es.uji.ei1027.ControlDeAcceso.services;

import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import es.uji.ei1027.ControlDeAcceso.model.Gestor;
import java.util.Map;
import java.util.List;



public interface EspacioService {
   // public Map<String, List<EspacioPublico>> listEspaciosPorMunicipio(String municipio);

    public List<EspacioPublico> listEspaciosPorGestor(Usuario user);
}
