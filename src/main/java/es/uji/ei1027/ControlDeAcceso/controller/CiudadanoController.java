package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String listUsuarios( HttpSession session, Model model) {
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
    public String processAddCiudadanoSubmit(@ModelAttribute("user") Usuario user, HttpSession session,BindingResult bindingResult) {

        RegisterValidator validator = new RegisterValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "ciudadano/add";

        Usuario usuariobbdd1=userDao.getUsuario(user.getUsuario());
        if(usuariobbdd1!=null){
            bindingResult.rejectValue("usuario", "invalidStr", "El nombre de usuario ya existe");
            return "ciudadano/add";
        }
        Usuario usuariobbdd2=userDao.getUsuarioDni(user.getDni());
        if(usuariobbdd2!=null){
            bindingResult.rejectValue("dni", "invalidStr", "Ya existe un usuario con ese dni");
            return "ciudadano/add";
        }

        BasicPasswordEncryptor cifrar = new BasicPasswordEncryptor();
        String passCifrada=cifrar.encryptPassword(user.getContrase??a());
        user.setContrase??a(passCifrada);

        userDao.addCiudadano(user);
        userDao.addCiudadano(user.getDni());

        user.setTipoUsuario("Ciudadano");
        session.setAttribute("user", user);

        session.setAttribute("tipo",user.getTipoUsuario());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        System.out.println("EMAIL ENVIADO");
        System.out.println("*************************");
        System.out.println("Fecha y hora de env??o: " + formatter.format(new Date()));
        System.out.println("Correo destinatario: " + user.getEmail() + "\n"+
                "Correo del que envia: controlDeAcceso@gva.es\n" +
                "Estimado/a se??or/a "+ user.getNombre() + ":");
        System.out.println("Desde CONTROL DE ACCESO le comunicamos que se ha registrado en el sistema correctamente.");
        System.out.println("Si usted no est?? ha realizado este registro o no entiende este email, por favor p??ngase");
        System.out.println("en contacto con nosotros respondiendo a este correo o llame al numero de tel??fono: 920 14 58 74");
        System.out.println("");
        System.out.println("Gracias por su colaboraci??n.\n");
        System.out.println("Control de acceso.\n");
        System.out.println("*************************");

        return "ciudadano/index";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editUsuario(Model model, @PathVariable String dni,HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
//        System.out.println("dni de la sesion-> "+user.getDni()+" dni del get-> "+dni);
        try{
            if(user.getTipoUsuario().equals("Gestor") || user.getDni().equals(dni)){

                model.addAttribute("user", userDao.getUsuarioDni(dni));
                return "ciudadano/update";
            }
        }catch (Exception e){

            return "error/error";
        }

        return "error/error";


    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("user") Usuario user, BindingResult bindingResult,HttpSession session) {


        UpdateValidator validator = new UpdateValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "ciudadano/update";
        }

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
            if(user.getTipoUsuario().equals("Ciudadano")) {
                model.addAttribute("perfil", userDao.getUsuario(user.getUsuario()));
                return "/ciudadano/index";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }
    @RequestMapping(value = "/info")
    public String info(HttpSession session, Model model) {

        return "info/info";
    }

}

