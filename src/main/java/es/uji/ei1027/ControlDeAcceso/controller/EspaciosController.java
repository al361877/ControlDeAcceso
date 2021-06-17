package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;
import es.uji.ei1027.ControlDeAcceso.dao.ZonaDao;
import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import es.uji.ei1027.ControlDeAcceso.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private ZonaDao zonaDao;

    @Autowired
    public void setEspacios(EspacioPublicoDao espacioPublicoDao) {
        this.espacioPublicoDao = espacioPublicoDao;
    }
    @Autowired
    public void setZona(ZonaDao zonaDao) {

        this.zonaDao = zonaDao;
    }

    @RequestMapping("/list")
    public String listEspacios( Model model) {

        List<EspacioPublico> lista = espacioPublicoDao.getEspacios();
        int cont=0;

        List<String> municipios = new ArrayList<>();
        municipios.add("Ninguno");
        List<EspacioPublico> listaInterior=new ArrayList<>();
        List<List<EspacioPublico>> matriz=new ArrayList<>();
        EspacioPublico espacio;

        for(int i=0;i<lista.size();i++) {
            espacio=lista.get(i);

           if(!municipios.contains(espacio.getMunicipio())){
               municipios.add(espacio.getMunicipio());
           }

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


        model.addAttribute("municipios",municipios);
        model.addAttribute("matrizEspacios", matriz);

        return "espacios/list.html";
    }

    @RequestMapping(value="/listPorMunicipio", method = RequestMethod.POST)
    public String listEspaciosPorMunicipio(@ModelAttribute("municipio") String municipio, Model model) {

        List<EspacioPublico> lista;
        List<EspacioPublico> listaTodosEspacios = espacioPublicoDao.getEspacios();
        List<String> municipios = new ArrayList<>();
        municipios.add("Ninguno");

        for(EspacioPublico esp:listaTodosEspacios){
            if(!municipios.contains(esp.getMunicipio())){
                municipios.add(esp.getMunicipio());
            }
        }

        if(municipio.equals("Ninguno")){
             lista = listaTodosEspacios;
        }else{
             lista = espacioPublicoDao.getEspaciosPorMunicipio(municipio);

        }
        int cont=0;


        List<EspacioPublico> listaInterior=new ArrayList<>();
        List<List<EspacioPublico>> matriz=new ArrayList<>();
        EspacioPublico espacio;



        for(int i=0;i<lista.size();i++) {
            espacio=lista.get(i);
            if(!municipios.contains(espacio.getMunicipio())){
                municipios.add(espacio.getMunicipio());
            }

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

        model.addAttribute("municipios",municipios);
        model.addAttribute("matrizEspacios", matriz);

        return "espacios/list.html";
    }



        //llamada mediante el botón de la página de la lista hacia la página de añadir una reserva
    @RequestMapping(value="/prereserva/{espacio}", method = RequestMethod.GET)
    public String prereserva(Model model, @PathVariable String espacio, HttpSession session) {

        Usuario user = (Usuario) session.getAttribute("user");

        model.addAttribute("espacio", espacioPublicoDao.getEspacio(espacio));


        try {
            if (user.getTipoUsuario().equals("Ciudadano")) {
                System.out.println("HOLA");
                List<Zona> lista = zonaDao.getZonasDisponiblesPorEspacio(espacio);
                List<String> listaZonasString = new ArrayList<>();
                for (Zona zona : lista) {
                    System.out.println(zona.toString());
                    listaZonasString.add(zona.getId());
                }
                int cont = 0;


                List<Zona> listaInterior = new ArrayList<>();
                List<List<Zona>> matriz = new ArrayList<>();
                Zona zona;

                for (int i = 0; i < lista.size(); i++) {
                    zona = lista.get(i);

                    if (cont < 3) {

                        listaInterior.add(zona);
                    } else {
                        matriz.add(listaInterior);
                        listaInterior = new ArrayList<>();
                        listaInterior.add(zona);
                        cont = -1;
                    }

                    if (i == lista.size() - 1) {
                        matriz.add(listaInterior);
                        listaInterior = new ArrayList<>();
                    }
                    cont++;
                }

                model.addAttribute("zonasL", listaZonasString);
                model.addAttribute("matrizZonas", matriz);
                boolean disponibilidad;

                disponibilidad= !listaZonasString.isEmpty();

                model.addAttribute("disponibilidad",disponibilidad);
                return "espacios/prereserva.html";
            }

        } catch (Exception e) {

            return "/espacios/prereservaSinLogin";
        }
        return "/espacios/prereservaSinLogin";

    }



}

