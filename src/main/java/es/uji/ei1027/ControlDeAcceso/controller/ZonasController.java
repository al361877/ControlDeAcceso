package es.uji.ei1027.ControlDeAcceso.controller;

import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.dao.ZonaDao;
import es.uji.ei1027.ControlDeAcceso.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("zonas")
public class ZonasController {

    private EspacioPublicoDao espacioPublicoDao;
    private ZonaDao zonaDao;
    private UsuarioDao usuarioDao;

    @Autowired
    public void setEspacioPublicoDao(EspacioPublicoDao espacioPublicoDao) { this.espacioPublicoDao = espacioPublicoDao; }

    @Autowired
    public void setZonaDao(ZonaDao zonaDao) { this.zonaDao = zonaDao; }

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) { this.usuarioDao = usuarioDao; }

    @RequestMapping(value="/list/{id}", method = RequestMethod.GET)
    public String listZonasPorEspacio(HttpSession session, Model model, @PathVariable String id) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Gestor")) {

//                EspacioPublico espacioPublico = espacioPublicoDao.getEspacio(id);
                List<Zona> lista = zonaDao.getZonasDisponiblesPorEspacio(id);

//                List<Zona> lista = zonaDao.getZonas();
                model.addAttribute("zonas", lista);
                model.addAttribute("espacio", id);

                return "zonas/list.html";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/add/{id}", method = RequestMethod.GET)
    public String addZona(Model model, @PathVariable String id_espacio) {
        Zona zona = new Zona();
        EspacioPublico espacioPublico = espacioPublicoDao.getEspacio(id_espacio);
        zona.setEspacio_publico(espacioPublico.getId());
        zona.setCp(espacioPublico.getCp());
        model.addAttribute("zona", zona);

        return "zonas/add";
    }

    //add ciudadano
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddZonaSubmit(@ModelAttribute("zona") Zona zona, HttpSession session, BindingResult bindingResult) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Gestor")) {

                ZonasValidator validator = new ZonasValidator();
                validator.validate(zona, bindingResult);
                if (bindingResult.hasErrors())
                    return "zonas/add";

                return "zonas/addConfirm";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }


    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editZona(Model model, @PathVariable String id, HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Gestor")){

                model.addAttribute("zona", zonaDao.getZonaId(id));
                return "zonas/update";
            }
        }catch (Exception e){

            return "error/error";
        }

        return "error/error";


    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("zona") Zona zona, BindingResult bindingResult,HttpSession session) {

        ZonasValidator validator = new ZonasValidator();
        validator.validate(zona, bindingResult);
        if (bindingResult.hasErrors())
            return "zonas/update";

        zonaDao.updateZona(zona);
        return "zonas/updateConfirm";
    }


    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable String id, HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Gestor")){
                zonaDao.deleteZona(id);
                return "controlador/deleteConfirm";
            }
        }catch (Exception e){
            System.out.println("Estás en el catch");
            return "error/error";
        }
        return "error/error";
    }

}
