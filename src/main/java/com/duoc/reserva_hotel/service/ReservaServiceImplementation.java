package com.duoc.reserva_hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.reserva_hotel.model.Reserva;
import com.duoc.reserva_hotel.repository.ReservaRepository;

import java.time.LocalDate;

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
            throw new IllegalArgumentException("Reserva no encontrada");
        } 
    }

    @Override
    public boolean isAvailable(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio) || fin.isEqual(inicio)){
            throw new IllegalArgumentException("La fecha ingresada no es valida ");
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
    public Reserva createReserva(String nombreHuesped, Integer personas, LocalDate inicio, LocalDate fin) {

        if (nombreHuesped == null || nombreHuesped.isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un nombre válido.");
        }

        if (!inicio.isBefore(fin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de término.");
        }

        if (isAvailable(inicio, fin)) {
            Reserva nuevaReserva = new Reserva();
            nuevaReserva.setNombreHuesped(nombreHuesped);
            nuevaReserva.setCantidadPersonas(personas);
            nuevaReserva.setFechaInicio(inicio);
            nuevaReserva.setFechaTermino(fin);
            nuevaReserva.setEstado("RESERVADO");

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
    public Reserva updateReserva(Long id, String nombreHuesped, Integer personas, LocalDate inicio, LocalDate fin, String estado) {
        
        Optional<Reserva> optionalReserva = getReservasById(id);

        if (optionalReserva.isPresent()) {
            Reserva reservaExistente = optionalReserva.get();
            
            reservaExistente.setNombreHuesped(nombreHuesped);
            reservaExistente.setCantidadPersonas(personas);
            reservaExistente.setFechaInicio(inicio);
            reservaExistente.setFechaTermino(fin);
            reservaExistente.setEstado(estado);

            return reservaRepository.save(reservaExistente);
        }
        return null; 
    }
}
