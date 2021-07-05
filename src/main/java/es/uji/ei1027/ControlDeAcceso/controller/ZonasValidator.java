package es.uji.ei1027.ControlDeAcceso.controller;

import es.uji.ei1027.ControlDeAcceso.model.EspacioPublico;
import es.uji.ei1027.ControlDeAcceso.model.Zona;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ZonasValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) { return Zona.class.equals(cls); }

    @Override
    public void validate(Object obj, Errors errors) {
        Zona zona = (Zona) obj;

        if (zona.getId().trim().equals(null) || zona.getId().trim().equals("") || zona.getId().trim().equals(" "))
            errors.rejectValue("id", "nonullobj","No se ha introducido id de la zona");
        if (zona.getId().length() > 20)
            errors.rejectValue("id", "invalidStr","El ID de la zona supera el límite de carácteres (20)");

        if (zona.getNombre().trim().equals(null) || zona.getNombre().trim().equals("") || zona.getNombre().trim().equals(" "))
            errors.rejectValue("nombre", "nonullobj","No se ha introducido el nombre de la zona");
        if (zona.getNombre().length() > 50)
            errors.rejectValue("nombre", "invalidStr","El nombre de la zona supera el límite de carácteres (50)");

        if (zona.getTipo_suelo().trim().equals(null) || zona.getTipo_suelo().trim().equals("") || zona.getTipo_suelo().trim().equals(" "))
            errors.rejectValue("tipo_suelo", "nonullobj","No se ha introducido el tipo de suelo de la zona");
        if (zona.getTipo_suelo().length() > 30)
            errors.rejectValue("tipo_suelo", "invalidStr","El nombre del tipo de suelo de la zona supera el límite de carácteres (30)");

        if (zona.getTipo_acceso().trim().equals(null) || zona.getTipo_acceso().trim().equals("") || zona.getTipo_acceso().trim().equals(" "))
            errors.rejectValue("tipo_acceso", "nonullobj","No se ha introducido el tipo de acceso de la zona");
        if (zona.getTipo_acceso().length() > 30)
            errors.rejectValue("tipo_acceso", "invalidStr","El nombre del tipo de acceso de la zona supera el límite de carácteres (30)");

        if (zona.getAforo_actual()<0)
            errors.rejectValue("aforo_actual", "nonullobj","El aforo actual introducido no es valido");

        if (zona.getAforo_maximo() < 0)
            errors.rejectValue("aforo_maximo", "nonullobj","El aforo maximo introducido no es valido");

        if (zona.getAforo_maximo() < zona.getAforo_actual())
            errors.rejectValue("aforo_maximo", "nonullobj","El aforo maximo introducido no puede ser inferior al actual");

    }
}
