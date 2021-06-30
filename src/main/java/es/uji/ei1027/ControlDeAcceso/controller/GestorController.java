package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
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
import java.util.List;

@Controller
@RequestMapping("gestor")
public class GestorController {
    private UsuarioDao userDao;

    @Autowired
    public void setGestor(UsuarioDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/index")
    public String index(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Gestor")) {
                model.addAttribute("perfil", userDao.getUsuario(user.getUsuario()));
                return "/gestor/index";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

}

