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
    public void setUsuario(UsuarioDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/list")
    public String listUsuarios( HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Gestor")) {

                List<Usuario> lista = userDao.getGestores();

                model.addAttribute("usuarios", lista);

                return "gestor/list.html";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }



    @RequestMapping(value="/add")
    public String addControlado(Model model) {
        model.addAttribute("user", new Usuario());

        return "gestor/add";
    }

    //add gestor
  /*  @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddGestorSubmit(@ModelAttribute("user") Usuario user, HttpSession session,BindingResult bindingResult) {

        RegisterValidator validator = new RegisterValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "gestor/add";

        userDao.addGestor(user);
        userDao.addGestor(user.getDni());
        user.setTipoUsuario("Gestor");
        session.setAttribute("user", user);

        session.setAttribute("tipo",user.getTipoUsuario());

        return "gestor/index";
    }
*/
    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editUsuario(Model model, @PathVariable String dni,HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
        System.out.println("dni de la sesion-> "+user.getDni()+" dni del get-> "+dni);
        try{
            if(user.getTipoUsuario().equals("Gestor") || user.getDni().equals(dni)){

                model.addAttribute("user", userDao.getUsuarioDni(dni));
                return "gestor/update";
            }
        }catch (Exception e){

            return "error/error";
        }

        return "error/error";


    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("user") Usuario user, BindingResult bindingResult,HttpSession session) {
        System.out.println("before update "+user.getNombre());


        if (bindingResult.hasErrors())
            return "ciudadano/update/"+user.getDni();

        userDao.updateUsuario(user);
        return "ciudadano/updateConfirm";
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

