package com.duoc.reserva_hotel;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Service
public class ReservaServiceImplementation implements ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }
    
    @Override
    public Optional<Reserva> getReservasById(Long id) {
        if(id!=null){
            return reservaRepository.findById(id);
        }else {
            return null;
        } 
    }

    @Override
    public boolean isAvailable(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio) || fin.isEqual(inicio)){
            throw new IllegalArgumentException("Error: la fecha ingresada no es valida ");
        }

        List<Reserva> reservas = reservaRepository.findAll();

        for (Reserva r : reservas) {
            if (r.getEstado().equals("RESERVADA")) {
                if (inicio.isBefore(r.getFechaTermino()) && fin.isAfter(r.getFechaInicio())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Reserva createReserva(String nombreHuesped, Integer personas, String inicio, String fin) {

        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);

        if (nombreHuesped == null || nombreHuesped.isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un nombre válido.");
        }

        if (!fechaInicio.isBefore(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de término.");
        }

        if (isAvailable(fechaInicio, fechaFin)) {
            Reserva nuevaReserva = new Reserva();
            nuevaReserva.setNombreHuesped(nombreHuesped);
            nuevaReserva.setCantidadPersonas(personas);
            nuevaReserva.setFechaInicio(fechaInicio);
            nuevaReserva.setFechaTermino(fechaFin);
            nuevaReserva.setEstado("RESERVADA");

            Reserva reservaGuardada = reservaRepository.save(nuevaReserva);

            reservaGuardada.setNumeroReserva(String.format("RH%02d", reservaGuardada.getId()));
            return reservaRepository.save(reservaGuardada);
        } else {
            throw new RuntimeException("La habitación no está disponible para las fechas seleccionadas.");
        }
    }

    @Override
    public void deleteReserva(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id de reserva no es valido");
        }
        reservaRepository.deleteById(id);
    }

    @Override
    public Reserva updateReserva(Long id, String nombreHuesped, Integer personas, String inicio, String fin, String estado) {
        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);

        List<Reserva> reservas = reservaRepository.findAll();

        for (Reserva r : reservas){
            if (r.getId().equals(id)){
                Reserva reservaActualizada = new Reserva();
                reservaActualizada.setId(id);
                reservaActualizada.setNumeroReserva(r.getNumeroReserva());
                reservaActualizada.setNombreHuesped(nombreHuesped);
                reservaActualizada.setCantidadPersonas(personas);
                reservaActualizada.setFechaInicio(fechaInicio);
                reservaActualizada.setFechaTermino(fechaFin);
                reservaActualizada.setEstado(estado);

                return reservaRepository.save(reservaActualizada);
            }
        }
        return null;

    }

    
}
