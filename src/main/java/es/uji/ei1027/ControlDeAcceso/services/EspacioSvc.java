package es.uji.ei1027.ControlDeAcceso.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.Controlador;
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


    //Paso el dni del controlador para sacar la lista de los espacios que vigila
    @Override
    public List<EspacioPublico> listEspaciosPorControlador(String dni) {
        //Lista del mismo controlador junto a los datos de cada vigilancia
        List<Controlador> controlador_esp = usuarioDao.getControladorByDni(dni);

        System.out.println("La lista del controlador es: " + controlador_esp);

        //Lista de espacios
        List<EspacioPublico> espacios = new ArrayList<EspacioPublico>();

        //por cada controlador saco el espacio público y lo añado a la lista de espacios
        for(int i=0; i<controlador_esp.size(); i++) {
            espacios.add(espacioDao.getEspacio(controlador_esp.get(i).getEspacio_publico()));
            System.out.println("El espacio " + i + " es: ");
        }

        return espacios;
    }


   //Le paso un Usuario que sea gestor y me devuelve todos los espacios de ese gestor;
    @Override
    public List<EspacioPublico> listEspaciosPorGestor(Usuario user){
        Gestor gestor=new Gestor();
        gestor=usuarioDao.getGestorByDni(user.getDni());
        return espacioDao.getEspaciosPorMunicipio(gestor.getMunicipio());
    }
}
