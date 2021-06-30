package es.uji.ei1027.ControlDeAcceso.services;

import java.util.List;
import java.util.Map;

import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import es.uji.ei1027.ControlDeAcceso.model.Gestor;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspacioSvc implements EspacioService{

    @Autowired
    EspacioPublicoDao espacioDao;

    @Autowired
    UsuarioDao usuarioDao;


 /*   @Override
    public Map<String, List<EspacioPublico>> listEspaciosPorMunicipio(String municipio) {
        List<EspacioPublico> espacios = espacioDao.getEspaciosPorMunicipio(municipio);

        return null;
    }
   */

   //Le paso un Usuario que sea gestor y me devuelve todos los espacios de ese gestor;
    @Override
    public List<EspacioPublico> listEspaciosPorGestor(Usuario user){
        Gestor gestor=new Gestor();
        gestor=usuarioDao.getGestorByDni(user.getDni());
        return espacioDao.getEspaciosPorMunicipio(gestor.getMunicipio());
    }
}
