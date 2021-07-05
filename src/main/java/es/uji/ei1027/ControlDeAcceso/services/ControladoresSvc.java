package es.uji.ei1027.ControlDeAcceso.services;

import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;

import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ControladoresSvc implements ControladoresService{

    @Autowired
    EspacioPublicoDao espacioDao;

    @Autowired
    UsuarioDao controladorDao;

    @Override
    public List<Controlador> listControladoresPorMunicipio(String idMunicipio){
        //lista de espacios de ese municipio
        List<EspacioPublico> espacios = espacioDao.getEspaciosPorMunicipio(idMunicipio);


        //controladores que trabajan en el municipio dado
        List<Controlador> contDelMunicipio=new ArrayList<>();
        List<String> idConDelMunicipio=new ArrayList<>();

        List<Controlador> controladoresDelEspacio=new ArrayList<>();

        //compruebo para cada espacio del municipio obtenido de qué controladores tiene
        for(EspacioPublico espacio:espacios){
            controladoresDelEspacio=controladorDao.getControladorByEspacio(espacio.getId());

            //de cada lista de controladores devuelta de ese espacio
            for(Controlador controlador:controladoresDelEspacio){
                //miro que no se repita el dni del controlador para mostrarlo solo una vez
                if(!idConDelMunicipio.contains(controlador.getDni())){
                    contDelMunicipio.add(controlador);
                    idConDelMunicipio.add(controlador.getDni());
                }
            }
        }

        return contDelMunicipio;
    }


}
