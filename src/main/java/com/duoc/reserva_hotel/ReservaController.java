package com.duoc.reserva_hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> getAll() {
        return reservaService.getAll();
    }

    // http://localhost:8080/reservas/disponibilidad?inicio=2026-04-30&fin=2026-05-05 esta disponible
    // http://localhost:8080/reservas/disponibilidad?inicio=2026-03-28&fin=2026-03-28 esta ocupado
    // http://localhost:8080/reservas/disponibilidad?inicio=2026-03-25&fin=2026-03-27 esta cancelada
    @GetMapping("/disponibilidad")
    public String check(@RequestParam String inicio, @RequestParam String fin) {
        boolean available = reservaService.isAvailable(LocalDate.parse(inicio), LocalDate.parse(fin));
        return available ? "Disponible" : "Ocupado";
    }
}
