package com.duoc.reserva_hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.duoc.reserva_hotel.dto.ReservaDto;
import com.duoc.reserva_hotel.dto.ReservaDtoUpdate;
import com.duoc.reserva_hotel.model.Reserva;
import com.duoc.reserva_hotel.service.ReservaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> getAllReservas() {
        return reservaService.getAllReservas();
    }

    // http://localhost:8080/reservas/disponibilidad?inicio=2026-04-30&fin=2026-05-05 esta disponible
    // http://localhost:8080/reservas/disponibilidad?inicio=2026-03-28&fin=2026-03-29 esta ocupado
    @GetMapping("/disponibilidad")
    public String isAvailable(@RequestParam String inicio, @RequestParam String fin) {
        boolean available = reservaService.isAvailable(LocalDate.parse(inicio), LocalDate.parse(fin));
        return available ? "Disponible" : "Ocupado";
    }

    // http://localhost:8080/reservas/crear
    /* {
        "nombreHuesped": "Nataly Peña",
        "personas": 2,
        "inicio": "2026-05-15",
        "fin": "2026-05-18"
        } */
    @PostMapping("/crear")
    public Reserva createReserva(@RequestBody ReservaDto dto) {
        return reservaService.createReserva(dto.nombreHuesped(), dto.personas(), dto.inicio(), dto.fin());
    }

    // http://localhost:8080/reservas/1
    @DeleteMapping("/{id}")
    public void deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
    }
    // http://localhost:8080/reservas/7
    /* {
        "nombreHuesped": "Nataly Andrea Peña",
        "personas": 3,
        "inicio": "2026-05-15",
        "fin": "2026-05-18",
        "estado": "RESERVADA"
    } */
    @PutMapping("/{id}")
    public Reserva updateReserva(@PathVariable Long id, @RequestBody ReservaDtoUpdate dto){
        return reservaService.updateReserva(id, dto.nombreHuesped(), dto.personas(), dto.inicio(), dto.fin(), dto.estado());
    }
}
