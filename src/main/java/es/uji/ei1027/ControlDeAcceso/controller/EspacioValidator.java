package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EspacioValidator implements Validator{

    @Override
    public boolean supports(Class<?> cls) {
        return EspacioPublico.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        EspacioPublico espacio = (EspacioPublico) obj;

        if (espacio.getId()==null || espacio.getId().trim().equals("") || espacio.getId().trim().equals(" "))
            errors.rejectValue("id", "nonullobj","No se ha introducido id del espacio");
        if (espacio.getId().length() > 20)
            errors.rejectValue("id", "invalidStr", "La id supera el límite de carácteres (20)");

        if (espacio.getNombre()==null || espacio.getNombre().trim().equals("") || espacio.getNombre().trim().equals(" "))
            errors.rejectValue("nombre", "nonullobj","No se ha introducido el nombre del espacio");
        if (espacio.getNombre().length() > 50)
            errors.rejectValue("nombre", "invalidStr", "El nombre supera el límite de carácteres (50)");

        if (espacio.getMunicipio()==null || espacio.getMunicipio().trim().equals("") || espacio.getMunicipio().trim().equals(" "))
            errors.rejectValue("municipio", "nonullobj", "No se ha introducido el municipio al que perteneces el espacio");
        if (espacio.getMunicipio().length() > 20)
            errors.rejectValue("municipio", "invalidStr", "El nombre del municipio supera el límite de carácteres (20)");

        if (espacio.getTipo_espacio()==null || espacio.getTipo_espacio().trim().equals("") || espacio.getTipo_espacio().trim().equals(" "))
            errors.rejectValue("tipo_espacio", "nonullobj", "No se ha introducido tipo de espacio");
        if (espacio.getTipo_espacio().length() > 50)
            errors.rejectValue("tipo_espacio", "invalidStr", "El tipo de espacio supera el límite de carácteres (50)");

        if (espacio.getCp() < 1000 || espacio.getCp() > 52999)
            errors.rejectValue("cp", "invalidInt","El código postal introducido no es válido");

    }

}
/*

public class UpdateEspacioValidator implements Validator{

    @Override
    public boolean supports(Class<?> cls) {
        return EspacioPublico.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        EspacioPublico espacio = (EspacioPublico) obj;
        //    LocalDate today= LocalDate.now();


        if (espacio.getNombre()==null || espacio.getNombre().trim().equals(""))
            errors.rejectValue("espacio", "nonullobj","No se ha introducido el nombre del espacio");


        if (espacio.getMunicipio()==null || espacio.getMunicipio().trim().equals(""))
            errors.rejectValue("municipio", "nonullobj", "No se ha introducido el municipio al que perteneces el espacio");
        }

        if (espacio.getTipo_espacio()==null || espacio.getTipo_espacio().trim().equals(""))
            errors.rejectValue("fechaIniString", "nonullobj", "No se ha introducido tipo de espacio");

        if (espacio.getCp()<=0 )
            errors.rejectValue("numeropersonas", "invalidInt","El número de asistentes se ha introducido erroneamente");

    }


}

*/