package com.duoc.reserva_hotel;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

@Service
public interface ReservaService {
    List<Reserva> getAllReservas();
    Optional<Reserva> getReservasById(Long id);
    boolean isAvailable(LocalDate inicio, LocalDate fin);
    Reserva createReserva(String nombreHuesped, Integer personas, String inicio, String fin);
    void deleteReserva(Long id);
    Reserva updateReserva (Long id, String nombreHuesped, Integer personas, String inicio, String fin, String estado);
    
}
