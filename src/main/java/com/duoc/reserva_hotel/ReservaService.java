package com.duoc.reserva_hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservaService {
    private List<Reserva> reservas = new ArrayList();

    public ReservaService() {
        reservas.add(new Reserva(1L, "HR01", "Sebastian Corvalan", 2, LocalDate.parse("2026-03-28"), LocalDate.parse("2026-03-30"), "RESERVADA"));
        reservas.add(new Reserva(1L, "HR02", "Andres Rodriguez", 1, LocalDate.parse("2026-04-01"), LocalDate.parse("2026-04-02"), "RESERVADA"));
        reservas.add(new Reserva(1L, "HR03", "Maria Castillo", 3, LocalDate.parse("2026-04-05"), LocalDate.parse("2026-04-10"), "RESERVADA"));
        reservas.add(new Reserva(1L, "HR04", "Cristian Valencia", 1, LocalDate.parse("2026-03-25"), LocalDate.parse("2026-03-30"), "CANCELADA"));
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

    public List<Reserva> getAll() {
        return reservas;
    }
}
