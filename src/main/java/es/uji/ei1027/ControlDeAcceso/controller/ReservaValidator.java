package es.uji.ei1027.ControlDeAcceso.controller;

import es.uji.ei1027.ControlDeAcceso.dao.FranjaDao;
import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.FranjaEspacio;
import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservaValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Reserva.class.equals(cls);
    }
    @Autowired
    private FranjaDao franjaDao;
    @Override
    public void validate(Object obj, Errors errors) {
        Reserva reserva = (Reserva) obj;


        if (reserva.getZona()==null || reserva.getZona().trim().equals("")){
            System.out.println("ejnogfos");
            errors.rejectValue("zona", "nonullobj","No se ha introducido ninguna zona");
        }

        if (reserva.getFechaIniString()==null || reserva.getFechaIniString().trim().equals("")) {
            System.out.println("etro");
            errors.rejectValue("fechaIniString", "nonullobj", "No se ha introducido ninguna fecha de reserva");
        }else{
            //compruebo que haya sido con 2 dias de antelacion
            LocalDate today= LocalDate.now();
            LocalDate fechaini=reserva.getFechaIni();
            int daysToday=today.getDayOfYear()+2, yearsToday= today.getYear();

            int daysFecha=fechaini.getDayOfYear(), yearsFecha=fechaini.getYear();

            if(daysFecha>daysToday && yearsFecha==yearsToday){
                errors.rejectValue("fechaIniString", "invalidStr", "La reserva es para dentro de más de 2 días ");
            }


        }

        if (reserva.getNumPersonas()<=0 ){
            System.out.println("etro personas");
            errors.rejectValue("numPersonas", "invalidInt","El número de asistentes se ha introducido erroneamente");

        }
        if (reserva.getFranja()==null || reserva.getFranja().trim().equals("")) {
            System.out.println("etro franja");
            errors.rejectValue("franja", "nonullobj", "No se ha introducido ninguna franja horaria");
        }
        else{


            //compruebo que haya sido 1h antes de la hora de inicio
            LocalDate today = LocalDate.now();
            LocalDate fechaini=reserva.getFechaIni();
            int daysFecha=fechaini.getDayOfYear(), daysToday=today.getDayOfYear();
            if(daysFecha==daysToday){
                int horaIni=Time.valueOf(reserva.getHoraIniString()).getHours();
                int ahora=LocalDateTime.now().getHour()+1;
                System.out.println("Hora ini= "+horaIni+" ahora "+ahora);
                if(ahora>=horaIni){
                    System.out.println("entro");
                    errors.rejectValue("franja", "invalidStr", "La reserva ha de hacerse con 1h de antelación");
                }
            }


        }





    }
}
