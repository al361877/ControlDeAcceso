package es.uji.ei1027.ControlDeAcceso.controller;

import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Time;
import java.time.LocalDate;

public class ReservaValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Reserva.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Reserva reserva = (Reserva) obj;


        if (reserva.getZona()==null || reserva.getZona().trim().equals("")){
            System.out.println("ejnogfos");
            errors.rejectValue("zona", "nonullobj","No se ha introducido ninguna zona");
        }


        if (reserva.getFranja()==null || reserva.getFranja().trim().equals("")) {
            System.out.println("etro");
            errors.rejectValue("franja", "nonullobj", "No se ha introducido ninguna franja horaria");
        }else{
            //compruebo que haya sido 1h antes de la hora de inicio
            LocalDate today= LocalDate.now();

            int horaIni=Time.valueOf(reserva.getHoraIniString()).getHours();
            int ahora=Time.valueOf(today.atStartOfDay().toString()).getHours();

            if(ahora>=horaIni+1){
                errors.rejectValue("franja", "invalidStr", "La reserva ha de hacerse con 1h de antelación");
            }

        }


        if (reserva.getFechaIniString()==null || reserva.getFechaIniString().trim().equals("")) {
            System.out.println("etro");
            errors.rejectValue("fechaIniString", "nonullobj", "No se ha introducido ninguna fecha de reserva");
        }else{
            //compruebo que haya sido con 2 dias de antelacion
            LocalDate today= LocalDate.now();
            LocalDate fechaini=reserva.getFechaIni();
            int daysToday=today.getDayOfYear(), yearsToday= today.getYear();

            int daysFecha=fechaini.getDayOfYear(), yearsFecha=fechaini.getYear();

            if(daysFecha>=daysToday+2 && yearsFecha==yearsToday){
                errors.rejectValue("fechaIniString", "invalidStr", "La reserva se ha de hacer 2 días con antelación");
            }


        }

        if (reserva.getNumPersonas()<=0 ){
            System.out.println("etro");
            errors.rejectValue("numeropersonas", "invalidInt","El número de asistentes se ha introducido erroneamente");

        }


    }
}
