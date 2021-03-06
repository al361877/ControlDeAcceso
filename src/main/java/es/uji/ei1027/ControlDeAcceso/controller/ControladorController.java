package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.dao.ZonaDao;
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

    @Autowired
    public void setControladoresService(ControladoresService controladoresService) {
        this.controladoresService = controladoresService;
    }

    @RequestMapping("/listPorMunicipio")
    public String listControladoresPorMunicipio(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{

            if(user.getTipoUsuario().equals("Gestor")) {

                //Cojo el municipio del gestor que está loggeado
                String municipioDelGestor = userDao.getGestorByDni(user.getDni()).getMunicipio();

                System.out.println("Estás en la lista de controladores del municipio " + municipioDelGestor);

                List<Controlador> controladores=new ArrayList<>();
                controladores=controladoresService.listControladoresPorMunicipio(municipioDelGestor);
                System.out.println("Y no ha petado");
                model.addAttribute("controladores", controladores);

                return "controlador/listPorMunicipio.html";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }



    @RequestMapping(value="/listPorEspacio/{espacio}", method = RequestMethod.GET)
    public String listControladoresPorEspacio(HttpSession session, Model model, @PathVariable String espacio) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{

            if(user.getTipoUsuario().equals("Gestor")) {
                System.out.println("Estás en la lista de controladores por espacio");
                List<Controlador> controladores= userDao.getControladorByEspacio(espacio);

                for(Controlador contr:controladores){
                    System.out.println(contr.toString());
                }

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


              //  model.addAttribute("controladores",controladores);
              //  return "controlador/listPorEspacioPrueba";

            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/add")
    public String addControlador(Model model) {
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

        return "controlador/addConfirm";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editUsuario(Model model, @PathVariable String dni,HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
//        System.out.println("dni de la sesion-> "+user.getDni()+" dni del get-> "+dni);
        try{
            if(user.getTipoUsuario().equals("Gestor") || user.getDni().equals(dni)){

                model.addAttribute("controlador", userDao.getUsuarioDni(dni));
                return "controlador/update";
            }
        }catch (Exception e){

            return "error/error";
        }

        return "error/error";


    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("controlador") Usuario user, BindingResult bindingResult,HttpSession session) {
//        System.out.println("before update "+user.getNombre());

        UpdateValidator validator = new UpdateValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "controlador/update";

        userDao.updateUsuario(user);
        return "controlador/updateConfirm";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni, HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Gestor")){
                userDao.deleteUsuario(dni);
                return "controlador/deleteConfirm";
            }
        }catch (Exception e){
//            System.out.println("Estás en el catch");
            return "error/error";
        }
        return "error/error";
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

