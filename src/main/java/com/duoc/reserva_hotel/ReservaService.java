package com.duoc.reserva_hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservaService {
    private List<Reserva> reservas = new ArrayList();

    public ReservaService() {
        reservas.add(new Reserva(1L, "HR1", "Sebastian Corvalan", 2, LocalDate.parse("2026-03-28"), LocalDate.parse("2026-03-30"), "RESERVADA"));
        reservas.add(new Reserva(2L, "HR2", "Andres Rodriguez", 1, LocalDate.parse("2026-04-01"), LocalDate.parse("2026-04-02"), "RESERVADA"));
        reservas.add(new Reserva(3L, "HR3", "Maria Castillo", 3, LocalDate.parse("2026-04-05"), LocalDate.parse("2026-04-10"), "RESERVADA"));
        reservas.add(new Reserva(4L, "HR4", "Cristian Valencia", 1, LocalDate.parse("2026-03-25"), LocalDate.parse("2026-03-30"), "CANCELADA"));
    }

    public List<Reserva> getAll() {
        return reservas;
    }

    public boolean isAvailable(LocalDate inicio, LocalDate fin) {

        if (fin.isBefore(inicio) || fin.isEqual(inicio)){
            throw new IllegalArgumentException("Error: la fecha ingresada no es valida ");
        }

        for (Reserva r : reservas) {
            if (r.getEstado().equals("RESERVADA")) {
                if (inicio.isBefore(r.getFechaTermino()) && fin.isAfter(r.getFechaInicio())) {
                    return false;
                }
            }
        }
        return true;
    }

    public String addReserva(String nombreHuesped, Integer personas, String inicio, String fin) {

        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);

        if (nombreHuesped == null || nombreHuesped.isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un nombre válido.");
        }

        if (!fechaInicio.isBefore(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de término.");
        }

        if (isAvailable(fechaInicio, fechaFin)) {
            Long newId = (long) (reservas.size() + 1);
            String newNumeroReserva = (String.format("RH%02d", newId));
            Reserva nuevaReserva = new Reserva(newId, newNumeroReserva, nombreHuesped, personas, fechaInicio, fechaFin, "RESERVADA");

            reservas.add(nuevaReserva);
        
            return "Nueva reserva ingresada";
        } else {
            return "La reserva no se pudo completar";
        }
    }

    public String removeReserva(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id de reserva no es valido");
        }
        
        for (Reserva r : reservas) {
            if ("CANCELADA".equals(r.getEstado())) {
                return "La reserva ya se encuentra cancelada";
            }
            if (r.getId().equals(id)) {
                r.setEstado("CANCELADA");
                return "La reserva a sido cancelada";
            }
        }
        return "Reserva no encontrada";
    }
}
