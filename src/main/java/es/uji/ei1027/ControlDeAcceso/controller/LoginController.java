package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.dao.ReservaDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

class UserValidator implements Validator {
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        // Exercici: Afegeix codi per comprovar que
        // l'usuari i la contrasenya no estiguen buits
        Usuario usuario= (Usuario) obj;


        if( usuario.getUsuario().trim().equals("")) {
            errors.rejectValue("usuario", "obligatori", "Falta introducir un valor");
        }
        if(usuario.getContraseña().trim().equals(""))
            errors.rejectValue("contraseña","obligatori","Falta introducir un valor");
    }
}

@Controller
public class LoginController {
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ReservaDao reservaDao;



    //llamada al metodo login
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Usuario());
        return "login";
    }

    //llamada al metodo login desde reservar espacio
    @RequestMapping(value="/login/{espacio}", method = RequestMethod.GET)
    public String loginReserva(Model model,@PathVariable String espacio) {
        model.addAttribute("user", new Usuario());
        return "login";
    }

    //respuesta al login
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") Usuario user, BindingResult bindingResult, HttpSession session) {

        UserValidator userValidator = new UserValidator();

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }

        System.out.println(user.getUsuario());
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari

        user = usuarioDao.getUsuarioConPass(user.getUsuario(),user.getContraseña());

        if (user == null) {
            bindingResult.rejectValue("contraseña", "obligatorio", "Contraseña  o nombre de usuario incorrecto");

            return "login";
        }


        // String contraseña = user.getContraseña();
        // hacer select de la bbdd de ciudadanos para comprobar la contraseña, pero donde?
        // y con eso, se enseña el mensaje de error de contraseña incorrecta, si no, nunca saltara...



        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", user);

        session.setAttribute("tipo",user.getTipoUsuario());





        return "redirect:/"+user.getTipoUsuario().toLowerCase()+"/index";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
