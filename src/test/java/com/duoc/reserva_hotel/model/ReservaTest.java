package com.duoc.reserva_hotel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ReservaTest {
    @Test
    void testGettersAndSetters() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setNumeroReserva("HR1");
        reserva.setNombreHuesped("Sebastian Corvalan");
        reserva.setCantidadPersonas(2);
        reserva.setFechaInicio(LocalDate.of(2026, 05, 04));
        reserva.setFechaTermino(LocalDate.of(2026, 5, 10));
        reserva.setEstado("RESERVADA");

        assertEquals(1L, reserva.getId());
        assertEquals("HR1", reserva.getNumeroReserva());
        assertEquals("Sebastian Corvalan", reserva.getNombreHuesped());
        assertEquals(2, reserva.getCantidadPersonas());
        assertEquals(LocalDate.of(2026, 05, 04), reserva.getFechaInicio());
        assertEquals(LocalDate.of(2026, 5, 10), reserva.getFechaTermino());
        assertEquals("RESERVADA", reserva.getEstado());
    }
}