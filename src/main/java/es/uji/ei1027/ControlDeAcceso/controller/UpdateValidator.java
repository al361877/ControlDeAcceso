package es.uji.ei1027.ControlDeAcceso.controller;

import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class UpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Usuario usuario = (Usuario) obj;


        if (usuario.getNombre().trim().equals(null) || usuario.getNombre().trim().equals("") || usuario.getNombre().trim().equals(" "))
            errors.rejectValue("nombre", "nonullobj","No se ha introducido nombre y apellidos");
        if (usuario.getNombre().length() > 100)
            errors.rejectValue("nombre", "invalidStr","Nombre y apellidos demasiado largos");

        if (usuario.getNacimientoString()==null || usuario.getNacimientoString().trim().equals("") || usuario.getNacimientoString().trim().equals(" "))
            errors.rejectValue("nacimientoString", "nonullobj","No se ha introducido ninguna fecha de nacimiento");
        else{
//            System.out.println("nacimiento string= "+usuario.getNacimientoString());
            usuario.setNacimiento(usuario.getNacimientoString());
            LocalDate nacimiento= usuario.getNacimiento();
            LocalDate today= LocalDate.now();
            int daysToday=today.getDayOfYear(), yearsToday= today.getYear();

            int daysNacimiento=nacimiento.getDayOfYear(), yearsNacimiento=nacimiento.getYear();

            if(yearsToday-yearsNacimiento<18|| (yearsToday-yearsNacimiento==18 && daysNacimiento>daysToday))
                errors.rejectValue("nacimientoString", "invalidStr","Debe ser mayor de edad para ingresar en la plataforma");

        }

        if (usuario.getCiudad().trim().equals(null) || usuario.getCiudad().trim().equals("") || usuario.getCiudad().trim().equals(" "))
            errors.rejectValue("ciudad", "nonullobj","No se ha introducido la ciudad");
        if (usuario.getCiudad().length() > 100)
            errors.rejectValue("ciudad", "invalidStr","El nombre de la ciudad es demasiado largo");

        if (usuario.getCalle().trim().equals(null) || usuario.getCalle().trim().equals("") || usuario.getCalle().trim().equals(" "))
            errors.rejectValue("calle", "nonullobj","No se ha introducido ninguna calle");
        if (usuario.getCalle().length() > 100)
            errors.rejectValue("calle", "invalidStr","El nombre de la calle es demasiado largo");

        if (usuario.getCp()==0 || usuario.getCp() < 01000 || usuario.getCp() > 52999 )
            errors.rejectValue("cp", "nonullobj","El codigo postal introducido no es valido");

    }
}
