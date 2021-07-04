package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.Controlador;
import es.uji.ei1027.ControlDeAcceso.model.Gestor;
import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import es.uji.ei1027.ControlDeAcceso.services.ControladoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("controlador")
public class ControladorController {
    private UsuarioDao userDao;
    private ControladoresService controladoresService;

    @Autowired
    public void setUsuario(UsuarioDao userDao) {
        this.userDao = userDao;
    }

  /*  @RequestMapping("/list")
    public String listControladoresPorMunicipio(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            System.out.println("Estás en la lista de controladores por municipio");
            if(user.getTipoUsuario().equals("Gestor")) {

                //Cojo el municipio del gestor que está loggeado
                String municipioDelGestor = userDao.getGestorByDni(user.getDni()).getMunicipio();
                System.out.println(controladoresService.listControladoresPorMunicipio(municipioDelGestor));
                List<Controlador> controladores=controladoresService.listControladoresPorMunicipio(municipioDelGestor);
                System.out.println("Y no ha petado");
                model.addAttribute("controladores", controladores);

                return "controlador/list.html";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

*/


    @RequestMapping(value="/listPorEspacio/{espacio}", method = RequestMethod.GET)
    public String listControladoresPorEspacio(HttpSession session, Model model, @PathVariable String espacio) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{

            if(user.getTipoUsuario().equals("Gestor")) {
                System.out.println("Estás en la lista de controladores por espacio");
                List<Controlador> controladores= userDao.getControladorByEspacio(espacio);

                Usuario usuarioControlador=new Usuario();
                List<Usuario> usuariosControlador=new ArrayList<>();
                List<String> diasTrabajo=new ArrayList<>();

                for(Controlador cont:controladores){
                    System.out.println(cont.toString());
                    usuarioControlador=userDao.getUsuarioDni(cont.getDni());
                    usuariosControlador.add(usuarioControlador);
                }

                if(controladores.isEmpty()) {
                    return "controlador/noHayControladoresEspacio";
                }
                System.out.println("Y no ha petado");
                model.addAttribute("controladores", usuariosControlador);

                return "controlador/listPorEspacio";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/add")
    public String addControlado(Model model) {
        model.addAttribute("user", new Usuario());

        return "controlador/add";
    }

    //add ciudadano
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddControladorSubmit(@ModelAttribute("user") Usuario user, HttpSession session,BindingResult bindingResult) {

        RegisterValidator validator = new RegisterValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "controlador/add";

        userDao.addControlador(user);
        userDao.addControlador(user.getDni());
        user.setTipoUsuario("Controlador");
        session.setAttribute("user", user);

        session.setAttribute("tipo",user.getTipoUsuario());

        return "controlador/index";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editUsuario(Model model, @PathVariable String dni,HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
        System.out.println("dni de la sesion-> "+user.getDni()+" dni del get-> "+dni);
        try{
            if(user.getTipoUsuario().equals("Gestor") || user.getDni().equals(dni)){

                model.addAttribute("user", userDao.getUsuarioDni(dni));
                return "controlador/update";
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
            if(user.getTipoUsuario().equals("Controlador")) {
                model.addAttribute("perfil", userDao.getUsuario(user.getUsuario()));
                return "/controlador/index";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

}

