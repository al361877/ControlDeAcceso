package es.uji.ei1027.ControlDeAcceso.controller;

import es.uji.ei1027.ControlDeAcceso.dao.ReservaDao;
import es.uji.ei1027.ControlDeAcceso.model.Reserva;
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
@RequestMapping("reservas")



public class ReservaController {
    private ReservaDao resDao;


    @Autowired
    public void setReserva(ReservaDao resDao) {
        this.resDao = resDao;
    }

    @RequestMapping("/list")
    public String listReserva( Model model) {

        List<Reserva> lista = resDao.getReservas();

        model.addAttribute("reservas", resDao.getReservas());

        return "reservas/listarReservas.html";
    }

    @RequestMapping(value="/add")
    public String addReserva(Model model) {
        model.addAttribute("res", new Reserva());

        return "reservas/add";
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
    @RequestMapping(value="/busca/{id}", method = RequestMethod.GET)
    public String buscaReservaId(Model model, @PathVariable String id) {

        model.addAttribute("res", resDao.getReservaId(id));
        return "user/reserva/busca";
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
