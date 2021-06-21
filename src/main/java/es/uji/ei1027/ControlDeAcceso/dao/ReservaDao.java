package es.uji.ei1027.ControlDeAcceso.dao;


import ch.qos.logback.core.joran.spi.ElementPath;
//import com.sun.org.apache.regexp.internal.RE;
import es.uji.ei1027.ControlDeAcceso.model.RelacionRZ;
import es.uji.ei1027.ControlDeAcceso.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class ReservaDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS reserva
    public void addReserva(Reserva reserva) {
        try {
            System.out.println("add de reserva");
            String[] zonas=reserva.getZona().split(",");
            jdbcTemplate.update("INSERT INTO reserva VALUES (?,?,?,?,?,?,?,?,?)",
                    reserva.getId(),reserva.getDniCiudadano(),reserva.getEspacio_publico(),reserva.getEstado_reserva(),reserva.getFechaIni(),reserva.getFechaFin(),reserva.getHoraIni(),reserva.getHoraFin(),reserva.getNumPersonas()
            );

            for(String zona: zonas){
                System.out.println("relacion entre id_zona= "+zona+" e id_reserva= "+reserva.getId());
               //add de la relacion en la base de datos
                jdbcTemplate.update("INSERT INTO relacionrz VALUES (?,?)",zona,reserva.getId());

            }



            System.out.println("añadido");
        } catch (EmptyResultDataAccessException e){
            System.out.println("cath del dao");

        }
    }


    //Datos reserva
    public List<Reserva> getReservas (){
        try{
            return jdbcTemplate.query("SELECT * FROM rerserva ", new ReservaRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Reserva getReservaId (String id){
        try{
            Reserva reserva= jdbcTemplate.queryForObject("SELECT * FROM reserva WHERE id=?", new ReservaRowMapper(), id);
            //cojo todas las relaciones entre esta reserva y las zonas
            List<RelacionRZ> listaRelaciones = jdbcTemplate.query("SELECT * FROM relacionrz WHERE id_reserva=?", new RelacionRZRowMapper(), reserva.getId());
            //meto todas las zonas en un string
            String zonas = "";
            RelacionRZ relacion = null;
            for (int i = 0; i < listaRelaciones.size() - 1; i++) {
                relacion = listaRelaciones.get(i);
                zonas = zonas + relacion.getId_zona() + ",";
            }
            relacion = listaRelaciones.get(listaRelaciones.size() - 1);
            zonas = relacion.getId_zona();
            //cambio el string zonas de la reserva
            reserva.setZona(zonas);
            System.out.println(reserva.toString());
            return reserva;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Reserva> getReservasByDni(String dni){
        try{
            System.out.println("Estas en la Lista de reservas");
            List<Reserva> lista = (List<Reserva>) jdbcTemplate.query("SELECT * FROM reserva WHERE dni_ciudadano=?", new ReservaRowMapper(),dni);

            for(Reserva res:lista) {
                //cojo todas las relaciones entre esta reserva y las zonas
                List<RelacionRZ> listaRelaciones = jdbcTemplate.query("SELECT * FROM relacionrz WHERE id_reserva=?", new RelacionRZRowMapper(), res.getId());
                //meto todas las zonas en un string
                String zonas = "";
                RelacionRZ relacion = null;
                for (int i = 0; i < listaRelaciones.size() - 1; i++) {
                    relacion = listaRelaciones.get(i);
                    zonas = zonas + relacion.getId_zona() + ",";
                }
                relacion = listaRelaciones.get(listaRelaciones.size() - 1);
                zonas = relacion.getId_zona();
                //cambio el string zonas de la reserva
                res.setZona(zonas);
                System.out.println(res.toString());
            }


            return lista;
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }
    public List<Reserva> getReservasPendientesByDni(String dni){
        try{


            System.out.println("Estas en la Lista de reservas con el dni "+dni);
            List<Reserva> lista =
                    (List<Reserva>) jdbcTemplate.query("SELECT * FROM reserva WHERE dni_ciudadano=? and estado_reserva='pendienteDeUso'", new ReservaRowMapper(),dni);
            System.out.println("lista cogida");
            for(Reserva res:lista) {

                //cojo todas las relaciones entre esta reserva y las zonas
                List<RelacionRZ> listaRelaciones = jdbcTemplate.query("SELECT * FROM relacionrz WHERE id_reserva=?", new RelacionRZRowMapper(), res.getId());
                System.out.println("relaciones cogidas");
                //meto todas las zonas en un string
                StringBuilder zonas = new StringBuilder(" ");
                RelacionRZ relacion = null;
                for (int i = 0; i < listaRelaciones.size() - 1; i++) {
                    relacion = listaRelaciones.get(i);
                    System.out.println(relacion.toString());
                    zonas.append(relacion.getId_zona()).append(",");
                }
                relacion = listaRelaciones.get(listaRelaciones.size() - 1);
                zonas.append(relacion.getId_zona());
                //cambio el string zonas de la reserva
                res.setZona(zonas.toString());
                System.out.println(res.toString());
            }


            return lista;


        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    //ACTUALIZAMOS reserva
    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE reserva SET dni_ciudadano=?,franja_espacio=?,espacio_publico=?,estado_reserva=?,zona=? WHERE id=?",
                reserva.getDniCiudadano(),
                reserva.getEspacio_publico(),reserva.getEstado_reserva(),
                reserva.getZona(),reserva.getId());

    }




    public void canceladaPorUsuarioReserva(String id){


            jdbcTemplate.update("UPDATE reserva SET estado_reserva=? WHERE id=?","canceladaU",id);
        }


    public void canceladaPorControladorReserva(String id){


        jdbcTemplate.update("UPDATE reserva SET estado_reserva=? WHERE id=?","canceladaC",id);
    }
    public void finUsoReserva(String id){


        jdbcTemplate.update("UPDATE reserva SET estado_reserva=? WHERE id=?","finUso",id);
    }


}
