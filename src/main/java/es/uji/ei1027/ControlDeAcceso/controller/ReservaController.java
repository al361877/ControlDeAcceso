package es.uji.ei1027.ControlDeAcceso.controller;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import es.uji.ei1027.ControlDeAcceso.dao.FranjaDao;
import es.uji.ei1027.ControlDeAcceso.dao.ReservaDao;
import es.uji.ei1027.ControlDeAcceso.dao.ZonaDao;
import es.uji.ei1027.ControlDeAcceso.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
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
                    System.out.println(res.toString());
                }

                model.addAttribute("reservas", lista);
                if(lista.isEmpty()){
                    return "reservas/unlist";
                }

                return "reservas/list";
            }/*else if(user.getTipoUsuario().equals("Controlador")){
                List<Reserva> lista = resDao.getReservasByDni(user.getDni());

                model.addAttribute("reservas", resDao.getReservas());

                return "reservas/listarReservas.html";
            }*/
        }catch (Exception e){
            System.out.println("Estás en el catch");
            return "error/error";
        }
        System.out.println("Estás fuera del try");
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
                    int cont = 0;
                disponibilidad = !listaZonasString.isEmpty();
                if(disponibilidad) {
                    List<Zona> listaInterior = new ArrayList<>();
                    List<List<Zona>> matriz = new ArrayList<>();
                    Zona zona;

                    List<FranjaEspacio> listaFranja=franjaDao.getFranjas();
                    List<FranjaEspacio> listafranjaInterior = new ArrayList<>();
                    List<List<FranjaEspacio>> matrizFranja = new ArrayList<>();
                    FranjaEspacio franjaEspacio;

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

                    cont=0;
                    //matriz franjas
                    for (int i = 0; i < listaFranja.size(); i++) {
                        franjaEspacio = listaFranja.get(i);

                        if (cont < 3) {

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

//                ReservaValidator validator = new ReservaValidator();
//                System.out.println(res.toString());
//                validator.validate(res, bindingResult);
//                System.out.println(res.toString());
//                if (bindingResult.hasErrors()){
//                    System.out.println("ennntrooo");
//                    return "reservas/add";
//
//                }


                //validar
//                if (res.getZona()==null || res.getZona().trim().equals("")){
//                    System.out.println("ejnogfos");
//                    bindingResult.rejectValue("zona", "nonullobj","No se ha introducido ninguna zona");
//                    return "reservas/add";
//                }
//
//
//                if (res.getFranja()==null || res.getFranja().trim().equals("")) {
//                    System.out.println("etro");
//                    bindingResult.rejectValue("franja", "nonullobj", "No se ha introducido ninguna franja horaria");
//                    return "reservas/add";
//                }else{
//                    //compruebo que haya sido 1h antes de la hora de inicio
//                    LocalDate today= LocalDate.now();
//
//                    int horaIni= Time.valueOf(res.getHoraIniString()).getHours();
//                    int ahora=Time.valueOf(today.atStartOfDay().toString()).getHours();
//
//                    if(ahora>=horaIni+1){
//                        bindingResult.rejectValue("franja", "invalidStr", "La reserva ha de hacerse con 1h de antelación");
//                        return "reservas/add";
//                    }
//
//                }
//
//
//                if (res.getFechaIniString()==null || res.getFechaIniString().trim().equals("")) {
//                    System.out.println("etro");
//                    bindingResult.rejectValue("fechaIniString", "nonullobj", "No se ha introducido ninguna fecha de reserva");
//                    return "reservas/add";
//                }else{
//                    String fechaIni=res.getFechaIniString();
//                    res.setFechaIni(fechaIni);
//                    //compruebo que haya sido con 2 dias de antelacion
//                    LocalDate today= LocalDate.now();
//                    LocalDate fechaini=res.getFechaIni();
//                    int daysToday=today.getDayOfYear(), yearsToday= today.getYear();
//
//                    int daysFecha=fechaini.getDayOfYear(), yearsFecha=fechaini.getYear();
//
//                    if(daysFecha>=daysToday+2 && yearsFecha==yearsToday){
//                        bindingResult.rejectValue("fechaIniString", "invalidStr", "La reserva se ha de hacer 2 días con antelación");
//                        return "reservas/add";
//                    }
//
//
//                }
//
//                if (res.getNumPersonas()<=0 ){
//                    System.out.println("etro");
//                    bindingResult.rejectValue("numeropersonas", "invalidInt","El número de asistentes se ha introducido erroneamente");
//                    return "reservas/add";
//
//                }



                res.setId(aleatorio());
                res.setDniCiudadano(user.getDni());
                res.setEstado_reserva("pendienteDeUso");
                System.out.println(res.toString());

                FranjaEspacio franjaEspacio=franjaDao.getFranja(res.getFranja());
                res.setHoraIniString(franjaEspacio.getHoraIniString());
                res.setHoraFinString(franjaEspacio.getHoraFinString());


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


                List<FranjaEspacio> listaFranja=franjaDao.getFranjas();
                List<FranjaEspacio> listafranjaInterior = new ArrayList<>();
                List<List<FranjaEspacio>> matrizFranja = new ArrayList<>();
                FranjaEspacio franjaEspacio;
                for (Zona zona : lista) {

                    listaZonasString.add(zona.getId());
                }
                int cont = 0;
                disponibilidad = !listaZonasString.isEmpty();
                if(disponibilidad) {
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


                    cont=0;
                    //matriz franjas
                    for (int i = 0; i < listaFranja.size(); i++) {
                        franjaEspacio = listaFranja.get(i);

                        if (cont < 3) {

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
    public String processUpdateSubmit(@ModelAttribute("res") Reserva res, HttpSession session) {


        Usuario user = (Usuario) session.getAttribute("user");
        System.out.println("holaa");
        try{
            if(user.getTipoUsuario().equals("Ciudadano")){


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
