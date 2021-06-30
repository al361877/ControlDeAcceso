package es.uji.ei1027.ControlDeAcceso.controller;

import es.uji.ei1027.ControlDeAcceso.dao.UsuarioDao;
import es.uji.ei1027.ControlDeAcceso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class RegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.equals(cls);
    }
    @Autowired
    private UsuarioDao userDao;

    @Override
    public void validate(Object obj, Errors errors) {
        Usuario usuario = (Usuario) obj;

        if (usuario.getDni()==null || usuario.getDni().trim().equals("") || usuario.getDni().trim().equals(" "))
            errors.rejectValue("dni", "nonullobj","No se ha introducido el DNI");
        if (usuario.getDni().length() > 9)
            errors.rejectValue("dni", "invalidStr","DNI debe tener 9 carácteres");

        if (usuario.getUsuario()==null || usuario.getUsuario().trim().equals("") || usuario.getUsuario().trim().equals(" ")) {
            errors.rejectValue("usuario", "nonullobj", "No se ha introducido usuario");
        }
//        }else{
//            //compruebo que no haya nadie con el mismo nombre
//            System.out.println(usuario.getUsuario());
//            Usuario usuariobbdd=userDao.getUsuario(usuario.getUsuario());
//            if(usuariobbdd!=null){
//                errors.rejectValue("usuario", "invalidStr", "Ese nombre de usuario ya existe");
//            }
//        }
        if (usuario.getUsuario().length() > 30)
            errors.rejectValue("usuario", "invalidStr","Usuario demasiado largo");

        if (usuario.getNombre()==null || usuario.getNombre().trim().equals("") || usuario.getNombre().trim().equals(" "))
            errors.rejectValue("nombre", "nonullobj","No se ha introducido nombre y apellidos");
        if (usuario.getNombre().length() > 100)
            errors.rejectValue("nombre", "invalidStr","Nombre y apellidos demasiado largos");

        if (usuario.getTelefono()==null || usuario.getTelefono().trim().equals("") || usuario.getTelefono().trim().equals(" "))
            errors.rejectValue("telefono", "nonullobj","No se ha introducido ningún telefono");
        if (usuario.getTelefono().length() > 15)
            errors.rejectValue("telefono", "invalidStr","El número de teléfono es demasiado largo");

        if (usuario.getEmail()==null || usuario.getEmail().trim().equals("") || usuario.getEmail().trim().equals(" "))
            errors.rejectValue("email", "nonullobj","No se ha introducido email");
        if (usuario.getEmail().length() > 50)
            errors.rejectValue("email", "invalidStr","Email demasiado largo");

        if (usuario.getContraseña()==null || usuario.getContraseña().trim().equals("") || usuario.getContraseña().trim().equals(" "))
            errors.rejectValue("contraseña", "nonullobj","No se ha introducido contraseña");
        if (usuario.getContraseña().length() > 30)
            errors.rejectValue("contraseña", "invalidStr","La contraseña no cumple los requisitos");

        if (usuario.getNacimientoString()==null || usuario.getNacimientoString().trim().equals("") || usuario.getNacimientoString().trim().equals(" "))
            errors.rejectValue("nacimientoString", "nonullobj","No se ha introducido ninguna fecha de nacimiento");
        else{
            System.out.println("nacimiento string= "+usuario.getNacimientoString());
            usuario.setNacimiento(usuario.getNacimientoString());
            LocalDate nacimiento= usuario.getNacimiento();
            LocalDate today= LocalDate.now();
            int daysToday=today.getDayOfYear(), yearsToday= today.getYear();

            int daysNacimiento=nacimiento.getDayOfYear(), yearsNacimiento=nacimiento.getYear();

            if(yearsToday-yearsNacimiento<18|| (yearsToday-yearsNacimiento==18 && daysNacimiento>daysToday))
                errors.rejectValue("nacimientoString", "invalidStr","Debe ser mayor de edad para ingresar en la plataforma");

        }

        if (usuario.getCiudad()==null || usuario.getCiudad().trim().equals("") || usuario.getCiudad().trim().equals(" "))
            errors.rejectValue("ciudad", "nonullobj","No se ha introducido la ciudad");

        if (usuario.getCiudad().length() > 100)
            errors.rejectValue("ciudad", "invalidStr","El nombre de la ciudad es demasiado largo");

        if (usuario.getCalle()==null || usuario.getCalle().trim().equals("") || usuario.getCalle().trim().equals(" "))
            errors.rejectValue("calle", "nonullobj","No se ha introducido ninguna calle");

        if (usuario.getCalle().length() > 100)
            errors.rejectValue("calle", "invalidStr","El nombre de la calle es demasiado largo");

        if (usuario.getCp() < 1000 || usuario.getCp() > 52999)
            errors.rejectValue("cp", "invalidInt","El codigo postal introducido no es valido");




    }
}
