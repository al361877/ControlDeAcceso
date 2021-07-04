package es.uji.ei1027.ControlDeAcceso.services;

import es.uji.ei1027.ControlDeAcceso.model.Reserva;

import java.util.List;


public interface ReservaService {
   // public List<Reserva> listReservaControlador(String dni);

    public List<Reserva> listReservaGestor(String idEspacio);
}
