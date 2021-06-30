package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.*;
import es.uji.ei1027.ControlDeAcceso.model.*;
import es.uji.ei1027.ControlDeAcceso.services.EspacioService;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("espacios")
public class EspaciosController {
    private EspacioPublicoDao espacioPublicoDao;
    private ZonaDao zonaDao;
    private EstacionDao estacionDao;
    private ServicioDao servicioDao;
    private UsuarioDao usuarioDao;

    private EspacioService espacioService;

    @Autowired
    public void setEspacios(EspacioPublicoDao espacioPublicoDao) {
        this.espacioPublicoDao = espacioPublicoDao;
    }

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Autowired
    public void setEstacionDao(EstacionDao estacionDao) {
        this.estacionDao = estacionDao;
    }
    @Autowired
    public void setServicioDao(ServicioDao servicioDao) {
        this.servicioDao = servicioDao;
    }


    @Autowired
    public void setZona(ZonaDao zonaDao) {

        this.zonaDao = zonaDao;
    }

    @Autowired
    public void setEspacioService(EspacioService espacioService){
        this.espacioService = espacioService;
    }


    @RequestMapping("/listDelGestor")
    public String listEspaciosPorGestor(HttpSession session, Model model){
        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Gestor")){
                List<EspacioPublico> espacios = espacioService.listEspaciosPorGestor(user);

                model.addAttribute("espaciosC", espacios);

                return "espacios/listDelGestor.html";
            }
        }catch (Exception e){
            return "error/error";
        }

        return "error/error";
    }

    @RequestMapping("listDelControlador")
    public String listEspaciosPorControlador(HttpSession session, Model model){
        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Controlador")){
                List<EspacioPublico> espacios = espacioService.listEspaciosPorGestor(user);

                model.addAttribute("espacios", espacios);

                return "espacios/listDelControlador.html";
            }
        }catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }


    @RequestMapping("/list")
    public String listEspacios(Model model) {

        List<EspacioPublico> lista = espacioPublicoDao.getEspacios();
        int cont = 0;

        List<String> municipios = new ArrayList<>();
        municipios.add("Ninguno");
        List<EspacioPublico> listaInterior = new ArrayList<>();
        List<List<EspacioPublico>> matriz = new ArrayList<>();
        EspacioPublico espacio;

        for (int i = 0; i < lista.size(); i++) {
            espacio = lista.get(i);

            if (!municipios.contains(espacio.getMunicipio())) {
                municipios.add(espacio.getMunicipio());
            }

            if (cont < 3) {

                listaInterior.add(espacio);
            } else {
                matriz.add(listaInterior);
                listaInterior = new ArrayList<>();
                listaInterior.add(espacio);
                cont = -1;
            }

            if (i == lista.size() - 1) {
                matriz.add(listaInterior);
                listaInterior = new ArrayList<>();
            }
            cont++;
        }


        model.addAttribute("municipios", municipios);
        model.addAttribute("matrizEspacios", matriz);

        return "espacios/list.html";
    }

    @RequestMapping(value = "/listPorMunicipio", method = RequestMethod.POST)
    public String listEspaciosPorMunicipio(@ModelAttribute("municipio") String municipio, Model model) {

        List<EspacioPublico> lista;
        List<EspacioPublico> listaTodosEspacios = espacioPublicoDao.getEspacios();
        List<String> municipios = new ArrayList<>();
        municipios.add("Ninguno");

        for (EspacioPublico esp : listaTodosEspacios) {
            if (!municipios.contains(esp.getMunicipio())) {
                municipios.add(esp.getMunicipio());
            }
        }

        if (municipio.equals("Ninguno")) {
            lista = listaTodosEspacios;
        } else {
            lista = espacioPublicoDao.getEspaciosPorMunicipio(municipio);

        }
        int cont = 0;


        List<EspacioPublico> listaInterior = new ArrayList<>();
        List<List<EspacioPublico>> matriz = new ArrayList<>();
        EspacioPublico espacio;


        for (int i = 0; i < lista.size(); i++) {
            espacio = lista.get(i);
            if (!municipios.contains(espacio.getMunicipio())) {
                municipios.add(espacio.getMunicipio());
            }

            if (cont < 3) {

                listaInterior.add(espacio);
            } else {
                matriz.add(listaInterior);
                listaInterior = new ArrayList<>();
                listaInterior.add(espacio);
                cont = -1;
            }

            if (i == lista.size() - 1) {
                matriz.add(listaInterior);
                listaInterior = new ArrayList<>();
            }
            cont++;
        }

        model.addAttribute("municipios", municipios);
        model.addAttribute("matrizEspacios", matriz);

        return "espacios/list.html";
    }


    //llamada mediante el botón de la página de la lista hacia la página de añadir una reserva
    @RequestMapping(value = "/prereserva/{espacio}", method = RequestMethod.GET)
    public String prereserva(Model model, @PathVariable String espacio) {

        EspacioPublico espacioPublico=espacioPublicoDao.getEspacio(espacio);

        model.addAttribute("espacio", espacioPublico);

        List <Estacion> listaEstaciones=estacionDao.getEstaciones();
        LocalDate today= LocalDate.now();
        for(Estacion estacion: listaEstaciones){
            int diasIni=estacion.getFechaIni().getDayOfYear();
            int diasFin=estacion.getFechaFin().getDayOfYear();
            int diasHoy= today.getDayOfYear();

            if(diasHoy>=diasIni && diasHoy<=diasFin){
                System.out.println(estacion.getId());
                Servicio servicioActual=servicioDao.getServicioEspacioEstacion(espacio,estacion.getId());
                model.addAttribute("servicio", servicioActual.getTipo_servicio());
            }


        }

            return "espacios/prereservaSinLogin.html";
    }


    @RequestMapping(value="/add")
    public String addEspacio(Model model) {
        model.addAttribute("espacio", new EspacioPublico());

        return "espacios/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addEspacio(Model model, HttpSession session,@ModelAttribute("espacio") EspacioPublico espacio,
                             BindingResult bindingResult) {
        Usuario user=(Usuario) session.getAttribute("user");



        try{
            if(session.getAttribute("tipo").equals("Gestor")){

                Gestor gestor = usuarioDao.getGestorByDni(user.getDni());



                EspacioValidator espacioValidator=new EspacioValidator();
                espacioValidator.validate(espacio, bindingResult);

                if(bindingResult.hasErrors()){
                    return "espacios/add";
                }

                if(espacio.getMunicipio() != null && !gestor.getMunicipio().equals(espacio.getMunicipio())){
                    bindingResult.rejectValue("municipio", "invalitStr", "No gestionas el espacio de municipio");
                }

                espacio.setId(aleatorio());

                return "espacios/add";
            }else{
                return "error/error";
            }
        }catch (Exception e){
            return "error/error";
        }
      //  return "redirect:/login";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateEspacio(Model model, HttpSession session, @PathVariable String id){

        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Gestor")){
                EspacioPublico espacio=espacioPublicoDao.getEspacio(id);

                model.addAttribute("espacio", espacio);

            }
        }catch (Exception e){
            return "error/error";
        }

        return "error/error";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String publicUpdateSubmit(@ModelAttribute("esp") EspacioPublico espacio, HttpSession session, BindingResult bindingResult, Model model){
        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Gestor")){
                EspacioValidator validator = new EspacioValidator();
                validator.validate(espacio, bindingResult);

                if(bindingResult.hasErrors()){

                    return "espacios/add";
                }
            }
        }catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    private String aleatorio(){
        Random aleatorio = new Random();
        String alfa = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
        String cadena = "";
        int numero;
        int forma;
        forma=(int)(aleatorio.nextDouble() * alfa.length()-1+0);
        numero=(int)(aleatorio.nextDouble() * 99+100);
        cadena=cadena+alfa.charAt(forma)+numero;
        return cadena;
    }
}

