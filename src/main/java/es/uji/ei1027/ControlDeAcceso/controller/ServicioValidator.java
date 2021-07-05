package es.uji.ei1027.ControlDeAcceso.controller;


import es.uji.ei1027.ControlDeAcceso.model.Servicio;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class ServicioValidator implements Validator{

    @Override
    public boolean supports(Class<?> cls) {
        return Servicio.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Servicio servicio = (Servicio) obj;

        if (servicio.getId()==null || servicio.getId().trim().equals("") || servicio.getId().trim().equals(" "))
            errors.rejectValue("id_servicio", "nonullobj","No se ha introducido id del servicio");
        if (servicio.getId().length() > 20)
            errors.rejectValue("id_servicio", "invalidStr", "La id supera el límite de carácteres (20)");

        if (servicio.getTipo_servicio()==null || servicio.getTipo_servicio().trim().equals("") || servicio.getTipo_servicio().trim().equals(" "))
            errors.rejectValue("tipo_servicio", "nonullobj","No se ha introducido el tipo de servicio");
        if (servicio.getTipo_servicio().length() > 50)
            errors.rejectValue("tipo_servicio", "invalidStr", "El tipo de servicio supera el límite de carácteres (50)");

//        if (servicio.getIdEspacio()==null || servicio.getIdEspacio().trim().equals("") || servicio.getIdEspacio().trim().equals(" "))
//            errors.rejectValue("espacio", "nonullobj","No se ha introducido el espacio del servicio");
//        if (servicio.getIdEspacio().length() > 20)
//            errors.rejectValue("espacio", "invalidStr", "El espacio del servicio supera el límite de carácteres (20)");

        if (servicio.getEstacion()==null || servicio.getEstacion().trim().equals("") || servicio.getEstacion().trim().equals(" "))
            errors.rejectValue("id_estacion", "nonullobj","No se ha introducido la estacion del año del servicio");
        if (servicio.getEstacion().length() > 50)
            errors.rejectValue("id_estacion", "invalidStr", "La estacion del año del servicio supera el límite de carácteres (50)");

    }

}