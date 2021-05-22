package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.EspacioPublicoDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        model.addAttribute("espacios", lista);

        return "espacios/list.html";
    }


}

