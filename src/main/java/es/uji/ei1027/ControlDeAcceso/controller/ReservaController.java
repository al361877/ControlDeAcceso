package es.uji.ei1027.ControlDeAcceso.controller;

import es.uji.ei1027.ControlDeAcceso.dao.ReservaDao;
import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
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
@RequestMapping("reservas")



public class ReservaController {
    private ReservaDao resDao;


    @Autowired
    public void setReserva(ReservaDao resDao) {
        this.resDao = resDao;
    }

    @RequestMapping("/list")
    public String listReserva(HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Ciudadano")){


                List<Reserva> lista = resDao.getReservasPendientesByDni(user.getDni());

                model.addAttribute("reservas", lista);
                System.out.println(lista.isEmpty());
                if(lista.isEmpty()){
                    return "reservas/unlist";
                }

                return "reservas/list";
            }/*else if(user.getTipoUsuario().equals("Controlador")){
                List<Reserva> lista = resDao.getReservasByDni(user.getDni());

                model.addAttribute("reservas", resDao.getReservas());

                return "reservas/listarReservas.html";
            }*/
        }catch (Exception e){
            System.out.println("Estás en el catch");
            return "error/error";
        }
        System.out.println("Estás fuera del try");
        return "error/error";
    }



    @RequestMapping(value="/add/{id}", method = RequestMethod.GET)
    public String addReserva(Model model,HttpSession session,@PathVariable String id) {

        Usuario user = (Usuario) session.getAttribute("user");

        try{
            if(user.getTipoUsuario().equals("Ciudadano")){

                model.addAttribute("res", new Reserva());
                System.out.println("HOLA");
                return "reservas/add/"+id;
            }
        }catch (Exception e){
            System.out.println("salto al login");
            return "redirect:/login";
        }

         return "redirect:/login";


    }

    //add reserva
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddReservaSubmit(@ModelAttribute("res") Reserva res,BindingResult bindingResult) {

        resDao.addReserva(res);
        return "redirect:list";
    }

    /*
    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable String id, String ) {

        model.addAttribute("user", resDao.getReservaId(id));
        return "user/reserva/update";
    }
*/

    //Para que los controladores puedan ven las reservas de los ciudadanos
    @RequestMapping(value="/busca/{id}", method = RequestMethod.GET)
    public String buscaReservaId(Model model, @PathVariable String id) {

        model.addAttribute("res", resDao.getReservaId(id));
        return "reserva/busca";
    }

    //Un ciudadano solo puede ver sus reservas
    @RequestMapping(value="/busca/{dni}", method = RequestMethod.GET)
    public String buscaReservaDni(Model model, @PathVariable String dni, @PathVariable String id) {

        model.addAttribute("res", resDao.getReservaId(id));
        return "reserva/busca";
    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("res") Reserva res,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "reserva/update";
        resDao.updateReserva(res);
        return "redirect:list";
    }


/*
    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        resDao.deleteReserva(id);
        return "redirect:../list";
    }

 */
}
