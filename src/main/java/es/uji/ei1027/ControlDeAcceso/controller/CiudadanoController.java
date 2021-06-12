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
@RequestMapping("ciudadano")
public class CiudadanoController {
    private UsuarioDao userDao;


    @Autowired
    public void setUsuario(UsuarioDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/list")
    public String listUusarios( HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Gestor")) {

                List<Usuario> lista = userDao.getCiudadanos();

                model.addAttribute("usuarios", lista);

                return "ciudadano/list.html";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }



    @RequestMapping(value="/add")
    public String addCiudadano(Model model) {
        model.addAttribute("user", new Usuario());

        return "ciudadano/add";
    }

    //add ciudadano
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddCiudadanoSubmit(@ModelAttribute("user") Usuario user,BindingResult bindingResult) {

        RegisterValidator validator = new RegisterValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "ciudadano/add";

        userDao.addUsuario(user);
        userDao.addCiudadano(user.getDni());
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editUsuario(Model model, @PathVariable String dni) {

        model.addAttribute("user", userDao.getUsuarioDni(dni));
        return "ciudadano/update";
    }

    @RequestMapping(value="/busca/{dni}", method = RequestMethod.GET)
    public String buscaUsuario(Model model, @PathVariable String dni) {

        model.addAttribute("usuario", userDao.getUsuarioDni(dni));
        return "ciudadano/busca";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("user") Usuario usuario,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "ciudadano/update";
        userDao.updateUsuario(usuario);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        userDao.deleteUsuario(dni);
        return "redirect:../list";
    }

    @RequestMapping(value = "/index")
    public String index(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Ciudadano")) {
                model.addAttribute("perfil", userDao.getUsuario(user.getUsuario()));
                return "ciudadano/index";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

}

