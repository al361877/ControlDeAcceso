package es.uji.ei1027.ControlDeAcceso.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioRowMapper;
import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("espacios")
public class EspaciosController {
    private EspacioPublicoDao espacioPublicoDao;


    @Autowired
    public void setEspacios(EspacioPublicoDao espacioPublicoDao) {
        this.espacioPublicoDao = espacioPublicoDao;
    }

    @RequestMapping("/list")
    public String listEspacios( Model model) {

        List<EspacioPublico> lista = espacioPublicoDao.getEspacios();
        int cont=0;


        List<EspacioPublico> listaInterior=new ArrayList<>();
        List<List<EspacioPublico>> matriz=new ArrayList<>();
        EspacioPublico espacio;

        for(int i=0;i<lista.size();i++) {
            espacio=lista.get(i);

            if (cont < 3) {

                listaInterior.add(espacio);
            }else{
                matriz.add(listaInterior);
                listaInterior=new ArrayList<>();
                listaInterior.add(espacio);
                cont=-1;
            }

            if(i==lista.size()-1) {
                matriz.add(listaInterior);
                listaInterior=new ArrayList<>();
            }
            cont++;
        }


        model.addAttribute("matrizEspacios", matriz);

        return "espacios/list.html";
    }

    @RequestMapping(value="/listPorMunicipio", method = RequestMethod.POST)
    public String listEspaciosPorMunicipio(@ModelAttribute("municipio") String municipio, Model model) {

        List<EspacioPublico> lista;
        if(municipio.equals("Ninguno")){
             lista = espacioPublicoDao.getEspacios();
        }else{
             lista = espacioPublicoDao.getEspaciosPorMunicipio(municipio);

        }
        int cont=0;


        List<EspacioPublico> listaInterior=new ArrayList<>();
        List<List<EspacioPublico>> matriz=new ArrayList<>();
        EspacioPublico espacio;

        for(int i=0;i<lista.size();i++) {
            espacio=lista.get(i);

            if (cont < 3) {

                listaInterior.add(espacio);
            }else{
                matriz.add(listaInterior);
                listaInterior=new ArrayList<>();
                listaInterior.add(espacio);
                cont=-1;
            }

            if(i==lista.size()-1) {
                matriz.add(listaInterior);
                listaInterior=new ArrayList<>();
            }
            cont++;
        }


        model.addAttribute("matrizEspacios", matriz);

        return "espacios/list.html";
    }
    //llamada mediante el botón de la página de la lista hacia la página de añadir una reserva
    @RequestMapping(value="/prereserva/{espacio}", method = RequestMethod.GET)
    public String prereserva(Model model, @PathVariable String espacio) {


        model.addAttribute("espacio", espacioPublicoDao.getEspacio(espacio));
        return "espacios/prereserva.html";
    }

}

