package es.uji.ei1027.ControlDeAcceso.services;

import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.dao.ReservaDao;
import es.uji.ei1027.ControlDeAcceso.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaSvc implements ReservaService{

    @Autowired
    ReservaDao reservaDao;

    @Autowired
    UsuarioDao usuarioDao;

/*
    //Paso el dni del controlador para sacar la lista de los espacios que vigila
    @Override
    public List<Reserva> listReservaControlador(String dni) {
        //Lista del mismo controlador junto a los datos de cada vigilancia
        List<Controlador> controlador_esp = usuarioDao.getControladorByDni(dni);

        System.out.println("La lista del controlador es: " + controlador_esp);

        //Lista de espacios
        //List<Reserva> espacios = new ArrayList<EspacioPublico>();

        //por cada controlador saco el espacio público y lo añado a la lista de espacios
        for(int i=0; i<controlador_esp.size(); i++) {
           // espacios.add(espacioDao.getEspacio(controlador_esp.get(i).getEspacio_publico()));
            System.out.println("El espacio " + i + " es: ");
        }

        return null;
    }
*/

   //Le paso un Usuario que sea gestor y me devuelve todos los espacios de ese gestor;
    @Override
    public List<Reserva> listReservaGestor(String espacio){

        return reservaDao.getReservasByEspacio(espacio);
    }
}
