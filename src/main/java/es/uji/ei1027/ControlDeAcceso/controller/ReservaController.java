package es.uji.ei1027.ControlDeAcceso.controller;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;
import es.uji.ei1027.ControlDeAcceso.dao.FranjaDao;
import es.uji.ei1027.ControlDeAcceso.dao.ReservaDao;
import es.uji.ei1027.ControlDeAcceso.dao.ZonaDao;
import es.uji.ei1027.ControlDeAcceso.model.*;
import es.uji.ei1027.ControlDeAcceso.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("reservas")



public class ReservaController {
    private ReservaDao resDao;
    private ZonaDao zonaDao;
    private FranjaDao franjaDao;
    private EspacioPublicoDao espacioDao;
    private ReservaService reservaService;

    @Autowired
    public void setEspacioDao(EspacioPublicoDao espacioDao) {

        this.espacioDao = espacioDao;
    }
    @Autowired
    public void setReserva(ReservaDao resDao) {

        this.resDao = resDao;
    }
    @Autowired
    public void setFranjaDao(FranjaDao franjaDao) {

        this.franjaDao= franjaDao;
    }
    @Autowired
    public void setZona(ZonaDao zonaDao) {

        this.zonaDao = zonaDao;
    }
    @RequestMapping("/list")
    public String listReserva(HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Ciudadano")){


                List<Reserva> lista = resDao.getReservasPendientesByDni(user.getDni());
                FranjaEspacio franjaEspacio;
                for(Reserva res: lista){
                    franjaEspacio=franjaDao.getFranja(res.getFranja());
                    res.setHoraIniString(franjaEspacio.getHoraIniString());
                    res.setHoraFinString(franjaEspacio.getHoraFinString());
                    EspacioPublico espacioPublico=espacioDao.getEspacio(res.getEspacio_publico());
                    res.setNombreEspacio(espacioPublico.getNombre());
                }

                model.addAttribute("reservas", lista);
                if(lista.isEmpty()){
                    return "reservas/unlist";
                }

                return "reservas/list";
            }

        }catch (Exception e){
            System.out.println("Estás en el catch");
            return "error/error";
        }
        System.out.println("Estás fuera del try");
        return "error/error";
    }


    @RequestMapping(value ="/listDelFuncionario/{espacio}", method = RequestMethod.GET)
    public String listReservasPorGestor(HttpSession session, Model model, @PathVariable String espacio){
        Usuario user = (Usuario) session.getAttribute("user");
        System.out.println("Eres otro: " + user.getTipoUsuario());

        try{
            if(user.getTipoUsuario().equals("Gestor") || user.getTipoUsuario().equals("Controlador")){
                List<Reserva> reservas = resDao.getReservasByEspacio(espacio);
                System.out.println("Estás dentro del if del : " + espacio);




                FranjaEspacio franjaEspacio;
                for(Reserva res: reservas){
                    franjaEspacio=franjaDao.getFranja(res.getFranja());
                    res.setHoraIniString(franjaEspacio.getHoraIniString());
                    res.setHoraFinString(franjaEspacio.getHoraFinString());
                    EspacioPublico espacioPublico=espacioDao.getEspacio(res.getEspacio_publico());
                    res.setNombreEspacio(espacioPublico.getNombre());
                }




                if(reservas.isEmpty()){
                    return"reservas/noHayReservasEspacio";
                }
                System.out.println("Los espacios que tiene son: "+ reservas);
                model.addAttribute("reservas", reservas);

                return "reservas/listFuncionario";
            }
        }catch (Exception e){
            return "error/error";
        }

        return "error/error";
    }



    @RequestMapping(value="/add/{id}", method = RequestMethod.GET)
    public String addReserva(Model model,HttpSession session,@PathVariable String id) {

        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Ciudadano")){


                List<Zona> lista = zonaDao.getZonasDisponiblesPorEspacio(id);
                List<String> listaZonasString = new ArrayList<>();
                boolean disponibilidad;

                    for (Zona zona : lista) {

                        listaZonasString.add(zona.getId());
                    }


                disponibilidad = !listaZonasString.isEmpty();


                if(disponibilidad) {

                    List<List<Zona>> matriz =crearMatrizZonas(id);
                    List<List<FranjaEspacio>> matrizFranja=crearMatrizFranja();

                    Reserva reserva= new Reserva();
                    reserva.setEspacio_publico(id);

                    model.addAttribute("zonasL", listaZonasString);
                    model.addAttribute("matrizZonas", matriz);
                    model.addAttribute("matrizFranja", matrizFranja);
                    model.addAttribute("reserva",reserva);

                    return "reservas/add";
                }else{
                    return "reservas/noHayZonas";
                }
            }
        }catch (Exception e){
            System.out.println("salto al login");
            return "redirect:/login";
        }

         return "redirect:/login";


    }
//
//    @RequestMapping(value="/add", method = RequestMethod.POST)
//    public String addReservaEspacio(HttpSession session,@ModelAttribute("zona") Zona zona) {
//        Usuario user = (Usuario) session.getAttribute("user");
//        System.out.println("ENTROOO");
//
//        try{
//            if(user.getTipoUsuario().equals("Ciudadano")){
//
//                System.out.println("zona "+zona.toString());
//                return "reservas/add";
//
//
//            }
//        }catch (Exception e){
//            System.out.println("Estás en el catch");
//            return "error/error";
//        }
//        System.out.println("Estás fuera del try");
//        return "error/error";
//
//
//    }

    public List<List<FranjaEspacio>> crearMatrizFranja(){
        List<FranjaEspacio> listaFranja=franjaDao.getFranjas();
        List<FranjaEspacio> listafranjaInterior = new ArrayList<>();
        List<List<FranjaEspacio>> matrizFranja = new ArrayList<>();
        FranjaEspacio franjaEspacio;
        int cont=0;
        for (int i = 0; i < listaFranja.size(); i++) {
            franjaEspacio = listaFranja.get(i);

            if (cont < 6) {

                listafranjaInterior.add(franjaEspacio);
            } else {
                matrizFranja.add(listafranjaInterior);
                listafranjaInterior = new ArrayList<>();
                listafranjaInterior.add(franjaEspacio);
                cont = 0;
            }

            if (i == listaFranja.size() - 1) {
                matrizFranja.add(listafranjaInterior);
                listafranjaInterior = new ArrayList<>();
            }
            cont++;
        }
        return matrizFranja;

    }
    public List<List<Zona>> crearMatrizZonas(String id){
        int cont=0;
        List<Zona> lista = zonaDao.getZonasDisponiblesPorEspacio(id);

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
                cont = 0;
            }

            if (i == lista.size() - 1) {
                matriz.add(listaInterior);
                listaInterior = new ArrayList<>();
            }
            cont++;
        }
        return matriz;
    }

    @RequestMapping(value="/canceladaU/{id}", method = RequestMethod.GET)
    public String deleteReserva(Model model,HttpSession session,@PathVariable String id) {
        Usuario user = (Usuario) session.getAttribute("user");
        Reserva res=resDao.getReservaId(id);
        try{
            if(user.getTipoUsuario().equals("Ciudadano") && user.getDni().equals(res.getDniCiudadano())){
                System.out.println("entro");
                resDao.canceladaPorUsuarioReserva(id);

                return "reservas/deleteConfirm";


            }
        }catch (Exception e){
            System.out.println("Estás en el catch");
            return "error/error";
        }
        System.out.println("Estás fuera del try");
        return "error/error";


    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String addReservaEspacioPost(Model model,HttpSession session,@ModelAttribute("reserva") Reserva res,BindingResult bindingResult) {
        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Ciudadano")){
                FranjaEspacio franjaEspacio=franjaDao.getFranja(res.getFranja());
                res.setHoraIniString(franjaEspacio.getHoraIniString());
                res.setHoraFinString(franjaEspacio.getHoraFinString());

                ReservaValidator validator = new ReservaValidator();
                validator.validate(res, bindingResult);

                if (bindingResult.hasErrors()){
                    System.out.println("ennntrooo");

                    List<List<Zona>> matriz =crearMatrizZonas(res.getEspacio_publico());
                    List<List<FranjaEspacio>> matrizFranja=crearMatrizFranja();
                    model.addAttribute("matrizZonas", matriz);
                    model.addAttribute("matrizFranja", matrizFranja);
                    return "reservas/add";

                }




                res.setId(aleatorio());
                res.setDniCiudadano(user.getDni());
                res.setEstado_reserva("pendienteDeUso");
                System.out.println(res.toString());




                resDao.addReserva(res);
                //FALTA AÑADIR LAS PERSONAS A LA ZONAAAA
                return "/reservas/addConfirm";


            }
        }catch (Exception e){
            System.out.println("Estás en el catch");
            return "error/error";
        }
        System.out.println("Estás fuera del try");
        return "error/error";


    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateReserva(Model model,HttpSession session,@PathVariable String id) {

        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Ciudadano")){
                Reserva reserva= resDao.getReservaId(id);
                String idEspacio= reserva.getEspacio_publico();
                List<Zona> lista = zonaDao.getZonasDisponiblesPorEspacio(idEspacio);


                List<String> listaZonasString = new ArrayList<>();
                boolean disponibilidad;



                for (Zona zona : lista) {

                    listaZonasString.add(zona.getId());
                }
                int cont = 0;
                disponibilidad = !listaZonasString.isEmpty();
                if(disponibilidad) {
                    List<List<Zona>> matriz =crearMatrizZonas(idEspacio);
                    List<List<FranjaEspacio>> matrizFranja=crearMatrizFranja();

                    model.addAttribute("zonasL", listaZonasString);
                    model.addAttribute("matrizZonas", matriz);
                    model.addAttribute("matrizFranja", matrizFranja);
                    model.addAttribute("reserva",reserva);

                    return "reservas/update";
                }else{
                    return "reservas/noHayZonas";
                }
            }
        }catch (Exception e){
            System.out.println("salto al login");
            return "error/error";
        }

        return "error/error";


    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("reserva") Reserva res, HttpSession session,BindingResult bindingResult,Model model ) {


        Usuario user = (Usuario) session.getAttribute("user");
        System.out.println("holaa");
        try{
            if(user.getTipoUsuario().equals("Ciudadano")){

                ReservaValidator validator = new ReservaValidator();
                validator.validate(res, bindingResult);

                if (bindingResult.hasErrors()){
                    System.out.println("ennntrooo");

                    List<List<Zona>> matriz =crearMatrizZonas(res.getEspacio_publico());
                    List<List<FranjaEspacio>> matrizFranja=crearMatrizFranja();
                    model.addAttribute("matrizZonas", matriz);
                    model.addAttribute("matrizFranja", matrizFranja);
                    return "reservas/update";

                }
                String fechaIni=res.getFechaIniString();


                res.setFechaIni(fechaIni);




                //FALTA AÑADIR LAS PERSONAS A LA ZONAAAA

                resDao.updateReserva(res);

                return "/reservas/updateConfirm";


            }
        }catch (Exception e){
            System.out.println("Estás en el catch");
            return "error/error";
        }
        System.out.println("Estás fuera del try");
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

/*
    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        resDao.deleteReserva(id);
        return "redirect:../list";
    }

 */
}
