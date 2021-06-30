package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Time;
import java.time.LocalDate;


public class EspacioValidator implements Validator{

    @Override
    public boolean supports(Class<?> cls) {
        return EspacioPublico.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        EspacioPublico espacio = (EspacioPublico) obj;
    //    LocalDate today= LocalDate.now();


        if (espacio.getNombre()==null || espacio.getNombre().trim().equals("")){
            System.out.println("ejnogfos");
            errors.rejectValue("espacio", "nonullobj","No se ha introducido el nombre del espacio");
        }


        if (espacio.getMunicipio()==null || espacio.getMunicipio().trim().equals("")) {
            System.out.println("etro");
            errors.rejectValue("municipio", "nonullobj", "No se ha introducido el municipio al que perteneces el espacio");
        }

        if (espacio.getTipo_espacio()==null || espacio.getTipo_espacio().trim().equals("")) {
            System.out.println("etro");
            errors.rejectValue("fechaIniString", "nonullobj", "No se ha introducido tipo de espacio");
        }
        if (espacio.getCp()<=0 ){
            System.out.println("etro");
            errors.rejectValue("numeropersonas", "invalidInt","El número de asistentes se ha introducido erroneamente");

        }


    }


}
