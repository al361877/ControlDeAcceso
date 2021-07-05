package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.ZonaDao;
import es.uji.ei1027.ControlDeAcceso.model.Controlador;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import es.uji.ei1027.ControlDeAcceso.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("zona")
public class ZonaController{
    @Autowired
    private ZonaDao zonaDao;

    @Autowired
    public void setZona(ZonaDao zonaDao) {

        this.zonaDao = zonaDao;
    }

    @RequestMapping(value="/listPorEspacio/{espacio}", method = RequestMethod.GET)
    public String listZonasPorEspacio(HttpSession session, Model model, @PathVariable String espacio) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{

            if(user.getTipoUsuario().equals("Gestor") || user.getTipoUsuario().equals("Controlador")) {
                System.out.println("Estás en la una prueba de zonas por espacio");

                List<Zona> zonas=zonaDao.getZonasByEspacio(espacio);

                for(Zona zona:zonas){
                    System.out.println(zona.toString());
                }

                if(zonas.isEmpty()) {
                    return "zona/noHayZonasEspacio";
                }

                model.addAttribute("zonas", zonas);

                return "zona/listPorEspacio";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

}
